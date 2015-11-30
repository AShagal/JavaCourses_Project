package com.epam.courses.client.supportingUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.xml.internal.ws.Closeable;

public class MessageWindow {

	private final DialogBox dialogBox = new DialogBox();
	private final Label errorLabel = new Label("");
	private final Button closeButton = new Button("Закрыть");

	public MessageWindow() {
		dialogBox.setPopupPosition(3 * Window.getClientWidth() / 7, Window.getClientHeight() / 3);
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		vPanel.add(errorLabel);
		vPanel.add(closeButton);
		dialogBox.add(vPanel);
	}

	public void showErrorMessage(String msg) {
		dialogBox.setText("Ошибка");
		showMsg(msg);
	}

	public void showWarningMessage(String msg) {
		dialogBox.setText("Предупреждение");
		showMsg(msg);
	}

	public void showInfoMessage(String msg) {
		dialogBox.setText("Информация");
		showMsg(msg);
	}

	private void showMsg(String msg) {
		errorLabel.setText(msg);
		dialogBox.show();
		closeButton.setFocus(true);
	}

}
