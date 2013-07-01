/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.RegionNode;

/**
 *
 * @author mogargaa65
 */
public class RegionSystemSettingsAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { RegionNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        RegionNode rNode = activatedNodes[0].getLookup().lookup(RegionNode.class);
        
        RegionSystemSettingsPanel s = new RegionSystemSettingsPanel (rNode.getrName(), project.getProjectDirectory());
        if(s.showDialog())
        {
            s.save();
        }           
    }

    @Override
    public String getName() 
    {
        return "System Settings";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
