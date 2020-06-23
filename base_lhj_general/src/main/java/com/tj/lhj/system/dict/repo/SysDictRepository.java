package com.tj.lhj.system.dict.repo;

import com.tj.lhj.system.dict.enti.SysDictEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname SysDictRepository
 * @Discription TODO
 * @date 2020/2/6 12:02
 * @Created by liutengjun
 */
@Repository
public interface SysDictRepository extends JpaRepository<SysDictEntity, Integer> {

  @Query("select d from SysDictEntity d where d.dictFlag =:dictFlag")
  List<SysDictEntity> findByDictFlag(@Param("dictFlag") String dictFlag);

  @Query("select distinct new SysDictEntity(d.dictKey,d.dictFlag) from SysDictEntity d order by d.dictKey asc")
  Page<SysDictEntity> findAllList(Pageable pageable);

  @Query("select new SysDictEntity(d.id,d.dictName,d.dictValue,d.description) from SysDictEntity d where d.dictFlag =:dictFlag order by d.dictValue asc")
  List<SysDictEntity> findDetailByDictFlag(@Param("dictFlag") String dictFlag);
}
