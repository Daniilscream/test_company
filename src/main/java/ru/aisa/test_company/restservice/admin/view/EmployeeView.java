package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
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
    private final HorizontalLayout horizontalLayout = new HorizontalLayout();

    public EmployeeView(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        editor = new EmployeeEditor(employeeRepository,departmentRepository);
        departmentGrid.addColumn(EmployeeEntity::getId).setCaption("Id");
        departmentGrid.addColumn(EmployeeEntity::getFullName).setCaption("ФИО");
        departmentGrid.addColumn(EmployeeEntity::getPosition).setCaption("Должность");
        departmentGrid.addColumn(EmployeeEntity::getDateOfReceipt).setCaption("Дата приема");
        departmentGrid.addColumn(EmployeeEntity::getDepartmentName).setCaption("Отдел");
        this.employeeRepository = employeeRepository;

        horizontalLayout.addComponents(filter,addNewBtn);
        addComponent(horizontalLayout);
        addComponent(editor);
        filter.setPlaceholder("Поиск по имени");
        departmentGrid.setWidth("100%");
        departmentGrid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(departmentGrid);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showDepartment(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editEmployee(new EmployeeEntity()));

        departmentGrid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
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
