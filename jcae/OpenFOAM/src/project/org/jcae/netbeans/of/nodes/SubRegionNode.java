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
import project.org.jcae.netbeans.of.actions.RemoveSubRegionAction;
import project.org.jcae.netbeans.of.actions.RenameSubRegionAction;
import project.org.jcae.netbeans.of.actions.SnappyHexMeshAction;

/**
 *
 * @author mogargaa65
 */
public class SubRegionNode extends AbstractNode {
    
    public static final String SUBREGION_ICON = "project/org/jcae/netbeans/of/resources/SubRegionIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    private String rName;
    private String sName;
    
    public SubRegionNode(String sName, String rName, Project pr)
    {
        super(new SubRegionChildren(sName, rName, pr), new MyLookup());
        this.project = project;
        this.rName = rName;
        this.sName = sName;
        setDisplayName(sName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);          
        instanceContent.add(this);        
        instanceContent.add(getChildren());       
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(SUBREGION_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(SUBREGION_ICON) ;
    }    
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    //CommonProjectActions.newFileAction(),
                    //CommonProjectActions.copyProjectAction(),
                    //Utilities.actionsForPath("Actions/Project/").get(0), 
                    ((Action) new RemoveSubRegionAction()),
                    ((Action) new RenameSubRegionAction()),
                    ((Action) new SnappyHexMeshAction())
                };
    }     
    
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            // The line below is causing a Bug
            //sheet.put(ProjectUtils.createSubRegionSheetSet(sName, rName, project.getProjectDirectory()));
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

    /**
     * @return the sName
     */
    public String getsName() {
        return sName;
    }

    /**
     * @param sName the sName to set
     */
    public void setsName(String sName) {
        this.sName = sName;
    }
}
