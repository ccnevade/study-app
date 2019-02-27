package fat.cc.study.activity.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fat.cc.study.R;
import fat.cc.study.bean.CourseListItem;

public class CourseListAdapter extends ArrayAdapter<CourseListItem> {

    private int layoutId;

    public CourseListAdapter(@NonNull Context context, int resource,@NonNull List<CourseListItem> itemlist) {
        super(context, resource,itemlist);
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        TextView courseNameTV = view.findViewById(R.id.courseName);
        if (null != item.getCourseName()) {
            courseNameTV.setText(item.getCourseName());
        }
        TextView introductionTV = view.findViewById(R.id.introduce);
        if (null != item.getIntroduction()) {
            introductionTV.setText(item.getIntroduction());
        }
        TextView feeTV = view.findViewById(R.id.fee);
        if (null != item.getFee()) {
            feeTV.setText(item.getFee().toString()+"元");
        }
        return view;
    }
}
