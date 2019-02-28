package fat.cc.study.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import fat.cc.study.R;
import fat.cc.study.activity.BaseActivity;
import fat.cc.study.activity.menu.StuInfoActivity;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.bean.LoginUser;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private BootstrapEditText usernameEditText;

    private BootstrapEditText passwordEditText;

    private BootstrapButton loginBtn;

    public  static LoginUser LOGIN_USER = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setActionBarTitle("学生登录");
        this.init();

        /**
         * 监听
         */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    /**
     * 初始化组件
     */
    private void init() {
        usernameEditText = findViewById(R.id.username_editText);

        passwordEditText = findViewById(R.id.password_editText);

        loginBtn = findViewById(R.id.login_btn);

    }

    /**
     * 登录
     */
    private void login(){
        String username = usernameEditText.getText().toString();

        String password = passwordEditText.getText().toString();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            Toast.makeText(this, "用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
        }


        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username",username).add("password",password).build();
        Request request = new Request.Builder()
                .post(body)
                .url(URL.LOGIN).
                        build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                System.out.println("请求失败！");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ApiReview  apiReview = JSON.parseObject(response.body().string(), ApiReview.class);
                    if (null != apiReview && apiReview.getCode().equals(200000)) {
                        Intent intent = new Intent(LoginActivity.this, StuInfoActivity.class);
                        intent.putExtra("stuInfo", JSON.toJSONString(apiReview.getData()));
                        startActivityForResult(intent,0);
                    }
                }
            }
        });


    }
}
