package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.dto.CategoryDTO;
import lk.libgis.libray_management_system.entity.Category;
import lk.libgis.libray_management_system.repo.CategoryRepo;

/**
 * 
 * @author Chamod Abeywickrama
 */
public class CategoryService {

    private final CategoryRepo categoryRepo = new CategoryRepo();

    public boolean addCategory(CategoryDTO category) {
        try {
            Category entity = this.convertDTOtoEntity(category);
            return categoryRepo.saveCategory(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return categoryRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(CategoryDTO category) {
        Category entity = convertDTOtoEntity(category);
        try {
            return categoryRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<CategoryDTO> search(int id) {
        try {
            Optional<Category> category = categoryRepo.searchCategory(id);
            if (category.isPresent()) {
                CategoryDTO categoryDTO = convertEntityToDTO(category.get());
                return Optional.of(categoryDTO);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<CategoryDTO> getAll() {
        try {
            List<Category> all = categoryRepo.getAll();
            if (all.isEmpty()) {
                throw new RuntimeException("No categories found");
            }
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category category : all) {
                CategoryDTO categoryDTO = convertEntityToDTO(category);
                categoryDTOS.add(categoryDTO);
            }
            return categoryDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Category convertDTOtoEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }

    private CategoryDTO convertEntityToDTO(Category categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());
        return categoryDTO;
    }
}
