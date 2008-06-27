/*
 * Project Info:  http://jcae.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * (C) Copyright 2005, by EADS CRC
 */

package org.jcae.netbeans.cad;

import org.jcae.opencascade.jni.BRep_Builder;
import org.jcae.opencascade.jni.ShapeAnalysis_FreeBounds;
import org.jcae.opencascade.jni.TopoDS_Compound;
import org.jcae.opencascade.jni.TopoDS_Shape;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;

public class FreeBoundsAction extends CookieAction
{
	protected int mode()
	{
		return CookieAction.MODE_ALL;
	}

	protected Class[] cookieClasses()
	{
		return new Class[]{NbShape.class};
	}

	protected void performAction(Node[] arg0)
	{
		BRep_Builder bb = new BRep_Builder();
		TopoDS_Compound tc=new TopoDS_Compound();
		bb.makeCompound(tc);
		for (Node anArg0 : arg0)
		{
			TopoDS_Shape shape = GeomUtils.getShape(anArg0).getImpl();
			ShapeAnalysis_FreeBounds safb = new ShapeAnalysis_FreeBounds(shape);
			TopoDS_Compound closedWires = safb.getClosedWires();
			TopoDS_Compound openWires = safb.getOpenWires();

			if (closedWires != null)
				bb.add(tc, closedWires);

			if (openWires != null)
				bb.add(tc, openWires);
		}
		
		throw new UnsupportedOperationException("J3D feature not yet implemented");
		
		/*View v=ViewManager.getDefault().getCurrentView();
		OCCProvider occp=new OCCProvider(tc);
		occp.setEdgeColor(Color.GREEN);
		
		 TODO : J3D Version
		ViewableCAD viewable = new ViewableCAD(occp);
		viewable.setName(arg0[0].getName()+" free edges");
		viewable.setLineWidth(3f);
		v.add(viewable);
		 */
	}

	public String getName()
	{
		return "Free edges";
	}

	public HelpCtx getHelpCtx()
	{
		return null;
	}
	
	protected boolean asynchronous()
	{
		return false;
	}
}
