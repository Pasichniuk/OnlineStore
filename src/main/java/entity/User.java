package entity;

public class User {

    private int id;
    private String login;
    private String blockStatus;
    private String role;

    public User(int id, String login, String blockStatus, String role) {
        this.id = id;
        this.login = login;
        this.blockStatus = blockStatus;
        this.role = role;
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
}
