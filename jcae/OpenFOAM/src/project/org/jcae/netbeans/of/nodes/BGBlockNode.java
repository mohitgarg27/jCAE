/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.BGBlockMeshAction;

/**
 *
 * @author mogargaa65
 */
public class BGBlockNode extends AbstractNode 
{
    private String sName;
    private String rName;
    private final String BGBLOCK_ICON="project/org/jcae/netbeans/of/resources/BGBlockIcon.png";
    
    private final InstanceContent instanceContent = new InstanceContent();
        
    public BGBlockNode(String sName, String rName, Project pr) 
    {
        super(new BGBlockChildren(sName, rName, pr), new MyLookup());
        this.sName = sName;
        this.rName = rName;
        setDisplayName("BG Block"); 
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);                
        instanceContent.add(pr);  
        instanceContent.add(getChildren());
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
    
    @Override
    public Action[] getActions(boolean popup) {
        
        ArrayList<Action> actions=new ArrayList<Action>();
        actions.add(new BGBlockMeshAction()) ;
        return actions.toArray(new Action[actions.size()]);
    }    
}
