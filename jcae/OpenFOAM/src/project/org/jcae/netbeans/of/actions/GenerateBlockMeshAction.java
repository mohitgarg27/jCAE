/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.BGBlockChildren;
import project.org.jcae.netbeans.of.nodes.BGBlockNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class GenerateBlockMeshAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { Project.class, BGBlockNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        // Add region element in Project.xml
        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        BGBlockNode bgBlockNode = activatedNodes[0].getLookup().lookup(BGBlockNode.class);
        BGBlockChildren bgChild = activatedNodes[0].getLookup().lookup(BGBlockChildren.class);
        
        BGBlockPanel bgPanel = new BGBlockPanel(bgBlockNode.getrName(), bgBlockNode.getsName(), pr);
        bgPanel.loadPanel();
        if(bgPanel.showDialog())
        {
            BGBlockPanel.BGBlock bgNewParams = bgPanel.getBGBlock();
            ProjectUtils.setBlockMeshInSubRegion(bgNewParams, bgBlockNode.getrName(), bgBlockNode.getsName(), pr.getProjectDirectory());
            Node[] nodes = BGBlockChildren.generateNodes(bgBlockNode.getsName(), bgBlockNode.getrName(), pr);
            bgChild.addChildren(Arrays.asList(nodes));
        }        
    }

    @Override
    public String getName() 
    {
        return "BackGround Mesh";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
