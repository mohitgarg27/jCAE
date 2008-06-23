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

import java.util.ArrayList;
import java.util.List;
import vtk.vtkActor;
import vtk.vtkCanvas;
import vtk.vtkFloatArray;
import vtk.vtkMapper;
import vtk.vtkPolyData;
import vtk.vtkPolyDataMapper;
import vtk.vtkPolyDataNormals;

/**
 * This class is the interface of a Node. There is two type of nodes :
 * _ the nodes (implemented by the class Node) that can have children of type Node or LeafNode ;
 * _ the leaf nodes that are of type LeafNode.
 * This structure is used because VTK can't have many actors so we regroup some of geometric
 * entities that are represented by LeafNode in a one Node wich will merge all the geometry in one actor.
 * The merge is made only if the Node is set as managing (using the method setManager).
 * A node can be highlighted using the select method.
 * @author ibarz
 */
public abstract class AbstractNode {
// Datas
	protected AbstractNode parent;
	private final ArrayList<ActorListener> actorListeners = new ArrayList<ActorListener>();
	//private final ArrayList<DataListener> dataListeners = new ArrayList<DataListener>();
	private boolean manager = false;
	protected vtkActor actor = null; // If actor != null the node is not a manager
	protected vtkPolyDataMapper mapper = null;
	protected vtkPolyData data = null;

	protected long lastUpdate = 0;
	
	protected long modificationTime = System.nanoTime();
	protected boolean visible = true;
	protected int[] selectionPoint = new int[0];
	protected long selectionTime = 0   ;
	protected boolean selected;
	
	protected vtkActor selectionHighLighter = null;
	protected boolean pickable = true;
	
	public static interface ActorListener
	{
		void actorCreated(AbstractNode node, vtkActor actor);
		void actorDeleted(AbstractNode node, vtkActor actor);
		void actorHighLighted(AbstractNode node, vtkActor actor);
	}
		
	public static class ActorCustomiser
	{
		public void customiseActor(vtkActor actor)
		{
			
		}
	}
	
	public static class ActorHighLightedCustomiser
	{
		public void customiseActorHighLighted(vtkActor actor)
		{
			System.out.println("TEST");
		}
	}
	
	public static class MapperCustomiser
	{
		public void customiseMapper(vtkMapper mapper)
		{
		}
	}
	
	public static class MapperHighLightedCustomiser
	{
		public void customiseMapperHighLighted(vtkMapper mapper)
		{
		}
	}
	
	public static class ActorSelectionCustomiser
	{
		public void customiseActorSelection(vtkActor actor)
		{
			System.out.println("TEST2");
		}
	}
	
	public static class MapperSelectionCustomiser
	{
		public void customiseMapperSelection(vtkMapper mapper)
		{
		}
	}
	
	public static ActorCustomiser DEFAULT_ACTOR_CUSTOMISER = new ActorCustomiser();
	public static ActorHighLightedCustomiser DEFAULT_ACTOR_HIGHLIGHTED_CUSTOMISER = new ActorHighLightedCustomiser();
	public static MapperCustomiser DEFAULT_MAPPER_CUSTOMISER = new MapperCustomiser();
	public static MapperHighLightedCustomiser DEFAULT_MAPPER_HIGHLIGHTED_CUSTOMISER = new MapperHighLightedCustomiser();
	public static ActorSelectionCustomiser DEFAULT_ACTOR_SELECTION_CUSTOMISER = new ActorSelectionCustomiser();
	public static MapperSelectionCustomiser DEFAULT_MAPPER_SELECTION_CUSTOMISER = new MapperSelectionCustomiser();
	
	protected ActorCustomiser actorCustomiser = null;
	protected ActorHighLightedCustomiser actorHighLightedCustomiser = null;
	protected MapperCustomiser mapperCustomiser = null;
	protected MapperHighLightedCustomiser mapperHighLightedCustomiser = null;
	protected ActorSelectionCustomiser actorSelectionCustomiser = null;
	protected MapperSelectionCustomiser mapperSelectionCustomiser = null;
	
	/*public static interface DataListener
	{
		void dataModified(AbstractNode node, vtkPolyData data);
	}*/

