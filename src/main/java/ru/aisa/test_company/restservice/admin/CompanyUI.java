package ru.aisa.test_company.restservice.admin;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    private TabSheet tabs;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated()) {
            setContent(new LoginView());
        } else {
            final HorizontalLayout rootLayout = new HorizontalLayout();
            rootLayout.setSpacing(false);
            rootLayout.setSizeFull();
            tabs = new TabSheet();

            rootLayout.addComponent(tabs);
            VerticalLayout tab1 = new VerticalLayout();
            tab1.addComponent(new DepartmentView(departmentRepository));
            tabs.addTab(tab1, "Отделы");
            tabs.addStyleName("centered-tabs");

            VerticalLayout tab2 = new VerticalLayout();
            tab2.addComponent(new EmployeeView(employeeRepository, departmentRepository));
            tabs.addTab(tab2, "Сотрудники");
            setContent(rootLayout);
        }
    }

    @WebServlet(urlPatterns = "/admin", name = "BasicsUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CompanyUI.class, productionMode = false)
    public static class BasicsUIServlet extends VaadinServlet {
    }
}
