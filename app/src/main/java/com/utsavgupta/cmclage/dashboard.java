package com.utsavgupta.cmclage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

public class dashboard
        extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener
{
    BlockPruebaAdapterAppointments adapter;
    private ArrayList<String> appointment_dates = new ArrayList();
    private ArrayList<String> appointment_id = new ArrayList();
    private ArrayList<String> appointment_times = new ArrayList();
    DatabaseReference appoitments;
    ImageButton back;
    private AlertDialog.Builder builder1;
    private ArrayList<String> clinics = new ArrayList();
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
                    if (patient_id.equalsIgnoreCase(arrayOfString[1]))
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
                else
                {
                   // Boolean localBoolean = Boolean.valueOf(ConnectivityReceiver.isConnected());
                    //if (localBoolean.booleanValue())
                   // {
                        read_latlong();
                        //read_status();
                   // }
                  //  else if (!localBoolean.booleanValue())
                    //{
                        username.setText((CharSequence)names.get(0));
                        p_id.setText(patient_id);
                        progressDialog.cancel();
                       // showSnack(false);
                        read_latlong();
                        BlockPruebaAdapterAppointments localBlockPruebaAdapterAppointments = new BlockPruebaAdapterAppointments(appointment_id, 
                                appointment_dates, appointment_times, names, clinics, locations, hospital_noa, invoice_nos,
                                latitudes, longitudes, statust);
                        adapter = localBlockPruebaAdapterAppointments;
                        recyclerview.setAdapter(adapter);
                    //}
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
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_dashboard);
        username = ((TextView)findViewById(R.id.username));
        p_id = ((TextView)findViewById(R.id.level));
        builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        swipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.simpleSwipeRefreshLayout));
        appoitments = FirebaseDatabase.getInstance().getReference("appointmentIdsx");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data , please wait.");
        progressDialog.show();
        recyclerview = ((RecyclerView)findViewById(R.id.recyclerView));
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(localLinearLayoutManager);
        patient_id = getSharedPreferences("patient_detail", 0).getString("patient_id", null);
        readData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            public void onRefresh()
            {
                readData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        back = ((ImageButton)findViewById(R.id.back));
        back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                finishAffinity();
                startActivity(new Intent(dashboard.this, nav_d.class));
            }
        });
    }

    public void onNetworkConnectionChanged(boolean paramBoolean) {}

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
                username.setText((names.get(0)));
                p_id.setText(patient_id);

                BlockPruebaAdapterAppointments localBlockPruebaAdapterAppointments = new BlockPruebaAdapterAppointments(appointment_id,
                        appointment_dates, appointment_times, names,
                        clinics, locations, hospital_noa, invoice_nos,
                        latitudes, longitudes, status);
                adapter = localBlockPruebaAdapterAppointments;
                recyclerview.setAdapter(adapter);
                progressDialog.cancel();
            }
        });
    }
}

/* Location:           C:\Users\Utsav Gupta\Downloads\debug\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes3_dex2jar.jar * Qualified Name:     com.utsavgupta.cmclage. * JD-Core Version:    0.7.0.1 */