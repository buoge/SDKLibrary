package com.glodon.tool.exception;

/**
 * 不支持异常情况
 * Created by lichongmac@163.com on 2019-07-03.
 */
public class NotSupportedException extends Exception {
    public NotSupportedException(String message) {
        super(message);
    }
}
