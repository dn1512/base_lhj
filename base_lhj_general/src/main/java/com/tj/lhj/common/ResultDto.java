package com.tj.lhj.common;

import java.util.HashMap;

public class ResultDto extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResultDto(){
        this.put("code",0);
    }

    public static ResultDto success(){
        return new ResultDto();
    }

    public static ResultDto success(String msg){
        ResultDto resultDto = new ResultDto();
        resultDto.put("msg",msg);
        return resultDto;
    }

    public static ResultDto error(){
        return error(500, "未知异常，请联系管理员");
    }

    public static ResultDto error(String msg){
        return error(500, msg);
    }

    public static ResultDto error(int code,String msg){
        ResultDto resultDto = new ResultDto();
        resultDto.put("code",code);
        resultDto.put("msg",msg);
        return resultDto;
    }

    public ResultDto put(String key, Object value){
        super.put(key, value);
        return this;
    }

    public ResultDto code(int code){
        return this.put("code",code);
    }

    public ResultDto msg(String msg){
        return this.put("msg",msg);
    }

    public ResultDto obj(Object obj){
        return this.put("obj",obj);
    }
}
