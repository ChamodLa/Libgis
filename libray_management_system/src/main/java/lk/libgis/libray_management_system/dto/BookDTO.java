/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Chamod Abeywickrama
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int authorId;
    private int mainCategory;
}
