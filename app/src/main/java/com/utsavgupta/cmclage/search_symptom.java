package com.utsavgupta.cmclage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class search_symptom extends AppCompatActivity {
EditText text;
TextView desc;
    private ArrayList<String> department = new ArrayList<>();
    private ArrayList<String> symptoms = new ArrayList<>();
    private String status="";suggestion_adapter adapter;
    String t=""; private RecyclerView recyclerview;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_symptom);
        text = (EditText) findViewById(R.id.text);
       // desc = (TextView) findViewById(R.id.desc);
        recyclerview = (android.support.v7.widget.RecyclerView) findViewById(R.id.disease_recycler);

        android.support.v7.widget.LinearLayoutManager llm = new android.support.v7.widget.LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(llm);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                t=text.getText().toString().trim();
                //desc.setText(t);
                new JSONTask().execute(t);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                startActivity(new Intent(search_symptom.this, nav_d.class));
                // Animatoo.animateFade(getApplicationContext());
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String a=params[0];
            try {
                HttpClient httpClient = new DefaultHttpClient();
                // Creating HTTP Post
                HttpPost httpPost = new HttpPost("https://caritonsearchdisease.herokuapp.com/api");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                nameValuePair.add(new BasicNameValuePair("query", a));


                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } catch (UnsupportedEncodingException e) {
                    // writing error to Log
                    e.printStackTrace();
                }
                // Making HTTP Request
                StringBuffer str=new StringBuffer();
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    String responseBody = EntityUtils.toString(response.getEntity());
                  //  JSONObject obj = new JSONObject(responseBody);
                   status = responseBody;
                  //  JSONObject data = obj.getJSONObject("data");

                    return responseBody;

                    // writing response to log
                    // String abc = "124" + status_o + usersCount+website+roles;
                    //Toast.makeText(getApplicationContext(), abc + "1111", Toast.LENGTH_LONG).show();
                    //dialog.cancel();
                    //Log.d("Http Response:", status + "123+abc");
                } catch (ClientProtocolException e) {
                    // writing exception to log
                    e.printStackTrace();
                } catch (IOException e) {
                    // writing exception to log
                    e.printStackTrace();

                }

                return status;


            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {

                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsonArray= null;
            department.clear();
            symptoms.clear();
            try {
                jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    JSONObject source=jsonObject.getJSONObject("_source");
                    String name=source.getString("name");
                    String descripton=source.getString("description");
                    department.add(name);
                    symptoms.add(descripton);
                }
                adapter=new suggestion_adapter(department,symptoms);
                recyclerview.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

           // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
           // String str="Ear, Neck"+"\n"+"Neck"+"\n"+"Throat problems"+"\n"+"Allergic rhinitis"+"\n"+"Tonsillitis"+"\n";
            //desc.setText(str);

        }
    }
}
