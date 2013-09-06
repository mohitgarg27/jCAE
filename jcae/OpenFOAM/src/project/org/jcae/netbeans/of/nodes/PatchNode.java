/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import javax.swing.Action;
import org.jcae.netbeans.cad.BrepDataObject;
import org.jcae.netbeans.cad.BrepNode;
import org.jcae.netbeans.cad.NbShape;
import org.jcae.netbeans.cad.ShapeNode;
import org.jcae.netbeans.cad.ViewShapeCookie;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.PatchBoundaryAction;
import project.org.jcae.netbeans.of.actions.RemovePatchAction;
import project.org.jcae.netbeans.of.actions.RenamePatchAction;
import project.org.jcae.netbeans.of.actions.ViewPatchAction;
import project.org.jcae.netbeans.of.actions.PatchFlavor;
import project.org.jcae.netbeans.of.actions.StitchPatchAction;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class PatchNode extends AbstractNode 
{
    private Project project;
    private String pName;
    private String sName;
    private String rName;
    
    private final String PATCH_ICON="project/org/jcae/netbeans/of/resources/PatchNodeIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private NbShape shape = null;
    private ViewShapeCookie shapeCookie;
    private BrepDataObject bdo;
    private BrepNode geomNode;
    
    public PatchNode(String pName, String sName, String rName, Project pr)
    {        
        super(Children.LEAF, new MyLookup());
        this.project = pr;
        this.pName = pName;
        this.sName = sName;
        this.rName = rName;
        setDisplayName(pName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);                
        instanceContent.add(pr);  
        load();
        instanceContent.add(shape);
        instanceContent.add(shapeCookie);
        instanceContent.add(bdo);
        instanceContent.add(geomNode);
    }
    
    public void load()
    {
        if(getShape()==null)
        {
            try {
                String loc = project.getProjectDirectory().getPath()+"/"+rName+"/"+sName+"/"+pName+".brep";
                setShape(new NbShape(loc));                
                FileObject fo = FileUtil.toFileObject(new File(loc));
                bdo = (BrepDataObject)DataObject.find(fo);
                geomNode = (BrepNode)bdo.getNodeDelegate();
                shapeCookie = new ViewShapeCookie(this);
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    SystemAction.get(ViewPatchAction.class),
                    SystemAction.get(RenamePatchAction.class),
                    SystemAction.get(RemovePatchAction.class),
                    SystemAction.get(PatchBoundaryAction.class),
                    SystemAction.get(StitchPatchAction.class)
                    //SystemAction.get( MergePatchAction.class)
                };
    }     

    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(PATCH_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(PATCH_ICON) ;
    }

    /**
     * @return the pName
     */
    public String getpName() {
        return pName;
    }

    /**
     * @return the sName
     */
    public String getsName() {
        return sName;
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
            sheet.put(ProjectUtils.createPatchSheetSet(getpName(), getsName(), getrName(), project.getProjectDirectory()));
            return sheet;
    }     

    /**
     * @param pName the pName to set
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * @param sName the sName to set
     */
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * @param rName the rName to set
     */
    public void setrName(String rName) {
        this.rName = rName;
    }
    
    @Override
    public boolean canCopy()
    {
            return true;
    }

    @Override
    public Transferable clipboardCopy() throws IOException 
    {
        Transferable deflt = super.clipboardCopy();
        ExTransferable added = ExTransferable.create(deflt);
        added.put(new ExTransferable.Single(PatchFlavor.PATCH_FLAVOR) {
            @Override
            protected PatchNode getData() {
                return getLookup().lookup(PatchNode.class);
            }
        });
        return added;
    }    

    /**
     * @return the shape
     */
    public NbShape getShape() {
        return shape;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(NbShape shape) {
        this.shape = shape;
    }

    public String getName()
    {
        return pName;
    }
            
    
}
