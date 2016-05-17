package com.example.luis.asqqui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String APP_ID = "18135C42-3B9A-D110-FF0F-510A30F62D00";
    public static final String SECRET_KEY = "5E00C64E-612F-1F25-FFFA-C1813C5EDE00";
    public static final String VERSION = "v1";

    TabLayout tabLayout;
    ViewPager viewPager;

    public static boolean USER_LOGGED = false;

    public static final int VEREADOR = 0;
    public static final int DEP_ESTADUAL = 1;
    public static final int PREFEITO = 2;
    public static final int DEP_FEDERAL = 3;
    public static final int GOVERNADOR = 4;
    public static final int SENADOR = 5;
    public static final int PRESIDENTE = 6;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);
        if (Backendless.UserService.loggedInUser() == "") {
            USER_LOGGED = false;
        } else {
            USER_LOGGED = true;
        }
        if (!USER_LOGGED) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);
//        EndpointsAsyncTask eat = new EndpointsAsyncTask();
//        eat.execute(new Pair<Context, String>(this, "Luis"));

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final ListView drawerList = (ListView)findViewById(R.id.main_side_drawer);

        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_SHORT).show();
                drawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(drawerList);
            }
        });

        ArrayList<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("bbbb");
        list.add("bbbb");
        list.add("bbbb");


        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, list));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragment(), "Vereador");
        adapter.addFragment(new TabFragment(), "Prefeito");
        adapter.addFragment(new TabFragment(), "Dep. Estadual");
        viewPager.setAdapter(adapter);
    }

//    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
//        private  MyPoliticianApi myApiService = null;
//        private Context context;
//
//        @Override
//        protected String doInBackground(Pair<Context, String>... params) {
//            if(myApiService == null) {  // Only do this once
//                MyPoliticianApi.Builder builder = new MyPoliticianApi.Builder(AndroidHttp.newCompatibleTransport(),
//                        new AndroidJsonFactory(), null)
//                        // options for running against local devappserver
//                        // - 10.0.2.2 is localhost's IP address in Android emulator
//                        // - turn off compression when running against local devappserver
//                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                            @Override
//                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                abstractGoogleClientRequest.setDisableGZipContent(true);
//                            }
//                        });
//                // end options for devappserver
//
//                myApiService = builder.build();
//            }
//
//            context = params[0].first;
//            String name = params[0].second;
//
//            try {
////                return myApiService.sayHi(name).execute().getData();
//                return myApiService.add((long)1, "Luis", "42", "Presidente").execute().getName();
//            } catch (IOException e) {
//                return e.getMessage();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//        }
//    }
}
