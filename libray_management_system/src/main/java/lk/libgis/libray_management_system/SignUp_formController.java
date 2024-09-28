/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lk.libgis.libray_management_system;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Chamod Abeywickrama
 */
public class SignUp_formController implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private AnchorPane SubAnchorpane;

    @FXML
    private AnchorPane bodybg;

    @FXML
    private PasswordField cpword;

    @FXML
    private PasswordField pword;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtusername;

    @FXML
    void btnloginOnAction(ActionEvent event) {
        
        try {
            Node load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/login_form.fxml"));

            
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Scene scene = new Scene((Parent) load);
            stage.setScene(scene);
            stage.show();
        
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load the signup form");
            alert.setContentText("An error occurred while trying to load the login form. Please contact the system administrator.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnsignupOnAction(ActionEvent event) {
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
