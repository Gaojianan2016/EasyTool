package com.jumi.easyrxevent;

/**
 * @author gjn
 * @time 2019/5/23 13:49
 */

public class RxMsg {
    public int code;
    public String msg;

    public RxMsg(int code) {
        this.code = code;
    }

    public RxMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
