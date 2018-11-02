package com.xing.properties;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/2 2:45 PM
 * @desc :
 */
public class RedisConfig {
    /**
     * address
     * 格式：redis://127.0.0.1:6379
     */
    private String address;

    /**
     * password
     */
    private String password;

    /**
     * database
     */
    private int database;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
