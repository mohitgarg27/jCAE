/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.RegionChildren;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;

/**
 *
 * @author mogargaa65
 */
public class MoveUpSubRegionAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { Project.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        SubRegionNode srNode = activatedNodes[0].getLookup().lookup(SubRegionNode.class);
        RegionChildren rcNode = srNode.getParentNode().getLookup().lookup(RegionChildren.class);
        
        rcNode.moveUp(srNode,srNode.getrName(),project.getProjectDirectory());
    }

    @Override
    public String getName() 
    {
        return "Move up";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
