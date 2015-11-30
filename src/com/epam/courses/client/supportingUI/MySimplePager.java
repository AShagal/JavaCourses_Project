package com.epam.courses.client.supportingUI;

import com.google.gwt.view.client.Range;
import com.google.gwt.user.cellview.client.SimplePager;

public class MySimplePager extends SimplePager {

	public MySimplePager(TextLocation right, Resources pagerResources, boolean b, int i, boolean c) {
		super(right, pagerResources, b, i, c);
	}

	@Override
	public void setPageStart(int index) {
		if (this.getDisplay() != null) {
			Range range = this.getDisplay().getVisibleRange();
			int pageSize = range.getLength();
			index = Math.max(0, index);
			if (index != range.getStart()) {
				this.getDisplay().setVisibleRange(index, pageSize);
			}
		}
	}

}
