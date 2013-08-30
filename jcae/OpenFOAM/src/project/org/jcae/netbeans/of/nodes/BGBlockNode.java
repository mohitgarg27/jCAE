/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.nodes;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Action;
import org.jcae.netbeans.cad.BrepDataObject;
import org.jcae.netbeans.cad.BrepNode;
import org.jcae.netbeans.cad.NbShape;
import org.jcae.netbeans.cad.ViewShapeCookie;
import org.jcae.opencascade.jni.BRepAlgoAPI_Fuse;
import org.jcae.opencascade.jni.BRepBuilderAPI_MakeEdge;
import org.jcae.opencascade.jni.BRepBuilderAPI_MakeVertex;
import org.jcae.opencascade.jni.BRepTools;
import org.jcae.opencascade.jni.TopoDS_Shape;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import project.org.jcae.netbeans.of.actions.BGBlockMeshAction;
import project.org.jcae.netbeans.of.actions.BGBlockPanel;
import project.org.jcae.netbeans.of.actions.ViewBGBlockAction;
import project.org.jcae.netbeans.of.project.ProjectUtils;

/**
 *
 * @author mogargaa65
 */
public class BGBlockNode extends AbstractNode 
{
    private String sName;
    private String rName;
    private Project project;
    private final String BGBLOCK_ICON="project/org/jcae/netbeans/of/resources/BGBlockIcon.png";
    
    private final InstanceContent instanceContent = new InstanceContent();
        
    private NbShape shape = null;
    private ViewShapeCookie shapeCookie;
    private BrepDataObject bdo;
    private BrepNode geomNode;
    
    public BGBlockNode(String sName, String rName, Project pr) 
    {
        super(new BGBlockChildren(sName, rName, pr), new MyLookup());
        this.sName = sName;
        this.rName = rName;
        this.project = pr;
        setDisplayName("BG Block"); 
        ((MyLookup)getLookup()).setDelegates(new AbstractLookup(instanceContent));        
        instanceContent.add(this);                
        instanceContent.add(pr);  
        instanceContent.add(getChildren());
        load();
        instanceContent.add(shape);
        instanceContent.add(shapeCookie);
        instanceContent.add(bdo);
        instanceContent.add(geomNode);    
    }
    
    public void load()
    {
        try {
            String loc = project.getProjectDirectory().getPath()+"/"+rName+"/"+sName+"/BGBlock.brep";
            BRepTools.write(load(loc), loc);
            setShape(new NbShape(loc));                
            FileObject fo = FileUtil.toFileObject(new File(loc));
            bdo = (BrepDataObject)DataObject.find(fo);
            geomNode = (BrepNode)bdo.getNodeDelegate();
            shapeCookie = new ViewShapeCookie(this);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    private TopoDS_Shape load(String loc)
    {
        // Create a edge geometry, save as BGBlock.brep in subRegion dir
        BGBlockPanel.BGBlock blockParams = ProjectUtils.getBlockmeshInSubRegion(rName, sName, project.getProjectDirectory());

        String[] sl = blockParams.getlBounds();
        String[] su = blockParams.getuBounds();

        double[] dl = new double[3];
        double[] du = new double[3];
        
        dl[0] = Double.parseDouble(sl[0]);
        dl[1] = Double.parseDouble(sl[1]);
        dl[2] = Double.parseDouble(sl[2]);

        du[0] = Double.parseDouble(su[0]);
        du[1] = Double.parseDouble(su[1]);
        du[2] = Double.parseDouble(su[2]);
        
        double[][] pts = { {dl[0], dl[1], dl[2]}, {dl[0], dl[1], du[2]}, 
            {dl[0], du[1], du[2]}, {dl[0], du[1], dl[2]},
            {du[0], dl[1], dl[2]}, {du[0], dl[1], du[2]}, 
            {du[0], du[1], du[2]}, {du[0], du[1], dl[2]}};
        
//        TopoDS_Shape[] vertices = new TopoDS_Shape[8];
//        for(int i=0;i<8;i++)
//            vertices[i] = new BRepBuilderAPI_MakeVertex(pts[i]).shape();
        
        TopoDS_Shape[] wires = new TopoDS_Shape[12];
        wires[0] = new BRepBuilderAPI_MakeEdge(pts[0], pts[1]).shape();
        wires[1] = new BRepBuilderAPI_MakeEdge(pts[1], pts[2]).shape();
        wires[2] = new BRepBuilderAPI_MakeEdge(pts[2], pts[3]).shape();
        wires[3] = new BRepBuilderAPI_MakeEdge(pts[3], pts[0]).shape();
        wires[4] = new BRepBuilderAPI_MakeEdge(pts[4], pts[5]).shape();
        wires[5] = new BRepBuilderAPI_MakeEdge(pts[5], pts[6]).shape();
        wires[6] = new BRepBuilderAPI_MakeEdge(pts[6], pts[7]).shape();
        wires[7] = new BRepBuilderAPI_MakeEdge(pts[7], pts[4]).shape();
        wires[8] = new BRepBuilderAPI_MakeEdge(pts[0], pts[4]).shape();
        wires[9] = new BRepBuilderAPI_MakeEdge(pts[1], pts[5]).shape();
        wires[10] = new BRepBuilderAPI_MakeEdge(pts[2], pts[6]).shape();
        wires[11] = new BRepBuilderAPI_MakeEdge(pts[3], pts[7]).shape();
        
        TopoDS_Shape toRet = recMerge(wires, 0,11);
        return toRet;
    }

    private TopoDS_Shape recMerge(TopoDS_Shape[] wires, int i, int j) 
    {
        int count = j-i+1;
        if(count==1)
            return wires[i];
        else if (count>1)
        {
            {
                TopoDS_Shape shape1 = recMerge(wires, i, (i+count/2) -1);
                TopoDS_Shape shape2 = recMerge(wires, i+count/2, j);
                return new BRepAlgoAPI_Fuse(shape1, shape2).shape();
            }
        }        
        return null;
    }
    
    @Override
    public Image getIcon(int i)
    {
        return ImageUtilities.loadImage(BGBLOCK_ICON) ;
    }
    
    @Override
    public Image getOpenedIcon(int i)
    {                
        return ImageUtilities.loadImage(BGBLOCK_ICON) ;
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
    public Action[] getActions(boolean popup) 
    {
        ArrayList<Action> actions=new ArrayList<Action>();
        actions.add( SystemAction.get( BGBlockMeshAction.class)) ;
        actions.add( SystemAction.get( ViewBGBlockAction.class)) ;
        return actions.toArray(new Action[actions.size()]);
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
        return "BGBlock-"+sName;
    }

}
