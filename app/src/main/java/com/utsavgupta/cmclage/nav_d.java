package com.utsavgupta.cmclage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class nav_d
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener
{
    BlockPruebaAdapter adapter;
    private ArrayList<String> appointment_dates = new ArrayList();
    private ArrayList<String> appointment_datesx = new ArrayList();
    private ArrayList<String> appointment_id = new ArrayList();
    private ArrayList<String> appointment_idx = new ArrayList();
    private ArrayList<String> appointment_times = new ArrayList();
    private ArrayList<String> appointment_timesx = new ArrayList();
    DatabaseReference appoitments;
    private AlertDialog.Builder builder1;
    private ArrayList<String> clinics = new ArrayList();
    dashboard_adapter dashboard_adapter;
    private ArrayList<String> hospital_noa = new ArrayList();
    private ArrayList<String> invoice_nos = new ArrayList();
    private ArrayList<String> latitudes = new ArrayList();
    private ArrayList<String> loc_ids = new ArrayList();
    private ArrayList<String> locations = new ArrayList();
    private ArrayList<String> longitudes = new ArrayList();
    private ArrayList<String> names = new ArrayList();
    private ArrayList<String> namesx = new ArrayList();
    TextView p_id;
    String patient_id = "";
    private ProgressDialog progressDialog;
    private RecyclerView recyclerview;
    private RecyclerView recyclerviewx;
    private ArrayList<String> status = new ArrayList();
    private ArrayList<String> statust = new ArrayList();
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView username;
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_d);
        Toolbar localToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(localToolbar);
        getSupportActionBar().setElevation(0.0F);
        this.username = ((TextView)findViewById(R.id.username));
        this.p_id = ((TextView)findViewById(R.id.level));
        this.builder1 = new AlertDialog.Builder(this);
        this.builder1.setCancelable(false);
        this.swipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.simpleSwipeRefreshLayout));
        this.appoitments = FirebaseDatabase.getInstance().getReference("appointmentIdsx");
        Splash.savePreferences("userlogin", "logged_in");
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setMessage("Fetching Data, please wait.");
        this.progressDialog.show();
        this.recyclerviewx = ((RecyclerView)findViewById(R.id.recyclerViewx));
        LinearLayoutManager localLinearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        this.recyclerviewx.setLayoutManager(localLinearLayoutManager1);
        readDatax();
        this.recyclerview = ((RecyclerView)findViewById(R.id.recyclerView));
        LinearLayoutManager localLinearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        this.recyclerview.setLayoutManager(localLinearLayoutManager2);
        this.patient_id = getSharedPreferences("patient_detail", 0).getString("patient_id", null);
        readData();
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                nav_d.this.readData();
                nav_d.this.swipeRefreshLayout.setRefreshing(false);
            }
        });
        DrawerLayout localDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle localActionBarDrawerToggle = new ActionBarDrawerToggle(this, localDrawerLayout, localToolbar, 2131755188, 2131755187);
        localDrawerLayout.addDrawerListener(localActionBarDrawerToggle);
        localActionBarDrawerToggle.syncState();
        ((NavigationView)findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }


    public static boolean deleteDir(File paramFile)
    {
        if ((paramFile != null) && (paramFile.isDirectory()))
        {
            String[] arrayOfString = paramFile.list();
            for (int i = 0; i < arrayOfString.length; i++) {
                if (!deleteDir(new File(paramFile, arrayOfString[i]))) {
                    return false;
                }
            }
            return paramFile.delete();
        }
        if ((paramFile != null) && (paramFile.isFile())) {
            return paramFile.delete();
        }
        return false;
    }

    private void readData()
    {
        this.appointment_id.clear();
        this.appointment_dates.clear();
        this.appointment_times.clear();
        this.names.clear();
        this.clinics.clear();
        this.locations.clear();
        this.loc_ids.clear();
        this.hospital_noa.clear();
        this.invoice_nos.clear();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.patients_record), Charset.forName("UTF-8")));
        Object localObject = "";
        for (;;)
        {
            try
            {
                String str = localBufferedReader.readLine();
                localObject = str;
                if (str != null)
                {
                    String[] arrayOfString = ((String)localObject).split(",");
                    if (this.patient_id.equalsIgnoreCase(arrayOfString[1]))
                    {
                        this.appointment_id.add(arrayOfString[2]);
                        this.appointment_dates.add(arrayOfString[4]);
                        this.appointment_times.add(arrayOfString[3]);
                        this.names.add(arrayOfString[5]);
                        this.clinics.add(arrayOfString[6]);
                        this.locations.add(arrayOfString[7]);
                        this.loc_ids.add(arrayOfString[8]);
                        this.hospital_noa.add(arrayOfString[9]);
                        this.invoice_nos.add(arrayOfString[10]);
                        this.statust.add(arrayOfString[11]);
                    }
                }
                else
                {
                    Boolean localBoolean = Boolean.valueOf(ConnectivityReceiver.isConnected());
                    if (localBoolean.booleanValue())
                    {
                        read_latlong();
                        read_status();
                    }
                    else if (!localBoolean.booleanValue())
                    {
                        this.username.setText(names.get(0));
                        this.p_id.setText(this.patient_id);
                        this.progressDialog.cancel();
                        showSnack(false);
                        read_latlong();
                        BlockPruebaAdapter localBlockPruebaAdapter = new BlockPruebaAdapter(this.appointment_id, this.appointment_dates, this.appointment_times, this.names, this.clinics, this.locations, this.hospital_noa, this.invoice_nos, this.latitudes, this.longitudes, this.statust);
                        this.adapter = localBlockPruebaAdapter;
                        this.recyclerview.setAdapter(this.adapter);
                    }
                    return;
                }
            }
            catch (IOException localIOException)
            {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("Error");
                localStringBuilder.append((String)localObject);
                Log.e("MainActivity", localStringBuilder.toString(), localIOException);
                localIOException.printStackTrace();
                return;
            }
        }
    }

    private void readDatax()
    {
        this.appointment_id.clear();
        this.appointment_dates.clear();
        this.appointment_times.clear();
        this.names.clear();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.patients_record), Charset.forName("UTF-8")));
        Object localObject = "";
        int i = 1;
        for (;;)
        {
            try
            {
                String str = localBufferedReader.readLine();
                localObject = str;
                if (str != null)
                {
                    String[] arrayOfString = ((String)localObject).split(",");
                    if ((arrayOfString[6].equals("CARDIOLOGY")) && (arrayOfString[4].equals("22-03-2019")) && (!arrayOfString[11].equals("0")))
                    {
                        this.appointment_idx.add(arrayOfString[1]);
                        this.appointment_datesx.add(String.valueOf(i));
                        this.appointment_timesx.add(arrayOfString[3]);
                        this.namesx.add(arrayOfString[11]);
                        i++;
                    }
                }
                else
                {
                    this.dashboard_adapter = new dashboard_adapter(this.appointment_idx, this.appointment_datesx, this.appointment_timesx, this.namesx);
                    this.recyclerviewx.setAdapter(this.dashboard_adapter);
                    return;
                }
            }
            catch (IOException localIOException)
            {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("Error");
                localStringBuilder.append((String)localObject);
                Log.e("MainActivity", localStringBuilder.toString(), localIOException);
                localIOException.printStackTrace();
                return;
            }
        }
    }

    private void read_latlong()
    {
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.cmc_lat_longs), Charset.forName("UTF-8")));
        Object localObject = "";
        try
        {
            for (;;)
            {
                String str = localBufferedReader.readLine();
                localObject = str;
                if (str == null) {
                    break;
                }
                String[] arrayOfString = ((String)localObject).split(",");
                for (int i = 0; i < this.loc_ids.size(); i++) {
                    if (arrayOfString[1].equalsIgnoreCase((String)this.loc_ids.get(i)))
                    {
                        this.latitudes.add(arrayOfString[3]);
                        this.longitudes.add(arrayOfString[4]);
                    }
                }
            }
            return;
        }
        catch (IOException localIOException)
        {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Error");
            localStringBuilder.append((String)localObject);
            Log.e("MainActivity", localStringBuilder.toString(), localIOException);
            localIOException.printStackTrace();
        }
    }

    private void showSnack(boolean isConnected)
    {
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

    public void deleteCache(Context paramContext)
    {
        try
        {
            deleteDir(paramContext.getCacheDir());
            return;
        }
        catch (Exception localException) {}
    }

    public void deleteDatabase(Context paramContext)
    {
        try
        {
            String str = getFilesDir().getPath();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(str.substring(0, str.lastIndexOf("/")));
            localStringBuilder.append("/databases");
            deleteDir(paramContext.getDatabasePath(localStringBuilder.toString()));
            return;
        }
        catch (Exception localException) {}
    }

    public void onBackPressed()
    {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Closing Activity").setMessage("Are you sure you want to exit the app?").setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                nav_d.this.moveTaskToBack(true);
                nav_d.this.finish();
            }
        }).setNegativeButton("No", null).show();
    }



    public boolean onNavigationItemSelected(MenuItem paramMenuItem)
    {
        int i = paramMenuItem.getItemId();
        if (i == R.id.nav_buildings)
        {
            startActivity(new Intent(this, navigation.class));
        }
        else if (i == R.id.nav_symptoms)
        {
            startActivity(new Intent(this, suggestion.class));
        }
        else if (i == R.id.nav_search)
        {
            startActivity(new Intent(this, search_symptom.class));
        }
        else if (i == R.id.nav_about)
        {
            startActivity(new Intent(this, About.class));
        }
        else if (i == R.id.nav_appointments)
        {
            startActivity(new Intent(this, dashboard.class));
        }
        else if (i == R.id.nav_static_map)
        {
            startActivity(new Intent(this, MapsMarkerActivity.class));
        }
        else if (i == R.id.nav_logout)
        {
            this.builder1.setTitle("Warning!");
            this.builder1.setMessage("You will lose your previous activities data. Do you still want to proceed?");
            this.builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    paramAnonymousDialogInterface.cancel();
                    nav_d localnav_d1 = nav_d.this;
                    localnav_d1.deleteCache(localnav_d1.getApplicationContext());
                    nav_d localnav_d2 = nav_d.this;
                    localnav_d2.deleteDatabase(localnav_d2.getApplicationContext());
                    Splash.savePreferences("userlogin", "");
                    nav_d localnav_d3 = nav_d.this;
                    localnav_d3.deleteCache(localnav_d3.getApplicationContext());
                    Intent localIntent = new Intent(nav_d.this.getApplicationContext(), users.class);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("/data/data/");
                    localStringBuilder.append(nav_d.this.getPackageName());
                    localStringBuilder.append("/shared_prefs/");
                    File[] arrayOfFile = new File(localStringBuilder.toString()).listFiles();
                    int i = arrayOfFile.length;
                    for (int j = 0; j < i; j++) {
                        arrayOfFile[j].delete();
                    }
                    nav_d.this.startActivity(localIntent);
                }
            });
            this.builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    paramAnonymousDialogInterface.cancel();
                }
            });
            this.builder1.create().show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onNetworkConnectionChanged(boolean paramBoolean) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onResume()
    {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    void read_status()
    {
        new StringBuffer();
        this.appoitments.addValueEventListener(new ValueEventListener()
        {
            public void onCancelled(@NonNull DatabaseError paramAnonymousDatabaseError) {}

            public void onDataChange(@NonNull DataSnapshot paramAnonymousDataSnapshot)
            {
                nav_d.this.status.clear();
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext())
                {
                    DataSnapshot localDataSnapshot = (DataSnapshot)localIterator.next();
                    for (int i = 0; i < nav_d.this.appointment_id.size(); i++) {
                        if (localDataSnapshot.getKey().equals(nav_d.this.appointment_id.get(i))) {
                            status.add(localDataSnapshot.getValue().toString());
                        }
                    }
                }
                nav_d.this.username.setText((CharSequence)nav_d.this.names.get(0));
                nav_d.this.p_id.setText(nav_d.this.patient_id);
                nav_d localnav_d = nav_d.this;
                BlockPruebaAdapter localBlockPruebaAdapter = new BlockPruebaAdapter(localnav_d.appointment_id, nav_d.this.appointment_dates, nav_d.this.appointment_times, nav_d.this.names, nav_d.this.clinics, nav_d.this.locations, nav_d.this.hospital_noa, nav_d.this.invoice_nos, nav_d.this.latitudes, nav_d.this.longitudes, nav_d.this.status);
                localnav_d.adapter = localBlockPruebaAdapter;
                nav_d.this.recyclerview.setAdapter(nav_d.this.adapter);
                nav_d.this.progressDialog.cancel();
            }
        });
    }
}

/* Location:           C:\Users\Utsav Gupta\Downloads\debug\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes3_dex2jar.jar * Qualified Name:     com.utsavgupta.cmclage.nav_d * JD-Core Version:    0.7.0.1 */