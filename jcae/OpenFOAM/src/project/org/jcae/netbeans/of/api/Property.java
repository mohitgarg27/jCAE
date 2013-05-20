/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.api;

/**
 *
 * @author mita
 */
public class Property 
{
    private String name;
    private String defVal;
    private String val;
    private SelectionList sl;

    public Property() 
    {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
    
    
    
}
