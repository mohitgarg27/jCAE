/*
 * Project Info:  http://jcae.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * (C) Copyright 2012, by EADS France
 */
package org.jcae.mesh.amibe.metrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * An AnalyticMetric which refine mesh around a set of points and lines.
 * The metric around each point is:
 * Sinf * (1 - 2 * (1 - S0 / Sinf) / (1 + (R * d + 1) ^ 3 ) )
 * where Sinf is the mesh size far from the point, S0 is the mesh size on the
 * point, d is the distance from the point and R a coeficient saying how fast
 * Sinf is reached.
 * @author Jerome Robert
 */
public class DistanceMetric implements MetricSupport.AnalyticMetricInterface {

	private static final Logger LOGGER=Logger.getLogger(DistanceMetric.class.getName());

	public interface DistanceMetricInterface
	{
		double getTargetSize(double x, double y, double z);
	}

	private static class PointSource implements DistanceMetricInterface
	{
		private final double sx,sy,sz;
		/** alpha = 2 * (1 - SO / Sinf) */
		private final double alpha;
		private final double coef;
		private final double sizeInf;
		/**
		 * if the distance^2 is greater than this value this source is not
		 * concidered
		 */
		private final double threshold;

		public PointSource(final double sx, final double sy, final double sz,
			final double alpha, final double coef, final double sizeInf, final double threshold)
		{
			this.sx = sx;
			this.sy = sy;
			this.sz = sz;
			this.alpha = alpha;
			this.coef = coef;
			this.sizeInf = sizeInf;
			this.threshold = threshold;
		}

		public double getTargetSize(final double x, final double y, final double z)
		{
			double toReturn = sizeInf;
			final double dx = sx-x;
			final double dy = sy-y;
			final double dz = sz-z;
			final double d2 = dx*dx+dy*dy+dz*dz;
			if(d2 < threshold)
			{
				final double d = 1.0 + coef * Math.sqrt(d2);
				toReturn = sizeInf * (1.0 - alpha / (1.0 + d * d * d));
			}
			return toReturn;
		}
	}

	private static class LineSource implements DistanceMetricInterface
	{
		private final double sx0,sy0,sz0;
		private final double sx1,sy1,sz1;
		private final boolean closed0, closed1;
		private final double [] dir = new double[3];
		private final double maxAbscissa;
		/** alpha = 2 * (1 - SO / Sinf) */
		private final double alpha;
		private final double coef;
		private final double sizeInf;
		/**
		 * if the distance^2 is greater than this value this source is not
		 * considered
		 */
		private final double threshold;

		public LineSource(final double sx0, final double sy0, final double sz0, final boolean closed0,
			final double sx1, final double sy1, final double sz1, final boolean closed1,
			final double alpha, final double coef, final double sizeInf, final double threshold)
		{
			this.sx0 = sx0;
			this.sy0 = sy0;
			this.sz0 = sz0;
			this.sx1 = sx1;
			this.sy1 = sy1;
			this.sz1 = sz1;
			this.closed0 = closed0;
			this.closed1 = closed1;
			this.alpha = alpha;
			this.coef = coef;
			this.sizeInf = sizeInf;
			this.threshold = threshold;
			this.dir[0] = this.sx1 - this.sx0;
			this.dir[1] = this.sy1 - this.sy0;
			this.dir[2] = this.sz1 - this.sz0;
			final double norm = Math.sqrt(dir[0] * dir[0] + dir[1] * dir[1] + dir[2] * dir[2]);
			if (norm < 1.e-20)
				throw new IllegalArgumentException("Endpoints must be different");
			final double invNorm = 1.0 / norm;
			this.dir[0] *= invNorm;
			this.dir[1] *= invNorm;
			this.dir[2] *= invNorm;
			if (closed0 && closed1) {
				maxAbscissa = norm;
			} else {
				maxAbscissa = Double.MAX_VALUE;
			}
		}

