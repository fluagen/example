package com.cnebula.example.mybatis.dataobject;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import lombok.Data;

@Data
@TableName(autoResultMap = true)
public class Users {

	private Long id;
    private String name;
    private Integer age;
    private String email;
    
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Wallet wallet;
    
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Address> addresses;
}