	public AbstractNode(AbstractNode parent)
	{
		this.parent = parent;
		if(parent != null) parent.addChild(this);
	}

	public AbstractNode getRoot()
	{
		if(this.parent != null)
			return this.parent.getRoot();
		else return this;
	}
	
	public AbstractNode getParent()
	{
		return this.parent;
	}
	
	protected abstract void addChild(AbstractNode parent);

	public void addActorListener(ActorListener listener)
	{
		actorListeners.add(listener);
	}
	
	/**
	 * Set pickable the actor of the node. If the node is not a manager,
	 * set the parent to be pickable.
	 * @param pickable
	 */
	public void setPickable(boolean pickable)
	{
		this.pickable = pickable;
		
		int pickableNative = (pickable) ? 1 : 0;
		if(actor != null)
			actor.SetPickable(pickableNative);
		else if(parent != null)
			parent.setPickable(pickable);
	}
	
	public void removeActorListener(ActorListener listener)
	{
		actorListeners.remove(listener);
	}
	
	/*public void addDataListener(DataListener listener)
	{
		dataListeners.add(listener);
	}
	
	public void removeDataListener(DataListener listener)
	{
		dataListeners.remove(listener);
	}*/
	
	public vtkActor getActor()
	{
		return this.actor;
	}

	protected void fireActorCreated(vtkActor actor)
	{
		/*System.out.println("NUMBER OF ACTOR LISTENERS : " + actorListeners.size());
		for(ActorListener listener : actorListeners)
		{
			System.out.println("TYPE : " + listener.getClass().getSimpleName());
		}*/
		
		
		for (ActorListener listener : actorListeners)
			listener.actorCreated(this, actor);
	}

	protected void fireActorDeleted(vtkActor actor)
	{
		for (ActorListener listener : actorListeners)
			listener.actorDeleted(this, actor);
	}

	public void setActorCustomiser(ActorCustomiser actorCustomiser)
	{
		this.actorCustomiser = actorCustomiser;
	}

	public void setActorHighLightedCustomiser(ActorHighLightedCustomiser actorHighLightedCustomiser)
	{
		this.actorHighLightedCustomiser = actorHighLightedCustomiser;
	}

	public void setMapperCustomiser(MapperCustomiser mapperCustomiser)
	{
		this.mapperCustomiser = mapperCustomiser;
	}

	public void setMapperHighLightedCustomiser(MapperHighLightedCustomiser mapperHighLightedCustomiser)
	{
		this.mapperHighLightedCustomiser = mapperHighLightedCustomiser;
	}

	public ActorSelectionCustomiser getActorSelectionCustomiser()
	{
		if(actorSelectionCustomiser != null)
			return actorSelectionCustomiser;
		else if(parent != null)
			actorSelectionCustomiser = parent.getActorSelectionCustomiser();
		
		if(actorSelectionCustomiser == null)
			actorSelectionCustomiser = DEFAULT_ACTOR_SELECTION_CUSTOMISER;
		
		return actorSelectionCustomiser;
	}

	public void setActorSelectionCustomiser(ActorSelectionCustomiser actorSelectionCustomiser)
	{
		this.actorSelectionCustomiser = actorSelectionCustomiser;
	}


	public MapperSelectionCustomiser getMapperSelectionCustomiser()
	{
		if(mapperSelectionCustomiser != null)
			return mapperSelectionCustomiser;
		else if(parent != null)
			mapperSelectionCustomiser = parent.getMapperSelectionCustomiser();
		
		if(mapperSelectionCustomiser == null)
			mapperSelectionCustomiser = DEFAULT_MAPPER_SELECTION_CUSTOMISER;
		
		return mapperSelectionCustomiser;
	}

	public void setMapperSelectionCustomiser(MapperSelectionCustomiser mapperSelectionCustomiser)
	{
		this.mapperSelectionCustomiser = mapperSelectionCustomiser;
	}

	
	
	public ActorCustomiser getActorCustomiser()
	{
		if(actorCustomiser != null)
			return actorCustomiser;
		else if(parent != null)
			actorCustomiser = parent.getActorCustomiser();
		
		if(actorCustomiser == null)
			actorCustomiser = DEFAULT_ACTOR_CUSTOMISER;
		
		return actorCustomiser;
	}

