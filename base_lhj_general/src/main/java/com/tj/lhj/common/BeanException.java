package com.tj.lhj.common;

/**
 * @Classname BeanException
 * @Discription TODO
 * @date 2020/6/17 15:03
 * @Created by liutengjun
 */
public class BeanException extends RuntimeException  {
  public BeanException() {
    super();
  }

  public BeanException(final String msg) {
    super("[ARSF.Common]：" + msg);
  }

  public BeanException(final String msg, final Throwable cause) {
    super("[ARSF.Common]：" + msg, cause);
  }
}
