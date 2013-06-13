/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.w3c.dom.Element;
import project.org.jcae.netbeans.of.project.ProjectSHMXmlUtils;

/**
 *
 * @author mohit
 */

public class SnappyHexMeshSettingsPanel extends javax.swing.JPanel {

    /**
     * Creates new form SnappyHexMeshSettingsPanel
     */
    String regionName;
    String subRegionName;
    Project project;
    
    SHMParams params;
    
    public SnappyHexMeshSettingsPanel(String rName, String sName, Project pr) 
    {
        initComponents();
        this.regionName = rName;
        this.subRegionName = sName;
        this.project = pr;

        params = ProjectSHMXmlUtils.getSHMInSubRegion(rName, sName, pr.getProjectDirectory());
        
    }
    
    private static <T> DefaultListModel populateListModel(Collection<T> patches)
    {
        DefaultListModel model = new DefaultListModel();
        for(int i=0; i<patches.size(); i++)
        {
            model.add(i, ((ArrayList<T>)patches).get(i) );
        }
        return model;
        
    }
        
    public SHMParams loadParams() // Reverse of loadPanel()
    {
        //1. General settings
        
        params.setSubRegionName(jlSubRegionSTL.getText());
        params.setMeshType(jlMeshType.getText());
        //jListRefRegion1.setModel(populateListModel(params.getRefRegion()) ); // TODO
        
        // 2. Castellated
        params.setFeaturesFile(jLFeaturesFile.getText());
        params.setFeaturesLevel(jLLevel.getText());
        params.setResolveFeatureAngle(jTFResolveFeatureAngles.getText());
        params.setLocationInMesh(jTFLocationInMesh.getText());
        params.setAllowFreeStandingZoneFaces(jTFAllowFreeStandingZoneFaces.getText());
        params.setMaxLocalCells(jTFMaxLocalCells.getText());
        params.setMaxGlobalCells(jTFMaxGlobalCells.getText());
        params.setMinRefinementCells(jTFMinRefinementCells.getText());
        params.setMaxLoadUnbalance(jTFMaxLoadUnbalance.getText());
        params.setNCellsBetweenLevels(jTFNCellsBetweenLevels.getText());
        //jListPatches0.setModel(populateListModel( params.setListPatches0())); // TODO
        //jListRefRegions.setModel(populateListModel(params.setRefRegion())); // TODO
        
        // 3. Snapped
        params.setNSmoothPatch(jTFNSmoothPatch.getText());
        params.setTolerance(jTFTolerance.getText());
        params.setNSolveIter(jTFNSolveIter.getText());
        params.setNRelaxIter(jTFNRelaxIter.getText());
        params.setNFeatureSnapIter(jTFNFeatureSnapIter.getText());
        
        // 4. Add layer
        params.setRelativeSizes(jTFRelativeSizes.getText());
        params.setExpansiveRatio(jTFExpansionRatio.getText());
        params.setFinalLayerThickness(jTFFinalLayerThickness.getText());
        params.setMinThickness(jTFMinThickness.getText());
        params.setNGrow(jTFNGrow.getText());
        params.setFeatureAngle(jTFFeatureAngle.getText());
        params.setNRelaxIter1(jTFNRelaxIter1.getText());
        params.setNSmoothSurfaceNormals(jTFNSmoothSurfaceNormals.getText());
        params.setNSmoothNormals(jTFNSmoothNormals.getText());
        params.setNSmoothThickness(jTFNSmoothThickness.getText());
        params.setMaxFaceThicknessRatio(jTFMaxFaceThicknessRatio.getText());
        params.setMaxThicknessToMedialRatio(jTFMaxThicknessTMedialRatio.getText());
        params.setMinMedianAxisAngle(jTFMinMedianAxisAngle.getText());
        params.setNBufferCellsNoExtrude(jTFNBufferCellsNoExtrude.getText());
        params.setNLayerIter(jTFNLayerIter.getText());
        //jListPatches.setModel(populateListModel( params.setListPatches0())); // TODO
        
        // 5. Mesh quality
        params.getMaxNonOrtho()[0] = jTFCMaxNonOrtho.getText();
        params.getMaxBoundarySkewness()[0] = jTFCMaxBoundarySkewness.getText();
        params.getMaxInternalSkewness()[0] = jTFCMaxInternalSkewness.getText();
        params.getMaxConcave()[0] = jTFCMaxConcave.getText();
        params.getMinFlatness()[0] = jTFCMaxFlatness.getText();
        params.getMinVol()[0] = jTFCMinVol.getText();
        params.getMinTetQuality()[0] = jTFCMinTetQuality.getText();
        params.getMinTetQuality()[0] = jTFCMinArea.getText();
        params.getMinTwist()[0] = jTFCMinTwist.getText();
        params.getMinDeterminant()[0] = jTFCMinDeterminant.getText();
        params.getMinFaceWeight()[0] = jTFCMinFaceWeight.getText();
        params.getMinVolRatio()[0] = jTFCMinVolRatio.getText();
        params.getMinTriangleTwist()[0] = jTFCMinTriangleTwist.getText();
        params.getNSmoothScale()[0] = jTFCNSmoothScale.getText();
        params.getErrorReduction()[0] = jTFCErrorReduction.getText();

        params.getMaxNonOrtho()[1] = jTFSMaxNonOrtho.getText();
        params.getMaxBoundarySkewness()[1] = jTFSMaxBoundarySkewness.getText();
        params.getMaxInternalSkewness()[1] = jTFSMaxInternalSkewness.getText();
        params.getMaxConcave()[1] = jTFSMaxConcave.getText();
        params.getMinFlatness()[1] = jTFSMaxFlatness.getText();
        params.getMinVol()[1] = jTFSMinVol.getText();
        params.getMinTetQuality()[1] = jTFSMinTetQuality.getText();
        params.getMinTetQuality()[1] = jTFSMinArea.getText();
        params.getMinTwist()[1] = jTFSMinTwist.getText();
        params.getMinDeterminant()[1] = jTFSMinDeterminant.getText();
        params.getMinFaceWeight()[1] = jTFSMinFaceWeight.getText();
        params.getMinVolRatio()[1] = jTFSMinVolRatio.getText();
        params.getMinTriangleTwist()[1] = jTFSMinTriangleTwist.getText();
        params.getNSmoothScale()[1] = jTFSNSmoothScale.getText();
        params.getErrorReduction()[1] = jTFSErrorReduction.getText();

        params.getMaxNonOrtho()[2] = jTFLMaxNonOrtho.getText();
        params.getMaxBoundarySkewness()[2] = jTFLMaxBoundarySkewness.getText();
        params.getMaxInternalSkewness()[2] = jTFLMaxInternalSkewness.getText();
        params.getMaxConcave()[2] = jTFLMaxConcave.getText();
        params.getMinFlatness()[2] = jTFLMaxFlatness.getText();
        params.getMinVol()[2] = jTFLMinVol.getText();
        params.getMinTetQuality()[2] = jTFLMinTetQuality.getText();
        params.getMinTetQuality()[2] = jTFLMinArea.getText();
        params.getMinTwist()[2] = jTFLMinTwist.getText();
        params.getMinDeterminant()[2] = jTFLMinDeterminant.getText();
        params.getMinFaceWeight()[2] = jTFLMinFaceWeight.getText();
        params.getMinVolRatio()[2] = jTFLMinVolRatio.getText();
        params.getMinTriangleTwist()[2] = jTFLMinTriangleTwist.getText();
        params.getNSmoothScale()[2] = jTFLNSmoothScale.getText();
        params.getErrorReduction()[2] = jTFLErrorReduction.getText();
        
        return params;
    }
    
