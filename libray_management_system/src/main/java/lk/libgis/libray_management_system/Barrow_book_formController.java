package lk.libgis.libray_management_system;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.libgis.libray_management_system.dto.BookDTO;
import lk.libgis.libray_management_system.dto.BookRecordDTO;
import lk.libgis.libray_management_system.dto.MemberDTO;
import lk.libgis.libray_management_system.service.BookRecordService;
import lk.libgis.libray_management_system.service.BookService;
import lk.libgis.libray_management_system.service.MemberService;
import tm.BookRecordTM;

public class Barrow_book_formController {

    @FXML
    private TableColumn<BookRecordTM, String> colBookId;

    @FXML
    private TableColumn<BookRecordTM, String> colBookName;

    @FXML
    private TableColumn<BookRecordTM, String> colMemberName;

    @FXML
    private TableColumn<BookRecordTM, LocalDate> colBarrowDate;

    @FXML
    private TableColumn<BookRecordTM, LocalDate> colReturnDate;

    @FXML
    private DatePicker dpBarrowDate;

    @FXML
    private DatePicker dpReturnDate;

    @FXML
    private TableView<BookRecordTM> tbBarrowBook;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBookIsbn;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtMainCategory;

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

    private final BookService bookService = new BookService();
    private final MemberService memberService = new MemberService();
    private final BookRecordService bookRecordService = new BookRecordService();

    private ObservableList<BookRecordTM> cartList = FXCollections.observableArrayList();

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        String bookId = txtBookId.getText().trim();
        String memberId = txtMemberId.getText().trim();

        if (bookId.isEmpty() || memberId.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter both book ID and member ID").show();
            return;
        }

        try {
            Optional<BookDTO> bookSearch = bookService.search(Integer.parseInt(bookId));
            Optional<Object> memberSearch = memberService.search(memberId);

            if (bookSearch.isPresent() && memberSearch.isPresent()) {
                BookDTO bookDTO = bookSearch.get();
                MemberDTO memberDTO = (MemberDTO) memberSearch.get();

                BookRecordTM recordTM = convertBookRecordDtoToTM(new BookRecordDTO(
                    Integer.parseInt(bookId), 
                    memberId, 
                    Date.valueOf(dpBarrowDate.getValue()), 
                    false, 
                    Date.valueOf(dpReturnDate.getValue()))
                );

                cartList.add(recordTM);
                loadCartData();

            } else {
                new Alert(AlertType.ERROR, "Book or Member not found").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtMemberIdOnAction(ActionEvent event) {
        Optional<Object> search = memberService.search(txtMemberId.getText());
        if (search.isPresent()) {
            setMemberDataToFields((MemberDTO) search.get());
        } else {
            new Alert(AlertType.ERROR, "Member Not Found").show();
        }
    }

    @FXML
    void txtBookIdOnAction(ActionEvent event) {
        Optional<BookDTO> search = bookService.search(Integer.parseInt(txtBookId.getText().trim()));
        if (search.isPresent()) {
            setBookDataToFields(search.get());
        } else {
            new Alert(AlertType.ERROR, "Book Not Found").show();
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        if (cartList.isEmpty()) {
            new Alert(AlertType.WARNING, "Cart is empty!").show();
            return;
        }

        List<BookRecordDTO> recordDTOList = new ArrayList<>();
        for (BookRecordTM record : cartList) {
            BookRecordDTO bookRecordDTO = new BookRecordDTO(
                    record.getBookId(),
                    txtMemberId.getText(),
                    Date.valueOf(record.getBorrowedDate()),
                    false,
                    Date.valueOf(record.getReturnDate())
            );
            recordDTOList.add(bookRecordDTO);
        }

        try {
            boolean isSaved = bookRecordService.saveAll(recordDTOList);
            if (isSaved) {
                new Alert(AlertType.INFORMATION, "Books borrowed successfully").show();
                cartList.clear();
                tbBarrowBook.setItems(cartList);
            } else {
                new Alert(AlertType.ERROR, "Failed to save records").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    private void setMemberDataToFields(MemberDTO member) {
        txtMemberId.setText(member.getId());
        txtMemberName.setText(member.getName());
        txtMemberAddress.setText(member.getAddress());
        txtMemberEmail.setText(member.getEmail());
        txtMemberContact.setText(member.getContact());
    }

    private void setBookDataToFields(BookDTO book) {
        txtBookId.setText(String.valueOf(book.getId()));
        txtBookName.setText(book.getName());
        txtBookIsbn.setText(book.getIsbn());
        txtMainCategory.setText(String.valueOf(book.getMainCategory()));
    }

    private void visualizeTable() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBarrowDate.setCellValueFactory(new PropertyValueFactory<>("barrowedDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    public void loadCartData() {
        try {
            if (!cartList.isEmpty()) {
                tbBarrowBook.setItems(cartList);
                tbBarrowBook.refresh();
                System.out.println("Table data refreshed. Cart size: " + cartList.size());
            } else {
                tbBarrowBook.setItems(null);
                System.out.println("Cart list is empty.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading cart data: " + e.getMessage());
            new Alert(AlertType.ERROR, "Failed to load cart data: " + e.getMessage()).show();
        }
    }


    public void initialize(URL url, ResourceBundle rb) {
        visualizeTable();
        loadCartData();
        System.out.println("BarrowBookFormController initialized.");
    }

    private BookRecordTM convertBookRecordDtoToTM(BookRecordDTO bookRecordDTO) {
        return new BookRecordTM(
                bookRecordDTO.getBookId(),
                txtBookName.getText(),
                txtMemberName.getText(),
                dpBarrowDate.getValue(),
                dpReturnDate.getValue()
        );
    }

}
