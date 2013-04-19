/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.project;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author mogargaa65
 */
class OFProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private final FileObject xmlFile;
    private Lookup lkp;
    
    public OFProject(FileObject fo, ProjectState ps) {
        this.projectDir = fo;
        this.state = ps;
        
        xmlFile = fo.getFileObject("Project.xml");
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
        
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
            new Info(),
            new OFProjectLogicalView(this),
            // register your features here
            
            });
        }
        return lkp;
    }
    
    public String[] getRegions()
    {
        String[] toReturn = new String[]{};
        
        
        
        return toReturn;
    }
    
    public void loadRegions()
    {
        
    }
    
    private final class Info implements ProjectInformation 
    {

        @StaticResource()
        public static final String CUSTOMER_ICON = "project/org/jcae/netbeans/of/resources/OpenFOAMIcon.gif";

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(CUSTOMER_ICON));
            //return null;
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            //do nothing, won't change
        }

        @Override
        public Project getProject() {
            return OFProject.this;
        }

    }
}
