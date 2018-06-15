package music.magic.magicmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HongJay on 2016/8/11.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"歌单", "排行", "分类","歌手","搜索"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment a = null;
        if (position == 0) {
            a = new Fragment_recommend();
        } else if (position == 1) {
            a = new Fragment_bang();
        } else if (position == 2) {
            a = new Fragment_type();
        } else if (position == 3) {
            a = new Fragment_singer();
        } else if (position == 4) {
            a = new SearchFragment();
        }
        return a;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
