package com.a2r.a2rmaster.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.a2r.a2rmaster.Fragment.HomeFragment;
import com.a2r.a2rmaster.Fragment.ProductFragment;
import com.a2r.a2rmaster.Fragment.UsersFragment;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.Constants;

public class Home extends AppCompatActivity {
    private DrawerLayout drawer;
    private View navHeader;
    Toolbar toolbar;
    NavigationView navigationView;
    private static final String TAG_HOME = "Home";
    private static final String TAG_USERS = "Users";
    private static final String TAG_CATEGORY = "Category List";
    public static String CURRENT_TAG = TAG_HOME;
    public static int item_id;
    private Handler mHandler;
    TextView txtName,txtPhone;
    String user_name,user_phone;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtPhone = (TextView) navHeader.findViewById(R.id.website);
        user_name = Home.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_NAME, null);
        user_phone = Home.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_PHONE, null);
        txtName.setText(user_name);
        txtPhone.setText(user_phone);
        //imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        //imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        mHandler = new Handler();

        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
             //   getUserDetails();

                super.onDrawerOpened(drawerView);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        setUpNavigationView();
        if (savedInstanceState == null) {
           // navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        item_id = R.id.nav_home;
                        CURRENT_TAG = TAG_HOME;
                        break;
                   case R.id.a2r_users:
                        item_id = R.id.a2r_users;
                        CURRENT_TAG = TAG_USERS;
                        break;
                        case R.id.a2r_items:
                        item_id = R.id.a2r_items;
                        CURRENT_TAG = TAG_CATEGORY;
                        break;
                     /*case R.id.resident_preapprove:
                        item_id = R.id.resident_preapprove;
                        CURRENT_TAG = TAG_PREAPPROVE;
                        break;
                    case R.id.resident_preapproved_list:
                        item_id = R.id.resident_preapproved_list;
                        CURRENT_TAG = TAG_PREAPPROVELIST;
                        break;

                    case R.id.resident_service:
                        item_id = R.id.resident_service;
                        //  navItemIndex = 6;
                        CURRENT_TAG = TAG_SERVICE;
                        break;
                    case R.id.resident_support:
                        item_id = R.id.resident_support;
                        //  navItemIndex = 6;
                        CURRENT_TAG = TAG_SUPPORT;
                        break;*/
                    default:
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }
    private void loadHomeFragment() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            //toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    public void setToolbarTitle() {
        getSupportActionBar().setTitle("Home");
    }

    private void selectNavMenu() {
        navigationView.getMenu().findItem(item_id).setChecked(true);

    }
    public  void setText(String s){
        toolbar.setTitle(s);
    }
    private Fragment getHomeFragment() {
        switch (item_id) {
            case R.id.nav_home:
                // home
               HomeFragment homeFragment = new HomeFragment();
                toolbar.setTitle("Home");
                return homeFragment;
            case R.id.a2r_users:
                // Entry for visitors
                UsersFragment user_frag = new UsersFragment();
                toolbar.setTitle("Users");
                return user_frag;
                case R.id.a2r_items:
                // Entry for visitors
                ProductFragment product_frag = new ProductFragment();
                toolbar.setTitle("Category List");
                return product_frag;
            /* case R.id.resident_profile:
                // Entry for visitors
                ResidentProfile profile = new ResidentProfile();
                toolbar.setTitle("Update your phone number");
                return profile;
            case R.id.resident_preapprove:
                // Entry for visitors
                ResidentPreApprove preapprove = new ResidentPreApprove();
                toolbar.setTitle("Invite Visitors");
                return preapprove;
            case R.id.resident_preapproved_list:
                // Entry for visitors
                ResidentPreApproveList preapprovelist = new ResidentPreApproveList();
                toolbar.setTitle("Invited Visitors List");
                return preapprovelist;

            case R.id.resident_service:
                ResidentService serviceprovider = new ResidentService();
                toolbar.setTitle("My Service Providers");
                return serviceprovider;
            case R.id.resident_support:
                ResidentSupport residentSupport = new ResidentSupport();
                toolbar.setTitle("Support");
                return residentSupport;*/

            default:
                return new HomeFragment();
        }
    }
}
