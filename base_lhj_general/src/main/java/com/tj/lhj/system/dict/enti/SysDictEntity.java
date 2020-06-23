package com.tj.lhj.system.dict.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Classname SysDictEntity
 * @Discription TODO
 * @date 2020/2/5 0:57
 * @Created by liutengjun
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="sys_dict")
public class SysDictEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition="int(11) COMMENT '数据唯一ID'")
  private Integer id;

  @Column(name = "dict_key",nullable = false,columnDefinition = "varchar(20) COMMENT '字典组'")
  private String dictKey;

  @Column(name = "dict_name",nullable = true,columnDefinition = "varchar(20) COMMENT '字典名'")
  private String dictName;

  @Column(name = "dict_value",nullable = true,columnDefinition = "varchar(50) COMMENT '字典值'")
  private String dictValue;

  @Column(name = "dict_flag",nullable = false,columnDefinition = "varchar(20) COMMENT '字典标识'")
  private String dictFlag;

  @Column(name = "description",nullable = true,columnDefinition = "varchar(20) COMMENT '描述'")
  private String description;

  public SysDictEntity(String dictKey,String dictFlag){
    this.setDictKey(dictKey);
    this.setDictFlag(dictFlag);
  }

  public SysDictEntity(Integer id,String dictName,String dictValue,String description){
    this.setId(id);
    this.setDictName(dictName);
    this.setDictValue(dictValue);
    this.setDescription(description);
  }
}
