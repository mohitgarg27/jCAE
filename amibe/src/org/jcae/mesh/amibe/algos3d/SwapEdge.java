/* jCAE stand for Java Computer Aided Engineering. Features are : Small CAD
   modeler, Finite element mesher, Plugin architecture.

    Copyright (C) 2005, by EADS CRC
    Copyright (C) 2007,2008, by EADS France

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.jcae.mesh.amibe.algos3d;

import org.jcae.mesh.amibe.ds.Mesh;
import org.jcae.mesh.amibe.ds.HalfEdge;
import org.jcae.mesh.amibe.ds.AbstractHalfEdge;
import org.jcae.mesh.xmldata.MeshReader;
import org.jcae.mesh.xmldata.MeshWriter;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Laplacian smoothing.
 */

public class SwapEdge extends AbstractAlgoHalfEdge
{
	private static final Logger LOGGER=Logger.getLogger(SwapEdge.class.getName());
	private double planarMin = 0.95;
	private int counter = 0;
	
	/**
	 * Creates a <code>SwapEdge</code> instance.
	 *
	 * @param m  the <code>Mesh</code> instance to modify
	 * @param options  map containing key-value pairs to modify algorithm
	 *        behaviour.  Valid key is <code>angle</code>.
	 */
	public SwapEdge(final Mesh m, final Map<String, String> options)
	{
		super(m);
		for (final Map.Entry<String, String> opt: options.entrySet())
		{
			final String key = opt.getKey();
			final String val = opt.getValue();
			if (key.equals("angle"))
			{
				planarMin = new Double(val).doubleValue();
				LOGGER.fine("Minimum dot product of face normals allowed for swapping an edge: "+planarMin);
			}
			else
				throw new RuntimeException("Unknown option: "+key);
		}
		counter = m.getTriangles().size() * 3;
	}
	
	@Override
	public Logger thisLogger()
	{
		return LOGGER;
	}

	@Override
	public void preProcessAllHalfEdges()
	{
	}

	@Override
	public double cost(final HalfEdge e)
	{
		return - e.checkSwap3D(planarMin);
	}
	
	@Override
	public boolean canProcessEdge(HalfEdge current)
	{
		return counter > 0 && !current.hasAttributes(AbstractHalfEdge.BOUNDARY | AbstractHalfEdge.NONMANIFOLD);
	}

	@Override
	public HalfEdge processEdge(HalfEdge current, double costCurrent)
	{
		if (LOGGER.isLoggable(Level.FINE))
			LOGGER.fine("Swap edge: "+current+"  cost="+costCurrent);
		counter --;
		for (int i = 0; i < 3; i++)
		{
			HalfEdge h = uniqueOrientation(current);
			if (!tree.remove(uniqueOrientation(current)))
				notInTree++;
			assert !tree.contains(h);
			h.clearAttributes(AbstractHalfEdge.MARKED);
			current = current.next();
		}
		HalfEdge sym = current.sym();
		for (int i = 0; i < 2; i++)
		{
			sym = sym.next();
			HalfEdge h = uniqueOrientation(sym);
			if (!tree.remove(h))
				notInTree++;
			h.clearAttributes(AbstractHalfEdge.MARKED);
		}
		current = (HalfEdge) mesh.edgeSwap(current);
		// Update edge costs
		for (int i = 0; i < 2; i++)
		{
			addToTree(uniqueOrientation(current));
			current = current.prev();
		}
		current = current.sym();
		for (int i = 0; i < 2; i++)
		{
			current = current.next();
			addToTree(uniqueOrientation(current));
		}
		return current.prev();
	}
	
	@Override
	public void postProcessAllHalfEdges()
	{
		LOGGER.info("Number of swapped edges: "+processed);
		//LOGGER.info("Number of edges which were not in the binary tree before being removed: "+notInTree);
		LOGGER.info("Number of edges still present in the binary tree: "+tree.size());
	}

	private final static String usageString = "<xmlDir> <brepFile> <outputDir>";

	/**
	 * 
	 * @param args xmlDir, -t tolerance | -n triangle, brepFile, output
	 */
	public static void main(final String[] args)
	{
		final HashMap<String, String> options = new HashMap<String, String>();
		if(args.length != 3)
		{
			System.out.println(usageString);
			return;
		}
		LOGGER.info("Load geometry file");
		final Mesh mesh = new Mesh();
		try
		{
			MeshReader.readObject3D(mesh, args[0]);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		new SwapEdge(mesh, options).compute();
		final File brepFile=new File(args[1]);
		try
		{
			MeshWriter.writeObject3D(mesh, args[2], brepFile.getName());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
