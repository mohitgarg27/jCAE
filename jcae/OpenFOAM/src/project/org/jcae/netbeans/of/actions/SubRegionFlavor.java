/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.org.jcae.netbeans.of.actions;

import java.awt.datatransfer.DataFlavor;
import project.org.jcae.netbeans.of.nodes.SubRegionNode;

/**
 *
 * @author mogargaa65
 */
public class SubRegionFlavor extends DataFlavor
{
    public static final DataFlavor SUBREGION_FLAVOR = new SubRegionFlavor();
    public SubRegionFlavor() 
    {
         super(SubRegionNode.class, "SubRegionNode");
    }

}
