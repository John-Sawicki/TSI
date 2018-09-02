package com.example.android.tsi.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.tsi.R;

import java.util.List;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.SystemAdapterVH> {
    private String[] systemNames = {"PAGA Sound Coverage", "Wire Gauge Calculator", "Power Load Calculator"," Task Tracker"};
    final private SystemOnClickInterface mSystemOnClickInterface;
    public SystemAdapter(SystemOnClickInterface clickInterface){
        mSystemOnClickInterface = clickInterface;
    }
    public interface SystemOnClickInterface{
        void onClick(int index);
    }
    @Override
    public SystemAdapter.SystemAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int systemLayout = R.layout.system_name;
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view = layoutInflater.inflate(systemLayout, parent, false);
        return new SystemAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SystemAdapterVH holder, int position) {
      String system = systemNames[position];
      holder.tv_system_name.setText(system);
    }

    @Override
    public int getItemCount() {
        return systemNames.length;
    }
    public class SystemAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tv_system_name;
        public SystemAdapterVH(View view){
            super(view);
            tv_system_name = view.findViewById(R.id.tv_system_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int systemClicked = getAdapterPosition();
            mSystemOnClickInterface.onClick(systemClicked);
        }
    }
}
