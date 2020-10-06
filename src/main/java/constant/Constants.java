package constant;

public final class Constants {

    public static final int CART_LIMIT = 20;

    public static final int RECORDS_PER_PAGE = 5;

    public static final int MAX_PRICE = 10_000;
    public static final int MIN_PRICE = 0;

    public static final String URL = "jdbc:mysql://localhost:3306/online_store";
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "10082002";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String PATH_LOGOUT = "/log-out";

    public static final String PATH_ORDERS = "/order";

    public static final String PATH_LOGIN = "/log-in";
    public static final String PATH_LOGIN_JSP = "view/login-jsp.jsp";

    public static final String PATH_REGISTRATION = "/registration";
    public static final String PATH_REGISTRATION_JSP = "view/registration-jsp.jsp";

    public static final String PATH_PROFILE = "/profile";
    public static final String PATH_PROFILE_JSP = "view/user/profile-jsp.jsp";

    public static final String PATH_CART = "/cart";
    public static final String PATH_CART_JSP = "view/user/cart-jsp.jsp";

    public static final String PATH_CATALOG = "/catalog";
    public static final String PATH_CATALOG_JSP = "view/user/catalog-jsp.jsp";

    public static final String PATH_HOME = "/home";
    public static final String PATH_HOME_JSP = "view/user/home-jsp.jsp";

    public static final String PATH_ADMIN_PROFILE = "/admin-profile";
    public static final String PATH_ADMIN_PROFILE_JSP = "view/admin/admin-profile-jsp.jsp";

    public static final String PATH_ADMIN_USERS = "/admin-users";
    public static final String PATH_ADMIN_USERS_JSP = "view/admin/admin-users-jsp.jsp";

    public static final String PATH_ADMIN_ORDERS = "/admin-orders";
    public static final String PATH_ADMIN_ORDERS_JSP = "view/admin/admin-orders-jsp.jsp";

    public static final String PATH_ADMIN_CATALOG = "/admin-catalog";
    public static final String PATH_ADMIN_CATALOG_JSP = "/view/admin/admin-catalog-jsp.jsp";
}
