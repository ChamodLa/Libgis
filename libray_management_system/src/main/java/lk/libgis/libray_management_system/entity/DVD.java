/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.entity;

/**
 *
 * @author Chamod Abeywickrama
 */
public class DVD {
    private int id;
    private String name;
    private double price;
    private int directorId;
    private int dvdCategory;
    private int duration;

    public DVD() {
    }

    public DVD(int id, String name, double price, int directorId, int dvdCategory, int duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.directorId = directorId;
        this.dvdCategory = dvdCategory;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public int getDvdCategory() {
        return dvdCategory;
    }

    public void setDvdCategory(int dvdCategory) {
        this.dvdCategory = dvdCategory;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
