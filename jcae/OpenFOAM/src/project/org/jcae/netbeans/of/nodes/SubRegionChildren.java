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
import org.jcae.netbeans.cad.GeomUtils;
import org.jcae.netbeans.cad.NbShape;
import org.jcae.netbeans.cad.ShapeChildren;
import org.jcae.netbeans.cad.ShapeNode;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class SubRegionChildren extends Children.Array{

    private static class SortedNode implements Comparable<SortedNode>
    {
            private Node node;

            public SortedNode(Node node)
            {
                    this.node = node;
            }

            public Node getNode()
            {
                    return node;
            }

            public int compareTo(SortedNode t) {
                return node.getDisplayName().compareTo(t.node.getDisplayName());
            }

    }
        
    Project project;
    
    public SubRegionChildren( String sName, String rName, Project pr) 
    {
        super( Arrays.asList(SubRegionChildren.generateNodes(sName, rName, pr)));
    }
    
    public void addChildren(Collection<Node> nodes)
    {
//        TreeSet<SortedNode> set = new TreeSet<SortedNode>();
//        for(Node n:getNodes())
//                set.add(new SortedNode(n));
//
//        for(Node n:nodes)
//                set.add(new SortedNode(n));
//
//        nodes.clear();
//        for(SortedNode n:set)
//                nodes.add(n.getNode());
//        refresh();
        
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
        refresh();
    }
    
    private static Collection<SubRegionNode> getSubRegionNodes(String rName, String sName, Project pr)
    {
        Collection<SubRegionNode> toReturn = new ArrayList<SubRegionNode>();
        Collection<String> subRegions = ProjectUtils.getSubRegions(rName, sName, pr.getProjectDirectory());
        
        for(String srName: subRegions)
            toReturn.add(new SubRegionNode(srName, rName, pr));
        
        return toReturn;
    }
        
    private static Node[] generateNodes(String sName, String rName, Project pr)
    {
        List<Node> toReturn = new ArrayList<Node>();

        toReturn.add(new BGBlockNode(sName, rName, pr));
        toReturn.add(new PatchesNode(sName, rName, pr));
        
        Collection<SubRegionNode> subRegionMerged = getSubRegionNodes(rName, sName, pr);
        for(SubRegionNode srNode: subRegionMerged)
        {
            toReturn.add(srNode);
        }
        
        return toReturn.toArray(new Node[]{});        
    }
    
}
