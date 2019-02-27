package fat.cc.study.activity.notice;

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
import fat.cc.study.bean.NoticeListItem;

public class NoticeListAdapter extends ArrayAdapter<NoticeListItem> {

    private int layoutId;

    public NoticeListAdapter(@NonNull Context context, int resource, @NonNull List<NoticeListItem> itemlist) {
        super(context, resource,itemlist);
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NoticeListItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        TextView titleTv = view.findViewById(R.id.notice_title);
        if (null != item.getTitle()) {
            titleTv.setText(item.getTitle());
        }
        TextView publisherTv = view.findViewById(R.id.notice_publisher);
        if (null != item.getPublisher()) {
            publisherTv.setText(item.getPublisher());
        }
        return view;
    }
}
