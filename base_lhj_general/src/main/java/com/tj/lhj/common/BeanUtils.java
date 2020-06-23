package com.tj.lhj.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname BeanUtils
 * @Discription TODO
 * @date 2020/1/13 9:50
 * @Created by liutengjun
 */
public final class BeanUtils {
  public static Map<String, Object> getObjectField(Object object) {
    if (object == null) {
      return null;
    } else {
      try {
        Map<String, Object> propertiesMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        PropertyDescriptor[] var4 = propertyDescriptors;
        int var5 = propertyDescriptors.length;

        for(int var6 = 0; var6 < var5; ++var6) {
          PropertyDescriptor property = var4[var6];
          String key = property.getName();
          if (!key.equals("class")) {
            Method getter = property.getReadMethod();
            Object value = getter.invoke(object);
            propertiesMap.put(key, value);
          }
        }

        return propertiesMap;
      } catch (Exception var11) {
        throw new BeanException("Object is " + object + ": ", var11);
      }
    }
  }
}
