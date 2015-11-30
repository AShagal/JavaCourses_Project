package com.epam.courses.client.supportingUI;

import java.util.ArrayList;
import java.util.List;

import com.epam.courses.shared.User;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.VerticalPanel;

public class IntputInfoDialog {

	public enum Action {
		ADD_PERSON, ADD_COURSE, ADD_REVIEW, LOGIN, UPDATE_INFO, UPDATE_PASSWORD
	};

	public final static int MAX_TEXT_SIZE = 500;

	private DialogBox dialogBox = new DialogBox();
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel hPanel = new HorizontalPanel();
	private Button submitButton = new Button("Ок");
	private Button closeButton = new Button("Закрыть");
	private Label nameLabel = new Label("Имя:");
	private Label surnameLabel = new Label("Фамилия:");
	private Label loginLabel = new Label("Логин:");
	private Label passwordLabel = new Label("Пароль:");
	private Label newPasswordLabel = new Label("Новый пароль:");
	private Label oldPasswordLabel = new Label("Старый пароль:");
	private Label titleLabel = new Label("Название:");
	private Label lecturerLabel = new Label("Преподаватель:");
	private Label studentLabel = new Label();
	private Label errorLabel = new Label("");
	private final TextBox nameTextBox = new TextBox();
	private final TextBox surnameTextBox = new TextBox();
	private final TextBox titleTextBox = new TextBox();
	private final TextBox loginTextBox = new TextBox();
	private final PasswordTextBox passwordTextBox = new PasswordTextBox();
	private final PasswordTextBox newPasswordTextBox = new PasswordTextBox();
	private final ListBox lecturerDropBox = new ListBox();
	private final TextArea review = new TextArea();
	private int lecturerIdChoosen;
	private HandlerRegistration handlerSubmit;
	List<Integer> indLecturers = new ArrayList<>();

