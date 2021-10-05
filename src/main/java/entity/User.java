package entity;

/**
 * User entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class User {

    // TODO: consider converting to a record

    private int id;
    private String login;
    private String blockStatus;
    private String role;
    private String userName;
    private String userNameRU;

    public User(int id, String login, String blockStatus, String role, String userName, String userNameRU) {
        this.id = id;
        this.login = login;
        this.blockStatus = blockStatus;
        this.role = role;
        this.userName = userName;
        this.userNameRU = userNameRU;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(String blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameRU() {
        return userNameRU;
    }

    public void setUserNameRU(String userNameRU) {
        this.userNameRU = userNameRU;
    }
}