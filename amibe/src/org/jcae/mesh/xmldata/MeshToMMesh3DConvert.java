/* jCAE stand for Java Computer Aided Engineering. Features are : Small CAD
   modeler, Finit element mesher, Plugin architecture.
 
	Copyright (C) 2003 Jerome Robert <jeromerobert@users.sourceforge.net>
 
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

package org.jcae.mesh.xmldata;

import org.jcae.mesh.mesher.ds.*;
import org.jcae.mesh.cad.*;
import java.io.*;
import java.util.Iterator;
import gnu.trove.TIntIntHashMap;
import gnu.trove.TObjectIntHashMap;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;
import org.apache.xpath.CachedXPathAPI;
import org.apache.log4j.Logger;


public class MeshToMMesh3DConvert extends JCAEXMLData
{
	private static Logger logger=Logger.getLogger(MeshToMMesh3DConvert.class);
	private int nrRefs = 0;
	private int nrNodes = 0;
	private int nrTriangles = 0;
	private int offsetBnd = 0;
	private int nodeOffset = 0;
	private int [] ind = new int[3];
	private TIntIntHashMap xrefs = null;
	private double [] coordRefs = null;
	private DataOutputStream nodesOut, refsOut, trianglesOut, groupsOut;
	private String xmlDir;
	private File xmlFile;
	private File nodesFile, refFile, trianglesFile, groupsFile;
	private Document documentOut;
	private Element groupsElement;
	
	public MeshToMMesh3DConvert (String dir)
	{
		xmlDir = dir;
	}
	
	public void computeRefs(String xmlInFile)
	{
		Document document;
		try
		{
			document = XMLHelper.parseXML(new File(xmlDir, xmlInFile));
		}
		catch(FileNotFoundException ex)
		{
			return;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		CachedXPathAPI xpath = new CachedXPathAPI();
		try
		{
			Node submeshElement = xpath.selectSingleNode(document,
				"/jcae/mesh/submesh");
			Node submeshNodes = xpath.selectSingleNode(submeshElement, "nodes");
			
			int numberOfReferences = Integer.parseInt(
				xpath.selectSingleNode(submeshNodes, "references/number/text()").getNodeValue());
			nrRefs += numberOfReferences;
			int numberOfNodes = Integer.parseInt(
				xpath.selectSingleNode(submeshNodes, "number/text()").getNodeValue());
			nrNodes += numberOfNodes;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		logger.debug("Total: "+nrRefs+" references");
	}
	
	public void initialize(String xmlOutFile)
	{
		coordRefs = new double[3*nrRefs];
		xrefs = new TIntIntHashMap(nrRefs);
		
		xmlFile = new File(xmlDir, xmlOutFile);
		File dir = new File(xmlDir, xmlOutFile+".files");
		//create the directory if it does not exist
		if(!dir.exists())
			dir.mkdirs();
		
		nodesFile=new File(dir, JCAEXMLData.nodes3dFilename);
		refFile = new File(dir, JCAEXMLData.ref1dFilename);
		trianglesFile = new File(dir, JCAEXMLData.triangles3dFilename);
		groupsFile = new File(dir, JCAEXMLData.groupsFilename);
		
		try
		{
			documentOut = JCAEXMLWriter.createJcaeDocument();
			groupsElement = documentOut.createElement("groups");
		
			nodesOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nodesFile)));
			refsOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(refFile, true)));
			trianglesOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(trianglesFile)));
			groupsOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(groupsFile)));
		}
		catch(FileNotFoundException ex)
		{
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void finalize()
	{
		//  Stores coordinates of boundary nodes
		//  Set nrRefs to its final value after elimination
		//  of duplicates
		nrRefs = offsetBnd;
		logger.debug("Append coordinates of "+nrRefs+" nodes");
		try
		{
			for (int i = 0; i < 3*nrRefs; i++)
				nodesOut.writeDouble(coordRefs[i]);
			nodesOut.close();
			refsOut.close();
			trianglesOut.close();
			groupsOut.close();
			
			// Write jcae3d
			Element jcaeElement = documentOut.getDocumentElement();
			Element meshElement = documentOut.createElement("mesh");
			Element subMeshElement = documentOut.createElement("submesh");
			Element nodesElement = XMLHelper.parseXMLString(documentOut,
				"<nodes>"+
				"<number>"+nrNodes+"</number>"+
				"<file format=\"doublestream\" location=\""+XMLHelper.canonicalize(xmlDir, nodesFile.toString())+"\"/>"+
				"<references>"+
				"<number>"+nrRefs+"</number>"+
				"<file format=\"integerstream\" location=\""+XMLHelper.canonicalize(xmlDir, refFile.toString())+"\"/>"+
				"</references>"+
				"</nodes>");
			subMeshElement.appendChild(nodesElement);
			Element trianglesElement = XMLHelper.parseXMLString(documentOut,
				"<triangles>"+
				"<number>"+nrTriangles+"</number>"+
				"<file format=\"integerstream\" location=\""+XMLHelper.canonicalize(xmlDir, trianglesFile.toString())+"\"/>"+
				"</triangles>");
			subMeshElement.appendChild(trianglesElement);
			subMeshElement.appendChild(groupsElement);
			
			meshElement.appendChild(subMeshElement);
			jcaeElement.appendChild(meshElement);

			XMLHelper.writeXML(documentOut, xmlFile);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Reads a SubMesh2D instance from a file.
	 * @param file The name of the XML file
	 */
	public void convert(String xmlInFile, int groupId, CADFace F)
	{
		Document documentIn;
		try
		{
			documentIn = XMLHelper.parseXML(new File(xmlDir, xmlInFile));
		}
		catch(FileNotFoundException ex)
		{
			return;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		int i;
		CachedXPathAPI xpath = new CachedXPathAPI();
		CADGeomSurface surface = F.getGeomSurface();
		try
		{
			String nodesFile = xpath.selectSingleNode(documentIn,
				"/jcae/mesh/submesh/nodes/file/@location").getNodeValue();
			DataInputStream nodesIn=new DataInputStream(new BufferedInputStream(new FileInputStream(xmlDir+File.separator+nodesFile)));
			String refFile = xpath.selectSingleNode(documentIn,
				"/jcae/mesh/submesh/nodes/references/file/@location").getNodeValue();
			DataInputStream refsIn=new DataInputStream(new BufferedInputStream(new FileInputStream(xmlDir+File.separator+refFile)));
			String trianglesFile = xpath.selectSingleNode(documentIn,
				"/jcae/mesh/submesh/triangles/file/@location").getNodeValue();
			DataInputStream trianglesIn=new DataInputStream(new BufferedInputStream(new FileInputStream(xmlDir+File.separator+trianglesFile)));
			
			Node submeshElement = xpath.selectSingleNode(documentIn,
				"/jcae/mesh/submesh");
			Node submeshNodes = xpath.selectSingleNode(submeshElement, "nodes");
			
			int numberOfReferences = Integer.parseInt(
				xpath.selectSingleNode(submeshNodes, "references/number/text()").getNodeValue());
			int [] refs = new int[numberOfReferences];
			logger.debug("Reading "+numberOfReferences+" references");
			int numberOfNodes = Integer.parseInt(
				xpath.selectSingleNode(submeshNodes, "number/text()").getNodeValue());
			logger.debug("Reading "+numberOfNodes+" nodes");
			//  Interior nodes
			for (i = 0; i < numberOfNodes - numberOfReferences; i++)
			{
				double u = nodesIn.readDouble();
				double v = nodesIn.readDouble();
				double [] p3 = surface.value(u, v);
				for (int j = 0; j < 3; j++)
					nodesOut.writeDouble(p3[j]);
			}
			//  Boundary nodes
			for (i = 0; i < numberOfReferences; i++)
				refs[i] = refsIn.readInt();
			for (i = 0; i < numberOfReferences; i++)
			{
				double u = nodesIn.readDouble();
				double v = nodesIn.readDouble();
				if (!xrefs.contains(refs[i]))
				{
					double [] p3 = surface.value(u, v);
					xrefs.put(refs[i], offsetBnd);
					for (int j = 0; j < 3; j++)
						coordRefs[3*offsetBnd+j] = p3[j];
					offsetBnd++;
					refsOut.writeInt(refs[i]);
				}
			}
			
			Node submeshFaces = xpath.selectSingleNode(submeshElement, "triangles");
			int numberOfFaces = Integer.parseInt(
					xpath.selectSingleNode(submeshFaces, "number/text()").getNodeValue());
			logger.debug("Reading "+numberOfFaces+" faces");
			for (i=0; i < numberOfFaces; i++)
			{
				ind[0] = trianglesIn.readInt();
				ind[1] = trianglesIn.readInt();
				ind[2] = trianglesIn.readInt();
				for (int j = 0; j < 3; j++)
				{
					if (ind[j] < numberOfNodes - numberOfReferences)
						ind[j] += nodeOffset;
					else
						ind[j] = xrefs.get(refs[ind[j] - numberOfNodes + numberOfReferences]);
					trianglesOut.writeInt(ind[j]);
				}
			}
			logger.debug("End reading");
			
			groupsElement.appendChild(XMLHelper.parseXMLString(documentOut,
				"<group id=\""+(groupId-1)+"\">"+
				"<name>"+groupId+"</name>"+
				"<number>"+numberOfFaces+"</number>"+ 
				"<file format=\"integerstream\" location=\""+
				XMLHelper.canonicalize(xmlDir, groupsFile.toString())+"\""+
				" offset=\""+nrTriangles+"\"/></group>"));
			
			nodeOffset += numberOfNodes - numberOfReferences;
			nrTriangles += numberOfFaces;
			nodesIn.close();
			trianglesIn.close();
			refsIn.close();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}

