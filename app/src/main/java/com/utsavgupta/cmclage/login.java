package com.utsavgupta.cmclage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;*/
public class login extends Activity {

    EditText mob, pwd;
    Button login,login_mro;
    ImageButton back;
    TextView forgot;
    AlertDialog.Builder builder1, builder2;
    private ProgressDialog progressdialog;
    private String message, status;
    private String mob_no = "", pawrd = "";
    String access_token = "", refresh_token = "", token_id = "";
    private String status_u, idg, firstName;
    Boolean is_student;
    String primaryNumber = null, username = null,
            emailg = null, created = null, _id = null, created_p = null, board = null,
            class_p = null, language = null, lastLoginAt = null, programId = null,
            dateOfBirth = null, address = null, primary = null, status_o = null,
            id_o = null, website = null, name = null, enrollmentType = null, id_o_p = null,
            created_o = null;
    String primarycontact_o = "";
    JSONArray exams = null, phones = null, roles = null, phones_o = null, rejectionComments = null;

    int wallet = 0, usersCount = 0;
    private String abc, _v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // checkConnection();
        mob = (EditText) findViewById(R.id.mob);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        login_mro = (Button) findViewById(R.id.mro);
        back = (ImageButton) findViewById(R.id.back);
        // forgot = (TextView) findViewById(R.id.forgot);
        builder2 = new AlertDialog.Builder(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                startActivity(new Intent(getApplicationContext(), users.class));
            }
        });
        login_mro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressdialog = new ProgressDialog(login.this);
                progressdialog.setCancelable(false);
                progressdialog.setMessage("Fetching data , please wait..");
                progressdialog.show();
                Log.d("Login Pressed", "In Login onCLick Listener");
                mob_no = mob.getText().toString().trim();
                pawrd = pwd.getText().toString().trim();
                // v.vibrate(100);//0.1 sec
                //Boolean isconnected = ConnectivityReceiver.isConnected();
                if (mob_no.isEmpty() || pawrd.isEmpty()) {
                    progressdialog.cancel();
                    builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setTitle("Enter Login Credentials");
                    //builder1.setMessage("Connect tO N");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    //checkConnection();
                    if (readDataMro(mob_no, pawrd)) {

                        progressdialog.cancel();
                        Intent intent = new Intent(login.this, mro.class);
                        SharedPreferences.Editor editor = getSharedPreferences("patient_detail", MODE_PRIVATE).edit();
                        editor.putString("patient_id", mob_no);
                        editor.apply();
                        //  intent.putExtra("patient_id",mob_no);
                        finishAffinity();
                        startActivity(intent);
                    }
                    // OnLogin();
                    // new JSONTask().execute("https://api-dev.penpencil.xyz/v1/oauth/token", mob_no, pawrd);


                    // } else if (isconnected == false) {
                    // dialog.cancel();
                    else {
                        builder1 = new AlertDialog.Builder(view.getContext());
                        builder1.setTitle("Invalid Patient Id or Password");
                        //builder1.setMessage("Connect tO N");
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // startActivity(new Intent(view.getContext(),BlockTabbedteacher.class));
                                        progressdialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

                progressdialog = new ProgressDialog(login.this);
                progressdialog.setCancelable(false);
                progressdialog.setMessage("Fetching data , please wait..");
                progressdialog.show();
                Log.d("Login Pressed", "In Login onCLick Listener");
                mob_no = mob.getText().toString().trim();
                pawrd = pwd.getText().toString().trim();
                // v.vibrate(100);//0.1 sec
                //Boolean isconnected = ConnectivityReceiver.isConnected();
                if (mob_no.isEmpty() || pawrd.isEmpty()) {
                    progressdialog.cancel();
                    builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setTitle("Enter Login Credentials");
                    //builder1.setMessage("Connect tO N");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    //checkConnection();
                    if (readData(mob_no, pawrd)) {

                        progressdialog.cancel();
                        Intent intent = new Intent(login.this, nav_d.class);
                        SharedPreferences.Editor editor = getSharedPreferences("patient_detail", MODE_PRIVATE).edit();
                        editor.putString("patient_id", mob_no);
                        editor.apply();
                      //  intent.putExtra("patient_id",mob_no);
                        finishAffinity();
                        startActivity(intent);
                    }
                    // OnLogin();
                    // new JSONTask().execute("https://api-dev.penpencil.xyz/v1/oauth/token", mob_no, pawrd);


                    // } else if (isconnected == false) {
                    // dialog.cancel();
                    else {
                        builder1 = new AlertDialog.Builder(view.getContext());
                        builder1.setTitle("Invalid Patient Id or Password");
                        //builder1.setMessage("Connect tO N");
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // startActivity(new Intent(view.getContext(),BlockTabbedteacher.class));
                                        progressdialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
                // }


            }


        });
    }

    private Boolean readData(String patient_id, String password) {
        InputStream is = getResources().openRawResource(R.raw.patients_list);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        int counter = 0;
        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");
                if ((patient_id.equalsIgnoreCase(tokens[1])) && (password.equalsIgnoreCase(tokens[2]))) {
                    ++counter;
                }

            }
            if (counter == 0) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private Boolean readDataMro(String patient_id, String password) {

            if ((patient_id.equalsIgnoreCase("mro124") && (password.equalsIgnoreCase("123456")))) {
                return true;
            } else {
                return false;
            }

    }
        }


