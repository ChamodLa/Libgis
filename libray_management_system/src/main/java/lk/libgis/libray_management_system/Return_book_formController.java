package lk.libgis.libray_management_system;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.libgis.libray_management_system.dto.BookRecordReturnDTO;
import lk.libgis.libray_management_system.service.BookRecordReturnService;
import lk.libgis.libray_management_system.util.DBConnection;

public class Return_book_formController {

    @FXML
    private TableColumn<BookRecordReturnDTO, String> coMemberId;

    @FXML
    private TableColumn<BookRecordReturnDTO, String> colBookId;

    @FXML
    private TableColumn<BookRecordReturnDTO, String> colBookName;

    @FXML
    private TableColumn<BookRecordReturnDTO, String> colMemberName;

    @FXML
    private TableColumn<BookRecordReturnDTO, String> colRecordId;

    @FXML
    private TableColumn<BookRecordReturnDTO, String> colReturnDate;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblFine;

    @FXML
    private Label lblLateDateCount;

    @FXML
    private TableView<BookRecordReturnDTO> tbReturnBook;

    @FXML
    private TextField txtSearch;

    private BookRecordReturnService bookRecordReturnService;


    public void initialize() {
        try {
            DBConnection dbConnection = DBConnection.getInstance();
            bookRecordReturnService = new BookRecordReturnService(dbConnection.getConnection());

            colRecordId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            coMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
            colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
            colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

            loadBookRecords();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading database driver: " + e.getMessage());
        }
    }

    private void loadBookRecords() {
        try {
            List<BookRecordReturnDTO> records = bookRecordReturnService.getAll();
            tbReturnBook.getItems().clear();
            tbReturnBook.getItems().addAll(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnMarkAsReturnOnAction(ActionEvent event) {
        BookRecordReturnDTO selectedRecord = tbReturnBook.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            try {
                LocalDate currentDate = LocalDate.now();
                String returnDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                boolean isMarkedReturned = bookRecordReturnService.markAsReturned(selectedRecord.getId(), returnDate);
                if (isMarkedReturned) {
                    bookRecordReturnService.delete(selectedRecord.getId());

                    loadBookRecords();
                    tbReturnBook.getItems().remove(selectedRecord);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success!");
                    successAlert.setHeaderText("Book Returned Successfully");
                    successAlert.setContentText("The book has been marked as returned and removed from the database.");
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error!");
                    errorAlert.setHeaderText("Failed to Mark Book as Returned");
                    errorAlert.setContentText("An error occurred while marking the book as returned. Please try again.");
                    errorAlert.showAndWait();
                    System.out.println("Failed to mark as returned");
                }
            } catch (IllegalArgumentException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("Book Already Returned");
                errorAlert.setContentText("This book has already been marked as returned.");
                errorAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("Unexpected Error");
                errorAlert.setContentText("An unexpected error occurred. Please check the logs for details.");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Warning!");
            errorAlert.setHeaderText("No Book Selected");
            errorAlert.setContentText("Please select a book record from the table before marking it as returned.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void txtSearchOnAction(ActionEvent event) {
        String searchQuery = txtSearch.getText();
        List<BookRecordReturnDTO> filteredRecords = tbReturnBook.getItems().stream()
                .filter(record -> {
                    return record.getBookName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                           record.getMemberId().toLowerCase().contains(searchQuery.toLowerCase()) ||
                           record.getMemberName().toLowerCase().contains(searchQuery.toLowerCase());
                })
                .collect(Collectors.toList());

        tbReturnBook.getItems().clear();
        tbReturnBook.getItems().addAll(filteredRecords);
    }
}