/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
class RegionNode extends AbstractNode {
   
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
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            // The line below is causing Bug# https://netbeans.org/bugzilla/show_bug.cgi?id=228748
            sheet.put(ProjectUtils.createRegionSheetSet(rName, project.getProjectDirectory()));
            return sheet;
    }    
}
