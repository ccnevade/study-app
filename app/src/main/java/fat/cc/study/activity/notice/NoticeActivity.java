package fat.cc.study.activity.notice;

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
import fat.cc.study.activity.BaseActivity;
import fat.cc.study.bean.ApiReview;
import fat.cc.study.bean.Notice;
import fat.cc.study.bean.NoticeListItem;
import fat.cc.study.common.URL;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoticeActivity extends BaseActivity {

    private ListView listView;
    private NoticeListAdapter adapter;
    private List<NoticeListItem> list = new ArrayList<>();
    //下拉刷新容器
    private SwipeRefreshLayout swipeRefreshView;
    //消息分发器
    private RequestDataHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setActionBarTitle("公告列表");
        //初始化
        listView = findViewById(R.id.notice_lv);
        handler = new RequestDataHandler();

        //请求数据
        requestData();
        adapter = new NoticeListAdapter(NoticeActivity.this, R.layout.notice_list_item, list);
        //设置数据适配器
        listView.setAdapter(adapter);
        //点击事件监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoticeListItem item = list.get(i);

                Notice notice = new Notice();
                notice.setTitle(item.getTitle());
                notice.setContent(item.getContent());
                notice.setPublisher(item.getPublisher());
                notice.setCreateTime(item.getPublishDate());
                //TODO 跳转界面
                Intent intent = new Intent(NoticeActivity.this, NoticeContentActivity.class);
                intent.putExtra("notice", JSON.toJSONString(notice));
                startActivityForResult(intent, 0);
            }
        });

        //处理下拉刷新
        swipeRefreshView = findViewById(R.id.notice_swipeRefresh);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 请求数据
                requestData();
                swipeRefreshView.setRefreshing(false);
            }
        });

    }


    /**
     * 异步发送http请求获取数据
     */
    public void requestData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(URL.LIST_NOTICE ).build();
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
                        List<Notice> requestList = JSON.parseArray(JSON.toJSONString(apiReview.getData()), Notice.class);
                        for (Notice obj : requestList) {
                            list.add(new NoticeListItem(obj.getTitle(),obj.getContent(),obj.getPublisher(),obj.getCreateTime()));
                        }
                        //发送一个handler事件，再handler中更新主线程ui
                        handler.sendMessage(new Message());
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
            adapter.notifyDataSetChanged();
        }
    }
}