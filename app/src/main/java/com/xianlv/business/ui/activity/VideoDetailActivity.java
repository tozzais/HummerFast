package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;
import com.xianlv.business.bean.VideoDetail;
import com.xianlv.business.bean.eventbus.RefreshVideoList;
import com.xianlv.business.global.ImageUtil;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoDetailActivity extends BaseActivity {


    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_image)
    ImageView tvImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_views)
    TextView tvViews;

    public static void launch(Context from, String videoId) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, VideoDetailActivity.class);
        intent.putExtra("videoId", videoId);
        from.startActivity(intent);
    }

    private String videoId;

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_video;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        videoId = getIntent().getStringExtra("videoId");
        setStatusBar(2);
        toolbar.setNavigationIcon(R.mipmap.back_video);
        toolbar.setNavigationOnClickListener(view -> back());
        web_view.getSettings().setJavaScriptEnabled(true);


    }

    @Override
    public void loadData() {
        if (!isLoad) {
            showProress();
        }
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("videoId", "" + videoId);
        new RxHttp<BaseResult<VideoDetail>>().send(ApiManager.getService().videoDetail(map),
                new Response<BaseResult<VideoDetail>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<VideoDetail> result) {
                        showContent();
                        isLoad = true;
                        VideoDetail videoDetail = result.data;
                        videoView.setUrl(videoDetail.video); //设置视频地址
                        StandardVideoController controller = new StandardVideoController(mActivity);
                        controller.addDefaultControlComponent("", false);
                        videoView.setVideoController(controller); //设置控制器
                        videoView.start();
                        setData(videoDetail.content);

                        tvVideoTitle.setText(videoDetail.title);
                        ImageUtil.loadFullAddress(mActivity,tvImage,videoDetail.truePic);
                        tvName.setText(videoDetail.trueName);
                        tvTime.setText(videoDetail.createTime);
                        tvViews.setText(videoDetail.views);

                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }

                });

    }
    public void setData(String content){
        String varjs = "<style>p{margin:0;}</style> <script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        web_view.loadDataWithBaseURL("", varjs + content, "text/html", "UTF-8", null);
//        web_view.loadData(content, "text/html", "UTF-8");
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onDestroy() {
        DestoryWebview();
        super.onDestroy();
        videoView.release();
    }

    private void DestoryWebview() {
        if (web_view != null) {
            ViewGroup parent = (ViewGroup) web_view.getParent();
            if (parent != null) {
                parent.removeView(web_view);
            }
            web_view.removeAllViews();
            web_view.destroy();
        }
    }


    @Override
    public void onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    private void updateNumber(){
        Map<String, String> map = new HashMap<>();
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 6));
        map.put("videoId", "" + videoId);
        new RxHttp<BaseResult>().send(ApiManager.getService().updateVideoViews(map),
                new Response<BaseResult>(mActivity,Response.BOTH) {
                    @Override
                    public void onNext(BaseResult baseResult) {
                        EventBus.getDefault().post(new RefreshVideoList());
                    }
                });
    }


    @Override
    public void initListener() {
        super.initListener();
        updateNumber();
    }
}