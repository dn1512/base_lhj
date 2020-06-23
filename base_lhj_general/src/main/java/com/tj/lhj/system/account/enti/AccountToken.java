package com.tj.lhj.system.account.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * token信息
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="sys_account_token")
public class AccountToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition="int(11) COMMENT '数据唯一ID'")
    private Integer id;

    @Column(name = "account_id", nullable = false, columnDefinition="int(11) COMMENT '账户ID'")
    private Integer accountId;

    @Column(name = "token",nullable = false,columnDefinition = "varchar(40) COMMENT '账户'")
    private String token;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Timestamp gmtModified;
}
