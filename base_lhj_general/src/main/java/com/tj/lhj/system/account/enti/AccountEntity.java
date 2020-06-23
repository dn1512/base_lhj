package com.tj.lhj.system.account.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用于登录的账号信息，记录了员工ID
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="sys_account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition="int(20) COMMENT '数据唯一ID'")
    private Integer id;

    @Column(name = "org_id", nullable = true, columnDefinition="int(20) COMMENT '机构id'")
    private Integer orgId;

    @Column(name = "account_name",nullable = true,columnDefinition = "varchar(50) COMMENT '账户'")
    private String accountName;

    @Column(name = "account_password",nullable = true,columnDefinition = "varchar(50) COMMENT '登录密码'")
    private String accountPassword;

    @Column(name = "email",nullable = true,columnDefinition = "varchar(100) COMMENT '邮箱'")
    private String email;

    @Column(name = "mobile",nullable = true,columnDefinition = "varchar(100) COMMENT '电话'")
    private String mobile;

    @Column(name = "status",nullable = true,columnDefinition = "varchar(1) COMMENT '0:正常、1:禁用'")
    private String status;

    @Column(name = "remark",nullable = true,columnDefinition = "varchar(500) COMMENT '备注'")
    private String remark;

    @Column(name = "user_id_create",nullable = true,columnDefinition = "int(20) COMMENT '创建用户id'")
    private Integer userIdCreate;

    @Column(name = "gmt_create",nullable = true,columnDefinition = "datetime COMMENT '创建时间'")
    private Timestamp gmtCreate;

    @Column(name = "gmt_modified",nullable = true,columnDefinition = "datetime COMMENT '修改时间'")
    private Timestamp gmtModified;

    @Transient
    private String orgName;
}
