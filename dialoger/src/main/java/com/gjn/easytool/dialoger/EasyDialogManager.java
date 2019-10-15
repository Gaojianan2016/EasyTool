package com.gjn.easytool.dialoger;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.gjn.easytool.dialoger.base.BaseDialogFragment;
import com.gjn.easytool.dialoger.base.IDialogCancelListener;
import com.gjn.easytool.dialoger.base.IDialogConvertView;
import com.gjn.easytool.dialoger.base.ViewHolder;
import com.gjn.easytool.utils.DisplayUtils;
import com.gjn.easytool.utils.ViewUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gjn
 * @time 2019/4/10 10:45
 */

public class EasyDialogManager {
    public final static int SMALL_SIZE = 2;
    public final static int MIDDLE_SIZE = 1;
    public final static int LARGE_SIZE = 0;
    private static final String TAG = "EasyDialogManager";
    private FragmentManager fm;
    private Activity activity;

    private CopyOnWriteArrayList<BaseDialogFragment> fragments;
    private IDialogCancelListener dialogCancelListener;

    private EasyDialogFragment smallLoadingDialog;
    private EasyDialogFragment middleLoadingDialog;
    private EasyDialogFragment largeLoadingDialog;

    public EasyDialogManager(AppCompatActivity activity) {
        fm = activity.getSupportFragmentManager();
        this.activity = activity;
        init();
    }

    public EasyDialogManager(Fragment fragment) {
        fm = fragment.getChildFragmentManager();
        activity = fragment.getActivity();
        init();
    }

    private void init() {
        fragments = new CopyOnWriteArrayList<>();

        dialogCancelListener = new IDialogCancelListener() {
            @Override
            public void onCancel(DialogFragment dialogFragment) {
                Log.i(TAG, "touch or back dissmiss: " + dialogFragment);
                fragments.remove(dialogFragment);
            }
        };

        smallLoadingDialog = getEasyLoadingDialog(SMALL_SIZE);
        middleLoadingDialog = getEasyLoadingDialog(MIDDLE_SIZE);
        largeLoadingDialog = getEasyLoadingDialog(LARGE_SIZE);
    }

    private void show(BaseDialogFragment dialogFragment) {
        Log.i(TAG, "show: " + dialogFragment);
        fragments.add(dialogFragment);
        dialogFragment.setOnDialogCancelListener(dialogCancelListener);
        dialogFragment.show(fm, dialogFragment.getTag());
    }

    private void dismiss(BaseDialogFragment dialogFragment) {
        Log.i(TAG, "dismiss: " + dialogFragment);
        fragments.remove(dialogFragment);
        dialogFragment.clearOnDialogCancelListeners();
        dialogFragment.dismissAllowingStateLoss();
    }

    public void addDialogCancelListener(IDialogCancelListener listener) {
        for (BaseDialogFragment fragment : fragments) {
            fragment.addOnDialogCancelListener(listener);
        }
    }

