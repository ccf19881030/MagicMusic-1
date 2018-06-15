package music.magic.magicmusic;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static music.magic.magicmusic.zhengze.匹配下一个;
import static music.magic.magicmusic.zhengze.取子匹配文本;
import static music.magic.magicmusic.zhengze.开始匹配;

public class MusicRelaxActivity extends AppCompatActivity {
    private String rex_msc_id;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private mRexWaterFallAdapter mAdapter;
    private ArrayList<mRexCard> mlist = new ArrayList<mRexCard>();
    private MediaPlayer music_player;
    private SeekBar pro_for;
    private List<File> mVideoFileList;
    private String dl;
    private SimpleDraweeView img;
    private TextView time_text;
    private TextView play_name;
    private ImageView ctrl;
    private int Now = -1;
    private Timer timer;
    private Timer timer2;
    private boolean isLocal = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_music_relax);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            Bundle extras = getIntent().getExtras();
            rex_msc_id = extras.getString("rex_id");
        } catch (Exception e) {
            e.printStackTrace();
        }


        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new mRexWaterFallAdapter(this, mlist);
        mRecyclerView = (RecyclerView) findViewById(R.id.relax_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.buttonSetOnclick(new mRexWaterFallAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                try {
                    File file = new File(mlist.get(position).Choose_id);
                    if (file.delete()) {
                        mlist.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(MusicRelaxActivity.this, "删除成功..", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MusicRelaxActivity.this, "删除失败..", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAdapter.setOnItemClickLitener(new mRexWaterFallAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (isLocal) {
                    try {
                        Now = position;
                        music_player.reset();
                        music_player.setDataSource(mlist.get(position).Choose_id); // 设置数据源
                        music_player.prepare(); // prepare自动播放
                        play_name.setText(mlist.get(Now).ChooseName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dl = mlist.get(position).Choose_id;
                    Now = position;
                    Get_dl();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //something to do
            }
        });

        img = (SimpleDraweeView) findViewById(R.id.cycler_img);
        time_text = (TextView) findViewById(R.id.time_text);
        play_name = (TextView) findViewById(R.id.music_name);
        ctrl = (ImageView) findViewById(R.id.ctrl);
        ctrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (music_player.isPlaying()) {
                    music_player.pause();
                    img.clearAnimation();
                    ctrl.setImageResource(R.drawable.btn_radio_fm_play_n);
                } else {
                    music_player.start();
                    ctrl.setImageResource(R.drawable.btn_radio_fm_pause_n);
                    RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    LinearInterpolator lin = new LinearInterpolator();
                    rotate.setInterpolator(lin);
                    rotate.setDuration(3000);//设置动画持续周期
                    rotate.setRepeatCount(-1);//设置重复次数
                    rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                    rotate.setStartOffset(10);//执行前的等待时间
                    img.startAnimation(rotate);
                }
            }
        });


        pro_for = (SeekBar) findViewById(R.id.pro_for);
        pro_for.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress * music_player.getDuration()
                        / seekBar.getMax();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                music_player.seekTo(progress);
            }
        });
        Player();
        DialogUtil.ShowLoadingDialog(this, "正在加载,喝口茶....    ");
        if (rex_msc_id.contains("search")) {
            rex_msc_id.replace("search:", "");
            Get_search();
        } else if (rex_msc_id.contains("bang:")) {
            Get_bang(rex_msc_id.replace("bang:",""));
        } else {
            Get_music();
        }
    }


    public void Player() {
        try {
            music_player = new MediaPlayer();
            music_player.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            music_player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    try {
                        pro_for.setSecondaryProgress(percent);
                        int currentProgress = pro_for.getMax()
                                * music_player.getCurrentPosition() / music_player.getDuration();
                        Log.e(currentProgress + "% play", percent + " buffer");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            music_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    music_player.start();
                    RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    LinearInterpolator lin = new LinearInterpolator();
                    rotate.setInterpolator(lin);
                    rotate.setDuration(3000);//设置动画持续周期
                    rotate.setRepeatCount(-1);//设置重复次数
                    rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                    rotate.setStartOffset(10);//执行前的等待时间
                    img.startAnimation(rotate);
                    ctrl.setImageResource(R.drawable.btn_radio_fm_pause_n);

                }
            });
            music_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    img.clearAnimation();

                    if (Now + 1 < mlist.size() && music_player != null && !isLocal) {
                        Now = Now + 1;
                        dl = mlist.get(Now).Choose_id;
                        Get_dl();
                    } else if (Now != -1 && Now + 1 < mlist.size() && music_player != null && isLocal) {
                        try {
                            Now = Now + 1;
                            music_player.reset();
                            music_player.setDataSource(mlist.get(Now).Choose_id); // 设置数据源
                            Log.e("fillllllllllllle:", mlist.get(Now).Choose_id);
                            music_player.prepare(); // prepare自动播放
                            play_name.setText(mlist.get(Now).ChooseName);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ctrl.setImageResource(R.drawable.btn_radio_fm_play_n);
                }
            });
            music_player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // Toasty.error(MusicRelaxActivity.this, "error: " + Integer.toString(what), Toast.LENGTH_LONG).show();
                    ctrl.setImageResource(R.drawable.btn_radio_fm_play_n);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        timer = new Timer(true);
        timer.schedule(timerTask, 0, 1000);
    }

    private String generateTime(int time) {
        int totalSeconds = time;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d",
                minutes, seconds);
    }

    // 计时器
    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if (music_player == null)
                return;//&& seekBar.isPressed() == false
            if (music_player.isPlaying() && pro_for.isPressed() == false) {
                handler.sendEmptyMessage(0); // 发送消息
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = music_player.getCurrentPosition();
            int duration = music_player.getDuration();
            if (duration > 0) {
                //Log.e("position", Integer.toString(width * position / duration));
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                try {
                    long pos = pro_for.getMax() * position / duration;
                    pro_for.setProgress((int) pos);
                    time_text.setText(generateTime(position / 1000) + "/" + generateTime(duration / 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        ;
    };

    public void playUrl(String url, String name) {
        try {
            music_player.reset();
            music_player.setDataSource(url); // 设置数据源
            music_player.prepare(); // prepare自动播放
            play_name.setText(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (timer2 != null) {
                timer2.cancel();
                timer2 = null;
            }
            if (music_player.isPlaying()) {
                music_player.stop();//停止音频的播放
            }
            music_player.release();//释放资源
            music_player = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }


    public void Get_music() {

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(rex_msc_id, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String json = Text_Editor.取指定文本2(s, "var jsonm = ", ",\"rids\"") + "}";
                    Log.e("json", json);
                    parserJson(json);
                    if ("".equals(s)) {
                        Toast.makeText(MusicRelaxActivity.this, "网络错误，请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Get_search() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://player.kuwo.cn/webmusic/getsjplayinfo?flag=6&pn=1&pr=500&type=music&key=" + URLEncoder.encode(rex_msc_id, "utf-8"), new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(MusicRelaxActivity.this, "网络错误，请检查网络！", Toast.LENGTH_SHORT).show();
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                    parserJson2(s);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void parse_bang(String html) {
        zhengze.创建表达式("singTexUp2[\\s\\S]*?<p>(.*?)</p>[\\s\\S]*?<p class=\"singName\">(.*?)</p>[\\s\\S]*?commDownSong\\((.*?),event\\)",
                true, true);
        开始匹配(html);
        while (匹配下一个()) {
           buildData(取子匹配文本(1),"",取子匹配文本(2),取子匹配文本(3));
        }
        mAdapter.notifyDataSetChanged();
    }
    private void Get_bang(String bid){
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://m.kuwo.cn/newh5/bang/content?bid=" + bid, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                    Toast.makeText(MusicRelaxActivity.this, "网络错误，请检查网络！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    DialogUtil.HideLoadingDialog(MusicRelaxActivity.this);
                    parse_bang(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Get_dl() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://player.kuwo.cn/webmusic/st/getNewMuiseByRid?rid=MUSIC_" + dl, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(MusicRelaxActivity.this, "网络错误，请检查网络！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    String s = null;
                    try {
                        s = new String(responseBody, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String json;
                    String pic;
                    String name;
                    String mp3dl;
                    String mp3path;
                    pic = Text_Editor.取指定文本2(s, "<artist_pic240>", "</artist_pic240>");
                    name = Text_Editor.取指定文本2(s, "<name>", "</name>") + " - " + Text_Editor.取指定文本2(s, "<artist>", "</artist>");
                    mp3dl = Text_Editor.取指定文本2(s, "<mp3dl>", "</mp3dl>");
                    mp3path = Text_Editor.取指定文本2(s, "<mp3path>", "</mp3path>");
                    if ("".equals(mp3dl) || "".equals(mp3path)) {
                        json = "";
                    } else {
                        json = "http://" + mp3dl + "/resource/" + mp3path;
                    }
                    playUrl(json, name);
                    img.setImageURI(Uri.parse(pic));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String parserJson(String jsonStr) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray listArray = obj.getJSONArray("musiclist");//获取JSON数组
            String[] names = new String[listArray.length()];
            String[] pictures = new String[listArray.length()];
            String[] artist = new String[listArray.length()];
            String[] ids = new String[listArray.length()];
            String[] album = new String[listArray.length()];
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject List_item = listArray.getJSONObject(i);

                names[i] = List_item.getString("name");
                ids[i] = List_item.getString("musicrid");
                artist[i] = List_item.getString("artist");
                album[i] = List_item.getString("album");
                Log.i("network", "请求结果:" + names[i]);
                buildData(names[i], "http://p0.so.qhimgs1.com/bdr/_240_/t01832495825b19c460.jpg", artist[i] + " | " + album[i], ids[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String parserJson2(String jsonStr) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray listArray = obj.getJSONArray("list");//获取JSON数组
            String[] names = new String[listArray.length()];
            String[] pictures = new String[listArray.length()];
            String[] artist = new String[listArray.length()];
            String[] ids = new String[listArray.length()];
            String[] album = new String[listArray.length()];
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject List_item = listArray.getJSONObject(i);
                names[i] = List_item.getString("songName");
                ids[i] = List_item.getString("rid").replace("MUSIC_", "");
                artist[i] = List_item.getString("artist");
                album[i] = List_item.getString("album");
                Log.i("network", "请求结果:" + names[i]);
                buildData(names[i], "http://183.131.85.206:455/search.png", artist[i] + " | " + album[i], ids[i]);
            }

            // buildData(names,pictures,ids,onlines,NickNames);
        } catch (JSONException e) {
            //new Thread(networkTask1).start();//启动网络线程
            //Waringdialog("应用程序出错:"+e.toString());
            e.printStackTrace();
        }
        return result;
    }

    private List<mRexCard> buildData(String names, String imgUrs, String rates, String ids) {
        mRexCard p = new mRexCard();
        p.ChoosePic = imgUrs;
        p.ChooseName = names;
        p.Chooserate = rates;
        p.Choose_id = ids;
        p.ChooseHeight = 150; //偶数和奇数的图片设置不同的高度，以到达错开的目的
        mlist.add(p);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(mlist.size());
        // mAdapter.notifyItemChanged(list.size());
        return mlist;
    }


    /////////////////////////local music
    public void ReadBitMap(String path) {
        try {
            mVideoFileList = new ArrayList<File>();
            File f = new File(path);
            Log.e("file", f.getPath());
            List<File> fileList = getFile(f);
            Log.e("length", mVideoFileList.toString());

            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                Message msg = new Message();
                Bundle data = new Bundle();
                String FullStr = f.getPath() + "/" + file.getName();
                FullStr = FullStr.toLowerCase();
                try {
                    if (FullStr.endsWith(".mp3")) {
                        buildData(file.getName(), "http://183.131.85.206:455/search.png", formatFileSize(file.length()), f.getPath() + "/" + file.getName());
                    }
                } catch (Exception e) {
                    //                Waringdialog("应用程序出错:"+e.toString());
                }
                data.putString("Name", file.getName());
                data.putString("Size", formatFileSize(file.length()));
                data.putString("Truepath", f.getPath() + "/" + file.getName());
                msg.setData(data);
                handler.sendMessage(msg);
                data = null;
                Log.e("文件名字", file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<File> getFile(File file) {
        File[] fileArray = file.listFiles();
        for (File f : fileArray) {
            if (f.isFile()) {
                mVideoFileList.add(f);
            } else {
                getFile(f);
            }
        }
        Toast.makeText(MusicRelaxActivity.this, "共: " + Integer.toString(mVideoFileList.size()) + " 首歌曲..", Toast.LENGTH_LONG).show();
        return mVideoFileList;
    }

    public static String formatFileSize(long size) {
        if (size < 1024) {
            return String.format("%d B", size);
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0f);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", size / 1024.0f / 1024.0f);
        } else {
            return String.format("%.1f GB", size / 1024.0f / 1024.0f / 1024.0f);
        }
    }

}
