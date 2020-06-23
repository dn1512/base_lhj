package com.tj.lhj.system.menu.repo;

import com.tj.lhj.system.menu.enti.SysMenuEntity;
import com.tj.lhj.system.menu.enti.SysMenuRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SysMenuRoleRepository
 * @Discription TODO
 * @date 2019/12/4 14:01
 * @Created by liutengjun
 */
@Repository
public interface SysMenuRoleRepository extends JpaRepository<SysMenuRoleEntity,Integer> {
    @Query("select distinct m.menuId from SysMenuRoleEntity m , com.tj.lhj.system.role.enti.SysAccountRoleEntity a where m.roleId = a.roleId and a.accountId = :accountId ")
    List<Integer> findByAccountId(@Param("accountId") Integer accountId);

    @Query("select m from SysMenuRoleEntity m where m.roleId = :roleId ")
    List<SysMenuRoleEntity> findDetailByRoleId(@Param("roleId") Integer roleId);
}
