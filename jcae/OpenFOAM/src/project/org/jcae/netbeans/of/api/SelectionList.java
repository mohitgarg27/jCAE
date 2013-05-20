/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.api;

import java.util.Collection;

/**
 *
 * @author mita
 */
public class SelectionList 
{

    private String source;
    private String tag;
    
    private Collection<String> list = null;

    public SelectionList() 
    {
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the list
     */
    public Collection<String> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(Collection<String> list) {
        this.list = list;
    }
    
    
}
