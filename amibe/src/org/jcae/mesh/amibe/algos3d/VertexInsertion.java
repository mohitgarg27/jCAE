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

package org.jcae.mesh.amibe.algos3d;

import gnu.trove.PrimeFinder;
import java.nio.DoubleBuffer;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcae.mesh.amibe.ds.AbstractHalfEdge;
import org.jcae.mesh.amibe.ds.AbstractHalfEdge.Quality;
import org.jcae.mesh.amibe.ds.HalfEdge;
import org.jcae.mesh.amibe.ds.Mesh;
import org.jcae.mesh.amibe.ds.Triangle;
import org.jcae.mesh.amibe.ds.TriangleHE;
import org.jcae.mesh.amibe.ds.Vertex;
import org.jcae.mesh.amibe.metrics.Location;
import org.jcae.mesh.amibe.metrics.MetricSupport.AnalyticMetricInterface;
import org.jcae.mesh.amibe.projection.MeshLiaison;
import org.jcae.mesh.amibe.projection.TriangleKdTree;
import org.jcae.mesh.xmldata.MeshReader;
import org.jcae.mesh.xmldata.MultiDoubleFileReader;

/**
 *
 * @author Jerome Robert
 */
public class VertexInsertion {
	private final MeshLiaison liaison;
	private final static Logger LOGGER = Logger.getLogger(VertexInsertion.class.getName());
	private final TriangleKdTree kdTree;
	/** triangles added when inserting a point in the middle of a triangle */
	private final Collection<Triangle> tmp = new ArrayList<Triangle>(3);
	private Collection<Vertex> notMutableInserted, mutableInserted;
	private Vertex projectedMiddle, middle;
	private final AnalyticMetricInterface metric;
	private final Quality quality = new Quality();

	public VertexInsertion(MeshLiaison liaison, final double size) {
		this(liaison, new AnalyticMetricInterface() {

			public double getTargetSize(double x, double y, double z,
				int groupId) {
				return size;
			}

			public double getTargetSizeTopo(Mesh mesh, Vertex v) {
				return size;
			}
		});
	}

	public VertexInsertion(MeshLiaison liaison, AnalyticMetricInterface metric) {
		this.liaison = liaison;
		LOGGER.info("Start creating kd-tree");
		kdTree = new TriangleKdTree(liaison.getMesh());
		LOGGER.info(kdTree.stats());
		projectedMiddle = liaison.getMesh().createVertex(0, 0, 0);
		middle = liaison.getMesh().createVertex(0, 0, 0);
		this.metric = metric;
	}

	public void insertNodes(final DoubleBuffer vertices, int group)
	{
		final int size = vertices.limit() / 3;
		AbstractList<Vertex> l = new AbstractList<Vertex>() {

			@Override
			public Vertex get(int index) {
				int n = index * 3;
				return new Vertex(null, vertices.get(n), vertices.get(n + 1),
					vertices.get(n + 2));
			}

			@Override
			public int size() {
				return size;
			}
		};
		insertNodes(l, group);
	}

	/**
	 * Insert vertices from another mesh
	 * @param vertices the vertices to insert
	 * @param group insert vertices only in triangles of this group. Use -1
	 * to try to insert in all groups
	 * @param liaisonError the maximal acceptable distance between the
	 * inserted point and it's projection.
	 * @param insertionTol Do not the insert point if a point  already exist at
	 * a distance lower than this value
	 */
	public void insertNodes(List<Vertex> vertices, int group)
	{
		if(vertices.isEmpty())
		{
			mutableInserted = Collections.emptyList();
			notMutableInserted = Collections.emptyList();
			return;
		}
		Location projection = new Location();
		mutableInserted = new ArrayList<Vertex>(vertices.size());
		notMutableInserted = new ArrayList<Vertex>();
		int n = vertices.size();
		int step = PrimeFinder.nextPrime(n+1);
		int k = 0;
		int nbInserted = 0;
		main: for(int i = 0; i < n; i++)
		{
			k = (k + step) % n;
			Vertex v = vertices.get(k);
			double localMetric = metric.getTargetSize(
				v.getX(), v.getY(), v.getZ(), group);
			double localMetric2 = localMetric * localMetric;
			double tol2 = localMetric2 / (40 * 40);
			TriangleHE t = (TriangleHE) kdTree.getClosestTriangle(
				v, projection, group);
			liaison.move(v, projection, group, true);
			for(Vertex tv:t.vertex)
			{
				if(tv.sqrDistance3D(v) < tol2)
				{
					if(tv.isMutable())
						mutableInserted.add(tv);
					else
						notMutableInserted.add(tv);
					continue main;
				}
			}

			// We could check that we are close from an edge but we don't
			// because swaping will properly handle this case
			t.split(liaison.getMesh(), v, tmp);
			kdTree.replace(t, tmp);
			tmp.clear();
			mutableInserted.add(v);
			swap(v, localMetric2 / 2, group);
			nbInserted ++;
		}
		LOGGER.info(nbInserted+" / "+vertices.size()+" inserted nodes on group "+group);
	}

