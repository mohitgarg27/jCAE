/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.StitchNode;
import project.org.jcae.netbeans.of.nodes.StitchesChildren;
import project.org.jcae.netbeans.of.nodes.StitchesNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RemoveStitchAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { StitchNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        StitchNode sNode = activatedNodes[0].getLookup().lookup(StitchNode.class);   
        StitchesNode ssNode = (StitchesNode) sNode.getParentNode();
        StitchesChildren sChild = (StitchesChildren) ssNode.getLookup().lookup(StitchesChildren.class);
        
         int n = JOptionPane.showConfirmDialog(null, "Are you sure?","Deleting Region", JOptionPane.YES_NO_OPTION);
        // n=0 means true
        
        if(n==0)
        {
            ProjectUtils.removeStitchElement(sNode.getsName(), sNode.getrName(), project.getProjectDirectory());
            sChild.removeChildren(sNode);
            // Remove StitchNode
        }
        
        
//        PatchesChildren pcNode = activatedNodes[0].getParentNode().getLookup().lookup(PatchesChildren.class);
//               
//        int n = JOptionPane.showConfirmDialog(null, "Deleting "+pNode.getpName() +": Are you sure?","Deleting Patch", JOptionPane.YES_NO_OPTION);
//        // n=0 means true
//        
//        if(n==0)
//        {
//            try {
//                ProjectUtils.removePatchElement(pNode.getpName(), pNode.getsName(), pNode.getrName() ,project.getProjectDirectory());
//                pcNode.removeChildren(pNode);
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (TransformerConfigurationException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (TransformerException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
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
