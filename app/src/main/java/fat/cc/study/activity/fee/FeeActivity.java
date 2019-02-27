package fat.cc.study.activity.fee;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import fat.cc.study.R;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeeActivity extends AppCompatActivity {

    private TextView feeContentTV;

    private RequestDataHandler handler;

    private int studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        feeContentTV = findViewById(R.id.fee_content);
        handler = new RequestDataHandler();

        studentId = getIntent().getIntExtra("studentId",0);
        requestData(studentId);
    }


    /**
     * 异步发送http请求获取数据
     */
    public void requestData(int id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(URL.FEE + id ).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
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
                        bundle.putString("fee",apiReview.getData().toString());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                } else {
                    System.out.println("请求失败");
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
            feeContentTV.setText(msg.getData().getString("fee"));

        }
    }
}
