package entity;

/**
 * Order entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class Order {

    private int orderID;
    private int userID;
    private String status;
    private String userLogin;

    public Order(int orderID, int userID, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
