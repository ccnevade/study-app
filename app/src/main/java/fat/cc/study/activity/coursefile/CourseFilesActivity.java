package fat.cc.study.activity.coursefile;

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

public class CourseFilesActivity extends BaseActivity {

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
        setActionBarTitle("课程资源");

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

//                Intent intent = null;
//                List<String> typsList = Arrays.asList("text/html", "image/*", "application/pdf", "text/plain", "audio/*", "video/*", "application/x-chm", "application/msword",
//                        "application/vnd.ms-excel", "application/vnd.ms-powerpoint", "application/vnd.android.package-archive");

//                if (typsList.contains(item.getFileType())) {
//
//                    if (FileType.html.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getHtmlFileIntent(item.getFileType());
//                    } else if (FileType.image.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getImageFileIntent(item.getFileType());
//                    } else if (FileType.pdf.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getImageFileIntent(item.getFileType());
//                    } else if (FileType.plain.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getTextFileIntent(item.getFileType());
//                    } else if (FileType.audio.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getAudioFileIntent(item.getFileType());
//                    } else if (FileType.video.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getVideoFileIntent(item.getFileType());
//                    } else if (FileType.chm.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getChmFileIntent(item.getFileType());
//                    } else if (FileType.msword.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getWordFileIntent(item.getFileType());
//                    } else if (FileType.msexcel.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getExcelFileIntent(item.getFileType());
//                    } else if (FileType.msppt.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getPPTFileIntent(item.getFileType());
//                    } else if (FileType.apk.getFileType().contains(item.getFileType())) {
//                        intent = FilesUtils.getApkFileIntent(item.getFileType());
//                    }

//                    switch (item.getFileType()) {
//                        case "text/html":
//                            intent = FilesUtils.getHtmlFileIntent(item.getFileType());
//                            break;
//                        case "image/*":
//                            intent = FilesUtils.getImageFileIntent(item.getFileType());
//                            break;
//                        case "application/pdf":
//                            intent = FilesUtils.getPdfFileIntent(item.getFileType());
//                            break;
//                        case "text/plain":
//                            intent = FilesUtils.getTextFileIntent(item.getFileType());
//                            break;
//                        case "audio/*":
//                            intent = FilesUtils.getAudioFileIntent(item.getFileType());
//                            break;
//                        case "video/*":
//                            intent = FilesUtils.getVideoFileIntent(item.getFileType());
//                            break;
//                        case "application/x-chm":
//                            intent = FilesUtils.getChmFileIntent(item.getFileType());
//                            break;
//                        case "application/msword":
//                            intent = FilesUtils.getWordFileIntent(item.getFileType());
//                            break;
//                        case "application/vnd.ms-excel":
//                            intent = FilesUtils.getExcelFileIntent(item.getFileType());
//                            break;
//                        case "application/vnd.ms-powerpoint":
//                            intent = FilesUtils.getPPTFileIntent(item.getFileType());
//                            break;
//                        case "application/vnd.android.package-archive":
//                            intent = FilesUtils.getApkFileIntent(item.getFileType());
//                            break;
//                        default:
//                            intent = FilesUtils.getTextFileIntent(item.getFileType());
//                            break;
//
//                    }
//                    startActivity(intent);
//                }
//
//                Toast.makeText(CourseFilesActivity.this,"不支持此文件格式！",Toast.LENGTH_SHORT).show();
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
                            list.add(new CourseFilesListItem(obj.getFileName(),obj.getFileUri(),obj.getFileType()));
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
