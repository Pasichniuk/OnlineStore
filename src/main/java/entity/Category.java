package entity;

/**
 * Category entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class Category {

    private int categoryID;
    private String name;
    private String nameRU;

    public Category(int categoryID, String name, String nameRU) {
        this.categoryID = categoryID;
        this.name = name;
        this.nameRU = nameRU;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRU() {
        return nameRU;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }
}
