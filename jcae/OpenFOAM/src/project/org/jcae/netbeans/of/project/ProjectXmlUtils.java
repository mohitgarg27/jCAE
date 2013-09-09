/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
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
public class ProjectXmlUtils 
{
    public static String nodeToString(Node node) 
    {
        StringWriter sw = new StringWriter();
        try 
        {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } 
        catch (TransformerException te) 
        {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
    
    public static void printDocument(FileObject project, OutputStream out) throws IOException, TransformerException 
    {
        Document doc = getXMLDom(project);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), 
             new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }
    
    public static void removeAll(Node node) 
    {
        NodeList nl = node.getChildNodes();
                
        for(; nl.getLength()>0;)
        {
            
            Node n = nl.item(0);
            System.out.println(nodeToString(node));
            if(n.hasChildNodes()) //edit to remove children of children
            {
              removeAll(n);
              node.removeChild(n);
            }
            else
              node.removeChild(n);
        }
    }    
    
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

    public static Document getMasterBasePatchXMLDom()
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //URL u = ProjectUtils.class.getClassLoader().getResource(PROJECT_XML);
            //System.out.println(u.getFile());
            //FileObject f = FileUtil.getConfigFile( u.getFile() );
            String loc = System.getProperty("user.dir")+"/" + "./template/MasterBasePatch.xml";
            //System.out.println(loc);
            
            Document dom = db.parse(new File(loc));
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

    public static Document getMasterFieldPatchXMLDom()
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String loc = System.getProperty("user.dir")+"/" + "./template/MasterFieldPatch.xml";
            //System.out.println(loc);
            
            Document dom = db.parse(new File(loc));
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
  

    
    public static Document getMasterBlockMeshXMLDom()
    {
        try 
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String loc = System.getProperty("user.dir")+"/" + "./template/MasterBlockMesh.xml";
            //System.out.println(loc);
            
            Document dom = db.parse(new File(loc));
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
    
    public static Document getMasterProjectXMLDom()
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //URL u = ProjectUtils.class.getClassLoader().getResource(PROJECT_XML);
            //System.out.println(u.getFile());
            //FileObject f = FileUtil.getConfigFile( u.getFile() );
            String loc = System.getProperty("user.dir")+"/" + "./template/MasterProject.xml";
            
            Document dom = db.parse(new File(loc));
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
       
    public static Element getRootElement( FileObject project)
    {
        Document dom = getXMLDom(project);
        Element docEle = dom.getDocumentElement();
        //NodeList sName = docEle.getElementsByTagName("Project");
         
        //return docEle;
        return (Element) docEle.getElementsByTagName("Project").item(0);

    }    
    
    public static Element getRegionElement(String regionName, FileObject project)
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

    public static Element getSubRegionElement(String regionName, String subRegionName, FileObject project)
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
    
    public static Element getBGPatchesBlockElement(String regionName, String subRegionName, FileObject project)
    {
        Element theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList sName = theSubRegion.getElementsByTagName("BGBlock");
        
        Element theBGBlock = null;
        
        if(sName.getLength()==1)
        {
            theBGBlock = (Element) sName.item(0);
        }
        
        return theBGBlock;
    }
    
    public static Element getBGPatchElement(String pName, String regionName, String subRegionName, FileObject project)
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
    
    public static Element getPatchElement(String pName, String regionName, String subRegionName, FileObject project)
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
    
    public static Element getCellZoneElement(String cName, String regionName, FileObject project)
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
    
    public static Element getFaceZoneElement(String fName, String regionName, FileObject project)
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
    
    public static Element getBGBlockElement( String regionName, String subRegionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {
        Element theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList BMName = theSubRegion.getElementsByTagName("BlockMesh");
        
        if(BMName.getLength()==0) // means no BlockMesh
        {
            Document dom = ProjectXmlUtils.getMasterBlockMeshXMLDom();
            Element docEle = dom.getDocumentElement();
            
            Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
            Document projectXML = subRegionElement.getOwnerDocument();
            Node theBGImported = projectXML.importNode(docEle, true);
            subRegionElement.appendChild(theBGImported);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(projectXML);
            StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
        }

        theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        BMName = theSubRegion.getElementsByTagName("BlockMesh");
        
        Element theBGBlock = null;
        
        if(BMName!=null)
        {
                theBGBlock = (Element) BMName.item(0);
        }
        
        return theBGBlock;
    }    
    
    public static Element getBasePatchTypeElement(String patchType)
    {
        Element patchTypeEl = null;
        
        Document dom = getMasterBasePatchXMLDom();
        Element docEle = dom.getDocumentElement();
        System.out.println(nodeToString(docEle));
        NodeList sName = docEle.getElementsByTagName("BasePatch");
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element patch = (Element) sName.item(i);
                if(patch.getAttribute("type").equalsIgnoreCase(patchType))
                {
                    patchTypeEl = patch;
                    break;
                }
            }
        }
        return patchTypeEl;
    }

    public static NodeList getAllPatches(FileObject project)
    {
        Document dom = getXMLDom(project);
        Element docEle = dom.getDocumentElement();
        
         return docEle.getElementsByTagName("Patch");
    }
    
    public static NodeList getAllPatchesInSubRegion(String region, String subRegion, FileObject project)
    {
        Element subRegionElement = getSubRegionElement(region, subRegion, project);
        return subRegionElement.getElementsByTagName("Patch");
    }
    
    public static NodeList getAllElementsByTagName(FileObject project, String tagName)
    {
        Document dom = getXMLDom(project);
        Element docEle = dom.getDocumentElement();
        
         return docEle.getElementsByTagName(tagName);
    }    
    
    
    public static Element getFieldPatchTypeElement(String patchType)
    {
        Element patchTypeEl = null;
        
        Document dom = getMasterFieldPatchXMLDom();
        Element docEle = dom.getDocumentElement();
        System.out.println(nodeToString(docEle));
        NodeList sName = docEle.getElementsByTagName("FieldPatch");
        if(sName!=null)
        {
            for(int i=0; i<sName.getLength();i++)
            {
                Element patch = (Element) sName.item(i);
                if(patch.getAttribute("type").equalsIgnoreCase(patchType))
                {
                    patchTypeEl = patch;
                    break;
                }
            }
        }
        return patchTypeEl;
    }

    public static Element getPatchBaseTypeElement(String pName, String sName, String rName, FileObject projectDirectory) 
    {
        Element toRet = null;
        Element patchEle = getPatchElement(pName, rName, sName, projectDirectory);
        NodeList nl = patchEle.getElementsByTagName("BasePatch");
        
        if(nl!=null)
        {
            for (int i=0; i<nl.getLength();i++)
            {
                toRet = (Element) nl.item(i);
                break;                
            }
        }
        return toRet;
    
    }

    public static Element getPatchFieldTypeElement(String pName, String sName, String rName, FileObject projectDirectory, String field) 
    {
        Element toRet = null;
        Element patchEle = getPatchElement(pName, rName, sName, projectDirectory);
        NodeList nl = patchEle.getElementsByTagName("FieldPatch");
        
        if(nl!=null)
        {
            for (int i=0; i<nl.getLength();i++)
            {
                toRet = (Element) nl.item(i);
                String fieldType = toRet.getAttribute("fieldFile");
                if(field.equals(fieldType))
                    break;
                else
                    toRet = null;
            }
        }
        return toRet;    
    }
    
}
