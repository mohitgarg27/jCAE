/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.PatchNode;
import project.org.jcae.netbeans.of.project.ProjectUtils;

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

        //JOptionPane.showMessageDialog(null, "Viewing...");
        try {
            // Sample code to test amibe
            
            if(true)
            {
                if(false)
                {
                    ProjectUtils.generateSHMCases(project.getProjectDirectory());
                    ProjectUtils.performMeshMergers(project.getProjectDirectory());
                    ProjectUtils.performMeshStitches(project.getProjectDirectory());
                }
                ProjectUtils.performZoneCreation(project.getProjectDirectory());
            }
            //ProjectUtils.performStitches(project.getProjectDirectory());
            
            //ProjectUtils.performZoneCreation(project.getProjectDirectory());
            
            //ProjectUtils.generateSTLsInSubregion(pNode.getsName(), pNode.getrName(), project.getProjectDirectory());
            //BrepToSTL b = new BrepToSTL(new File("/home/mita/Downloads/cdce/solid/solSub2/Face3.brep"));
            //BrepToSTL b = new BrepToSTL(new File("/home/mita/Downloads/cdce/sample1/s1/Face1.brep"));
            //b.performAction();
            //b.generateSTL();
            //File fo = new File("/home/mita/Downloads/cdce/sample1/s1/Face1");
            //new MeshExporter.STL(fo.getAbsolutePath()).write("/home/mita/Downloads/cdce/sample1/s1/Face1.stl");
            
            //String s1 = ProjectUtils.createDict(pNode.getsName(), pNode.getrName(), project.getProjectDirectory(), "snap");
            //System.out.println(s1);
            //
            //String s = ProjectUtils.createBlockMeshDict(pNode.getsName(), pNode.getrName(), project.getProjectDirectory());
            //System.out.println(s);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
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
