package com.tj.lhj.system.menu.repo;

import com.tj.lhj.system.menu.enti.SysMenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SysMenuRepository
 * @Discription TODO
 * @date 2019/12/4 9:43
 * @Created by liutengjun
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenuEntity,Integer> {

  /**
   * 根据父级ID返回以orderNum正序排列的集合
   * @param parentId
   * @return
   */
  @Query("select u from SysMenuEntity u where u.parentId = :parentId order by u.orderNum asc ")
  List<SysMenuEntity> findByParentId(@Param("parentId") int parentId);

  /**
   * 返回以orderNum正序排列的根节点集合
   * @param pageable
   * @return
   */
  @Query("select u from SysMenuEntity u where u.level = 0 order by u.orderNum asc ")
  Page<SysMenuEntity> treeList(Pageable pageable);

  /**
   * 返回以orderNum正序排列的根节点集合(无分页)
   * @return
   */
  @Query("select u from SysMenuEntity u where u.level = 0 order by u.orderNum asc ")
  List<SysMenuEntity> treeListAll();


}
