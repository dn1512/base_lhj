package com.tj.lhj.system.role.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Classname SysRoleEntity
 * @Discription TODO
 * @date 2020/4/21 14:26
 * @Created by liutengjun
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "sys_role")
public class SysRoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id",nullable = false, columnDefinition="int(20) COMMENT '数据唯一ID'")
  private Integer roleId;

  @Column(name = "org_id", nullable = true, columnDefinition="int(20) COMMENT '机构id'")
  private Integer orgId;

  @Column(name = "role_name", nullable = true, columnDefinition="varchar(100) COMMENT '角色名称'")
  private String roleName;

  @Column(name = "role_sign", nullable = true, columnDefinition="varchar(100) COMMENT '角色标识'")
  private String roleSign;

  @Column(name = "remark", nullable = true, columnDefinition="varchar(100) COMMENT '备注'")
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
