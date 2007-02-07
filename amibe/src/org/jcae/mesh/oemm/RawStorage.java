/* jCAE stand for Java Computer Aided Engineering. Features are : Small CAD
   modeler, Finite element mesher, Plugin architecture.

    Copyright (C) 2005, by EADS CRC
    Copyright (C) 2007, by EADS France

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
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA */

package org.jcae.mesh.oemm;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

/**
 * This class implements out-of-core storage of raw OEMM structures.
 */
public class RawStorage
{
	private static Logger logger = Logger.getLogger(RawStorage.class);	

	//  In the raw file, a triangle has 9 double coordinates and two ints.
	private static final int TRIANGLE_SIZE_RAW = 80;
	//  In the dispatched file, a triangle has 9 int coordinates and an int.
	private static final int TRIANGLE_SIZE_DISPATCHED = 40;
	private static final int bufferSize = (TRIANGLE_SIZE_RAW * TRIANGLE_SIZE_DISPATCHED) << 4;
	private static ByteBuffer bb = ByteBuffer.allocate(bufferSize);
	private static DoubleBuffer bbD = bb.asDoubleBuffer();
	private static IntBuffer bbI = bb.asIntBuffer();
	
	public static interface SoupReaderInterface
	{
		public void processVertex(int i, Object coord);
		public void processTriangle(int group);
	}

