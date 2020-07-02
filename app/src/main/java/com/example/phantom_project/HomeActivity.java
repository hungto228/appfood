package com.example.phantom_project;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phantom_project.Adapter.MenuViewAdapter;
import com.example.phantom_project.Common.Common;
import com.example.phantom_project.InterFace.ItemOnclickListener;
import com.example.phantom_project.Model.ModelCategory;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "home";
    FloatingActionButton fab;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    TextView mFullName;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<ModelCategory, MenuViewAdapter> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toobar);
        toolbar.setTitle("menu");
        setSupportActionBar(toolbar);
//firebase
        reference = FirebaseDatabase.getInstance().getReference("category");

        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mFullName = findViewById(R.id.tv_fullname);
        recyclerView_menu = findViewById(R.id.recycleview_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // will update
                //       startActivity(new Intent(HomeActivity.this,Cart.class));
            }
        });
        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        View headView = navigationView.getHeaderView(0);
        mFullName = headView.findViewById(R.id.tv_fullname);
        mFullName.setText(Common.CurrentUsers.getName());
        //menu
        recyclerView_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_menu.setLayoutManager(layoutManager);
        loadmenu();


    }


    private void loadmenu() {
        adapter = new FirebaseRecyclerAdapter<ModelCategory, MenuViewAdapter>(ModelCategory.class, R.layout.menu_list, MenuViewAdapter.class, reference) {


            @Override
            protected void populateViewHolder(MenuViewAdapter menuViewAdapter, ModelCategory modelCategory, int i) {
                menuViewAdapter.mMenuname.setText(modelCategory.getName());
                Glide.with(getBaseContext()).load(modelCategory.getImage()).into(menuViewAdapter.imgView);
                final ModelCategory clickItem = modelCategory;
                menuViewAdapter.setItemOnclickListener(new ItemOnclickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent foodList = new Intent(HomeActivity.this, FoodList.class);
//                        foodList.putExtra("catagoryId", adapter.getRef(position).getKey());
//                        startActivity(foodList);
                    }
                });
            }
        };
        recyclerView_menu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_menu:
                Toast.makeText(HomeActivity.this, "nav menu", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onNavigationItemSelected:1");
                break;
            case R.id.nav_cart:
                Log.d(TAG, "onNavigationItemSelected:2");
                break;
            case R.id.nav_order:
                break;
            case R.id.nav_logout:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_thame:
                break;
            case R.id.nav_language:
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}

