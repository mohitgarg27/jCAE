/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.io.File;
import javax.swing.JOptionPane;
import org.jcae.mesh.xmldata.MeshExporter;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.project.BrepToSTL;

/**
 *
 * @author mogargaa65
 */
public class ViewPatchAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { PatchNode.class };
    }

    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        Project project = activatedNodes[0].getLookup().lookup(Project.class);
        PatchNode pNode = activatedNodes[0].getLookup().lookup(PatchNode.class);        

        // View code
        JOptionPane.showMessageDialog(null, "Viewing...");
        
        // Sample code to test amibe
        //BrepToSTL b = new BrepToSTL(new File("/home/mita/Downloads/cdce/sample1/s1/Face1.brep"));
        //b.performAction();
        File fo = new File("/home/mita/Downloads/cdce/sample1/s1/Face1");
        new MeshExporter.STL(fo.getAbsolutePath()).write("/home/mita/Downloads/cdce/sample1/s1/Face1.stl");
        
    }

    @Override
    public String getName() 
    {
        return "View";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
