package com.gjn.easytool.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.MvpLog;
import com.gjn.easytool.easyrxevent.RxBus;
import com.gjn.easytool.easyrxevent.RxMsg;

import io.reactivex.functions.Consumer;

public class RxBusActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        RxBus.getRxMsgMainThread(new Consumer<RxMsg>() {
            @Override
            public void accept(RxMsg rxMsg) throws Exception {
                MvpLog.e("触发rx " + rxMsg.code);
                Toast.makeText(RxBusActivity.this, "Open", Toast.LENGTH_SHORT).show();
            }
        });

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.postRxMsg(100);
            }
        });

    }
}
