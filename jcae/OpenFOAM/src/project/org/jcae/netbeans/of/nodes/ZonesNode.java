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
class ZonesNode extends AbstractNode
{
    
    private Project project;
    private String rName;
    private final String ZONES_ICON="project/org/jcae/netbeans/of/resources/ZonesIcon.jpg";
    private final InstanceContent instanceContent = new InstanceContent();
    
    public ZonesNode(String rName, Project pr) {
        
        super(new ZonesChildren(rName, pr), new MyLookup());
        this.project = pr;
        this.rName = rName;
        setDisplayName("Zones");
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);
        instanceContent.add(getChildren());
        instanceContent.add(pr);        
    }

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(ZONES_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(ZONES_ICON) ;
    }

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }
}
