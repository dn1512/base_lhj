package com.tj.lhj.system.org.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Classname SysOrgEntity
 * @Discription TODO
 * @date 2020/4/21 14:38
 * @Created by liutengjun
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="sys_org")
public class SysOrgEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "org_id", nullable = false, columnDefinition="int(20) COMMENT '数据唯一ID'")
  private Integer orgId;

  @Column(name = "parent_id", nullable = true, columnDefinition="int(20) COMMENT '父级ID'")
  private Integer parentId;

  @Transient
  private String parentName;

  @Column(name = "code",nullable = true,columnDefinition = "varchar(100) COMMENT '机构编码'")
  private String code;

  @Column(name = "name",nullable = true,columnDefinition = "varchar(100) COMMENT '机构名称'")
  private String name;

  @Column(name = "order_num",nullable = true,columnDefinition = "int(3) COMMENT '排序'")
  private Integer orderNum;

  @Column(name = "status",nullable = true,columnDefinition = "int(1) COMMENT '可用标识  1：可用  0：不可用'")
  private Integer status;

  @Column(name = "gmt_create",nullable = true,columnDefinition = "datetime COMMENT '创建时间'")
  private Timestamp gmtCreate;

  @Column(name = "gmt_modified",nullable = true,columnDefinition = "datetime COMMENT '修改时间'")
  private Timestamp gmtModified;

  @Column(name = "level", columnDefinition="int(2) COMMENT '层级'")
  private Integer level;

  @Column(name = "isLeaf", columnDefinition="int(1) COMMENT '是否叶子节点'")
  private boolean isLeaf;

  @Column(name = "expanded", columnDefinition="int(1) COMMENT '是否展开'")
  private boolean expanded;
}
