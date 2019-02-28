package fat.cc.study.activity.suggestion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import fat.cc.study.R;
import fat.cc.study.activity.BaseActivity;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SuggestionActivity extends BaseActivity {

    private EditText suggestionTitleET;

    private EditText suggestionET;

    private BootstrapButton subBtn;

    private RequestDataHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        setActionBarTitle("意见建议");


        this.init();

        handler = new RequestDataHandler();

        final String author = getIntent().getStringExtra("author");

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isBlank(suggestionET.getText().toString())) {
                    Toast.makeText(SuggestionActivity.this,"请输入您的意见或建议！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                submitData(author);
            }
        });
    }

    private void init() {
        suggestionTitleET = findViewById(R.id.suggestion_title);
        suggestionET = findViewById(R.id.suggestion_et);
        subBtn = findViewById(R.id.suggestion_btn);

    }

    /**
     * 异步发送http提交数据
     */
    public void submitData(String author) {
        OkHttpClient client = new OkHttpClient();
        final RequestBody body = new FormBody.Builder().add("title",suggestionTitleET.getText().toString()).add("content",suggestionET.getText().toString()).add("author",author).build();
        Request request = new Request.Builder()
                .post(body)
                .url(URL.SUGGEST).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ApiReview apiReview = JSON.parseObject(response.body().string(), ApiReview.class);
                    if (null != apiReview && apiReview.getCode().equals(200000)) {

                        //发送一个handler事件，再handler中更新主线程ui
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success",true);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }else {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success",false);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                } else {
                    System.out.println("请求失败");
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success",false);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 向主线程发送消息更新ui
     */
    class RequestDataHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.getData().getBoolean("success")) {
                Toast.makeText(SuggestionActivity.this,"感谢您的宝贵意见！",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(SuggestionActivity.this,"意见提交失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
