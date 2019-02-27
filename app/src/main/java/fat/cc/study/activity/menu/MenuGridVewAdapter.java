package fat.cc.study.activity.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fat.cc.study.R;
import fat.cc.study.bean.MenuBean;

/**
 * 网格菜单适配器
 */
public class MenuGridVewAdapter extends BaseAdapter {


    private List<MenuBean> menuBeanList;

    private LayoutInflater layoutInflater;

    public MenuGridVewAdapter(Context context,List<MenuBean> menuBeanList) {
        this.menuBeanList = menuBeanList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menuBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.menu_item_gv, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.menu_item_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MenuBean menuBean = menuBeanList.get(position);
        if (null != menuBean) {
            viewHolder.textView.setText(menuBean.getName());
            viewHolder.textView.setBackgroundResource(R.color.colorPrimary);
        }

        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }

}
