package com.epam.courses.client.supportingUI;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;

public class RoleEnableEditCell extends EditTextCell {

	private static boolean isEditable = false;

	@Override
	public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
			ValueUpdater<String> valueUpdater) {
		if (isEditable) {
			super.onBrowserEvent(context, parent, value, event, valueUpdater);
		}
	}

	public boolean isEditable() {
		return isEditable;
	}

	public static void setEditable(boolean isEditable) {
		RoleEnableEditCell.isEditable = isEditable;
	}

}
