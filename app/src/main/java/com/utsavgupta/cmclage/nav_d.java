package com.utsavgupta.cmclage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utsavgupta.cmclage.Adapters.BlockPruebaAdapter;
import com.utsavgupta.cmclage.Adapters.dashboard_adapter;
import com.utsavgupta.cmclage.Connectivity.ConnectivityReceiver;
import com.utsavgupta.cmclage.Connectivity.MyApplication;
import com.utsavgupta.cmclage.navActivities.MapsMarkerActivity;
import com.utsavgupta.cmclage.navActivities.About;
import com.utsavgupta.cmclage.navActivities.dashboard;
import com.utsavgupta.cmclage.navActivities.navigation;
import com.utsavgupta.cmclage.navActivities.search_symptom;
import com.utsavgupta.cmclage.navActivities.suggestion;

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
    private ArrayList<String> exptime = new ArrayList();
    private ArrayList<String> appointment_dates = new ArrayList();
    private ArrayList<String> cardiologyDatePatient = new ArrayList();
    private ArrayList<String> appointment_datesx = new ArrayList();
    private ArrayList<String> appointment_id = new ArrayList();
    private ArrayList<String> appointment_idx = new ArrayList();
    private ArrayList<String> appointment_times = new ArrayList();
    private ArrayList<String> appointment_timesx = new ArrayList();
    DatabaseReference appoitments;
    private AlertDialog.Builder builder1;
    private ArrayList<String> clinics = new ArrayList();
    com.utsavgupta.cmclage.Adapters.dashboard_adapter dashboard_adapter;
    private ArrayList<String> hospital_noa = new ArrayList();
    private ArrayList<String> invoice_nos = new ArrayList();
    private ArrayList<String> latitudes = new ArrayList();
    private ArrayList<String> loc_ids = new ArrayList();
    private ArrayList<String> locations = new ArrayList();
    private ArrayList<String> longitudes = new ArrayList();
    private ArrayList<String> names = new ArrayList();
    private ArrayList<String> tokenx = new ArrayList();
    TextView p_id,department_name,date;
    String patient_id = "";
    private ProgressDialog progressDialog;
    private RecyclerView recyclerview;
    private RecyclerView recyclerviewx;
    private ArrayList<String> status = new ArrayList();
    private ArrayList<String> statust = new ArrayList();
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView username;
    private int NOTIFY_ME_ID=1124;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_d);
        Toolbar localToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(localToolbar);
        getSupportActionBar().setElevation(0.0F);
        department_name=(TextView) findViewById(R.id.dept);
        date=(TextView)findViewById(R.id.date);
        username = ((TextView)findViewById(R.id.username));
        p_id = ((TextView)findViewById(R.id.level));
        builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        swipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.simpleSwipeRefreshLayout));
        appoitments = FirebaseDatabase.getInstance().getReference("appointmentIdsx");
        Splash.savePreferences("userlogin", "logged_in");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data, please wait.");
        progressDialog.show();
        recyclerviewx = ((RecyclerView)findViewById(R.id.recyclerViewx));
        LinearLayoutManager localLinearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerviewx.setLayoutManager(localLinearLayoutManager1);

        recyclerview = ((RecyclerView)findViewById(R.id.recyclerView));
        LinearLayoutManager localLinearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(localLinearLayoutManager2);
        patient_id = getSharedPreferences("patient_detail", 0).getString("patient_id", null);
        readData();
        readDatax();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                readDatax();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        DrawerLayout localDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle localActionBarDrawerToggle = new ActionBarDrawerToggle(this, localDrawerLayout,
                localToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        appointment_id.clear();
        appointment_dates.clear();
        appointment_times.clear();
        names.clear();
        clinics.clear();
        locations.clear();
        loc_ids.clear();
        hospital_noa.clear();
        invoice_nos.clear();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader
                (getResources().openRawResource(R.raw.patients_record), Charset.forName("UTF-8")));
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
                    if (patient_id.equalsIgnoreCase(arrayOfString[1]))
                    { if(!(arrayOfString[11].equals("0")))
                    {
                        appointment_id.add(arrayOfString[2]);
                        appointment_dates.add(arrayOfString[4]);
                        appointment_times.add(arrayOfString[3]);
                        names.add(arrayOfString[5]);
                        clinics.add(arrayOfString[6]);
                        locations.add(arrayOfString[7]);
                        loc_ids.add(arrayOfString[8]);
                        hospital_noa.add(arrayOfString[9]);
                        invoice_nos.add(arrayOfString[10]);
                        statust.add(arrayOfString[11]);
                    }
                    }
                }
                else
                {
                 /*  Boolean localBoolean = Boolean.valueOf(ConnectivityReceiver.isConnected());
                    if (localBoolean.booleanValue())
                    {*/
                        read_latlong();
                       // read_status();
                    //}
                  // else if (!localBoolean.booleanValue())
                    //{
                        username.setText(names.get(0));
                        p_id.setText(patient_id);

                       // showSnack(false);
                        read_latlong();
                        BlockPruebaAdapter localBlockPruebaAdapter = new BlockPruebaAdapter(appointment_id,
                                appointment_dates, appointment_times, names,
                                clinics, locations, hospital_noa, invoice_nos,
                                latitudes, longitudes, statust);
                        //showToast(appointment_dates.size()+"");
                        adapter = localBlockPruebaAdapter;
                        recyclerview.setAdapter(adapter);
                    //}
                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                    //editor.putString("name", "Elena");
                    editor.putString("token", statust.get(0));
                    editor.apply();
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
      department_name.setText(clinics.get(0));
      date.setText(appointment_dates.get(0));
        appointment_idx.clear();
        cardiologyDatePatient.clear();
        appointment_datesx.clear();
        appointment_timesx.clear();
        tokenx.clear();
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
                    if ( (arrayOfString[6].equals(clinics.get(0))) && (arrayOfString[4].equals(appointment_dates.get(0)))
                            && (!arrayOfString[11].equals("0")) && (arrayOfString[1].equals(patient_id)))
                    {
                        appointment_idx.add(arrayOfString[1]);
                        cardiologyDatePatient.add(arrayOfString[2]);
                        appointment_datesx.add(String.valueOf(i));
                        appointment_timesx.add(arrayOfString[3]);
                        tokenx.add(arrayOfString[11]);
                        i++;
                    }
                }
                 else
                {
                    Boolean isconnected=ConnectivityReceiver.isConnected();
                   if(!isconnected) {
                       dashboard_adapter = new dashboard_adapter(appointment_idx, appointment_datesx, appointment_timesx, tokenx,appointment_timesx);
                       recyclerviewx.setAdapter(dashboard_adapter);
                       progressDialog.cancel();
                   }
                   else
                   {
                       cardiologyFirebase();
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
private void cardiologyFirebase()
{
    //readDatax();

    exptime.clear();
    appoitments.addValueEventListener(new ValueEventListener()
    {
        public void onCancelled(@NonNull DatabaseError paramAnonymousDatabaseError) {}

        public void onDataChange(@NonNull DataSnapshot paramAnonymousDataSnapshot)
        {
            exptime.clear();
            Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
            while (localIterator.hasNext())
            {
                DataSnapshot localDataSnapshot = (DataSnapshot)localIterator.next();
                for (int i = 0; i < cardiologyDatePatient.size(); i++)
                {

                    if (localDataSnapshot.getKey().equals(cardiologyDatePatient.get(i)))
                    {
                            exptime.add(localDataSnapshot.getValue().toString());
                    }
                }
            }
            dashboard_adapter = new dashboard_adapter(appointment_idx, appointment_datesx,appointment_timesx, tokenx,exptime);
            recyclerviewx.setAdapter(dashboard_adapter);
            progressDialog.cancel();
        }
    });
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
                for (int i = 0; i < loc_ids.size(); i++) {
                    if (arrayOfString[1].equalsIgnoreCase((String)loc_ids.get(i)))
                    {
                        latitudes.add(arrayOfString[3]);
                        longitudes.add(arrayOfString[4]);
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
    void read_status()
    {
        new StringBuffer();
        appoitments.addValueEventListener(new ValueEventListener()
        {
            public void onCancelled(@NonNull DatabaseError paramAnonymousDatabaseError) {}

            public void onDataChange(@NonNull DataSnapshot paramAnonymousDataSnapshot)
            {
                status.clear();
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext())
                {
                    DataSnapshot localDataSnapshot = (DataSnapshot)localIterator.next();
                    for (int i = 0; i < appointment_id.size(); i++) {
                        if (localDataSnapshot.getKey().equals(appointment_id.get(i))) {
                            status.add(localDataSnapshot.getValue().toString());

                        }
                    }
                }
                username.setText((CharSequence)names.get(0));
                p_id.setText(patient_id);
                nav_d localnav_d = nav_d.this;
                BlockPruebaAdapter localBlockPruebaAdapter =
                        new BlockPruebaAdapter(localnav_d.appointment_id, appointment_dates,
                                appointment_times, names, clinics, locations, hospital_noa,
                                invoice_nos, latitudes, longitudes, status);
                localnav_d.adapter = localBlockPruebaAdapter;
                recyclerview.setAdapter(adapter);
                progressDialog.cancel();
            }
        });
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
                moveTaskToBack(true);
                finish();
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
            builder1.setTitle("Warning!");
            builder1.setMessage("You will lose your previous activities data. Do you still want to proceed?");
            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener()
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
                    Intent localIntent = new Intent(getApplicationContext(), users.class);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("/data/data/");
                    localStringBuilder.append(getPackageName());
                    localStringBuilder.append("/shared_prefs/");
                    File[] arrayOfFile = new File(localStringBuilder.toString()).listFiles();
                    int i = arrayOfFile.length;
                    for (int j = 0; j < i; j++) {
                        arrayOfFile[j].delete();
                    }
                    startActivity(localIntent);
                }
            });
            builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    paramAnonymousDialogInterface.cancel();
                }
            });
            builder1.create().show();
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
private void showToast(String s)
{
    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
}

}

/* Location:           C:\Users\Utsav Gupta\Downloads\debug\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes3_dex2jar.jar * Qualified Name:     com.utsavgupta.cmclage.nav_d * JD-Core Version:    0.7.0.1 */