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
public class BGBlockNode extends AbstractNode 
{
    private String sName;
    private String rName;
    private final String BGBLOCK_ICON="project/org/jcae/netbeans/of/resources/BGBlockIcon.png";
    
    public BGBlockNode(String sName, String rName, Project pr) 
    {
        super(new BGBlockChildren(sName, rName, pr));
        this.sName = sName;
        this.rName = rName;
        setDisplayName("BG Block"); 
    }
    
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(BGBLOCK_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(BGBLOCK_ICON) ;
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
