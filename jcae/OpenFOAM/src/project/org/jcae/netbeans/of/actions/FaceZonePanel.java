/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mohit
 */
public class FaceZonePanel extends javax.swing.JPanel 
{
    String regionName;
    Project project;
    
    FZParams params;
    
    /**
     * Creates new form FaceZonePanel
     */
    public FaceZonePanel(String rName, Project pr) 
    {
        initComponents();
        regionName = rName;
        project = pr;
        
        params = new FZParams();
        jCBSubRegions.setModel(populateListModel(ProjectUtils.getAllNestedSubRegions(regionName, project.getProjectDirectory())));
        
        jCBSubRegions.setSelectedIndex(0);
        jCBSubRegionsActionPerformed(null);
        
    }

    private static <T> DefaultComboBoxModel populateListModel(Collection<T> patches)
    {        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i=0; i<patches.size(); i++)
        {
            model.addElement(((ArrayList<T>)patches).get(i) );
        }
        return model;
        
    }
    
    public boolean showDialog()
    {
        final JOptionPane jp = new JOptionPane(this,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);        
        JDialog d = jp.createDialog("FaceZone ");
        d.setResizable(true);
        d.setVisible(true);        
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(jp.getValue());
    }  
    
    private void loadParams()
    {
        params.patchName = jCBPatches.getSelectedItem().toString();
        params.subRegionName =  jCBSubRegions.getSelectedItem().toString();
        params.zoneName = jTFZName.getText();
    }
    
    public void save()
    {
        loadParams();
        ProjectUtils.addFaceZoneElement(regionName, params.getZoneName(), params.getSubRegionName(), params.getPatchName(), project.getProjectDirectory());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTFZName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCBSubRegions = new javax.swing.JComboBox();
        jCBPatches = new javax.swing.JComboBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(FaceZonePanel.class, "FaceZonePanel.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FaceZonePanel.class, "FaceZonePanel.jLabel1.text")); // NOI18N

        jTFZName.setText(org.openide.util.NbBundle.getMessage(FaceZonePanel.class, "FaceZonePanel.jTFZName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FaceZonePanel.class, "FaceZonePanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FaceZonePanel.class, "FaceZonePanel.jLabel3.text")); // NOI18N

        jCBSubRegions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBSubRegionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFZName)
                    .addComponent(jCBSubRegions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCBPatches, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFZName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBSubRegions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBPatches, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCBSubRegionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBSubRegionsActionPerformed
        // TODO add your handling code here:
        String subRegion =  jCBSubRegions.getSelectedItem().toString();
        jCBPatches.setModel(populateListModel(ProjectUtils.getPatches(subRegion, regionName, project.getProjectDirectory())));
    }//GEN-LAST:event_jCBSubRegionsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jCBPatches;
    private javax.swing.JComboBox jCBSubRegions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTFZName;
    // End of variables declaration//GEN-END:variables

    public static class FZParams
    {
        private String zoneName;
        private String subRegionName;
        private String patchName;

        /**
         * @return the zoneName
         */
        public String getZoneName() {
            return zoneName;
        }

        /**
         * @param zoneName the zoneName to set
         */
        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }

        /**
         * @return the subRegionName
         */
        public String getSubRegionName() {
            return subRegionName;
        }

        /**
         * @param subRegionName the subRegionName to set
         */
        public void setSubRegionName(String subRegionName) {
            this.subRegionName = subRegionName;
        }

        /**
         * @return the patchName
         */
        public String getPatchName() {
            return patchName;
        }

        /**
         * @param patchName the patchName to set
         */
        public void setPatchName(String patchName) {
            this.patchName = patchName;
        }
    }
}
