package com.epam.courses.client.supportingUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.epam.courses.shared.Course;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

public class MyCellTable<T> {

	private CellTable<T> cellTable;
	private ListDataProvider<T> dataProvider;
	private ListHandler<T> columnSortHandler;
	private SelectionModel<T> selectionModel;
	private SimplePager.Resources pagerResources;

	@UiField(provided = true)
	MySimplePager pager;

	public MyCellTable(ProvidesKey<T> keyProvider) {
		cellTable = new CellTable<T>(keyProvider);
		dataProvider = new ListDataProvider<T>();
		dataProvider.addDataDisplay(cellTable);
		cellTable.setVisible(false);
		columnSortHandler = new ListHandler<T>(null);
		selectionModel = new MultiSelectionModel<T>(keyProvider);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<T> createCheckboxManager());
		pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new MySimplePager(TextLocation.RIGHT, pagerResources, false, 0, true);
		pager.setDisplay(cellTable);

		pager.setVisible(false);
	}

	public CellTable<T> getTable() {
		return cellTable;
	}

	public void setVisible(boolean flag) {
		pager.setVisible(flag);
		cellTable.setVisible(flag);
	}

	public void initTable(List<T> data) {
		cellTable.setRowCount(0);
		dataProvider.getList().clear();
		dataProvider.setList(data);
		columnSortHandler.setList(dataProvider.getList());
		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.setRowCount(data.size());
		cellTable.setRowData(0, data);
		cellTable.setWidth("100%");

	}

	public <V> void addColumn(Column<T, V> column, String name, int width, Comparator<T> comparator) {
		column.setSortable(true);
		columnSortHandler.setComparator(column, comparator);
		cellTable.addColumn(column, name);
		cellTable.setColumnWidth(column, width, Unit.PCT);
	}

	public <V> void addColumn(Column<T, V> column, String name, int width, FieldUpdater<T, V> fieldUpdater) {
		column.setFieldUpdater(fieldUpdater);
		cellTable.addColumn(column, name);
		cellTable.setColumnWidth(column, width, Unit.PCT);
	}

	public <V> void addColumn(Column<T, V> column, String name, int width, Comparator<T> comparator,
			FieldUpdater<T, V> fieldUpdater) {
		column.setSortable(true);
		column.setFieldUpdater(fieldUpdater);
		columnSortHandler.setComparator(column, comparator);
		cellTable.addColumn(column, name);
		cellTable.setColumnWidth(column, width, Unit.PCT);
	}


	public void clearTable() {
		int cols = cellTable.getColumnCount();
		for (int i = 0; i < cols; ++i) {
			cellTable.removeColumn(0);
		}
	}

	public <V> void addColumn(Column<T, V> column, String name, int width) {
		cellTable.addColumn(column, name);
		cellTable.setColumnWidth(column, width, Unit.PCT);

	}

	public Widget getPager() {
		return pager;
	}

}
