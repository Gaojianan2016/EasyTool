package com.gjn.easytool.dialoger.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2019/4/10 10:18
 */

public abstract class BaseDialogFragment extends DialogFragment implements IDialogConvertView,
        IDialogDataBinding {
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

    private IDialogCancelListener mDialogCancelListener;
    private List<IDialogCancelListener> mDialogCancelListeners;

    public abstract AlertDialog.Builder getDialogBuilder();

    public abstract int getLayoutId();

    public abstract int getDataLayoutId();

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
        if (getDialogBuilder() == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getLayoutId() != View.NO_ID) {
                ViewHolder holder = ViewHolder.create(getActivity(), getLayoutId(), container);
                convertView(holder, this);
                return holder.getView();
            }
            if (getDataLayoutId() != View.NO_ID) {
                DataBindingHolder holder = DataBindingHolder.create(getActivity(), getDataLayoutId(), container);
                convertView(holder, this);
                return holder.getDataBinding().getRoot();
            }
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
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mDialogCancelListener != null) {
            mDialogCancelListener.onCancel(this);
        }
        if (mDialogCancelListeners != null) {
            for (IDialogCancelListener listener : mDialogCancelListeners) {
                listener.onCancel(this);
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

    public void setOnDialogCancelListener(IDialogCancelListener listener) {
        mDialogCancelListener = listener;
    }

    public void addOnDialogCancelListener(IDialogCancelListener listener) {
        if (mDialogCancelListeners == null) {
            mDialogCancelListeners = new ArrayList<>();
        }
        mDialogCancelListeners.add(listener);
    }

    public void clearOnDialogCancelListeners() {
        if (mDialogCancelListeners != null) {
            mDialogCancelListeners.clear();
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
