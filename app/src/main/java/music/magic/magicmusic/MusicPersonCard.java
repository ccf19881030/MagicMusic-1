package music.magic.magicmusic;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MusicPersonCard implements Serializable {
    public String music_pic; //明显头像的Url
    public String music_title;  //明显的名字
    public String music_detail;  //评分
    public int mimgHeight;  //头像图片的高度
    public String music_id;
}
