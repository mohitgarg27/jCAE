/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

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

/**
 *
 * @author mogargaa65
 */
public class ProjectUtils 
{

    @StaticResource()
    public static final String PROJECT_TEMPLATE = "project/org/jcae/netbeans/of/resources/template/MasterProject.xml";
    
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

        return true;
    }    

    public static boolean addPatchElement(String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {
        Document dom = ProjectXmlUtils.getMasterProjectXMLDom();
        Element docEle = dom.getDocumentElement();
        NodeList pName = docEle.getElementsByTagName("Patch");

        Element thePatch = (Element) pName.item(0);
        thePatch.setAttribute("name", patchName);
        thePatch.setAttribute("brepLocation", "Patches/"+patchName);
                
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

        return true;
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

        return true;
    }    

    public static boolean updatePatchElement(String newPatchName, String patchName, String subRegionName, String regionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {           
        Element patchElement = ProjectXmlUtils.getPatchElement(patchName, regionName, subRegionName, project);
        patchElement.setAttribute("name", newPatchName);
        patchElement.setAttribute("brepLocation", "Patches/"+newPatchName);
        
        FileObject fo = project.getFileObject("Patches");
        for(FileObject f : fo.getChildren())
        {
            if(f.getName().equalsIgnoreCase(patchName))
            {
                try {
                    FileLock fl = f.lock();
                    f.rename(fl, newPatchName, "");
                    fl.releaseLock();
                    break;
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                    patchElement.setAttribute("name", patchName);
                    patchElement.setAttribute("brepLocation", "Patches/"+patchName);
                }
            }
        }        
        Document projectXML = patchElement.getOwnerDocument();
       
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(projectXML);
        StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
        transformer.transform(source, result);

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

        return true;
    }          
    
}
