package fat.cc.study.activity.coursefile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fat.cc.study.R;
import fat.cc.study.activity.video.VideoActivity;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.bean.CourseFiles;
import fat.cc.study.bean.CourseFilesListItem;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseFilesActivity extends AppCompatActivity {

    private ListView listView;
    private CourseFileListAdapter adapter;
    private List<CourseFilesListItem> list = new ArrayList<>();
    //下拉刷新容器
    private SwipeRefreshLayout swipeRefreshView;
    //消息分发器
    private RequestDataHandler handler;

    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_files);

        //初始化
        listView = findViewById(R.id.file_lv);
        handler = new RequestDataHandler();

        courseId = getIntent().getIntExtra("courseId", 0);

        //请求数据
        requestData(courseId);
        adapter = new CourseFileListAdapter(CourseFilesActivity.this, R.layout.file_list_item, list);
        //设置数据适配器
        listView.setAdapter(adapter);
        //点击事件监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseFilesListItem item = list.get(i);

                //TODO 跳转界面
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("filePath", item.getFileUri());
                startActivity(intent);
                Toast.makeText(getApplicationContext(),item.getFileName(),Toast.LENGTH_SHORT).show();

            }
        });

        //处理下拉刷新
        swipeRefreshView = findViewById(R.id.course_file_swipeRefresh);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 请求数据
                requestData(courseId);
                swipeRefreshView.setRefreshing(false);
            }
        });

    }

    /**
     * 异步发送http请求获取数据
     */
    public void requestData(Integer id){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(URL.LIST_COURSE_FILE+id).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    ApiReview apiReview = JSON.parseObject(response.body().string(), ApiReview.class);
                    if (null != apiReview && apiReview.getCode().equals(200000)) {
                        List<CourseFiles> requestList = JSON.parseArray(JSON.toJSONString(apiReview.getData()), CourseFiles.class);
                        for (CourseFiles obj : requestList) {
                            list.add(new CourseFilesListItem(obj.getFileName(),obj.getFileUri()));
                        }
                        //发送一个handler事件，再handler中更新主线程ui
                        handler.sendMessage(new Message());
                    }

                }else {
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
            adapter.notifyDataSetChanged();
        }
    }
}
