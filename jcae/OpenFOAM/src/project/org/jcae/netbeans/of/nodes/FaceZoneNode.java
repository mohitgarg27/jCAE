/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.RemoveFaceZoneAction;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class FaceZoneNode extends AbstractNode 
{

    private Project project;
    private String rName;
    private String fName;
    private final String FZONES_ICON="project/org/jcae/netbeans/of/resources/FaceZoneIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    public FaceZoneNode(String fName, String rName, Project pr) 
    {
        super(Children.LEAF, new MyLookup());
        this.fName = fName;
        this.rName = rName;
        this.project = pr;
        setDisplayName(fName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);                
        instanceContent.add(pr);        
    }

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(FZONES_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(FZONES_ICON) ;
    }    

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }
    
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            sheet.put(ProjectUtils.createFaceZoneSheetSet(fName, rName, project.getProjectDirectory()));
            return sheet;
    }   
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    SystemAction.get(RemoveFaceZoneAction.class)      
                };
    }     
}
