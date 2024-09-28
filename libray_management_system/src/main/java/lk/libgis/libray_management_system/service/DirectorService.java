/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.dto.DirectorDTO;
import lk.libgis.libray_management_system.entity.Director;
import lk.libgis.libray_management_system.repo.DirectorRepo;

/**
 *
 * @author Chamod Abeywickrama
 */
public class DirectorService {

    private final DirectorRepo directorRepo = new DirectorRepo();

    public boolean addDirector(DirectorDTO directorDTO) {
        try {
            Director entity = this.convertDTOtoEntity(directorDTO);
            return directorRepo.saveDirector(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            return directorRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(DirectorDTO directorDTO) {
        try {
            Director entity = convertDTOtoEntity(directorDTO);
            return directorRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<DirectorDTO> search(String id) {
        try {
            Optional<Director> director = directorRepo.searchDirector(id);
            if (director.isPresent()) {
                DirectorDTO directorDTO = convertEntityToDTO(director.get());
                return Optional.of(directorDTO);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public List<DirectorDTO> getAll() {
        try {
            List<Director> all = directorRepo.getAll();
            List<DirectorDTO> directorDTOS = new ArrayList<>();
            for (Director director : all) {
                DirectorDTO directorDTO = convertEntityToDTO(director);
                directorDTOS.add(directorDTO);
            }
            return directorDTOS;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Director convertDTOtoEntity(DirectorDTO directorDTO) {
        return new Director(
                directorDTO.getId(),
                directorDTO.getName()
        );
    }

    private DirectorDTO convertEntityToDTO(Director directorEntity) {
        return new DirectorDTO(
                directorEntity.getId(),
                directorEntity.getName()
        );
    }
}
