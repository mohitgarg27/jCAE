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
class PatchesChildren extends Children.Array
{

    public PatchesChildren(String sName, String rName, Project pr) 
    {
        super(Arrays.asList(PatchesChildren.generateNodes(sName, rName, pr)));        
    }
    
    private static Node[] generateNodes( String sName, String rName, Project pr)
    {
        Collection<PatchNode> pNodes = getPatchNodes(sName, rName, pr);
               
        List<Node> toReturn = new ArrayList<Node>();
        if(pNodes!=null)
            for(PatchNode n:pNodes)
            {
                toReturn.add((Node) n);
            }     
        
        return toReturn.toArray(new Node[]{});
    }      
    
    private static Collection<PatchNode> getPatchNodes(String sName, String rName, Project pr)
    {
        Collection<PatchNode> pNodes  = new ArrayList<PatchNode>();
        
        Collection<String> patches = ProjectUtils.getPatches(sName, rName, pr.getProjectDirectory());
        
        if(patches!=null)
        {
            for (String pName:patches)
            {
                pNodes.add(new PatchNode(pName, sName, rName, pr));
            }
        }
        
        return pNodes;
    }
}
