package org.liyunkun.week9_4listvideo;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by liyunkun on 2016/10/27 0027.
 */
public class MyAdapter extends BaseAdapter{

    private List<VideoBean> list;
    private Context context;
    private LayoutInflater inflater;
    private int currentPosition=-1;//当前正在播放的视频
    private MediaPlayer player;

    public MyAdapter(List<VideoBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
        player=new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.lv_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        VideoBean bean = list.get(position);
        int height = bean.getHeight();
        int width = bean.getWidth();
        //给ImageView和SurfaceView重新定义宽和高
        ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
        lp.height=height;
        lp.width=width;
        holder.iv.requestLayout();
        //因为ImageView和SurfaceView的父容器一致，因此他们的LayoutParams一样
        holder.surfaceView.setLayoutParams(lp);

        holder.title.setText(bean.getTitle());
        Picasso.with(context).load(bean.getImageUrl()).into(holder.iv);

        //设置当播放的视频划出屏幕的时候，停止播放
        Object tag = holder.iv.getTag();
        if (tag != null) {
            Integer num = (Integer) tag;
            if (num == currentPosition && num != position) {
                player.stop();
                currentPosition=-1;
            }
        }

        holder.iv.setTag(position);

        //设置要播放的视频所在的item
        if (currentPosition == position) {
            holder.iv.setVisibility(View.INVISIBLE);
            holder.surfaceView.setVisibility(View.VISIBLE);

            player.reset();
            player.setDisplay(holder.surfaceView.getHolder());
            try {
                player.setDataSource(bean.getVideoUrl());
                player.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            holder.iv.setVisibility(View.VISIBLE);
            holder.surfaceView.setVisibility(View.INVISIBLE);
        }

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击图片为播放视频，将点击的item的position赋值给定义的position
                currentPosition= (int) v.getTag();
                //adapter刷新
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder{
        ImageView iv;
        SurfaceView surfaceView;
        TextView title;

        public ViewHolder(View itemView) {
            iv= (ImageView) itemView.findViewById(R.id.iv);
            surfaceView= (SurfaceView) itemView.findViewById(R.id.surface_view);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }
}
