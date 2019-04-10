package com.gjn.easytool.dialoger.base;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjn.easytool.utils.ResourcesUtils;

/**
 * @author gjn
 * @time 2019/4/10 10:27
 */

public class ViewHolder {
    private View view;
    private SparseArray<View> views;

    private ViewHolder(View view) {
        this.view = view;
        views = new SparseArray<>();
    }

    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    public static ViewHolder create(Activity activity, int id, ViewGroup container) {
        return create(ResourcesUtils.getView(activity, id, container, false));
    }

    public View getView() {
        return view;
    }

    public <T extends View> T findViewById(int id) {
        View view = views.get(id);
        if (view == null) {
            view = this.view.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public void setTextViewText(int id, CharSequence text) {
        getTextView(id).setText(text);
    }

    public TextView getTextView(int id) {
        return findViewById(id);
    }

    public ImageView getImageView(int id) {
        return findViewById(id);
    }

    public void setIdOnClickListener(int id, View.OnClickListener l){
        findViewById(id).setOnClickListener(l);
    }

    public void setViewOnClickListener(View.OnClickListener l) {
        view.setOnClickListener(l);
    }

    public void setViewOnLongClickListener(View.OnLongClickListener l) {
        view.setOnLongClickListener(l);
    }
}
