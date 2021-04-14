package ru.aisa.test_company.restservice.admin.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import ru.aisa.test_company.restservice.model.DepartmentEntity;
import ru.aisa.test_company.restservice.repository.DepartmentRepository;

import java.util.List;

@SpringView(name="department")
public class DepartmentView extends VerticalLayout implements View {

    private DepartmentEditor editor;
    private DepartmentRepository departmentRepository;

    private Grid<DepartmentEntity> departmentGrid = new Grid<>();

    private final TextField filter = new TextField();
    private final Button addNewBtn = new Button("Добавить отдел");
    private final HorizontalLayout horizontalLayout = new HorizontalLayout();

    public DepartmentView(DepartmentRepository departmentRepository) {
        editor = new DepartmentEditor(departmentRepository);
        departmentGrid.addColumn(DepartmentEntity::getId).setCaption("Id");
        departmentGrid.addColumn(DepartmentEntity::getNameDepartment).setCaption("Название отдела");
        departmentGrid.addColumn(DepartmentEntity::getEmployeeSize).setCaption("Количество сотрудников");
        this.departmentRepository = departmentRepository;

        horizontalLayout.addComponents(filter,addNewBtn);
        addComponent(horizontalLayout);
        addComponent(editor);
        filter.setPlaceholder("Поиск по названию");
        departmentGrid.setWidth("100%");
        departmentGrid.setHeightMode(HeightMode.UNDEFINED);
        addComponent(departmentGrid);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showDepartment(e.getValue()));

        addNewBtn.addClickListener(e -> editor.editDepartment(new DepartmentEntity()));

        departmentGrid.asSingleSelect().addValueChangeListener(e -> {
            editor.editDepartment(e.getValue());
        });

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showDepartment(filter.getValue());
        });

        showDepartment("");
    }

    private void showDepartment(String name) {
        if(departmentRepository.findAll() == null){
            return;
        }
        if(name.isEmpty()){
            departmentGrid.setItems((List<DepartmentEntity>) departmentRepository.findAll());
        } else{
            departmentGrid.setItems(departmentRepository.findByName(name));
        }
    }
}
