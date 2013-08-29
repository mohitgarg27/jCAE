/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jcae.netbeans.cad.NbShape;
import org.jcae.netbeans.cad.ViewShapeCookie;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;

/**
 *
 * @author mogargaa65
 */
public class ViewPatchAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { PatchNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        PatchNode pNode = activatedNodes[0].getLookup().lookup(PatchNode.class);        
        NbShape shape = activatedNodes[0].getLookup().lookup(NbShape.class);
        final ViewShapeCookie vs = activatedNodes[0].getLookup().lookup(ViewShapeCookie.class);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                vs.view();
            }
        });
        
        //JOptionPane.showMessageDialog(null, "Viewing not implemented");        
        
    }

    @Override
    public String getName() 
    {
        return "View";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
