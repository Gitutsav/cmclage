package com.utsavgupta.cmclage.navActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.utsavgupta.cmclage.Adapters.suggestion_adapter;
import com.utsavgupta.cmclage.R;
import com.utsavgupta.cmclage.nav_d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class suggestion extends AppCompatActivity {
    private ArrayList<String> department = new ArrayList<>();
    private ArrayList<String> symptoms = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> clinics = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> loc_ids = new ArrayList<>();
    private ArrayList<String> hospital_noa = new ArrayList<>();
    private ArrayList<String> invoice_nos = new ArrayList<>();
    private ArrayList<String> latitudes = new ArrayList<>();
    private ArrayList<String> longitudes = new ArrayList<>();String patient_id="";
    suggestion_adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerview;
    private ImageButton back;
    Boolean exit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                startActivity(new Intent(suggestion.this, nav_d.class));
                // Animatoo.animateFade(getApplicationContext());
            }
        });


        recyclerview = (android.support.v7.widget.RecyclerView) findViewById(R.id.disease_recycler);

        android.support.v7.widget.LinearLayoutManager llm = new android.support.v7.widget.LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(llm);


        readData();


    }
   /* @Override
    public void onBackPressed() {

      if(exit)
      {
          finishAffinity();
          finish();
      }
      else{
          exit=true;
          Toast.makeText(getApplicationContext(),"Press back again to exit!",Toast.LENGTH_LONG).show();
      }
    }*/
    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.disease_lists);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");

                    department.add(tokens[1]);
                    symptoms.add(tokens[2]);




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

            adapter=new suggestion_adapter(department,symptoms);
            recyclerview.setAdapter(adapter);
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }





}
