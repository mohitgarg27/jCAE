/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import project.org.jcae.netbeans.of.actions.SnappyHexMeshSettingsPanel;
import static project.org.jcae.netbeans.of.project.ProjectXmlUtils.getAllPatchesInSubRegion;
import static project.org.jcae.netbeans.of.project.ProjectXmlUtils.getSubRegionElement;

/**
 *
 * @author mita
 */
public class ProjectSHMXmlUtils 
{
    
    public static Element getSHMBlockElement( String regionName, String subRegionName, FileObject project) throws TransformerConfigurationException, TransformerException
    {
        Element theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        NodeList SHMBlockName = theSubRegion.getElementsByTagName("SHM");
        
        Document docMasterSHM = getMasterSHMXMLDom();
        
        if(SHMBlockName.getLength()==0) // means no BlockMesh
        {
            Document dom = getMasterSHMXMLDom();
            Element docEle = dom.getDocumentElement();
            
            Element subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
            Document projectXML = subRegionElement.getOwnerDocument();
            Node theSHMImported = projectXML.importNode(docEle, true);
            subRegionElement.appendChild(theSHMImported);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(projectXML);
            StreamResult result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
            
            // Set default params
            
            subRegionElement = ProjectXmlUtils.getSubRegionElement(regionName, subRegionName, project);
            
            // 1. Set {SubRegionName.stl}
            NodeList propertiesList= ((Element)subRegionElement).getElementsByTagName("Properties");
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("STL"))
                {
                    tmp.setAttribute("name", subRegionName+".stl");
                    
                    NodeList propertyList= ((Element)tmp).getElementsByTagName("Property");
                    for(int j = 0; j<propertyList.getLength(); j++)
                    {
                        Element tmp1 = (Element) propertyList.item(j);
                        if(tmp1.getAttribute("id").equalsIgnoreCase("subRegionName"))
                        {
                            tmp1.setAttribute("val", subRegionName);
                        }
                    }
                    break;
                }
            }            
                        