		public double getTargetSize(final double x, final double y, final double z)
		{
			// Compute the projection on the line
			double dx = x - sx0;
			double dy = y - sy0;
			double dz = z - sz0;
			double abscissa = dx * dir[0] + dy * dir[1] + dz * dir[2];
			if (closed0 && abscissa < 0.0) {
				abscissa = 0.0;
			}
			if (closed1 && abscissa > maxAbscissa) {
				abscissa = maxAbscissa;
			}
			dx -= dir[0] * abscissa;
			dy -= dir[1] * abscissa;
			dz -= dir[2] * abscissa;
			double d2 = dx * dx + dy * dy + dz * dz;
			if(d2 < threshold)
			{
				double d = Math.sqrt(d2);
				return sizeInf * (1.0 - alpha / (1.0 + Math.pow(coef * d + 1.0, 3)));
			}
			return sizeInf;
		}
	}


	private final List<DistanceMetricInterface> sources = new ArrayList<DistanceMetricInterface>();
	private final double sizeInf;
	public DistanceMetric(double sizeInf) {
		this.sizeInf = sizeInf;
	}

	public DistanceMetric(double sizeInf, String fileName) throws IOException {
		this(sizeInf);
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String buffer;
		while((buffer = br.readLine()) != null)
		{
			if (buffer.startsWith("#"))
				continue;
			String[] line = buffer.trim().split("[\\s,;]+");
			int type = Integer.parseInt(line[0]);
			if (type == 1)
			{
				double x = Double.parseDouble(line[1]);
				double y = Double.parseDouble(line[2]);
				double z = Double.parseDouble(line[3]);
				double size0 = Double.parseDouble(line[4]);
				double coef = Double.parseDouble(line[5]);
				addPoint(x, y, z, size0, coef);
			} else if (type == 2)
			{
				double x0 = Double.parseDouble(line[1]);
				double y0 = Double.parseDouble(line[2]);
				double z0 = Double.parseDouble(line[3]);
				boolean closed0 = (Integer.parseInt(line[4]) != 0);
				double x1 = Double.parseDouble(line[5]);
				double y1 = Double.parseDouble(line[6]);
				double z1 = Double.parseDouble(line[7]);
				boolean closed1 = (Integer.parseInt(line[8]) != 0);
				double size0 = Double.parseDouble(line[9]);
				double coef = Double.parseDouble(line[10]);
				addLine(x0, y0, z0, closed0, x1, y1, z1, closed1, size0, coef);
			}
			else
				throw new IllegalArgumentException("Invalid line found in file "+fileName+": "+line);
		}
		br.close();
	}

	/**
	 * Add a point around which to refine
	 * @param size0 metric on the point
	 * @param sizeInf metric far from the point
	 * @param coef how fast we go from size0 to sizeInf
	 */
	public final void addPoint(final double x, final double y, final double z, final double size0, final double coef)
	{
		double alpha = 2.0 * (1.0 - size0 / sizeInf);
		if (alpha < 0.05)
		{
			// Do nothing
			LOGGER.warning("Source point ignored, size should be lower than target size at infinity");
			return;
		}
		double scoef = 1.0 / (Math.log(sizeInf / size0) * (sizeInf + size0) * coef);
		double threshold = (Math.pow(alpha/0.05 - 1.0, 1.0/3.0) - 1.0) / scoef;
		threshold = threshold * threshold;
		sources.add(new PointSource(x, y, z, alpha, scoef, sizeInf, threshold));
	}

	/**
	 * Add a line around which to refine
	 * @param size0 metric on the point
	 * @param coef how fast we go from size0 to sizeInf
	 */
	public final void addLine(
		final double x0, final double y0, final double z0, final boolean closed0,
		final double x1, final double y1, final double z1, final boolean closed1,
		final double size0, final double coef)
	{
		double alpha = 2.0 * (1.0 - size0 / sizeInf);
		if (alpha < 0.05)
		{
			// Do nothing
			LOGGER.warning("Source line ignored, size should be lower than target size at infinity");
			return;
		}
		double scoef = 1.0 / (Math.log(sizeInf / size0) * (sizeInf + size0) * coef);
		double threshold = (Math.pow(alpha/0.05 - 1.0, 1.0/3.0) - 1.0) / scoef;
		threshold = threshold * threshold;
		sources.add(new LineSource(x0, y0, z0, closed0, x1, y1, z1, closed1, alpha, scoef, sizeInf, threshold));
	}

	@Override
	public double getTargetSize(double x, double y, double z) {
		double minValue = sizeInf;
		for (DistanceMetricInterface s : sources) {
			minValue = Math.min(s.getTargetSize(x, y, z), minValue);
		}
		return minValue;
	}
}