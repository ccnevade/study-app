package fat.cc.study.activity.coursefile;

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
import fat.cc.study.bean.CourseFilesListItem;

public class CourseFileListAdapter extends ArrayAdapter<CourseFilesListItem> {

    private int layoutId;

    public CourseFileListAdapter(@NonNull Context context, int resource, @NonNull List<CourseFilesListItem> itemlist) {
        super(context, resource,itemlist);
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseFilesListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        TextView fileNameTV = view.findViewById(R.id.fileName);
        fileNameTV.setText(item.getFileName());
        return view;
    }
}
