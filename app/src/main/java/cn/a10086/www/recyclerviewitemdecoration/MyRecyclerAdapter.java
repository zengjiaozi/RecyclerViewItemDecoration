package cn.a10086.www.recyclerviewitemdecoration;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private final List<DataBean> comingslist;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;


    public MyRecyclerAdapter(Context mContext, List<DataBean> comingslist) {
        this.mContext = mContext;
        this.comingslist = comingslist;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.date_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder myholder = (MyViewHolder) holder;
        myholder.setData(position);
    }

    @Override
    public int getItemCount() {
        return comingslist.size();
    }

    class MyViewHolder extends ViewHolder {
    	
        private TextView mv_name;
        private TextView mv_dec;
        private TextView mv_date;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mv_name = (TextView) itemView.findViewById(R.id.mv_name);
            mv_dec = (TextView) itemView.findViewById(R.id.mv_dec);
            mv_date = (TextView) itemView.findViewById(R.id.mv_date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        public void setData(int position) {
        	DataBean coming = comingslist.get(position);

            String name = coming.getName();
            mv_name.setText(name);

            String date = coming.getDate();
            mv_date.setText(date);

            String dec = coming.getDec();
            mv_dec.setText(dec);

            //注：当你发下图片无法打开是，做个字符串替换即可
            int imagUrl = coming.getImgId();
            imageView.setImageResource(imagUrl);

        }
    }
}


