/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.api;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.w3c.dom.Node;

/**
 *
 * @author mohit
 */
public class Property implements ofProp
{
    private String key;
    private String defVal;
    private String val;
    private SelectionList sl = null;
    
    private String secondaryVal;
    private String defSecondaryVal;
    
    private JLabel jKey;
    private JComponent jVal;
    private JComponent jSecondaryVal;

    private JPanel jPanel;
            
    public Property() 
    {
        
    }

    public JPanel loadGraphics()
    {
        if(secondaryVal==null)
        {
            jPanel = new JPanel(new GridLayout(1, 2 ));
            jKey = new JLabel(key);
            jPanel.add(jKey);
            
            if(sl==null)
            {
                if(val.equalsIgnoreCase(""))
                    jVal = new JTextField(defVal);
                else
                    jVal = new JTextField(val);
            }
            else
            {
                sl.loadGraphics();
                jVal = sl.getJcomponent();
                if(!val.equalsIgnoreCase(""))
                {
                    ((JComboBox)jVal).setSelectedItem(val);
                }
            }
            jPanel.add(jVal);
            
        }
        else
        {
            jPanel = new JPanel(new GridLayout(1, 3 ));
            jKey = new JLabel(key);
            jPanel.add(jKey);
            
            if(sl==null)
            {
                if(val.equalsIgnoreCase(""))
                    jVal = new JTextField(defVal);
                else
                    jVal = new JTextField(val);
            }
            else
            {
                sl.loadGraphics();
                jVal = sl.getJcomponent();
                if(!val.equalsIgnoreCase(""))
                {
                    ((JComboBox)jVal).setSelectedItem(val);
                }
            }
            jPanel.add(jVal);            
            
            if(secondaryVal.equalsIgnoreCase(""))
                jSecondaryVal = new JTextField(defSecondaryVal);
            else
                jSecondaryVal = new JTextField(secondaryVal);
            jPanel.add(jSecondaryVal);
        }
        
        return jPanel;
    }    
    
    public void setVals()
    {
        if(secondaryVal==null)
        {
            jPanel = new JPanel(new GridLayout(1, 2 ));
            jKey = new JLabel(key);
            jPanel.add(jKey);
            
            if(sl==null)
            {
                val=((JTextField)jVal).getText();
            }
            else
            {
                val =  ((JComboBox)jVal).getSelectedItem().toString();
            }            
        }
        else
        {
            if(sl==null)
            {
                val=((JTextField)jVal).getText();
            }
            else
            {
                val =  ((JComboBox)jVal).getSelectedItem().toString();
            }  
            secondaryVal = ((JTextField)jSecondaryVal).getText();            
        }
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
     * @return the defVal
     */
    public String getDefVal() {
        return defVal;
    }

    /**
     * @param defVal the defVal to set
     */
    public void setDefVal(String defVal) {
        this.defVal = defVal;
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
     * @return the sl
     */
    public SelectionList getSl() {
        return sl;
    }

    /**
     * @param sl the sl to set
     */
    public void setSl(SelectionList sl) {
        this.sl = sl;
    }

    /**
     * @return the secondaryVal
     */
    public String getSecondaryVal() {
        return secondaryVal;
    }

    /**
     * @param secondaryVal the secondaryVal to set
     */
    public void setSecondaryVal(String secondaryVal) {
        this.secondaryVal = secondaryVal;
    }

    /**
     * @return the defSecondaryVal
     */
    public String getDefSecondaryVal() {
        return defSecondaryVal;
    }

    /**
     * @param defSecondaryVal the defSecondaryVal to set
     */
    public void setDefSecondaryVal(String defSecondaryVal) {
        this.defSecondaryVal = defSecondaryVal;
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
     * @return the jVal
     */
    public JComponent getjVal() {
        return jVal;
    }

    /**
     * @param jVal the jVal to set
     */
    public void setjVal(JComponent jVal) {
        this.jVal = jVal;
    }

    /**
     * @return the jSecondaryVal
     */
    public JComponent getjSecondaryVal() {
        return jSecondaryVal;
    }

    /**
     * @param jSecondaryVal the jSecondaryVal to set
     */
    public void setjSecondaryVal(JComponent jSecondaryVal) {
        this.jSecondaryVal = jSecondaryVal;
    }
    
}
