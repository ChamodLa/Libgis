package lk.libgis.libray_management_system;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Dashboard_formController {

    @FXML
    private AnchorPane mainwin;
    

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Label txtusername;

    @FXML
    void btnBarrowOnAction(ActionEvent event) {
    mainwin.getChildren().clear();
    try {
        Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/barrow_book_form.fxml"));
        mainwin.getChildren().add(load);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the barrow book form. Please check the file path and try again.");
        alert.show();
        e.printStackTrace();
    }

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
            try {
            Node load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/login_form.fxml"));

            
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Scene scene = new Scene((Parent) load);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load the signup form");
            alert.setContentText("An error occurred while trying to load the signup form. Please contact the system administrator.");
            alert.showAndWait();
        }
    }
    

    @FXML
    void btnManageAuthorsOnAction(ActionEvent event) {
    mainwin.getChildren().clear();
    try {
        Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/manage_authors_and_directors_form.fxml"));
        mainwin.getChildren().add(load);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the manage book form. Please check the file path and try again.");
        alert.show();
        e.printStackTrace();
    }

    }

    @FXML
    void btnManageBooksOnAction(ActionEvent event) {
    mainwin.getChildren().clear();
    try {
        Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/manage_book_form.fxml"));
        mainwin.getChildren().add(load);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the manage book form. Please check the file path and try again.");
        alert.show();
        e.printStackTrace();
    }
    }
    
    @FXML
    void btnManageDvdsOnAction(ActionEvent event) {
        mainwin.getChildren().clear();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/manage_dvd_form.fxml"));
            mainwin.getChildren().add(load);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the manage DVD form. Please check the file path and try again.");
            alert.show();
            e.printStackTrace();
        }
    }


    @FXML
    void btnManageMemberOnAction(ActionEvent event) {
    mainwin.getChildren().clear();
    try {
        Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/manage_member_form.fxml"));
        mainwin.getChildren().add(load);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the manage book form. Please check the file path and try again.");
        alert.show();
        e.printStackTrace();
    }

    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
    mainwin.getChildren().clear();
    try {
        Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/return_book_form.fxml"));
        mainwin.getChildren().add(load);
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load the manage book form. Please check the file path and try again.");
        alert.show();
        e.printStackTrace();
    }
        
    }

}
