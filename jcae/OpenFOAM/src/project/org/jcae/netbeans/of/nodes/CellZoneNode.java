/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
class CellZoneNode extends AbstractNode {

    private Project project;
    private String rName;
    private String cName;
    private final String CZONES_ICON="project/org/jcae/netbeans/of/resources/CellZoneIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    public CellZoneNode(String cName, String rName, Project pr) 
    {
        super(Children.LEAF, new MyLookup());
        this.project = pr;
        this.rName = rName;
        this.cName = cName;
        setDisplayName(cName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);
        instanceContent.add(pr);
    }

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(CZONES_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(CZONES_ICON) ;
    }    

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }
    
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            sheet.put(ProjectUtils.createCellZoneSheetSet(cName, rName, project.getProjectDirectory()));
            return sheet;
    }     
}
