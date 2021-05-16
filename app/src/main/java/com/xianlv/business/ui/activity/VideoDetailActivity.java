package com.xianlv.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.xianlv.business.R;

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

    public static void launch(Context from) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, VideoDetailActivity.class);
        from.startActivity(intent);
    }
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
        setStatusBar(2);
        toolbar.setNavigationIcon(R.mipmap.back_video);
        toolbar.setNavigationOnClickListener(view -> back());

    }

    @Override
    public void loadData() {
        videoView.setUrl("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("", false);
        videoView.setVideoController(controller); //设置控制器
//        videoView.start(); //开始播放，不调用则不自动播放

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl("https://www.baidu.com");

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

}