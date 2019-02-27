package fat.cc.study.activity.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fat.cc.study.R;
import fat.cc.study.activity.coursefile.CourseFilesActivity;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.bean.CourseListItem;
import fat.cc.study.bean.StudentCourse;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseActivity extends Activity {

    private ListView listView;
    private CourseListAdapter adapter;
    private List<CourseListItem> list = new ArrayList<>();
    //下拉刷新容器
    private SwipeRefreshLayout swipeRefreshView;
    //消息分发器
    private RequestDataHandler handler;

    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //初始化
        listView = findViewById(R.id.course_lv);
        handler = new RequestDataHandler();

        studentId = getIntent().getIntExtra("studentId", 0);

        //请求数据
        requestData(studentId);
        adapter = new CourseListAdapter(CourseActivity.this, R.layout.course_list_item, list);
        //设置数据适配器
        listView.setAdapter(adapter);
        //点击事件监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseListItem item = list.get(i);

                //TODO 跳转界面
                Intent intent = new Intent(CourseActivity.this, CourseFilesActivity.class);
                intent.putExtra("courseId", item.getCourseId());
                startActivityForResult(intent,0);
            }
        });

        //处理下拉刷新
        swipeRefreshView = findViewById(R.id.course_swipeRefresh);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 请求数据
                requestData(studentId);
                swipeRefreshView.setRefreshing(false);
            }
        });

    }



    /**
     * 异步发送http请求获取数据
     */
    public void requestData(Integer studentId){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(URL.LIST_COURSE+studentId).build();
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
                        List<StudentCourse> requestList = JSON.parseArray(JSON.toJSONString(apiReview.getData()), StudentCourse.class);
                        for (StudentCourse studentCourse : requestList) {
                            list.add(new CourseListItem(studentCourse.getCourseId(),studentCourse.getCourse().getName(), studentCourse.getCourse().getIntroduction(), studentCourse.getCourse().getFee()));
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