    public void showDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w(TAG, "dFragment is null.");
            return;
        }
        dismissDialog(dialogFragment);
        show(dialogFragment);
    }

    public void dismissDialog(BaseDialogFragment dialogFragment) {
        if (fragments.contains(dialogFragment)) {
            dismiss(dialogFragment);
        }
    }

    public void showOnlyDialog(BaseDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            Log.w(TAG, "dFragment is null.");
            return;
        }
        if (!fragments.contains(dialogFragment)) {
            show(dialogFragment);
        }
    }

    public void clearDialog() {
        for (BaseDialogFragment dialogFragment : fragments) {
            dismissDialog(dialogFragment);
        }
        fragments.clear();
    }

    public CopyOnWriteArrayList<BaseDialogFragment> getFragments() {
        return fragments;
    }

    public EasyDialogFragment showAndroidDialog(CharSequence title, CharSequence msg,
                                                DialogInterface.OnClickListener yesOnClickListener) {
        return showAndroidDialog(title, msg, "是", yesOnClickListener, "否");
    }

    public EasyDialogFragment showAndroidDialog(CharSequence title, CharSequence msg,
                                                CharSequence yes, DialogInterface.OnClickListener yesOnClickListener,
                                                CharSequence no) {
        return showAndroidDialog(title, msg, yes, yesOnClickListener, no, null);
    }

    public EasyDialogFragment showAndroidDialog(CharSequence title, CharSequence msg,
                                                CharSequence yes, DialogInterface.OnClickListener yesOnClickListener,
                                                CharSequence no, DialogInterface.OnClickListener noOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setMessage(msg).setPositiveButton(yes, yesOnClickListener)
                .setNegativeButton(no, noOnClickListener);
        EasyDialogFragment dialogFragment = EasyDialogFragment.newInstance(builder);
        showDialog(dialogFragment);
        return dialogFragment;
    }

    public EasyDialogFragment showEasyNormalDialog(CharSequence msg, View.OnClickListener yesOnClickListener) {
        return showEasyNormalDialog(msg, "确定", yesOnClickListener, "取消");
    }

    public EasyDialogFragment showEasyNormalDialog(CharSequence msg, CharSequence yes,
                                                   View.OnClickListener yesOnClickListener, CharSequence no) {
        return showEasyNormalDialog(msg, yes, yesOnClickListener, no, null);
    }

    public EasyDialogFragment showEasyNormalDialog(CharSequence msg, CharSequence yes, View.OnClickListener yesOnClickListener,
                                                   CharSequence no, View.OnClickListener noOnClickListener) {
        return showEasyNormalDialog(BaseDialogFragment.DIMAMOUT, false, msg, yes, yesOnClickListener, no, noOnClickListener);
    }

    public EasyDialogFragment showEasyOneBtnDialog(CharSequence msg, CharSequence yes, View.OnClickListener yesOnClickListener) {
        return showEasyNormalDialog(BaseDialogFragment.DIMAMOUT, true, msg, yes, yesOnClickListener, "", null);
    }

    public EasyDialogFragment showEasyNormalDialog(float dimAmout, final boolean isOneBtn, final CharSequence msg, final CharSequence yes,
                                                   final View.OnClickListener yesOnClickListener, final CharSequence no,
                                                   final View.OnClickListener noOnClickListener) {
        EasyDialogFragment dialogFragment = EasyDialogFragment.newInstance(R.layout.edf_dialog_normal, new IDialogConvertView() {
            @Override
            public void convertView(ViewHolder holder, final DialogFragment dialogFragment) {
                if (isOneBtn) {
                    holder.findViewById(R.id.edf_v_line_edb).setVisibility(View.GONE);
                    holder.findViewById(R.id.edf_tv_no_edb).setVisibility(View.GONE);
                } else {
                    holder.findViewById(R.id.edf_v_line_edb).setVisibility(View.VISIBLE);
                    holder.findViewById(R.id.edf_tv_no_edb).setVisibility(View.VISIBLE);
                }
                holder.setTextViewText(R.id.edf_tv_msg_edn, msg);
                holder.setTextViewText(R.id.edf_tv_yes_edb, yes);
                holder.setTextViewText(R.id.edf_tv_no_edb, no);
                holder.setIdOnClickListener(R.id.edf_tv_yes_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (yesOnClickListener != null) {
                            yesOnClickListener.onClick(v);
                        }
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
                holder.setIdOnClickListener(R.id.edf_tv_no_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noOnClickListener != null) {
                            noOnClickListener.onClick(v);
                        }
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
            }
        });
        dialogFragment.setDimAmout(dimAmout).setTransparent(true);
        showDialog(dialogFragment);
        return dialogFragment;
    }

    public EasyDialogFragment showEasyInputDialog(CharSequence msg, CharSequence yes, int maxSize,
                                                  EasyInputListener inputListener) {
        return showEasyInputDialog(BaseDialogFragment.DIMAMOUT, msg, yes, maxSize, inputListener, "取消", null);
    }

    public EasyDialogFragment showEasyInputDialog(float dimAmout, final CharSequence msg, final CharSequence yes, final int maxSize,
                                                  final EasyInputListener inputListener, final CharSequence no,
                                                  final View.OnClickListener noOnClickListener) {
        EasyDialogFragment dialogFragment = EasyDialogFragment.newInstance(R.layout.edf_dialog_input, new IDialogConvertView() {
            @Override
            public void convertView(ViewHolder holder, final DialogFragment dialogFragment) {
                holder.setTextViewText(R.id.edf_tv_msg_edi, msg);
                holder.setTextViewText(R.id.edf_tv_yes_edb, yes);
                holder.setTextViewText(R.id.edf_tv_no_edb, no);
                final TextView textView = holder.findViewById(R.id.edf_tv_size_edi);
                final EditText editText = holder.findViewById(R.id.edf_et_content_edi);
                textView.setText(String.valueOf(maxSize));
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > maxSize) {
                            textView.setText("超出数量 " + (s.length() - maxSize));
                            textView.setTextColor(Color.RED);
                        } else {
                            textView.setText(String.valueOf(maxSize - s.length()));
                            textView.setTextColor(Color.BLACK);
                        }
                    }
                });
                holder.setIdOnClickListener(R.id.edf_tv_yes_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputListener != null) {
                            inputListener.confirm(v, editText.getText(), maxSize);
                        }
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
                holder.setIdOnClickListener(R.id.edf_tv_no_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noOnClickListener != null) {
                            noOnClickListener.onClick(v);
                        }
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
            }
        });
        dialogFragment.setDimAmout(dimAmout).setTransparent(true);
        showDialog(dialogFragment);
        return dialogFragment;
    }

    public EasyDialogFragment showEasyDelayDialog(CharSequence msg, int time, CharSequence yes, View.OnClickListener yesOnClickListener) {
        return showEasyDelayDialog(false, msg, time, yes, yesOnClickListener);
    }

    public EasyDialogFragment showEasyDelayDialog(boolean isOneBtn, CharSequence msg, int time, CharSequence yes,
                                                  View.OnClickListener yesOnClickListener) {
        return showEasyDelayDialog(BaseDialogFragment.DIMAMOUT, isOneBtn, msg, time, yes, yesOnClickListener, "取消", null);
    }

    public EasyDialogFragment showEasyDelayDialog(float dimAmout, final boolean isOneBtn, final CharSequence msg, final int time, final CharSequence yes,
                                                  final View.OnClickListener yesOnClickListener, final CharSequence no,
                                                  final View.OnClickListener noOnClickListener) {
        EasyDialogFragment dialogFragment = EasyDialogFragment.newInstance(R.layout.edf_dialog_normal, new IDialogConvertView() {
            @Override
            public void convertView(ViewHolder holder, final DialogFragment dialogFragment) {
                if (isOneBtn) {
                    holder.findViewById(R.id.edf_v_line_edb).setVisibility(View.GONE);
                    holder.findViewById(R.id.edf_tv_no_edb).setVisibility(View.GONE);
                } else {
                    holder.findViewById(R.id.edf_v_line_edb).setVisibility(View.VISIBLE);
                    holder.findViewById(R.id.edf_tv_no_edb).setVisibility(View.VISIBLE);
                }
                holder.setTextViewText(R.id.edf_tv_msg_edn, msg);
                holder.setTextViewText(R.id.edf_tv_no_edb, no);
                final TextView textView = holder.findViewById(R.id.edf_tv_yes_edb);
                textView.setText(yes + "(" + time + ")");
                textView.setTextColor(Color.GRAY);
                textView.setEnabled(false);
                final CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textView.setText(yes + "(" + (millisUntilFinished / 1000) + ")");
                    }

                    @Override
                    public void onFinish() {
                        textView.setText(yes);
                        textView.setEnabled(true);
                        textView.setTextColor(Color.BLACK);
                    }
                };
                timer.start();
                holder.setIdOnClickListener(R.id.edf_tv_yes_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (yesOnClickListener != null) {
                            yesOnClickListener.onClick(v);
                        }
                        timer.cancel();
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
                holder.setIdOnClickListener(R.id.edf_tv_no_edb, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noOnClickListener != null) {
                            noOnClickListener.onClick(v);
                        }
                        timer.cancel();
                        dismiss((BaseDialogFragment) dialogFragment);
                    }
                });
            }
        });
        dialogFragment.setDimAmout(dimAmout).setTransparent(true);
        showDialog(dialogFragment);
        return dialogFragment;
    }

    public EasyDialogFragment showSmallLoadingDialog() {
        showOnlyDialog(smallLoadingDialog);
        return smallLoadingDialog;
    }

    public EasyDialogFragment showMiddleLoadingDialog() {
        showOnlyDialog(middleLoadingDialog);
        return middleLoadingDialog;
    }

    public EasyDialogFragment showLargeLoadingDialog() {
        showOnlyDialog(largeLoadingDialog);
        return largeLoadingDialog;
    }

    public EasyDialogFragment getSmallLoadingDialog() {
        return smallLoadingDialog;
    }

    public EasyDialogFragment getMiddleLoadingDialog() {
        return middleLoadingDialog;
    }

    public EasyDialogFragment getLargeLoadingDialog() {
        return largeLoadingDialog;
    }

    public void dismissSmallLoadingDialog() {
        dismissDialog(smallLoadingDialog);
    }

    public void dismissMiddleLoadingDialog() {
        dismissDialog(middleLoadingDialog);
    }

    public void dismissLargeLoadingDialog() {
        dismissDialog(largeLoadingDialog);
    }

    public EasyDialogFragment showEasyLoadingDialog(int size) {
        return showEasyLoadingDialog(BaseDialogFragment.DIMAMOUT, size);
    }

    public EasyDialogFragment showEasyLoadingDialog(float dimAmout, final int size) {
        EasyDialogFragment dialogFragment = getEasyLoadingDialog(dimAmout, size);
        showDialog(dialogFragment);
        return dialogFragment;
    }

    private EasyDialogFragment getEasyLoadingDialog(int size) {
        return getEasyLoadingDialog(BaseDialogFragment.DIMAMOUT, size);
    }

    private EasyDialogFragment getEasyLoadingDialog(float dimAmout, final int size) {
        int edge = ViewUtils.getScreenWidth(activity);
        switch (size) {
            case SMALL_SIZE:
                edge /= 7;
                break;
            case MIDDLE_SIZE:
                edge /= 5;
                break;
            case LARGE_SIZE:
            default:
                edge /= 3;
        }
        EasyDialogFragment dialogFragment = EasyDialogFragment.newInstance(R.layout.edf_dialog_loading, new IDialogConvertView() {
            @Override
            public void convertView(ViewHolder holder, DialogFragment dialogFragment) {
                ProgressBar progressBar = holder.findViewById(R.id.edf_pb_loading_edl);
                int padding;
                switch (size) {
                    case SMALL_SIZE:
                        padding = DisplayUtils.px2dp(activity, 5);
                        break;
                    case MIDDLE_SIZE:
                        padding = DisplayUtils.px2dp(activity, 20);
                        break;
                    case LARGE_SIZE:
                    default:
                        padding = DisplayUtils.px2dp(activity, 35);
                }
                progressBar.setPadding(padding, padding, padding, padding);
            }
        });
        dialogFragment.setWidth(edge)
                .setHeight(edge)
                .setDimAmout(dimAmout)
                .setCloseOnTouchOutside(false)
                .setTransparent(true);
        return dialogFragment;
    }

    public interface EasyInputListener {
        void confirm(View v, Editable msg, int maxSize);
    }
}
