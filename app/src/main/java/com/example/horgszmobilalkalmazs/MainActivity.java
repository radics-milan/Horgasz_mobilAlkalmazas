package com.example.horgszmobilalkalmazs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getName();
    DatabaseHal  databaseHal = new DatabaseHal(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageView menuImage = findViewById(R.id.menuImage);

        menuImage.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView titleText = findViewById(R.id.titleText);
        ImageView titleImage = findViewById(R.id.titleImage);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            titleText.setText(navDestination.getLabel());

            String label = Objects.requireNonNull(navDestination.getLabel()).toString();
            switch (label){
                case "Főoldal":
                    titleImage.setImageResource(R.drawable.ic_home);
                    break;
                case "Halak":
                    titleImage.setImageResource(R.drawable.ic_fish2);
                    break;
                case "Játék":
                    titleImage.setImageResource(R.drawable.ic_game);
                    break;
                case "Eredmények":
                    titleImage.setImageResource(R.drawable.ic_score);
                    break;
                case "Fogások":
                    titleImage.setImageResource(R.drawable.ic_fogas);
                    break;
                case "Információ":
                    titleImage.setImageResource(R.drawable.ic_info);
                    break;
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(internetReceiver, filter);
    }

    BroadcastReceiver internetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                boolean noConn = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if (!noConn){
                    //ha van internet, akkor megpróbáljuk letölteni az adatokat a felhőből
                    databaseHal.getAllDataFromFireStore();
                }
            }
        }
    };
}