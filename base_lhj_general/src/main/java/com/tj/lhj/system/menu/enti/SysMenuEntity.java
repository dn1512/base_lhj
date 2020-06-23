package com.tj.lhj.system.menu.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Classname SysMenuEntity
 * @Discription 系统菜单Entity
 * @date 2019/12/2 22:17
 * @Created by liutengjun
 */

@Entity
@Data
@NoArgsConstructor
@Table(name="sys_menu")
public class SysMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition="int(11) COMMENT '数据唯一ID'")
    private Integer id;

    @Column(name = "parent_id", columnDefinition="int(11) COMMENT '父级ID'")
    private Integer parentId;

    @Column(name = "level", columnDefinition="int(2) COMMENT '层级'")
    private Integer level;

    @Column(name = "isLeaf", columnDefinition="int(1) COMMENT '是否叶子节点'")
    private boolean isLeaf;

    @Column(name = "expanded", columnDefinition="int(1) COMMENT '是否展开'")
    private boolean expanded;

    @Transient
    private String parentName;

    @Column(name = "menu_name",nullable = false,columnDefinition = "varchar(20) COMMENT '菜单名称'")
    private String menuName;

    @Column(name = "url",columnDefinition = "varchar(50) COMMENT '菜单url'")
    private String url;

    @Column(name = "icon",columnDefinition = "varchar(40) COMMENT '菜单图标'")
    private String icon;

    @Column(name = "order_num",nullable = false,columnDefinition = "int(3) COMMENT '排序号'")
    private Integer orderNum;

    @Column(name = "type",nullable = false,columnDefinition = "int(1) COMMENT '类型   0：目录   1：菜单 '")
    private Integer type;

    @Transient
    private List<?> list;
}
