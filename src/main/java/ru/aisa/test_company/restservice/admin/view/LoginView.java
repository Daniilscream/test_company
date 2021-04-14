package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;

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
            login(userField.getValue(), passwordField.getValue());
        });
    }


    private void login(String userName, String pwd) {
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            System.out.println(userField.getValue());
            UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                if (currentUser.hasRole("admin")) {
                    getUI().getPage().replaceState("/#!department");
                    getUI().getPage().reload();

                }
            } catch (UnknownAccountException uae) {
                Notification.show("User unknown");
                Logger.getLogger(getClass().getName()).warn("User unknown", uae);
            } catch (IncorrectCredentialsException ice) {
                Notification.show("Incorrect credentials");
                Logger.getLogger(getClass().getName()).warn("Incorrect credentials", ice);
            } catch (LockedAccountException lae) {
                Notification.show("Account locked");
                Logger.getLogger(getClass().getName()).warn("Account locked", lae);
            } catch (AuthenticationException ae) {
                Notification.show("Invalid login");
                Logger.getLogger(getClass().getName()).warn("Invalid login", ae);
            }
        } else {
            currentUser.logout();
            loginButton.setCaption("Вход");
        }
    }
}
