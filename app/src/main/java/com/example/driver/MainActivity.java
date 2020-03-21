package com.example.driver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    RelativeLayout menu_layout;
    ImageButton menuButton;
    Animation animation;
    int click=0;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
     RelativeLayout.LayoutParams menuParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content,new mapsFragment()).commit();



        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        menu_layout=findViewById(R.id.menu_view);
        menuButton=findViewById(R.id.menu_btn);
        menuParams= (RelativeLayout.LayoutParams) menu_layout.getLayoutParams();
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click==0){
                 openMenu();
                }else {
                  closeMenu();
                }
                menu_layout.setAnimation(animation);
            }
        });


    }

    public void onClickItem(View view) {
        int itemIndex=view.getId();
        fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        if (itemIndex==R.id.nav_map)fragmentTransaction.replace(R.id.frame_content,new mapsFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_alert)fragmentTransaction.replace(R.id.frame_content,new AlertFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_search)fragmentTransaction.replace(R.id.frame_content,new SearchFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_settings)fragmentTransaction.replace(R.id.frame_content,new SettingsFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_help)fragmentTransaction.replace(R.id.frame_content,new HelpFragment()).addToBackStack( "pager" );
        if (itemIndex==R.id.nav_profile)fragmentTransaction.replace(R.id.frame_content,new ProfileFragment()).addToBackStack( "pager" );
        closeMenu();
        fragmentTransaction.commit();

    }

    public void openMenu() {
        menuButton.setImageResource(R.drawable.ic_close);
        menuButton.setBackground(getResources().getDrawable(R.drawable.bg_circl_red));
        menu_layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        click=1;

    }
    public void closeMenu() {
        menuButton.setImageResource(R.drawable.ic_sort_black_24dp);
        menuButton.setBackground(getResources().getDrawable(R.drawable.bg_circle_primary));
        menu_layout.setAnimation(animation);
        menu_layout.setLayoutParams(menuParams);
        click=0;
    }
}
