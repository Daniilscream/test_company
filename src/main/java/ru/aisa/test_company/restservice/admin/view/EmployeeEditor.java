package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.model.EmployeeEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;
import ru.aisa.test_company.restservice.repository.EmployeeRepository;

public class EmployeeEditor extends VerticalLayout {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private EmployeeEntity employeeEntity;
    private DepartmentEntity departmentEntity;

    private TextField fullName = new TextField("ФИО");
    private TextField position = new TextField("Должность");
    private TextField departmentName = new TextField("Отдел");
    private DateField dateOfReceipt = new DateField("Дата приема");

    private Button save = new Button("Сохранить");
    private Button cancel = new Button("Закрыть");
    private Button delete = new Button("Удалить");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<EmployeeEntity> employeeBinder = new Binder<>(EmployeeEntity.class);

    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    @Autowired
    public EmployeeEditor(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;

        addComponents(fullName, position, departmentName, dateOfReceipt, actions);

        employeeBinder.forField(dateOfReceipt).withConverter(new LocalDateToDateConverter()).bind("dateOfReceipt");
        employeeBinder.bindInstanceFields(this);

        setSpacing(true);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void delete() {
        employeeRepository.delete(employeeEntity);
        changeHandler.onChange();
        getUI().getPage().reload();
    }

    private void save() {
        DepartmentEntity departmentEntity = departmentRepository.findByNameDepartment(departmentName.getValue());
        employeeEntity.setDepartmentId(departmentEntity);
        employeeRepository.save(employeeEntity);
        changeHandler.onChange();
        getUI().getPage().reload();
    }

    public void editEmployee(EmployeeEntity newEmployee){
        if(newEmployee == null){
            setVisible(false);
            return;
        }
        if(newEmployee.getId() != null){
            employeeEntity = employeeRepository.findById(newEmployee.getId()).orElse(newEmployee);
            dateOfReceipt.setVisible(false);
        } else {
            employeeEntity = newEmployee;
            delete.setVisible(false);
            dateOfReceipt.setVisible(true);
        }
        employeeBinder.setBean(employeeEntity);
        setVisible(true);
        fullName.focus();
    }

    public ChangeHandler getChangeHandler() {
        return changeHandler;
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
