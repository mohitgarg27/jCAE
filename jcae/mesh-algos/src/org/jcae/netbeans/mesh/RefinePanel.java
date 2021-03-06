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
 * (C) Copyright 2005-2010, by EADS France
 */


package org.jcae.netbeans.mesh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author Jerome Robert
 */
public class RefinePanel extends JPanel {
	private class MyProperty<T> extends PropertySupport.Reflection<T>
	{
		public MyProperty(Class<T> type, String property, String name)
			throws NoSuchMethodException
		{
			this(type, property, name, name);
		}
		public MyProperty(Class<T> type, String property, String name, String description)
			throws NoSuchMethodException
		{
			super(RefinePanel.this, type, property);
			setName(name);
			setShortDescription(description);
		}
	}

	private double coplanarity = 0.9;
	private double targetSize = 1.0;
	private boolean featureOnly, preserveGroups = true;
	private boolean allowNearNodes;
	private double nearLengthRatio = 0.70710678118654746;
	
    private PointMetricPanel tablePanel = new PointMetricPanel() {
		@Override
		protected double getDefaultSize() {
			return getTargetSize();
		}
	};
	
    /** Creates new form RemeshPanel */
    public RefinePanel() {
		setLayout(new BorderLayout());
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sp.add(tablePanel, JSplitPane.BOTTOM);
		tablePanel.setBorder(new TitledBorder("Analytic metric"));
		PropertySheet ps = new PropertySheet();
		ps.setPreferredSize(new Dimension(0, 200));
		AbstractNode node = new AbstractNode(Children.LEAF)
		{
			@Override
			public PropertySet[] getPropertySets() {
				return new PropertySet[]{createPropertySet()};
			}
		};
		ps.setNodes(new Node[]{node});
		ps.setDescriptionAreaVisible(true);
		sp.add(ps, JSplitPane.TOP);
		add(sp, BorderLayout.CENTER);
    }

	private Sheet.Set createPropertySet()
	{
		Sheet.Set r = new Sheet.Set();
		r.setName("Parameters");
		try {
			r.put(new MyProperty<Double>(Double.TYPE, "targetSize", "Target size"));
			r.put(new MyProperty<Double>(Double.TYPE, "coplanarity",
				"Coplanarity", "Dot product of face normals to detect feature edges"));
			r.put(new MyProperty<Boolean>(Boolean.TYPE, "featureOnly", "Features only",
				"Only remesh feature edges (boundaries, ridges, nonmanifold)."));
			r.put(new MyProperty<Boolean>(Boolean.TYPE, "preserveGroups",
				"Preserve groups",
				"Edges adjacent to two different groups are handled like free edges."));
			r.put(new MyProperty<Boolean>(Boolean.TYPE, "allowNearNodes",
				"Allow near nodes",
				"Insert vertices even if this creates a small edge."));
			r.put(new MyProperty<Double>(Double.TYPE, "nearLengthRatio",
				"Near length ratio",
				"Ratio to size target to determine if a vertex is near an existing point (default: 1/sqrt(2)."){
				@Override
				public boolean canWrite() {
					return !allowNearNodes;
				}
			});
		} catch (NoSuchMethodException ex) {
			Exceptions.printStackTrace(ex);
		}
		return r;
	}

	public boolean isAllowNearNodes() {
		return allowNearNodes;
	}

	public void setAllowNearNodes(boolean allowNearNodes) {
		this.allowNearNodes = allowNearNodes;
	}

	public double getNearLengthRatio() {
		return nearLengthRatio;
	}

	public void setNearLengthRatio(double nearLengthRatio) {
		this.nearLengthRatio = nearLengthRatio;
	}

	public boolean showDialog()
	{
        final JOptionPane jp = new JOptionPane(this,
             JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog d = jp.createDialog("Refine options");
		d.setResizable(true);
		d.setVisible(true);
        return Integer.valueOf(JOptionPane.OK_OPTION).equals(jp.getValue());
	}
	
	public double getCoplanarity() {
		return coplanarity;
	}

	public void setCoplanarity(double coplanarity) {
		this.coplanarity = coplanarity;
	}

	public boolean isFeatureOnly() {
		return featureOnly;
	}

	public void setFeatureOnly(boolean featureOnly) {
		this.featureOnly = featureOnly;
	}

	public double getTargetSize() {
		return targetSize;
	}

	public void setTargetSize(double size) {
		this.targetSize = size;
	}

	public void writePointMetric(PrintStream out)
	{
		tablePanel.writeTable(out);
	}

	public boolean isPointMetric()
	{
		return !tablePanel.isEmpty();
	}

	public boolean isPreserveGroups() {
		return preserveGroups;
	}

	public void setPreserveGroups(boolean preserveGroups) {
		this.preserveGroups = preserveGroups;
	}

}
