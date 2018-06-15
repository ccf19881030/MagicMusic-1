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

public class MusicWaterFallAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MusicPersonCard> mData; //定义数据源



    //定义构造方法，默认传入上下文和数据源
    public MusicWaterFallAdapter(Context context, List<MusicPersonCard> data) {
        mContext = context;
        mData = data;
    }

    @Override  //将ItemView渲染进来，创建ViewHolder
    public MusicWaterFallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music, null);
        return new MusicWaterFallAdapter.MyViewHolder(view);
    }

    @Override  //将数据源的数据绑定到相应控件上
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MusicWaterFallAdapter.MyViewHolder holder3 = (MusicWaterFallAdapter.MyViewHolder) holder;
        MusicPersonCard personCard = mData.get(position);
        Uri uri = Uri.parse(personCard.music_pic);
        holder3.muserAvatar.setImageURI(uri);
        holder3.muserAvatar.getLayoutParams().height = personCard.mimgHeight;
        holder3.muserName.setText(personCard.music_title);
        holder3.mrate.setText(personCard.music_detail);
        holder3.muser_id.setText(personCard.music_id);

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
        public SimpleDraweeView muserAvatar;
        public TextView muserName;
        public TextView mrate;
        public TextView muser_id;
        public Button music_delet;
        public MyViewHolder(View itemView) {
            super(itemView);
            muserAvatar = (SimpleDraweeView) itemView.findViewById(R.id.music_pic);
            muserName = (TextView) itemView.findViewById(R.id.music_title);
            mrate=(TextView) itemView.findViewById(R.id.music_detail);
            muser_id=(TextView) itemView.findViewById(R.id.music_id);
        }
    }
}
