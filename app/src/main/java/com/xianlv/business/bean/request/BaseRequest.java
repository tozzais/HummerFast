package com.xianlv.business.bean.request;

import java.util.UUID;

public class BaseRequest {
    public String nonce_str;
    public String sign;

    public BaseRequest() {
        nonce_str = UUID.randomUUID().toString().replace("-", "").substring(0,6);
    }
}
