package com.cnebula.example.mybatis.dataobject;

import java.util.List;

import lombok.Data;

/**
 * 钱包
 */
@Data
public class Wallet {
    /**
     * 名称
     */
    private String name;
    
    /**
     * 级别
     */
    private int level;
    
    /**
     * 各种货币
     */
    private List<Currency> currencies;
}
