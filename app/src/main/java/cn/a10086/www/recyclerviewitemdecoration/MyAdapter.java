package cn.a10086.www.recyclerviewitemdecoration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mine.Beans;

/**
 * @author
 * @time 2017/2/17  10:30
 * @desc ${TODD}
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {


    private Context context;
    private List<Beans> lists;


    public MyAdapter(Context context, List<Beans> lists) {
        this.context = context;
        this.lists = lists;
    }


//    创建一个视图
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_two, null));
    }

//    绑定数据
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //绑定holder
        MyHolder myholder = (MyHolder) holder;
//        绑定数据
        myholder.setDatas(position);


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }



//   创建holder
    class MyHolder extends RecyclerView.ViewHolder {
        //        holder 的写法
        private TextView mv_name;
        private TextView mv_dec;
        private TextView mv_date;
        private ImageView imageView;


        public MyHolder(View itemView) {
            super(itemView);
//            找到控件的位置
            mv_name = (TextView) itemView.findViewById(R.id.mv_name);
            mv_dec = (TextView) itemView.findViewById(R.id.mv_dec);
            mv_date = (TextView) itemView.findViewById(R.id.mv_date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }


        public void setDatas(int position) {
            Beans beans = lists.get(position);

            String name = beans.getName();
            mv_name.setText(name);

            String date = beans.getDate();
            mv_date.setText(date);

            String dec = beans.getDec();
            mv_dec.setText(dec);

            //注：当你发下图片无法打开是，做个字符串替换即可
            int imagUrl = beans.getImgId();
            imageView.setImageResource(imagUrl);



        }
    }
}
