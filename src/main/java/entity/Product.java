package entity;

import java.util.Date;

/**
 * Product entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class Product {

    // TODO: consider converting to a record

    private int id;
    private String name;
    private String category;
    private String categoryRU;
    private Date additionDate;
    private double price;
    private int count;
    private int reserve;

    public Product(int id, String name, String category, double price, Date additionDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.additionDate = additionDate;
        this.price = price;
        this.count = 1;
        this.reserve = 0;
    }

    public Product() {}

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }
}