/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.dto.DvdCategoryDTO;
import lk.libgis.libray_management_system.entity.DvdCategory;
import lk.libgis.libray_management_system.repo.DvdCategoryRepo;

/**
 * 
 * @author Chamod Abeywickrama
 */
public class DvdCategoryService {

    private final DvdCategoryRepo dvdCategoryRepo = new DvdCategoryRepo();

    public boolean addDvdCategory(DvdCategoryDTO dvdCategory) {
        try {
            DvdCategory entity = this.convertDTOtoEntity(dvdCategory);
            return dvdCategoryRepo.saveDvdCategory(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return dvdCategoryRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(DvdCategoryDTO dvdCategory) {
        DvdCategory entity = convertDTOtoEntity(dvdCategory);
        try {
            return dvdCategoryRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<DvdCategoryDTO> search(int id) {
        try {
            Optional<DvdCategory> dvdCategory = dvdCategoryRepo.searchDvdCategory(id);
            if (dvdCategory.isPresent()) {
                DvdCategoryDTO dvdCategoryDTO = convertEntityToDTO(dvdCategory.get());
                return Optional.of(dvdCategoryDTO);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<DvdCategoryDTO> getAll() {
        try {
            List<DvdCategory> all = dvdCategoryRepo.getAll();
            if (all.isEmpty()) {
                throw new RuntimeException("No DVD categories found");
            }
            List<DvdCategoryDTO> dvdCategoryDTOS = new ArrayList<>();
            for (DvdCategory dvdCategory : all) {
                DvdCategoryDTO dvdCategoryDTO = convertEntityToDTO(dvdCategory);
                dvdCategoryDTOS.add(dvdCategoryDTO);
            }
            return dvdCategoryDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private DvdCategory convertDTOtoEntity(DvdCategoryDTO dvdCategoryDTO) {
        DvdCategory dvdCategory = new DvdCategory();
        dvdCategory.setId(dvdCategoryDTO.getId());
        dvdCategory.setName(dvdCategoryDTO.getName());
        return dvdCategory;
    }

    private DvdCategoryDTO convertEntityToDTO(DvdCategory dvdCategoryEntity) {
        DvdCategoryDTO dvdCategoryDTO = new DvdCategoryDTO();
        dvdCategoryDTO.setId(dvdCategoryEntity.getId());
        dvdCategoryDTO.setName(dvdCategoryEntity.getName());
        return dvdCategoryDTO;
    }
}
