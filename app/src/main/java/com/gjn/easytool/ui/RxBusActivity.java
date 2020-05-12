package com.gjn.easytool.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gjn.easytool.R;
import com.gjn.easytool.easymvp.MvpLog;
import com.gjn.easytool.easyrxevent.OnSubscribeCallback;
import com.gjn.easytool.easyrxevent.RxBus2;
import com.gjn.easytool.easyrxevent.RxMsg;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxBusActivity extends AppCompatActivity {

    Button btn;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        compositeDisposable = new CompositeDisposable();

        RxBus2.getRxMsgMainThread(new OnSubscribeCallback<RxMsg>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (compositeDisposable != null) {
                    compositeDisposable = new CompositeDisposable();
                }
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(RxMsg rxMsg) {
                MvpLog.e("触发rx2 " + rxMsg.code);
                Toast.makeText(RxBusActivity.this, "Open2", Toast.LENGTH_SHORT).show();
            }
        });

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus2.postRxMsg(101);
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}
