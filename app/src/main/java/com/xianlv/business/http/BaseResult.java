package com.xianlv.business.http;


/**
 * Created by jumpbox on 16/8/23.
 */
public class BaseResult<T>{
    public int code;
    public T data;
    public String msg;
}
