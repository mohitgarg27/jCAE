/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jcae.netbeans.options;

import org.openide.util.NbPreferences;

final public class AmibePanel extends javax.swing.JPanel {

	private final AmibeOptionsPanelController controller;

	AmibePanel(AmibeOptionsPanelController controller) {
		this.controller = controller;
		initComponents();
		// TODO listen to changes in form fields and call controller.changed()
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();

        label1.setText(org.openide.util.NbBundle.getMessage(AmibePanel.class, "AmibePanel.label1.text")); // NOI18N

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	void load() {
		// TODO read settings and initialize GUI
		// Example:        
		// someCheckBox.setSelected(Preferences.userNodeForPackage(AmibePanel.class).getBoolean("someFlag", false));
		// or for org.openide.util with API spec. version >= 7.4:
		// someCheckBox.setSelected(NbPreferences.forModule(AmibePanel.class).getBoolean("someFlag", false));
		// or:
		// someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
		jFormattedTextField1.setText(NbPreferences.forModule(AmibePanel.class).get("AmibeARThreshold", ""));
		if(jFormattedTextField1.getText().equalsIgnoreCase(""))
			jFormattedTextField1.setText("400000");
	}

	void store() {
		// TODO store modified settings
		// Example:
		// Preferences.userNodeForPackage(AmibePanel.class).putBoolean("someFlag", someCheckBox.isSelected());
		// or for org.openide.util with API spec. version >= 7.4:
		// Ignoring integer's sign
		NbPreferences.forModule(AmibePanel.class).put("AmibeARThreshold", jFormattedTextField1.getText().replace("-", ""));
		// or:
		// SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());
	}

	boolean valid() {
		// TODO check whether form is consistent and complete
		return true;
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
