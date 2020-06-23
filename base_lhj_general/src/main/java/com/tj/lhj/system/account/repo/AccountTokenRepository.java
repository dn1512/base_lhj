package com.tj.lhj.system.account.repo;

import com.tj.lhj.system.account.enti.AccountToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenRepository extends JpaRepository<AccountToken,Integer> {
    @Query("select a from AccountToken a where a.accountId =:accountId")
    AccountToken findByAccountId(@Param("accountId")Integer accountId);

    @Query("select a from AccountToken a where a.token =:accessToken")
    AccountToken findByToken(@Param("accessToken")String accessToken);
}
