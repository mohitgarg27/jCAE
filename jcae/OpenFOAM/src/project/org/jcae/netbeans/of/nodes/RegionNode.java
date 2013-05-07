/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.AddSubRegionAction;
import project.org.jcae.netbeans.of.actions.RemoveRegionAction;
import project.org.jcae.netbeans.of.actions.RenameRegionAction;

/**
 *
 * @author mogargaa65
 */
public class RegionNode extends AbstractNode {
   
    public static final String REGION_ICON = "project/org/jcae/netbeans/of/resources/RegionIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    private String rName;
    
    public RegionNode(String rName, Project pr)
    {
        super(new RegionChildren(rName, pr), new MyLookup());
        this.project = project;
        this.rName = rName; 
        setIconBaseWithExtension(REGION_ICON);
        setDisplayName(rName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);        
        instanceContent.add(this);        
        instanceContent.add(getChildren());       
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(REGION_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(REGION_ICON) ;
    }    
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    //CommonProjectActions.newFileAction(),
                    //CommonProjectActions.copyProjectAction(),
                    //Utilities.actionsForPath("Actions/Project/").get(0), 
                    ((Action) new AddSubRegionAction()),
                    ((Action) new RemoveRegionAction()),
                    ((Action) new RenameRegionAction())
                };
    } 
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            // The line below is causing bug
            //sheet.put(ProjectUtils.createRegionSheetSet(rName, project.getProjectDirectory()));
            return sheet;
    }    

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }

    /**
     * @param rName the rName to set
     */
    public void setrName(String rName) {
        this.rName = rName;
    }
}
