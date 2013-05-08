/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jcae.netbeans.cad;

import java.awt.datatransfer.DataFlavor;
import org.jcae.opencascade.Shape;

/**
 *
 * @author mogargaa65
 */
public class NBShapeFlavor extends DataFlavor
{
    public static final DataFlavor NBSHAPE_FLAVOR = new NBShapeFlavor();
    public NBShapeFlavor() 
    {
         super(Shape.class, "Shape");
    }

}
