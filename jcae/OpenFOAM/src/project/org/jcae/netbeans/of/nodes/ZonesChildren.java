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
public class ZonesChildren extends Children.Array {
    
    public ZonesChildren(String rName, Project pr) 
    {
        super(Arrays.asList(ZonesChildren.generateNodes(rName, pr)));
        
    }

    private static Collection<FaceZoneNode> getFaceZoneNodes(String rName, Project pr)
    {
        Collection<FaceZoneNode> toReturn = new ArrayList<FaceZoneNode>();
        Collection<String> fZones = ProjectUtils.getFaceZones(rName, pr.getProjectDirectory());
        
        for(String sName: fZones)
            toReturn.add(new FaceZoneNode(sName, rName, pr));
        
        return toReturn;
    }
    
    private static Collection<CellZoneNode> getCellZoneNodes(String rName, Project pr)
    {
        Collection<CellZoneNode> toReturn = new ArrayList<CellZoneNode>();
        Collection<String> cZones = ProjectUtils.getCellZones(rName, pr.getProjectDirectory());
        
        for(String sName: cZones)
            toReturn.add(new CellZoneNode(sName, rName, pr));
        
        return toReturn;
    }
    
    private static Node[] generateNodes(String rName, Project pr)
    {
        Collection<CellZoneNode> cNodes = getCellZoneNodes(rName, pr);
        Collection<FaceZoneNode> fNodes = getFaceZoneNodes(rName, pr);
        
        List<Node> toReturn = new ArrayList<Node>();
        
        if(cNodes!=null)
            for(CellZoneNode n:cNodes) 
            {
                toReturn.add((Node) n);
            }
        
        if(fNodes!=null)
            for(FaceZoneNode n:fNodes) 
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
