package com.gjn.easytool.ui;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.base.BaseMvpActivity;
import com.gjn.easytool.utils.QRUtils;
import com.gjn.easytool.utils.ViewUtils;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

/**
 * @author gjn
 * @time 2019/4/15 16:54
 */

public class QrcodeActivity extends BaseMvpActivity{

    ImageView imageView;
    EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
    }

    @Override
    protected void initData() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qr = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(qr)) {
                        showToast("字符串为空，无法生成");
                        return;
                    }
                    Bitmap bitmap = QRUtils.str2bitmap(qr);
                    imageView.setImageBitmap(bitmap);
                    editText.setText("");
                    showToast("生成成功");
                } catch (WriterException e) {
                    showToast("生成失败" + e.getMessage());
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qr = QRUtils.bitmap2str(ViewUtils.getImageViewBitmap(imageView));
                    editText.setText(qr);
                    showToast("解析成功");
                } catch (FormatException | ChecksumException | NotFoundException e) {
                    showToast("解析失败" + e.getMessage());
                }
            }
        });
    }
}
