package com.sunxiaoyu.seewificode;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 *
 * Created by Sunxy on 2017/12/26.
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {

    private List<WifiModel> list;
    private ClipboardManager cm;

    public WifiListAdapter(List<WifiModel> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(list.get(position));
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView ssidView;
        private TextView passView;

        public ViewHolder(View itemView) {
            super(itemView);
            ssidView = itemView.findViewById(R.id.wifi_name);
            passView = itemView.findViewById(R.id.wifi_password);
        }

        public void bindView(final WifiModel wifiModel) {
            ssidView.setText("Ssid: " + wifiModel.getSsid());
            passView.setText("Pass: " + wifiModel.getPassWord());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cm == null){
                        cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    }
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(wifiModel.getPassWord());

                    Toast.makeText(view.getContext(),
                            wifiModel.getSsid()+"的密码已经复制到剪贴板了", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
