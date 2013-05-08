/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.nodes.PatchesChildren;
import project.org.jcae.netbeans.of.nodes.RegionChildren;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RenamePatchAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
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
        PatchesChildren pcNode = activatedNodes[0].getParentNode().getLookup().lookup(PatchesChildren.class);
        
        String s = (String)JOptionPane.showInputDialog( null, "Rename","Rename Patch", JOptionPane.PLAIN_MESSAGE, null, null, pNode.getpName());
                
        try {
            ProjectUtils.updatePatchElement(s, pNode.getpName(), pNode.getsName(), pNode.getrName(), project.getProjectDirectory());
            pcNode.renameChildren(pNode, s);
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public String getName() 
    {
        return "Rename";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
