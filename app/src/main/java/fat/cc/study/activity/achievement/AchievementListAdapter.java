package fat.cc.study.activity.achievement;

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
import fat.cc.study.bean.AchievementListItem;

public class AchievementListAdapter extends ArrayAdapter<AchievementListItem> {

    private int layoutId;

    public AchievementListAdapter(@NonNull Context context, int resource, @NonNull List<AchievementListItem> itemlist) {
        super(context, resource,itemlist);
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AchievementListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        TextView courseNameTV = view.findViewById(R.id.a_courseName);
        if (null != item.getCourseName()) {
            courseNameTV.setText(item.getCourseName());
        }
        TextView achievementTV = view.findViewById(R.id.achievement);
        if (null != item.getAchievement()) {
            achievementTV.setText(item.getAchievement().toString()+"分");
        }
        return view;
    }
}
