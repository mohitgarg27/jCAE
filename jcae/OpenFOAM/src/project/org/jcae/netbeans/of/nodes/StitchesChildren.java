/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class StitchesChildren extends Children.Array 
{
    
    public StitchesChildren(String rName, Project pr) 
    {
        super(Arrays.asList(StitchesChildren.generateNodes(rName, pr)));        
    }

    private static Collection<StitchNode> getStitchNodes(String rName, Project pr)
    {
        Collection<StitchNode> toReturn = new ArrayList<StitchNode>();
        Collection<String> stitches = ProjectUtils.getStitches(rName, pr.getProjectDirectory());
        
        for(String sName: stitches)
            toReturn.add(new StitchNode(sName, rName, pr));
        
        return toReturn;
    }
    
    private static Node[] generateNodes(String rName, Project pr)
    {
        Collection<StitchNode> sNodes = getStitchNodes(rName, pr);
        
        List<Node> toReturn = new ArrayList<Node>();
        
        if(sNodes!=null)
            for(StitchNode n:sNodes) 
            {
                toReturn.add((Node) n);
            }
        
        return toReturn.toArray(new Node[]{});
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
    
    public void removeChildren( Node node)
    {
        
        Collection<Node> set = new ArrayList<Node>();
        for(Node n:getNodes())
        {
            if(node!=n)
            set.add(n);
        }
                
        this.nodes = set;
        try {
            node.destroy();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        refresh();
    }     
    
}
