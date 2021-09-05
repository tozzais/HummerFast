package com.xianlv.business.ui.roommanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.adapter.switchroom.ModifyRoomPriceAdapter;
import com.xianlv.business.bean.eventbus.RefreshRoomPrice;
import com.xianlv.business.bean.switchroom.RoomPriceDetail;
import com.xianlv.business.bean.switchroom.RoomPriceItem;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 房价管理
 */
public class ModifyRoomPriceActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_name)
    TextView tv_name;

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ModifyRoomPriceActivity.class);
        from.startActivity(intent);
    }

    public static void launch(Context context, String promotionId, String workDateStr) {
        if (!ClickUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(context, ModifyRoomPriceActivity.class);
        intent.putExtra("promotionId",promotionId);
        intent.putExtra("workDateStr",workDateStr);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_room_price;
    }


    private String promotionId;
    private String workDateStr;
    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("修改房价");
        promotionId = getIntent().getStringExtra("promotionId");
        workDateStr = getIntent().getStringExtra("workDateStr");

    }

    @Override
    public void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("promotionId", promotionId);
        map.put("workDate", workDateStr);
        new RxHttp<BaseResult<RoomPriceItem>>().send(ApiManager.getService().getRoomPrice(map),
                new Response<BaseResult<RoomPriceItem>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<RoomPriceItem> result) {
                        roomPriceItem = result.data;
                        setData();
                    }
                });


    }
    private RoomPriceItem roomPriceItem;
    private void setData(){
        tv_name.setText(String.format("%s-%s %s%s", roomPriceItem.roomName, roomPriceItem.promotionName, roomPriceItem.date, roomPriceItem.week));
        ModifyRoomPriceAdapter adapter = new ModifyRoomPriceAdapter();
        rv_list.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_list.setAdapter(adapter);
        List<RoomPriceDetail> leveList = roomPriceItem.leveList;
        if (leveList ==null || leveList.size() == 0){
            return;
        }
        for (int i = 0; i < leveList.size(); i++) {
            leveList.get(i).isShow = (i==0);
        }
        adapter.setNewData(leveList);
    }

    @OnClick({R.id.tv_login})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_login) {//
            modify();
        }
    }

    private void modify(){
        if (roomPriceItem == null || roomPriceItem.leveList == null ||
                roomPriceItem.leveList.size() == 0){
            return;
        }
        if (roomPriceItem.leveList.get(0).isShow){
            tsg("请点击确认按钮，确认会员房价");
            return;
        }
        for (RoomPriceDetail roomPriceDetail:roomPriceItem.leveList){
            if (TextUtils.isEmpty(roomPriceDetail.price)){
                tsg("请输入"+roomPriceDetail.levelName);
                return;
            }
        }
        List<Map<String,String>> list = new ArrayList<>();
        for (RoomPriceDetail roomPriceDetail:roomPriceItem.leveList){
            Map<String, String> map = new HashMap<>();
            map.put("levelId",roomPriceDetail.id);
            map.put("price",roomPriceDetail.price);
            list.add(map);
        }
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("workDate", roomPriceItem.date);
        map.put("promotionId", roomPriceItem.promotionId);
        map.put("levelParam", new Gson().toJson(list));
        new RxHttp<BaseResult>().send(ApiManager.getService().modifyRoomPrice(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {

                        EventBus.getDefault().post(new RefreshRoomPrice());
                        tsg("修改成功");
                        finish();
                    }
                });
    }


}