/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RegionChildren extends Children.Array  {

    public RegionChildren(String name, Project pr) {
        super( Arrays.asList(RegionChildren.generateNodes(name, pr)) );
    }
    
    public void addChildren(Collection<Node> nodes)
    {
        
        Collection<Node> set = new ArrayList<Node>();
        for(Node n:getNodes())
                set.add(n);

        for(Node n:nodes)
                set.add(n);

        //nodes.clear();
        //for(Node n:set)
        //        nodes.add(n);
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

    public void renameChildren( Node node, String newName)
    {
        
        Collection<Node> set = new ArrayList<Node>();
        for(Node n:getNodes())
        {
            if(node==n)
            {
                ((SubRegionNode) node ).setsName(newName);
                ((SubRegionNode) node ).setDisplayName(newName);
            }
            set.add(n);
        }
                

        this.nodes = set;
        refresh();
    }   
    
    private static ZonesNode getZonesNodes(String rName, Project pr)
    {        
        
        return new ZonesNode(rName, pr);
    }
    
    private static Collection<SubRegionNode> getSubRegionNodes(String rName, Project pr)
    {
        Collection<SubRegionNode> toReturn = new ArrayList<SubRegionNode>();
        Collection<String> subRegions = ProjectUtils.getSubRegions(rName, pr.getProjectDirectory());
        
        for(String sName: subRegions)
            toReturn.add(new SubRegionNode(sName, rName, pr));
        
        return toReturn;
    }
    
    private static Node[] generateNodes(String rName, Project pr)
    {
        Collection<SubRegionNode> srNodes = getSubRegionNodes(rName, pr);
        List<Node> toReturn = new ArrayList<Node>();
        
        if(srNodes!=null)
            for(SubRegionNode n:srNodes) 
            {
                toReturn.add((Node) n);
            }       
        toReturn.add(0,getZonesNodes(rName, pr));
        
        return toReturn.toArray(new Node[]{});
    }    
}
