/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.nodes.Node;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.AddRegionAction;

/**
 *
 * @author mogargaa65
 */
public class ProjectNode extends AbstractNode {
    
    Project project;
    private final InstanceContent instanceContent = new InstanceContent();
        
    @StaticResource()
    public static final String PROJECT_ICON = "project/org/jcae/netbeans/of/resources/OpenFOAMIcon.gif";

    public ProjectNode(Project project)
    {
        super(new ProjectChildren(project), new MyLookup() );
        this.project = project;        
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);
        instanceContent.add(project);
        instanceContent.add(getChildren());        
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(PROJECT_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(PROJECT_ICON) ;
    }
    
    @Override
    public String getDisplayName() {
        return project.getProjectDirectory().getName();
    }
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    //CommonProjectActions.newFileAction(),
                    //CommonProjectActions.copyProjectAction(),
                    //Utilities.actionsForPath("Actions/Project/").get(0), 
                    ( SystemAction.get( AddRegionAction.class)),
                    CommonProjectActions.deleteProjectAction(),                    
                    CommonProjectActions.closeProjectAction()
                };
    } 
    
    public void AddRegions()
    {
        getChildren().add(new Node[] { new AbstractNode(Children.LEAF)});
//            if(geomNode!=null)
//                    getChildren().remove(new Node[]{geomNode});
//            if(getMesh().getGeometryFile()!=null)
//            {
//                    geomNode=createGeomNode(getMesh().getGeometryFile());
//                    getChildren().add(new Node[] { geomNode } );
//            }

    }
    
}
