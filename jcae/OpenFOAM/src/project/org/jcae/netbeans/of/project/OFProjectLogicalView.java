/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.Node;
import org.openide.filesystems.FileObject;
import project.org.jcae.netbeans.of.nodes.ProjectNode;


/**
 *
 * @author mogargaa65
 */
public class OFProjectLogicalView implements LogicalViewProvider{
    
    private final OFProject project;
    
    public OFProjectLogicalView(OFProject project) {
        this.project = project;
    }
    
    @Override
    public Node createLogicalView() 
    {
            FileObject projectDirectory = project.getProjectDirectory();            
            return new ProjectNode(project);

    }

    @Override
    public Node findPath(Node root, Object target) {
        //leave unimplemented for now
        return null;
    }
    
}
