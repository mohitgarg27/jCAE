/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.api;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.w3c.dom.Node;

/**
 *
 * @author mohit
 */
public class Function implements ofProp
{
    private String key;
    private String separator;
    private Collection<Param> params;
    
    private JLabel jKey;
    private JPanel jPanel;

    public JPanel loadGraphics()
    {        
        jKey = new JLabel(key);
        if(params!=null)
        {
            jPanel = new JPanel(new GridLayout(1, params.size()+1));
            jPanel.add(jKey);
            
            ArrayList<Param> paramsList = (ArrayList<Param>) params;
                    
            for(int i=0;i<paramsList.size();i++)
            {
                paramsList.get(i).loadGraphics();
                jPanel.add(paramsList.get(i).getjComponent());
            }
        }
        else
        {
            jPanel = new JPanel(new GridLayout(1, 1));
            jPanel.add(jKey);
        }
        return jPanel;
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
     * @return the separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * @return the params
     */
    public Collection<Param> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Collection<Param> params) {
        this.params = params;
    }

}
