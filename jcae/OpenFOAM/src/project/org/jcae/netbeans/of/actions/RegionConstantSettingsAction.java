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
public class RegionConstantSettingsAction extends CookieAction
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
        
        if(rNode.getrType().equalsIgnoreCase("solid"))
        {
            RegionSolidConstantSettingsPanel s = new RegionSolidConstantSettingsPanel(rNode.getrName(), project.getProjectDirectory());
            if(s.showDialog())
            {
                s.save();
            }
        }
        else if(rNode.getrType().equalsIgnoreCase("fluid"))
        {
            RegionFluidConstantSettingsPanel s = new RegionFluidConstantSettingsPanel(rNode.getrName(), project.getProjectDirectory());
            if(s.showDialog())
            {
                s.save();
            }            
        }
        
        
    }

    @Override
    public String getName() 
    {
        return "Constant Settings";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
