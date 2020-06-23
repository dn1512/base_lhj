package com.tj.lhj.system.filter;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class LoginTypeToken implements AuthenticationToken {

  public LoginTypeToken() {}

  public LoginTypeToken(String loginType, String token) {
    this.loginType = loginType;
    this.token = token;
  }

  private String loginType;

  private String token;

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
