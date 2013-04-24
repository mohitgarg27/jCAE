/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author mogargaa65
 */
public class GeometriesChildren extends Children.Array{

    Project project;
    
    public GeometriesChildren(Project p)
    {
        this.project = p;
    }
    
    @Override
    protected Collection<Node> initCollection()
    {
        //Get all files
        
        FileObject proj = project.getProjectDirectory();
        FileObject geomFolder = proj.getFileObject("Geometries");
        if(geomFolder==null)
            return null;
        
        FileObject[] geoms = geomFolder.getChildren();
        ArrayList<Node> toReturn = new ArrayList<Node>(geoms.length);

        for (FileObject f:geoms)
        {
            try {
                toReturn.add( (DataObject.find(f)).getNodeDelegate());
                //toReturn.add((new BrepDataObject(f, null)).getNodeDelegate());
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        return toReturn;
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
    
    public void refreshNodes()
    {
        Collection<Node> newNodes = initCollection();
        
        //Collection<Node> set = new ArrayList<Node>();
        //for(Node n:getNodes())
         //       set.add(n);

        //for(Node n:newNodes)
        //        set.add(n);

        nodes.clear();
        for(Node n:newNodes)
                nodes.add(n);
        
        refresh();
    }
    
    
}
