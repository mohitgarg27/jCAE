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
 * @author mohit
 */
public class RegionFluidConstantSettingsPanel extends javax.swing.JPanel {

    /**
     * Creates new form RegionFluidConstantSettingsPanel
     */
    String regionName;
    FileObject project;
    
    public RegionFluidConstantSettingsPanel(String regionName, FileObject project) 
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
        jTAG.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/constant/" + "g" ));
        jTARadiation.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/constant/" + "radiationProperties" ));
        jTARas.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/constant/" + "RASProperties" ));
        jTATransport.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/constant/" + "transportProperties" ));
        jTATurbulence.setText(ProjectUtils.readFile(project.getPath() + "/" + regionName + "/constant/" + "turbulenceProperties" ));
        
    }
    
    public void save()
    {
        ProjectFileUtils.writeFile(jTAG.getText(), project.getPath() + "/" + regionName + "/constant/" + "g" );
        ProjectFileUtils.writeFile(jTARadiation.getText(), project.getPath() + "/" + regionName + "/constant/" + "radiationProperties" );
        ProjectFileUtils.writeFile(jTARas.getText(), project.getPath() + "/" + regionName + "/constant/" + "RASProperties" );
        ProjectFileUtils.writeFile(jTATransport.getText(), project.getPath() + "/" + regionName + "/constant/" + "transportProperties" );
        ProjectFileUtils.writeFile(jTATurbulence.getText(), project.getPath() + "/" + regionName + "/constant/" + "turbulenceProperties" );
        
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTATransport = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTATurbulence = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTARadiation = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTARas = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTAG = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTATransport.setColumns(20);
        jTATransport.setRows(5);
        jScrollPane1.setViewportView(jTATransport);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionFluidConstantSettingsPanel.class, "RegionFluidConstantSettingsPanel.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTATurbulence.setColumns(20);
        jTATurbulence.setRows(5);
        jScrollPane2.setViewportView(jTATurbulence);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionFluidConstantSettingsPanel.class, "RegionFluidConstantSettingsPanel.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTARadiation.setColumns(20);
        jTARadiation.setRows(5);
        jScrollPane3.setViewportView(jTARadiation);

        jPanel4.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionFluidConstantSettingsPanel.class, "RegionFluidConstantSettingsPanel.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTARas.setColumns(20);
        jTARas.setRows(5);
        jScrollPane4.setViewportView(jTARas);

        jPanel5.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionFluidConstantSettingsPanel.class, "RegionFluidConstantSettingsPanel.jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTAG.setColumns(20);
        jTAG.setRows(5);
        jScrollPane5.setViewportView(jTAG);

        jPanel6.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(RegionFluidConstantSettingsPanel.class, "RegionFluidConstantSettingsPanel.jPanel6.TabConstraints.tabTitle"), jPanel6); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTAG;
    private javax.swing.JTextArea jTARadiation;
    private javax.swing.JTextArea jTARas;
    private javax.swing.JTextArea jTATransport;
    private javax.swing.JTextArea jTATurbulence;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
