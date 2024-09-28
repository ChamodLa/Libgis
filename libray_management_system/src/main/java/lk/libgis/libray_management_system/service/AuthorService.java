/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.service;

import lk.libgis.libray_management_system.dto.AuthorDTO;
import lk.libgis.libray_management_system.entity.Author;
import lk.libgis.libray_management_system.repo.AuthorRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Chamod Abeywickrama
 */

public class AuthorService {

    private final AuthorRepo authorRepo = new AuthorRepo();

    public boolean addAuthor(AuthorDTO author) {
        try {
            Author entity = this.convertDTOtoEntity(author);
            return authorRepo.saveAuthor(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            return authorRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(AuthorDTO author) {
        Author entity = convertDTOtoEntity(author);
        try {
            return authorRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }

    public Optional<Object> search(String id) {
        try {
            Optional<Author> author = authorRepo.searchAuthor(id);
            if (author.isPresent()) {
                AuthorDTO authorDTO = convertEntityToDTO(author.get());
                return Optional.of(authorDTO);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public List<AuthorDTO> getAll() {
        try {
            List<Author> all = authorRepo.getAll();
            if (all.isEmpty()) {
                throw new RuntimeException();
            }
            List<AuthorDTO> authorDTOS = new ArrayList<>();
            for (Author author : all) {
                AuthorDTO authorDTO = convertEntityToDTO(author);
                authorDTOS.add(authorDTO);
            }
            return authorDTOS;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Author convertDTOtoEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        return author;
    }

    private AuthorDTO convertEntityToDTO(Author authorEntity) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(authorEntity.getId());
        authorDTO.setName(authorEntity.getName());
        return authorDTO;
    }
}
