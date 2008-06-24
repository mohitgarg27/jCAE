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
 * (C) Copyright 2008, by EADS France
 */
package org.jcae.vtk;

import gnu.trove.TIntArrayList;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import vtk.vtkActor;
import vtk.vtkPlane;
import vtk.vtkPlaneCollection;
import vtk.vtkRenderer;

/**
 * @author Julian Ibarz
 */
public abstract class Viewable extends MultiCanvas
{

	
	/** The position of the mouse when the press event occurs */
	private String name = null;
	private ArrayList<SelectionListener> selectionListeners =
			new ArrayList<SelectionListener>();
	protected final double tolerance = 0.002; // 0.2% of tolerance in function of the (far-near) distance

	protected int pixelTolerance = 0; // pixel tolerance for point picking (0 by default)

	protected final Scene scene;
	protected final Node rootNode; // The rootNode node of the viewable

	protected boolean appendSelection = false;
	protected HashSet<LeafNode> selectionNode = new HashSet<LeafNode>();
	protected HashSet<LeafNode> selectionCell = new HashSet<LeafNode>();
	protected boolean selectionChanged = false;

	// Do not modify this member directly (use setMode instead)
	private SelectionType selectionType = SelectionType.NODE;

	public enum SelectionType
	{
		NODE,
		CELL,
		POINT
	}

	public void setAppendSelection(boolean appendSelection)
	{
		this.appendSelection = appendSelection;
	}

	public Viewable()
	{
		this(new Scene(), new Node(null));
	}
	
	public Viewable(Scene scene, Node root)
	{
		this.scene = scene;
		this.rootNode = root;

		addNode(root);
		root.addChildCreationListener(this);
		root.setActorHighLightedCustomiser(new AbstractNode.ActorHighLightedCustomiser() {

			@Override
			public void customiseActorHighLighted(vtkActor actor)
			{
				super.customiseActorHighLighted(actor);
				
				Utils.vtkPropertySetColor(actor.GetProperty(), selectionColor);
			}
		});
		root.setActorSelectionCustomiser(new AbstractNode.ActorSelectionCustomiser() {

			@Override
			public void customiseActorSelection(vtkActor actor)
			{
				super.customiseActorSelection(actor);
				
				Utils.vtkPropertySetColor(actor.GetProperty(), selectionColor);
			}
			
		});
	}

	public void setSelectionType(SelectionType selectionType)
	{
		this.selectionType = selectionType;
	}

	public SelectionType getSelectionType()
	{
		return this.selectionType;
	}

	protected int[] selectPointOnSurface(Canvas canvas, int[] firstPoint, int[] secondPoint)
	{
		assert false;
		/*vtkSelectVisiblePoints selector = new vtkSelectVisiblePoints();
		selector.ReleaseDataFlagOn();
		
		meshData.Update();
		selector.SetInput(meshData);
		selector.SelectionWindowOn();
		selector.SetTolerance(0.0001);
		selector.SetSelection(firstPoint[0], secondPoint[0], secondPoint[1], firstPoint[1]);
		selector.SetRenderer(canvas.GetRenderer());
		
		
		selector.SetTolerance(Utils.computeTolerance(canvas, tolerance));
		
		// We have to render without the highlight and then update to have the points
		highLight.SetVisibility(0);
		canvas.lock();
		canvas.GetRenderer().RenderSecured();
		selector.Update();
		canvas.unlock();
		highLight.SetVisibility(1);
		
		// We have putted the ids in the field data of points on the creation of the vtkPolyData with vtkIdFilter
		vtkPolyData data = selector.GetOutput();
		data.ReleaseDataFlagOn();
		vtkIdTypeArray ids = (vtkIdTypeArray) data.GetPointData().GetAbstractArray(fieldDataName);
		
		return Utils.getValues(ids);*/

		return new int[0];
	}

