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
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.AddSubRegionAction;
import project.org.jcae.netbeans.of.actions.RemoveRegionAction;
import project.org.jcae.netbeans.of.actions.RenameRegionAction;
import project.org.jcae.netbeans.of.actions.SubRegionFlavor;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class RegionNode extends AbstractNode {
   
    public static final String REGION_ICON = "project/org/jcae/netbeans/of/resources/RegionIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    private String rName;
    
    public RegionNode(String rName, Project pr)
    {
        super(new RegionChildren(rName, pr), new MyLookup());
        this.project = pr;
        this.rName = rName; 
        setIconBaseWithExtension(REGION_ICON);
        setDisplayName(rName);
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);        
        instanceContent.add(this);        
        instanceContent.add(getChildren());       
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(REGION_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(REGION_ICON) ;
    }    
    
    @Override
    public Action[] getActions(boolean arg0) {
        return new Action[]{
                    //CommonProjectActions.newFileAction(),
                    //CommonProjectActions.copyProjectAction(),
                    //Utilities.actionsForPath("Actions/Project/").get(0), 
                    ((Action) new AddSubRegionAction()),
                    ((Action) new RemoveRegionAction()),
                    ((Action) new RenameRegionAction())
                };
    } 
    @Override
    public Sheet createSheet()
    {
            Sheet sheet=super.createSheet();
            // The line below is causing bug
            //sheet.put(ProjectUtils.createRegionSheetSet(rName, project.getProjectDirectory()));
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
                        Node nParent = (Node) n.getParentNode();
                        Node grandParent = (Node) nParent.getParentNode();
                        
                        RegionNode gParent = null;
                        if(grandParent instanceof RegionNode)
                            gParent = (RegionNode) grandParent;
                        
                        if(! (grandParent instanceof RegionNode) || ! (nParent instanceof SubRegionNode) /*|| !(n.getParentNode().getParentNode().equals(this))*/ )
                        {
                            JOptionPane.showMessageDialog(null, "DeMerger is possible only within two Subregions within a Region");
                            return null;
                        }
                        if(! gParent.getrName().equals(getrName()))
                        {
                            JOptionPane.showMessageDialog(null, "DeMerger is possible only within two Subregions within a Region");
                            return null;
                        }
                            
                        SubRegionNode subRegionNodeParent = (SubRegionNode) n.getParentNode();
                        
                        // 1. Modify Project.xml accordingly.
                        ProjectUtils.unmergeSubRegions(rName, n.getsName(), subRegionNodeParent.getsName(), project.getProjectDirectory());
                        
                        // 2. Remove the subRegion from its parent
                        SubRegionChildren srcNode = n.getParentNode().getLookup().lookup(SubRegionChildren.class);
                        srcNode.removeChildren(n);

                        // 3. Add subRegion into merged Region
                        RegionChildren rcNode = getLookup().lookup(RegionChildren.class);
                        Collection<Node> nColl = new ArrayList<Node>();
                        nColl.add(n);
                        rcNode.addChildren(nColl);

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
