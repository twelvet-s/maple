package com.twelvet.framework.enums;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 返回状态码
 */
public enum HttpStatus {
    // 访问成功
    SUCCESS(200, "SUCCESS"),

    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String reasonPhrase;

    HttpStatus(int value, String reasonPhrase) {
        this.code = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return this.code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
