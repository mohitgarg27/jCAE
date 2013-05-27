/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class BGBlockChildren extends Children.Array
{

    public BGBlockChildren(String sName, String rName, Project pr) 
    {
        super(Arrays.asList(BGBlockChildren.generateNodes(sName, rName, pr))); 
    }
    
    public static Node[] generateNodes( String sName, String rName, Project pr)
    {
        Collection<BGPatchNode> pNodes = getBGPatchNodes(sName, rName, pr);
               
        List<Node> toReturn = new ArrayList<Node>();
        if(pNodes!=null)
            for(BGPatchNode n:pNodes)
            {
                toReturn.add((Node) n);
            }     
        
        return toReturn.toArray(new Node[]{});
    }      
    
    private static Collection<BGPatchNode> getBGPatchNodes(String sName, String rName, Project pr)
    {
        Collection<BGPatchNode> pNodes  = new ArrayList<BGPatchNode>();
        
        Collection<String> patches = ProjectUtils.getBGPatches(sName, rName, pr.getProjectDirectory());
        
        if(patches!=null)
        {
            for (String pName:patches)
            {
                pNodes.add(new BGPatchNode(pName, sName, rName, pr));
            }
        }
        
        return pNodes;
    }    
    
    public void addChildren(Collection<Node> nodes)
    {
        Collection<Node> set = new ArrayList<Node>();
        for(Node n:getNodes())
                set.add(n);

        for(Node n:nodes)
                set.add(n);

        this.nodes = set;
        refresh();
    }    
}