	public ActorHighLightedCustomiser getActorHighLightedCustomiser()
	{
		if(actorHighLightedCustomiser != null)
			return actorHighLightedCustomiser;
		else if(parent != null)
			actorHighLightedCustomiser = parent.getActorHighLightedCustomiser();
		
		if(actorHighLightedCustomiser == null)
			actorHighLightedCustomiser = DEFAULT_ACTOR_HIGHLIGHTED_CUSTOMISER;
		
		return actorHighLightedCustomiser;
	}

	public MapperCustomiser getMapperCustomiser()
	{
		if(mapperCustomiser != null)
			return mapperCustomiser;
		else if(parent != null)
			mapperCustomiser = parent.getMapperCustomiser();
		
		if(mapperCustomiser == null)
			mapperCustomiser = DEFAULT_MAPPER_CUSTOMISER;
			
		return mapperCustomiser;
	}

	public MapperHighLightedCustomiser getMapperHighLightedCustomiser()
	{
		if(mapperHighLightedCustomiser != null)
			return mapperHighLightedCustomiser;
		else if(parent != null)
			mapperHighLightedCustomiser = parent.getMapperHighLightedCustomiser();
		
		if(mapperHighLightedCustomiser == null)
			mapperHighLightedCustomiser = DEFAULT_MAPPER_HIGHLIGHTED_CUSTOMISER;
		
		return mapperHighLightedCustomiser;
	}
	
	
	
	/*protected void fireDataModified(vtkPolyData data)
	{
		for(DataListener listener : dataListeners)
			listener.dataModified(this, data);
	}*/
	
	
	
	protected void fireActorHighLighted(vtkActor actor)
	{		
		for (ActorListener listener : actorListeners)
			listener.actorHighLighted(this, actor);
	}
	
	public void setManager(boolean manager)
	{
		if(this.manager = manager)
			return;
		
		this.manager = manager;
		
		if(!this.manager)
			deleteDatas();
		
		modified();
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		//System.out.println("this.visible = " + this.visible);
		if(this.visible == visible)
			return;
		
		//System.out.println("CHANGE VISIIBLITY " + visible);
		
		this.visible = visible;
		
		if(actor != null)
			actor.SetVisibility((visible) ? 1 : 0);
		
		modified();
	}
	
	protected abstract void refresh();
	
	protected long getModificationTime()
	{
		return this.modificationTime;
	}
	
	public void modified()
	{
		this.modificationTime = System.nanoTime();
	}
	
	protected void createData(LeafNode.DataProvider dataProvider)
	{
		data = new vtkPolyData();
		data.SetPoints(Utils.createPoints(dataProvider.getNodes()));
		data.SetVerts(Utils.createCells(dataProvider.getNbrOfVertice(), dataProvider.getVertice()));
		data.SetLines(Utils.createCells(dataProvider.getNbrOfLines(), dataProvider.getLines()));
		data.SetPolys(Utils.createCells(dataProvider.getNbrOfPolys(), dataProvider.getPolys()));
		
		//System.out.println("DATA RECREATED : " + data.GetNumberOfPoints());
		
		if(dataProvider.getNormals() == null)
			return;
		
		// Compute normals that are not given
		vtkPolyDataNormals algoNormals = new vtkPolyDataNormals();
		algoNormals.SetInput(data);
		algoNormals.SplittingOff();
		algoNormals.FlipNormalsOff();
		algoNormals.AutoOrientNormalsOff();
		algoNormals.ComputePointNormalsOn();
		algoNormals.Update();

		data = algoNormals.GetOutput();
		
		vtkFloatArray computedNormals = (vtkFloatArray) data.GetPointData().GetNormals();
		float[] javaComputedNormals = computedNormals.GetJavaArray();
		float[] javaNormals = dataProvider.getNormals();
		
		// If the normals are not computed change them by the normals computed by the meshes
		for(int i = 0 ; i < javaComputedNormals.length / 3 ; i += 3)
		{
			if(javaNormals[i] == 0. && javaNormals[i + 1] == 0. && javaNormals[i + 2] == 0.)
			{
				javaNormals[i] = javaComputedNormals[i];
				javaNormals[i + 1] = javaComputedNormals[i  + 1];
				javaNormals[i + 2] = javaComputedNormals[i + 2];
			}
		}
		
		vtkFloatArray normals = new vtkFloatArray();
		normals.SetNumberOfComponents(3);
		normals.SetJavaArray(javaNormals);
		
		data.GetPointData().SetNormals(normals);
		//fireDataModified(data);
	}
	
