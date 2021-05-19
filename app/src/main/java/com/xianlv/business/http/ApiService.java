package com.xianlv.business.http;


import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.bean.MineInfo;
import com.xianlv.business.bean.ShopResult;
import com.xianlv.business.bean.request.BaseRequest;
import com.xianlv.business.bean.request.RequestCode;
import com.xianlv.business.bean.request.RequestLogin;
import com.xianlv.business.bean.request.RequestRegister;
import com.xianlv.business.bean.request.RequestShopInfo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {

    @POST(HttpUrl.get_code)
    Observable<BaseResult>
    getCode(@Body RequestCode requestCode);



    @POST(HttpUrl.login)
    Observable<BaseResult<LoginBean>>
    getLogin(@Body RequestLogin bean);
    @POST(HttpUrl.shop_info)
    Observable<BaseResult<ShopResult>>
    shopInfo(@Body RequestShopInfo bean);
    @POST(HttpUrl.register)
    Observable<BaseResult>
    register(@Body RequestRegister requestCode);
    @POST(HttpUrl.mine_info)
    Observable<BaseResult<MineInfo>>
    mineInfo(@Body BaseRequest bean);






}
