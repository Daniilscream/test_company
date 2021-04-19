package ru.aisa.test_company.restservice.admin.window;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ru.aisa.test_company.restservice.admin.view.EmployeeEditor;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;
import ru.aisa.test_company.restservice.repository.WorkingDayRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class EmployeeInfoWindow extends Window {

    private Grid<EmployeeEntity> employeeGrid = new Grid<>("Сотрудник");

    private Button editButton = new Button("Редактировать сотрудника");

    private EmployeeEditor editor;

    @Resource
    private WorkingDayRepository workingDayRepository;

    public EmployeeInfoWindow(EmployeeEntity employeeEntity, EmployeeRepository er, DepartmentRepository dr) {
        setWidth("70%");
        center();

        VerticalLayout components = new VerticalLayout();

        editor = new EmployeeEditor(er, dr);

        editButton.addClickListener(e -> editor.editEmployee(employeeEntity));

        employeeGrid.setWidth("100%");
        employeeGrid.setHeightMode(HeightMode.UNDEFINED);

        List<EmployeeEntity> departmentInfos= new ArrayList<>();
        departmentInfos.add(employeeEntity);

        employeeGrid.setItems(departmentInfos);
        employeeGrid.addColumn(EmployeeEntity::getId).setCaption("ID");
        employeeGrid.addColumn(EmployeeEntity::getFullName).setCaption("ФИО");
        employeeGrid.addColumn(EmployeeEntity::getPosition).setCaption("Должность");
        employeeGrid.addColumn(EmployeeEntity::getDateOfReceipt).setCaption("Дата приема");
        employeeGrid.addColumn(EmployeeEntity::getDepartmentName).setCaption("Отдел");
        employeeGrid.addColumn(EmployeeEntity::getDaysForWork).setCaption("Количество рабочих дней");
        employeeGrid.addColumn(EmployeeEntity::getLastVacation).setCaption("Дата последнего отпуска");
        components.addComponents(editButton, editor, employeeGrid);
        setContent(components);
    }
}
