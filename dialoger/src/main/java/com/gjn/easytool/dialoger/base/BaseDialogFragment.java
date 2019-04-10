package com.gjn.easytool.dialoger.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/4/10 10:18
 */

public abstract class BaseDialogFragment extends DialogFragment implements IDialogConvertView {
    private static final String TAG = "BaseDialogFragment";

    public static final int WRAP_CONTENT = ViewPager.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewPager.LayoutParams.MATCH_PARENT;

    public static final float DIMAMOUT = 0.7f;

    private boolean isCloseOnTouchOutside = true;
    private boolean isCanClose = true;
    private boolean isShowAnimations = false;
    private boolean isTransparent = false;
    private int windowAnimations = -1;
    private float dimAmout = DIMAMOUT;
    private int width = WRAP_CONTENT;
    private int height = WRAP_CONTENT;
    private int gravity = Gravity.CENTER;

    private IDialogDismissListener mDialogDismissListener;
    private List<IDialogDismissListener> mDialogDismissListeners;

    public abstract AlertDialog.Builder getDialogBuilder();

    public abstract int getLayoutId();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getDialogBuilder() != null) {
            return getDialogBuilder().create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialogBuilder() == null && getLayoutId() != View.NO_ID) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            ViewHolder holder = ViewHolder.create(getActivity(), getLayoutId(), container);
            convertView(holder, this);
            return holder.getView();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            init();
        } catch (Exception e) {
            Log.w(TAG, "onStart: ", e);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogDismissListener != null) {
            mDialogDismissListener.onDismiss(this);
        }
        if (mDialogDismissListeners != null) {
            for (IDialogDismissListener listener : mDialogDismissListeners) {
                listener.onDismiss(this);
            }
        }
    }

    private void init() {
        getDialog().setCanceledOnTouchOutside(isCloseOnTouchOutside);
        getDialog().setCancelable(isCanClose);
        Window window = getDialog().getWindow();
        if (window != null) {
            if (isTransparent) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            WindowManager.LayoutParams params = window.getAttributes();
            if (dimAmout != DIMAMOUT) {
                params.dimAmount = dimAmout;
            }
            if (windowAnimations != -1 && isShowAnimations) {
                params.windowAnimations = windowAnimations;
            }
            params.width = width;
            params.height = height;
            params.gravity = gravity;
            window.setAttributes(params);
        }
    }

    public void setOnDialogDismissListener(IDialogDismissListener listener) {
        mDialogDismissListener = listener;
    }

    public void addOnDialogDismissListener(IDialogDismissListener listener) {
        if (mDialogDismissListeners == null) {
            mDialogDismissListeners = new ArrayList<>();
        }
        mDialogDismissListeners.add(listener);
    }

    public void clearOnDialogDismissListeners() {
        if (mDialogDismissListeners != null) {
            mDialogDismissListeners.clear();
        }
    }

    public BaseDialogFragment setCloseOnTouchOutside(boolean closeOnTouchOutside) {
        isCloseOnTouchOutside = closeOnTouchOutside;
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(isCloseOnTouchOutside);
        }
        return this;
    }

    public BaseDialogFragment setCanClose(boolean cancelable) {
        isCanClose = cancelable;
        if (getDialog() != null) {
            getDialog().setCancelable(isCanClose);
        }
        return this;
    }

    public BaseDialogFragment setShowAnimations(boolean showAnimations) {
        isShowAnimations = showAnimations;
        return this;
    }

    public BaseDialogFragment setTransparent(boolean transparent) {
        isTransparent = transparent;
        return this;
    }

    public BaseDialogFragment setWindowAnimations(int windowAnimations) {
        this.windowAnimations = windowAnimations;
        if (this.windowAnimations != -1) {
            setShowAnimations(true);
        }
        return this;
    }

    public BaseDialogFragment setDimAmout(float dimAmout) {
        if (dimAmout < 0 || dimAmout > 1) {
            Log.w(TAG, "setDimAmout fail dimAmout is " + dimAmout);
            return this;
        }
        this.dimAmout = dimAmout;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }
}
