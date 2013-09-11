/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.api.project.Project;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.options.PatchBasicInfo;
import project.org.jcae.netbeans.of.options.PatchFieldInfo;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mita
 */
public class PatchBoundaryPanel extends javax.swing.JPanel 
{
    /**
     * Creates new form PatchBoundaryPanel
     */
    String regionName;
    String subRegionName;
    String patchName;
    Project project;

    PatchNode pNode;
    
    // Panels for different field files
    JPanel j1; // Basic
    JPanel j2; //
    JPanel j3; 
    JPanel j4; 
    JPanel j5; 
    JPanel j6; 
    JPanel j7; 
    JPanel j8; 
    JPanel j9;     
    
    public PatchBoundaryPanel(PatchNode patchNode, String rName, String sName, String pName, Project pr)
    {
        pNode = patchNode;
        regionName = rName;
        subRegionName = sName;
        patchName = pName;
        project = pr;
        initComponents();
    }
    
    public boolean showDialog()
    {
        final JOptionPane jp = new JOptionPane(this,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        //JDialog j = new JDialog
        load();
        JDialog d = jp.createDialog("Boundary conditions");
        d.setResizable(true);
        d.setVisible(true);
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(jp.getValue());
    }    
    
    public void load()
    {
            j1 = new PatchBasicInfo(pNode, project);
            jPanel1.setLayout(new GridLayout());
            jPanel1.add(j1);
            ((PatchBasicInfo)j1).load();
            
            String regionType = ProjectUtils.getRegionType(pNode.getrName(), project.getProjectDirectory());
            if(regionType.equalsIgnoreCase("Fluid"))
            {
                j2 = new PatchFieldInfo(pNode, project, "K");            
                jPanel2.setLayout(new GridLayout());
                jPanel2.add(j2);
                ((PatchFieldInfo)j2).load();

                j3 = new PatchFieldInfo(pNode, project, "kappaEff");            
                jPanel3.setLayout(new GridLayout());
                jPanel3.add(j3);
                ((PatchFieldInfo)j3).load();

                j4 = new PatchFieldInfo(pNode, project, "nut");            
                jPanel4.setLayout(new GridLayout());
                jPanel4.add(j4);
                ((PatchFieldInfo)j4).load();

                j5 = new PatchFieldInfo(pNode, project, "omega");            
                jPanel5.setLayout(new GridLayout());
                jPanel5.add(j5);
                ((PatchFieldInfo)j5).load();

                j6 = new PatchFieldInfo(pNode, project, "p_rgh");            
                jPanel6.setLayout(new GridLayout());
                jPanel6.add(j6);
                ((PatchFieldInfo)j6).load();

                j7 = new PatchFieldInfo(pNode, project, "T");            
                jPanel7.setLayout(new GridLayout());
                jPanel7.add(j7);
                ((PatchFieldInfo)j7).load();

                j8 = new PatchFieldInfo(pNode, project, "U");            
                jPanel8.setLayout(new GridLayout());
                jPanel8.add(j8);
                ((PatchFieldInfo)j8).load();                
                
                jPanel9.setVisible(false);                
                
            }
            else if(regionType.equalsIgnoreCase("Solid"))
            {
                j2 = new PatchFieldInfo(pNode, project, "K");            
                jPanel2.setLayout(new GridLayout());
                jPanel2.add(j2);
                ((PatchFieldInfo)j2).load();

                j7 = new PatchFieldInfo(pNode, project, "T");            
                jPanel7.setLayout(new GridLayout());
                jPanel7.add(j7);
                ((PatchFieldInfo)j7).load();
                
                j9 = new PatchFieldInfo(pNode, project, "material");            
                jPanel9.setLayout(new GridLayout());
                jPanel9.add(j9);
                ((PatchFieldInfo)j9).load();

                jPanel3.setVisible(false);
                jPanel4.setVisible(false);
                jPanel5.setVisible(false);
                jPanel6.setVisible(false);
                jPanel8.setVisible(false);
            }
            
            
            
    }
    
    public void save()
    {
        ((PatchBasicInfo)j1).save();
        
        String regionType = ProjectUtils.getRegionType(pNode.getrName(), project.getProjectDirectory());
        if(regionType.equalsIgnoreCase("Fluid"))
        {
            ((PatchFieldInfo)j2).save();
            ((PatchFieldInfo)j3).save();
            ((PatchFieldInfo)j4).save();
            ((PatchFieldInfo)j5).save();
            ((PatchFieldInfo)j6).save();
            ((PatchFieldInfo)j7).save();
            ((PatchFieldInfo)j8).save();

        }
        else if(regionType.equalsIgnoreCase("Solid"))
        {
            ((PatchFieldInfo)j2).save();
            ((PatchFieldInfo)j7).save();
            ((PatchFieldInfo)j9).save();
            
        }
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel6.TabConstraints.tabTitle"), jPanel6); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel7.TabConstraints.tabTitle"), jPanel7); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel8.TabConstraints.tabTitle"), jPanel8); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PatchBoundaryPanel.class, "PatchBoundaryPanel.jPanel9.TabConstraints.tabTitle"), jPanel9); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
