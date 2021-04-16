package ru.aisa.test_company.restservice.configuration;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    public static void doAuthenticateAndProcess(String userName, String password) {

        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        SecurityManager securityManager = new DefaultSecurityManager(iniRealm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
                if (currentUser.hasRole("ADMIN")) {
                    UI.getCurrent().getPage().replaceState("/admin#!department");
                    UI.getCurrent().getPage().reload();
                }
            } catch (UnknownAccountException uae) {
                Notification.show("User unknown");
            } catch (IncorrectCredentialsException ice) {
                Notification.show("Incorrect credentials");
            } catch (LockedAccountException lae) {
                Notification.show("Account locked");
            } catch (AuthenticationException ae) {
                Notification.show("Invalid login");
            }
        } else {
            if (currentUser.hasRole("ADMIN")) {
                UI.getCurrent().getPage().replaceState("/admin#!department");
                UI.getCurrent().getPage().reload();
            }
        }

    }

    public Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }
}