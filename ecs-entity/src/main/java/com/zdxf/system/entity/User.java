package com.zdxf.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@TableName("user")
public class User implements Serializable {

//    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Size(min=5, max=20,message = "用户名不能小于5位，大于20位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min=6,max = 200,message = "密码不能小于6位,大于20位")
    private String password;

    private String userImg;

    private Date updatetime;

    private String status;
}
