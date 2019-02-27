package fat.cc.study.activity.notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;

import fat.cc.study.R;
import fat.cc.study.bean.Notice;

public class NoticeContentActivity extends AppCompatActivity {

    private TextView titleTV;

    private TextView contentTV;

    private TextView publisherTV;

    private TextView publisherDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);

        this.init();
        String noticeStr = getIntent().getStringExtra("notice");
        Notice notice = JSON.parseObject(noticeStr, Notice.class);

        titleTV.setText(notice.getTitle());
        contentTV.setText(notice.getContent());
        publisherTV.setText(notice.getPublisher());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        publisherDateTV.setText(sdf.format(notice.getCreateTime()));
    }

    private void init() {
        titleTV = findViewById(R.id.title_tv);
        contentTV = findViewById(R.id.content_tv);
        publisherTV = findViewById(R.id.publisher_tv);
        publisherDateTV = findViewById(R.id.publisher_date_tv);
    }
}
