package lk.libgis.libray_management_system;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.libgis.libray_management_system.util.DBConnection;

/**
 * FXML Controller class
 *
 * @author Chamod Abeywickrama
 */
public class Login_formController implements Initializable {

    @FXML
    private AnchorPane MainAnchorpane;

    @FXML
    private AnchorPane SubAnchorpane;

    @FXML
    private AnchorPane bodybg;

    @FXML
    private Button btnlogin;

    @FXML
    private Hyperlink btnsignup;

    @FXML
    private PasswordField pword;

    @FXML
    private TextField user;

    @FXML
    void btnloginOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String username = user.getText();
        String password = pword.getText();

        if (validateLogin(username, password)) {
            Stage window = (Stage) pword.getScene().getWindow();
            window.close();
            Stage stage = new Stage();
            try {
                Parent load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/dashboard_form.fxml"));
                Scene scene = new Scene(load);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please check your credentials and try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnsignupOnAction(ActionEvent event) {
        try {
            Node load = FXMLLoader.load(getClass().getResource("/lk/libgis/libray_management_system/signup_form.fxml"));
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
            alert.setContentText("An error occurred while trying to load the signup form. Please contact the system administrator.");
            alert.showAndWait();
        }
    }

    private boolean validateLogin(String username, String password) throws SQLException, ClassNotFoundException {
        try (PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM admin WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
