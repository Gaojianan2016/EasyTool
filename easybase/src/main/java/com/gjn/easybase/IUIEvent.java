package com.gjn.easybase;

import android.os.Bundle;

import com.gjn.easytool.dialoger.base.BaseDialogFragment;

/**
 * @author gjn
 * @time 2019/4/11 11:05
 */

public interface IUIEvent {
    void showToast(String msg);

    void showNext(Class cls);

    void showNext(Class cls, Bundle bundle);

    void toNext(Class cls);

    void toNext(Class cls, Bundle bundle);

    void showDialog(BaseDialogFragment dialogFragment);

    void dismissDialog(BaseDialogFragment dialogFragment);

    void showOnlyDialog(BaseDialogFragment dialogFragment);

    void dismissAll();
}
