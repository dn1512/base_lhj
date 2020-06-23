package com.tj.lhj.system.account.service;

import com.tj.lhj.common.ResultDto;
import com.tj.lhj.system.account.enti.AccountEntity;
import com.tj.lhj.system.account.enti.AccountToken;
import com.tj.lhj.system.account.repo.AccountTokenRepository;
import com.tj.lhj.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class SySAccountTokenService {
    @Autowired AccountTokenRepository accountTokenRepository;

    public void saveAccountToken(Integer accountId,String token){
        AccountToken at = accountTokenRepository.findByAccountId(accountId);
        if (null == at){
            at = new AccountToken();
            at.setAccountId(accountId);
            at.setGmtCreate(new Timestamp(System.currentTimeMillis()));
        }else{
            at.setGmtModified(new Timestamp(System.currentTimeMillis()));
        }
        at.setToken(token);
        accountTokenRepository.save(at);
    }

    public String createAccountToken(Integer id){
        String token;
        //账户ID+随机UUID+系统当前时间
        token = DigestUtils.md5Hex(id + UUID.randomUUID().toString() + DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
        return token;
    }

    public AccountToken getByToken(String accessToken) {
        return accountTokenRepository.findByToken(accessToken);
    }

    public void upDateTokenModified(Integer accountTokenId) {
        AccountToken at = accountTokenRepository.findById(accountTokenId).get();
        at.setGmtModified(new Timestamp(System.currentTimeMillis()));
        accountTokenRepository.save(at);
    }
}
