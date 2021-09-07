package com.xianlv.business.adapter;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.SunmiPrint;
import com.xianlv.business.bean.OrderDetail;
import com.xianlv.business.bean.ReceiveOrderItem;
import com.xianlv.business.bean.switchroom.EmployeePermissions;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.Refund1Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReceivePayOrderAdapter extends BaseQuickAdapter<ReceiveOrderItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public ReceivePayOrderAdapter(int type) {
        super(R.layout.item_receive_pay_order, null);
        this.type = type;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void convert(BaseViewHolder helper,  ReceiveOrderItem item) {
        int position = helper.getAdapterPosition();

//        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
//        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
//        SendFoodReminderSubAdapter adapter = new SendFoodReminderSubAdapter();
//        rv_goods.setAdapter(adapter);
//        List<MealOrderItem.Goods> detailVOS = item.detailVOS;
//        String openStr = "展开(共"+ detailVOS.size()+"件)";
//        String closeStr = "收起(共"+ detailVOS.size()+"件)";
       TextView tv_text3 = helper.getView(R.id.tv_text3);
       TextView tv_text8 = helper.getView(R.id.tv_text8);
       TextView tv_text10 = helper.getView(R.id.tv_text10);
       TextView tv_text9 = helper.getView(R.id.tv_text9);
        TextView tv_refuse = helper.getView(R.id.tv_refuse);
        TextView tv_text41 = helper.getView(R.id.tv_text41);
        if (type == 0){
            tv_refuse.setVisibility(View.VISIBLE);
            helper.setVisible(R.id.tv_text2, false);
            tv_text9.setVisibility(View.GONE);
            tv_text10.setVisibility(View.GONE);
            tv_text41.setVisibility(View.GONE);
            tv_text3.setText("收款金额："+item.money);
            helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                    .setText(R.id.tv_text2,item.getReturnStatus())
                    .setText(R.id.tv_text4,"收款类型："+item.purposeStatus)
                    .setText(R.id.tv_text5,"付款人："+item.nickname)
                    .setText(R.id.tv_text6,"收款员工："+item.operuser)
                    .setText(R.id.tv_text7,"收款时间："+item.createtime)
                    .setText(R.id.tv_text8,"备注"+item.remark);
        }else {
            tv_refuse.setVisibility(View.GONE);
            tv_text3.setText("退款金额："+item.refundMoney);
            helper.setText(R.id.tv_text1,"订单号："+item.orderNo)
                    .setText(R.id.tv_text2,item.getReturnStatus())
                    .setText(R.id.tv_text4,"收款类型："+item.money)
                    .setText(R.id.tv_text41,"收款金额："+item.qrcodeName)
                    .setText(R.id.tv_text5,"付款人："+item.nickname)
                    .setText(R.id.tv_text6,"收款员工："+item.operuser)
                    .setText(R.id.tv_text7,"收款时间："+item.createtime)
                    .setText(R.id.tv_text8,"退款时间："+item.checktime)
                    .setText(R.id.tv_text9,"操作人："+item.operuserRefund)
                    .setText(R.id.tv_text10,"备注："+ (TextUtils.isEmpty(item.reason)?"":item.reason));
        }

        helper.getView(R.id.tv_pass).setOnClickListener(v -> {
            if (type == 0){
                print1(item.scanId);
            }else {
                print2(item.scanRefundId);
            }

//            SunmiPrint.INSTANCE.printReceipt(getContext(),item,type);
        });
        tv_refuse.setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
            new RxHttp<BaseResult<EmployeePermissions>>().send(ApiManager.getService().queryAuthority(map)
                    ,new Response<BaseResult<EmployeePermissions>>(getContext()){
                        @Override
                        public void onSuccess(BaseResult<EmployeePermissions> baseResult) {
                            if (baseResult.data.isCanReceivePayment()){
                                Refund1Activity.launch(getContext(),item.scanId);
                            }else {
                                ToastCommom.createToastConfig().ToastShow(getContext(),"暂无权限");
                            }
                        }
                    });


        });


    }
    private void print1(String scanId){
        Map<String,String> map = new HashMap<>();
        map.put("scanId", scanId);
        new RxHttp<BaseResult<OrderDetail>>().send(ApiManager.getService().getOrderDetail(map),
                new Response<BaseResult<OrderDetail>>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult<OrderDetail> result) {
                        SunmiPrint.INSTANCE.printReceipt(getContext(),result.data,type);
                    }
                });
    }

    private void print2(String scanId){
        Map<String,String> map = new HashMap<>();
        map.put("scanRefundId", scanId);
        new RxHttp<BaseResult<OrderDetail>>().send(ApiManager.getService().getOrderDetail1(map),
                new Response<BaseResult<OrderDetail>>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult<OrderDetail> result) {
                        SunmiPrint.INSTANCE.printReceipt(getContext(),result.data,type);
                    }
                });
    }
}
