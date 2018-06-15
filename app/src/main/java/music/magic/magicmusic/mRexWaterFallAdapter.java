package music.magic.magicmusic;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class mRexWaterFallAdapter extends RecyclerView.Adapter {
    private ButtonInterface buttonInterface;
    private Context mContext;
    private List<mRexCard> mData; //定义数据源
    private mRexWaterFallAdapter.OnItemClickListener mOnItemClickLitener;
    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(mRexWaterFallAdapter.OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    //定义构造方法，默认传入上下文和数据源
    public mRexWaterFallAdapter(Context context, List<mRexCard> data) {
        mContext = context;
        mData = data;
    }

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick(View view, int position);
    }
    @Override  //将ItemView渲染进来，创建ViewHolder
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_relax_music, null);
        return new MyViewHolder(view);
    }

    @Override  //将数据源的数据绑定到相应控件上
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder holder4 = (MyViewHolder) holder;
        mRexCard chooseCard = mData.get(position);
        Uri uri = Uri.parse(chooseCard.ChoosePic);
        holder4.chooAvatar.setImageURI(uri);
        holder4.chooAvatar.getLayoutParams().height = chooseCard.ChooseHeight;
        holder4.chooName.setText(chooseCard.ChooseName);
        holder4.choo_id.setText(chooseCard.Choose_id);
        holder4.choo_rate.setText(chooseCard.Chooserate);
        if(chooseCard.Choose_id.contains("FishCloud")||chooseCard.Choose_id.contains("files")){
            holder4.delet_button.setVisibility(View.VISIBLE);
        }
        holder4.delet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonInterface.onclick(v,position);
                }
            }
        });
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //定义自己的ViewHolder，将View的控件引用在成员变量上
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView chooAvatar;
        public TextView chooName;
        public TextView choo_id;
        public TextView choo_rate;
        public Button delet_button;
        public MyViewHolder(View itemView) {
            super(itemView);
            chooAvatar = (SimpleDraweeView) itemView.findViewById(R.id.rex_pic);
            chooName = (TextView) itemView.findViewById(R.id.rex_name);
            choo_id=(TextView) itemView.findViewById(R.id.rex_id);
            choo_rate=(TextView) itemView.findViewById(R.id.rex_singer);
            delet_button=(Button) itemView.findViewById(R.id.music_delet);
        }
    }
}
