/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.FaceZoneNode;
import project.org.jcae.netbeans.of.nodes.StitchesChildren;
import project.org.jcae.netbeans.of.nodes.ZonesChildren;
import project.org.jcae.netbeans.of.nodes.ZonesNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RemoveFaceZoneAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { FaceZoneNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        FaceZoneNode fNode = activatedNodes[0].getLookup().lookup(FaceZoneNode.class);   
        ZonesNode zNode = (ZonesNode) fNode.getParentNode();
        ZonesChildren zChild = (ZonesChildren) zNode.getLookup().lookup(ZonesChildren.class);
        
         int n = JOptionPane.showConfirmDialog(null, "Are you sure?","Deleting Zone", JOptionPane.YES_NO_OPTION);
        // n=0 means true
        
        if(n==0)
        {
            ProjectUtils.removeZoneElement(fNode.getDisplayName(), zNode.getrName(), project.getProjectDirectory());
            zChild.removeChildren(fNode);
            // Remove StitchNode
        }
        
    }

    @Override
    public String getName() 
    {
        return "Remove";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
