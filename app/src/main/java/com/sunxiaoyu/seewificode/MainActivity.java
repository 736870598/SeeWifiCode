package com.sunxiaoyu.seewificode;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getWifiInfo();
    }

    private void initView(){
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getWifiInfo(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("获取wifi信息中，请稍等...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        Observable
                .just("")
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<WifiModel>>() {
                    @Override
                    public List<WifiModel> apply(String s) throws Exception {
                        return new WifiManage().Read();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WifiModel>>() {
                    @Override
                    public void accept(List<WifiModel> wifiModels) throws Exception {
                        dialogDismiss();
                        if (wifiModels.isEmpty()){

                        }else {
                            recycleView.setAdapter(new WifiListAdapter(wifiModels));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dialogDismiss();
                        Toast.makeText(MainActivity.this,
                                "获取不到，有可能是你的手机没有root吧！" + throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    }
                });
    }

    private void dialogDismiss(){
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDismiss();
    }
}
