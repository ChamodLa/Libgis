/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.entity;

/**
 *
 * @author Chamod Abeywickrama
 */
public class Book {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int authorId;
    private int mainCategory;

    public Book() {
    }

    public Book(int id, String name, String isbn, double price, int authorId, int mainCategory) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.authorId = authorId;
        this.mainCategory = mainCategory;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }
}
