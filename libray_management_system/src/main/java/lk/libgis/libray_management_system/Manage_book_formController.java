package lk.libgis.libray_management_system;

import java.net.URL;
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
import lk.libgis.libray_management_system.dto.BookDTO;
import lk.libgis.libray_management_system.entity.Author;
import lk.libgis.libray_management_system.entity.Category;
import lk.libgis.libray_management_system.repo.AuthorRepo;
import lk.libgis.libray_management_system.repo.CategoryRepo;
import lk.libgis.libray_management_system.service.BookService;
import tm.BookTM;

/**
 * FXML Controller class
 *
 * @author Chamod Abeywickrama
 */
public class Manage_book_formController implements Initializable {

    @FXML
    private ComboBox<String> cmbmaincategory;

    @FXML
    private ComboBox<String> cmbAuthor;

    @FXML
    private TableColumn<BookTM, Integer> colbookid;

    @FXML
    private TableColumn<BookTM, String> colbookisbn;

    @FXML
    private TableColumn<BookTM, String> colbookname;

    @FXML
    private TableColumn<BookTM, Double> colbookprice;

    @FXML
    private TableView<BookTM> tblbook;

    @FXML
    private TextField txtbookid;

    @FXML
    private TextField txtbookisbn;

    @FXML
    private TextField txtbookprice;

    @FXML
    private TextField txtbooname;

    private final BookService service = new BookService();

    @FXML
    void bookidOnAction(ActionEvent event) {
        String bookIdText = txtbookid.getText().trim();
        if (bookIdText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid book ID").show();
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdText);
            Optional<BookDTO> search = service.search(bookId);
            if (search.isPresent()) {
                setDataToFields(search.get());
            } else {
                new Alert(Alert.AlertType.ERROR, "Book Not Found").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid book ID format").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtbookid.clear();
        txtbooname.clear();
        txtbookisbn.clear();
        txtbookprice.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookId = txtbookid.getText().trim();

        if (bookId.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter a valid book ID").show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this book?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean deleteSuccessful = service.delete(Integer.parseInt(bookId));
                if (deleteSuccessful) {
                    new Alert(AlertType.INFORMATION, "Book deleted successfully").show();
                } else {
                    new Alert(AlertType.ERROR, "Deletion failed. Please check the book ID and try again.").show();
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
            BookDTO bookDTO = collectData();
            boolean isBookSaved = service.addBook(bookDTO);
            if (isBookSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Book Saved Successfully").show();
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

    public BookDTO collectData() {
        try {
            int id = Integer.parseInt(txtbookid.getText().trim());
            String name = txtbooname.getText();
            String isbn = txtbookisbn.getText();
            double price = Double.parseDouble(txtbookprice.getText());

            String authorName = cmbAuthor.getSelectionModel().getSelectedItem();
            AuthorRepo authorRepo = new AuthorRepo();
            Optional<Author> selectedAuthor = authorRepo.searchAuthor(authorName);
            int authorId = selectedAuthor.map(Author::getId).orElseThrow(() -> new Exception("Author not found"));

            String categoryName = cmbmaincategory.getSelectionModel().getSelectedItem();
            Integer mainCategoryId = categoryMap.get(categoryName);
            if (mainCategoryId == null) {
                throw new Exception("Main category not found");
            }

            return new BookDTO(id, name, isbn, price, authorId, mainCategoryId);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid data format: " + e.getMessage()).show();
            return null;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error collecting data: " + e.getMessage()).show();
            return null;
        }
    }



    public void setDataToFields(BookDTO book) {
        txtbookid.setText(String.valueOf(book.getId()));
        txtbooname.setText(book.getName());
        txtbookisbn.setText(book.getIsbn());
        txtbookprice.setText(String.valueOf(book.getPrice()));

        int authorIndex = book.getAuthorId();
        cmbAuthor.getSelectionModel().select(authorIndex);

        int categoryIndex = book.getMainCategory();
        cmbmaincategory.getSelectionModel().select(categoryIndex);
    }


    private BookTM convertBookDtoToTM(BookDTO bookDTO) {
        BookTM bookTM = new BookTM();
        bookTM.setId(bookDTO.getId());
        bookTM.setName(bookDTO.getName());
        bookTM.setIsbn(bookDTO.getIsbn());
        bookTM.setPrice(bookDTO.getPrice());
        return bookTM;
    }

    public void loadTableData() {
        try {
            List<BookTM> list = new ArrayList<>();
            List<BookDTO> all = service.getAll();
            for (BookDTO bookDTO : all) {
                BookTM bookTM = convertBookDtoToTM(bookDTO);
                list.add(bookTM);
            }
            ObservableList<BookTM> bookTMS = FXCollections.observableArrayList(list);
            tblbook.setItems(bookTMS);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load table data: " + e.getMessage()).show();
        }
    }

    private void visualizeTable() {
        colbookid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colbookname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbookisbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colbookprice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private Map<String, Integer> categoryMap = new HashMap<>();

    private void loadComboBoxes() {
        try {
            AuthorRepo authorRepo = new AuthorRepo();
            List<Author> authors = authorRepo.getAll();
            ObservableList<String> authorNames = FXCollections.observableArrayList();
            for (Author author : authors) {
                authorNames.add(author.getName());
            }
            cmbAuthor.setItems(authorNames);

            CategoryRepo categoryRepo = new CategoryRepo();
            List<Category> categories = categoryRepo.getAll();
            ObservableList<String> categoryNames = FXCollections.observableArrayList();
            for (Category category : categories) {
                categoryNames.add(category.getName());
                categoryMap.put(category.getName(), category.getId());
            }
            cmbmaincategory.setItems(categoryNames);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load authors and categories: " + e.getMessage()).show();
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
