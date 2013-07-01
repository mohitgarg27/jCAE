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
import project.org.jcae.netbeans.of.nodes.ProjectChildren;
import project.org.jcae.netbeans.of.nodes.RegionNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RenameRegionAction extends CookieAction
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
        RegionNode rNode = activatedNodes[0].getLookup().lookup(RegionNode.class);
        String s = (String)JOptionPane.showInputDialog( null, "Rename","Rename Region", JOptionPane.PLAIN_MESSAGE, null, null, rNode.getrName());
        ProjectChildren rcNode = rNode.getParentNode().getLookup().lookup(ProjectChildren.class);
       
        if(s.equalsIgnoreCase(rNode.getrName()))
            return;
        
        try {
            ProjectUtils.updateRegionElement(s, rNode.getrName(), project.getProjectDirectory());
            rcNode.renameChildren(rNode, s);
            
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
