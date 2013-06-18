/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.filesystems.FileLock;
import org.w3c.dom.Node;
import project.org.jcae.netbeans.of.actions.BGBlockPanel;
import project.org.jcae.netbeans.of.api.Function;
import project.org.jcae.netbeans.of.api.Param;
import project.org.jcae.netbeans.of.api.Property;
import project.org.jcae.netbeans.of.api.SelectionList;
import project.org.jcae.netbeans.of.api.ofProp;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;
import static project.org.jcae.netbeans.of.project.ProjectSHMXmlUtils.getMasterSHMXMLDom;
import static project.org.jcae.netbeans.of.project.ProjectSHMXmlUtils.setSHM;
import static project.org.jcae.netbeans.of.project.ProjectXmlUtils.getSubRegionElement;

/**
 *
 * @author mogargaa65
 */
public class ProjectUtils 
{

    @StaticResource()
    public static final String PROJECT_TEMPLATE = "project/org/jcae/netbeans/of/resources/template/MasterProject.xml";
    
    @StaticResource()
    public static final String PROJECT_BASE_PATCH = "project/org/jcae/netbeans/of/resources/template/MasterBasePatch.xml";
    
    /*
     * Project details generators****************
     */

    public static Collection<String> getRegions(FileObject project)
    {
        Collection<String>  regions = new ArrayList<String>();
        Document dom = ProjectXmlUtils.getXMLDom(project);
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("Region");
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element region = (Element) sName.item(i);
                regions.add(region.getAttribute("name"));
            }
        }
       
        return regions;
    }
    
    public static Collection<String> getSubRegions(String regionName, FileObject project) 
    {
        Collection<String>  sRegions = new ArrayList<String>();
        
        Element theRegion = ProjectXmlUtils.getRegionElement(regionName, project);
        
        if(theRegion!=null)
        {
            NodeList srName = theRegion.getElementsByTagName("SubRegion");
            if(srName!=null)
            {
                for(int i=0; i<srName.getLength();i++)
                {
                    Element sregion = (Element) srName.item(i);
                    if(sregion.getParentNode().isSameNode(theRegion))
                        sRegions.add(sregion.getAttribute("name"));
                }
            }
        }
        return sRegions;
        
    }
    
    public static Collection<String> getSubRegions(String regionName, String sName, FileObject project) 
    {
        Collection<String>  sRegions = new ArrayList<String>();
        
        Element theSubRegion = ProjectXmlUtils.getSubRegionElement(regionName, sName, project);
        
        if(theSubRegion!=null)
        {
            NodeList srName = theSubRegion.getElementsByTagName("SubRegion");
            if(srName!=null)
            {
                for(int i=0; i<srName.getLength();i++)
                {
                    Element sregion = (Element) srName.item(i);
                    if(sregion.getParentNode().isSameNode(theSubRegion))
                        sRegions.add(sregion.getAttribute("name"));
                }
            }
        }
        return sRegions;
        
    }    

    public static Collection<String> getFaceZones(String rName, FileObject project) 
    {
        Collection<String>  fZones = new ArrayList<String>();        
        Element theRegion = ProjectXmlUtils.getRegionElement(rName, project);
        
        if(theRegion!=null)
        {
            NodeList fzName = theRegion.getElementsByTagName("FaceZone");
            if(fzName!=null)
            {
                for(int i=0; i<fzName.getLength();i++)
                {
                    Element fz = (Element) fzName.item(i);
                     fZones.add(fz.getAttribute("name"));
                }
            }
        }
        return fZones;
    }
    
    public static Collection<String> getCellZones(String rName, FileObject project) 
    {
        Collection<String>  cZones = new ArrayList<String>();        
        Element theRegion = ProjectXmlUtils.getRegionElement(rName, project);
        
        if(theRegion!=null)
        {
            NodeList czName = theRegion.getElementsByTagName("CellZone");
            if(czName!=null)
            {
                for(int i=0; i<czName.getLength();i++)
                {
                    Element cz = (Element) czName.item(i);
                     cZones.add(cz.getAttribute("name"));
                }
            }
        }
        return cZones;
    }    

    public static Collection<String> getStitches(String rName, FileObject projectDirectory) 
    {
        Collection<String>  stitches = new ArrayList<String>();        
        Element theRegion = ProjectXmlUtils.getRegionElement(rName, projectDirectory);
        
        if(theRegion!=null)
        {
            NodeList sNames = theRegion.getElementsByTagName("Stitch");
            if(sNames!=null)
            {
                for(int i=0; i<sNames.getLength();i++)
                {
                    Element s = (Element) sNames.item(i);
                     stitches.add(s.getAttribute("name"));
                }
            }
        }
        return stitches;
    }
    
    public static Collection<String> getPatches(String sName, String rName, FileObject project) 
    {
        Collection<String>  patches = new ArrayList<String>();
        
        Element theRegion = ProjectXmlUtils.getSubRegionElement(rName, sName, project);
        
        if(theRegion!=null)
        {
            NodeList pName = theRegion.getElementsByTagName("Patch");
            if(pName!=null)
            {
                for(int i=0; i<pName.getLength();i++)
                {
                    Element p = (Element) pName.item(i);
                     patches.add(p.getAttribute("name"));
                }
            }
        }
        return patches;
    }    
    
    public static Collection<String> getAllPatches(FileObject project) 
    {
        Collection<String>  sPatches = new ArrayList<String>();
        
        NodeList nPatches = ProjectXmlUtils.getAllPatches(project);
        
        if(nPatches!=null)
        {
            for(int i=0; i<nPatches.getLength(); i++)
            {
                sPatches.add( ((Element)nPatches.item(i)).getAttribute("name") );
            }
        }
        return sPatches;
    }  
    
    public static Collection<String> getBGPatches(String sName, String rName, FileObject project) 
    {
        Collection<String>  bgPatches = new ArrayList<String>();
        
        Element theRegion = ProjectXmlUtils.getSubRegionElement(rName, sName, project);
        
        if(theRegion!=null)
        {
            NodeList pName = theRegion.getElementsByTagName("BGPatch");
            if(pName!=null)
            {
                for(int i=0; i<pName.getLength();i++)
                {
                    Element p = (Element) pName.item(i);
                     bgPatches.add(p.getAttribute("name"));
                }
            }
        }
        return bgPatches;
    }

    /*
     * Property sheet generators****************
     */
    public static Sheet.Set createRegionSheetSet(String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getRegionElement(rName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});
        return set;
    }
    
    public static Sheet.Set createSubRegionSheetSet(String sName, String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getSubRegionElement(rName, sName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});
        
        return set;
    }

    public static Sheet.Set createBGPatchSheetSet(String pName, String sName, String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getBGPatchElement(pName, rName, sName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});
        
        return set;
    }

    public static Sheet.Set createPatchSheetSet(String pName, String sName, String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getPatchElement(pName, rName, sName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});

        set.put(new PropertySupport.ReadOnly<String>("BrepLocation", String.class, "BrepLocation", "BrepLocation")
			{	
				public String getValue()
				{
					return rEl.getAttribute("brepLocation") ;
				}
			});
        
        return set;
    }

    public static Sheet.Set createCellZoneSheetSet(String zName, String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getCellZoneElement(zName, rName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});
        
        return set;
    }
    
    public static Sheet.Set createFaceZoneSheetSet(String zName, String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = ProjectXmlUtils.getFaceZoneElement(zName, rName, pr);
        set.put(new PropertySupport.ReadOnly<String>("Name", String.class, "Name", "Name")
			{	
				public String getValue()
				{
					return rEl.getAttribute("name") ;
				}
			});

        set.put(new PropertySupport.ReadOnly<String>("Patch", String.class, "Patch", "Patch")
			{	
				public String getValue()
				{
					return rEl.getAttribute("Patch") ;
				}
			});
        
        return set;
    }
    
    /*
    * Add/Update/Delete Elements****************
    */
    public static boolean addRegionElement(String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("Region");

        Element theRegion = (Element) sName.item(0);
        theRegion.setAttribute("name", regionName);
                
        Element rootElement = ProjectXmlUtils.getRootElement(project);
        
        Document projectXML = rootElement.getOwnerDocument();
        Node theRegionImported = projectXML.importNode(theRegion, true);
        
        rootElement.appendChild(theRegionImported);
        ProjectXmlUtils.removeAll(theRegionImported);
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        ProjectFileUtils.makeDir(project.getPath()+"/"+regionName);
        return true;
    }

    public static boolean addSubRegionElement(String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("SubRegion");

        Element theSubRegion = (Element) sName.item(0);
        theSubRegion.setAttribute("name", subRegionName);
                
        Element regionElement = ProjectXmlUtils.getRegionElement(regionName, project);
        
        Document projectXML = regionElement.getOwnerDocument();
        Node theSubRegionImported = projectXML.importNode(theSubRegion, true);
        
        regionElement.appendChild(theSubRegionImported);
        ProjectXmlUtils.removeAll(theSubRegionImported);
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        ProjectFileUtils.makeDir(project.getPath()+"/"+regionName+"/"+subRegionName);
        return true;
    }    

    public static boolean addPatchElement(String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {
        Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList pName = docEle.getElementsByTagName("Patch");

        Element thePatch = (Element) pName.item(0);
        thePatch.setAttribute("name", patchName);
        thePatch.setAttribute("brepLocation", project.getPath()+"/"+regionName+"/"+subRegionName+"/"+patchName);
                
        Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
        
        Document projectXML = subRegionElement.getOwnerDocument();
        Node thePatchImported = projectXML.importNode(thePatch, true);
        
        subRegionElement.appendChild(thePatchImported);
        ProjectXmlUtils.removeAll(thePatchImported);
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        // Ammend SHM block if exists
        Element theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList SHMBlockName = theSubRegion.getElementsByTagName("SHM");
        
        if(SHMBlockName.getLength()==1)
        {
            Element theSHMBlock = null;
            if(SHMBlockName!=null)
                    theSHMBlock = (Element) SHMBlockName.item(0);
            
            ProjectSHMXmlUtils.addPatchinSHMBlock(patchName, theSHMBlock);
            projectXML = theSHMBlock.getOwnerDocument();
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            source = new DOMSource(projectXML);
            result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
        }
            
        return true;
    }    

    public static boolean addBGPatchElement(String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {
        Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList pName = docEle.getElementsByTagName("BGPatch");

        Element thePatch = (Element) pName.item(0);
        thePatch.setAttribute("name", patchName);
                
        Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
        
        Document projectXML = subRegionElement.getOwnerDocument();
        Node thePatchImported = projectXML.importNode(thePatch, true);
        
        subRegionElement.appendChild(thePatchImported);
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        return true;
    } 
    
    public static void addStitchElement(String stitchName, String pName, String pName0, String rName, FileObject projectDirectory) 
    {
        try {
            Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
            Element docEle = dom.getDocumentElement();
            NodeList sEls = docEle.getElementsByTagName("Stitch");

            Element thePatch = (Element) sEls.item(0);
            thePatch.setAttribute("name", stitchName);
            thePatch.setAttribute("patchName1", pName);
            thePatch.setAttribute("patchName1", pName0);
                    
            Element regionElement = ProjectXmlUtils.getRegionElement(rName, projectDirectory);
            
            Document projectXML = regionElement.getOwnerDocument();
            Node theStitchImported = projectXML.importNode(thePatch, true);
            
            regionElement.appendChild(theStitchImported);
           
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(projectXML);
            StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);                
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public static boolean updateRegionElement(String newRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element regionElement = ProjectXmlUtils.getRegionElement(regionName, project);
        regionElement.setAttribute("name", newRegionName);
        
        Document projectXML = regionElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        File f = new File(project.getPath()+"/"+regionName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.rename(fl, newRegionName, "");
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        return true;
    }        
    
    public static boolean updateSubRegionElement(String newSubRegionName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
        subRegionElement.setAttribute("name", newSubRegionName);
        
        Document projectXML = subRegionElement.getOwnerDocument();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);
        
        File f = new File(project.getPath()+"/"+regionName+"/"+subRegionName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.rename(fl, newSubRegionName, "");
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        // Update SHM element in subRegion
        Element shmEle = ProjectSHMXmlUtils.getSHMBlockElement(regionName, newSubRegionName, project);
        //shmEle.getParentNode().removeChild(shmEle);
        
        // 1. Update the STL file naem
        ProjectSHMXmlUtils.setSHM(newSubRegionName+".stl", shmEle); 
        // 2. Update feature file
        ProjectSHMXmlUtils.setFeaturesFile(newSubRegionName+".eMesh", shmEle); 
        
        //Node shmEleImported = projectXML.importNode(shmEle, true);
        //subRegionElement.appendChild(shmEleImported);
          
        
        projectXML = shmEle.getOwnerDocument();
        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        source = new DOMSource(projectXML);
        result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        return true;
    }    

    public static boolean updatePatchElement(String newPatchName, String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element patchElement = ProjectXmlUtils.getPatchElement(patchName, regionName, subRegionName, project);
        patchElement.setAttribute("name", newPatchName);
        patchElement.setAttribute("brepLocation", project.getPath()+"/"+regionName+"/"+subRegionName+"/"+newPatchName);
           
        Document projectXML = patchElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        File f = new File(project.getPath()+"/"+regionName+"/"+subRegionName+"/"+patchName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.rename(fl, newPatchName, "");
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        return true;
    }    
    
    public static boolean removeRegionElement(String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element regionElement = ProjectXmlUtils.getRegionElement(regionName, project);
        
        Node parent = regionElement.getParentNode();
        parent.removeChild(regionElement);
        
        Document projectXML = regionElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        File f = new File(project.getPath()+"/"+regionName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.delete(fl);
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }        
        return true;
    }      

    public static boolean removeSubRegionElement(String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
        
        Node parent = subRegionElement.getParentNode();
        parent.removeChild(subRegionElement);
        
        Document projectXML = subRegionElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);
        
        File f = new File(project.getPath()+"/"+regionName+"/"+subRegionName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.delete(fl);
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return true;
    }          

    public static boolean removePatchElement(String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException, IOException
    {           
        Element patchElement = ProjectXmlUtils.getPatchElement(patchName, regionName, subRegionName, project);
        
        Node parent = patchElement.getParentNode();
        parent.removeChild(patchElement);
        
        FileObject fo = project.getFileObject("Patches");
        for(FileObject f : fo.getChildren())
        {
            if(f.getName().equalsIgnoreCase(patchName))
            {
                f.delete();
                break;
            }
        }
        
        Document projectXML = patchElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

        File f = new File(project.getPath()+"/"+regionName+"/"+subRegionName+"/"+patchName);
        FileObject rFile = FileUtil.toFileObject(f);

        try {
            FileLock fl = rFile.lock();            
            rFile.delete(fl);
            fl.releaseLock();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return true;
    }          


    public static void removeStitchElement(String sName, String rName, FileObject projectDirectory) 
    {   
        try {
            Element theRegion = ProjectXmlUtils.getRegionElement(rName, projectDirectory);
            
            if(theRegion!=null)
            {
                NodeList sNames = theRegion.getElementsByTagName("Stitch");
                if(sNames!=null)
                {
                    for(int i=0; i<sNames.getLength();i++)
                    {
                        Element s = (Element) sNames.item(i);
                         if(s.getAttribute("name").equalsIgnoreCase(sName))
                         {
                             s.getParentNode().removeChild(s);
                             break;
                         }
                    }
                }
            }
     
            Document projectXML = theRegion.getOwnerDocument();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(projectXML);
            StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
    
    public static void copyPatchElement(PatchNode n, String sName, String rName, FileObject projectDirectory) throws TransformerConfigurationException, TransformerException 
    {
        addPatchElement(n.getpName(), sName, rName, projectDirectory);
        ProjectFileUtils.copyFile(projectDirectory.getPath()+"/"+n.getrName()+"/"+n.getsName()+"/"+n.getpName(), projectDirectory.getPath()+"/"+rName+"/"+sName+"/"+n.getpName());
    }

//    public static String mergePatches(PatchNode pNode0, PatchNode pNode1, FileObject projectDirectory) throws TransformerConfigurationException, TransformerException 
//    {
//        String newName = pNode0.getpName()+"-"+pNode1.getpName(); 
//        
//        String file0 = projectDirectory.getPath()+"/"+pNode0.getrName()+"/"+pNode0.getsName()+"/"+pNode0.getpName();
//        String file1 = projectDirectory.getPath()+"/"+pNode1.getrName()+"/"+pNode1.getsName()+"/"+pNode1.getpName();
//        
//        String fileDest = projectDirectory.getPath()+"/"+pNode0.getrName()+"/"+pNode0.getsName()+"/"+newName;
//        
//        addPatchElement(newName, pNode0.getsName(), pNode0.getsName(), projectDirectory);
//        
//        TopoDS_Shape shape0 = Utilities.readFile(file0);
//        TopoDS_Shape shape1 = Utilities.readFile(file1);
//        BRepAlgoAPI_BooleanOperation bt = new BRepAlgoAPI_Fuse(shape0, shape1);
//        
//        
//        return newName;
//    }

    public static String[] getBasePatches() 
    {
        
        Collection<String> patches = new ArrayList<String>();
                
        //FileObject f = FileUtil.getConfigFile(PROJECT_BASE_PATCH);
        
        Document dom = ProjectXmlUtils.getMasterBasePatchXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("Patch");
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element region = (Element) sName.item(i);
                patches.add(region.getAttribute("type"));
            }
        }
               
        return patches.toArray(new String[patches.size()]);
   }

    public static Collection<Property> getBasePatchTypeProperties(String patchSelected, PatchNode pNode, FileObject projectDirectory) 
    {
        Collection<Property> collProp = new ArrayList<Property>();
        
        Element patchElement = ProjectXmlUtils.getBasePatchTypeElement(patchSelected);
        extractPopertiesFromBasePatchElement(patchElement, collProp, projectDirectory);
        
        return collProp;        
    }
    
    private static void extractPopertiesFromBasePatchElement(Element element, Collection<Property> collProp, FileObject projectDirectory)
    {
        NodeList propNames = element.getElementsByTagName("Property");
        NodeList funcNames = element.getElementsByTagName("Function");

        //System.out.println(propNames.getLength());
        if(propNames!=null)
        {
            for(int i=0; i<propNames.getLength();i++)
            {
                Property p = new Property();
                p.setVal( ( (Element) propNames.item(i)).getAttribute("val") );
                p.setDefVal( ( (Element) propNames.item(i)).getAttribute("defVal") );
                p.setKey(( (Element) propNames.item(i)).getAttribute("key") );
                
                Element prop = (Element) propNames.item(i);
                //System.out.println(prop.toString());
                
                NodeList selectionList = prop.getElementsByTagName("SelectionList");
                
                //System.out.println(selectionList.getLength());
                
                if(selectionList!=null && selectionList.getLength()>=1)
                {
                    
                    Element el = (Element) selectionList.item(0);
                    
                    System.out.println(el.toString());
                    
                    String a = el.getAttribute("source");
                    SelectionList sl = new SelectionList();
                    sl.setSource(( (Element) selectionList.item(0)).getAttribute("source") );
                    sl.setTag(( (Element) selectionList.item(0)).getAttribute("tag") );
                    p.setSl(sl);
                    
                    // Populate list
                    if(sl.getSource().equalsIgnoreCase("Project.xml"))
                    {
                        sl.setList(getElementsByTagName(projectDirectory, sl.getTag() ) );
                    }
                }
                collProp.add(p);
            }
        }   
        
        if(funcNames!=null)
        {
            
        }
    }
    
    public static Collection<String> getElementsByTagName(FileObject project, String tagName) 
    {
        Collection<String>  sPatches = new ArrayList<String>();
        
        NodeList nPatches = ProjectXmlUtils.getAllElementsByTagName(project, tagName);
        
        if(nPatches!=null)
        {
            for(int i=0; i<nPatches.getLength(); i++)
            {
                sPatches.add( ((Element)nPatches.item(i)).getAttribute("name") );
            }
        }
        return sPatches;
    }      

    public static String[] getFieldPatches() 
    {
        
        Collection<String> patches = new ArrayList<String>();
        
        Document dom = ProjectXmlUtils.getMasterFieldPatchXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("FieldPatch");
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element region = (Element) sName.item(i);
                patches.add(region.getAttribute("type"));
            }
        }
               
        return patches.toArray(new String[patches.size()]);
   }    

    public static Collection<ofProp> getFieldPatchTypeProperties(String patchSelected, PatchNode pNode, FileObject projectDirectory) 
    {
        Collection<ofProp> collProp = new ArrayList<ofProp>();
        
        Element patchElement = ProjectXmlUtils.getFieldPatchTypeElement(patchSelected);
        extractPopertiesFromFieldPatchElement(patchElement, collProp, projectDirectory);
        
        return collProp;           
    }

    private static void extractPopertiesFromFieldPatchElement(Element element, Collection<ofProp> collProp, FileObject projectDirectory) 
    {
        NodeList propNames = element.getElementsByTagName("Property");
        NodeList funcNames = element.getElementsByTagName("Function");

        if(propNames!=null)
        {
            for(int i=0; i<propNames.getLength();i++)
            {
                Property p = new Property();
                p.setVal( ( (Element) propNames.item(i)).getAttribute("val") );
                p.setDefVal( ( (Element) propNames.item(i)).getAttribute("defVal") );
                p.setSecondaryVal( ( (Element) propNames.item(i)).getAttribute("secondaryVal") );
                p.setDefSecondaryVal( ( (Element) propNames.item(i)).getAttribute("defaultSecondaryVal") );

                p.setKey(( (Element) propNames.item(i)).getAttribute("key") );
                
                Element prop = (Element) propNames.item(i);               
                NodeList selectionList = prop.getElementsByTagName("SelectionList");
                
                if(selectionList!=null && selectionList.getLength()>=1)
                {
                    
                    Element el = (Element) selectionList.item(0);
                    
                    System.out.println(el.toString());
                    
                    String a = el.getAttribute("source");
                    SelectionList sl = new SelectionList();
                    sl.setSource(( (Element) selectionList.item(0)).getAttribute("source") );
                    sl.setTag(( (Element) selectionList.item(0)).getAttribute("tag") );
                    p.setSl(sl);
                    
                    // Populate list
                    if(sl.getSource().equalsIgnoreCase("Project.xml"))
                    {
                        sl.setList(getElementsByTagName(projectDirectory, sl.getTag() ) );
                    }
                }
                //ofProp pInterface = p;
                collProp.add(p);
            }
        }   
        
        if(funcNames!=null)
        {
            for(int i=0; i<funcNames.getLength();i++)
            {
                Function f = new Function();
                f.setKey(( (Element) funcNames.item(i)).getAttribute("key") );
                f.setSeparator(( (Element) funcNames.item(i)).getAttribute("separator") );
                
                Element func = (Element) funcNames.item(i);
                NodeList paramList = func.getElementsByTagName("Param");
                
                if(paramList!=null)
                {
                    Collection<Param> paramsColl = new ArrayList<Param>();
                    for(int j=0;j<paramList.getLength();j++)
                    {
                        Param param = new Param();
                        param.setKey(( (Element) paramList.item(j)).getAttribute("key") );
                        param.setVal(( (Element) paramList.item(j)).getAttribute("val") );
                        paramsColl.add(param);
                    }
                    f.setParams(paramsColl);
                }
                collProp.add(f);
            }
        }
    }

    public static BGBlockPanel.BGBlock getBlockmeshInSubRegion(String rName, String sName, FileObject projectDirectory)
    {
        BGBlockPanel.BGBlock toReturn = new BGBlockPanel.BGBlock();
        
        try 
        {            
            Element el = ProjectXmlUtils.getBGBlockElement(rName, sName, projectDirectory);
            
            NodeList funcs = el.getElementsByTagName("Function");
            
            Element vertices = null;
            
            Element lBound = null;
            Element uBound = null;
            
            Element resolution = null;
            Element simpleGrading = null;
            
            int a = funcs.getLength();
            for(int i=0; i<funcs.getLength();i++)
            {
                Element tmp = (Element) funcs.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("vertices"))
                {
                    vertices = tmp;
                    NodeList verticesChildren = vertices.getElementsByTagName("Function");
                    for(int j=0; j<verticesChildren.getLength();j++)
                    {
                        Element tmp1 = (Element) verticesChildren.item(j);
                        String id = tmp1.getAttribute("id");
                        if(id.equalsIgnoreCase("0"))
                        {
                            lBound = tmp1;
                        }
                        if(id.equalsIgnoreCase("6"))
                        {
                            uBound = tmp1;
                        }
                    }
                }
                if(name.equalsIgnoreCase("resolution"))
                {
                    resolution = tmp;
                }
                if(name.equalsIgnoreCase("simpleGrading"))
                {
                    simpleGrading = tmp;                   
                }
            }
            
            NodeList params;
            
            params = lBound.getElementsByTagName("Param");
            if(params.getLength()==3)
            {
                String[] s = new String[3];
                for(int i=0; i< params.getLength(); i++)
                {
                    s[i] = ((Element) params.item(i)).getAttribute("val");
                }
                toReturn.setlBounds(s);
            }

            params = uBound.getElementsByTagName("Param");
            if(params.getLength()==3)
            {
                String[] s = new String[3];
                for(int i=0; i< params.getLength(); i++)
                {
                    s[i] = ((Element) params.item(i)).getAttribute("val");
                }
                toReturn.setuBounds(s);
            }
            
            params = resolution.getElementsByTagName("Param");
            if(params.getLength()==3)
            {
                String[] s = new String[3];
                for(int i=0; i< params.getLength(); i++)
                {
                    s[i] = ((Element) params.item(i)).getAttribute("val");
                }
                toReturn.setResolution(s);
            }

            params = simpleGrading.getElementsByTagName("Param");
            if(params.getLength()==3)
            {
                String[] s = new String[3];
                for(int i=0; i< params.getLength(); i++)
                {
                    s[i] = ((Element) params.item(i)).getAttribute("val");
                }
                toReturn.setSimpleGrading(s);
            }            

        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }   
        
        return toReturn;
    }
    
    public static void setBlockMeshInSubRegion(BGBlockPanel.BGBlock bgNewParams, String rName, String sName, FileObject projectDirectory)
    {
        
        String[][] verticesVals = new String[8][3];
        
        double x1 = Double.parseDouble(bgNewParams.getlBounds()[0]);
        double y1 = Double.parseDouble(bgNewParams.getlBounds()[1]);
        double z1 = Double.parseDouble(bgNewParams.getlBounds()[2]);

        double x2 = Double.parseDouble(bgNewParams.getuBounds()[0]);
        double y2 = Double.parseDouble(bgNewParams.getuBounds()[1]);
        double z2 = Double.parseDouble(bgNewParams.getuBounds()[2]);
        
        verticesVals[0] = new String[] {String.valueOf(x1), String.valueOf(y1), String.valueOf(z1) };
        verticesVals[1] = new String[] {String.valueOf(x2), String.valueOf(y1), String.valueOf(z1) };
        verticesVals[2] = new String[] {String.valueOf(x2), String.valueOf(y2), String.valueOf(z1) };
        verticesVals[3] = new String[] {String.valueOf(x1), String.valueOf(y2), String.valueOf(z1) };
        verticesVals[4] = new String[] {String.valueOf(x1), String.valueOf(y1), String.valueOf(z2) };
        verticesVals[5] = new String[] {String.valueOf(x2), String.valueOf(y1), String.valueOf(z2) };
        verticesVals[6] = new String[] {String.valueOf(x2), String.valueOf(y2), String.valueOf(z2) };
        verticesVals[7] = new String[] {String.valueOf(x1), String.valueOf(y2), String.valueOf(z2) };
        
        String[] resVals = bgNewParams.getResolution();
        String[] simpleGrading = bgNewParams.getSimpleGrading();
        
        try {
            Element el = ProjectXmlUtils.getBGBlockElement(rName, sName, projectDirectory);
            NodeList funcs = el.getElementsByTagName("Function");
            
            //int a = funcs.getLength();
            for(int i=0; i<funcs.getLength();i++)
            {
                Element tmp = (Element) funcs.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("vertices"))
                {                    
                    NodeList verticesChildren = tmp.getElementsByTagName("Function");
                    if(verticesChildren.getLength()==8)
                    {
                        for(int j=0; j<verticesChildren.getLength();j++)
                        {
                            Element tmp1 = (Element) verticesChildren.item(j);
                            String id = tmp1.getAttribute("id");
                            int intId = Integer.parseInt(id);
                            
                            NodeList paramList = tmp1.getElementsByTagName("Param");
                            if(paramList.getLength()==3)
                            {
                                for(int k=0;k<paramList.getLength();k++)
                                {
                                    ( (Element) paramList.item(k)).setAttribute("val", verticesVals[intId][k]);
                                }
                            }
                        }
                    }
                }
                if(name.equalsIgnoreCase("resolution"))
                {
                    NodeList paramList = tmp.getElementsByTagName("Param");
                    if(paramList.getLength()==3)
                    {
                        for(int k=0;k<paramList.getLength();k++)
                        {
                            ( (Element) paramList.item(k)).setAttribute("val", resVals[k]);
                        }
                    }
                }
                if(name.equalsIgnoreCase("simpleGrading"))
                {
                    NodeList paramList = tmp.getElementsByTagName("Param");
                    if(paramList.getLength()==3)
                    {
                        for(int k=0;k<paramList.getLength();k++)
                        {
                            ( (Element) paramList.item(k)).setAttribute("val", simpleGrading[k]);
                        }
                    }
                }            

             
            }       
                            
            Document projectXML = el.getOwnerDocument();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(projectXML);
            StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
            
            loadBGPatches(rName, sName, projectDirectory);
            
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
    
    private static void cleanBGPatches(String rName, String sName, FileObject projectDirectory) throws TransformerConfigurationException, TransformerException
    {
        
        Element docEle = ProjectXmlUtils.getSubRegionElement(rName, sName, projectDirectory);
        NodeList pName = docEle.getElementsByTagName("BGPatch");

        for(int i=0;i<pName.getLength();i++)
        {
            pName.item(i).getParentNode().removeChild(pName.item(i));
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(docEle.getOwnerDocument());
        StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);
        
    }
    private static void loadBGPatches(String rName, String sName, FileObject projectDirectory) throws TransformerConfigurationException, TransformerException
    {
        // Create BGPatches

        Element docEle = ProjectXmlUtils.getSubRegionElement(rName, sName, projectDirectory);
        System.out.println(ProjectXmlUtils.nodeToString(docEle));
        NodeList pName = docEle.getElementsByTagName("BGPatch");
        
        if(pName.getLength()!=8)
        {
            cleanBGPatches(rName, sName, projectDirectory);
            NodeList patchFuncs = docEle.getElementsByTagName("Function");
            Element patches = null;
            for(int i=0; i<patchFuncs.getLength();i++)
            {
                patches = (Element) patchFuncs.item(i);
                
                String name = patches.getAttribute("name");
                if(name.equalsIgnoreCase("patches"))
                {
                    System.out.println(ProjectXmlUtils.nodeToString(patches));
                    break;
                }                
            }

           //System.out.println(patches);
            
            NodeList patchList = patches.getElementsByTagName("Function");
            
            for(int i=0; i<patchList.getLength();i++)
            {
                Element el = (Element) patchList.item(i);                
                String elementType = el.getAttribute("type");
                if(elementType.equalsIgnoreCase("patch"))
                {
                    String patchName = el.getAttribute("name");
                    addBGPatchElement(patchName, sName, rName, projectDirectory);                    
                }
            }
        }

    }
    
    public static void mergeSubRegions(String regionName, String SubRegionMergee, String subRegionMergedInto, FileObject projectDirectory  )
    {
        try {
            Element docEle = ProjectXmlUtils.getRegionElement(regionName, projectDirectory);
            NodeList sNames = docEle.getElementsByTagName("SubRegion");
            Element subRegionMergeeElement = null;
            Element subRegionMergedIntoElement = null;
            
            for(int i=0; i<sNames.getLength();i++)
            {
                Element el  = (Element) sNames.item(i);
                if(el.getAttribute("name").equalsIgnoreCase(SubRegionMergee))
                {
                    subRegionMergeeElement = el;
                }
                if(el.getAttribute("name").equalsIgnoreCase(subRegionMergedInto))
                {
                    subRegionMergedIntoElement = el;
                }
            }
            
            subRegionMergeeElement.getParentNode().removeChild(subRegionMergeeElement);
            
            subRegionMergedIntoElement.appendChild(subRegionMergeeElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(docEle.getOwnerDocument());
            StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
    
    public static void unmergeSubRegions(String regionName, String SubRegionMergee, String subRegionMergedInto, FileObject projectDirectory  )
    {
        try {
            Element docEle = ProjectXmlUtils.getRegionElement(regionName, projectDirectory);
            NodeList sNames = docEle.getElementsByTagName("SubRegion");
            Element subRegionMergeeElement = null;
            //Element subRegionMergedIntoElement = null;
            
            for(int i=0; i<sNames.getLength();i++)
            {
                Element el  = (Element) sNames.item(i);
                if(el.getAttribute("name").equalsIgnoreCase(SubRegionMergee))
                {
                    subRegionMergeeElement = el;
                }
            }
            
            subRegionMergeeElement.getParentNode().removeChild(subRegionMergeeElement);
            
            docEle.appendChild(subRegionMergeeElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(docEle.getOwnerDocument());
            StreamResult result = new StreamResult(FileUtil.toFile(projectDirectory.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
}