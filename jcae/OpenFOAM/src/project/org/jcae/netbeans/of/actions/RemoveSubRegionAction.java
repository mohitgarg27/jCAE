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
public class RemoveSubRegionAction extends CookieAction
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
        
        int n = JOptionPane.showConfirmDialog(null, "Deleting "+srNode.getsName() +": Are you sure?","Deleting SubRegion", JOptionPane.YES_NO_OPTION);
        // n=0 means true
        
        if(n==0)
        {
            try {
                ProjectUtils.removeSubRegionElement(srNode.getsName(), srNode.getrName() ,project.getProjectDirectory());
                rcNode.removeChildren(srNode);
            } catch (TransformerConfigurationException ex) {
                Exceptions.printStackTrace(ex);
            } catch (TransformerException ex) {
                Exceptions.printStackTrace(ex);
            }
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
