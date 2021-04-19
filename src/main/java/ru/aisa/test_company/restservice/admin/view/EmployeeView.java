package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ru.aisa.test_company.restservice.admin.window.EmployeeInfoWindow;
import ru.aisa.test_company.restservice.configuration.ShiroConfig;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;

import java.util.List;

public class EmployeeView extends VerticalLayout implements View {

    private EmployeeEditor editor;
    private EmployeeRepository employeeRepository;

    private Grid<EmployeeEntity> departmentGrid = new Grid<>();

    private final TextField filter = new TextField();
    private final Button addNewBtn = new Button("Добавить сотрудника");
    private final Button logout = new Button("Выйти");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout();

    public EmployeeView(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        editor = new EmployeeEditor(employeeRepository,departmentRepository);
        departmentGrid.addColumn(EmployeeEntity::getId).setCaption("Id");
        departmentGrid.addColumn(EmployeeEntity::getFullName).setCaption("ФИО");
        departmentGrid.addColumn(EmployeeEntity::getPosition).setCaption("Должность");
        departmentGrid.addColumn(EmployeeEntity::getDateOfReceipt).setCaption("Дата приема");
        departmentGrid.addColumn(EmployeeEntity::getDepartmentName).setCaption("Отдел");
        this.employeeRepository = employeeRepository;

        horizontalLayout.setWidth("100%");
        horizontalLayout.addComponents(filter,addNewBtn);
        horizontalLayout.addComponent(logout);
        horizontalLayout.setExpandRatio(logout,1.0f);
        horizontalLayout.setComponentAlignment(logout, Alignment.TOP_RIGHT);

        addComponent(horizontalLayout);
        addComponent(editor);
        filter.setPlaceholder("Поиск по имени");
        departmentGrid.setWidth("100%");
        departmentGrid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(departmentGrid);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showDepartment(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editEmployee(new EmployeeEntity()));

        logout.addClickListener(e -> {
            ShiroConfig.logout();
            getUI().close();
            getUI().getPage().reload();
        });

        departmentGrid.addItemClickListener(listener ->{
            if(listener.getMouseEventDetails().isDoubleClick()){
                getUI().addWindow(new EmployeeInfoWindow(listener.getItem(), employeeRepository, departmentRepository));
            }
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showDepartment(filter.getValue());
        });

        showDepartment("");
    }

    private void showDepartment(String name) {
        if(employeeRepository.findAll() == null){
            return;
        }
        if(name.isEmpty()){
            departmentGrid.setItems((List<EmployeeEntity>) employeeRepository.findAll());
        } else{
            departmentGrid.setItems(employeeRepository.findByName(name));
        }
    }
}
