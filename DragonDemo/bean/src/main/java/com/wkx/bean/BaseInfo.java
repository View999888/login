package com.wkx.bean;

import java.io.Serializable;

public class BaseInfo<D> implements Serializable {

    private static final long serialVersionUID = -6195452361048076470L;

    public int errNo;
    public int exeTime;
    public String msg;
    public D result;

    public boolean isSuccess() {
        return errNo == 0;
    }
}