            // 2. Add patches
            Element patchEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("GeometryPatch"))
                {
                    // Remove empty patch, but keep the instance for future
                    patchEle = tmp;
                    tmp.getParentNode().removeChild(tmp);
                    break;
                }
            }
            
            Node geometryRegionEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("geometryRegion"))
                {
                    geometryRegionEle = tmp;
                    break;
                }
            }
            
            NodeList patchList = getAllPatchesInSubRegion(regionName, subRegionName, project);
            if(patchEle!=null)
            {
                for(int i=0;i<patchList.getLength(); i++)
                {
                    Element projectPatchEle = (Element) patchList.item(i);
                    patchEle.setAttribute("name", projectPatchEle.getAttribute("name"));
                    
                    NodeList patchEleChildren = patchEle.getElementsByTagName("Property");
                    Element patchEleChild = (Element) patchEleChildren.item(0);
                    patchEleChild.setAttribute("val", projectPatchEle.getAttribute("name"));
                    
                    projectXML = subRegionElement.getOwnerDocument();
                    Node patchEleImported = projectXML.importNode(patchEle, true);
                    geometryRegionEle.appendChild(patchEleImported);                    
                }
            }
            
            // 3. Set featurefile "SubRegionName.eMesh"
            // Find Function ("features")
            Element featuresFunction = null;
            NodeList funcList = subRegionElement.getElementsByTagName("Function");
            for(int i=0; i<funcList.getLength(); i++)
            {
                Element funcEl = (Element)funcList.item(i);
                if(funcEl.getAttribute("name").equalsIgnoreCase("features"))
                {
                    featuresFunction = funcEl;
                    break;
                }
            }
            
            System.out.println(ProjectXmlUtils.nodeToString(featuresFunction));
            NodeList propertyList = featuresFunction.getElementsByTagName("Property");
            for(int i=0; i<propertyList.getLength(); i++)
            {
                Element propEl = (Element)propertyList.item(i);
                System.out.println(propEl.getAttribute("name"));
                if(propEl.getAttribute("id").equalsIgnoreCase("featuresFile"))
                {
                    
                    propEl.setAttribute("file", "\""+ subRegionName+".eMesh\"");
                    propEl.setAttribute("val", "\""+ subRegionName+".eMesh\"");
                    break;
                }
            }

            Element refSurf = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("refinementSurfaces"))
                {
                    refSurf = tmp;
                    NodeList nl = refSurf.getElementsByTagName("Properties");
                    for(int j=0; j<nl.getLength();j++)
                    {
                        Element prEl = (Element) nl.item(j);
                        if(prEl.getAttribute("name").equalsIgnoreCase(""))
                        {
                            prEl.setAttribute("name", subRegionName);
                        }                                        
                    }
                    break;
                }
            }            
            
            patchEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("RefinedPatch"))
                {
                    // Remove empty patch, but keep the instance for future
                    patchEle = tmp;
                    tmp.getParentNode().removeChild(tmp);
                    break;
                }
            }
            
            Element surfaceRegionEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("surfaceRegion"))
                {
                    surfaceRegionEle = tmp;
                    break;
                }
            }
            
            if(patchEle!=null)
            {
                for(int i=0;i<patchList.getLength(); i++)
                {
                    Element projectPatchEle = (Element) patchList.item(i);
                    patchEle.setAttribute("name", projectPatchEle.getAttribute("name"));
                    
                    NodeList patchEleChildren = patchEle.getElementsByTagName("Property");
                    //Element patchEleChild = (Element) patchEleChildren.item(0);
                    //patchEleChild.setAttribute("val", projectPatchEle.getAttribute("name"));
                    
                    Node patchEleImported = projectXML.importNode(patchEle, true);
                    surfaceRegionEle.appendChild(patchEleImported);                    
                }
            }
            
            // 5. Add patches in Layers
            patchEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("layerPatch"))
                {
                    // Remove empty patch, but keep the instance for future
                    patchEle = tmp;
                    tmp.getParentNode().removeChild(tmp);
                    break;
                }
            }
            
            Element layerRegionEle = null;
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("name");
                if(name.equalsIgnoreCase("layers"))
                {
                    layerRegionEle = tmp;
                    break;
                }
            }
            
            if(patchEle!=null)
            {
                for(int i=0;i<patchList.getLength(); i++)
                {
                    Element projectPatchEle = (Element) patchList.item(i);
                    patchEle.setAttribute("name", projectPatchEle.getAttribute("name"));
                    
                    NodeList patchEleChildren = patchEle.getElementsByTagName("Property");
                    //Element patchEleChild = (Element) patchEleChildren.item(0);
                    //patchEleChild.setAttribute("val", projectPatchEle.getAttribute("name"));
                    
                    Node patchEleImported = projectXML.importNode(patchEle, true);
                    layerRegionEle.appendChild(patchEleImported);                    
                }
            }           
                      
            // 6. Remove empty RefineRegions
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("refinedRegion"))
                {
                    tmp.getParentNode().removeChild(tmp);
                    break;
                }
            }
                        
            // 7. Remove empty RefinementRegion element
            for(int i=0; i<propertiesList.getLength();i++)
            {
                Element tmp = (Element) propertiesList.item(i);
                String name = tmp.getAttribute("id");
                if(name.equalsIgnoreCase("refinementRegion"))
                {
                    tmp.getParentNode().removeChild(tmp);
                    break;
                }
                
            }
            
            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer;
            //transformer = transformerFactory.newTransformer();
            System.err.println(ProjectXmlUtils.nodeToString(subRegionElement));
            projectXML = subRegionElement.getOwnerDocument();
            source = new DOMSource(projectXML);
            result = new StreamResult(FileUtil.toFile(project.getFileObject(OFProjectFactory.PROJECT_FILE)));
            transformer.transform(source, result);
            System.err.println(ProjectXmlUtils.nodeToString(subRegionElement));
        }
        
        theSubRegion = getSubRegionElement(regionName, subRegionName, project);
        SHMBlockName = theSubRegion.getElementsByTagName("SHM");
        
        Element theSHMBlock = null;
        if(SHMBlockName!=null)
                theSHMBlock = (Element) SHMBlockName.item(0);
        
        return theSHMBlock;
    }  
    
    public static void addPatchinSHMBlock(String patchName, Element shmEle)
    {
        // Add patches in Geometry tag
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        Document projectXML = null;
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        NodeList propertiesListinSHM= ((Element)shmEle).getElementsByTagName("Properties");
        Element patchEle = null;
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("GeometryPatch"))
            {
                patchEle = tmp;
                break;
            }
        }
            
        Node geometryRegionEle = null;
        for(int i=0; i<propertiesListinSHM.getLength();i++)
        {
            Element tmp = (Element) propertiesListinSHM.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("geometryRegion"))
            {
                geometryRegionEle = tmp;
                break;
            }
        }
            
        if(patchEle!=null)
        {
            patchEle.setAttribute("name", patchName);

            NodeList patchEleChildren = patchEle.getElementsByTagName("Property");
            Element patchEleChild = (Element) patchEleChildren.item(0);
            patchEleChild.setAttribute("val", patchName);

            projectXML = shmEle.getOwnerDocument();
            Node patchEleImported = projectXML.importNode(patchEle, true);
            geometryRegionEle.appendChild(patchEleImported);           
        }
        
        // Add patch in Refinement tags
        patchEle = null;
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("RefinedPatch"))
            {
                patchEle = tmp;
                break;
            }
        }
            
        Element surfaceRegionEle = null;
        for(int i=0; i<propertiesListinSHM.getLength();i++)
        {
            Element tmp = (Element) propertiesListinSHM.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("surfaceRegion"))
            {
                surfaceRegionEle = tmp;
                break;
            }
        }
            
        if(patchEle!=null)
        {
            //Element projectPatchEle = (Element) patchList.item(i);
            patchEle.setAttribute("name", patchName);
            //NodeList patchEleChildren = patchEle.getElementsByTagName("Property");            
            Node patchEleImported = projectXML.importNode(patchEle, true);
            surfaceRegionEle.appendChild(patchEleImported);                                
        }
            
        // Add patches in Layers
        patchEle = null;
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("layerPatch"))
            {
                patchEle = tmp;
                break;
            }
        }
            
        Element layerRegionEle = null;
        for(int i=0; i<propertiesListinSHM.getLength();i++)
        {
            Element tmp = (Element) propertiesListinSHM.item(i);
            String name = tmp.getAttribute("name");
            if(name.equalsIgnoreCase("layers"))
            {
                layerRegionEle = tmp;
                break;
            }
        }
            
        if(patchEle!=null)
        {            
            patchEle.setAttribute("name", patchName);
            Node patchEleImported = projectXML.importNode(patchEle, true);
            layerRegionEle.appendChild(patchEleImported);                                
        }      
        
    }
    
    public static Document getMasterSHMXMLDom()
    {
        try 
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            String loc = System.getProperty("user.dir")+"/" + "./template/MasterSHM.xml";
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
        
    public static void setSHMInSubRegion(String rName, String sName, FileObject projectDirectory, SnappyHexMeshSettingsPanel.SHMParams params)            
    {
        try 
        {
            Element el = getSHMBlockElement(rName, sName, projectDirectory);
            System.out.println(ProjectXmlUtils.nodeToString((Node)el));
            
            setAllowFreeStandingZoneFaces(params.getAllowFreeStandingZoneFaces(), el);
            setMeshQualitySetting(params.getErrorReduction(), el, "ErrorReduction");
            setExpansionRatio(params.getExpansiveRatio(), el);
            setFeatureAngle(params.getFeatureAngle(), el);
            setFeaturesFile(params.getFeaturesFile(), el);
            setFinalLayerThickness(params.getFinalLayerThickness(), el);
            setFeaturesLevel(params.getFeaturesLevel(), el);
            setRefinementSurfaces(params.getListPatches0(), el);
            setLocationInMesh(params.getLocationInMesh(), el);
            setMeshQualitySetting(params.getMaxBoundarySkewness(), el, "MaxBoundarySkewness");
            setMeshQualitySetting(params.getMaxConcave(), el, "MaxConcave");
            setMaxFaceThicknessRatio(params.getMaxFaceThicknessRatio(), el);
            setMeshQualitySetting(params.getMinFlatness(), el, "MinFlatness");
            setMaxGlobalCells(params.getMaxGlobalCells(), el);
            setMeshQualitySetting(params.getMaxInternalSkewness(), el, "MaxInternalSkewness");
            setMaxLoadUnbalance(params.getMaxLoadUnbalance(), el);
            setMaxLocalCells(params.getMaxLocalCells(), el);
            setMeshQualitySetting(params.getMaxNonOrtho(), el, "MaxNonOrtho");
            setMinRefinementCells(params.getMinRefinementCells(), el);
            setMaxThicknessToMedialRatio(params.getMaxThicknessToMedialRatio(), el);
            setGeometryType(params.getMeshType(), el);
            setMeshQualitySetting(params.getMinArea(), el, "MinArea");
            setMeshQualitySetting(params.getMinDeterminant(), el, "MinDeterminant");
            setMeshQualitySetting(params.getMinFaceWeight(), el, "MinFaceWeight");
            setMinMedianAxisAngle(params.getMinMedianAxisAngle(), el);
            setMeshQualitySetting(params.getMinTetQuality(), el, "MinTetQuality");
            setMinThickness(params.getMinThickness(), el);
            setMeshQualitySetting(params.getMinTriangleTwist(), el, "MinTriangleTwist");
            setMeshQualitySetting(params.getMinTwist(), el, "MinTwist");
            setMeshQualitySetting(params.getMinVol(), el, "MinVol");
            setMeshQualitySetting(params.getMinVolRatio(), el, "MinVolRatio");
            setNBufferCellsNoExtrude(params.getNBufferCellsNoExtrude(), el);
            setNCellsBetweenLevels(params.getNCellsBetweenLevels(), el);
            setNFeatureSnapIter(params.getNFeatureSnapIter(), el);
            setNGrow(params.getNGrow(), el);
            setNLayerIter(params.getNLayerIter(), el);
            setNRelaxIter(params.getNRelaxIter(), el);
            setNRelaxIter1(params.getNRelaxIter1(), el);
            setNSmoothNormals(params.getNSmoothNormals(), el);
            setNSmoothPatch(params.getNSmoothPatch(), el);
            setMeshQualitySetting(params.getNSmoothScale(), el, "NSmoothScale");
            setNSmoothSurfaceNormals(params.getNSmoothSurfaceNormals(), el);
            setNSmoothThickness(params.getNSmoothThickness(), el);
            setNSolverIter(params.getNSolveIter(), el);
            setRefinementRegions(params.getRefRegion(), el);
            setRelativeSizes(params.getRelativeSizes(), el);
            setResolveFeatureAngle(params.getResolveFeatureAngle(), el);
            setTolerance(params.getTolerance(), el);
            setSHM(params.getSubRegionName(), el);           
            
            System.out.println(ProjectXmlUtils.nodeToString((Node)el));
            Document projectXML = el.getOwnerDocument();

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
    
    public static SnappyHexMeshSettingsPanel.SHMParams getSHMInSubRegion(String rName, String sName, FileObject projectDirectory)
    {
        SnappyHexMeshSettingsPanel.SHMParams toReturn = new SnappyHexMeshSettingsPanel.SHMParams();
        
        try 
        {            
            Element el = getSHMBlockElement(rName, sName, projectDirectory);
            System.out.println(ProjectXmlUtils.nodeToString((Node)el));
            
            toReturn.setAllowFreeStandingZoneFaces(ProjectSHMXmlUtils.getAllowFreeStandingZoneFaces(el));
            toReturn.setErrorReduction(ProjectSHMXmlUtils.getMeshQualitySetting(el, "ErrorReduction"));
            toReturn.setExpansiveRatio(ProjectSHMXmlUtils.getExpansionRatio(el));
            toReturn.setFeatureAngle(ProjectSHMXmlUtils.getFeatureAngle(el));
            toReturn.setFeaturesFile(ProjectSHMXmlUtils.getFeaturesFile(el));
            toReturn.setFinalLayerThickness(ProjectSHMXmlUtils.getFinalLayerThickness(el));
            toReturn.setFeaturesLevel(ProjectSHMXmlUtils.getFeaturesLevel(el));
            //toReturn.setListPatches(ProjectSHMXmlUtils.getRefinementSurfaces(el));
            toReturn.setListPatches0(ProjectSHMXmlUtils.getRefinementSurfaces(el));
            toReturn.setLocationInMesh(ProjectSHMXmlUtils.getLocationInMesh(el));
            toReturn.setMaxBoundarySkewness(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MaxBoundarySkewness"));
            toReturn.setMaxConcave(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MaxConcave"));
            toReturn.setMaxFaceThicknessRatio(ProjectSHMXmlUtils.getMaxFaceThicknessRatio(el));
            toReturn.setMinFlatness(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinFlatness"));
            toReturn.setMaxGlobalCells(ProjectSHMXmlUtils.getMaxGlobalCells(el));
            toReturn.setMaxInternalSkewness(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MaxInternalSkewness"));
            toReturn.setMaxLoadUnbalance(ProjectSHMXmlUtils.getMaxLoadUnbalance(el));
            toReturn.setMaxLocalCells(ProjectSHMXmlUtils.getMaxLocalCells(el));
            toReturn.setMaxNonOrtho(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MaxNonOrtho"));
            toReturn.setMinRefinementCells(ProjectSHMXmlUtils.getMinRefinementCells(el));
            toReturn.setMaxThicknessToMedialRatio(ProjectSHMXmlUtils.getMaxThicknessToMedialRatio(el));
            toReturn.setMeshType(ProjectSHMXmlUtils.getGeometryType(el));
            toReturn.setMinArea(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinArea"));
            toReturn.setMinDeterminant(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinDeterminant"));
            toReturn.setMinFaceWeight(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinFaceWeight"));
            toReturn.setMinMedianAxisAngle(ProjectSHMXmlUtils.getMinMedianAxisAngle(el));
            toReturn.setMinTetQuality(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinTetQuality"));
            toReturn.setMinThickness(ProjectSHMXmlUtils.getMinThickness(el));
            toReturn.setMinTriangleTwist(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinTriangleTwist"));
            toReturn.setMinTwist(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinTwist"));
            toReturn.setMinVol(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinVol")); //
            toReturn.setMinVolRatio(ProjectSHMXmlUtils.getMeshQualitySetting(el, "MinVolRatio"));
            toReturn.setNBufferCellsNoExtrude(ProjectSHMXmlUtils.getNBufferCellsNoExtrude(el));
            toReturn.setNCellsBetweenLevels(ProjectSHMXmlUtils.getNCellsBetweenLevels(el));
            toReturn.setNFeatureSnapIter(ProjectSHMXmlUtils.getNFeatureSnapIter(el));
            toReturn.setNGrow(ProjectSHMXmlUtils.getNGrow(el));
            toReturn.setNLayerIter(ProjectSHMXmlUtils.getNLayerIter(el));
            toReturn.setNRelaxIter(ProjectSHMXmlUtils.getNRelaxIter(el));
            toReturn.setNRelaxIter1(ProjectSHMXmlUtils.getNRelaxIter1(el));
            toReturn.setNSmoothNormals(ProjectSHMXmlUtils.getNSmoothNormals(el));
            toReturn.setNSmoothPatch(ProjectSHMXmlUtils.getNSmoothPatch(el));
            toReturn.setNSmoothScale(ProjectSHMXmlUtils.getMeshQualitySetting(el, "NSmoothScale"));
            toReturn.setNSmoothSurfaceNormals(ProjectSHMXmlUtils.getNSmoothSurfaceNormals(el));
            toReturn.setNSmoothThickness(ProjectSHMXmlUtils.getNSmoothThickness(el));
            toReturn.setNSolveIter(ProjectSHMXmlUtils.getNSolveIter(el));
            toReturn.setRefRegion(ProjectSHMXmlUtils.getRefinementRegions(el));
            //toReturn.setRefRegion1(ProjectSHMXmlUtils.getRefinementRegions(el));
            toReturn.setRelativeSizes(ProjectSHMXmlUtils.getRelativeSizes(el));
            toReturn.setResolveFeatureAngle(ProjectSHMXmlUtils.getResolveFeatureAngle(el));
            toReturn.setSubRegionName(ProjectSHMXmlUtils.getSHM(el));
            toReturn.setTolerance(ProjectSHMXmlUtils.getTolerance(el));
            
            
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }   
        
        return toReturn;
    }
    
    public static Element getRefinementRegionDefElementInMasterSHM()
    {
        Element toReturn = null;
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("refinementRegion"))
            {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }
    
    public static Element getRefinementRegionModeElementInMasterSHM()
    {
        Element toReturn = null;
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("refinedRegion"))
            {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }
    
    public static Element getPatchDefElementInMasterSHM()
    {
        Element toReturn = null;
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("GeometryPatch"))
            {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }

    public static Element getPatchSurfElementInMasterSHM()
    {
        Element toReturn = null;
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("RefinedPatch"))
            {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }
    
    public static Element getPatchLayerElementInMasterSHM()
    {
        Element toReturn = null;
        Document dom = getMasterSHMXMLDom();
        Element docEle = dom.getDocumentElement();
        
        NodeList propertiesList= ((Element)docEle).getElementsByTagName("Properties");
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase("layerPatch"))
            {
                toReturn = tmp;
                break;
            }
        }
        return toReturn;
    }    
    
    private static Element getElementById(Element shmEle, String id)
    {
        Element stlEle = null;
        NodeList propertiesList= shmEle.getElementsByTagName("Properties");
        NodeList propertyList= shmEle.getElementsByTagName("Property");
        NodeList funcList= shmEle.getElementsByTagName("Function");
        boolean flag = false;
        for(int i=0; i<propertiesList.getLength();i++)
        {
            Element tmp = (Element) propertiesList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase(id))
            {
                stlEle  = tmp;
                flag = true;
                break;
            }
        }
        
        if(!flag)
        for(int i=0; i<propertyList.getLength();i++)
        {
            Element tmp = (Element) propertyList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase(id))
            {
                stlEle  = tmp;
                flag = true;
                break;
            }
        }
        
        if(!flag)
        for(int i=0; i<funcList.getLength();i++)
        {
            Element tmp = (Element) funcList.item(i);
            String name = tmp.getAttribute("id");
            if(name.equalsIgnoreCase(id))
            {
                stlEle  = tmp;
                flag = true;
                break;
            }
        }
        
        return stlEle;
    }
    
    private static Element getElementByNestedIds(Element shmEle, String id, String id2)
    {
        Element ele = getElementById(shmEle, id);
        return getElementById(ele, id2);
    }
        
    public static String getSHM(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "STL");
        toReturn = el.getAttribute("name");
        return toReturn;
    }
    
    public static void setSHM(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "STL");
        el.setAttribute("name", val);
    }
    
    public static String getGeometryType(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "GeometryType");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setGeometryType(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "GeometryType");
        el.setAttribute("val", val);
    }
    
    public static Collection<SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion> getRefinementRegions(Element shmEle)
    {
        Collection<SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion> toReturn = new ArrayList<SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion>();
        
        Element refRegions = getElementById(shmEle, "RefinementRegions");
        NodeList refRegModes = refRegions.getElementsByTagName("Properties");
        HashMap<String, Element> refRegModeEls = new HashMap<String, Element>();
        
        for(int i=0; i<refRegModes.getLength(); i++)
        {
            Element rEl =  (Element) refRegModes.item(i) ;            
            if(rEl.getAttribute("id").equalsIgnoreCase("refinedRegion"))
            {
                String name = rEl.getAttribute("name");
                refRegModeEls.put(name, rEl);
            }
        }
        
        Element geomElement = getElementById(shmEle, "geometry");
        NodeList refRegDefinitions = geomElement.getElementsByTagName("Properties");
        for(int i=0; i<refRegDefinitions.getLength(); i++)
        {
            Element rEl =  (Element) refRegDefinitions.item(i) ;
            if(rEl.getAttribute("id").equalsIgnoreCase("refinementRegion"))
            {
                SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion r = new SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion();
                if(rEl!=null)
                {
                    r.setRefRegionName(rEl.getAttribute("name"));
                    NodeList rElChildren = rEl.getElementsByTagName("Property");
                    
                    for(int j=0;j<rElChildren.getLength();j++)
                    {
                        Element rElChild = (Element) rElChildren.item(j);
                        
                        if(rElChild.getAttribute("name").equalsIgnoreCase("type"))
                            r.setRefRegType(rElChild.getAttribute("val"));
                        if(rElChild.getAttribute("name").equalsIgnoreCase("min"))
                            r.setRefRegMin(rElChild.getAttribute("val"));
                        if(rElChild.getAttribute("name").equalsIgnoreCase("max"))
                            r.setRefRegMax(rElChild.getAttribute("val"));
                    }
                    

                    Element el = refRegModeEls.get(r.getRefRegionName());
                    if(el!=null)
                    {
                        NodeList elChildren = el.getElementsByTagName("Property");
                        for(int j=0;j<elChildren.getLength();j++)
                        {
                            Element rElChild = (Element) elChildren.item(j);

                            if(rElChild.getAttribute("name").equalsIgnoreCase("mode"))
                                r.setRefRefMode(rElChild.getAttribute("val"));
                            if(rElChild.getAttribute("name").equalsIgnoreCase("levels"))
                                r.setRefRefLevels(rElChild.getAttribute("val"));
                        }
                    }

                    toReturn.add(r);
                }
            }
        }
        
        return toReturn;
    }
    
    public static void setRefinementRegions(Collection<SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion> val, Element shmEle)
    {
        //Empty out previous elements of RefinementRegions
        Element geomElement = getElementById(shmEle, "geometry");
        NodeList refRegDefinitions = geomElement.getElementsByTagName("Properties");
        
        System.err.println(refRegDefinitions.getLength());
        
        for(int i=0; i<refRegDefinitions.getLength(); i++)
        {
            Element rEl =  (Element) refRegDefinitions.item(i);
            if (rEl==null)
                break;
            if(rEl.getAttribute("id").equalsIgnoreCase("refinementRegion"))
                rEl.getParentNode().removeChild(rEl);
        }
        
        Element refRegions = getElementById(shmEle, "RefinementRegions");
        NodeList refRegModes = refRegions.getElementsByTagName("Properties");
        
        System.err.println(refRegModes.getLength());
        
        for(int i=0; i<refRegModes.getLength(); )
        {
            Element rEl =  (Element) refRegModes.item(i) ;
            if (rEl==null)
                break;
            if(rEl.getAttribute("id").equalsIgnoreCase("refinedRegion"))
                rEl.getParentNode().removeChild(rEl);
        }      
        
        Element geomEle = getElementById(shmEle, "geometry"); // Where RefrinementRegions definitions are
        refRegions = getElementById(shmEle, "RefinementRegions"); // Where RefrinementRegions modes are
        
        // Add a new child for each of the collection item.
        for(int i=0; i<val.size(); i++)
        {
            SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion r = ((ArrayList<SnappyHexMeshSettingsPanel.SHMParams.RefinedRegion>)val).get(i);
            
            Element refRegDefEle = getRefinementRegionDefElementInMasterSHM();
            Element refRegModeEle = getRefinementRegionModeElementInMasterSHM();
            
            System.out.println(ProjectXmlUtils.nodeToString((Node)refRegDefEle));
            System.out.println(ProjectXmlUtils.nodeToString((Node)refRegModeEle));
            
            refRegDefEle.setAttribute("name", r.getRefRegionName());
            NodeList refRegDefEleProps = refRegDefEle.getElementsByTagName("Property");
            for(int j=0;j<refRegDefEleProps.getLength(); j++)
            {
                Element el = (Element)refRegDefEleProps.item(j);
                if(el.getAttribute("name").equalsIgnoreCase("type"))
                {
                    el.setAttribute("val", r.getRefRegType());
                }
                if(el.getAttribute("name").equalsIgnoreCase("min"))
                {
                    el.setAttribute("val", r.getRefRegMin());
                }
                if(el.getAttribute("name").equalsIgnoreCase("max"))
                {
                    el.setAttribute("val", r.getRefRegMax());
                }                
            }
            
            refRegModeEle.setAttribute("name", r.getRefRegionName());
            NodeList refRegModeEleProps = refRegModeEle.getElementsByTagName("Property");
            for(int j=0;j<refRegModeEleProps.getLength(); j++)
            {
                Element el = (Element)refRegModeEleProps.item(j);
                if(el.getAttribute("name").equalsIgnoreCase("mode"))
                {
                    el.setAttribute("val", r.getRefRefMode());
                }
                if(el.getAttribute("name").equalsIgnoreCase("levels"))
                {
                    el.setAttribute("val", r.getRefRefLevels());
                }                
            }

            System.out.println(ProjectXmlUtils.nodeToString((Node)refRegDefEle));
            System.out.println(ProjectXmlUtils.nodeToString((Node)refRegModeEle));
            
            Document projectXML = shmEle.getOwnerDocument();
            Node refRegDefEleImported = projectXML.importNode(refRegDefEle, true);
            geomEle.appendChild(refRegDefEleImported);
            Node refRegModeEleImported = projectXML.importNode(refRegModeEle, true);
            refRegions.appendChild(refRegModeEleImported);
            
        }
        
    }
    
    public static String getFeaturesFile(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "FeaturesFile");
        toReturn = el.getAttribute("file");
        return toReturn;
    }
    
    public static void setFeaturesFile(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "FeaturesFile");
        el.setAttribute("file", val);
    }    

    public static String getFeaturesLevel(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "FeaturesLevel");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setFeaturesLevel(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "FeaturesLevel");
        el.setAttribute("val", val);
    }    

    public static String getResolveFeatureAngle(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "ResolveFeatureAngle");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setResolveFeatureAngle(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "ResolveFeatureAngle");
        el.setAttribute("val", val);
    }       
    
    public static String getLocationInMesh(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "LocationInMesh");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setLocationInMesh(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "LocationInMesh");
        el.setAttribute("val", val);
    }       

    public static String getAllowFreeStandingZoneFaces(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "AllowFreeStandingZoneFaces");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setAllowFreeStandingZoneFaces(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "AllowFreeStandingZoneFaces");
        el.setAttribute("val", val);
    }        
    
    public static String getMaxLocalCells(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MaxLocalCells");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMaxLocalCells(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MaxLocalCells");
        el.setAttribute("val", val);
    }        
    
    public static String getMaxGlobalCells(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MaxGlobalCells");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMaxGlobalCells(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MaxGlobalCells");
        el.setAttribute("val", val);
    }            

    public static String getMinRefinementCells(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MinRefinementCells");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMinRefinementCells(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MinRefinementCells");
        el.setAttribute("val", val);
    }  

    public static String getMaxLoadUnbalance(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MaxLoadUnbalance");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMaxLoadUnbalance(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MaxLoadUnbalance");
        el.setAttribute("val", val);
    }      
    
    public static String getNCellsBetweenLevels(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NCellsBetweenLevels");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNCellsBetweenLevels(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NCellsBetweenLevels");
        el.setAttribute("val", val);
    }          
    
    // TODO
    public static Collection<SnappyHexMeshSettingsPanel.SHMParams.Patches> getRefinementSurfaces(Element shmEle)
    {
        Collection<SnappyHexMeshSettingsPanel.SHMParams.Patches> toReturn = new ArrayList<SnappyHexMeshSettingsPanel.SHMParams.Patches>();
        
        Element surfRegions = getElementById(shmEle, "surfaceRegion");
        NodeList patchSurfs = surfRegions.getElementsByTagName("Properties");
        HashMap<String, Element> patchSurfEls = new HashMap<String, Element>();
        
        for(int i=0; i<patchSurfs.getLength(); i++)
        {
            Element rEl =  (Element) patchSurfs.item(i);
            if(rEl.getAttribute("id").equalsIgnoreCase("RefinedPatch"))
            {
                String name = rEl.getAttribute("name");
                patchSurfEls.put(name, rEl);
            }
        }

        Element layersRegions = getElementById(shmEle, "layers");
        NodeList patchLayers = layersRegions.getElementsByTagName("Properties");
        HashMap<String, Element> patchLayerEls = new HashMap<String, Element>();
        
        for(int i=0; i<patchLayers.getLength(); i++)
        {
            Element rEl =  (Element) patchLayers.item(i);
            if(rEl.getAttribute("id").equalsIgnoreCase("layerPatch"))
            {
                String name = rEl.getAttribute("name");
                patchLayerEls.put(name, rEl);
            }
        }
                
        Element geometryRegion = getElementById(shmEle, "GeometryRegion");
        NodeList patchDefinitions = geometryRegion.getElementsByTagName("Properties");
        
        for(int i=0; i<patchDefinitions.getLength(); i++)
        {
            Element rEl =  (Element) patchDefinitions.item(i) ;
            if(rEl.getAttribute("id").equalsIgnoreCase("GeometryPatch"))
            {
                if(rEl!=null)
                {
                    SnappyHexMeshSettingsPanel.SHMParams.Patches r = new SnappyHexMeshSettingsPanel.SHMParams.Patches();
                
                    r.setPatchName(rEl.getAttribute("name"));
                    
                    Element el = patchSurfEls.get(r.getPatchName());
                    if(el!=null)
                    {
                        NodeList rlChild = el.getElementsByTagName("Property");
                        Element eChild = (Element)rlChild.item(0);
                        if(eChild.getAttribute("name").equalsIgnoreCase("level"))
                            r.setPatchLevels(eChild.getAttribute("val"));
                    }
                    
                    Element el2 = patchLayerEls.get(r.getPatchName());
                    if(el2!=null)
                    {
                        NodeList rlChild = el2.getElementsByTagName("Property");
                        Element eChild = (Element)rlChild.item(0);
                        if(eChild.getAttribute("name").equalsIgnoreCase("nSurfaceLayers"))
                            r.setNSurfaceLayers(eChild.getAttribute("val"));
                    }
                    toReturn.add(r);
                }                
            }                
        }
        
        return toReturn;
        
    }
    
    public static void setRefinementSurfaces(Collection<SnappyHexMeshSettingsPanel.SHMParams.Patches> val, Element shmEle)
    {
        //Empty out previous elements of RefinementRegions
        Element geomElement = getElementById(shmEle, "geometryRegion");
        NodeList patchDefinitions = geomElement.getElementsByTagName("Properties");
        for(int i=0; i<=patchDefinitions.getLength(); )
        {
            Element rEl =  (Element) patchDefinitions.item(i);
            if(rEl==null)
                break;
            if(rEl.getAttribute("id").equalsIgnoreCase("geometryPatch"))
                rEl.getParentNode().removeChild(rEl);
        }
        
        Element surfRegions = getElementById(shmEle, "SurfaceRegion");
        NodeList patchSurfs = surfRegions.getElementsByTagName("Properties");
        for(int i=0; i<=patchSurfs.getLength(); )
        {
            Element rEl =  (Element) patchSurfs.item(i) ;            
            if(rEl==null)
                break;
            if(rEl.getAttribute("id").equalsIgnoreCase("RefinedPatch"))
                rEl.getParentNode().removeChild(rEl);
        }      
        
        Element layerRegions = getElementById(shmEle, "layers");
        NodeList patchLayer = layerRegions.getElementsByTagName("Properties");
        for(int i=0; i<=patchLayer.getLength(); )
        {
            Element rEl =  (Element) patchLayer.item(i) ;            
            if(rEl==null)
                break;
            if(rEl.getAttribute("id").equalsIgnoreCase("layerPatch"))
                rEl.getParentNode().removeChild(rEl);
        }      
        
        geomElement = getElementById(shmEle, "geometryRegion"); // Where RefrinementRegions definitions are
        surfRegions = getElementById(shmEle, "SurfaceRegion"); // Where RefrinementRegions modes are
        layerRegions = getElementById(shmEle, "layers");
        
        // Add a new child for each of the collection item.
        for(int i=0; i<val.size(); i++)
        {
            SnappyHexMeshSettingsPanel.SHMParams.Patches r = ((ArrayList<SnappyHexMeshSettingsPanel.SHMParams.Patches>)val).get(i);
            
            Element PatchDefEle = getPatchDefElementInMasterSHM();
            Element PatchSurfEle = getPatchSurfElementInMasterSHM();
            Element PatchLayerEle = getPatchLayerElementInMasterSHM();
            
            System.out.println(ProjectXmlUtils.nodeToString((Node)PatchDefEle));
            System.out.println(ProjectXmlUtils.nodeToString((Node)PatchSurfEle));
            System.out.println(ProjectXmlUtils.nodeToString((Node)PatchLayerEle));
            
            PatchDefEle.setAttribute("name", r.getPatchName());
            NodeList patchDefEleProps = PatchDefEle.getElementsByTagName("Property");
            for(int j=0;j<patchDefEleProps.getLength(); j++)
            {
                Element el = (Element)patchDefEleProps.item(j);
                if(el.getAttribute("name").equalsIgnoreCase("name"))
                    el.setAttribute("val", r.getPatchName());
            }
            
            PatchSurfEle.setAttribute("name", r.getPatchName());
            NodeList patchSurfEleProps = PatchSurfEle.getElementsByTagName("Property");
            for(int j=0;j<patchSurfEleProps.getLength(); j++)
            {
                Element el = (Element) patchSurfEleProps.item(j);
                if(el.getAttribute("name").equalsIgnoreCase("level"))
                    el.setAttribute("val", r.getPatchLevels());
            }
                                   
            PatchLayerEle.setAttribute("name", r.getPatchName());
            NodeList patchLayerEleProps = PatchLayerEle.getElementsByTagName("Property");
            for(int j=0;j<patchLayerEleProps.getLength(); j++)
            {
                Element el = (Element) patchLayerEleProps.item(j);
                if(el.getAttribute("name").equalsIgnoreCase("nSurfaceLayers"))
                    el.setAttribute("val", r.getNSurfaceLayers());
            }
            
            Document projectXML = shmEle.getOwnerDocument();
            Node patchDefEleImported = projectXML.importNode(PatchDefEle, true);
            geomElement.appendChild(patchDefEleImported);
            Node patchSurfEleImported = projectXML.importNode(PatchSurfEle, true);
            surfRegions.appendChild(patchSurfEleImported);
            Node patchLayerEleImported = projectXML.importNode(PatchLayerEle, true);
            layerRegions.appendChild(patchLayerEleImported);
            
        }
        
    }       
    
    public static String getNSmoothPatch(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NSmoothPatch");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNSmoothPatch(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NSmoothPatch");
        el.setAttribute("val", val);
    }           
    
    public static String getTolerance(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "Tolerance");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setTolerance(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "Tolerance");
        el.setAttribute("val", val);
    }      

    public static String getNSolveIter(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NSolveIter");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNSolverIter(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NSolveIter");
        el.setAttribute("val", val);
    }    
    
    public static String getNRelaxIter(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NRelaxIter");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNRelaxIter(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NRelaxIter");
        el.setAttribute("val", val);
    }     
    
    public static String getNFeatureSnapIter(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NFeatureSnapIter");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNFeatureSnapIter(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NFeatureSnapIter");
        el.setAttribute("val", val);
    }         
    
    public static String getRelativeSizes(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "RelativeSizes");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setRelativeSizes(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "RelativeSizes");
        el.setAttribute("val", val);
    }         
    
    public static String getExpansionRatio(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "ExpansionRatio");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setExpansionRatio(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "ExpansionRatio");
        el.setAttribute("val", val);
    }      
    
    public static String getFinalLayerThickness(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "FinalLayerThickness");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setFinalLayerThickness(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "FinalLayerThickness");
        el.setAttribute("val", val);
    }         
    
    public static String getMinThickness(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MinThickness");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMinThickness(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MinThickness");
        el.setAttribute("val", val);
    }        
    
    public static String getNGrow(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NGrow");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNGrow(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NGrow");
        el.setAttribute("val", val);
    }        
    
    public static String getFeatureAngle(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "FeatureAngle");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setFeatureAngle(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "FeatureAngle");
        el.setAttribute("val", val);
    }      
    
    public static String getNRelaxIter1(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NRelaxIter1");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNRelaxIter1(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NRelaxIter1");
        el.setAttribute("val", val);
    }     
    
    public static String getNSmoothSurfaceNormals(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NSmoothSurfaceNormals");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNSmoothSurfaceNormals(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NSmoothSurfaceNormals");
        el.setAttribute("val", val);
    }     

    public static String getNSmoothNormals(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NSmoothNormals");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNSmoothNormals(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NSmoothNormals");
        el.setAttribute("val", val);
    }     

    public static String getNSmoothThickness(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NSmoothThickness");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNSmoothThickness(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NSmoothThickness");
        el.setAttribute("val", val);
    }     
    
    public static String getMaxFaceThicknessRatio(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MaxFaceThicknessRatio");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMaxFaceThicknessRatio(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MaxFaceThicknessRatio");
        el.setAttribute("val", val);
    }         
    
    public static String getMaxThicknessToMedialRatio(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MaxThicknessToMedialRatio");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMaxThicknessToMedialRatio(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MaxThicknessToMedialRatio");
        el.setAttribute("val", val);
    }             
    
    public static String getMinMedianAxisAngle(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "MinMedianAxisAngle");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setMinMedianAxisAngle(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "MinMedianAxisAngle");
        el.setAttribute("val", val);
    }            

    public static String getNBufferCellsNoExtrude(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NBufferCellsNoExtrude");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNBufferCellsNoExtrude(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NBufferCellsNoExtrude");
        el.setAttribute("val", val);
    }            
    
    public static String getNLayerIter(Element shmEle)
    {
        String toReturn = null;
        Element el = getElementById(shmEle, "NLayerIter");
        toReturn = el.getAttribute("val");
        return toReturn;
    }
    
    public static void setNLayerIter(String val, Element shmEle)
    {
        Element el = getElementById(shmEle, "NLayerIter");
        el.setAttribute("val", val);
    }            
    
    // TODO:to here the String[3] starts
    public static String[] getMeshQualitySetting(Element shmEle, String id)
    {
        String[] toReturn = new String[3];
        
        Element el0 = getElementByNestedIds(shmEle, "castellatedMesh", id );
        if(el0!=null)
            toReturn[0] = el0.getAttribute("val");

        Element el1 = getElementByNestedIds(shmEle, "castellatedMesh", id);
        if(el1!=null)
            toReturn[1] = el1.getAttribute("val");

        Element el2 = getElementByNestedIds(shmEle, "castellatedMesh", id);
        if(el2!=null)
            toReturn[2] = el2.getAttribute("val");
        
        return toReturn;
    }
    
    public static void setMeshQualitySetting(String[] val, Element shmEle, String id)
    {
        Element el0 = getElementByNestedIds(shmEle, "castellatedMesh", id );
        if(el0!=null)
            el0.setAttribute("val",val[0]);

        Element el1 = getElementByNestedIds(shmEle, "snap", id);
        if(el1!=null)
            el1.setAttribute("val",val[1]);

        Element el2 = getElementByNestedIds(shmEle, "addLayers", id);
        if(el2!=null)
            el2.setAttribute("val",val[2]);
        
    }           
    
}