    public void loadPanel() 
    {
        //1. General settings
        jlSubRegionSTL.setText(params.getSubRegionName());
        jlMeshType.setText(params.getMeshType());
        jListRefRegion1.setModel(populateListModel(params.getRefRegion()) );
        
        // 2. Castellated
        jLFeaturesFile.setText(params.getFeaturesFile());
        jLLevel.setText(params.getFeaturesLevel());
        jTFResolveFeatureAngles.setText(params.getResolveFeatureAngle());
        jTFLocationInMesh.setText(params.getLocationInMesh());
        jTFAllowFreeStandingZoneFaces.setText(params.getAllowFreeStandingZoneFaces());
        jTFMaxLocalCells.setText(params.getMaxLocalCells());
        jTFMaxGlobalCells.setText(params.getMaxGlobalCells());
        jTFMinRefinementCells.setText(params.getMinRefinementCells());
        jTFMaxLoadUnbalance.setText(params.getMaxLoadUnbalance());
        jTFNCellsBetweenLevels.setText(params.getNCellsBetweenLevels());
        jListPatches0.setModel(populateListModel( params.getListPatches0()));
        jListRefRegion.setModel(populateListModel(params.getRefRegion()));
        
        // 3. Snapped
        jTFNSmoothPatch.setText(params.getNSmoothPatch());
        jTFTolerance.setText(params.getTolerance());
        jTFNSolveIter.setText(params.getNSolveIter());
        jTFNRelaxIter.setText(params.getNRelaxIter());
        jTFNFeatureSnapIter.setText(params.getNFeatureSnapIter());
        
        // 4. Add layer
        jTFRelativeSizes.setText(params.getRelativeSizes());
        jTFExpansionRatio.setText(params.getExpansiveRatio());
        jTFFinalLayerThickness.setText(params.getFinalLayerThickness());
        jTFMinThickness.setText(params.getMinThickness());
        jTFNGrow.setText(params.getNGrow());
        jTFFeatureAngle.setText(params.getFeatureAngle());
        jTFNRelaxIter1.setText(params.getNRelaxIter1());
        jTFNSmoothSurfaceNormals.setText(params.getNSmoothSurfaceNormals());
        jTFNSmoothNormals.setText(params.getNSmoothNormals());
        jTFNSmoothThickness.setText(params.getNSmoothThickness());
        jTFMaxFaceThicknessRatio.setText(params.getMaxFaceThicknessRatio());
        jTFMaxThicknessTMedialRatio.setText(params.getMaxThicknessToMedialRatio());
        jTFMinMedianAxisAngle.setText(params.MinMedianAxisAngle);
        jTFNBufferCellsNoExtrude.setText(params.NBufferCellsNoExtrude);
        jTFNLayerIter.setText(params.getNLayerIter());
        jListPatches.setModel(populateListModel( params.getListPatches0()));
        
        // 5. Mesh quality
        jTFCMaxNonOrtho.setText(params.getMaxNonOrtho()[0]);
        jTFCMaxBoundarySkewness.setText(params.getMaxBoundarySkewness()[0]);
        jTFCMaxInternalSkewness.setText(params.getMaxInternalSkewness()[0]);
        jTFCMaxConcave.setText(params.getMaxConcave()[0]);
        jTFCMaxFlatness.setText(params.getMinFlatness()[0]);
        jTFCMinVol.setText(params.getMinVol()[0]);
        jTFCMinTetQuality.setText(params.getMinTetQuality()[0]);
        jTFCMinArea.setText(params.getMinTetQuality()[0]);
        jTFCMinTwist.setText(params.getMinTwist()[0]);
        jTFCMinDeterminant.setText(params.getMinDeterminant()[0]);
        jTFCMinFaceWeight.setText(params.getMinFaceWeight()[0]);
        jTFCMinVolRatio.setText(params.getMinVolRatio()[0]);
        jTFCMinTriangleTwist.setText(params.getMinTriangleTwist()[0]);
        jTFCNSmoothScale.setText(params.getNSmoothScale()[0]);
        jTFCErrorReduction.setText(params.getErrorReduction()[0]);
        
        jTFSMaxNonOrtho.setText(params.getMaxNonOrtho()[1]);
        jTFSMaxBoundarySkewness.setText(params.getMaxBoundarySkewness()[1]);
        jTFSMaxInternalSkewness.setText(params.getMaxInternalSkewness()[1]);
        jTFSMaxConcave.setText(params.getMaxConcave()[1]);
        jTFSMaxFlatness.setText(params.getMinFlatness()[1]);
        jTFSMinVol.setText(params.getMinVol()[1]);
        jTFSMinTetQuality.setText(params.getMinTetQuality()[1]);
        jTFSMinArea.setText(params.getMinTetQuality()[1]);
        jTFSMinTwist.setText(params.getMinTwist()[1]);
        jTFSMinDeterminant.setText(params.getMinDeterminant()[1]);
        jTFSMinFaceWeight.setText(params.getMinFaceWeight()[1]);
        jTFSMinVolRatio.setText(params.getMinVolRatio()[1]);
        jTFSMinTriangleTwist.setText(params.getMinTriangleTwist()[1]);
        jTFSNSmoothScale.setText(params.getNSmoothScale()[1]);
        jTFSErrorReduction.setText(params.getErrorReduction()[1]);
        
        jTFLMaxNonOrtho.setText(params.getMaxNonOrtho()[2]);
        jTFLMaxBoundarySkewness.setText(params.getMaxBoundarySkewness()[2]);
        jTFLMaxInternalSkewness.setText(params.getMaxInternalSkewness()[2]);
        jTFLMaxConcave.setText(params.getMaxConcave()[2]);
        jTFLMaxFlatness.setText(params.getMinFlatness()[2]);
        jTFLMinVol.setText(params.getMinVol()[2]);
        jTFLMinTetQuality.setText(params.getMinTetQuality()[2]);
        jTFLMinArea.setText(params.getMinTetQuality()[2]);
        jTFLMinTwist.setText(params.getMinTwist()[2]);
        jTFLMinDeterminant.setText(params.getMinDeterminant()[2]);
        jTFLMinFaceWeight.setText(params.getMinFaceWeight()[2]);
        jTFLMinVolRatio.setText(params.getMinVolRatio()[2]);
        jTFLMinTriangleTwist.setText(params.getMinTriangleTwist()[2]);
        jTFLNSmoothScale.setText(params.getNSmoothScale()[2]);
        jTFLErrorReduction.setText(params.getErrorReduction()[2]);
        
        
    }    
    
    public void savePanel()
    {
        loadParams();
        ProjectSHMXmlUtils.setSHMInSubRegion(regionName, subRegionName, project.getProjectDirectory(), params);
    }
    
