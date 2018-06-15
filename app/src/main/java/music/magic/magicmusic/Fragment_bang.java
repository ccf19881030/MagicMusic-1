package music.magic.magicmusic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static music.magic.magicmusic.zhengze.匹配下一个;
import static music.magic.magicmusic.zhengze.取子匹配文本;
import static music.magic.magicmusic.zhengze.开始匹配;

/**
 * Created by HongJay on 2016/8/11.
 */
public class Fragment_bang extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private ArrayList<PersonCard> list = new ArrayList<PersonCard>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment1, container, false);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WaterFallAdapter(view.getContext(), list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //调用RecyclerView#addOnItemTouchListener方法能添加一个RecyclerView.OnItemTouchListener对象
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(view.getContext(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(view.getContext(), MusicRelaxActivity.class);
                intent.putExtra("rex_id","bang:"+ list.get(position).ids);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

        Get_url();
        return view;
    }

    public void Get_url() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://m.kuwo.cn/newh5/bang/index", new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {

                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    parse_html(s);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<PersonCard> buildData(String pic, String tit, String son1, String son2, String son3, String id) {
        PersonCard p = new PersonCard();
        p.pic = pic;
        p.tit = tit;
        p.son1 = son1;
        p.son2 = son2;
        p.son3 = son3;
        p.ids = id;
        list.add(p);
        mAdapter.notifyItemInserted(list.size());
        //偶数和奇数的图片设置不同的高度，以到达错开的目的
        return list;
    }

    private void parse_html(String html) {
        zhengze.创建表达式("bid=(.*?)&pic[\\s\\S]*?<img data-src=\"(.*?)\"[\\s\\S]*?<h3>(.*?)</h3>[\\s\\S]*?chart_num\">1</span><span class=\"chart_songname\">(.*?)</span><span class=\"chart_artname\">(.*?)</span>[\\s\\S]*?chart_num\">2</span><span class=\"chart_songname\">(.*?)</span><span class=\"chart_artname\">(.*?)</span>[\\s\\S]*?chart_num\">3</span><span class=\"chart_songname\">(.*?)</span><span class=\"chart_artname\">(.*?)</span>",
                true, true);
        开始匹配(html);
        while (匹配下一个()) {
            Log.e("bid",取子匹配文本(1));
            buildData(取子匹配文本(2), 取子匹配文本(3), "1 " + 取子匹配文本(4).trim() + 取子匹配文本(5), "2 " + 取子匹配文本(6).trim() + 取子匹配文本(7), "3 " + 取子匹配文本(8).trim() + 取子匹配文本(9), 取子匹配文本(1));
        }
        mAdapter.notifyDataSetChanged();
    }
}
