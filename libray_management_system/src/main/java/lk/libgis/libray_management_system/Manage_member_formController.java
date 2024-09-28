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
import lk.libgis.libray_management_system.dto.MemberDTO;
import lk.libgis.libray_management_system.service.MemberService;
import tm.MemberTM;

public class Manage_member_formController implements Initializable {

    @FXML
    private TableColumn<MemberTM, String> colMemberContact;

    @FXML
    private TableColumn<MemberTM, String> colMemberEmail;

    @FXML
    private TableColumn<MemberTM, String> colMemberId;

    @FXML
    private TableColumn<MemberTM, String> colMemberName;

    @FXML
    private TableColumn<MemberTM, String> colMemberaddress;

    @FXML
    private TableView<MemberTM> tblMember;

    @FXML
    private TextField txtMemberAddress;

    @FXML
    private TextField txtMemberContact;

    @FXML
    private TextField txtMemberEmail;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtMemberName;
    
    private final MemberService service = new MemberService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            visualizeTable();
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize the table: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
            
           MemberDTO memberDTO= collectData();
           boolean isMemberSaved = service.addMember(memberDTO);
           if(isMemberSaved){
               new Alert(Alert.AlertType.INFORMATION,"Member Saved Sucessfully").show();
           }else{
               new Alert(Alert.AlertType.ERROR,"Somthing went Wrong - May Be Dublicate ID").show();
           }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtMemberId.clear();
        txtMemberName.clear();
        txtMemberAddress.clear();
        txtMemberEmail.clear();
        txtMemberContact.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String memberId = txtMemberId.getText().trim();

        if (memberId.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid member ID").show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this member?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteSuccessful = false;
            try {
                deleteSuccessful = service.delete(memberId);
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
                return;
            }

            if (deleteSuccessful) {
                new Alert(AlertType.INFORMATION, "Member deleted successfully").show();
            } else {
                new Alert(AlertType.ERROR, "Deletion failed. Please check the member ID and try again.").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        MemberDTO memberDTO = collectData();

        if (memberDTO.getId().isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid member ID").show();
            return;
        }

        boolean isMemberUpdated = false;
        try {
            isMemberUpdated = service.update(memberDTO);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            return;
        }

        // Show appropriate alert based on the result
        if (isMemberUpdated) {
            new Alert(AlertType.INFORMATION, "Member updated successfully").show();
        } else {
            new Alert(AlertType.ERROR, "Update failed. Please check the member ID and try again.").show();
        }
    }

    @FXML
    void txtMemberContactOnAction(ActionEvent event) {
        Optional<Object> search = service.search(txtMemberContact.getText());
        if (search.isPresent()){
            setDataToFields((MemberDTO) search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found");
        }
    }

    @FXML
    void txtMemberEmailOnAction(ActionEvent event) {
        Optional<Object> search = service.search(txtMemberEmail.getText());
        if (search.isPresent()){
            setDataToFields((MemberDTO) search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found");
        }

    }

    @FXML
    void txtMemberIdOnAction(ActionEvent event) {
        Optional<Object> search = service.search(txtMemberId.getText());
        if (search.isPresent()){
            setDataToFields((MemberDTO) search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found");
        }

    }
    
    public void loadTableData() {
        try {
            List<MemberTM> list = new ArrayList<>();
            List<MemberDTO> all = service.getAll();
            for (MemberDTO memberDTO : all) {
                MemberTM memberTM = convertMemberDtoToTM(memberDTO);
                list.add(memberTM);
            }
            ObservableList<MemberTM> memberTMS = FXCollections.observableArrayList(list);
            tblMember.setItems(memberTMS);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table data: " + e.getMessage()).show();
        }
    }

    
    public MemberDTO collectData(){
        String id = txtMemberId.getText();
        String name = txtMemberName.getText();
        String address = txtMemberAddress.getText();
        String email = txtMemberEmail.getText();
        String contact = txtMemberContact.getText();
        MemberDTO memberDTO = new MemberDTO(id, name, address, email, contact);
        return memberDTO;
    }
    
    public void setDataToFields(MemberDTO member){
        txtMemberId.setText(member.getId());
        txtMemberName.setText(member.getName());
        txtMemberAddress.setText(member.getAddress());
        txtMemberEmail.setText(member.getEmail());
        txtMemberContact.setText(member.getContact());
    }
    
    private MemberTM convertMemberDtoToTM(MemberDTO memberDTO) {
        MemberTM memberTM = new MemberTM();
        memberTM.setId(memberDTO.getId());
        memberTM.setName(memberDTO.getName());
        memberTM.setAddress(memberDTO.getAddress());
        memberTM.setEmail(memberDTO.getEmail());
        memberTM.setContact(memberDTO.getContact());
        return memberTM;
    }


    private void visualizeTable() {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMemberaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMemberContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }
}
