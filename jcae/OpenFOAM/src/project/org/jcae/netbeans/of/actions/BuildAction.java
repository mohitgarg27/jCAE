/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import project.org.jcae.netbeans.of.project.ProjectUtils;

@ActionID(
        category = "Build",
        id = "project.org.jcae.netbeans.of.actions.BuildAction")
@ActionRegistration(
        iconBase = "project/org/jcae/netbeans/of/resources/Build.png",
        displayName = "#CTL_BuildAction")
@ActionReference(path = "Toolbars/File", position = 0)
@Messages("CTL_BuildAction=Build")
public final class BuildAction implements ActionListener {

    private final Project project;

    public BuildAction(Project context) 
    {
        this.project = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) 
    {
        // TODO use context
        try 
        {
                ProjectUtils.generateSHMCases(project.getProjectDirectory());
                ProjectUtils.performMeshMergers(project.getProjectDirectory());
                ProjectUtils.performMeshStitches(project.getProjectDirectory());
                ProjectUtils.performZoneCreation(project.getProjectDirectory());
        }
        catch (IOException ex) 
        {
            Exceptions.printStackTrace(ex);
        }        
    }
}
