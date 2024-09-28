package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lk.libgis.libray_management_system.dto.DVDDTO;
import lk.libgis.libray_management_system.entity.DVD;
import lk.libgis.libray_management_system.repo.DVDRepo;

/**
 *
 * @author Chamod Abeywickrama
 */
public class DVDService {

    private final DVDRepo dvdRepo = new DVDRepo();

    public boolean addDVD(DVDDTO dvdDTO) {
        try {
            DVD entity = this.convertDTOtoEntity(dvdDTO);
            System.out.println("Debug: Converting DVDDTO to DVD entity: " + entity);
            return dvdRepo.saveDVD(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.err.println("Debug: Exception in addDVD - " + ex.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return dvdRepo.delete(id);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(DVDDTO dvdDTO) {
        DVD entity = convertDTOtoEntity(dvdDTO);
        try {
            return dvdRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<DVDDTO> search(int id) {
        try {
            Optional<DVD> dvd = dvdRepo.searchDVD(id);
            if (dvd.isPresent()) {
                DVDDTO dvdDTO = convertEntityToDTO(dvd.get());
                return Optional.of(dvdDTO);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<DVDDTO> getAll() {
        try {
            List<DVD> all = dvdRepo.getAll();
            if (all.isEmpty()) {
                throw new RuntimeException("No DVDs found");
            }
            List<DVDDTO> dvdDTOS = new ArrayList<>();
            for (DVD dvd : all) {
                DVDDTO dvdDTO = convertEntityToDTO(dvd);
                dvdDTOS.add(dvdDTO);
            }
            return dvdDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private DVD convertDTOtoEntity(DVDDTO dvdDTO) {
        DVD dvd = new DVD();
        dvd.setId(dvdDTO.getId());
        dvd.setName(dvdDTO.getName());
        dvd.setPrice(dvdDTO.getPrice());
        dvd.setDirectorId(dvdDTO.getDirectorId());
        dvd.setDvdCategory(dvdDTO.getDvdCategory());
        dvd.setDuration(dvdDTO.getDuration());
        System.out.println("Debug: Converting DVDDTO to DVD entity: " + dvd);
        return dvd;
    }

    private DVDDTO convertEntityToDTO(DVD dvdEntity) {
        DVDDTO dvdDTO = new DVDDTO();
        dvdDTO.setId(dvdEntity.getId());
        dvdDTO.setName(dvdEntity.getName());
        dvdDTO.setPrice(dvdEntity.getPrice());
        dvdDTO.setDirectorId(dvdEntity.getDirectorId());
        dvdDTO.setDvdCategory(dvdEntity.getDvdCategory());
        dvdDTO.setDuration(dvdEntity.getDuration());
        return dvdDTO;
    }
}