	protected void selectCellOnSurface(Canvas canvas, int[] firstPoint, int[] secondPoint)
	{
		scene.pick(canvas, firstPoint, secondPoint);

		List<LeafNode> nodes = rootNode.getLeaves();

		HashSet<LeafNode> newSelection;

		if (appendSelection)
			newSelection = selectionCell;
		else
			newSelection = new HashSet<LeafNode>();

		for (LeafNode node : nodes)
		{
			TIntArrayList nodeSelection = node.getSelection();

			if (nodeSelection.size() != 0)
			{
				// Do not add if the group is already selected
				if (selectionNode.contains(node))
				{
					selectionNode.clear();
					continue;
				}

				if (newSelection.add(node))
					selectionChanged = true;
			} else if (newSelection.remove(node))
				selectionChanged = true;
		}

		selectionCell = newSelection;
	}

	protected void selectNodeOnSurface(Canvas canvas, int[] firstPoint, int[] secondPoint)
	{
		long begin = System.currentTimeMillis();
		scene.pick(canvas, firstPoint, secondPoint);
		System.out.println("TEMPS DE PICK COLOR : " + (System.currentTimeMillis() - begin));
		
		begin = System.currentTimeMillis();
		List<LeafNode> nodes = rootNode.getLeaves();

		HashSet<LeafNode> newSelection;

		if (appendSelection)
			newSelection = selectionNode;
		else
			newSelection = new HashSet<LeafNode>();

		for (LeafNode node : nodes)
		{
			TIntArrayList nodeSelection = node.getSelection();

			if (nodeSelection.size() != 0)
			{
				if (newSelection.add(node))
				{
					selectionChanged = true;

					// Clear the selectionNode because it's not a cell selectionNode
					nodeSelection.clear();

					// Remove the node from the selection cell
					selectionCell.remove(node);
				}
			} else if (newSelection.remove(node))
				selectionChanged = true;
		}

		selectionNode = newSelection;
		
		System.out.println("TEMPS DE DISPATCH : " + (System.currentTimeMillis() - begin));
	}

	public boolean getAppendSelection()
	{
		return this.appendSelection;
	}

	public void addSelectionListener(SelectionListener listener)
	{
		selectionListeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener listener)
	{
		selectionListeners.remove(listener);
	}

	protected void fireSelectionChanged()
	{
		for (SelectionListener listener : selectionListeners)
			listener.selectionChanged(this);
	}

	public void surfaceSelection(Canvas canvas, Point pressPosition_, Point releasePosition_)
	{
		if (!appendSelection)
					unSelectAll();
		
		int[] pressPosition = new int[2];
		pressPosition[0] = pressPosition_.x;
		pressPosition[1] = pressPosition_.y;
		int[] releasePosition = new int[2];
		releasePosition[0] = releasePosition_.x;
		releasePosition[1] = releasePosition_.y;
				
		switch (selectionType)
		{
			case POINT:
				selectPointOnSurface(canvas, pressPosition, releasePosition);
				break;
			case CELL:
				selectCellOnSurface(canvas, pressPosition, releasePosition);
				break;
			case NODE:
				selectNodeOnSurface(canvas, pressPosition, releasePosition);
				break;
			default:
				assert false : "Type of Selection unnknown";
		}
		
		manageSelection();
	}

	/**
	 * If you want the highlight disappears call highlight...
	 */
	public void unSelectAll()
	{
		for (LeafNode leaf : selectionCell)
			leaf.unSelectAll();

		selectionCell.clear();
		selectionNode.clear();
	}

	protected void highLightNodes()
	{
		List<LeafNode> leaves = rootNode.getLeaves();

		for (LeafNode leaf : leaves)
			if (selectionNode.contains(leaf))
				leaf.select();
			else
				leaf.unSelect();
	}
	
	@Override
	public void addNode(AbstractNode node)
	{
		super.addNode(node);
		scene.addNode(node);
	}
	
	public void removeNode(AbstractNode node)
	{
		scene.removeNode(node);
		super.removeNode(node);
	}
	
	private void highLightCells()
	{
		rootNode.highLightSelection();
		
		/*List<LeafNode> leaves = rootNode.getLeaves();

		for (LeafNode leaf : leaves)
			if (selectionCell.contains(leaf))
				leaf.highLightSelection();
			else
				leaf.unHighLightSelection();*/
	}

