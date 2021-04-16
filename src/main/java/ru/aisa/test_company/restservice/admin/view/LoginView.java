package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ru.aisa.test_company.restservice.configuration.ShiroConfig;

@SpringView(name="login")
public class LoginView extends VerticalLayout implements View {

    private FormLayout form = new FormLayout();
    private TextField userField;
    private PasswordField passwordField;
    private Button loginButton;

    public LoginView() {
        addComponent(form);
        userField = new TextField("Логин:");
        passwordField = new PasswordField("Пароль:");
        loginButton = new Button("Вход");
        form.addComponent(userField);
        form.addComponent(passwordField);
        form.addComponent(loginButton);
        form.setWidthUndefined();
        setComponentAlignment(form,Alignment.MIDDLE_CENTER);
        setSizeFull();

        loginButton.addClickListener(e -> {
            ShiroConfig.doAuthenticateAndProcess(userField.getValue(), passwordField.getValue());
        });
    }

}
