package com.yhz.yhz.json;

/**
 * @description: FastJsonObject ()
 * @author: Y.hz
 * @time: 2019/12/12 11:23
 */
public class FastJsonObject {
    private int code;
    private String mag;
    private Object obj ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
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
                ", mag='" + mag + '\'' +
                ", obj=" + obj +
                '}';
    }
}
