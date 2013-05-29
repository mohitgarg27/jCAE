/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.Arrays;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.BGBlockChildren;
import project.org.jcae.netbeans.of.nodes.BGBlockNode;
import project.org.jcae.netbeans.of.nodes.SubRegionChildren;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class SnappyHexMeshAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { Project.class, SubRegionNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        // Add region element in Project.xml
        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        SubRegionNode srNode = activatedNodes[0].getLookup().lookup(SubRegionNode.class);
        SubRegionChildren srChild = activatedNodes[0].getLookup().lookup(SubRegionChildren.class);
        
        SnappyHexMeshSettingsPanel s = new SnappyHexMeshSettingsPanel();
        s.showDialog();
        
//        BGBlockPanel bgPanel = new BGBlockPanel(bgBlockNode.getrName(), bgBlockNode.getsName(), pr);
//        bgPanel.loadPanel();
//        if(bgPanel.showDialog())
//        {
//            BGBlockPanel.BGBlock bgNewParams = bgPanel.getBGBlock();
//            ProjectUtils.setBlockMeshInSubRegion(bgNewParams, bgBlockNode.getrName(), bgBlockNode.getsName(), pr.getProjectDirectory());
//            Node[] nodes = BGBlockChildren.generateNodes(bgBlockNode.getsName(), bgBlockNode.getrName(), pr);
//            bgChild.addChildren(Arrays.asList(nodes));
//        }        
    }

    @Override
    public String getName() 
    {
        return "SnappyHexMesh Settings";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
