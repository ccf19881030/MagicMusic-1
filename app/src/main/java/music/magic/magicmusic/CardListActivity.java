package music.magic.magicmusic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CardListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MusicWaterFallAdapter mAdapter;
    private ArrayList<MusicPersonCard> mlist = new ArrayList<MusicPersonCard>();
    private int width;
    private String list_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DialogUtil.ShowLoadingDialog(this, "正在加载,喝口茶....    ");
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        list_url = getIntent().getStringExtra("rex_id");
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new MusicWaterFallAdapter(this, mlist);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //禁用滑动事件
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //调用RecyclerView#addOnItemTouchListener方法能添加一个RecyclerView.OnItemTouchListener对象
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CardListActivity.this, MusicRelaxActivity.class);
                intent.putExtra("rex_id", mlist.get(position).music_id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
        Get_url();
    }


    private List<MusicPersonCard> buildData(String names, String imgUrs, String rates, String ids) {

        MusicPersonCard p = new MusicPersonCard();
        p.music_pic = imgUrs;
        p.music_title = names;
        p.music_detail = rates;
        p.music_id = ids;
        p.mimgHeight = (width / 3 - 8); //偶数和奇数的图片设置不同的高度，以到达错开的目的
        mlist.add(p);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(mlist.size());
        // mAdapter.notifyItemChanged(list.size());
        return mlist;
    }

    public void Get_url() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(list_url, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    DialogUtil.HideLoadingDialog(CardListActivity.this);
                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    DialogUtil.HideLoadingDialog(CardListActivity.this);
                    String json = Text_Editor.取指定文本2(s, "singer_list clearfix", "<!-- 左边内容 end-->");
                    Log.e("json", json);
                    Creat_list(json);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Creat_list(String data) {
        String[] List = Text_Editor.取指定文本(data, "<li>", "</li>");

        String names;
        String pictures;
        String ids;
        String onlines;
        for (int i = 0; i < List.length; i++) {
            names = Text_Editor.取指定文本2(List[i], "m_name\">", "</a>");
            ids = "http://yinyue.kuwo.cn" + Text_Editor.取指定文本2(List[i], "<a href=\"", "\"");
            onlines = Text_Editor.取指定文本2(List[i], "m_number\">", "</p>");
            pictures = Text_Editor.取指定文本2(List[i], "lazy_src=\"", "\"");
            Log.e("names", names);
            buildData(names, pictures, onlines, ids);
        }
    }

}
