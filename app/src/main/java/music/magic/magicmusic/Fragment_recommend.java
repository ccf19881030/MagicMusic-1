package music.magic.magicmusic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_recommend.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_recommend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_recommend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MusicWaterFallAdapter mAdapter;
    private ArrayList<MusicPersonCard> mlist = new ArrayList<MusicPersonCard>();
    private int width;
    private String related_url;
    private String list_url = "http://yinyue.kuwo.cn/yy/cate_167.htm";
    private ImageView huanjing;
    private ImageView liupai;
    private ImageView xinqing;
    private ImageView renqun;
    private ImageView yuyan;
    private ImageView shijian;
    private OnFragmentInteractionListener mListener;

    private Context context;

    public Fragment_recommend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_recommend.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_recommend newInstance(String param1, String param2) {
        Fragment_recommend fragment = new Fragment_recommend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        //TO DO

        context = view.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        regest(view);
        Get_url();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void regest(View view) {
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new MusicWaterFallAdapter(view.getContext(), mlist);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //禁用滑动事件
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //调用RecyclerView#addOnItemTouchListener方法能添加一个RecyclerView.OnItemTouchListener对象
        mRecyclerView.addOnItemTouchListener(new RecyclerViewClickListener(context, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                related_url = mlist.get(position).music_id;
                Intent intent = new Intent(context, MusicRelaxActivity.class);
                if (related_url.contains("cinfo")) {
                    intent.putExtra("rex_id", related_url);
                    startActivity(intent);
                } else if (related_url.contains("cate")) {
                    list_url = mlist.get(position).music_id;
                    mlist.clear();
                    mAdapter.notifyDataSetChanged();
                    Get_url();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

        huanjing = (ImageView) view.findViewById(R.id.huanjing);
        huanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_14.htm";
                Get_url();
            }
        });
        liupai = (ImageView) view.findViewById(R.id.liupai);
        liupai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_15.htm";
                Get_url();
            }
        });
        xinqing = (ImageView) view.findViewById(R.id.xinqing);
        xinqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_13.htm";
                Get_url();
            }
        });
        renqun = (ImageView) view.findViewById(R.id.renqun);
        renqun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_11.htm";
                Get_url();
            }
        });
        yuyan = (ImageView) view.findViewById(R.id.yuyan);
        yuyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_10.htm";
                Get_url();
            }
        });
        shijian = (ImageView) view.findViewById(R.id.shijian);
        shijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlist.clear();
                mAdapter.notifyDataSetChanged();
                list_url = "http://yinyue.kuwo.cn/yy/cate_176.htm";
                Get_url();
            }
        });
    }

    public void Get_url() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(list_url, new AsyncHttpResponseHandler() {
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
}
