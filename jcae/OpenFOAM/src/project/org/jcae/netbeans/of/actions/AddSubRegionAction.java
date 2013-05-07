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
import project.org.jcae.netbeans.of.nodes.RegionChildren;
import project.org.jcae.netbeans.of.nodes.RegionNode;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class AddSubRegionAction extends CookieAction
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
        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        RegionNode rNode = activatedNodes[0].getLookup().lookup(RegionNode.class);
        RegionChildren rChild = activatedNodes[0].getLookup().lookup(RegionChildren.class);
        
        
        AddSubRegionPanel a = new AddSubRegionPanel();
        //a.setVisible(true);
        a.showDialog();
        String srName = a.getRegionName();
        
        if(srName.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, null, "No name provided", MODE_ONE);
            return;
        }
        try 
        {
            ProjectUtils.addSubRegionElement(srName, rNode.getrName(), pr.getProjectDirectory());
            SubRegionNode srNode = new SubRegionNode(srName, rNode.getrName(), pr);
            Collection<Node> nColl = new ArrayList<Node>();
            nColl.add(srNode);
            rChild.addChildren(nColl);
            
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
        return "Add SubRegion";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