	public static void readSoup(OEMM oemm, String file, SoupReaderInterface proc)
	{
		int [] ijk = new int[3];
		double [] xyz = new double[3];
		boolean hasNext = true;
		try
		{
			FileChannel fc = new FileInputStream(file).getChannel();
			while (hasNext)
			{
				bb.rewind();
				int nr = fc.read(bb);
				if (nr < bufferSize)
					hasNext = false;
				bbD.rewind();
				for(; nr > 0; nr -= TRIANGLE_SIZE_RAW)
				{
					for (int i = 0; i < 3; i++)
					{
						bbD.get(xyz);
						if (oemm != null)
						{
							oemm.double2int(xyz, ijk);
							proc.processVertex(i, ijk);
						}
						else
							proc.processVertex(i, xyz);
					}
					bbD.get();
					bbI.position(2*bbD.position() - 2);
					int attribute = bbI.get();
					proc.processTriangle(attribute);
				}
			}
			fc.close();
		}
		catch (FileNotFoundException ex)
		{
			logger.error("File "+file+" not found");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IOException ex)
		{
			logger.error("I/O error when reading "+file);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public static class CountTriangles implements SoupReaderInterface
	{
		private OEMMNode [] cells = new OEMMNode[3];
		private OEMM oemm;
		private long tcount = 0;
		public CountTriangles(OEMM o)
		{
			oemm = o;
		}
		public void processVertex(int i, Object coord)
		{
			int [] ijk = (int []) coord;
			cells[i] = oemm.build(ijk);
		}
		public void processTriangle(int group)
		{
			tcount++;
			cells[0].tn++;
			if (cells[1] != cells[0])
				cells[1].tn++;
			if (cells[2] != cells[0] && cells[2] != cells[1])
				cells[2].tn++;
		}
		long getTriangleCount()
		{
			return tcount;
		}
	}

	/**
	 * Build a raw OEMM and count the number of triangles which have to be assigned
	 * to each leaf.
	 *
	 * A triangle soup is read from the file associated with a raw OEMM, deepest cells
	 * for triangle vertices are created if needed, and triangle counters are updated.
	 * When this routine returns, all leaf nodes have been created and each node knowa
	 * how many triangles will be assigned to it in later stages.
	 * 
	 * @param  tree  a raw OEMM
	 */
	public static void countTriangles(RawOEMM tree)
	{
		if (tree.status != RawOEMM.OEMM_CREATED)
		{
			logger.error("The RawOEMM must first be initialized by calling RawOEMM(String file, int lmax, double [] umin, double [] umax)");
			return;
		}
		logger.info("Count triangles");
		logger.debug("Reading "+tree.getFileName()+" and count triangles");
		CountTriangles ct = new CountTriangles(tree);
		readSoup(tree, tree.getFileName(), ct);
		logger.info("Number of triangles: "+ct.getTriangleCount());
		tree.status = RawOEMM.OEMM_INITIALIZED;
		tree.printInfos();
	}
	
	public static Iterator getFacesIterator(final RawOEMM tree)
	{
		if (tree.status != RawOEMM.OEMM_CREATED)
		{
			logger.error("The RawOEMM must first be initialized by calling RawOEMM(String file, int lmax, double [] umin, double [] umax)");
			return null;
		}
		return new Iterator()
		{
			private boolean hasNext = true;
			private FileChannel fc;
			private ByteBuffer bb;
			private DoubleBuffer bbD;
			private IntBuffer bbI;
			private double [] xyz = new double[10];
			private int [] group = new int[1];
			private long count = 0;
			private void init()
			{
				try
				{
					fc = new FileInputStream(tree.getFileName()).getChannel();
					bb = ByteBuffer.allocate(TRIANGLE_SIZE_RAW);
					bbD = bb.asDoubleBuffer();
					bbI = bb.asIntBuffer();
				}
				catch (FileNotFoundException ex)
				{
					logger.error("File "+tree.getFileName()+" not found");
				}
			}
			public boolean hasNext()
			{
				if (fc == null)
					init();
				return hasNext;
			}
			public Object next()
			{
				if (!hasNext())
					throw new NoSuchElementException();
				try
				{
					bb.rewind();
					count++;
					int nr = fc.read(bb);
					if (nr < TRIANGLE_SIZE_RAW)
						hasNext = false;
					bbD.rewind();
					bbD.get(xyz);
					bbI.position(2*bbD.position() - 2);
					bbI.get(group);
					xyz[9] = group[0] + 0.5;
					return xyz;
				}
				catch (IOException ex)
				{
					hasNext = false;
				}
				return null;
			}
			public void remove()
			{
			}
		};
	}
	
	public static class DispatchTriangles implements SoupReaderInterface
	{
		private OEMMNode [] cells = new OEMMNode[3];
		private int [] ijk9 = new int[9];
		private OEMM oemm;
		private FileChannel fc;
		public DispatchTriangles(OEMM o, FileChannel f)
		{
			oemm = o;
			fc = f;
		}
		public void processVertex(int i, Object coord)
		{
			int [] ijk = (int []) coord;
			cells[i] = oemm.search(ijk);
			for (int j = 0; j < 3; j++)
				ijk9[6-3*i+j] = ijk[j];
		}
		public void processTriangle(int group)
		{
			try
			{
				addToCell(fc, cells[0], ijk9, group);
				if (cells[1] != cells[0])
					addToCell(fc, cells[1], ijk9, group);
				if (cells[2] != cells[0] && cells[2] != cells[1])
					addToCell(fc, cells[2], ijk9, group);
			}
			catch (IOException ex)
			{
				logger.error("I/O error when reading file  "+oemm.getFileName());
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * Read the triangle soup another time, and dispatch triangles into an intermediate
	 * OEMM data structure.
	 *
	 * The data structure has been setup in {@link #countTriangles}, and willl now be
	 * written onto disk as a linear octree.  Each block is composed of a header containing:
	 * <ol>
	 * <li>Block size.</li>
	 * <li>Cell size (in integer coordinates).</li>
	 * <li>Integer coordinates of its lower-left corner.</li>
	 * <li>Exact number of triangles stored in this leaf.</li>
	 * </ol>
	 * It is followed by the integer coordinates of triangle vertices.
	 * 
	 * @param  tree  a raw OEMM
	 * @param  structFile  output file containing octree data structure
	 * @param  dataFile  dispatched data file
	 */
	public static final void dispatch(RawOEMM tree, String structFile, String dataFile)
	{
		if (tree.status < RawOEMM.OEMM_INITIALIZED)
		{
			logger.error("The RawOEMM must first be initialized by calling countTriangles()");
			return;
		}
		logger.info("Put triangles into a linearized octree");
		logger.debug("Raw OEMM: compute global offset for raw file");
		//  For each octant, compute its index and its offset in output file.
		ComputeOffsetProcedure proc = new ComputeOffsetProcedure();
		tree.walk(proc);
		logger.debug("Raw OEMM: compute min/max indices");
		long outputFileSize = proc.getOffset();
		ComputeMinMaxIndicesProcedure cmmi_proc = new ComputeMinMaxIndicesProcedure();
		tree.walk(cmmi_proc);
		
		try
		{
			logger.debug("Raw OEMM: dispatch triangles into raw OEMM");
			FileInputStream fs = new FileInputStream(tree.getFileName());
			long size = fs.getChannel().size();
			RandomAccessFile raf = new RandomAccessFile(dataFile, "rw");
			FileChannel fc = raf.getChannel();
			raf.setLength(outputFileSize);

			DispatchTriangles dt = new DispatchTriangles(tree, fc);
			readSoup(tree, tree.getFileName(), dt);

			logger.debug("Raw OEMM: flush buffers");
			FlushBuffersProcedure fb_proc = new FlushBuffersProcedure(fc);
			tree.walk(fb_proc);
			raf.close();
			
			//  Write octree data structure onto disk
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(structFile)));
			WriteStructureProcedure wh_proc = new WriteStructureProcedure(out, dataFile, tree.nr_leaves, tree.x0);
			tree.walk(wh_proc);
			out.close();
		}
		catch (FileNotFoundException ex)
		{
			logger.error("File "+tree.getFileName()+" not found");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		catch (IOException ex)
		{
			logger.error("I/O error when reading file  "+tree.getFileName());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Extracts a raw OEMM from an intermediate OEMM.
	 *
	 * @param  file  file containing the intermediate OEMM.
	 * @return RawOEMM a raw OEMM.
	 */
	public static OEMM loadIntermediate(String file)
	{
		logger.debug("Loading intermediate raw OEMM from "+file);
		OEMM ret = new OEMM("(null)");
		try
		{
			DataInputStream bufIn = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			int [] ijk = new int[3];
			int version = bufIn.readInt();
			assert version == 1;
			int nrleaves = bufIn.readInt();
			int nrbytes = bufIn.readInt();
			byte [] name = new byte[nrbytes+1];
			bufIn.read(name, 0, nrbytes);
			ret = new OEMM(new String(name));
			ret.leaves = new OEMMNode[nrleaves];
			for (int i = 0; i < 4; i++)
				ret.x0[i] = bufIn.readDouble();
			for (int i = 0; i < nrleaves; i++)
			{
				long position = bufIn.readLong();
				int nr = bufIn.readInt();
				int size = bufIn.readInt();
				ijk[0] = bufIn.readInt();
				ijk[1] = bufIn.readInt();
				ijk[2] = bufIn.readInt();
				OEMMNode n = new OEMMNode(size, ijk);
				ret.insert(n);
				if (ret.head[0] != n)
					ret.head[0].tn += nr;
				n.counter = position;
				n.tn = nr;
				n.leafIndex = i;
				n.isLeaf = true;
				ret.leaves[i] = n;
			}
			bufIn.close();
		}
		catch (FileNotFoundException ex)
		{
			logger.error("File "+file+" not found");
		}
		catch (IOException ex)
		{
			logger.error("I/O error when reading file "+file);
		}
		ret.status = RawOEMM.OEMM_INITIALIZED;
		//  Adjust minIndex and maxIndex values
		ComputeMinMaxIndicesProcedure cmmi_proc = new ComputeMinMaxIndicesProcedure();
		ret.walk(cmmi_proc);
		return ret;
	}
	
	private static void addToCell(FileChannel fc, OEMMNode current, int [] ijk, int attribute)
		throws IOException
	{
		assert current.counter <= fc.size();
		//  With 20 millions of triangles, unbuffered output took 420s
		//  and buffered output 180s (4K buffer cache)
		ByteBuffer list = (ByteBuffer) current.extra;
		if (list == null)
		{
			//  Must be a multiple of 10!
			current.extra = ByteBuffer.allocate(4000);
			list = (ByteBuffer) current.extra;
			list.putLong(current.counter);
			list.flip();
			fc.write(list, current.counter);
			current.counter += list.limit();
			list.clear();
		}
		else if (!list.hasRemaining())
		{
			// Flush buffer
			list.flip();
			fc.write(list, current.counter);
			current.counter += list.limit();
			list.clear();
		}
		for (int i = 0; i < ijk.length; i++)
			list.putInt(ijk[i]);
		list.putInt(attribute);
		current.tn++;
	}
	
	private static class ComputeOffsetProcedure extends TraversalProcedure
	{
		private long offset = 0L;
		public final int action(OEMMNode current, int octant, int visit)
		{
			if (visit != LEAF)
				return SKIPWALK;
			current.counter = offset;
			offset += 8L + TRIANGLE_SIZE_DISPATCHED * (long) current.tn;
			//  Reinitialize this counter for further processing
			current.tn = 0;
			current.extra = null;
			return OK;
		}
		public long getOffset()
		{
			return offset;
		}
		public void init()
		{
			super.init();
			offset = 0L;
		}
	}
	
	private static class ComputeMinMaxIndicesProcedure extends TraversalProcedure
	{
		private int nrLeaves = 0;
		public final int action(OEMMNode current, int octant, int visit)
		{
			if (visit == PREORDER)
				current.minIndex = nrLeaves;
			else if (visit == POSTORDER)
				current.maxIndex = nrLeaves;
			else if (visit == LEAF)
			{
				current.leafIndex = nrLeaves;
				nrLeaves++;
			}
			return OK;
		}
	}
	
	private static class FlushBuffersProcedure extends TraversalProcedure
	{
		private FileChannel fc;
		public FlushBuffersProcedure(FileChannel channel)
		{
			fc = channel;
		}
		public final int action(OEMMNode current, int octant, int visit)
		{
			if (visit != LEAF)
				return SKIPWALK;
			ByteBuffer list = (ByteBuffer) current.extra;
			if (list == null)
			{
				list = ByteBuffer.allocate(8);
				list.putLong(current.counter);
				list.rewind();
				try
				{
					assert current.counter <= fc.size();
					fc.write(list, current.counter);
				}
				catch (IOException ex)
				{
					logger.error("I/O error when writing file");
				}
				return OK;
			}
			// Flush buffer
			try
			{
				assert current.counter <= fc.size();
				list.flip();
				fc.write(list, current.counter);
				current.counter += list.limit();
			}
			catch (IOException ex)
			{
				logger.error("I/O error when writing file");
			}
			return OK;
		}
	}
	
	private static class WriteStructureProcedure extends TraversalProcedure
	{
		private DataOutputStream out;
		public WriteStructureProcedure(DataOutputStream outStream, String dataFile, int l, double [] x0)
			throws IOException
		{
			out = outStream;
			//  Format version
			out.writeInt(1);
			//  Number of leaves
			out.writeInt(l);
			//  Number of bytes in data file name
			out.writeInt(dataFile.length());
			//  Data file
			out.writeBytes(dataFile);
			//  Integer <--> double coordinates
			for (int i = 0; i < 4; i++)
				out.writeDouble(x0[i]);
		}
		public final int action(OEMMNode current, int octant, int visit)
		{
			if (visit != LEAF)
				return SKIPWALK;
			try
			{
				//  Offset in data file
				//  This offset had been shifted when writing triangles
				current.counter -= 8L + TRIANGLE_SIZE_DISPATCHED * current.tn;
				out.writeLong(current.counter);
				//  Number of triangles really found
				out.writeInt(current.tn);
				//  Edge size
				out.writeInt(current.size);
				//  Lower-left corner
				out.writeInt(current.i0);
				out.writeInt(current.j0);
				out.writeInt(current.k0);
			}
			catch (IOException ex)
			{
				logger.error("I/O error when writing intermediate raw OEMM");
			}
			return OK;
		}
	}
	
}
