package com.epam.courses.client.supportingUI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccountSettingsDialog {

	private DialogBox dialogBox = new DialogBox();
	private VerticalPanel vPanel = new VerticalPanel();
	private Button personalDataButton = new Button("Изменить личные данные");
	private Button passwordButton = new Button("Изменить пароль");
	private Button closeButton = new Button("Закрыть");

	public AccountSettingsDialog(ClickHandler personalDataHAndler, ClickHandler passwordHandler) {
		dialogBox.setPopupPosition(3 * Window.getClientWidth() / 7, Window.getClientHeight() / 3);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(personalDataButton);
		vPanel.add(passwordButton);
		vPanel.add(closeButton);

		passwordButton.addClickHandler(passwordHandler);
		personalDataButton.addClickHandler(personalDataHAndler);

		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}

		});
		dialogBox.add(vPanel);
	}

	public void show() {
		dialogBox.show();
	}


}
