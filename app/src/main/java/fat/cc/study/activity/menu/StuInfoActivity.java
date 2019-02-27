package fat.cc.study.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import fat.cc.study.R;
import fat.cc.study.activity.achievement.AchievementActivity;
import fat.cc.study.activity.course.CourseActivity;
import fat.cc.study.activity.fee.FeeActivity;
import fat.cc.study.activity.notice.NoticeActivity;
import fat.cc.study.activity.suggestion.SuggestionActivity;
import fat.cc.study.bean.LoginUser;
import fat.cc.study.bean.MenuBean;

public class StuInfoActivity extends AppCompatActivity {

    private TextView usernameTV;

    private TextView accountTV;

    private TextView ageTV;

    private TextView majorTV;

    private TextView classroomTV;

    private GridView menuGV;

    private LoginUser loginUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        this.initFields();

        Intent intent = getIntent();
        String stuInfoStr = intent.getStringExtra("stuInfo");

        loginUser = JSON.parseObject(stuInfoStr, LoginUser.class);

        usernameTV.setText(loginUser.getName()+"");

        accountTV.setText(loginUser.getAccount()+"");

        ageTV.setText(loginUser.getAge()+"");

        majorTV.setText(loginUser.getMajor()+"");

        classroomTV.setText(loginUser.getClassroom()+"");

        this.initGridView();
    }

    //初始化
    private void initFields() {
        usernameTV = findViewById(R.id.username_value);

        accountTV = findViewById(R.id.account_value);

        ageTV = findViewById(R.id.age_value);

        majorTV = findViewById(R.id.major_value);

        classroomTV = findViewById(R.id.classroom_value);

    }

    private void initGridView() {

        menuGV = findViewById(R.id.menu_gv);

        //初始化九宫格按钮内容
        final List<MenuBean> menuBeanList = new ArrayList<>();
        menuBeanList.add(new MenuBean("课程信息"));
        menuBeanList.add(new MenuBean("成绩查询"));
        menuBeanList.add(new MenuBean("学费查询"));
        menuBeanList.add(new MenuBean("通知公告"));
        menuBeanList.add(new MenuBean("意见建议"));

        //绑定适配器
        menuGV.setAdapter(new MenuGridVewAdapter(this,menuBeanList));

        //监听点击事件
        menuGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuBean menuBean = menuBeanList.get(position);
                switch (position) {
                    case 0://课程信息
                        Intent intent = new Intent(StuInfoActivity.this, CourseActivity.class);
                        intent.putExtra("studentId", loginUser.getId());
                        startActivityForResult(intent,0);
                        break;
                    case 1://成绩查询
                        intent = new Intent(StuInfoActivity.this, AchievementActivity.class);
                        intent.putExtra("studentId", loginUser.getId());
                        startActivityForResult(intent,0);
                        break;
                    case 2://学费查询
                        intent = new Intent(StuInfoActivity.this, FeeActivity.class);
                        intent.putExtra("studentId", loginUser.getId());
                        startActivityForResult(intent,0);
                        break;
                    case 3://通知公告
                        intent = new Intent(StuInfoActivity.this, NoticeActivity.class);
                        startActivityForResult(intent,0);
                        break;
                    case 4://意见建议
                        intent = new Intent(StuInfoActivity.this, SuggestionActivity.class);
                        intent.putExtra("author",loginUser.getName());
                        startActivityForResult(intent,0);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"未定义错误",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
