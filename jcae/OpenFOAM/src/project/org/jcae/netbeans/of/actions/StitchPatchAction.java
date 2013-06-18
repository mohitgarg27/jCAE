/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.nodes.RegionNode;
import project.org.jcae.netbeans.of.nodes.StitchNode;
import project.org.jcae.netbeans.of.nodes.StitchesChildren;
import project.org.jcae.netbeans.of.nodes.StitchesNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class StitchPatchAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ALL;
    }

    @Override
    protected boolean enable(Node[] activatedNodes)
    {
        if(activatedNodes.length==2)
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
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        PatchNode pNode1 = activatedNodes[0].getLookup().lookup(PatchNode.class);
        PatchNode pNode2 = activatedNodes[1].getLookup().lookup(PatchNode.class);
        
        String stitchName = JOptionPane.showInputDialog("Stitch Name");
        if(stitchName=="")
            return;
             
        // 1. Add <Stitch> tag in Project.xml
        ProjectUtils.addStitchElement(stitchName, pNode1.getpName(), pNode2.getpName(), pNode1.getrName(), project.getProjectDirectory());
        
        // 2. Add Node under Stitches
        Node regionNode = null;
        Node n = pNode1.getParentNode();
        for(int i=0; i<3; i++)
        {
            n = n.getParentNode();
            if(n instanceof RegionNode)
            {
                regionNode = (RegionNode) n;
                break;
            }                    
        }
        
        if(regionNode!=null)
        {
            StitchesNode sNode = null;
            StitchesChildren sChild = null;
            for(Node sn: regionNode.getChildren().getNodes())
            {
                if(sn instanceof StitchesNode)
                {
                    sNode = (StitchesNode) sn;
                    break;
                }
            }
            
            if(sNode!=null)
            {
                sChild = sNode.getLookup().lookup(StitchesChildren.class);
                Collection<Node> nColl = new ArrayList<Node>();
                nColl.add(new StitchNode(stitchName, pNode1.getrName(), project));
                sChild.addChildren(nColl);
            }
            
        }        
    }

    @Override
    public String getName() 
    {
        return "Stitch";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
