package lk.libgis.libray_management_system;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.libgis.libray_management_system.dto.DVDDTO;
import lk.libgis.libray_management_system.entity.Director;
import lk.libgis.libray_management_system.entity.DvdCategory;
import lk.libgis.libray_management_system.repo.DirectorRepo;
import lk.libgis.libray_management_system.repo.DvdCategoryRepo;
import lk.libgis.libray_management_system.service.DVDService;
import tm.DVDTM;

public class Manage_dvd_formController implements Initializable {

    @FXML
    private ComboBox<String> cmbDirector;

    @FXML
    private ComboBox<String> cmbDvdCategory;
    
    @FXML
    private TableColumn<DVDTM, String> colDirectorName;

    @FXML
    private TableColumn<DVDTM, Integer> colDuration;

    @FXML
    private TableColumn<DVDTM, Integer> colDvdId;

    @FXML
    private TableColumn<DVDTM, String> colDvdName;

    @FXML
    private TableColumn<DVDTM, Double> colDvdPrice;

    @FXML
    private TableView<DVDTM> tblDvd;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtDvdId;

    @FXML
    private TextField txtDvdName;

    @FXML
    private TextField txtDvdPrice;

    private final DVDService service = new DVDService();
    private final Map<String, Integer> directorMap = new HashMap<>();
    private final Map<String, Integer> dvdCategoryMap = new HashMap<>();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtDvdId.clear();
        txtDvdName.clear();
        txtDvdPrice.clear();
        txtDuration.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String dvdIdText = txtDvdId.getText().trim();
        if (dvdIdText.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid DVD ID").show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this DVD?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean deleteSuccessful = service.delete(Integer.parseInt(dvdIdText));
                if (deleteSuccessful) {
                    new Alert(AlertType.INFORMATION, "DVD deleted successfully").show();
                    loadTableData();
                } else {
                    new Alert(AlertType.ERROR, "Deletion failed. Please check the DVD ID and try again.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            DVDDTO dvdDTO = collectData();
            boolean isDVDSaved = service.addDVD(dvdDTO);
            if (isDVDSaved) {
                new Alert(Alert.AlertType.INFORMATION, "DVD Saved Successfully").show();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong - Maybe Duplicate ID").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void dvdIdOnAction(ActionEvent event) {
        String dvdIdText = txtDvdId.getText().trim();
        if (dvdIdText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid DVD ID").show();
            return;
        }

        try {
            int dvdId = Integer.parseInt(dvdIdText);
            Optional<DVDDTO> search = service.search(dvdId);
            if (search.isPresent()) {
                setDataToFields(search.get());
            } else {
                new Alert(Alert.AlertType.ERROR, "DVD Not Found").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid DVD ID format").show();
        }
    }

    public DVDDTO collectData() {
        try {
            int id = Integer.parseInt(txtDvdId.getText().trim());
            String name = txtDvdName.getText();
            double price = Double.parseDouble(txtDvdPrice.getText());
            int duration = Integer.parseInt(txtDuration.getText());

            String directorName = cmbDirector.getSelectionModel().getSelectedItem();
            Integer directorId = directorMap.get(directorName);
            if (directorId == null) {
                throw new Exception("Director not found");
            }

            String categoryName = cmbDvdCategory.getSelectionModel().getSelectedItem();
            Integer dvdCategoryId = dvdCategoryMap.get(categoryName);
            if (dvdCategoryId == null) {
                throw new Exception("DVD category not found");
            }

            return new DVDDTO(id, name, price, directorId, dvdCategoryId, duration);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid data format: " + e.getMessage()).show();
            return null;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error collecting data: " + e.getMessage()).show();
            return null;
        }
    }

    public void setDataToFields(DVDDTO dvd) {
        txtDvdId.setText(String.valueOf(dvd.getId()));
        txtDvdName.setText(dvd.getName());
        txtDvdPrice.setText(String.valueOf(dvd.getPrice()));
        txtDuration.setText(String.valueOf(dvd.getDuration()));

        cmbDirector.getSelectionModel().select(dvd.getDirectorId());
        cmbDvdCategory.getSelectionModel().select(dvd.getDvdCategory());
    }

    private DVDTM convertDVDDtoToTM(DVDDTO dvdDTO) throws ClassNotFoundException, SQLException {
        DVDTM dvdTM = new DVDTM();
        dvdTM.setId(dvdDTO.getId());
        dvdTM.setName(dvdDTO.getName());
        dvdTM.setPrice(dvdDTO.getPrice());
        dvdTM.setDuration(dvdDTO.getDuration());
        return dvdTM;
    }

    public void loadTableData() {
        try {
            List<DVDTM> list = new ArrayList<>();
            List<DVDDTO> all = service.getAll();
            for (DVDDTO dvdDTO : all) {
                DVDTM dvdTM = convertDVDDtoToTM(dvdDTO);
                list.add(dvdTM);
            }
            ObservableList<DVDTM> dvdTMs = FXCollections.observableArrayList(list);
            tblDvd.setItems(dvdTMs);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table data: " + e.getMessage()).show();
        }
    }

    private void visualizeTable() {
        colDvdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDvdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDvdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colDirectorName.setCellValueFactory(new PropertyValueFactory<>("directorName"));
    }

    private void loadComboBoxes() {
        try {
            DirectorRepo directorRepo = new DirectorRepo();
            List<Director> directors = directorRepo.getAll();
            ObservableList<String> directorNames = FXCollections.observableArrayList();
            for (Director director : directors) {
                directorNames.add(director.getName());
                directorMap.put(director.getName(), director.getId());
            }
            cmbDirector.setItems(directorNames);

            DvdCategoryRepo categoryRepo = new DvdCategoryRepo();
            List<DvdCategory> categories = categoryRepo.getAll();
            ObservableList<String> categoryNames = FXCollections.observableArrayList();
            for (DvdCategory category : categories) {
                categoryNames.add(category.getName());
                dvdCategoryMap.put(category.getName(), category.getId());
            }
            cmbDvdCategory.setItems(categoryNames);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load directors and categories: " + e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            visualizeTable();
            loadTableData();
            loadComboBoxes();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize the table: " + e.getMessage()).show();
        }
    }
}
