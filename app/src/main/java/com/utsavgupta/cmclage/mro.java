package com.utsavgupta.cmclage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utsavgupta.cmclage.Adapters.BlockPruebaAdapter_mro;
import com.utsavgupta.cmclage.Connectivity.ConnectivityReceiver;
import com.utsavgupta.cmclage.Connectivity.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class mro extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener , ConnectivityReceiver.ConnectivityReceiverListener{
    private ArrayList<String> appointment_times = new ArrayList<>();
    private ArrayList<String> appointment_dates = new ArrayList<>();
    private ArrayList<String> appointment_id = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> clinics = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> loc_ids = new ArrayList<>();
    private ArrayList<String> hospital_noa = new ArrayList<>();
    private ArrayList<String> invoice_nos = new ArrayList<>();
    private ArrayList<String> latitudes = new ArrayList<>();
    private ArrayList<String> longitudes = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private ArrayList<String> statust = new ArrayList<>();
    private ArrayList<String> mobile = new ArrayList<>();
    private ArrayList<String> pname = new ArrayList<>();
    String patient_id="";
    BlockPruebaAdapter_mro adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerview;
    private AlertDialog.Builder builder1;
    private SwipeRefreshLayout swipeRefreshLayout;DatabaseReference appoitments;
    private ProgressDialog progressDialog;
    TextView username,p_id,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mro);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);*/
       // username=(TextView) findViewById(R.id.username);
       // p_id=(TextView)findViewById(R.id.level);
        logout=(TextView)findViewById(R.id.logout);
        builder1=new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        appoitments= FirebaseDatabase.getInstance().getReference("appointmentIdsx");
        Splash.savePreferences("userlogin","logged_in_mro");
        progressDialog=new ProgressDialog(mro.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data , please wait.");
        progressDialog.show();

        recyclerview = (android.support.v7.widget.RecyclerView) findViewById(R.id.recyclerView);

        android.support.v7.widget.LinearLayoutManager llm = new android.support.v7.widget.LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(llm);
        // Intent intent=getIntent();
        // patient_id=intent.getStringExtra("patient_id");
        SharedPreferences prefs = getSharedPreferences("patient_detail", MODE_PRIVATE);
        patient_id = prefs.getString("patient_id", null);

        readData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder1.setTitle("Warning!");
                builder1.setMessage("You will lose your previous activities data. Do you still want to proceed?");

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // startActivity(new Intent(getContext(),BlockTabbedteacher.class));
                                dialog.cancel();

                                deleteCache(getApplicationContext());
                                deleteDatabase(getApplicationContext());
                                Splash.savePreferences("userlogin","");
                                deleteCache(getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), users.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                File sharedPreferenceFile = new File("/data/data/"+ getPackageName()+ "/shared_prefs/");
                                File[] listFiles = sharedPreferenceFile.listFiles();
                                for (File file : listFiles) {
                                    file.delete();
                                }
                                startActivity(intent);
                            }

                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
    private void read_latlong()
    {
        InputStream is = getResources().openRawResource(R.raw.cmc_lat_longs);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {

                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");
                for (int i = 0; i < loc_ids.size(); i++) {

                    if (tokens[1].equalsIgnoreCase(loc_ids.get(i))) {
                        latitudes.add(tokens[3]);
                        longitudes.add(tokens[4]);
                    }
                }



            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
    void read_status()
    {
        final StringBuffer stringBuffer=new StringBuffer();
        appoitments.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    for(int i=0;i<appointment_id.size();i++)
                    {
                        if(postSnapshot.getKey().equals(appointment_id.get(i))){
                            //stringBuffer.append(postSnapshot.getValue());
                            status.add(postSnapshot.getValue().toString());
                        }
                    }
                }
               // username.setText("MRO");
                //p_id.setText(patient_id);
                adapter=new BlockPruebaAdapter_mro(appointment_id,appointment_dates,appointment_times,names,clinics,
                        locations,hospital_noa,invoice_nos,latitudes,longitudes,status,mobile,pname);
                recyclerview.setAdapter(adapter);
                progressDialog.cancel();
                // Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readData() {
        appointment_id.clear();appointment_dates.clear();appointment_times.clear();names.clear();clinics.clear();
        locations.clear();loc_ids.clear();hospital_noa.clear();invoice_nos.clear();mobile.clear();pname.clear();
        InputStream is = getResources().openRawResource(R.raw.patients_record);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");
                // appoitments.child(tokens[2]).setValue(tokens[11]);
                //if(patient_id.equalsIgnoreCase(tokens[1])) {
                appointment_id.add(tokens[2]);
                appointment_dates.add(tokens[4]);
                appointment_times.add(tokens[3]);
                names.add(tokens[5]);
                pname.add(tokens[5]);
                clinics.add(tokens[6]);
                locations.add(tokens[7]);
                loc_ids.add(tokens[8]);
                hospital_noa.add(tokens[9]);
                invoice_nos.add(tokens[10]);
                statust.add(tokens[11]);
                mobile.add(tokens[12]);
            //}



                // Read the data and store it in the WellData POJO.
                /*WellData wellData = new WellData();
                wellData.setOwner(tokens[0]);
                wellData.setApi(tokens[1]);
                wellData.setLongitude(tokens[2]);
                wellData.setLatitude(tokens[3]);
                wellData.setProperty(tokens[4]);
                wellData.setWellName(tokens[5]);
                wellDataList.add(wellData);*/
            }
            Boolean isconnected = ConnectivityReceiver.isConnected();
            if (isconnected) {
                // progressDialog.cancel();
                // Intent intent = new Intent(LoginActivity.this, BLOCK_nav_d.class);
                //startActivity(intent);
                // OnLogin();
                // new JSONTask().execute("https://api-dev.penpencil.xyz/v1/oauth/token", mob_no, pawrd);
                read_latlong();
                read_status();

            } else if (isconnected == false) {
               // username.setText(names.get(0));
               // p_id.setText(patient_id);
                progressDialog.cancel();
                showSnack(false);

                read_latlong();

                adapter=new BlockPruebaAdapter_mro(appointment_id,appointment_dates,appointment_times,names,clinics,
                        locations,hospital_noa,invoice_nos,latitudes,longitudes,statust,mobile,pname);
                recyclerview.setAdapter(adapter);

            }


        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                           /* Intent intent = new Intent(nav_d.this,
                                    nav_d.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                        //startActivity(intent);

                        moveTaskToBack(true);
                        finish();
                        // System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
    public void deleteDatabase(Context context) {
        try {
            String destPath = getFilesDir().getPath();
            destPath = destPath.substring(0, destPath.lastIndexOf("/")) + "/databases";
            File dir = context.getDatabasePath(destPath);
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