	protected void unHighLightSelection()
	{
		if(selectionHighLighter == null)
			return;
		
		//System.out.println("DELETE HIGHLIGHT SELECTION");
		
		fireActorDeleted(selectionHighLighter);
		selectionHighLighter = null;
	}
	protected void refreshMapper()
	{
		mapper.SetInput(data);		
		mapper.Update();
	}
	protected abstract void refreshData();
	public abstract void highLightSelection();
	
	protected void refreshActor()
	{
		refreshData();
		boolean actorCreated = false;
		
		//System.out.println("REFRESH ACTOR !");
		if(actor == null)
		{
			actorCreated = true;
			//System.out.println("CREATING AN ACTOR !");
			actor = new vtkActor();
			mapper = new vtkPolyDataMapper();
			actor.SetMapper(mapper);
			actor.SetVisibility((visible) ? 1 : 0);
			actor.SetPickable((pickable) ? 1 : 0);
		}
		refreshMapper();

		// Call fire after the map creation
		if(actorCreated)
			fireActorCreated(actor);

			
		/*System.out.println("number of lines : " + data.GetNumberOfLines());
		System.out.println("number of polys : " + data.GetNumberOfPolys());
		System.out.println("number of nodes : " + data.GetNumberOfPoints());*/
		/*mapper.SetColorModeToDefault();
		mapper.ScalarVisibilityOff();
		actor.GetProperty().SetColor(1, 0, 0);*/
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	protected void deleteDatas()
	{
		data = null;
		if(actor != null)
		{
			fireActorDeleted(actor);
			actor = null;
		}
		mapper = null;
	}

	public abstract List<LeafNode> getLeaves();
	
	/**
	 * Find the node that contains the cellID
	 * @param cellID
	 * @return
	 */
	protected abstract LeafNode getNode(int cellID);
	
	protected void pickPoint(vtkCanvas canvas, int[] firstPoint, int[] secondPoint, double tolerance)
	{
		/*vtkSelectVisiblePoints selector = new vtkSelectVisiblePoints();
		selector.ReleaseDataFlagOn();

		data.Update();
		selector.SetInput(data);
		selector.SelectionWindowOn();
		selector.SetSelection(firstPoint[0], secondPoint[0], secondPoint[1], firstPoint[1]);
		selector.SetRenderer(canvas.GetRenderer());
		selector.
		
		selector.SetTolerance(Utils.computeTolerance(canvas, tolerance));
		
		// We have to render without the highlight and then update to have the points
		canvas.lock();
		canvas.GetRenderer().Render();
		selector.Update();
		canvas.unlock();
		
		// We have putted the ids in the field data of points on the creation of the vtkPolyData with vtkIdFilter
		vtkPolyData data = selector.GetOutput();
		data.ReleaseDataFlagOn();
		vtkIdTypeArray ids = (vtkIdTypeArray) data.GetPointData().GetAbstractArray(fieldDataName);

		Utils.getValues(ids);*/
	}
	
	protected abstract void manageHighLight();
			
	protected void highLight()
	{
		assert actor != null;
		
		getActorHighLightedCustomiser().customiseActorHighLighted(actor);
		getMapperHighLightedCustomiser().customiseMapperHighLighted(mapper);
		
		fireActorHighLighted(actor);
	}
	
	protected void unHighLight()
	{
		assert actor != null;
		
		getActorCustomiser().customiseActor(actor);
		getMapperCustomiser().customiseMapper(mapper);
	}
	
	protected long selectionTime()
	{
		return selectionTime;
	}
	
	public void select()
	{
		if(selected)
			return;
		
		selected = true;
		selectionTime = System.nanoTime();
	}

	public void unSelect()
	{
		if(!selected)
			return;
		
		selected = false;
		selectionTime = System.nanoTime();
	}
	protected abstract void manageSelection(int[] cellSelection);
	
	public boolean isManager()
	{
		return manager;
	}
}
