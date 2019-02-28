package fat.cc.study.activity.video;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import fat.cc.study.R;
import fat.cc.study.activity.BaseActivity;

public class VideoActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        setActionBarTitle("视频");

        videoView = findViewById(R.id.videoView);

        //加载指定的视频文件
        String filePath = getIntent().getStringExtra("filePath");
        videoView.setVideoPath(filePath);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);

        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);

        //让VideoView获取焦点
        videoView.requestFocus();
    }

}