	public void highLight()
	{
		highLightCells();
		highLightNodes();
		//System.out.println("AVANT REFRESH");
		rootNode.refresh();
		//System.out.println("APRES REFRESH");

		Render();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		if (name == null)
			return super.toString();
		else
			return name;
	}

	public void removeClippingPlanes()
	{
		// Send empty plane collection to removeCanvas the older planes
		setClippingPlanes(new vtkPlaneCollection());

		Render();
	}

	void setClippingPlanes(vtkPlaneCollection planes)
	{
		assert false;
	}

	protected boolean isSelectionEmpty()
	{
		return selectionCell.size() == 0 && selectionNode.size() == 0;
	}

	protected void manageSelection()
	{
		if (selectionChanged)
			fireSelectionChanged();
		selectionChanged = false;
	}

	/**
	 * Compute a vtkPlaneCollection when mouse is released then delegate to
	 * manageClippingPlanes
	 */
	private void doClippingPlane(Canvas canvas, Point pressPosition, Point releasePosition)
	{
		// Compute the clipping planes
		vtkRenderer render = canvas.GetRenderer();
		vtkPlaneCollection planes = new vtkPlaneCollection();
		vtkPlane plane = null;
		double[] p1;
		double[] p2;
		double[] p3;

		/**
		 * These are the two points in the screen that define the different clipping planes
		 */
		/*int[][] pos1 =
		{
			{
				pressPosition[0], pressPosition[1]
			}, // Left plane

			{
				releasePosition[0], pressPosition[1]
			}, // Top plane

			{
				releasePosition[0], releasePosition[1]
			}, // Right plane

			{
				pressPosition[0], releasePosition[1]
			} // Bottom plane

		};
		int[][] pos2 =
		{
			{
				pressPosition[0], releasePosition[1]
			},
			{
				pressPosition[0], pressPosition[1]
			},
			{
				releasePosition[0], pressPosition[1]
			},
			{
				releasePosition[0], releasePosition[1]
			}
		};

		for (int i = 0; i < 4; ++i)
		{
			plane = new vtkPlane();
			render.SetDisplayPoint(pos1[i][0], pos1[i][1], 0.);
			render.DisplayToWorld();
			p1 = render.GetWorldPoint();
			render.SetDisplayPoint(pos1[i][0], pos1[i][1], 1.);
			render.DisplayToWorld();
			p2 = render.GetWorldPoint();
			render.SetDisplayPoint(pos2[i][0], pos2[i][1], 1.);
			render.DisplayToWorld();
			p3 = render.GetWorldPoint();

			// Compute two vectors of the plane
			Vector3d v1 = new Vector3d();
			Vector3d v2 = new Vector3d();
			Vector3d n = new Vector3d();

			v1.set(p2);
			v2.set(p1);
			n.set(p3);
			v1.sub(v2);
			v2.sub(n);

			// Compute cross product (the normal of the plane) between p1 and p2
			n.cross(v1, v2);
			n.normalize();

			plane.SetNormal(n.getX(), n.getY(), n.getZ());
			plane.SetOrigin(p1);
			planes.AddItem(plane);
		}

		setClippingPlanes(planes);*/
	}

	public void setPickable(boolean pickable)
	{
		scene.setPickable(pickable);
	}
	
	public void pointSelection(Canvas canvas, Point pickPosition)
	{
		if (!appendSelection)
					unSelectAll();
		
		int[] firstPoint = new int[2];
		int[] secondPoint = new int[2];
		
		firstPoint[0] = pickPosition.x - pixelTolerance;
		firstPoint[1] = pickPosition.y - pixelTolerance;
		secondPoint[0] = pickPosition.x + pixelTolerance;
		secondPoint[1] = pickPosition.y + pixelTolerance;
				
		switch (selectionType)
		{
			case POINT:
				selectPointOnSurface(canvas, firstPoint, secondPoint);
				break;
			case CELL:
				selectCellOnSurface(canvas, firstPoint, secondPoint);
				break;
			case NODE:
				selectNodeOnSurface(canvas, firstPoint, secondPoint);
				break;
			default:
				assert false : "Type of Selection unnknown";
		}

		manageSelection();
	}
}