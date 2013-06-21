/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.util.ArrayList;
import java.util.Collection;
import org.netbeans.api.project.Project;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;
import project.org.jcae.netbeans.of.nodes.FaceZoneNode;
import project.org.jcae.netbeans.of.nodes.RegionNode;
import project.org.jcae.netbeans.of.nodes.ZonesChildren;
import project.org.jcae.netbeans.of.nodes.ZonesNode;

/**
 *
 * @author mogargaa65
 */
public class AddFaceZoneAction extends CookieAction
{

    @Override
    protected int mode() 
    {
        return CookieAction.MODE_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() 
    {
        return new Class[] { Project.class };
    }
    
    @Override
    protected void performAction(Node[] activatedNodes) 
    {
        // Add region element in Project.xml
        Project pr = activatedNodes[0].getLookup().lookup(Project.class);
        ZonesNode zNode = (ZonesNode) activatedNodes[0].getLookup().lookup(ZonesNode.class);
        RegionNode rNode = (RegionNode) zNode.getParentNode();
        
        FaceZonePanel a = new FaceZonePanel(rNode.getrName(), pr);
        if(a.showDialog())
        {
            // 1. Add in Project.xml
            a.save();

            // 2. Add a Node in Zones
            //ZonesNode zNode = (ZonesNode) activatedNodes[0].getLookup().lookup(ZonesNode.class);
            ZonesChildren zChild = (ZonesChildren) zNode.getLookup().lookup(ZonesChildren.class);

            Collection<Node> nColl = new ArrayList<Node>();
            nColl.add(new FaceZoneNode(a.params.getZoneName(), rNode.getrName(), pr));
            zChild.addChildren(nColl);
        }       
    }

    @Override
    public String getName() 
    {
        return "Add FaceZone";
    }

    @Override
    public HelpCtx getHelpCtx() 
    {
        return null;        
    }
    
}
