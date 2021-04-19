package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;

public class DepartmentEditor extends VerticalLayout {

    private final DepartmentRepository departmentRepository;

    private DepartmentEntity departmentEntity;

    private TextField nameDepartment = new TextField("Название отдела");

    private Button save = new Button("Сохранить");
    private Button cancel = new Button("Закрыть");
    private Button delete = new Button("Удалить");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<DepartmentEntity> departmentBinder = new Binder<>(DepartmentEntity.class);

    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    @Autowired
    public DepartmentEditor(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;

        addComponents(nameDepartment, actions);

        departmentBinder.bindInstanceFields(this);

        setSpacing(true);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }

    private void delete() {
        departmentRepository.delete(departmentEntity);
        changeHandler.onChange();
    }

    private void save() {
        departmentRepository.save(departmentEntity);
        changeHandler.onChange();
    }

    public void editDepartment(DepartmentEntity newDepartment){
        if(newDepartment == null){
            setVisible(false);
            return;
        }
        if(newDepartment.getId() != null){
            departmentEntity = departmentRepository.findById(newDepartment.getId()).orElse(newDepartment);
        } else {
            departmentEntity = newDepartment;
            delete.setVisible(false);
        }
        departmentBinder.setBean(departmentEntity);
        setVisible(true);
        nameDepartment.focus();
    }

    public ChangeHandler getChangeHandler() {
        return changeHandler;
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
