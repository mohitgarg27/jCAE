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
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.RemovePatchAction;
import project.org.jcae.netbeans.of.actions.RenamePatchAction;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class PatchNode extends AbstractNode 
{
    private Project project;
    private String pName;
    private String sName;
    private String rName;
    
    private final String PATCH_ICON="project/org/jcae/netbeans/of/resources/PatchNodeIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    public PatchNode(String pName, String sName, String rName, Project pr)
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
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    ((Action) new RemovePatchAction()),
                    ((Action) new RenamePatchAction())
                };
    }     

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(PATCH_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(PATCH_ICON) ;
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
            sheet.put(ProjectUtils.createPatchSheetSet(getpName(), getsName(), getrName(), project.getProjectDirectory()));
            return sheet;
    }     

    /**
     * @param pName the pName to set
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * @param sName the sName to set
     */
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * @param rName the rName to set
     */
    public void setrName(String rName) {
        this.rName = rName;
    }
    
}
