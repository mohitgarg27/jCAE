/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.nodes.AbstractNode;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.project.ProjectFileUtils;

/**
 *
 * @author mogargaa65
 */
public class GeometriesNode extends AbstractNode {
    
    private final String GEOMS_ICON="project/org/jcae/netbeans/of/resources/ModelNode.png";
    private Project  project;
    private final InstanceContent instanceContent = new InstanceContent();
    
    public GeometriesNode(Project project) {
        
        super(new GeometriesChildren(project), new MyLookup());
        this.project = project;
        setDisplayName("Geometries");
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);        
        instanceContent.add(getChildren()); 
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
    
    
    private class ImportAction extends AbstractAction 
    {

        public ImportAction() 
        {
            putValue(NAME, "Import Brep");
        }

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            File f = new FileChooserBuilder(GeometriesNode.class).setTitle("Select Brep File").showOpenDialog();
            String path1 = project.getProjectDirectory().getFileObject("Geometries").getPath();
            String path2 = f.getAbsolutePath();
            ProjectFileUtils.copyFile(f.getAbsolutePath(), project.getProjectDirectory().getFileObject("Geometries").getPath().concat("/").concat(f.getName()) );

            // Update Geometries node
            ((GeometriesChildren)getChildren()).refreshNodes();
            
        }        
    }  
    
    
    private class RefreshAction extends AbstractAction 
    {

        public RefreshAction() 
        {
            putValue(NAME, "Refresh");
        }

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            // Update Geometries node
            ((GeometriesChildren)getChildren()).refreshNodes();
            
        }        
    }    
        
    @Override
    public Action[] getActions(boolean popup) {
        
        ArrayList<Action> actions=new ArrayList<Action>();
        actions.add(new ImportAction()) ;
        actions.add(new RefreshAction()) ;
        return actions.toArray(new Action[actions.size()]);
    }
}
