/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;

/**
 *
 * @author mogargaa65
 */
public class PatchBoundaryAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected boolean enable(Node[] activatedNodes)
    {
        if(activatedNodes.length==1)
            return true;
        
        return false;
    }
    
    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { PatchNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        PatchNode pNode = activatedNodes[0].getLookup().lookup(PatchNode.class) ;

        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        PatchBoundaryPanel s = new PatchBoundaryPanel(pNode, pNode.getrName(), pNode.getsName(), pNode.getpName(), pr);
        
        if(s.showDialog())
        {
            //SnappyHexMeshSettingsPanel.SHMParams params = s.loadParams();
            s.save();
        }
    }

    @Override
    public String getName() 
    {
        return "Boundaries";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
