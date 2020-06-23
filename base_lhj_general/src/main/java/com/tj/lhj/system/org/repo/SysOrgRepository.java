package com.tj.lhj.system.org.repo;

import com.tj.lhj.system.org.enti.SysOrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SysOrgRepository
 * @Discription TODO
 * @date 2020/4/21 14:46
 * @Created by liutengjun
 */
@Repository
public interface SysOrgRepository extends JpaRepository<SysOrgEntity,Integer> {
  @Query("select u from SysOrgEntity u where u.level = 0 order by u.orderNum asc ")
  List<SysOrgEntity> treeListAll();

  @Query("select u from SysOrgEntity u where u.parentId = :parentId order by u.orderNum asc ")
  List<SysOrgEntity> findByParentId(@Param("parentId") Integer parentId);

  @Query("select u from SysOrgEntity u where u.status = 1 ")
  List<SysOrgEntity> findAllKy();
}
