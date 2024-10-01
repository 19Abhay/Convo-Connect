package com.example.convoconnect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setSubtitle("Welcome");
        //  getSupportActionBar().setTitle("Convo Connect");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable
                (Color.parseColor("#E0B0FF")));
        getSupportActionBar().
                setTitle(Html.fromHtml("<font color ='#FFFFFF'>Convo Connect</font>"));
        // getSupportActionBar().setIcon(R.drawable.);

        //set adaptor to the ViewPager

        ViewPager2 viewpage1=findViewById(R.id.viewpagerhome);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

        viewpage1.setAdapter(adapter);
        //scroll tabs onscrolling of viewpager

        TabLayout tabs=findViewById(R.id.tablayouthome);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpage1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewpage1.setCurrentItem(tab.getPosition());
                    }
                }, 20);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

        viewpage1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                tabs.selectTab(tabs.getTabAt(position));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout)
        {
            //statement executes on click of Logout optionmenu
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);

        }
        else if (item.getItemId()==R.id.setting)
        {
            //  statement executes onclick of setting options
        }
        return super.onOptionsItemSelected(item);
    }
}

//Create an adapter for ViewPager

class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0)
            return new Chat();
        else if (position==1)
            return new Story();
        else
            return new ProfileFragment();


    }

    @Override
    public int getItemCount() {return 3; }

}