    public boolean showDialog()
    {
        final JOptionPane jp = new JOptionPane(this,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        //JDialog j = new JDialog
        loadPanel();
        JDialog d = jp.createDialog("SnappyHexMesh ");
        d.setResizable(true);
        d.setVisible(true);
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(jp.getValue());
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlSubRegionSTL = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlMeshType = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListRefRegion1 = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTFRefRegType = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFRefRegMin = new javax.swing.JTextField();
        jTFRefRegMax = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFRefRegName = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLFeaturesFile = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLLevel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTFResolveFeatureAngles = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTFLocationInMesh = new javax.swing.JTextField();
        jTFAllowFreeStandingZoneFaces = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jTFMaxLocalCells = new javax.swing.JTextField();
        jTFMaxGlobalCells = new javax.swing.JTextField();
        jTFMinRefinementCells = new javax.swing.JTextField();
        jTFMaxLoadUnbalance = new javax.swing.JTextField();
        jTFNCellsBetweenLevels = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListPatches0 = new javax.swing.JList();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTFPatchLevels = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListRefRegion = new javax.swing.JList();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTFRefRegMode = new javax.swing.JTextField();
        jTFRefRegLevels = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTFNSmoothPatch = new javax.swing.JTextField();
        jTFTolerance = new javax.swing.JTextField();
        jTFNSolveIter = new javax.swing.JTextField();
        jTFNRelaxIter = new javax.swing.JTextField();
        jTFNFeatureSnapIter = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTFExpansionRatio = new javax.swing.JTextField();
        jTFFinalLayerThickness = new javax.swing.JTextField();
        jTFMinThickness = new javax.swing.JTextField();
        jTFNGrow = new javax.swing.JTextField();
        jTFFeatureAngle = new javax.swing.JTextField();
        jTFNRelaxIter1 = new javax.swing.JTextField();
        jTFNSmoothSurfaceNormals = new javax.swing.JTextField();
        jTFNSmoothNormals = new javax.swing.JTextField();
        jTFNSmoothThickness = new javax.swing.JTextField();
        jTFMaxFaceThicknessRatio = new javax.swing.JTextField();
        jTFMaxThicknessTMedialRatio = new javax.swing.JTextField();
        jTFMinMedianAxisAngle = new javax.swing.JTextField();
        jTFNBufferCellsNoExtrude = new javax.swing.JTextField();
        jTFNLayerIter = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jTFRelativeSizes = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListPatches = new javax.swing.JList();
        jPanel16 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jTFNSurfaceLayers = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTFCMaxNonOrtho = new javax.swing.JTextField();
        jTFCMaxInternalSkewness = new javax.swing.JTextField();
        jTFCMaxConcave = new javax.swing.JTextField();
        jTFCMaxFlatness = new javax.swing.JTextField();
        jTFCMinVol = new javax.swing.JTextField();
        jTFCMinTetQuality = new javax.swing.JTextField();
        jTFCMinArea = new javax.swing.JTextField();
        jTFCMinTwist = new javax.swing.JTextField();
        jTFCMinDeterminant = new javax.swing.JTextField();
        jTFCMinFaceWeight = new javax.swing.JTextField();
        jTFCMinVolRatio = new javax.swing.JTextField();
        jTFCMinTriangleTwist = new javax.swing.JTextField();
        jTFCNSmoothScale = new javax.swing.JTextField();
        jTFCErrorReduction = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFCMaxBoundarySkewness = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jTFSMaxNonOrtho = new javax.swing.JTextField();
        jTFSMaxInternalSkewness = new javax.swing.JTextField();
        jTFSMaxConcave = new javax.swing.JTextField();
        jTFSMaxFlatness = new javax.swing.JTextField();
        jTFSMinVol = new javax.swing.JTextField();
        jTFSMinTetQuality = new javax.swing.JTextField();
        jTFSMinArea = new javax.swing.JTextField();
        jTFSMinTwist = new javax.swing.JTextField();
        jTFSMinDeterminant = new javax.swing.JTextField();
        jTFSMinFaceWeight = new javax.swing.JTextField();
        jTFSMinVolRatio = new javax.swing.JTextField();
        jTFSMinTriangleTwist = new javax.swing.JTextField();
        jTFSNSmoothScale = new javax.swing.JTextField();
        jTFSErrorReduction = new javax.swing.JTextField();
        jBCopyCToS = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTFSMaxBoundarySkewness = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jTFLMaxNonOrtho = new javax.swing.JTextField();
        jTFLMaxInternalSkewness = new javax.swing.JTextField();
        jTFLMaxConcave = new javax.swing.JTextField();
        jTFLMaxFlatness = new javax.swing.JTextField();
        jTFLMinVol = new javax.swing.JTextField();
        jTFLMinTetQuality = new javax.swing.JTextField();
        jTFLMinArea = new javax.swing.JTextField();
        jTFLMinTwist = new javax.swing.JTextField();
        jTFLMinDeterminant = new javax.swing.JTextField();
        jTFLMinFaceWeight = new javax.swing.JTextField();
        jTFLMinVolRatio = new javax.swing.JTextField();
        jTFLMinTriangleTwist = new javax.swing.JTextField();
        jTFLNSmoothScale = new javax.swing.JTextField();
        jTFLErrorReduction = new javax.swing.JTextField();
        jBCopyCToL = new javax.swing.JButton();
        jBCopySToL = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        jTFLMaxBoundarySkewness = new javax.swing.JTextField();

        jPanel1.setAutoscrolls(true);
        jPanel1.setPreferredSize(new java.awt.Dimension(790, 1087));
        jPanel1.setRequestFocusEnabled(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel5.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jlSubRegionSTL, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jlSubRegionSTL.text")); // NOI18N
        jlSubRegionSTL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jlMeshType, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jlMeshType.text")); // NOI18N
        jlMeshType.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlMeshType))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(43, 43, 43)
                        .addComponent(jlSubRegionSTL)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jlSubRegionSTL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jlMeshType))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel6.border.title"))); // NOI18N

        jListRefRegion1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListRefRegion1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListRefRegion1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListRefRegion1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel5.text")); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel6.text")); // NOI18N

        jTFRefRegType.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegType.text")); // NOI18N
        jTFRefRegType.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel8.text")); // NOI18N

        jTFRefRegMin.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegMin.text")); // NOI18N
        jTFRefRegMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFRefRegMax.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegMax.text")); // NOI18N
        jTFRefRegMax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel9.text")); // NOI18N

        jTFRefRegName.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegName.text")); // NOI18N
        jTFRefRegName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jButton6, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton6.text")); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTFRefRegMin, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTFRefRegType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addComponent(jTFRefRegMax, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTFRefRegName)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTFRefRegName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFRefRegType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jTFRefRegMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTFRefRegMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(37, 37, 37)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(940, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel1);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jScrollPane5.TabConstraints.tabTitle"), jScrollPane5); // NOI18N

        jPanel2.setBorder(null);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel8.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLFeaturesFile, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLFeaturesFile.text")); // NOI18N
        jLFeaturesFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLLevel, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLLevel.text")); // NOI18N
        jLLevel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel15.text")); // NOI18N

        jTFResolveFeatureAngles.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFResolveFeatureAngles.text")); // NOI18N
        jTFResolveFeatureAngles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel17.text")); // NOI18N

        jTFLocationInMesh.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLocationInMesh.text")); // NOI18N
        jTFLocationInMesh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFAllowFreeStandingZoneFaces.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFAllowFreeStandingZoneFaces.text")); // NOI18N
        jTFAllowFreeStandingZoneFaces.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel84, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel84.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel85, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel85.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel86, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel86.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel87, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel87.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel88, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel88.text")); // NOI18N

        jTFMaxLocalCells.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMaxLocalCells.text")); // NOI18N
        jTFMaxLocalCells.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMaxGlobalCells.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMaxGlobalCells.text")); // NOI18N
        jTFMaxGlobalCells.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMinRefinementCells.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMinRefinementCells.text")); // NOI18N
        jTFMinRefinementCells.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMaxLoadUnbalance.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMaxLoadUnbalance.text")); // NOI18N
        jTFMaxLoadUnbalance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNCellsBetweenLevels.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNCellsBetweenLevels.text")); // NOI18N
        jTFNCellsBetweenLevels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel88)
                    .addComponent(jLabel87)
                    .addComponent(jLabel86)
                    .addComponent(jLabel85)
                    .addComponent(jLabel84)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLFeaturesFile, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jLLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTFResolveFeatureAngles)
                    .addComponent(jTFAllowFreeStandingZoneFaces)
                    .addComponent(jTFLocationInMesh)
                    .addComponent(jTFMaxLocalCells)
                    .addComponent(jTFMaxGlobalCells)
                    .addComponent(jTFMinRefinementCells)
                    .addComponent(jTFMaxLoadUnbalance)
                    .addComponent(jTFNCellsBetweenLevels))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLFeaturesFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLLevel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTFResolveFeatureAngles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTFLocationInMesh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFAllowFreeStandingZoneFaces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel84)
                    .addComponent(jTFMaxLocalCells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel85)
                    .addComponent(jTFMaxGlobalCells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel86)
                    .addComponent(jTFMinRefinementCells, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFMaxLoadUnbalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jTFNCellsBetweenLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 58, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel9.border.title"))); // NOI18N

        jListPatches0.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListPatches0.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListPatches0ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListPatches0);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel14.text")); // NOI18N

        jTFPatchLevels.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFPatchLevels.text")); // NOI18N
        jTFPatchLevels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton4.text")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel14)
                .addGap(30, 30, 30)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jTFPatchLevels, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTFPatchLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton4)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel10.border.title"))); // NOI18N

        jListRefRegion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListRefRegion.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListRefRegionValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListRefRegion);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel18.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel19.text")); // NOI18N

        jTFRefRegMode.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegMode.text")); // NOI18N
        jTFRefRegMode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFRefRegLevels.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRefRegLevels.text")); // NOI18N
        jTFRefRegLevels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton5.text")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(43, 43, 43)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTFRefRegMode)
                        .addComponent(jTFRefRegLevels, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTFRefRegMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTFRefRegLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(646, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(jPanel2);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jScrollPane6.TabConstraints.tabTitle"), jScrollPane6); // NOI18N

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel13.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel20.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel21, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel21.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel22, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel22.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel23, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel23.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel24, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel24.text")); // NOI18N

        jTFNSmoothPatch.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSmoothPatch.text")); // NOI18N
        jTFNSmoothPatch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFTolerance.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFTolerance.text")); // NOI18N
        jTFTolerance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNSolveIter.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSolveIter.text")); // NOI18N
        jTFNSolveIter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNRelaxIter.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNRelaxIter.text")); // NOI18N
        jTFNRelaxIter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNFeatureSnapIter.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNFeatureSnapIter.text")); // NOI18N
        jTFNFeatureSnapIter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20))
                .addGap(65, 65, 65)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFNSmoothPatch, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(jTFTolerance)
                    .addComponent(jTFNSolveIter)
                    .addComponent(jTFNRelaxIter)
                    .addComponent(jTFNFeatureSnapIter))
                .addContainerGap(282, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTFNSmoothPatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTFTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jTFNSolveIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTFNRelaxIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTFNFeatureSnapIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1193, Short.MAX_VALUE))
        );

        jScrollPane7.setViewportView(jPanel3);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jScrollPane7.TabConstraints.tabTitle"), jScrollPane7); // NOI18N

        jPanel4.setAutoscrolls(true);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel14.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel25, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel25.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel26, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel26.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel27, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel27.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel28, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel28.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel29, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel29.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel30, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel30.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel31, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel31.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel32, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel32.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel33, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel33.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel34, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel34.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel35, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel35.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel36, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel36.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel37, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel37.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel38, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel38.text")); // NOI18N

        jTFExpansionRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFExpansionRatio.text")); // NOI18N
        jTFExpansionRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFFinalLayerThickness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFFinalLayerThickness.text")); // NOI18N
        jTFFinalLayerThickness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMinThickness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMinThickness.text")); // NOI18N
        jTFMinThickness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNGrow.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNGrow.text")); // NOI18N
        jTFNGrow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFFeatureAngle.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFFeatureAngle.text")); // NOI18N
        jTFFeatureAngle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNRelaxIter1.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNRelaxIter1.text")); // NOI18N
        jTFNRelaxIter1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNSmoothSurfaceNormals.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSmoothSurfaceNormals.text")); // NOI18N
        jTFNSmoothSurfaceNormals.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNSmoothNormals.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSmoothNormals.text")); // NOI18N
        jTFNSmoothNormals.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNSmoothThickness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSmoothThickness.text")); // NOI18N
        jTFNSmoothThickness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMaxFaceThicknessRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMaxFaceThicknessRatio.text")); // NOI18N
        jTFMaxFaceThicknessRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMaxThicknessTMedialRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMaxThicknessTMedialRatio.text")); // NOI18N
        jTFMaxThicknessTMedialRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFMinMedianAxisAngle.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFMinMedianAxisAngle.text")); // NOI18N
        jTFMinMedianAxisAngle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNBufferCellsNoExtrude.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNBufferCellsNoExtrude.text")); // NOI18N
        jTFNBufferCellsNoExtrude.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFNLayerIter.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNLayerIter.text")); // NOI18N
        jTFNLayerIter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel83, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel83.text")); // NOI18N

        jTFRelativeSizes.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFRelativeSizes.text")); // NOI18N
        jTFRelativeSizes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel83)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel35)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(jLabel30)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel31))
                .addGap(40, 40, 40)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFExpansionRatio)
                    .addComponent(jTFFinalLayerThickness)
                    .addComponent(jTFMinThickness)
                    .addComponent(jTFNGrow)
                    .addComponent(jTFFeatureAngle)
                    .addComponent(jTFNRelaxIter1)
                    .addComponent(jTFNSmoothSurfaceNormals)
                    .addComponent(jTFNSmoothNormals)
                    .addComponent(jTFNSmoothThickness)
                    .addComponent(jTFMaxFaceThicknessRatio)
                    .addComponent(jTFMaxThicknessTMedialRatio)
                    .addComponent(jTFMinMedianAxisAngle)
                    .addComponent(jTFNBufferCellsNoExtrude)
                    .addComponent(jTFNLayerIter)
                    .addComponent(jTFRelativeSizes, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel83)
                    .addComponent(jTFRelativeSizes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFExpansionRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFFinalLayerThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFMinThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNGrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFFeatureAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNRelaxIter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNSmoothSurfaceNormals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNSmoothNormals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNSmoothThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFMaxFaceThicknessRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFMaxThicknessTMedialRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jTFMinMedianAxisAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNBufferCellsNoExtrude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNLayerIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel15.border.title"))); // NOI18N

        jListPatches.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListPatches.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListPatchesValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListPatches);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel16.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel39, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel39.text")); // NOI18N

        jTFNSurfaceLayers.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFNSurfaceLayers.text")); // NOI18N
        jTFNSurfaceLayers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel39)
                        .addGap(32, 32, 32)
                        .addComponent(jTFNSurfaceLayers, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jButton3)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTFNSurfaceLayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(681, Short.MAX_VALUE))
        );

        jScrollPane8.setViewportView(jPanel4);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jScrollPane8.TabConstraints.tabTitle"), jScrollPane8); // NOI18N

        jPanel17.setAutoscrolls(true);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel18.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel40, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel40.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel41, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel41.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel42, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel42.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel43, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel43.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel44, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel44.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel45, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel45.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel46, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel46.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel47, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel47.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel48, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel48.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel49, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel49.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel50, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel50.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel51, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel51.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel52, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel52.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel53, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel53.text")); // NOI18N

        jTFCMaxNonOrtho.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMaxNonOrtho.text")); // NOI18N
        jTFCMaxNonOrtho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMaxInternalSkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMaxInternalSkewness.text")); // NOI18N
        jTFCMaxInternalSkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMaxConcave.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMaxConcave.text")); // NOI18N
        jTFCMaxConcave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMaxFlatness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMaxFlatness.text")); // NOI18N
        jTFCMaxFlatness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinVol.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinVol.text")); // NOI18N
        jTFCMinVol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinTetQuality.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinTetQuality.text")); // NOI18N
        jTFCMinTetQuality.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinArea.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinArea.text")); // NOI18N
        jTFCMinArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinTwist.text")); // NOI18N
        jTFCMinTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinDeterminant.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinDeterminant.text")); // NOI18N
        jTFCMinDeterminant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinFaceWeight.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinFaceWeight.text")); // NOI18N
        jTFCMinFaceWeight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinVolRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinVolRatio.text")); // NOI18N
        jTFCMinVolRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCMinTriangleTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMinTriangleTwist.text")); // NOI18N
        jTFCMinTriangleTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCNSmoothScale.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCNSmoothScale.text")); // NOI18N
        jTFCNSmoothScale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFCErrorReduction.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCErrorReduction.text")); // NOI18N
        jTFCErrorReduction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel2.text")); // NOI18N

        jTFCMaxBoundarySkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFCMaxBoundarySkewness.text")); // NOI18N
        jTFCMaxBoundarySkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel53)
                    .addComponent(jLabel52)
                    .addComponent(jLabel51)
                    .addComponent(jLabel50)
                    .addComponent(jLabel49)
                    .addComponent(jLabel48)
                    .addComponent(jLabel47)
                    .addComponent(jLabel46)
                    .addComponent(jLabel45)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addComponent(jLabel40))
                .addGap(48, 48, 48)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFCMaxNonOrtho)
                    .addComponent(jTFCMaxInternalSkewness)
                    .addComponent(jTFCMaxConcave)
                    .addComponent(jTFCMaxFlatness)
                    .addComponent(jTFCMinVol)
                    .addComponent(jTFCMinTetQuality)
                    .addComponent(jTFCMinArea)
                    .addComponent(jTFCMinTwist)
                    .addComponent(jTFCMinDeterminant)
                    .addComponent(jTFCMinFaceWeight)
                    .addComponent(jTFCMinVolRatio)
                    .addComponent(jTFCMinTriangleTwist)
                    .addComponent(jTFCNSmoothScale)
                    .addComponent(jTFCErrorReduction)
                    .addComponent(jTFCMaxBoundarySkewness, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(jTFCMaxNonOrtho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCMaxBoundarySkewness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMaxInternalSkewness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMaxConcave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMaxFlatness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinTetQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinDeterminant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinFaceWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinVolRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCMinTriangleTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCNSmoothScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCErrorReduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel19.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel54, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel54.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel55, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel55.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel56, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel56.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel57, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel57.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel58, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel58.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel59, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel59.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel60, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel60.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel61, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel61.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel62, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel62.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel63, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel63.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel64, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel64.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel65, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel65.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel66, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel66.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel67, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel67.text")); // NOI18N

        jTFSMaxNonOrtho.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMaxNonOrtho.text")); // NOI18N
        jTFSMaxNonOrtho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMaxInternalSkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMaxInternalSkewness.text")); // NOI18N
        jTFSMaxInternalSkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMaxConcave.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMaxConcave.text")); // NOI18N
        jTFSMaxConcave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMaxFlatness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMaxFlatness.text")); // NOI18N
        jTFSMaxFlatness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinVol.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinVol.text")); // NOI18N
        jTFSMinVol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinTetQuality.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinTetQuality.text")); // NOI18N
        jTFSMinTetQuality.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinArea.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinArea.text")); // NOI18N
        jTFSMinArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinTwist.text")); // NOI18N
        jTFSMinTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinDeterminant.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinDeterminant.text")); // NOI18N
        jTFSMinDeterminant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinFaceWeight.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinFaceWeight.text")); // NOI18N
        jTFSMinFaceWeight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinVolRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinVolRatio.text")); // NOI18N
        jTFSMinVolRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSMinTriangleTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMinTriangleTwist.text")); // NOI18N
        jTFSMinTriangleTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSNSmoothScale.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSNSmoothScale.text")); // NOI18N
        jTFSNSmoothScale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFSErrorReduction.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSErrorReduction.text")); // NOI18N
        jTFSErrorReduction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jBCopyCToS, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jBCopyCToS.text")); // NOI18N
        jBCopyCToS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopyCToSActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel4.text")); // NOI18N

        jTFSMaxBoundarySkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFSMaxBoundarySkewness.text")); // NOI18N
        jTFSMaxBoundarySkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel67)
                    .addComponent(jLabel66)
                    .addComponent(jLabel65)
                    .addComponent(jLabel64)
                    .addComponent(jLabel63)
                    .addComponent(jLabel62)
                    .addComponent(jLabel61)
                    .addComponent(jLabel60)
                    .addComponent(jLabel59)
                    .addComponent(jLabel58)
                    .addComponent(jLabel57)
                    .addComponent(jLabel56)
                    .addComponent(jLabel55)
                    .addComponent(jLabel54))
                .addGap(48, 48, 48)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFSMaxNonOrtho, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jTFSMaxInternalSkewness)
                    .addComponent(jTFSMaxConcave)
                    .addComponent(jTFSMaxFlatness)
                    .addComponent(jTFSMinVol)
                    .addComponent(jTFSMinTetQuality)
                    .addComponent(jTFSMinArea)
                    .addComponent(jTFSMinTwist)
                    .addComponent(jTFSMinDeterminant)
                    .addComponent(jTFSMinFaceWeight)
                    .addComponent(jTFSMinVolRatio)
                    .addComponent(jTFSMinTriangleTwist)
                    .addComponent(jTFSNSmoothScale)
                    .addComponent(jTFSErrorReduction)
                    .addComponent(jTFSMaxBoundarySkewness))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jBCopyCToS)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFSMaxNonOrtho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBCopyCToS)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFSMaxBoundarySkewness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMaxInternalSkewness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMaxConcave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMaxFlatness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinTetQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinDeterminant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinFaceWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinVolRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSMinTriangleTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSNSmoothScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFSErrorReduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jPanel20.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel68, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel68.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel69, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel69.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel70, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel70.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel71, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel71.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel72, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel72.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel73, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel73.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel74, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel74.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel75, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel75.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel76, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel76.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel77, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel77.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel78, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel78.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel79, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel79.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel80, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel80.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel81, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel81.text")); // NOI18N

        jTFLMaxNonOrtho.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMaxNonOrtho.text")); // NOI18N
        jTFLMaxNonOrtho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMaxInternalSkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMaxInternalSkewness.text")); // NOI18N
        jTFLMaxInternalSkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMaxConcave.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMaxConcave.text")); // NOI18N
        jTFLMaxConcave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMaxFlatness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMaxFlatness.text")); // NOI18N
        jTFLMaxFlatness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinVol.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinVol.text")); // NOI18N
        jTFLMinVol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinTetQuality.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinTetQuality.text")); // NOI18N
        jTFLMinTetQuality.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinArea.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinArea.text")); // NOI18N
        jTFLMinArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinTwist.text")); // NOI18N
        jTFLMinTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinDeterminant.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinDeterminant.text")); // NOI18N
        jTFLMinDeterminant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinFaceWeight.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinFaceWeight.text")); // NOI18N
        jTFLMinFaceWeight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinVolRatio.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinVolRatio.text")); // NOI18N
        jTFLMinVolRatio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLMinTriangleTwist.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMinTriangleTwist.text")); // NOI18N
        jTFLMinTriangleTwist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLNSmoothScale.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLNSmoothScale.text")); // NOI18N
        jTFLNSmoothScale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTFLErrorReduction.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLErrorReduction.text")); // NOI18N
        jTFLErrorReduction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        org.openide.awt.Mnemonics.setLocalizedText(jBCopyCToL, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jBCopyCToL.text")); // NOI18N
        jBCopyCToL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopyCToLActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jBCopySToL, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jBCopySToL.text")); // NOI18N
        jBCopySToL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopySToLActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel82, org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jLabel82.text")); // NOI18N

        jTFLMaxBoundarySkewness.setText(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jTFLMaxBoundarySkewness.text")); // NOI18N
        jTFLMaxBoundarySkewness.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel81)
                    .addComponent(jLabel80)
                    .addComponent(jLabel79)
                    .addComponent(jLabel78)
                    .addComponent(jLabel77)
                    .addComponent(jLabel76)
                    .addComponent(jLabel75)
                    .addComponent(jLabel74)
                    .addComponent(jLabel73)
                    .addComponent(jLabel72)
                    .addComponent(jLabel71)
                    .addComponent(jLabel70)
                    .addComponent(jLabel69)
                    .addComponent(jLabel68)
                    .addComponent(jLabel82))
                .addGap(48, 48, 48)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFLMaxNonOrtho, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jTFLMaxInternalSkewness)
                    .addComponent(jTFLMaxConcave)
                    .addComponent(jTFLMaxFlatness)
                    .addComponent(jTFLMinVol)
                    .addComponent(jTFLMinTetQuality)
                    .addComponent(jTFLMinArea)
                    .addComponent(jTFLMinTwist)
                    .addComponent(jTFLMinDeterminant)
                    .addComponent(jTFLMinFaceWeight)
                    .addComponent(jTFLMinVolRatio)
                    .addComponent(jTFLMinTriangleTwist)
                    .addComponent(jTFLNSmoothScale)
                    .addComponent(jTFLErrorReduction)
                    .addComponent(jTFLMaxBoundarySkewness))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBCopyCToL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBCopySToL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBCopyCToL)
                        .addComponent(jTFLMaxNonOrtho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel68)))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBCopySToL)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTFLMaxBoundarySkewness, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMaxInternalSkewness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMaxConcave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMaxFlatness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinTetQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinDeterminant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinFaceWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinVolRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel78))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLMinTriangleTwist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLNSmoothScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel80))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFLErrorReduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel81))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jScrollPane9.setViewportView(jPanel17);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(SnappyHexMeshSettingsPanel.class, "SnappyHexMeshSettingsPanel.jScrollPane9.TabConstraints.tabTitle"), jScrollPane9); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1470, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jListRefRegion1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListRefRegion1ValueChanged
        // TODO add your handling code here:
        SHMParams.RefinedRegion selRefReg = (SHMParams.RefinedRegion)jListRefRegion1.getSelectedValue();
        jTFRefRegName.setText(selRefReg.getRefRegionName());
        jTFRefRegType.setText(selRefReg.getRefRegType());
        jTFRefRegMax.setText(selRefReg.getRefRegMax());
        jTFRefRegMin.setText(selRefReg.getRefRegMin());
    }//GEN-LAST:event_jListRefRegion1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //ArrayList<SHMParams.RefinedRegion> list = (ArrayList<SHMParams.RefinedRegion>) params.getRefRegion();
        SHMParams.RefinedRegion newRefReg = new SHMParams.RefinedRegion();
        newRefReg.setRefRefLevels("<levels>");
        newRefReg.setRefRefMode("<Mode>");
        newRefReg.setRefRegMax("<Max>");
        newRefReg.setRefRegMin("<Min>");
        newRefReg.setRefRegType("<type>");
        newRefReg.setRefRegionName("<name>");
        
        params.getRefRegion().add(newRefReg);
        
        jListRefRegion1.setModel( populateListModel(params.getRefRegion()) );
        jListRefRegion.setModel( populateListModel(params.getRefRegion()) );
        jListRefRegion1.setSelectedIndex(jListRefRegion1.getModel().getSize()-1);
        
        jTFRefRegName.setText(newRefReg.getRefRegionName());
        jTFRefRegType.setText(newRefReg.getRefRegType());
        jTFRefRegMax.setText(newRefReg.getRefRegMax());
        jTFRefRegMin.setText(newRefReg.getRefRegMin());
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SHMParams.RefinedRegion selRefReg = (SHMParams.RefinedRegion)jListRefRegion1.getSelectedValue();
        ArrayList<SHMParams.RefinedRegion> list = (ArrayList<SHMParams.RefinedRegion>) params.getRefRegion();
        
        for(SHMParams.RefinedRegion r:list)
        {
            if(r.equals(selRefReg))
            {
                list.remove(r);            
                break;
            }
        }
        jTFRefRegName.setText("");
        jTFRefRegType.setText("");
        jTFRefRegMax.setText("");
        jTFRefRegMin.setText("");
        jListRefRegion1.setModel( populateListModel(params.getRefRegion()) );
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(jTFRefRegName.getText().equalsIgnoreCase("<name>"))
        {
            JOptionPane.showMessageDialog(this, "Please provide a proper name");
            return;
        }
        SHMParams.RefinedRegion selRefReg = (SHMParams.RefinedRegion)jListRefRegion1.getSelectedValue();
        
        selRefReg.setRefRegMax(jTFRefRegMax.getText());
        selRefReg.setRefRegMin(jTFRefRegMin.getText());
        selRefReg.setRefRegType(jTFRefRegType.getText());
        selRefReg.setRefRegionName(jTFRefRegName.getText());
        
        jListRefRegion1.setModel( populateListModel(params.getRefRegion()) );
        jListRefRegion.setModel( populateListModel(params.getRefRegion()) );
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jListPatches0ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListPatches0ValueChanged
        // TODO add your handling code here:
        SHMParams.Patches p = (SHMParams.Patches) jListPatches0.getSelectedValue();
        jTFPatchLevels.setText(p.getPatchLevels());
        
    }//GEN-LAST:event_jListPatches0ValueChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        SHMParams.Patches p = (SHMParams.Patches) jListPatches0.getSelectedValue();
        p.setPatchLevels(jTFPatchLevels.getText());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jListRefRegionValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListRefRegionValueChanged
        // TODO add your handling code here:
        SHMParams.RefinedRegion selRefReg = (SHMParams.RefinedRegion)jListRefRegion.getSelectedValue();
        jTFRefRegMode.setText(selRefReg.getRefRefMode());
        jTFRefRegLevels.setText(selRefReg.getRefRefLevels());
    }//GEN-LAST:event_jListRefRegionValueChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        SHMParams.RefinedRegion selRefReg = (SHMParams.RefinedRegion)jListRefRegion.getSelectedValue();
        
        selRefReg.setRefRefLevels(jTFRefRegLevels.getText());
        selRefReg.setRefRefMode(jTFRefRegMode.getText());
        
        jListRefRegion.setModel( populateListModel(params.getRefRegion()) );
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jListPatchesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListPatchesValueChanged
        // TODO add your handling code here:
        SHMParams.Patches p = (SHMParams.Patches) jListPatches.getSelectedValue();
        jTFNSurfaceLayers.setText(p.getNSurfaceLayers());
    }//GEN-LAST:event_jListPatchesValueChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        SHMParams.Patches p = (SHMParams.Patches) jListPatches.getSelectedValue();
        p.setNSurfaceLayers(jTFNSurfaceLayers.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jBCopyCToSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopyCToSActionPerformed
        // TODO add your handling code here:
        jTFSMaxNonOrtho.setText(jTFCMaxNonOrtho.getText());
        jTFSMaxBoundarySkewness.setText(jTFCMaxBoundarySkewness.getText());
        jTFSMaxInternalSkewness.setText(jTFCMaxInternalSkewness.getText());
        jTFSMaxConcave.setText(jTFCMaxConcave.getText());
        jTFSMaxFlatness.setText(jTFCMaxFlatness.getText());
        jTFSMinVol.setText(jTFCMinVol.getText());
        jTFSMinTetQuality.setText(jTFCMinTetQuality.getText());
        jTFSMinArea.setText(jTFCMinArea.getText());
        jTFSMinTwist.setText(jTFCMinTwist.getText());
        jTFSMinDeterminant.setText(jTFCMinDeterminant.getText());
        jTFSMinFaceWeight.setText(jTFCMinFaceWeight.getText());
        jTFSMinVolRatio.setText(jTFCMinVolRatio.getText());
        jTFSMinTriangleTwist.setText(jTFCMinTriangleTwist.getText());
        jTFSNSmoothScale.setText(jTFCNSmoothScale.getText());
        jTFSErrorReduction.setText(jTFCErrorReduction.getText());
    }//GEN-LAST:event_jBCopyCToSActionPerformed

    private void jBCopyCToLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopyCToLActionPerformed
        // TODO add your handling code here:
        jTFLMaxNonOrtho.setText(jTFCMaxNonOrtho.getText());
        jTFLMaxBoundarySkewness.setText(jTFCMaxBoundarySkewness.getText());
        jTFLMaxInternalSkewness.setText(jTFCMaxInternalSkewness.getText());
        jTFLMaxConcave.setText(jTFCMaxConcave.getText());
        jTFLMaxFlatness.setText(jTFCMaxFlatness.getText());
        jTFLMinVol.setText(jTFCMinVol.getText());
        jTFLMinTetQuality.setText(jTFCMinTetQuality.getText());
        jTFLMinArea.setText(jTFCMinArea.getText());
        jTFLMinTwist.setText(jTFCMinTwist.getText());
        jTFLMinDeterminant.setText(jTFCMinDeterminant.getText());
        jTFLMinFaceWeight.setText(jTFCMinFaceWeight.getText());
        jTFLMinVolRatio.setText(jTFCMinVolRatio.getText());
        jTFLMinTriangleTwist.setText(jTFCMinTriangleTwist.getText());
        jTFLNSmoothScale.setText(jTFCNSmoothScale.getText());
        jTFLErrorReduction.setText(jTFCErrorReduction.getText());
    }//GEN-LAST:event_jBCopyCToLActionPerformed

    private void jBCopySToLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopySToLActionPerformed
        // TODO add your handling code here:
        jTFLMaxNonOrtho.setText(jTFSMaxNonOrtho.getText());
        jTFLMaxBoundarySkewness.setText(jTFSMaxBoundarySkewness.getText());
        jTFLMaxInternalSkewness.setText(jTFSMaxInternalSkewness.getText());
        jTFLMaxConcave.setText(jTFSMaxConcave.getText());
        jTFLMaxFlatness.setText(jTFSMaxFlatness.getText());
        jTFLMinVol.setText(jTFSMinVol.getText());
        jTFLMinTetQuality.setText(jTFSMinTetQuality.getText());
        jTFLMinArea.setText(jTFSMinArea.getText());
        jTFLMinTwist.setText(jTFSMinTwist.getText());
        jTFLMinDeterminant.setText(jTFSMinDeterminant.getText());
        jTFLMinFaceWeight.setText(jTFSMinFaceWeight.getText());
        jTFLMinVolRatio.setText(jTFSMinVolRatio.getText());
        jTFLMinTriangleTwist.setText(jTFSMinTriangleTwist.getText());
        jTFLNSmoothScale.setText(jTFSNSmoothScale.getText());
        jTFLErrorReduction.setText(jTFSErrorReduction.getText());
    }//GEN-LAST:event_jBCopySToLActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCopyCToL;
    private javax.swing.JButton jBCopyCToS;
    private javax.swing.JButton jBCopySToL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLFeaturesFile;
    private javax.swing.JLabel jLLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListPatches;
    private javax.swing.JList jListPatches0;
    private javax.swing.JList jListRefRegion;
    private javax.swing.JList jListRefRegion1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTFAllowFreeStandingZoneFaces;
    private javax.swing.JTextField jTFCErrorReduction;
    private javax.swing.JTextField jTFCMaxBoundarySkewness;
    private javax.swing.JTextField jTFCMaxConcave;
    private javax.swing.JTextField jTFCMaxFlatness;
    private javax.swing.JTextField jTFCMaxInternalSkewness;
    private javax.swing.JTextField jTFCMaxNonOrtho;
    private javax.swing.JTextField jTFCMinArea;
    private javax.swing.JTextField jTFCMinDeterminant;
    private javax.swing.JTextField jTFCMinFaceWeight;
    private javax.swing.JTextField jTFCMinTetQuality;
    private javax.swing.JTextField jTFCMinTriangleTwist;
    private javax.swing.JTextField jTFCMinTwist;
    private javax.swing.JTextField jTFCMinVol;
    private javax.swing.JTextField jTFCMinVolRatio;
    private javax.swing.JTextField jTFCNSmoothScale;
    private javax.swing.JTextField jTFExpansionRatio;
    private javax.swing.JTextField jTFFeatureAngle;
    private javax.swing.JTextField jTFFinalLayerThickness;
    private javax.swing.JTextField jTFLErrorReduction;
    private javax.swing.JTextField jTFLMaxBoundarySkewness;
    private javax.swing.JTextField jTFLMaxConcave;
    private javax.swing.JTextField jTFLMaxFlatness;
    private javax.swing.JTextField jTFLMaxInternalSkewness;
    private javax.swing.JTextField jTFLMaxNonOrtho;
    private javax.swing.JTextField jTFLMinArea;
    private javax.swing.JTextField jTFLMinDeterminant;
    private javax.swing.JTextField jTFLMinFaceWeight;
    private javax.swing.JTextField jTFLMinTetQuality;
    private javax.swing.JTextField jTFLMinTriangleTwist;
    private javax.swing.JTextField jTFLMinTwist;
    private javax.swing.JTextField jTFLMinVol;
    private javax.swing.JTextField jTFLMinVolRatio;
    private javax.swing.JTextField jTFLNSmoothScale;
    private javax.swing.JTextField jTFLocationInMesh;
    private javax.swing.JTextField jTFMaxFaceThicknessRatio;
    private javax.swing.JTextField jTFMaxGlobalCells;
    private javax.swing.JTextField jTFMaxLoadUnbalance;
    private javax.swing.JTextField jTFMaxLocalCells;
    private javax.swing.JTextField jTFMaxThicknessTMedialRatio;
    private javax.swing.JTextField jTFMinMedianAxisAngle;
    private javax.swing.JTextField jTFMinRefinementCells;
    private javax.swing.JTextField jTFMinThickness;
    private javax.swing.JTextField jTFNBufferCellsNoExtrude;
    private javax.swing.JTextField jTFNCellsBetweenLevels;
    private javax.swing.JTextField jTFNFeatureSnapIter;
    private javax.swing.JTextField jTFNGrow;
    private javax.swing.JTextField jTFNLayerIter;
    private javax.swing.JTextField jTFNRelaxIter;
    private javax.swing.JTextField jTFNRelaxIter1;
    private javax.swing.JTextField jTFNSmoothNormals;
    private javax.swing.JTextField jTFNSmoothPatch;
    private javax.swing.JTextField jTFNSmoothSurfaceNormals;
    private javax.swing.JTextField jTFNSmoothThickness;
    private javax.swing.JTextField jTFNSolveIter;
    private javax.swing.JTextField jTFNSurfaceLayers;
    private javax.swing.JTextField jTFPatchLevels;
    private javax.swing.JTextField jTFRefRegLevels;
    private javax.swing.JTextField jTFRefRegMax;
    private javax.swing.JTextField jTFRefRegMin;
    private javax.swing.JTextField jTFRefRegMode;
    private javax.swing.JTextField jTFRefRegName;
    private javax.swing.JTextField jTFRefRegType;
    private javax.swing.JTextField jTFRelativeSizes;
    private javax.swing.JTextField jTFResolveFeatureAngles;
    private javax.swing.JTextField jTFSErrorReduction;
    private javax.swing.JTextField jTFSMaxBoundarySkewness;
    private javax.swing.JTextField jTFSMaxConcave;
    private javax.swing.JTextField jTFSMaxFlatness;
    private javax.swing.JTextField jTFSMaxInternalSkewness;
    private javax.swing.JTextField jTFSMaxNonOrtho;
    private javax.swing.JTextField jTFSMinArea;
    private javax.swing.JTextField jTFSMinDeterminant;
    private javax.swing.JTextField jTFSMinFaceWeight;
    private javax.swing.JTextField jTFSMinTetQuality;
    private javax.swing.JTextField jTFSMinTriangleTwist;
    private javax.swing.JTextField jTFSMinTwist;
    private javax.swing.JTextField jTFSMinVol;
    private javax.swing.JTextField jTFSMinVolRatio;
    private javax.swing.JTextField jTFSNSmoothScale;
    private javax.swing.JTextField jTFTolerance;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlMeshType;
    private javax.swing.JLabel jlSubRegionSTL;
    // End of variables declaration//GEN-END:variables

    public static class SHMParams 
    {
        public static class RefinedRegion
        {
            private String RefRegionName;
            private String RefRegType;
            private String RefRegMin;
            private String RefRegMax;
            private String RefRefMode;
            private String RefRefLevels;

            
            @Override
            public String toString()
            {
                return RefRegionName;
            }
            
            public RefinedRegion()
            {
                RefRefLevels = "";
                RefRefMode = "";
                RefRegMax = "";
                RefRegMin = "";
                RefRegType = "";
                RefRegionName = "";
            }
            /**
             * @return the RefRegionName
             */
            public String getRefRegionName() {
                return RefRegionName;
            }

            /**
             * @param RefRegionName the RefRegionName to set
             */
            public void setRefRegionName(String RefRegionName) {
                this.RefRegionName = RefRegionName;
            }

            /**
             * @return the RefRegType
             */
            public String getRefRegType() {
                return RefRegType;
            }

            /**
             * @param RefRegType the RefRegType to set
             */
            public void setRefRegType(String RefRegType) {
                this.RefRegType = RefRegType;
            }

            /**
             * @return the RefRegMin
             */
            public String getRefRegMin() {
                return RefRegMin;
            }

            /**
             * @param RefRegMin the RefRegMin to set
             */
            public void setRefRegMin(String RefRegMin) {
                this.RefRegMin = RefRegMin;
            }

            /**
             * @return the RefRegMax
             */
            public String getRefRegMax() {
                return RefRegMax;
            }

            /**
             * @param RefRegMax the RefRegMax to set
             */
            public void setRefRegMax(String RefRegMax) {
                this.RefRegMax = RefRegMax;
            }

            /**
             * @return the RefRefMode
             */
            public String getRefRefMode() {
                return RefRefMode;
            }

            /**
             * @param RefRefMode the RefRefMode to set
             */
            public void setRefRefMode(String RefRefMode) {
                this.RefRefMode = RefRefMode;
            }

            /**
             * @return the RefRefLevels
             */
            public String getRefRefLevels() {
                return RefRefLevels;
            }

            /**
             * @param RefRefLevels the RefRefLevels to set
             */
            public void setRefRefLevels(String RefRefLevels) {
                this.RefRefLevels = RefRefLevels;
            }
        }

        public static class Patches
        {
            private String PatchName;
            private String PatchLevels;
            private String NSurfaceLayers;

            @Override
            public String toString()
            {
                return PatchName;
            }
            public Patches()
            {
                PatchName = "";
                PatchLevels = "";
                NSurfaceLayers = "";
            }
            /**
             * @return the PatchName
             */
            public String getPatchName() {
                return PatchName;
            }

            /**
             * @param PatchName the PatchName to set
             */
            public void setPatchName(String PatchName) {
                this.PatchName = PatchName;
            }

            /**
             * @return the PatchLevels
             */
            public String getPatchLevels() {
                return PatchLevels;
            }

            /**
             * @param PatchLevels the PatchLevels to set
             */
            public void setPatchLevels(String PatchLevels) {
                this.PatchLevels = PatchLevels;
            }

            /**
             * @return the NSurfaceLayers
             */
            public String getNSurfaceLayers() {
                return NSurfaceLayers;
            }

            /**
             * @param NSurfaceLayers the NSurfaceLayers to set
             */
            public void setNSurfaceLayers(String NSurfaceLayers) {
                this.NSurfaceLayers = NSurfaceLayers;
            }
        }

        // General
        private String STL;
        private String GeometryType;
        private Collection<RefinedRegion> RefinementRegions;
        
        // Castellated
        private String FeaturesFile;
        private String FeaturesLevel;
        private String ResolveFeatureAngle;
        private String LocationInMesh;
        private String AllowFreeStandingZoneFaces;
        private String MaxLocalCells;
        private String MaxGlobalCells;
        private String MinRefinementCells;
        private String MaxLoadUnbalance;
        private String NCellsBetweenLevels;
        private Collection<Patches> RefinementSurfaces;
        //private Collection<RefinedRegion> RefRegion1 = RefinementRegions;
        
        //Snapped
        private String NSmoothPatch;
        private String Tolerance;
        private String NSolveIter;
        private String NRelaxIter;
        private String NFeatureSnapIter;
        
        // Added layers
        private String RelativeSizes;
        private String ExpansiveRatio;
        private String FinalLayerThickness;
        private String MinThickness;
        private String NGrow;
        private String FeatureAngle;
        private String NRelaxIter1;
        private String NSmoothSurfaceNormals;
        private String NSmoothNormals;
        private String NSmoothThickness;
        private String MaxFaceThicknessRatio;
        private String MaxThicknessToMedialRatio;
        private String MinMedianAxisAngle;
        private String NBufferCellsNoExtrude;
        private String NLayerIter;
        //private Collection<Patches> ListPatches  = RefinementSurfaces; 
        
        // Mesh Quality
        private String[] MaxNonOrtho;
        private String[] MaxBoundarySkewness;
        private String[] MaxInternalSkewness;
        private String[] MaxConcave;
        private String[] MinFlatness;
        private String[] MinVol;
        private String[] MinTetQuality;
        private String[] MinArea;
        private String[] MinTwist;
        private String[] MinDeterminant;
        private String[] MinFaceWeight;
        private String[] MinVolRatio;
        private String[] MinTriangleTwist;
        private String[] NSmoothScale;
        private String[] ErrorReduction;
           
        public static SHMParams populate(Element shmElement)
        {
            SHMParams param = new SHMParams();
            
            
            
            
            return param;
        }

        /**
         * @return the STL
         */
        public String getSubRegionName() {
            return STL;
        }

        /**
         * @param STL the STL to set
         */
        public void setSubRegionName(String SubRegionName) {
            this.STL = SubRegionName;
        }

        /**
         * @return the GeometryType
         */
        public String getMeshType() {
            return GeometryType;
        }

        /**
         * @param GeometryType the GeometryType to set
         */
        public void setMeshType(String MeshType) {
            this.GeometryType = MeshType;
        }

//        /**
//         * @return the RefRegion1
//         */
//        public Collection<RefinedRegion> getRefRegion1() {
//            return RefRegion1;
//        }
//
//        /**
//         * @param RefRegion1 the RefRegion1 to set
//         */
//        public void setRefRegion1(Collection<RefinedRegion> RefRegion1) {
//            this.RefRegion1 = RefRegion1;
//        }

        /**
         * @return the FeaturesFile
         */
        public String getFeaturesFile() {
            return FeaturesFile;
        }

        /**
         * @param FeaturesFile the FeaturesFile to set
         */
        public void setFeaturesFile(String FeaturesFile) {
            this.FeaturesFile = FeaturesFile;
        }

        /**
         * @return the FeaturesLevel
         */
        public String getFeaturesLevel() {
            return FeaturesLevel;
        }

        /**
         * @param FeaturesLevel the FeaturesLevel to set
         */
        public void setFeaturesLevel(String Level) {
            this.FeaturesLevel = Level;
        }

        /**
         * @return the ResolveFeatureAngles
         */
        public String getResolveFeatureAngle() {
            return ResolveFeatureAngle;
        }

        /**
         * @param ResolveFeatureAngles the ResolveFeatureAngles to set
         */
        public void setResolveFeatureAngle(String ResolveFeatureAngles) {
            this.ResolveFeatureAngle = ResolveFeatureAngles;
        }

        /**
         * @return the LocationInMesh
         */
        public String getLocationInMesh() {
            return LocationInMesh;
        }

        /**
         * @param LocationInMesh the LocationInMesh to set
         */
        public void setLocationInMesh(String LocationInMesh) {
            this.LocationInMesh = LocationInMesh;
        }

        /**
         * @return the AllowFreeStandingZoneFaces
         */
        public String getAllowFreeStandingZoneFaces() {
            return AllowFreeStandingZoneFaces;
        }

        /**
         * @param AllowFreeStandingZoneFaces the AllowFreeStandingZoneFaces to set
         */
        public void setAllowFreeStandingZoneFaces(String AllowFreeStandingZoneFaces) {
            this.AllowFreeStandingZoneFaces = AllowFreeStandingZoneFaces;
        }

        /**
         * @return the MaxLocalCells
         */
        public String getMaxLocalCells() {
            return MaxLocalCells;
        }

        /**
         * @param MaxLocalCells the MaxLocalCells to set
         */
        public void setMaxLocalCells(String MaxLocalCells) {
            this.MaxLocalCells = MaxLocalCells;
        }

        /**
         * @return the MaxGlobalCells
         */
        public String getMaxGlobalCells() {
            return MaxGlobalCells;
        }

        /**
         * @param MaxGlobalCells the MaxGlobalCells to set
         */
        public void setMaxGlobalCells(String MaxGlobalCells) {
            this.MaxGlobalCells = MaxGlobalCells;
        }

        /**
         * @return the MinRefinementCells
         */
        public String getMinRefinementCells() {
            return MinRefinementCells;
        }

        /**
         * @param MinRefinementCells the MinRefinementCells to set
         */
        public void setMinRefinementCells(String MaxRefinementCells) {
            this.MinRefinementCells = MaxRefinementCells;
        }

        /**
         * @return the MaxLoadUnbalance
         */
        public String getMaxLoadUnbalance() {
            return MaxLoadUnbalance;
        }

        /**
         * @param MaxLoadUnbalance the MaxLoadUnbalance to set
         */
        public void setMaxLoadUnbalance(String MaxLoadUnbalance) {
            this.MaxLoadUnbalance = MaxLoadUnbalance;
        }

        /**
         * @return the NCellsBetweenLevels
         */
        public String getNCellsBetweenLevels() {
            return NCellsBetweenLevels;
        }

        /**
         * @param NCellsBetweenLevels the NCellsBetweenLevels to set
         */
        public void setNCellsBetweenLevels(String NCellsBetweenLevels) {
            this.NCellsBetweenLevels = NCellsBetweenLevels;
        }

        /**
         * @return the ListPatches0
         */
        public Collection<Patches> getListPatches0() {
            return RefinementSurfaces;
        }

        /**
         * @param ListPatches0 the ListPatches0 to set
         */
        public void setListPatches0(Collection<Patches> ListPatches0) {
            this.RefinementSurfaces = ListPatches0;
        }

        /**
         * @return the RefRegion
         */
        public Collection<RefinedRegion> getRefRegion() {
            return RefinementRegions;
        }

        /**
         * @param RefRegion the RefRegion to set
         */
        public void setRefRegion(Collection<RefinedRegion> RefRegion) {
            this.RefinementRegions = RefRegion;
        }

        /**
         * @return the NSmoothPatch
         */
        public String getNSmoothPatch() {
            return NSmoothPatch;
        }

        /**
         * @param NSmoothPatch the NSmoothPatch to set
         */
        public void setNSmoothPatch(String NSmoothPatch) {
            this.NSmoothPatch = NSmoothPatch;
        }

        /**
         * @return the Tolerance
         */
        public String getTolerance() {
            return Tolerance;
        }

        /**
         * @param Tolerance the Tolerance to set
         */
        public void setTolerance(String Tolerance) {
            this.Tolerance = Tolerance;
        }

        /**
         * @return the NSolverIter
         */
        public String getNSolveIter() {
            return NSolveIter;
        }

        /**
         * @param NSolverIter the NSolverIter to set
         */
        public void setNSolveIter(String NSolverIter) {
            this.NSolveIter = NSolverIter;
        }

        /**
         * @return the NRelaxIter
         */
        public String getNRelaxIter() {
            return NRelaxIter;
        }

        /**
         * @param NRelaxIter the NRelaxIter to set
         */
        public void setNRelaxIter(String NRelaxIter) {
            this.NRelaxIter = NRelaxIter;
        }

        /**
         * @return the NFeatureSnapIter
         */
        public String getNFeatureSnapIter() {
            return NFeatureSnapIter;
        }

        /**
         * @param NFeatureSnapIter the NFeatureSnapIter to set
         */
        public void setNFeatureSnapIter(String NFeatureSnapIter) {
            this.NFeatureSnapIter = NFeatureSnapIter;
        }

        /**
         * @return the RelativeSizes
         */
        public String getRelativeSizes() {
            return RelativeSizes;
        }

        /**
         * @param RelativeSizes the RelativeSizes to set
         */
        public void setRelativeSizes(String RelativeSizes) {
            this.RelativeSizes = RelativeSizes;
        }

        /**
         * @return the ExpansiveRatio
         */
        public String getExpansiveRatio() {
            return ExpansiveRatio;
        }

        /**
         * @param ExpansiveRatio the ExpansiveRatio to set
         */
        public void setExpansiveRatio(String ExpansiveRatio) {
            this.ExpansiveRatio = ExpansiveRatio;
        }

        /**
         * @return the FinalLayerThickness
         */
        public String getFinalLayerThickness() {
            return FinalLayerThickness;
        }

        /**
         * @param FinalLayerThickness the FinalLayerThickness to set
         */
        public void setFinalLayerThickness(String FinalLayerThickness) {
            this.FinalLayerThickness = FinalLayerThickness;
        }

        /**
         * @return the MinThickness
         */
        public String getMinThickness() {
            return MinThickness;
        }

        /**
         * @param MinThickness the MinThickness to set
         */
        public void setMinThickness(String MinThickness) {
            this.MinThickness = MinThickness;
        }

        /**
         * @return the NGrow
         */
        public String getNGrow() {
            return NGrow;
        }

        /**
         * @param NGrow the NGrow to set
         */
        public void setNGrow(String NGrow) {
            this.NGrow = NGrow;
        }

        /**
         * @return the FeatureAngle
         */
        public String getFeatureAngle() {
            return FeatureAngle;
        }

        /**
         * @param FeatureAngle the FeatureAngle to set
         */
        public void setFeatureAngle(String FeatureAngle) {
            this.FeatureAngle = FeatureAngle;
        }

        /**
         * @return the NRelaxIter1
         */
        public String getNRelaxIter1() {
            return NRelaxIter1;
        }

        /**
         * @param NRelaxIter1 the NRelaxIter1 to set
         */
        public void setNRelaxIter1(String NRelaxIter1) {
            this.NRelaxIter1 = NRelaxIter1;
        }

        /**
         * @return the NSmoothSurfaceNormals
         */
        public String getNSmoothSurfaceNormals() {
            return NSmoothSurfaceNormals;
        }

        /**
         * @param NSmoothSurfaceNormals the NSmoothSurfaceNormals to set
         */
        public void setNSmoothSurfaceNormals(String NSmoothSurfaceNormals) {
            this.NSmoothSurfaceNormals = NSmoothSurfaceNormals;
        }

        /**
         * @return the NSmoothNormals
         */
        public String getNSmoothNormals() {
            return NSmoothNormals;
        }

        /**
         * @param NSmoothNormals the NSmoothNormals to set
         */
        public void setNSmoothNormals(String NSmoothNormals) {
            this.NSmoothNormals = NSmoothNormals;
        }

        /**
         * @return the NSmoothThickness
         */
        public String getNSmoothThickness() {
            return NSmoothThickness;
        }

        /**
         * @param NSmoothThickness the NSmoothThickness to set
         */
        public void setNSmoothThickness(String NSmoothThickness) {
            this.NSmoothThickness = NSmoothThickness;
        }

        /**
         * @return the MaxFaceThicknessRatio
         */
        public String getMaxFaceThicknessRatio() {
            return MaxFaceThicknessRatio;
        }

        /**
         * @param MaxFaceThicknessRatio the MaxFaceThicknessRatio to set
         */
        public void setMaxFaceThicknessRatio(String MaxFaceThicknessRatio) {
            this.MaxFaceThicknessRatio = MaxFaceThicknessRatio;
        }

        /**
         * @return the MaxThicknessToMedialRatio
         */
        public String getMaxThicknessToMedialRatio() {
            return MaxThicknessToMedialRatio;
        }

        /**
         * @param MaxThicknessToMedialRatio the MaxThicknessToMedialRatio to set
         */
        public void setMaxThicknessToMedialRatio(String MaxThicknessToMedialRatio) {
            this.MaxThicknessToMedialRatio = MaxThicknessToMedialRatio;
        }

        /**
         * @return the MinMedianAxisAngle
         */
        public String getMinMedianAxisAngle() {
            return MinMedianAxisAngle;
        }

        /**
         * @param MinMedianAxisAngle the MinMedianAxisAngle to set
         */
        public void setMinMedianAxisAngle(String MinMedianAxisAngle) {
            this.MinMedianAxisAngle = MinMedianAxisAngle;
        }

        /**
         * @return the NBufferCellsNoExtrude
         */
        public String getNBufferCellsNoExtrude() {
            return NBufferCellsNoExtrude;
        }

        /**
         * @param NBufferCellsNoExtrude the NBufferCellsNoExtrude to set
         */
        public void setNBufferCellsNoExtrude(String NBufferCellsNoExtrude) {
            this.NBufferCellsNoExtrude = NBufferCellsNoExtrude;
        }

        /**
         * @return the NLayerIter
         */
        public String getNLayerIter() {
            return NLayerIter;
        }

        /**
         * @param NLayerIter the NLayerIter to set
         */
        public void setNLayerIter(String NLayerIter) {
            this.NLayerIter = NLayerIter;
        }

//        /**
//         * @return the ListPatches
//         */
//        public Collection<Patches> getListPatches() {
//            return ListPatches;
//        }
//
//        /**
//         * @param ListPatches the ListPatches to set
//         */
//        public void setListPatches(Collection<Patches> ListPatches) {
//            this.ListPatches = ListPatches;
//        }

        /**
         * @return the MaxNonOrtho
         */
        public String[] getMaxNonOrtho() {
            return MaxNonOrtho;
        }

        /**
         * @param MaxNonOrtho the MaxNonOrtho to set
         */
        public void setMaxNonOrtho(String[] MaxNonOrtho) {
            this.MaxNonOrtho = MaxNonOrtho;
        }

        /**
         * @return the MaxBoundarySkewness
         */
        public String[] getMaxBoundarySkewness() {
            return MaxBoundarySkewness;
        }

        /**
         * @param MaxBoundarySkewness the MaxBoundarySkewness to set
         */
        public void setMaxBoundarySkewness(String[] MaxBoundarySkewness) {
            this.MaxBoundarySkewness = MaxBoundarySkewness;
        }

        /**
         * @return the MaxInternalSkewness
         */
        public String[] getMaxInternalSkewness() {
            return MaxInternalSkewness;
        }

        /**
         * @param MaxInternalSkewness the MaxInternalSkewness to set
         */
        public void setMaxInternalSkewness(String[] MaxInternalSkewness) {
            this.MaxInternalSkewness = MaxInternalSkewness;
        }

        /**
         * @return the MaxConcave
         */
        public String[] getMaxConcave() {
            return MaxConcave;
        }

        /**
         * @param MaxConcave the MaxConcave to set
         */
        public void setMaxConcave(String[] MaxConcave) {
            this.MaxConcave = MaxConcave;
        }

        /**
         * @return the MinFlatness
         */
        public String[] getMinFlatness() {
            return MinFlatness;
        }

        /**
         * @param MinFlatness the MaxFlatness to set
         */
        public void setMinFlatness(String[] MaxFlatness) {
            this.MinFlatness = MaxFlatness;
        }

        /**
         * @return the MinVol
         */
        public String[] getMinVol() {
            return MinVol;
        }

        /**
         * @param MinVol the MinVol to set
         */
        public void setMinVol(String[] MinVal) {
            this.MinVol = MinVal;
        }

        /**
         * @return the MinTetQuality
         */
        public String[] getMinTetQuality() {
            return MinTetQuality;
        }

        /**
         * @param MinTetQuality the MinTetQuality to set
         */
        public void setMinTetQuality(String[] MinTetQuality) {
            this.MinTetQuality = MinTetQuality;
        }

        /**
         * @return the MinArea
         */
        public String[] getMinArea() {
            return MinArea;
        }

        /**
         * @param MinArea the MinArea to set
         */
        public void setMinArea(String[] MinArea) {
            this.MinArea = MinArea;
        }

        /**
         * @return the MinTwist
         */
        public String[] getMinTwist() {
            return MinTwist;
        }

        /**
         * @param MinTwist the MinTwist to set
         */
        public void setMinTwist(String[] MinTwist) {
            this.MinTwist = MinTwist;
        }

        /**
         * @return the MinDeterminant
         */
        public String[] getMinDeterminant() {
            return MinDeterminant;
        }

        /**
         * @param MinDeterminant the MinDeterminant to set
         */
        public void setMinDeterminant(String[] MinDeterminant) {
            this.MinDeterminant = MinDeterminant;
        }

        /**
         * @return the MinFaceWeight
         */
        public String[] getMinFaceWeight() {
            return MinFaceWeight;
        }

        /**
         * @param MinFaceWeight the MinFaceWeight to set
         */
        public void setMinFaceWeight(String[] MinFaceWeight) {
            this.MinFaceWeight = MinFaceWeight;
        }

        /**
         * @return the MinVolRatio
         */
        public String[] getMinVolRatio() {
            return MinVolRatio;
        }

        /**
         * @param MinVolRatio the MinVolRatio to set
         */
        public void setMinVolRatio(String[] MinVolRatio) {
            this.MinVolRatio = MinVolRatio;
        }

        /**
         * @return the MinTriangleTwist
         */
        public String[] getMinTriangleTwist() {
            return MinTriangleTwist;
        }

        /**
         * @param MinTriangleTwist the MinTriangleTwist to set
         */
        public void setMinTriangleTwist(String[] MinTriangleTwist) {
            this.MinTriangleTwist = MinTriangleTwist;
        }

        /**
         * @return the NSmoothScale
         */
        public String[] getNSmoothScale() {
            return NSmoothScale;
        }

        /**
         * @param NSmoothScale the NSmoothScale to set
         */
        public void setNSmoothScale(String[] NSmoothScale) {
            this.NSmoothScale = NSmoothScale;
        }

        /**
         * @return the ErrorReduction
         */
        public String[] getErrorReduction() {
            return ErrorReduction;
        }

        /**
         * @param ErrorReduction the ErrorReduction to set
         */
        public void setErrorReduction(String[] ErrorReduction) {
            this.ErrorReduction = ErrorReduction;
        }
        
        
    }
}
