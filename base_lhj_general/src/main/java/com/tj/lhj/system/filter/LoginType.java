package com.tj.lhj.system.filter;

public enum LoginType {
  OAUTH2("OAuth2"),  APPLET("Applet");

  private String type;

  private LoginType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return this.type.toString();
  }
}
