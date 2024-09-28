/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.libgis.libray_management_system;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.libgis.libray_management_system.dto.AuthorDTO;
import lk.libgis.libray_management_system.dto.DirectorDTO;
import lk.libgis.libray_management_system.service.AuthorService;
import lk.libgis.libray_management_system.service.DirectorService;
import tm.AuthorTM;
import tm.DirectorTM;

public class Manage_authors_and_directors_formController implements Initializable {

    @FXML
    private TableColumn<AuthorTM, Integer> colAuthorId;

    @FXML
    private TableColumn<AuthorTM, String> colAuthorName;

    @FXML
    private TableColumn<DirectorTM, Integer> colDirectorId;

    @FXML
    private TableColumn<DirectorTM, String> colDirectorName;

    @FXML
    private TableView<AuthorTM> tblAuthor;

    @FXML
    private TableView<DirectorTM> tblDirector;

    @FXML
    private TextField txtAuthorId;

    @FXML
    private TextField txtAuthorName;

    @FXML
    private TextField txtDirectorId;

    @FXML
    private TextField txtDirectorName;


    private final AuthorService authorService = new AuthorService();
    private final DirectorService directorService = new DirectorService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            visualizeTables();
            loadAuthorTableData();
            loadDirectorTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize the tables: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveAuthorOnAction(ActionEvent event) {
        AuthorDTO authorDTO = collectAuthorData();
        boolean isSaved = authorService.addAuthor(authorDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Author saved successfully").show();
            loadAuthorTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save author.").show();
        }
    }

    @FXML
    void btnSaveDirectorOnAction(ActionEvent event) {
        DirectorDTO directorDTO = collectDirectorData();
        boolean isSaved = directorService.addDirector(directorDTO);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Director saved successfully").show();
            loadDirectorTableData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save director.").show();
        }
    }

    @FXML
    void btnClearAuthorOnAction(ActionEvent event) {
        txtAuthorId.clear();
        txtAuthorName.clear();
    }

    @FXML
    void btnClearDirectorOnAction(ActionEvent event) {
        txtDirectorId.clear();
        txtDirectorName.clear();
    }

    @FXML
    void btnDeleteAuthorOnAction(ActionEvent event) {
        String authorId = txtAuthorId.getText().trim();
        if (authorId.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid author ID").show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this author?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteSuccessful = authorService.delete(authorId);
            if (deleteSuccessful) {
                new Alert(AlertType.INFORMATION, "Author deleted successfully").show();
                loadAuthorTableData();
            } else {
                new Alert(AlertType.ERROR, "Deletion failed. Please check the author ID and try again.").show();
            }
        }
    }

    @FXML
    void btnDeleteDirectorOnAction(ActionEvent event) {
        String directorId = txtDirectorId.getText().trim();
        if (directorId.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid director ID").show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this director?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteSuccessful = directorService.delete(directorId);
            if (deleteSuccessful) {
                new Alert(AlertType.INFORMATION, "Director deleted successfully").show();
                loadDirectorTableData();
            } else {
                new Alert(AlertType.ERROR, "Deletion failed. Please check the director ID and try again.").show();
            }
        }
    }

    @FXML
    void btnUpdateAuthorOnAction(ActionEvent event) {
        AuthorDTO authorDTO = collectAuthorData();
        boolean isUpdated = authorService.update(authorDTO);
        if (isUpdated) {
            new Alert(AlertType.INFORMATION, "Author updated successfully").show();
            loadAuthorTableData();
        } else {
            new Alert(AlertType.ERROR, "Failed to update author.").show();
        }
    }

    @FXML
    void btnUpdateDirectorOnAction(ActionEvent event) {
        DirectorDTO directorDTO = collectDirectorData();
        boolean isUpdated = directorService.update(directorDTO);
        if (isUpdated) {
            new Alert(AlertType.INFORMATION, "Director updated successfully").show();
            loadDirectorTableData();
        } else {
            new Alert(AlertType.ERROR, "Failed to update director.").show();
        }
    }

    @FXML
    void txtAuthorIdOnAction(ActionEvent event) {
        Optional<Object> search = authorService.search(txtAuthorId.getText());
        if (search.isPresent()){
            setAuthorDataToFields((AuthorDTO) search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found");
        }
    }

    @FXML
    void txtDirectorIdOnAction(ActionEvent event) {
        Optional<DirectorDTO> director = directorService.search(txtDirectorId.getText().trim());
        if (director.isPresent()) {
            setDirectorDataToFields((director).get());
        } else {
            new Alert(Alert.AlertType.ERROR, "Director not found").show();
        }
    }


    private void loadAuthorTableData() {
        try {
            List<AuthorTM> list = new ArrayList<>();
            List<AuthorDTO> allAuthors = authorService.getAll();
            for (AuthorDTO authorDTO : allAuthors) {
                AuthorTM authorTM = convertAuthorDtoToTM(authorDTO);
                list.add(authorTM);
            }
            ObservableList<AuthorTM> authorTMS = FXCollections.observableArrayList(list);
            tblAuthor.setItems(authorTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load author table data: " + e.getMessage()).show();
        }
    }

    private void loadDirectorTableData() {
        try {
            List<DirectorTM> list = new ArrayList<>();
            List<DirectorDTO> allDirectors = directorService.getAll();
            for (DirectorDTO directorDTO : allDirectors) {
                DirectorTM directorTM = convertDirectorDtoToTM(directorDTO);
                list.add(directorTM);
            }
            ObservableList<DirectorTM> directorTMS = FXCollections.observableArrayList(list);
            tblDirector.setItems(directorTMS);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load director table data: " + e.getMessage()).show();
        }
    }

    private AuthorDTO collectAuthorData() {
        int id = Integer.parseInt(txtAuthorId.getText().trim());
        String name = txtAuthorName.getText().trim();
        return new AuthorDTO(id, name);
    }

    private DirectorDTO collectDirectorData() {
        int id = Integer.parseInt(txtDirectorId.getText().trim());
        String name = txtDirectorName.getText().trim();
        return new DirectorDTO(id, name);
    }

    private void setAuthorDataToFields(AuthorDTO author) {
        txtAuthorId.setText(String.valueOf(author.getId()));
        txtAuthorName.setText(author.getName());
    }

    private void setDirectorDataToFields(DirectorDTO director) {
        txtDirectorId.setText(String.valueOf(director.getId()));
        txtDirectorName.setText(director.getName());
    }

    private AuthorTM convertAuthorDtoToTM(AuthorDTO authorDTO) {
        AuthorTM authorTM = new AuthorTM();
        authorTM.setId(authorDTO.getId());
        authorTM.setName(authorDTO.getName());
        return authorTM;
    }

    private DirectorTM convertDirectorDtoToTM(DirectorDTO directorDTO) {
        DirectorTM directorTM = new DirectorTM();
        directorTM.setId(directorDTO.getId());
        directorTM.setName(directorDTO.getName());
        return directorTM;
    }


    private void visualizeTables() {
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDirectorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDirectorName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
