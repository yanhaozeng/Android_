package com.yhz.yhz.json;

/**
 * @description: FastJsonObject ()
 * @author: Y.hz
 * @time: 2019/12/12 11:23
 */
public class FastJsonObject {
    private int code;
    private String msg;
    private Object obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "FastJsonObject{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
