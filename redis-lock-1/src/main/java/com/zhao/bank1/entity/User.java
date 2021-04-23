package com.zhao.bank1.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String user_img;
    private Date updatedtime;
    private String status;
}
