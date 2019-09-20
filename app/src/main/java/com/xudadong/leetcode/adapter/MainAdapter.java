package com.xudadong.leetcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xudadong.leetcode.DetailActivity;
import com.xudadong.leetcode.R;
import com.xudadong.leetcode.contract.Model;

import java.util.List;

/**
 * <title>
 * <p>
 * Created by didi on 2019-07-11.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private Context mContext;
    private List<Model> mModelList;

    public MainAdapter(Context context, List<Model> modelList) {
        this.mContext = context;
        this.mModelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Model model = mModelList.get(position);
        viewHolder.vText.setText(model.getTitle());
        viewHolder.vItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(DetailActivity.getDetailIntent(mContext, model));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        TextView vText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vItem = itemView.findViewById(R.id.item);
            vText = itemView.findViewById(R.id.text);
        }
    }
}
