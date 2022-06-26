package com.xianlv.business.http;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.sign.SignUtil;
import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.global.GlobalParam;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {

    private static TreeMap<String, String> commonParams;

    public synchronized static void setCommonParam(Map<String, String> commonParams) {
        if (commonParams != null) {
            if (CommonInterceptor.commonParams != null) {
                CommonInterceptor.commonParams.clear();
            } else {
                CommonInterceptor.commonParams = new TreeMap<>();
            }
            for (String paramKey : commonParams.keySet()) {
                CommonInterceptor.commonParams.put(paramKey, commonParams.get(paramKey));
            }
        }
    }

    public synchronized static void updateOrInsertCommonParam(@NonNull String paramKey, @NonNull String paramValue) {
        if (commonParams == null) {
            commonParams = new TreeMap<>();
        }
        commonParams.put(paramKey, paramValue);
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request request = rebuildRequest(chain.request());
        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
//            LogUtil.e("response: " + sbJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        saveCookies(response, request.url().toString());
        return response;
    }


    public static byte[] toByteArray(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        InputStream inputStream = buffer.inputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bufferWrite = new byte[4096];
        int n;
        while (-1 != (n = inputStream.read(bufferWrite))) {
            output.write(bufferWrite, 0, n);
        }
        return output.toByteArray();
    }

    private Request rebuildRequest(Request request) throws IOException {
        RequestBody requestBody = request.body();
        StringBuilder sb = new StringBuilder();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            //编码设为UTF-8
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }
            if (charset != null)
            sb.append(buffer.readString(charset));
            buffer.close();
        }
        LogUtil.e("加密信息前"+sb.toString());
        String sign = "";
        if (request.method().equals("POST")) {
            sign = SignUtil.getMd5(sb.toString());
        }

        LoginBean loginBean = GlobalParam.getLoginBean();
//        if (!TextUtils.isEmpty(sign)){
//            LogUtil.e("加密信息后"+sign);
//        }
        request = request.newBuilder()
                    .addHeader("sign",sign == null?"":sign)
                    .addHeader("platform","APP")
                    .addHeader("token",loginBean == null?"":loginBean.token)
                    .build();
        return request;

    }




}
