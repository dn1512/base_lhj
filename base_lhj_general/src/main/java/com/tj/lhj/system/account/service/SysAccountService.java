package com.tj.lhj.system.account.service;

import com.tj.lhj.LhjApplication;
import com.tj.lhj.common.*;
import com.tj.lhj.system.account.enti.AccountEntity;
import com.tj.lhj.system.account.repo.SySAccountRepository;
import com.tj.lhj.system.org.enti.SysOrgEntity;
import com.tj.lhj.system.org.repo.SysOrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysAccountService {
    private static final Logger logger = LoggerFactory.getLogger(LhjApplication.class);

    @Autowired SySAccountRepository sySAccountRepository;

    @Autowired SysOrgRepository sysOrgRepository;
    /**
     * 为工程添加admin账户，密码123456。
     * 初次部署项目时使用
     */
    public void saveDefaultAccount() {
        String password = DigestUtils.md5Hex("123456");
        AccountEntity ae = sySAccountRepository.findByAccountName("admin");
        if(null == ae){
          ae = new AccountEntity();
            ae.setAccountName("admin");
            ae.setAccountPassword(password);
            ae.setStatus("0");
            logger.info("添加admin账号");
        }else{
            ae.setAccountPassword(password);
            logger.info("重置admin账号");
        }
        sySAccountRepository.save(ae);
    }

    /**
     * @param accountName
     * @return
     */
    public AccountEntity getAccountByName(String accountName) {
        AccountEntity ae = new AccountEntity();
        ae = sySAccountRepository.findByAccountName(accountName);
        return ae;
    }

    public AccountEntity getById(Integer accountId) {
        return sySAccountRepository.findById(accountId).get();
    }

    public AccountEntity save(AccountEntity ae) {
        return sySAccountRepository.save(ae);
    }

  public List<AccountEntity> findAll(Pageable pageable) {
        return sySAccountRepository.findAll();
  }

    public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
      ResultTableDataDto r = new ResultTableDataDto();
      PageRequest pageRequest = PageUtils.getPageRequest(tablePageInfosDTO);
      Page<AccountEntity> page = sySAccountRepository.findAllList(pageRequest);
      List<SysOrgEntity> orgs = sysOrgRepository.findAll();
      Map<Integer,String> orgNames = new HashMap<>();
      for(SysOrgEntity org:orgs){
        orgNames.put(org.getOrgId(),org.getName());
      }
      r.setSuccess(true);
      r.setTotalRows(page.getTotalElements());
      r.setCurPage(tablePageInfosDTO.getPage());
      if(page.getTotalElements() == 0 || null == page){
          return r;
      }
      List<AccountEntity> ae = page.getContent();
      List<AccountEntity> list = page.getContent();
      for(AccountEntity account:list){
        account.setOrgName(orgNames.get(account.getOrgId()));
      }
      r.setRows(list.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
      return r;
    }

  public ResultDto saveAccount(AccountEntity account) {
      try{
        if(null == account.getId()){
          account.setAccountPassword(DigestUtils.md5Hex(account.getAccountPassword()));
          account.setGmtCreate(new Timestamp(System.currentTimeMillis()));
          sySAccountRepository.save(account);
        }else{
          AccountEntity ae = sySAccountRepository.findById(account.getId()).get();
          account.setAccountPassword(ae.getAccountPassword());
          sySAccountRepository.save(account);
        }
        return  ResultDto.success("保存成功！");
      } catch (Exception e){
        e.printStackTrace();
        return ResultDto.error();
      }
  }

  public ResultDto delete(Integer id) {
    try{
      sySAccountRepository.deleteById(id);
      return ResultDto.success("删除成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }
}
