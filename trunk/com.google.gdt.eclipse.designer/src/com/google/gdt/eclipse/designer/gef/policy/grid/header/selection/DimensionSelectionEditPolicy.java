/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gdt.eclipse.designer.gef.policy.grid.header.selection;

import com.google.common.collect.Lists;
import com.google.gdt.eclipse.designer.gef.policy.grid.header.edit.DimensionHeaderEditPart;
import com.google.gdt.eclipse.designer.model.widgets.panels.grid.DimensionInfo;
import com.google.gdt.eclipse.designer.model.widgets.panels.grid.HTMLTableInfo;

import org.eclipse.wb.core.gef.header.AbstractHeaderSelectionEditPolicy;
import org.eclipse.wb.draw2d.Figure;
import org.eclipse.wb.draw2d.FigureUtils;
import org.eclipse.wb.draw2d.IColorConstants;
import org.eclipse.wb.draw2d.ILocator;
import org.eclipse.wb.draw2d.geometry.Rectangle;
import org.eclipse.wb.gef.graphical.handles.Handle;
import org.eclipse.wb.gef.graphical.handles.MoveHandle;
import org.eclipse.wb.gef.graphical.policies.LayoutEditPolicy;
import org.eclipse.wb.gef.graphical.policies.SelectionEditPolicy;

import java.util.List;

/**
 * Abstract {@link SelectionEditPolicy} for {@link DimensionHeaderEditPart}.
 * 
 * @author scheglov_ke
 * @coverage gwt.gef.policy
 */
abstract class DimensionSelectionEditPolicy<T extends DimensionInfo>
    extends
      AbstractHeaderSelectionEditPolicy {
  protected static final String REQ_RESIZE = "resize";

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public DimensionSelectionEditPolicy(LayoutEditPolicy mainPolicy) {
    super(mainPolicy);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Handles
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected List<Handle> createSelectionHandles() {
    List<Handle> handles = Lists.newArrayList();
    // move handle
    {
      MoveHandle moveHandle = new MoveHandle(getHost(), new HeaderMoveHandleLocator());
      moveHandle.setForeground(IColorConstants.red);
      handles.add(moveHandle);
    }
    //
    return handles;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utils
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * @return the host {@link DimensionHeaderEditPart}.
   */
  @SuppressWarnings("unchecked")
  private DimensionHeaderEditPart<T> getHostHeader() {
    return (DimensionHeaderEditPart<T>) getHost();
  }

  /**
   * @return the host {@link HTMLTableInfo}.
   */
  protected final HTMLTableInfo getPanel() {
    return getHostHeader().getPanel();
  }

  /**
   * @return the host {@link DimensionInfo}.
   */
  protected final T getDimension() {
    return getHostHeader().getDimension();
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Move location
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Implementation of {@link ILocator} to place handle directly on header.
   */
  private class HeaderMoveHandleLocator implements ILocator {
    public void relocate(Figure target) {
      Figure reference = getHostFigure();
      Rectangle bounds = reference.getBounds().getCopy();
      FigureUtils.translateFigureToFigure(reference, target, bounds);
      target.setBounds(bounds);
    }
  }
}