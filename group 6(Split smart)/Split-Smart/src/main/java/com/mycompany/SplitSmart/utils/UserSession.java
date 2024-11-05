
package com.mycompany.SplitSmart.utils;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String code;

    // Private constructor prevents instantiation from other classes
    private UserSession() {}

    // Public method to provide access to the instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getGroupCode() {
        return code;
    }

    public void setGroupCode(String code) {
        this.code = code;
    }
}
