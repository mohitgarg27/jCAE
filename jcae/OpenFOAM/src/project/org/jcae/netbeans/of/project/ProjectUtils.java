/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mogargaa65
 */
public class ProjectUtils {
    
    public static Document getXMLDom(FileObject project)
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
            return dom;
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
    public static Collection<String> getRegions(FileObject project)
    {
        Collection<String>  regions = new ArrayList<String>();
        Document dom = getXMLDom(project);
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
    
    private static Element getRegionElement(String regionName, FileObject project)
    {
        Document dom = getXMLDom(project);
        Element docEle = dom.getDocumentElement();
        NodeList sName = docEle.getElementsByTagName("Region");
        
        Element theRegion = null;
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element region = (Element) sName.item(i);
                if(region.getAttribute("name").equalsIgnoreCase(regionName))
                {
                    theRegion = region;
                    break;                            
                }
            }
        }
        
        return theRegion;
    }

    private static Element getSubRegionElement(String regionName, String subRegionName, FileObject project)
    {
        Element theRegion = getRegionElement(regionName, project);
        NodeList sName = theRegion.getElementsByTagName("SubRegion");
        
        Element theSubRegion = null;
        
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element subRegion = (Element) sName.item(i);
                if(subRegion.getAttribute("name").equalsIgnoreCase(subRegionName))
                {
                    theSubRegion = subRegion;
                    break;                            
                }
            }
        }
        
        return theSubRegion;
    }
    
    private static Element getBGPatchElement(String pName, String regionName, String subRegionName, FileObject project)
    {
        Element theRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList sName = theRegion.getElementsByTagName("BGPatch");
        
        Element theBGPatch = null;
        
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element bgPatch = (Element) sName.item(i);
                if(bgPatch.getAttribute("name").equalsIgnoreCase(pName))
                {
                    theBGPatch = bgPatch;
                    break;                            
                }
            }
        }
        
        return theBGPatch;
    }
    
    private static Element getPatchElement(String pName, String regionName, String subRegionName, FileObject project)
    {
        Element theRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList sName = theRegion.getElementsByTagName("Patch");
        
        Element thePatch = null;
        
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element patch = (Element) sName.item(i);
                if(patch.getAttribute("name").equalsIgnoreCase(pName))
                {
                    thePatch = patch;
                    break;                            
                }
            }
        }
        
        return thePatch;
    }    
    
    private static Element getCellZoneElement(String cName, String regionName, FileObject project)
    {
        Element theRegion = getRegionElement(regionName, project);
        NodeList sName = theRegion.getElementsByTagName("CellZone");
        
        Element theZone = null;
        
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element patch = (Element) sName.item(i);
                if(patch.getAttribute("name").equalsIgnoreCase(cName))
                {
                    theZone = patch;
                    break;                            
                }
            }
        }
        
        return theZone;
    }     
    
    private static Element getFaceZoneElement(String fName, String regionName, FileObject project)
    {
        Element theRegion = getRegionElement(regionName, project);
        NodeList sName = theRegion.getElementsByTagName("FaceZone");
        
        Element theZone = null;
        
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element patch = (Element) sName.item(i);
                if(patch.getAttribute("name").equalsIgnoreCase(fName))
                {
                    theZone = patch;
                    break;                            
                }
            }
        }
        
        return theZone;
    }    
        
    public static Collection<String> getSubRegions(String regionName, FileObject project) 
    {
        Collection<String>  sRegions = new ArrayList<String>();
        
        Element theRegion = getRegionElement(regionName, project);
        
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
        Element theRegion = getRegionElement(rName, project);
        
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
        Element theRegion = getRegionElement(rName, project);
        
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
        
        Element theRegion = getSubRegionElement(rName, sName, project);
        
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
        
        Element theRegion = getSubRegionElement(rName, sName, project);
        
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
    
    public static Sheet.Set createRegionSheetSet(String rName, FileObject pr)
    {
        Sheet.Set set=new Sheet.Set();
        final Element rEl = getRegionElement(rName, pr);
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
        final Element rEl = getSubRegionElement(rName, sName, pr);
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
        final Element rEl = getBGPatchElement(pName, rName, sName, pr);
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
        final Element rEl = getPatchElement(pName, rName, sName, pr);
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
        final Element rEl = getCellZoneElement(zName, rName, pr);
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
        final Element rEl = getFaceZoneElement(zName, rName, pr);
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
    
    public static boolean makeDir(String basePath, String dirName) {        
        
        File dir = new File(basePath+"/"+dirName);
        if(!dir.exists())
        {
            if(!dir.mkdir())
            {                
                return false;
            }
            else
                return true;
        }
        else
        {
            // empty it 
            String[] children = dir.list();
            for(int i=0; i<children.length; i++) 
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success)
                    return false;;

            }                         
            return true;                
        }
    }
    
    public static boolean makeDir(String dirPath) {
        
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            if(!dir.mkdir())
            {
                return false;
            }
            else
                return true;
        }
        else
        {
            // empty it 
            String[] children = dir.list();
            for(int i=0; i<children.length; i++) 
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success)
                    return false;;
            }                         
            return true;                
        }                
    } 
 
    public static boolean deleteDir(File dir) {
        try
        {
            if(!dir.exists())
                return true;
            if(dir.isDirectory()) 
            {
                String[] children = dir.list();
                for(int i=0; i<children.length; i++) 
                {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if(!success)
                        return false;;
                        
                }
            }            
            return dir.delete();
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    
    public static boolean copyFile(String srcPath, String destPath) {
        try
        {
            File f1 = new File(srcPath);
            File f2 = new File(destPath);
            
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);
            
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf)) > 0) 
            {
                out.write(buf, 0, len);               
            }
            in.close();
            out.close();
            
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }   

    public static boolean copyDir(String srcPath, String destPath) 
    {
        try
        {
            File f1 = new File(srcPath);
            if(f1.isDirectory())
            {
                // request for copy dir
                makeDir(destPath);
                
                String[] children = f1.list();
                for(int i=0; i<children.length; i++) 
                {
                    File child = new File(srcPath, children[i]);
                    if(child.isDirectory())
                    {
                        return copyDir(child.getAbsolutePath(), destPath+"/"+child.getName());
                    }
                    else if(child.isFile())
                    {
                        if(!copyFile(child.getAbsolutePath(), destPath+"/"+child.getName()))
                            return false;
                    }
                    
                }
                return true;
            }
            else if(f1.isFile())
            {
                System.out.println("Error: " +srcPath+ " Not a directory. Select a directory");
                return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }       
}
