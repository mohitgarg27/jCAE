/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
class BGPatchNode extends AbstractNode 
{

    private Project project;
    private String pName;
    private String sName;
    private String rName;
    
    private final String BGPATCH_ICON="project/org/jcae/netbeans/of/resources/BGPatchNodeIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    public BGPatchNode(String pName, String sName, String rName, Project pr)
    {        
        super(Children.LEAF, new MyLookup());
        this.project = pr;
        this.pName = pName;
        this.sName = sName;
        this.rName = rName;
        setDisplayName(pName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);                
        instanceContent.add(pr);  
    }

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(BGPATCH_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(BGPATCH_ICON) ;
    }

    /**
     * @return the pName
     */
    public String getpName() {
        return pName;
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
    
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            sheet.put(ProjectUtils.createBGPatchSheetSet(pName, sName, rName, project.getProjectDirectory()));
            return sheet;
    }     
}
