package com.xianlv.business.adapter.drop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianlv.business.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public ListDropDownAdapter(Context context, List<String> list,int checkItemPosition) {
        this.context = context;
        this.list = list;
        this.checkItemPosition = checkItemPosition;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.baseColor));
                viewHolder.iv_avatar.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.black_title_color));
                viewHolder.iv_avatar.setVisibility(View.GONE);
            }
        }
    }

    static class ViewHolder {
        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.iv_avatar)
        ImageView iv_avatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
