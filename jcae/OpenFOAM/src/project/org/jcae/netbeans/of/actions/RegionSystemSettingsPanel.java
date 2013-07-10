/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.openide.filesystems.FileObject;
import project.org.jcae.netbeans.of.project.ProjectFileUtils;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mita
 */
public class RegionSystemSettingsPanel extends javax.swing.JPanel {

    /**
     * Creates new form RegionSystemSettingsPanel
     */
    
    String regionName;
    FileObject project;
    
    public RegionSystemSettingsPanel(String regionName, FileObject project)
    {
        initComponents();
        this.regionName = regionName;
        this.project = project;         
    }
    
    public boolean showDialog()
    {
        final JOptionPane jp = new JOptionPane(this,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        //JDialog j = new JDialog
        load();
        JDialog d = jp.createDialog("SnappyHexMesh ");
        d.setResizable(true);
        d.setVisible(true);
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(jp.getValue());
    }   
    
    public void load()
    {
        // Get settings file and load file data
        jTAControlDict.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/" + "controlDict" ));
        jTAFVSchemes.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/" + "fvSchemes" ));
        jTAFVSolution.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/" + "fvSolution" ));
        
    }
    
    public void save()
    {
        ProjectFileUtils.writeFile(jTAControlDict.getText(), project.getPath() + "/" + regionName + "/" + "controlDict" );
        ProjectFileUtils.writeFile(jTAFVSchemes.getText(), project.getPath() + "/" + regionName + "/" + "fvSchemes" );
        ProjectFileUtils.writeFile(jTAFVSolution.getText(), project.getPath() + "/" + regionName + "/" + "fvSolution" );        
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAControlDict = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAFVSchemes = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAFVSolution = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(800, 700));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTAControlDict.setColumns(20);
        jTAControlDict.setRows(5);
        jScrollPane1.setViewportView(jTAControlDict);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionSystemSettingsPanel.class, "RegionSystemSettingsPanel.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTAFVSchemes.setColumns(20);
        jTAFVSchemes.setRows(5);
        jScrollPane2.setViewportView(jTAFVSchemes);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionSystemSettingsPanel.class, "RegionSystemSettingsPanel.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTAFVSolution.setColumns(20);
        jTAFVSolution.setRows(5);
        jScrollPane3.setViewportView(jTAFVSolution);

        jPanel3.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionSystemSettingsPanel.class, "RegionSystemSettingsPanel.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTAControlDict;
    private javax.swing.JTextArea jTAFVSchemes;
    private javax.swing.JTextArea jTAFVSolution;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
