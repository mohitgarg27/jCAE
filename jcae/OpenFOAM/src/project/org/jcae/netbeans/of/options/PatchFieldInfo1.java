/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.options;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.netbeans.api.project.Project;
import project.org.jcae.netbeans.of.api.ofProp;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mohit
 */
public class PatchFieldInfo1 extends javax.swing.JPanel {

    public class SubPanel2 extends JPanel 
    {        
        public SubPanel2()
        {           
            //setBackground(Color.white);
            setBorder(BorderFactory.createTitledBorder("Properties"));
            
        }
        
        public void initComponents() 
        {

            //setLayout(new GridLayout(0, 1));
            //JButton j = new JButton("fdscvdscsdc");
            //j.setBounds(20,20, 50,50);
            //this.add(j);
            //setBackground(Color.red);
            Collection<ofProp> collProp = ProjectUtils.getFieldPatchTypeProperties(patchSelected, pNode, pr.getProjectDirectory());
            
            // Create panel based on collProp
            if(collProp!=null)
            {
                setLayout(new GridLayout(collProp.size(), 1));
                
                for(int i=0;i<collProp.size();i++)
                {
                    ofProp ofProp = ((ArrayList<ofProp>) collProp).get(i);
                    add(ofProp.loadGraphics());
                }
            }
        }        
    }

    PatchNode pNode;
    Project pr;
    String patchSelected;
    
    /**
     * Creates new form PatchFieldInfo1
     */
    public PatchFieldInfo1(PatchNode pNode, Project pr) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("K"));
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

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox(ProjectUtils.getFieldPatches());
        jPanel1 = new SubPanel2();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PatchFieldInfo1.class, "PatchFieldInfo1.jLabel1.text")); // NOI18N

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(50, 50, 50)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        patchSelected = (String) jComboBox1.getSelectedItem();
        //jPanel1 = new SubPanel1();
        ((PatchFieldInfo1.SubPanel2)jPanel1).removeAll();
        ((PatchFieldInfo1.SubPanel2)jPanel1).repaint();
        ((PatchFieldInfo1.SubPanel2)jPanel1).initComponents();
        ((PatchFieldInfo1.SubPanel2)jPanel1).repaint();
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
