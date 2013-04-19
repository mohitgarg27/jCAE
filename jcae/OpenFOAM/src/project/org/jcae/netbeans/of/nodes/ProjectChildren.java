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
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class ProjectChildren extends Children.Array  {

    Project project;
    public ProjectChildren(Project project)
    {
        super( Arrays.asList(ProjectChildren.generateNodes(project)) );
        this.project = project;           
        
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
    
    private static GeometriesNode getGeometriesNodes(Project pr)
    {        
        return new GeometriesNode(pr);
    }
    
    private static Collection<RegionNode> getRegionNodes(Project pr)
    {
        Collection<RegionNode> toReturn = new ArrayList<RegionNode>();
        Collection<String> regions = ProjectUtils.getRegions(pr.getProjectDirectory());
        
        for(String rName: regions)
            toReturn.add(new RegionNode(rName, pr));
        
        return toReturn;
    }
    
    private static Node[] generateNodes(Project pr)
    {
        Collection<RegionNode> rNodes = getRegionNodes(pr);
        List<Node> toReturn = new ArrayList<Node>();
        
        if(rNodes!=null)
            for(RegionNode n:rNodes) 
            {
                toReturn.add((Node) n);
            }       
        toReturn.add(0,getGeometriesNodes(pr));
        
        return toReturn.toArray(new Node[]{});
    }
       
}
