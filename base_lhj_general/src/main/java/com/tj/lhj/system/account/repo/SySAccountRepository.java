package com.tj.lhj.system.account.repo;

import com.tj.lhj.system.account.enti.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SySAccountRepository extends JpaRepository<AccountEntity, Integer> {

  @Query(" select a from AccountEntity a where a.accountName=:accountName")
  AccountEntity findByAccountName(@Param("accountName") String accountName);

  @Query(" select a from AccountEntity a  ")
  Page<AccountEntity> findAllList(Pageable pageable);
}
