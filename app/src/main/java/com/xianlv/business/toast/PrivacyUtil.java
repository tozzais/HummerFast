package com.xianlv.business.toast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.xianlv.business.R;
import com.xianlv.business.ui.AgreementWebViewActivity;
import com.xianlv.business.util.DialogUtils;


public class PrivacyUtil {


    private static Dialog cityDialog;


    public static void showTwo(Context context,  OnDialogClickListener listener) {
        String str = "贤旅商家版APP (以下简称“本应用”)非常重视用户\n" +
                "隐私政策并严格遵守相关的法律规定。请您仔细阅\n" +
                "读《隐私政策》、《用户协议》后再继续使用。如果您继续使用我\n" +
                "们的服务，表示您已经充分阅读和理解我们协议的\n" +
                "全部内容。\n" +
                "本应用尊重并保护所有使用服务用户的个人隐私权\n" +
                "。为了给您提供更准确、更优质的服务，本应用会\n" +
                "按照本隐私权政策的规定使用和披露您的个人信息\n" +
                "。除本隐私权政策另有规定外，在未征得您事先许\n" +
                "可的情况下，本应用不会将这些信息对外披露或向\n" +
                "第三方提供。本应用会不时更新本隐私权政策。";
        SpannableString string = new SpannableString(str);
        //设置TextView,可以被当做字符串设置给TextView
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        string.setSpan(colorSpan, str.indexOf("《用户协议》"), str.indexOf("《用户协议》")+"《用户协议》".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        string.setSpan(colorSpan1, str.indexOf("《隐私政策》"), str.indexOf("《隐私政策》")+"《隐私政策》".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementWebViewActivity.launch(context,
                        "http://www.mofan.store/mf/profile/biz/html/xianlvyhxy.html","用户协议");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan,str.indexOf("《用户协议》"),str.indexOf("《用户协议》")+"《用户协议》".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementWebViewActivity.launch(context,
                        "http://www.mofan.store/mf/profile/biz/html/xianlvxy.html","隐私政策");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan1,str.indexOf("《隐私政策》"),str.indexOf("《隐私政策》")+"《隐私政策》".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        View messageView = View.inflate(context, R.layout.pop_privacy, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView,false);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        TextView tv_sure = messageView.findViewById(R.id.tv_sure);
        TextView tv_cancel = messageView.findViewById(R.id.tv_cancel);
        tv_content.setText(string);
        //要加上这句点击事件才会触发
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_sure.setOnClickListener(v -> {
            listener.onSure();
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            listener.onCancel();
            cityDialog.dismiss();
            cityDialog = null;

        });
    }


}
