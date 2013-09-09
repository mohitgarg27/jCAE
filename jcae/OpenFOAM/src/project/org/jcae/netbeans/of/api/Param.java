/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.api;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author mohit
 */
public class Param 
{
    private String key;
    private String val=null;
    
    private JLabel jKey;
    private JComponent jComponent;

    public void loadGraphics()
    {
         jKey = new JLabel(key);
         if(val==null)
            jComponent = new JTextField("");
         else
            jComponent = new JTextField(val); 
    }
    
    public void setVals()
    {
        val = ((JTextField) jComponent).getText();
    }
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the val
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(String val) {
        this.val = val;
    }

    /**
     * @return the jKey
     */
    public JLabel getjKey() {
        return jKey;
    }

    /**
     * @param jKey the jKey to set
     */
    public void setjKey(JLabel jKey) {
        this.jKey = jKey;
    }

    /**
     * @return the jComponent
     */
    public JComponent getjComponent() {
        return jComponent;
    }

    /**
     * @param jComponent the jComponent to set
     */
    public void setjComponent(JComponent jComponent) {
        this.jComponent = jComponent;
    }
}