	public Collection<Vertex> getNotMutableInserted() {
		return notMutableInserted;
	}

	public Collection<Vertex> getMutableInserted() {
		return mutableInserted;
	}

	private void swap(Vertex v, double sqrDeflection, int group)
	{
		Mesh mesh = liaison.getMesh();
		HalfEdge current = (HalfEdge) v.getIncidentAbstractHalfEdge((Triangle)v.getLink(), null);
		current = current.next();
		Vertex o = current.origin();
		assert current.apex() == v;
		boolean redo = true;
		while(redo)
		{
			redo = false;
			while(true)
			{
				boolean isSwapped = false;
				if (!current.hasAttributes(AbstractHalfEdge.NONMANIFOLD |
					AbstractHalfEdge.BOUNDARY | AbstractHalfEdge.OUTER)
					&& current.canSwapTopology())
				{
					quality.setEdge(current);
					if(quality.getSwappedAngle() > 0 &&
						quality.getSwappedQuality() > quality.getQuality())
					{
						middle.middle(current.apex(), current.sym().apex());
						liaison.move(projectedMiddle, middle, group, true);
						if(projectedMiddle.sqrDistance3D(middle) < sqrDeflection)
						{
							kdTree.remove(current.getTri());
							kdTree.remove(current.sym().getTri());
							current = (HalfEdge) mesh.edgeSwap(current);
							HalfEdge swapped = current.next();
							kdTree.addTriangle(swapped.getTri());
							kdTree.addTriangle(swapped.sym().getTri());
							redo = true;
							isSwapped = true;
						}
					}
				}

				if(!isSwapped)
				{
					current = current.nextApexLoop();
					if (current.origin() == o)
						break;
				}
			}
		}
	}
	/**
	 * Try to insert a vertex into an edge of the given triangle
	 * @param t
	 * @param tol2
	 * @param v
	 * @return The number of created triangles
	 */
	private boolean edgeSplit(Triangle t, double tol2, Vertex v)
	{
		AbstractHalfEdge e = t.getAbstractHalfEdge();
		for(int i = 0; i < 3; i++)
		{
			if(MeshLiaison.sqrOrthoDistance(v, e) < tol2)
			{
				if(e.hasAttributes(AbstractHalfEdge.NONMANIFOLD))
				{
					Iterator<AbstractHalfEdge> rit = e.fanIterator();
					while(rit.hasNext())
						kdTree.remove(rit.next().getTri());
				}
				else
				{
					kdTree.remove(t);
					kdTree.remove(e.sym().getTri());
				}
				liaison.getMesh().vertexSplit(e, v);
				Iterator<Triangle> it = v.getNeighbourIteratorTriangle();
				while(it.hasNext())
					kdTree.addTriangle(it.next());
				return true;
			}
			e = e.next();
			assert e != null;
		}
		return false;
	}

	public static void main(final String[] args) {
		try {
			MultiDoubleFileReader mdf = new MultiDoubleFileReader("/tmp/tmps2zXh3/nodes.bin");
			Mesh mesh = new Mesh();
			MeshReader.readObject3D(mesh, "/tmp/debug2.zebra/amibe.dir");
			MeshLiaison ml = MeshLiaison.create(mesh);
			ml.getMesh().buildGroupBoundaries();
			VertexInsertion vi = new VertexInsertion(ml, 300);
			for(int gId = 1; gId <= ml.getMesh().getNumberOfGroups(); gId++)
			{
				DoubleBuffer vs = mdf.next();
				if(gId == 65)
					vi.insertNodes(vs, gId);
			}
		} catch (Exception ex) {
			Logger.getLogger(VertexInsertion.class.getName()).log(Level.SEVERE,
				null, ex);
		}
	}
}
