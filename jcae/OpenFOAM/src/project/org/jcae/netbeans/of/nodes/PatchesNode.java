/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.jcae.netbeans.cad.NBShapeFlavor;
import org.jcae.netbeans.cad.NbShape;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.project.ProjectFileUtils;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class PatchesNode extends AbstractNode {

    private String sName;
    private String rName;
    private final String PATCHES_ICON="project/org/jcae/netbeans/of/resources/PatchesNodeIcon.png";
    private final InstanceContent instanceContent = new InstanceContent();
    
    private Project project;
    
    public PatchesNode(String sName, String rName, Project pr) 
    {
        super(new PatchesChildren(sName, rName, pr), new MyLookup());
        setDisplayName("Patches");        
        project = pr;
        this.sName = sName;
        this.rName = rName;
        
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(pr);
        instanceContent.add(this);
        instanceContent.add(getChildren());
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(PATCHES_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(PATCHES_ICON) ;
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
    public PasteType getDropType(final Transferable t, int arg1, int arg2) {
        if (t.isDataFlavorSupported(NBShapeFlavor.NBSHAPE_FLAVOR)) {
            return new PasteType() {
                @Override
                public Transferable paste() throws IOException {
                    try {
    
                        NbShape n = ( (NbShape) t.getTransferData(NBShapeFlavor.NBSHAPE_FLAVOR));
                        if(n.getName().toLowerCase().startsWith("face"))
                        {
                            n.saveImpl(project.getProjectDirectory().getPath()+"/Patches/"+ n.getName());
                            ProjectUtils.addPatchElement(n.getName(), sName, rName, project.getProjectDirectory() );
                            PatchesChildren pChild = getLookup().lookup(PatchesChildren.class);
                            Collection<Node> nColl = new ArrayList<Node>();
                            nColl.add(new PatchNode(n.getName(), sName, rName, project));
                            pChild.addChildren(nColl);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Only Face elements can be copied");
                        }
                        
                    } catch (TransformerConfigurationException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (TransformerException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (UnsupportedFlavorException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    return null;
                }
            };
        } else {
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
}
