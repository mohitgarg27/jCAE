/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.options;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.netbeans.api.project.Project;
import org.openide.awt.ColorComboBox;
import project.org.jcae.netbeans.of.api.Property;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mita
 */
public class PatchBasicInfo extends javax.swing.JPanel 
{

    public class SubPanel extends JPanel 
    {
        
        public SubPanel()
        {
            //setLayout(new GridLayout(0,2));
            //this.patchSelected = patchSelected;
            //initComponents1();
            
            //setBackground(Color.white);
            setBorder(BorderFactory.createTitledBorder("Properties")); 
        }
        
        public void initComponents() 
        {
            Collection<Property> collProp = ProjectUtils.getBasePatchTypeProperties(patchSelected, pNode, pr.getProjectDirectory());
            
            // Create panel based on collProp
            if(collProp!=null)
            {
                setLayout(new GridLayout(collProp.size(), 1));
                
                for(int i=0;i<collProp.size();i++)
                {
                    Property p = ((ArrayList<Property>) collProp).get(i);
                    add(p.loadGraphics());
                }
//                setLayout(new GridLayout(collProp.size(), 2));
//                for(int i=0;i<collProp.size();i++)
//                {
//                    Property p = ((ArrayList<Property>) collProp).get(i);
//                    JLabel label = new JLabel(p.getKey());                    
//                    label.setVisible(true);
//                    add(label);
//                    if(p.getSl()!=null)
//                    {
//                        JComboBox comboBox = new JComboBox( p.getSl().getList().toArray(new String[p.getSl().getList().size()]) );
//                        comboBox.setVisible(true);
//                        this.add(comboBox);
//                    }
//                    else
//                    {                        
//                        JTextField textField = new JTextField();
//                        if(p.getVal().equalsIgnoreCase(""))
//                            textField.setText(p.getDefVal());
//                        else
//                            textField.setText(p.getVal());
//                        add(textField);
//                    }                        
//                }
            }
        }        
    }
    
    PatchNode pNode;
    Project pr;
    String patchSelected;
    /**
     * Creates new form PatchBasicInfo
     */
    public PatchBasicInfo(PatchNode pNode, Project pr) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Basic Type"));                
        this.pNode = pNode;
        this.pr = pr;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox(ProjectUtils.getBasePatches());
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new SubPanel();

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PatchBasicInfo.class, "PatchBasicInfo.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        patchSelected = (String) jComboBox1.getSelectedItem();
        //jPanel1 = new SubPanel();
        ((SubPanel)jPanel1).removeAll();
        ((SubPanel)jPanel1).repaint();
        ((SubPanel)jPanel1).initComponents();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
