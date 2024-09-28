package lk.libgis.libray_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for DVD.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DVDDTO {
    private int id;
    private String name;
    private double price;
    private int directorId;
    private int dvdCategory;
    private int duration;
}
