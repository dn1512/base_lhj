package com.tj.lhj.system.menu.enti;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Classname SysMenuRoleEntity
 * @Discription TODO
 * @date 2019/12/4 13:59
 * @Created by liutengjun
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="sys_menu_role")
public class SysMenuRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition="int(11) COMMENT '数据唯一ID'")
    private Integer id;

    @Column(name = "menu_id", nullable = false, columnDefinition="int(11) COMMENT '菜单ID'")
    private Integer menuId;

    @Column(name = "role_id", nullable = false, columnDefinition="int(11) COMMENT '角色ID'")
    private Integer roleId;
}
