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
import project.org.jcae.netbeans.of.nodes.RegionChildren;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RenameSubRegionAction extends CookieAction
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
        String s = (String)JOptionPane.showInputDialog( null, "Rename","Rename SubRegion", JOptionPane.PLAIN_MESSAGE, null, null, srNode.getsName());
        RegionChildren rcNode = srNode.getParentNode().getLookup().lookup(RegionChildren.class);
        
        try {
            ProjectUtils.updateSubRegionElement(s, srNode.getsName(), srNode.getrName(), project.getProjectDirectory());
            rcNode.renameChildren(srNode, s);
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