	public IntputInfoDialog() {
		dialogBox.setPopupPosition(3 * Window.getClientWidth() / 7, Window.getClientHeight() / 3);
		getErrorLabel().getElement().getStyle().setColor("red");
		vPanel.add(nameLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(getNameTextBox());
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(surnameLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(surnameTextBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(loginLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(loginTextBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(passwordLabel);
		vPanel.add(oldPasswordLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(passwordTextBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(newPasswordLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(newPasswordTextBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(titleLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(titleTextBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vPanel.add(lecturerLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(lecturerDropBox);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		review.setWidth(Window.getClientWidth() / 5 + "px");
		review.setHeight(Window.getClientHeight() / 5 + "px");
		vPanel.setWidth(Window.getClientWidth() / 5 + "px");
		nameTextBox.setWidth("50%");
		titleTextBox.setWidth("50%");
		surnameTextBox.setWidth("50%");
		loginTextBox.setWidth("50%");
		passwordTextBox.setWidth("50%");
		newPasswordTextBox.setWidth("50%");
		lecturerDropBox.setWidth("50%");
		review.setWidth("100%");
		vPanel.add(studentLabel);
		vPanel.add(review);
		vPanel.add(errorLabel);
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		vPanel.add(hPanel);

		hPanel.add(submitButton);
		hPanel.add(closeButton);

		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getNameTextBox().setText("");
				surnameTextBox.setText("");
				loginTextBox.setText("");
				passwordTextBox.setText("");
				getErrorLabel().setText("");
				dialogBox.hide();
			}

		});

		lecturerDropBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				lecturerIdChoosen = indLecturers.get(lecturerDropBox.getSelectedIndex());
			}
		});
		dialogBox.add(vPanel);
	}

	public void setSubmitClickHandler(ClickHandler clickHandler) {
		if (null != handlerSubmit) {
			handlerSubmit.removeHandler();
		}
		handlerSubmit = submitButton.addClickHandler(clickHandler);
	}

	public void setListOfLecturers(List<User> arrLecturer) {
		lecturerDropBox.clear();
		indLecturers.clear();
		for (User lecturer : arrLecturer) {
			indLecturers.add(lecturer.getId());
			lecturerDropBox.addItem(lecturer.getSurname() + " " + lecturer.getName());
		}
		if (arrLecturer.size() != 0) {
			lecturerIdChoosen = indLecturers.get(0);
		} else {
			lecturerIdChoosen = -1;
		}
	}

	DialogBox getDialogBox() {
		return dialogBox;
	}

	Label getErrorLabel() {
		return errorLabel;
	}

	public void changeAction(Action action) {
		courseInfoVisibility(false);
		changePasswordVisibility(false);
		loginPasswordVisibility(true);
		reviewVisibility(false);
		studentLabel.setVisible(false);

		switch (action) {
		case ADD_PERSON:
			dialogBox.setText("Добавление пользователя");
			nameSurnameVisibility(true);
			break;
		case ADD_COURSE:
			dialogBox.setText("Добавление курса");
			nameSurnameVisibility(false);
			loginPasswordVisibility(false);
			courseInfoVisibility(true);
			break;
		case ADD_REVIEW:
			dialogBox.setText("Добавление отзыва");
			reviewVisibility(true);
			nameSurnameVisibility(false);
			loginPasswordVisibility(false);
			courseInfoVisibility(false);
			studentLabel.setVisible(true);
			break;
		case LOGIN:
			dialogBox.setText("Вход");
			nameSurnameVisibility(false);
			break;
		case UPDATE_PASSWORD:
			dialogBox.setText("Изменение пароля");
			nameSurnameVisibility(false);
			loginPasswordVisibility(false);
			changePasswordVisibility(true);
			break;
		case UPDATE_INFO:
			dialogBox.setText("Изменение личной информации");
			loginPasswordVisibility(false);
			nameSurnameVisibility(true);
			passwordLabel.setVisible(true);
			passwordTextBox.setVisible(true);
			break;
		default:
			break;
		}
	}

	private void changePasswordVisibility(boolean flag) {
		oldPasswordLabel.setVisible(flag);
		passwordTextBox.setVisible(flag);
		newPasswordLabel.setVisible(flag);
		newPasswordTextBox.setVisible(flag);
	}

	private void loginPasswordVisibility(boolean flag) {
		loginLabel.setVisible(flag);
		loginTextBox.setVisible(flag);
		passwordLabel.setVisible(flag);
		passwordTextBox.setVisible(flag);
	}

	private void courseInfoVisibility(boolean flag) {
		titleLabel.setVisible(flag);
		titleTextBox.setVisible(flag);
		lecturerLabel.setVisible(flag);
		lecturerDropBox.setVisible(flag);
	}

	private void nameSurnameVisibility(boolean flag) {
		nameLabel.setVisible(flag);
		nameTextBox.setVisible(flag);
		surnameLabel.setVisible(flag);
		surnameTextBox.setVisible(flag);
	}

	private void reviewVisibility(boolean flag) {
		studentLabel.setVisible(flag);
		review.setVisible(flag);
	}

	public String getName() {
		return getNameTextBox().getText();
	}

	public String getSurname() {
		return surnameTextBox.getText();
	}

	public String getLogin() {
		return loginTextBox.getText();
	}

	public String getPassword() {
		return passwordTextBox.getText();
	}

	public TextBox getNameTextBox() {
		return nameTextBox;
	}

	public ButtonBase setTextErrorLabel(String msg) {
		errorLabel.setText(msg);
		return null;
	}

	public void show() {
		setTextErrorLabel("");
		dialogBox.show();
	}

	public void hide() {
		dialogBox.hide();
	}

	public void clearFields() {
		nameTextBox.setText("");
		surnameTextBox.setText("");
		loginTextBox.setText("");
		passwordTextBox.setText("");
		titleTextBox.setText("");
		newPasswordTextBox.setText("");
	}

	public String getTitle() {
		return titleTextBox.getText();
	}

	public TextBox getTitleTextBox() {
		return titleTextBox;
	}

	public int getLecturerIdChoosen() {
		return lecturerIdChoosen;
	}

	public String getText() {
		return review.getText();
	}

	public void setStudentName(String string) {
		studentLabel.setText("Студент: " + string);
	}

	public void setReviewText(String text) {
		review.setText(text);
	}

	public void dropRegistrationField() {
		passwordTextBox.setText("");
		loginTextBox.setText("");
	}

	public void setSurnameField(String surname) {
		surnameTextBox.setText(surname);

	}

	public void setNameField(String name) {
		nameTextBox.setText(name);

	}

	public String getNewPassword() {
		return newPasswordTextBox.getText();
	}

}
