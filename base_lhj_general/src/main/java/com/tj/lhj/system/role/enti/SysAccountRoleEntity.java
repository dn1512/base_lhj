package com.tj.lhj.system.role.enti;

import javax.persistence.*;

/**
 * @Classname SysAccountRoleEntity
 * @Discription TODO
 * @date 2020/4/21 14:30
 * @Created by liutengjun
 */
@Table(name="sys_account_role")
public class SysAccountRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition="int(11) COMMENT '数据唯一ID'")
  private Integer id;

  @Column(name = "account_id", nullable = false, columnDefinition="int(11) COMMENT '账户ID'")
  private Integer accountId;

  @Column(name = "role_id", nullable = false, columnDefinition="int(11) COMMENT '角色ID'")
  private Integer roleId;
}
