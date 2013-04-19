/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.util.ImageUtilities;

/**
 *
 * @author mogargaa65
 */
public class PatchesNode extends AbstractNode {

    private String sName;
    private String rName;
    private final String PATCHES_ICON="project/org/jcae/netbeans/of/resources/PatchesNodeIcon.png";
    
    public PatchesNode(String sName, String rName, Project pr) 
    {
        super(new PatchesChildren(sName, rName, pr));
        setDisplayName("Patches");        
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
     * @return the sName
     */
    public String getsName() {
        return sName;
    }

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }
}
