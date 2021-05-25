package com.xianlv.business.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xianlv.business.R;
import com.xianlv.business.adapter.RefundDetailAdapter;
import com.xianlv.business.bean.CashDetail;
import com.xianlv.business.bean.CheckDeductionCardItem;
import com.xianlv.business.listener.OnSureClickListener;
import com.xianlv.business.ui.activity.CouponCodeAuthActivity;


public class CenterDialogUtil {


    private static Dialog cityDialog;

    public static void show(Context context,String title, String content
            , final OnGetStringListener listener) {
        View messageView = View.inflate(context, R.layout.pop_custom, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        TextView tv_title = messageView.findViewById(R.id.tv_title);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_sure.setOnClickListener(v -> {
            listener.getString("1");
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.getString("0");
            cityDialog.dismiss();
            cityDialog = null;

        });
    }



    public static void showTwo(Context context, CheckDeductionCardItem item, String money, String btnCancel, String btnSure
            , final OnGetStringListener listener) {
        View messageView = View.inflate(context, R.layout.pop_one_btn2, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        TextView tv_text1 = messageView.findViewById(R.id.tv_text1);
        TextView tv_text2 = messageView.findViewById(R.id.tv_text2);
        TextView tv_text3 = messageView.findViewById(R.id.tv_text3);
        TextView tv_text4 = messageView.findViewById(R.id.tv_text4);
        tv_text1.setText(item.nickname);
        tv_text2.setText(item.phone);
        tv_text3.setText(item.cardName);
        tv_text4.setText(money+"元");
        tv_cancel.setText(btnCancel);
        tv_sure.setText(btnSure);
        tv_sure.setOnClickListener(v -> {
            listener.getString("1");
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.getString("0");
            cityDialog.dismiss();
            cityDialog = null;

        });
    }

    public static void showExplain(Context context,String s, final OnGetStringListener listener) {
        View messageView = View.inflate(context, R.layout.pop_explain, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView, false);
        EditText et_name = messageView.findViewById(R.id.et_name);
        et_name.setText(s);
        et_name.setSelection(s.length());
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(v -> {
            listener.getString(et_name.getText().toString().trim());
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            cityDialog.dismiss();
            cityDialog = null;
        });
    }

    public static void showVerify(Context context, String s,int type,OnGetStringListener listener) {
        View messageView = View.inflate(context, R.layout.pop_verify, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView, true);
        TextView tv_verify1 = messageView.findViewById(R.id.tv_verify1);
        TextView tv_verify2 = messageView.findViewById(R.id.tv_verify2);
        TextView tv_title = messageView.findViewById(R.id.tv_title);
        tv_title.setText(s);
        tv_verify1.setOnClickListener(v -> {
            listener.getString("1");
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_verify2.setOnClickListener(v -> {
            CouponCodeAuthActivity.launch(context,type);
            cityDialog.dismiss();
            cityDialog = null;
        });
    }

    public static void showRefundDetail(Context context, CashDetail data, OnSureClickListener listener) {
        View messageView = View.inflate(context, R.layout.pop_refund, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView);
        RecyclerView rv_list = messageView.findViewById(R.id.rv_list);
        TextView tv_money = messageView.findViewById(R.id.tv_money);
        TextView btn_cancel = messageView.findViewById(R.id.btn_cancel);
        TextView btn_sure = messageView.findViewById(R.id.btn_sure);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        RefundDetailAdapter mAdapter = new RefundDetailAdapter();
        rv_list.setAdapter(mAdapter);
        tv_money.setText("￥"+data.money);
        mAdapter.setNewData(data.accountRecords);
        btn_cancel.setOnClickListener(v -> {
            cityDialog.dismiss();
            cityDialog = null;
        });
        btn_sure.setOnClickListener(v -> {
            listener.onSure();
            cityDialog.dismiss();
            cityDialog = null;
        });
    }



}
