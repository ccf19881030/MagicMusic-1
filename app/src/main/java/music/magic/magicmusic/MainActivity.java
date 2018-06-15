package music.magic.magicmusic;

import android.app.Fragment;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity implements Fragment_recommend.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
