package lk.libgis.libray_management_system;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.libgis.libray_management_system.dto.CategoryDTO;
import lk.libgis.libray_management_system.service.CategoryService;

public class Manage_category_formController implements Initializable {

    @FXML
    private TableColumn<CategoryDTO, Integer> colCategoryId;

    @FXML
    private TableColumn<CategoryDTO, String> colCategoryName;

    @FXML
    private TableView<CategoryDTO> tblCategoryManage;

    @FXML
    private TextField txtCategoryId;

    @FXML
    private TextField txtCategoryName;

    private final CategoryService categoryService = new CategoryService();
    private ObservableList<CategoryDTO> categoryList = FXCollections.observableArrayList();

    @FXML
    void btnClearCategoryOnAction(ActionEvent event) {
        txtCategoryId.clear();
        txtCategoryName.clear();
    }

    @FXML
    void btnDeleteCategoryOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtCategoryId.getText());
            if (categoryService.delete(id)) {
                loadCategories();
                btnClearCategoryOnAction(event);
            } else {
                
            }
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    void btnSaveCategoryOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtCategoryId.getText());
            String name = txtCategoryName.getText();
            CategoryDTO categoryDTO = new CategoryDTO(id, name);
            if (categoryService.addCategory(categoryDTO)) {
                loadCategories();
                btnClearCategoryOnAction(event);
            } else {

            }
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    void btnUpdateCategoryOnAction(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtCategoryId.getText());
            String name = txtCategoryName.getText();
            CategoryDTO categoryDTO = new CategoryDTO(id, name);
            if (categoryService.update(categoryDTO)) {
                loadCategories();
                btnClearCategoryOnAction(event);
            } else {

            }
        } catch (NumberFormatException e) {

        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadCategories();
    }
    
    private void loadCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getAll();
            categoryList.setAll(categories);
            tblCategoryManage.setItems(categoryList);
        } catch (RuntimeException e) {

        }
    }
}
