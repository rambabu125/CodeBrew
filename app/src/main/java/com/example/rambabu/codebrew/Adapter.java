package com.example.rambabu.codebrew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rambabu on 6/16/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<Model> pressModels;

    public Adapter(Context context, List<Model> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }

    public void notifyAdapter(List<Model> pressModels) {
        this.pressModels = pressModels;
        notifyDataSetChanged();
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.row_press, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.populate(pressModels.get(position));
    }

    @Override
    public int getItemCount() {
        return pressModels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_post;
        private TextView tv_time, tv_like, tv_title, tv_body, tv_read_more;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_post = (ImageView) itemView.findViewById(R.id.iv_post);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_body = (TextView) itemView.findViewById(R.id.tv_body);
            tv_read_more = (TextView) itemView.findViewById(R.id.tv_read_more);
        }

        public void populate(Model post) {

            tv_title.setText(post.getFullname());

            Glide.with(context)
                    .load(post.getProfile_pic())
                    .centerCrop()
                    .crossFade()
                    .into(iv_post);

        }

    }
}