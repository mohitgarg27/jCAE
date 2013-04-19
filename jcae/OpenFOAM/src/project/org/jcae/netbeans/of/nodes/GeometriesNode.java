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
public class GeometriesNode extends AbstractNode {
    
    private final String GEOMS_ICON="project/org/jcae/netbeans/of/resources/ModelNode.png";
    
    public GeometriesNode(Project project) {
        
        super(new GeometriesChildren(project));
        setDisplayName("Geometries");
        setIconBaseWithExtension("project/org/jcae/netbeans/of/resources/ModelNode.png");
    }

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(GEOMS_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(GEOMS_ICON) ;
    }
}
