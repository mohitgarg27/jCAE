/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.MoveDownSubRegionAction;
import project.org.jcae.netbeans.of.actions.MoveUpSubRegionAction;
import project.org.jcae.netbeans.of.actions.RemoveSubRegionAction;
import project.org.jcae.netbeans.of.actions.RenameSubRegionAction;
import project.org.jcae.netbeans.of.actions.SnappyHexMeshAction;
import project.org.jcae.netbeans.of.actions.SubRegionFlavor;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class SubRegionNode extends AbstractNode {
    
    public static final String SUBREGION_ICON = "project/org/jcae/netbeans/of/resources/SubRegionIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    private String rName;
    private String sName;
    
    public SubRegionNode(String sName, String rName, Project pr)
    {
        super(new SubRegionChildren(sName, rName, pr), new MyLookup());
        this.project = pr;
        this.rName = rName;
        this.sName = sName;
        setDisplayName(sName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);          
        instanceContent.add(this);        
        instanceContent.add(getChildren());       
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(SUBREGION_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(SUBREGION_ICON) ;
    }    
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    //CommonProjectActions.newFileAction(),
                    //CommonProjectActions.copyProjectAction(),
                    //Utilities.actionsForPath("Actions/Project/").get(0), 
                    (SystemAction.get(RemoveSubRegionAction.class)),
                    (SystemAction.get(RenameSubRegionAction.class)),
                    (SystemAction.get(SnappyHexMeshAction.class)),
                    (SystemAction.get(MoveUpSubRegionAction.class)),
                    (SystemAction.get(MoveDownSubRegionAction.class))
                };
    }     
    
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            // The line below is causing a Bug
            //sheet.put(ProjectUtils.createSubRegionSheetSet(sName, rName, project.getProjectDirectory()));
            return sheet;
    }      

    /**
     * @return the rName
     */
    public String getrName() {
        return rName;
    }

    /**
     * @param rName the rName to set
     */
    public void setrName(String rName) {
        this.rName = rName;
    }

    /**
     * @return the sName
     */
    public String getsName() {
        return sName;
    }

    /**
     * @param sName the sName to set
     */
    public void setsName(String sName) {
        this.sName = sName;
    }


    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public void destroy() throws IOException {
        //model.remove(getLookup().lookup(Customer.class));
        
    }
    
    @Override
    public PasteType getDropType(final Transferable t, int arg1, int arg2) 
    {
        if (t.isDataFlavorSupported(SubRegionFlavor.SUBREGION_FLAVOR)) 
        {
            return new PasteType() 
            {
                @Override
                public Transferable paste() throws IOException {
                    try 
                    { 
                        SubRegionNode n = ( (SubRegionNode) t.getTransferData(SubRegionFlavor.SUBREGION_FLAVOR));
                        if(! (n.getParentNode().equals(getParentNode())) || ! (n.getParentNode() instanceof RegionNode) || n.equals(this) )
                        {
                            JOptionPane.showMessageDialog(null, "Merger is possible only within two Subregions of a Region");
                            return null;
                        }
                        // 1. Modify Project.xml accordingly.                        
                        ProjectUtils.mergeSubRegions(rName, n.getsName(), getsName(), project.getProjectDirectory());
                        
                        // 2. Remove the subRegion from its parent                        
                        RegionChildren rcNode = n.getParentNode().getLookup().lookup(RegionChildren.class);
                        rcNode.removeChildren(n);
                        
                        // 3. Add subRegion into merged subRegion                        
                        SubRegionChildren srcNode = getLookup().lookup(SubRegionChildren.class);
                        Collection<Node> nColl = new ArrayList<Node>();
                        nColl.add(n);
                        srcNode.addChildren(nColl);

                    } catch (UnsupportedFlavorException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    return null;
                }
            };
        }
        else {
            return null;
        }
    }
    
    @Override
    protected void createPasteTypes(Transferable t, List<PasteType> s) {
        super.createPasteTypes(t, s);
        PasteType p = getDropType(t, 0, 0);
        if (p != null) {
            s.add(p);
        }
    } 
    
    @Override
    public Transferable clipboardCut() throws IOException 
    {
        Transferable deflt = super.clipboardCut();
        ExTransferable added = ExTransferable.create(deflt);
        added.put(new ExTransferable.Single(SubRegionFlavor.SUBREGION_FLAVOR) {
            @Override
            protected SubRegionNode getData() {
                return getLookup().lookup(SubRegionNode.class);
            }
        });
        return added;
    }

    
    @Override
    public Transferable clipboardCopy() throws IOException 
    {
        Transferable deflt = super.clipboardCut();
        ExTransferable added = ExTransferable.create(deflt);
        added.put(new ExTransferable.Single(SubRegionFlavor.SUBREGION_FLAVOR) {
            @Override
            protected SubRegionNode getData() {
                return getLookup().lookup(SubRegionNode.class);
            }
        });
        return added;
    }    
    
}
