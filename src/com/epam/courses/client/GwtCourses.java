package com.epam.courses.client;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import com.epam.courses.client.Session.Role;
import com.epam.courses.client.service.AdminService;
import com.epam.courses.client.service.AdminServiceAsync;
import com.epam.courses.client.service.CourseService;
import com.epam.courses.client.service.CourseServiceAsync;
import com.epam.courses.client.service.LecturerService;
import com.epam.courses.client.service.LecturerServiceAsync;
import com.epam.courses.client.service.RegistrationService;
import com.epam.courses.client.service.RegistrationServiceAsync;
import com.epam.courses.client.service.StudentCourseService;
import com.epam.courses.client.service.StudentCourseServiceAsync;
import com.epam.courses.client.service.StudentService;
import com.epam.courses.client.service.StudentServiceAsync;
import com.epam.courses.client.service.UserService;
import com.epam.courses.client.service.UserServiceAsync;
import com.epam.courses.client.supportingUI.AccountSettingsDialog;
import com.epam.courses.client.supportingUI.IntputInfoDialog;
import com.epam.courses.client.supportingUI.IntputInfoDialog.Action;
import com.epam.courses.client.supportingUI.MessageWindow;
import com.epam.courses.client.supportingUI.MyCellTable;
import com.epam.courses.client.supportingUI.MySelectionCell;
import com.epam.courses.client.supportingUI.RoleEnableEditCell;
import com.epam.courses.shared.Course;
import com.epam.courses.shared.User;
import com.epam.courses.shared.StudentCourse;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtCourses implements EntryPoint {

	private static final int MAX_REVIW_SIZE = 500;

	private static final double MENU_PANEL_WIDTH_MULT = 0.1;

	private static final double CONTENT_PANEL_HEIGHT_MULT = 0.95;

	private static final double CONTENT_PANEL_WIDTH_MULT = 0.88;

	private Session session = new Session();

	private static final int MAX_MARK = 5;
	public static final List<String> MARKS = new ArrayList<String>();

	private static final int MAX_WORD_LENGTH = 30;

	{
		for (int i = 0; i < MAX_MARK + 1; ++i) {
			MARKS.add(String.valueOf(i));
		}

	}

	private final MessageWindow messageWindow = new MessageWindow();

	private final LecturerServiceAsync lecturerService = GWT.create(LecturerService.class);
	private final CourseServiceAsync courseService = GWT.create(CourseService.class);
	private final StudentServiceAsync studentService = GWT.create(StudentService.class);
	private final StudentCourseServiceAsync studentCourseService = GWT.create(StudentCourseService.class);
	private final RegistrationServiceAsync regService = GWT.create(RegistrationService.class);
	private final UserServiceAsync userService = GWT.create(UserService.class);
	private final AdminServiceAsync adminService = GWT.create(AdminService.class);

	private MyCellTable<Course> courseGrid;
	private MyCellTable<User> lecturerGrid;
	private MyCellTable<User> studentGrid;
	private MyCellTable<StudentCourse> reviewGrid;
	private MyCellTable<StudentCourse> myCoursesGrid;
	private MySelectionCell lecturerSelection;

	private final Button signUpButton = new Button();
	private final Button signInButton = new Button();
	private final Button signOutButton = new Button();
	private final Button studentsListButton = new Button();
	private final Button coursesListButton = new Button();
	private final Button lecturersListButton = new Button();
	private final Button studentCoursesListButton = new Button();
	private final Button myCoursesButton = new Button();
	private final Button addReviewButton = new Button();
	private final Button addLecturerButton = new Button();
	private final Button addCourseButton = new Button();
	private final Button signCourseButton = new Button();
	private final Button accountSettingsButton = new Button();
	private final Button addAdminButton = new Button();

	private int clientWindowHeight;
	private int clientWindowWidth;

	private IntputInfoDialog addDialog;

	private void settingsButton(Button button, String text, String width, Panel panel, boolean isVisible,
			ClickHandler clickHandler) {
		button.setText(text);
		button.addClickHandler(clickHandler);
		button.setWidth(width);
		button.setVisible(isVisible);
		panel.add(button);
	}

	@Override
	public void onModuleLoad() {

		RoleEnableEditCell.setEditable(false);

		addDialog = new IntputInfoDialog();

		courseGrid = new MyCellTable<Course>(Course.KEY_PROVIDER);
		lecturerGrid = new MyCellTable<User>(User.KEY_PROVIDER);
		studentGrid = new MyCellTable<User>(User.KEY_PROVIDER);
		reviewGrid = new MyCellTable<StudentCourse>(StudentCourse.KEY_PROVIDER);
		myCoursesGrid = new MyCellTable<StudentCourse>(StudentCourse.KEY_PROVIDER);

		initTabels(Role.USER);

		clientWindowHeight = Window.getClientHeight();
		clientWindowWidth = Window.getClientWidth();
		String lineStyle = "3px solid #e7e7e7";

		final HorizontalPanel hMainPanel = new HorizontalPanel();
		hMainPanel.getElement().getStyle().setProperty("border", lineStyle);

		final VerticalPanel vMenuPanel = new VerticalPanel();
		String vPanelMenuWidth = clientWindowWidth * MENU_PANEL_WIDTH_MULT + "px";
		vMenuPanel.setWidth(vPanelMenuWidth);

		final VerticalPanel vContentPanel = new VerticalPanel();
		String vContentPanelWidth = clientWindowWidth * CONTENT_PANEL_WIDTH_MULT + "px";
		vContentPanel.setWidth(vContentPanelWidth);

		final VerticalPanel vSeparatorPanel = new VerticalPanel();
		vSeparatorPanel.getElement().getStyle().setProperty("border", lineStyle);
		vSeparatorPanel.setWidth("2px");
		vSeparatorPanel.setHeight(clientWindowHeight * CONTENT_PANEL_HEIGHT_MULT + "px");
		vSeparatorPanel.add(new Label());

		final HorizontalPanel hActionPanel = new HorizontalPanel();
		hActionPanel.setVisible(false);

		vContentPanel.add(hActionPanel);
		vContentPanel.add(courseGrid.getTable());
		vContentPanel.add(lecturerGrid.getTable());
		vContentPanel.add(studentGrid.getTable());
		vContentPanel.add(reviewGrid.getTable());
		vContentPanel.add(myCoursesGrid.getTable());

		vContentPanel.add(courseGrid.getPager());
		vContentPanel.add(lecturerGrid.getPager());
		vContentPanel.add(studentGrid.getPager());
		vContentPanel.add(reviewGrid.getPager());
		vContentPanel.add(myCoursesGrid.getPager());

		hMainPanel.add(vMenuPanel);
		hMainPanel.add(vSeparatorPanel);
		hMainPanel.add(vContentPanel);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				clientWindowWidth = event.getWidth();
				clientWindowHeight = event.getHeight();
				String vPanelMenuWidth = clientWindowWidth * MENU_PANEL_WIDTH_MULT + "px";
				vMenuPanel.setWidth(vPanelMenuWidth);
				String vContentPanelWidth = clientWindowWidth * CONTENT_PANEL_WIDTH_MULT + "px";
				vContentPanel.setWidth(vContentPanelWidth);
				vSeparatorPanel.setHeight(clientWindowHeight * CONTENT_PANEL_HEIGHT_MULT + "px");
				coursesListButton.setWidth(vPanelMenuWidth);
				lecturersListButton.setWidth(vPanelMenuWidth);
				studentsListButton.setWidth(vPanelMenuWidth);
				myCoursesButton.setWidth(vPanelMenuWidth);
				addReviewButton.setWidth(vPanelMenuWidth);
				addLecturerButton.setWidth(vPanelMenuWidth);
				addCourseButton.setWidth(vPanelMenuWidth);
				signUpButton.setWidth(vPanelMenuWidth);
				signInButton.setWidth(vPanelMenuWidth);
				signOutButton.setWidth(vPanelMenuWidth);
				addAdminButton.setWidth(vPanelMenuWidth);
				accountSettingsButton.setWidth(vPanelMenuWidth);
			}
		});

		getListLecturers();

		settingsButton(coursesListButton, "Список курсов", vPanelMenuWidth, vMenuPanel, true, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (session.getRole() == Role.ADMIN) {
					getListLecturers();
					hActionPanel.setVisible(true);
					addLecturerButton.setVisible(false);
					addCourseButton.setVisible(true);
				} else if (session.getRole() == Role.STUDENT) {
					getIdCoursesByStudent(session.getPerson().getId());
					addLecturerButton.setVisible(false);
					addCourseButton.setVisible(false);
					signCourseButton.setVisible(true);
					myCoursesGrid.setVisible(false);
					fillCoursesGrid();
				}
				studentGrid.setVisible(false);
				lecturerGrid.setVisible(false);
				reviewGrid.setVisible(false);
				fillCoursesGrid();
				courseGrid.setVisible(true);
			}
		});
		settingsButton(lecturersListButton, "Преподаватели", vPanelMenuWidth, vMenuPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (session.getRole() == Role.ADMIN) {
					hActionPanel.setVisible(true);
					addCourseButton.setVisible(false);
					getListLecturers();
					addLecturerButton.setVisible(true);
				}
				studentGrid.setVisible(false);
				courseGrid.setVisible(false);
				reviewGrid.setVisible(false);
				fillLecturerGrid();
			}
		});
		settingsButton(studentsListButton, "Студенты", vPanelMenuWidth, vMenuPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hActionPanel.setVisible(false);
				courseGrid.setVisible(false);
				lecturerGrid.setVisible(false);
				reviewGrid.setVisible(false);
				fillStudentGrid();
			}
		});
		settingsButton(myCoursesButton, "Мои курсы", vPanelMenuWidth, vMenuPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				myCoursesGrid.setVisible(true);
				courseGrid.setVisible(false);
				fillMyCoursesGrid();
			}
		});
		settingsButton(addAdminButton, "Добавить администратора", vPanelMenuWidth, vMenuPanel, false,
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						addDialog.clearFields();
						addDialog.changeAction(Action.ADD_PERSON);
						addDialog.setSubmitClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								String name = addDialog.getName();
								String surname = addDialog.getSurname();
								String login = addDialog.getLogin();
								String password = addDialog.getPassword();
								if (!isCorrect(name, surname, login, password)) {
									addDialog.setTextErrorLabel(
											"Ошибка: длина значения полей должна быть меньше 30 символов.");
									addDialog.getNameTextBox().setFocus(true);
								} else {
									addDialog.setTextErrorLabel("");
									User admin = new User(name, surname, login, password);
									sendAdminToServer(admin);
								}
							}

							private void sendAdminToServer(User admin) {
								adminService.addAdmin(admin, new AsyncCallback<Boolean>() {
									@Override
									public void onFailure(Throwable caught) {
										messageWindow.showErrorMessage("Ошибка на сервере: добавление администратора.");
									}

									@Override
									public void onSuccess(Boolean result) {
										if (result) {
											addDialog.setTextErrorLabel("Пользователь успешно добавлен.");
											addDialog.clearFields();
										} else {
											addDialog.setTextErrorLabel(
													"Ошибка: пользователь с таким логином уже существует.");
										}
									}
								});

							}
						});
						addDialog.show();
					}
				});

		settingsButton(addReviewButton, "Добавить отзыв", vPanelMenuWidth, vMenuPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				courseGrid.setVisible(false);
				fillReviewGrid();

			}
		});
		settingsButton(addLecturerButton, "Добавить", vPanelMenuWidth, hActionPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addDialog.changeAction(Action.ADD_PERSON);
				addDialog.getNameTextBox().setFocus(true);
				addDialog.setSubmitClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						String name = addDialog.getName();
						String surname = addDialog.getSurname();
						String password = addDialog.getLogin();
						String login = addDialog.getPassword();
						if (!isCorrect(surname, name, login, password)) {
							addDialog.setTextErrorLabel("Ошибка: длина значения полей должна быть меньше 30 символов.");
							addDialog.getNameTextBox().setFocus(true);
						} else {
							addDialog.setTextErrorLabel("");
							User lecturer = new User(name, surname, login, password);
							sendLecturerToServer(lecturer);
						}
					}

					private void sendLecturerToServer(User lecturer) {
						lecturerService.sendNewLecturerToServer(lecturer, new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								messageWindow.showErrorMessage("Ошибка сервера: добавление преподавателя.");

							}

							@Override
							public void onSuccess(Boolean result) {
								addDialog.setTextErrorLabel("Преподаватель добавлен.");
								getListLecturers();
								addDialog.clearFields();
							}
						});
					}
				});
				addDialog.show();
			}
		});
		settingsButton(addCourseButton, "Добавить", vPanelMenuWidth, hActionPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				addDialog.changeAction(Action.ADD_COURSE);
				addDialog.getTitleTextBox().setFocus(true);
				addDialog.setListOfLecturers(session.getLecturers());
				addDialog.setSubmitClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						String title = addDialog.getTitle();
						int idLecturer = addDialog.getLecturerIdChoosen();
						if (!isCorrect(title) || idLecturer == -1) {
							addDialog.setTextErrorLabel("Ошибка: длина значения полей должна быть меньше 30 символов.");
							addDialog.getTitleTextBox().setFocus(true);
						} else {
							addDialog.setTextErrorLabel("");
							Course course = new Course(title, idLecturer);
							sendCourseToServer(course);
						}
					}

					private void sendCourseToServer(Course course) {
						courseService.sendNewCourseToServer(course, new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								messageWindow.showErrorMessage("Ошибка на сервере: добавление нового курса.");
							}

							@Override
							public void onSuccess(Boolean result) {
								if (result == true) {
									addDialog.setTextErrorLabel("Курс успешно добавлен.");
									addDialog.clearFields();
								} else {
									addDialog.setTextErrorLabel("Ошибка: такой курс уже существует.");
								}
							}

						});

					}
				});
				addDialog.show();
				getListLecturers();

			}
		});

		settingsButton(signUpButton, "Регистрация", vPanelMenuWidth, vMenuPanel, true, new ClickHandler() { // registration
			@Override
			public void onClick(ClickEvent event) {
				courseGrid.setVisible(false);
				addDialog.changeAction(Action.ADD_PERSON);
				addDialog.setSubmitClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						sendRegistrationDataStudent(addDialog.getName(), addDialog.getSurname(), addDialog.getLogin(),
								addDialog.getPassword());
					}

					private void sendRegistrationDataStudent(String name, String surname, String login,
							String password) {
						regService.registerStudent(name, surname, login, password, new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								messageWindow.showErrorMessage("Ошибка на сервере: регистрация в системе.");

							}

							@Override
							public void onSuccess(Boolean result) {
								if (result) {
									addDialog.hide();
									addDialog.clearFields();
									messageWindow.showInfoMessage("Пользователь успешно добавлен.");
								} else {
									addDialog.setTextErrorLabel("Ошибка: пользователь с таким логином уже существует.");
									addDialog.dropRegistrationField();
								}

							}
						});

					}
				});
				addDialog.show();
			}
		});
		settingsButton(signInButton, "Вход", vPanelMenuWidth, vMenuPanel, true, new ClickHandler() { // login
			@Override
			public void onClick(ClickEvent event) {
				addDialog.changeAction(Action.LOGIN);
				addDialog.setSubmitClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						sendLoginData(addDialog.getLogin(), addDialog.getPassword());
					}

				});
				addDialog.show();
				courseGrid.setVisible(false);

			}

			private void sendLoginData(String login, String password) {
				regService.getUserByLoginPassword(login, password, new AsyncCallback<User>() {
					@Override
					public void onFailure(Throwable caught) {
						addDialog.setTextErrorLabel("Ошибка: пользователь не найден.");
					}

					@Override
					public void onSuccess(User result) {
						if (result == null) {
							addDialog.setTextErrorLabel("Ошибка: пользователь с такими данными не найден.");
							return;
						}
						Role role = Session.ROLES_MAP.get(result.getRole());
						addDialog.changeAction(Action.LOGIN);
						addDialog.clearFields();
						addDialog.hide();

						changePage(role, result);
					}

				});
			}
		});

		settingsButton(accountSettingsButton, "Настройка аккаунта", vPanelMenuWidth, vMenuPanel, false,
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						AccountSettingsDialog accDialog = new AccountSettingsDialog(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								addDialog.changeAction(Action.UPDATE_INFO);
								addDialog.setNameField(session.getPerson().getName());
								addDialog.setSurnameField(session.getPerson().getSurname());
								addDialog.setSubmitClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										final String pass = addDialog.getPassword();
										final String name = addDialog.getName();
										final String oldName = session.getPerson().getName();
										final String oldSurname = session.getPerson().getSurname();
										final String surname = addDialog.getSurname();
										if (name == oldName && surname == oldSurname) {
											addDialog.setTextErrorLabel("Ошибка: поля не были изменены.");
											return;
										}
										if (!isCorrect(name, surname)) {
											addDialog.setTextErrorLabel(
													"Ошибка: длина значения полей должна быть меньше 30 символов.");
											return;
										}
										if (!pass.equals(session.getPerson().getPassword())) {
											addDialog.setTextErrorLabel("Ошибка: старый пароль неверный.");
											return;
										}
										updatePersonalData(name, surname, session.getPerson().getId(),
												session.getRole());
									}

									private void updatePersonalData(final String name, final String surname, int id,
											Role role) {
										userService.updatePersonalData(name, surname, id, new AsyncCallback<Boolean>() {

											@Override
											public void onFailure(Throwable caught) {
												messageWindow.showErrorMessage(
														"Ошибка на сервере: обновление личных данных.");
											}

											@Override
											public void onSuccess(Boolean result) {
												messageWindow.showInfoMessage("Информация успешно обновлена.");

											}
										});
									}
								});
								addDialog.show();
							}
						}, new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								addDialog.changeAction(Action.UPDATE_PASSWORD);
								addDialog.setSubmitClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										final String newPassword = addDialog.getNewPassword();
										final String oldPassword = addDialog.getPassword();
										if (!oldPassword.equals(session.getPerson().getPassword())) {
											addDialog.setTextErrorLabel("Ошибка: старый пароль неверный.");
											return;
										}
										if (!isCorrect(newPassword)) {
											addDialog.setTextErrorLabel(
													"Ошибка: длина значения полей должна быть меньше 30 символов.");
											return;
										}
										updatePassword(newPassword, session.getPerson().getId());
									}

									private void updatePassword(String newPassword, int id) {
										userService.updatePassword(id, newPassword, new AsyncCallback<Boolean>() {

											@Override
											public void onFailure(Throwable caught) {
												messageWindow.showErrorMessage("Ошибка на сервере: обновление пароля.");
											}

											@Override
											public void onSuccess(Boolean result) {
												messageWindow.showInfoMessage("Пароль успешно обновлён.");

											}
										});
									}
								});
								addDialog.show();
							}
						});
						accDialog.show();
					}
				});

		settingsButton(signOutButton, "Выход", vPanelMenuWidth, vMenuPanel, false, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				session.endSession();
				hActionPanel.setVisible(false);
				lecturersListButton.setVisible(false);
				signUpButton.setVisible(true);
				signInButton.setVisible(true);
				signOutButton.setVisible(false);
				addReviewButton.setVisible(false);
				studentsListButton.setVisible(false);
				studentCoursesListButton.setVisible(false);
				courseGrid.setVisible(false);
				lecturerGrid.setVisible(false);
				studentGrid.setVisible(false);
				reviewGrid.setVisible(false);
				myCoursesButton.setVisible(false);
				myCoursesGrid.setVisible(false);
				accountSettingsButton.setVisible(false);
				addAdminButton.setVisible(false);
			}
		});

		RootPanel.get().add(hMainPanel);
	}

	protected void fillReviewGrid() {
		studentCourseService.sendRequestReviewInfo(session.getPerson().getId(),
				new AsyncCallback<List<StudentCourse>>() {

					@Override
					public void onFailure(Throwable caught) {

						messageWindow.showErrorMessage("Ошибка сервера: получение списка отзывов.");
					}

					@Override
					public void onSuccess(List<StudentCourse> result) {
						reviewGrid.initTable(result);
						reviewGrid.setVisible(true);
					}
				});
	}

	protected void fillStudentGrid() {
		studentService.sendRequestStudents(new AsyncCallback<List<User>>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: получение списка студентов.");

			}

			@Override
			public void onSuccess(List<User> result) {
				studentGrid.initTable(result);
				studentGrid.setVisible(true);
			}

		});

	}

	protected void fillCoursesGrid() {
		courseService.sendRequestCourses(new AsyncCallback<List<Course>>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: получение списка курсов.");
			}

			@Override
			public void onSuccess(List<Course> result) {
				courseGrid.initTable(result);
				courseGrid.setVisible(true);
			}

		});

	}

	protected void getListLecturers() {
		lecturerService.sendRequestLecturers(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: получение списка преподавателей.");
			}

			@Override
			public void onSuccess(List<User> result) {
				session.setLecturers(result);
				List<String> lecNames = new ArrayList<String>();
				for (User lecturer : session.getLecturers()) {
					lecNames.add(lecturer.getFullName());
				}
				lecturerSelection.refreshOptionsList(lecNames);
			}

		});
	}

	protected void fillLecturerGrid() {
		lecturerService.sendRequestLecturers(new AsyncCallback<List<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: получение списка преподавателей.");
			}

			@Override
			public void onSuccess(List<User> result) {
				lecturerGrid.initTable(result);
				lecturerGrid.setVisible(true);
			}

		});

	}

	private void initTabels(Role role) {
		courseGrid.addColumn(new Column<Course, String>(new RoleEnableEditCell()) {

			@Override
			public String getValue(Course object) {
				return object.getTitle();
			}
		}, "Курс", 30, new Comparator<Course>() {

			@Override
			public int compare(Course o1, Course o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		}, new FieldUpdater<Course, String>() {

			@Override
			public void update(int index, Course object, String value) {
				updateCourseTitle(object.getId(), value);
			}
		});
		if (role == Role.ADMIN) {
			final List<User> lecturers = session.getLecturers();
			List<String> lecNames = new ArrayList<String>();
			for (User lecturer : lecturers) {
				lecNames.add(lecturer.getFullName());
			}

			lecturerSelection = new MySelectionCell(lecNames);

			courseGrid.addColumn(new Column<Course, String>(lecturerSelection) {

				@Override
				public String getValue(Course object) {
					return object.getFullName();
				}
			}, "Преподаватель", 30, new Comparator<Course>() {

				@Override
				public int compare(Course o1, Course o2) {
					return o1.getFullName().compareTo(o2.getFullName());
				}
			}, new FieldUpdater<Course, String>() {

				@Override
				public void update(int index, Course object, String value) {
					for (User lecturer : lecturers) {
						if ((lecturer.getFullName()).equals(value)) {
							updateLecturerCourse(lecturer.getId(), object.getId());
						}
					}
				}

				private void updateLecturerCourse(int idLecturer, int idCourse) {
					courseService.updateCourseLecturer(idCourse, idLecturer, new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							messageWindow.showErrorMessage("Ошибка сервера: изменение преподавателя.");

						}

						@Override
						public void onSuccess(Boolean result) {
							messageWindow.showInfoMessage("Преподаватель успешно обновлён.");
						}
					});
				}

			});

			courseGrid.addColumn(new Column<Course, String>(new ButtonCell()) {

				@Override
				public String getValue(Course object) {
					return "Удалить";
				}

			}, "Действие", 20, new FieldUpdater<Course, String>() {

				@Override
				public void update(int index, Course object, String value) {
					deleteCourseById(object.getId());
				}

			});
		} else {
			courseGrid.addColumn(new Column<Course, String>(new TextCell()) {

				@Override
				public String getValue(Course object) {
					return object.getFullName();
				}
			}, "Преподаватель", 30, new Comparator<Course>() {

				@Override
				public int compare(Course o1, Course o2) {
					return o1.getFullName().compareTo(o2.getFullName());
				}
			});
		}
		if (role == Role.STUDENT) {
			courseGrid.addColumn(new Column<Course, String>(new ButtonCell()) {

				@Override
				public String getValue(Course object) {
					return "Записаться";
				}

				@Override
				public void render(Context context, Course src, SafeHtmlBuilder sb) {
					if (session.getStudCourses().contains(src.getId())) {
						sb.appendHtmlConstant("<Button disabled='disabled'>Записаться</button>");
					} else {
						sb.appendHtmlConstant("<button>Записаться</button>");
					}

				}

			}, "Действие", 20, new FieldUpdater<Course, String>() {

				@Override
				public void update(int index, Course object, String value) {
					addStudentCourse(object.getId(), session.getPerson().getId());
				}

			});
		}

		switch (role) {
		case ADMIN:
			lecturerGrid.addColumn(new Column<User, String>(new RoleEnableEditCell()) {

				@Override
				public String getValue(User object) {
					return object.getSurname();
				}

			}, "Фамилия", 20, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getSurname().compareTo(o2.getSurname());
				}

			}, new FieldUpdater<User, String>() {

				@Override
				public void update(int index, User object, String value) {
					updateLecturerSurname(object.getId(), value);
				}

			});

			lecturerGrid.addColumn(new Column<User, String>(new RoleEnableEditCell()) {

				@Override
				public String getValue(User object) {
					return object.getName();
				}

			}, "Имя", 20, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getName().compareTo(o2.getName());
				}

			}, new FieldUpdater<User, String>() {

				@Override
				public void update(int index, User object, String value) {
					updateLecturerName(object.getId(), value);
				}

			});
			lecturerGrid.addColumn(new Column<User, String>(new TextCell()) {

				@Override
				public String getValue(User object) {
					return object.getLogin();
				}

			}, "Логин", 20, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getLogin().compareTo(o2.getLogin());
				}

			});
			lecturerGrid.addColumn(new Column<User, String>(new ButtonCell()) {

				@Override
				public String getValue(User object) {
					return "Удалить";
				}

			}, "Действие", 20, new FieldUpdater<User, String>() {

				@Override
				public void update(int index, User object, String value) {
					deleteLecturerById(object.getId());
				}

			});

			studentGrid.addColumn(new Column<User, String>(new TextCell()) {

				@Override
				public String getValue(User object) {
					return object.getName();
				}
			}, "Имя", 30, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			studentGrid.addColumn(new Column<User, String>(new TextCell()) {

				@Override
				public String getValue(User object) {
					return object.getSurname();
				}
			}, "Фамилия", 30, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getSurname().compareTo(o2.getSurname());
				}
			});
			studentGrid.addColumn(new Column<User, String>(new TextCell()) {

				@Override
				public String getValue(User object) {
					return object.getLogin();
				}
			}, "Логин", 30, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					return o1.getLogin().compareTo(o2.getLogin());
				}
			});
			break;
		case STUDENT:
			myCoursesGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.getTitle();
				}
			}, "Курс", 15, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
			});
			myCoursesGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return String.valueOf(object.getMark());
				}
			}, "Оценка", 15, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					return o1.getMark() - o2.getMark();
				}
			});
			myCoursesGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.getReview();
				}

				@Override
				public void render(Context context, StudentCourse src, SafeHtmlBuilder sb) {
					String escaped_text = src.getReview().replace("\n", "<BR>").replace("\r", "");
					sb.appendHtmlConstant(escaped_text);
				}

			}, "Отзыв", 30);
			myCoursesGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.isFinished() ? "Закончен" : "Идёт";
				}

			}, "Отзыв", 30, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					if (o1.isFinished() == o2.isFinished()) {
						return 0;
					} else if (o1.isFinished()) {
						return 1;
					}
					return -1;
				}
			});
			myCoursesGrid.addColumn(new Column<StudentCourse, String>(new ButtonCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return "Отказаться";
				}

				@Override
				public void render(Context context, StudentCourse src, SafeHtmlBuilder sb) {
					if (src.isFinished()) {
						sb.appendHtmlConstant("<Button disabled='disabled'>Отказаться</button>");
					} else {
						sb.appendHtmlConstant("<button>Отказаться</button>");
					}

				}

			}, "Действие", 20, new FieldUpdater<StudentCourse, String>() {

				@Override
				public void update(int index, StudentCourse object, String value) {
					deleteStudentCourse(object.getId());
				}
			});

			break;
		case LECTURER:
			reviewGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.getTitle();
				}
			}, "Курс", 20, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
			});
			reviewGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.getFullName();
				}
			}, "Студент", 20, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					return o1.getFullName().compareTo(o2.getFullName());
				}
			});

			SelectionCell markCell = new SelectionCell(MARKS);
			reviewGrid.addColumn(new Column<StudentCourse, String>(markCell) {

				@Override
				public String getValue(StudentCourse object) {
					return String.valueOf(object.getMark());
				}
			}, "Преподаватель", 30, new Comparator<StudentCourse>() {

				@Override
				public int compare(StudentCourse o1, StudentCourse o2) {
					return o1.getMark() - o2.getMark();
				}
			}, new FieldUpdater<StudentCourse, String>() {

				@Override
				public void update(int index, StudentCourse object, String value) {
					updateMark(object.getId(), Integer.parseInt(value));
				}

				private void updateMark(int id, int mark) {
					studentCourseService.updateMark(id, mark, new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							messageWindow.showErrorMessage("Ошибка сервера: обновление оценки.");

						}

						@Override
						public void onSuccess(Boolean result) {
							messageWindow.showInfoMessage("Оценка обновлена.");
						}


					});
				}
			});
			reviewGrid.addColumn(new Column<StudentCourse, String>(new TextCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return object.getReview();
				}

				@Override
				public void render(Context context, StudentCourse src, SafeHtmlBuilder sb) {
					String escaped_text = src.getReview().replace("\n", "<BR>").replace("\r", "");
					sb.appendHtmlConstant(escaped_text);
				}

			}, "Отзыв", 40);
			reviewGrid.addColumn(new Column<StudentCourse, String>(new ButtonCell()) {

				@Override
				public String getValue(StudentCourse object) {
					return "Редактировать";
				}

			}, "Действие", 10, new FieldUpdater<StudentCourse, String>() {

				@Override
				public void update(int index, final StudentCourse object, String value) {
					addDialog.changeAction(Action.ADD_REVIEW);
					addDialog.setReviewText(object.getReview());
					addDialog.setStudentName(object.getFullName());
					addDialog.setSubmitClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							final String text = addDialog.getText();
							if (text.length() > MAX_REVIW_SIZE) {
								addDialog.setTextErrorLabel(
										"Ошибка: текст отзыва не может быть больше 300 символов. Текущий размер: "
												+ text.length());
								return;
							}
							studentCourseService.addReviewById(object.getId(), text, new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									messageWindow.showErrorMessage("Ошибка сервера: добавление отзыва.");
								}

								@Override
								public void onSuccess(Boolean result) {
									addDialog.setTextErrorLabel("Отзыв добавлен успешно");
								}
							});
						}
					});
					addDialog.show();
				}

			});
			break;
		default:
			break;
		}
	}

	protected void deleteLecturerById(int id) {
		lecturerService.deleteLecturer(id, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: удаление преподавателя.");

			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					messageWindow.showInfoMessage("Преподаватель удалён.");
				} else {
					messageWindow
							.showErrorMessage("Невозможно удалить преподавателя, существуют курсы, которые он ведёт.");
				}
			}

		});

	}

	protected void addStudentCourse(int idCourse, final int idUser) {
		studentCourseService.addStudentCourse(idCourse, idUser, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка сервера: запись на курс.");
			}

			@Override
			public void onSuccess(Boolean result) {
				getIdCoursesByStudent(idUser);
				messageWindow.showInfoMessage("Запись на курс произведена успешно.");
			}
		});
	}

	protected void fillMyCoursesGrid() {
		studentCourseService.sendRequestStudentCourse(session.getPerson().getId(),
				new AsyncCallback<List<StudentCourse>>() {
					@Override
					public void onFailure(Throwable caught) {
						messageWindow.showErrorMessage("Ошибка на сервере: запрос списка кусов студента.");
					}

					@Override
					public void onSuccess(List<StudentCourse> result) {
						myCoursesGrid.initTable(result);
						myCoursesGrid.setVisible(true);
					}
				});

	}

	private void changePage(Role role, User person) {
		session.startSession(person, role);
		deleteTables();

		initTabels(role);
		RoleEnableEditCell.setEditable(false);
		signUpButton.setVisible(false);
		signInButton.setVisible(false);
		signOutButton.setVisible(true);
		accountSettingsButton.setVisible(true);
		switch (role) {
		case ADMIN:
			addAdminButton.setVisible(true);
			RoleEnableEditCell.setEditable(true);
			accountSettingsButton.setVisible(true);
			lecturersListButton.setVisible(true);
			studentsListButton.setVisible(true);
			break;
		case LECTURER:
			signUpButton.setVisible(false);
			signInButton.setVisible(false);
			signOutButton.setVisible(true);
			addReviewButton.setVisible(true);
			break;
		case STUDENT:
			getIdCoursesByStudent(person.getId());
			myCoursesButton.setVisible(true);
			studentCoursesListButton.setVisible(true);
			break;
		default:
			break;
		}

	}

	protected void deleteTables() {
		studentGrid.clearTable();
		reviewGrid.clearTable();
		lecturerGrid.clearTable();
		courseGrid.clearTable();
		myCoursesGrid.clearTable();
	}

	protected void deleteStudentCourse(int id) {
		studentCourseService.deleteStudentCourse(id, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: отказ от курса.");
			}

			@Override
			public void onSuccess(Boolean result) {
				messageWindow.showInfoMessage("Курс успешно удалён из вашего списка.");
			}
		});
	}

	private void deleteCourseById(int id) {
		courseService.deleteCourseById(id, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: удаление курса.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == true) {
					messageWindow.showInfoMessage("Курс успешно удалён.");
				} else {
					messageWindow
							.showErrorMessage("Курс не может быть удалён, есть студенты, у которых он не завершён.");
				}
			}
		});
	}

	protected void updateLecturerName(int id, String value) {
		lecturerService.updateNameById(id, value, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: изменение имени преподавателя.");
			}

			@Override
			public void onSuccess(Boolean result) {
				messageWindow.showInfoMessage("Имя преподавателя успешно изменено.");
			}
		});
	}

	protected void updateLecturerSurname(int id, String value) {
		lecturerService.updateSurnameById(id, value, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: изменение фамилии преподавателя.");
			}

			@Override
			public void onSuccess(Boolean result) {
				messageWindow.showInfoMessage("Фамилия преподавателя успешно изменена.");
			}
		});
	}

	protected void updateCourseTitle(int id, String value) {
		courseService.updateCourseTitleById(id, value, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: изменение названия курса.");
			}

			@Override
			public void onSuccess(Boolean result) {
				messageWindow.showInfoMessage("Название курса успешно изменено.");
			}
		});

	}

	protected void sendReviewToServer(int idStudentCourse, String review) {
		studentCourseService.addReviewById(idStudentCourse, review, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: добавление отзыва.");
			}

			@Override
			public void onSuccess(Boolean result) {
				addDialog.setTextErrorLabel("Отзыв успешно добавлен.");
			}
		});
	}

	protected void getIdCoursesByStudent(int idStudent) {
		studentCourseService.getCoursesByStudent(idStudent, new AsyncCallback<List<Integer>>() {
			@Override
			public void onFailure(Throwable caught) {
				messageWindow.showErrorMessage("Ошибка на сервере: получение списка курсов.");
			}

			@Override
			public void onSuccess(List<Integer> result) {
				session.updateIdCourses(result);
			}
		});
	}

	private boolean isCorrect(String... args) {
		for (String s : args) {
			if (s.equals("") || s.length() > MAX_WORD_LENGTH) {
				return false;
			}
		}
		return true;
	}

}
