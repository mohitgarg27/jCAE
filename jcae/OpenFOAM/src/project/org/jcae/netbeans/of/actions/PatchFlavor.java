/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.awt.datatransfer.DataFlavor;
import project.org.jcae.netbeans.of.nodes.PatchNode;

/**
 *
 * @author mogargaa65
 */
public class PatchFlavor extends DataFlavor
{
    public static final DataFlavor PATCH_FLAVOR = new PatchFlavor();
    public PatchFlavor() 
    {
         super(PatchNode.class, "PatchNode");
    }

}
