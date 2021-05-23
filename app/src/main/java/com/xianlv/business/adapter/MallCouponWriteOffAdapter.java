package com.xianlv.business.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.R;
import com.xianlv.business.bean.CheckDeductionGoodsItem;
import com.xianlv.business.bean.eventbus.RefreshGoodsWriteOff;
import com.xianlv.business.bean.request.RequestGoodsVerify;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.listener.OnPositionClickListener;

import org.greenrobot.eventbus.EventBus;

public class MallCouponWriteOffAdapter extends BaseQuickAdapter<CheckDeductionGoodsItem, BaseViewHolder> implements LoadMoreModule {


    private OnPositionClickListener listener;
    public MallCouponWriteOffAdapter(OnPositionClickListener listener) {
        super(R.layout.item_write_off_mall, null);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper,  CheckDeductionGoodsItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_write_off = helper.getView(R.id.tv_write_off);
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageView iv_select = helper.getView(R.id.iv_select);
        ImageUtil.loadFullAddress(getContext(),iv_image,item.img);
        helper.setText(R.id.tv_name,item.productName)
                .setText(R.id.tv_special,"规格："+item.property);
        if (item.status == 1){
            iv_select.setImageResource(R.mipmap.icon_unselect);
            iv_select.setEnabled(false);
            tv_write_off.setEnabled(false);
            tv_write_off.setText("已核销");
            tv_write_off.setBackgroundResource(R.drawable.shape_gray5_deep);
            tv_write_off.setTextColor(getContext().getResources().getColor(R.color.grayText));
        }else {
            iv_select.setEnabled(true);
            tv_write_off.setEnabled(true);
            tv_write_off.setText("核销");
            tv_write_off.setBackgroundResource(R.drawable.shape_blue5);
            tv_write_off.setTextColor(getContext().getResources().getColor(R.color.white));
            if (item.isSelect){
                iv_select.setImageResource(R.mipmap.icon_select);
            }else {
                iv_select.setImageResource(R.mipmap.icon_unselect);
            }
        }
        iv_select.setOnClickListener(view -> {
            item.isSelect = !item.isSelect;
            listener.onclick(position);
            notifyDataSetChanged();
        });

        tv_write_off.setOnClickListener(view -> {
            RequestGoodsVerify bean = new RequestGoodsVerify();
            bean.verificationId = item.verificationId;
            new RxHttp<BaseResult>().send(ApiManager.getService().goods_verification(bean),
                    new Response<BaseResult>(getContext()) {
                        @Override
                        public void onSuccess(BaseResult result) {
                            ToastCommom.createToastConfig().ToastShow(getContext(),"核销成功");
                            EventBus.getDefault().post(new RefreshGoodsWriteOff());
                        }
                    });
        });





   }



}
