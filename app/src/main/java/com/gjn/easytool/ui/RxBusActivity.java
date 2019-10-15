package com.gjn.easytool.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gjn.easytool.R;
import com.jumi.easyrxevent.RxBus;
import com.jumi.easyrxevent.RxMsg;

import io.reactivex.functions.Consumer;

public class RxBusActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.postRxMsg(100);
            }
        });

        RxBus.getRxMsgMainThread(new Consumer<RxMsg>() {
            @Override
            public void accept(RxMsg rxMsg) throws Exception {
                Toast.makeText(RxBusActivity.this, "Open", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
