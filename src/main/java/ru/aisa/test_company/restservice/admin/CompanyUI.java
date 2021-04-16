package ru.aisa.test_company.restservice.admin;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import ru.aisa.test_company.restservice.admin.view.DepartmentView;
import ru.aisa.test_company.restservice.admin.view.EmployeeView;
import ru.aisa.test_company.restservice.admin.view.LoginView;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;

@SpringUI(path = "/admin")
public class CompanyUI extends UI {

    private Navigator navigator;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Subject subject = SecurityUtils.getSubject();
        navigator = new Navigator(this,this);


        if(!subject.isAuthenticated()) {
            navigator.addView("login", new LoginView());
            getNavigator().navigateTo("login");
        } else {
            final HorizontalLayout rootLayout = new HorizontalLayout();
            rootLayout.setSpacing(false);
            rootLayout.setSizeFull();

            rootLayout.addComponent(createNavigation());

            final Panel contentPanel = new Panel();
            contentPanel.setSizeFull();
            rootLayout.addComponent(contentPanel);
            rootLayout.setExpandRatio(contentPanel, 1.0f);

            setNavigator(new Navigator(this, contentPanel));

        getNavigator().addView("department", new DepartmentView(departmentRepository));
        getNavigator().addView("employee", new EmployeeView(employeeRepository, departmentRepository));

            setContent(rootLayout);
        }
    }

    private Component createNavigation(){

        final VerticalLayout navigationLayout = new VerticalLayout();

        final Label exercisesLabel = new Label("Навигация");
        exercisesLabel.setStyleName(ValoTheme.LABEL_H3);
        navigationLayout.addComponent(exercisesLabel);
        navigationLayout.addComponent(createNavigationButton("Отделы", "department"));
        navigationLayout.addComponent(createNavigationButton("Сотрудники", "employee"));

        final Panel navigationPanel = new Panel(navigationLayout);
        navigationPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);
        navigationPanel.setHeight("100%");
        navigationPanel.setWidth("300px");
        return navigationPanel;
    }

    private Button createNavigationButton(final String buttonCaption, final String viewName){
        final Button button = new Button(buttonCaption);
        button.setStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener(e -> getNavigator().navigateTo(viewName));
        return button;
    }

    @WebServlet(urlPatterns = "/admin", name = "BasicsUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CompanyUI.class, productionMode = false)
    public static class BasicsUIServlet extends VaadinServlet {
    }
}
