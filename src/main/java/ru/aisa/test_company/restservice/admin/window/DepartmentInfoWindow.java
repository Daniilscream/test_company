package ru.aisa.test_company.restservice.admin.window;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ru.aisa.test_company.restservice.admin.view.DepartmentEditor;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentInfoWindow extends Window {
    private Grid<DepartmentEntity> departmentGrid = new Grid<>("Отдел");
    private Grid<EmployeeEntity> employeeGrid = new Grid<>("Сотрудники отдела");

    private Button editButton = new Button("Редактировать отдел");

    private DepartmentEditor editor;

    public DepartmentInfoWindow(DepartmentEntity departmentEntity, DepartmentRepository departmentRepository) {
        setWidth("70%");
        center();

        VerticalLayout components = new VerticalLayout();

        editor = new DepartmentEditor(departmentRepository);

        departmentGrid.setWidth("100%");
        departmentGrid.setHeightMode(HeightMode.UNDEFINED);
        employeeGrid.setWidth("100%");
        employeeGrid.setHeightMode(HeightMode.UNDEFINED);

        List<DepartmentEntity> departmentInfos= new ArrayList<>();
        departmentInfos.add(departmentEntity);

        editButton.addClickListener(e -> editor.editDepartment(departmentEntity));

        departmentGrid.setItems(departmentInfos);
        departmentGrid.addColumn(DepartmentEntity::getId).setCaption("id");
        departmentGrid.addColumn(DepartmentEntity::getNameDepartment).setCaption("Название");
        departmentGrid.addColumn(DepartmentEntity::getWorkingHours).setCaption("Кол-во отработанных часов");

        employeeGrid.setItems(departmentEntity.getEmployees());
        employeeGrid.addColumn(EmployeeEntity::getId).setCaption("ID");
        employeeGrid.addColumn(EmployeeEntity::getFullName).setCaption("ФИО");
        employeeGrid.addColumn(EmployeeEntity::getPosition).setCaption("Должность");
        employeeGrid.addColumn(EmployeeEntity::getDateOfReceipt).setCaption("Дата приема");
        employeeGrid.addColumn(EmployeeEntity::getDaysForWork).setCaption("Количество рабочих дней");
        components.addComponents(editButton, editor, departmentGrid, employeeGrid);
        setContent(components);
    }
}