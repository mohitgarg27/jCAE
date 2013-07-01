/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Collection;
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
public class AddRegionAction extends CookieAction
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
        // Add region element in Project.xml
        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        ProjectChildren prChild = activatedNodes[0].getLookup().lookup(ProjectChildren.class);
        
        AddRegionPanel a = new AddRegionPanel();
        //a.setVisible(true);
        a.showDialog();
        String rName = a.getRegionName();
        String rType = a.getRegionType();
        
        if(rName.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, null, "No name provided", MODE_ONE);
            return;
        }
        try 
        {
            ProjectUtils.addRegionElement(rName, rType, pr.getProjectDirectory());
            RegionNode rNode = new RegionNode(rName, rType, pr);
            Collection<Node> nColl = new ArrayList<Node>();
            nColl.add(rNode);
            prChild.addChildren(nColl);            
            
            // update node's children
        } catch (TransformerConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TransformerException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }

    @Override
    public String getName() 
    {
        return "Add Region";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
