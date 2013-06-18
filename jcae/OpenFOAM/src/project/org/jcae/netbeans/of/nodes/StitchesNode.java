/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;

import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author mogargaa65
 */
public class StitchesNode extends AbstractNode 
{

    private String rName;
    private final String PATCHES_ICON="project/org/jcae/netbeans/of/resources/StitchesNode.gif";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    
    public StitchesNode(String rName, Project pr) 
    {
        super(new StitchesChildren(rName, pr), new MyLookup());
        setDisplayName("Stitches");        
        project = pr;
        this.rName = rName;
        
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);
        instanceContent.add(this);
        instanceContent.add(getChildren());
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(PATCHES_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(PATCHES_ICON) ;
    }

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }
       
}
