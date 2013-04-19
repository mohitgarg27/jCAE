/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import org.netbeans.api.project.Project;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author mogargaa65
 */
public class SubRegionChildren extends Children.Array{

    Project project;
    
    public SubRegionChildren( String sName, String rName, Project pr) 
    {
        super( Arrays.asList(SubRegionChildren.generateNodes(sName, rName, pr)));
    }
    
    public void addChildren(Collection<Node> nodes)
    {
        TreeSet<Node> set = new TreeSet<Node>();
        for(Node n:getNodes())
                set.add(n);

        for(Node n:nodes)
                set.add(n);

        nodes.clear();
        for(Node n:set)
                nodes.add(n);
        refresh();
    }
        
    private static Node[] generateNodes(String sName, String rName, Project pr)
    {
        List<Node> toReturn = new ArrayList<Node>();

        toReturn.add(new BGBlockNode(sName, rName, pr));
        toReturn.add(new PatchesNode(sName, rName, pr));

        return toReturn.toArray(new Node[]{});        
    }
    
}
