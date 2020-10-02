package entity;

import java.util.Date;

/**
 * Product entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class Product {

    private int id;
    private String name;
    private String category;
    private String categoryRU;
    private Date additionDate;
    private float price;

    public Product(int id, String name, String category, float price, Date additionDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.additionDate = additionDate;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryRU() {
        return categoryRU;
    }

    public void setCategoryRU(String categoryRU) {
        this.categoryRU = categoryRU;
    }

    public Date getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(Date additionDate) {
        this.additionDate = additionDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
