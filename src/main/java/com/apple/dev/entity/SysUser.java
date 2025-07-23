package com.apple.dev.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SysUser {
    private Long userId;

    private String userName;

    private String nickName;

    /**
     * 余额
     */
    private BigDecimal balance;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;
}
