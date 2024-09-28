/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.dto.BookDTO;
import lk.libgis.libray_management_system.entity.Book;
import lk.libgis.libray_management_system.repo.BookRepo;

/**
 *
 * @author Chamod Abeywickrama
 */
public class BookService {

    private final BookRepo bookRepo = new BookRepo();

    public boolean addBook(BookDTO book) {
        try {
            Book entity = this.convertDTOtoEntity(book);
            System.out.println("Debug: Converting BookDTO to Book entity: " + entity);
            return bookRepo.saveBook(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.err.println("Debug: Exception in addBook - " + ex.getMessage());
            return false;
        }
    }


    public boolean delete(int id) {
        try {
            return bookRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(BookDTO book) {
        Book entity = convertDTOtoEntity(book);
        try {
            return bookRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<BookDTO> search(int id) {
        try {
            Optional<Book> book = bookRepo.searchBook(id);
            if (book.isPresent()) {
                BookDTO bookDTO = convertEntityToDTO(book.get());
                return Optional.of(bookDTO);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<BookDTO> getAll() {
        try {
            List<Book> all = bookRepo.getAll();
            if (all.isEmpty()) {
                throw new RuntimeException("No books found");
            }
            List<BookDTO> bookDTOS = new ArrayList<>();
            for (Book book : all) {
                BookDTO bookDTO = convertEntityToDTO(book);
                bookDTOS.add(bookDTO);
            }
            return bookDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Book convertDTOtoEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPrice(bookDTO.getPrice());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setMainCategory(bookDTO.getMainCategory());
        System.out.println("Debug: Converting BookDTO to Book entity: " + book);
        return book;
    }


    private BookDTO convertEntityToDTO(Book bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setName(bookEntity.getName());
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setPrice(bookEntity.getPrice());
        bookDTO.setAuthorId(bookEntity.getAuthorId());
        bookDTO.setMainCategory(bookEntity.getMainCategory());
        return bookDTO;
    }


}
