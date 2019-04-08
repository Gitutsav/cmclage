package com.utsavgupta.cmclage.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.utsavgupta.cmclage.R;

import java.util.ArrayList;
import java.util.List;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;*/


public class suggestion_adapter extends RecyclerView.Adapter<suggestion_adapter.PruebaViewHolder> {
    //private Block_submit_tables_teachers bstt;
    private List<String> department = new ArrayList<>();
    private List<String> symptoms = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> clinics = new ArrayList<>();
    private List<String> locations = new ArrayList<>();
    private List<String> loc_ids = new ArrayList<>();
    private List<String> hospital_noa = new ArrayList<>();
    private List<String> invoice_nos = new ArrayList<>();
    private List<String> latitudes = new ArrayList<>();
    private List<String> longitudes = new ArrayList<>();String patient_id;
    AlertDialog.Builder builder1;
    String[] escrito;
    ProgressDialog dialog;
    private Context context;

    public suggestion_adapter(List<String> department, List<String> symptoms) {
        this.department=department;
        this.symptoms=symptoms;

        //escrito = new String[lista.size()];
    }

    @Override
    public PruebaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggest_model,parent,false);
        //this.bstt=new Block_submit_tables_teachers(v.getContext());
        context=v.getContext();
        return new PruebaViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PruebaViewHolder holder, int position) {
        String depart=department.get(position);
        String symp=symptoms.get(position);



        holder.bindProducto(depart,symp);
    }

    @Override
    public int getItemCount() {
        return department.size();
    }

    public String[] getEscrito() {
        return escrito;
    }

    public class PruebaViewHolder extends RecyclerView.ViewHolder{


        TextView dept,sympt;
        Button sync,navigate; ProgressBar pb;
        int accuracyt,slot_idt,taken_by_idt,school_idt,marked_by_id,marked_type=1;
        String remarkt,datett,marked_ont,datet;String message,status;
        double lattitudet,longitudet;
        String user_idt="",attendencestatust="";
        public PruebaViewHolder( View itemView) {
            super(itemView);
            dept = (TextView) itemView.findViewById(R.id.department);
            sympt = (TextView) itemView.findViewById(R.id.symptoms);
            // date = (TextView) itemView.findViewById(R.id.date);
            // itv = (TextView) itemView.findViewById(R.id.cname);
            // time = (TextView) itemView.findViewById(R.id.time);
            //  clinic = (TextView) itemView.findViewById(R.id.clinic);
            ///   pb=itemView.findViewById(R.id.progressBar);
            // location = (TextView) itemView.findViewById(R.id.location);
            //  navigate=itemView.findViewById(R.id.navigate);
            //   dialog=new ProgressDialog(itemView.getContext());
  /*          sync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder1 = new AlertDialog.Builder(view.getContext());

                    builder1.setCancelable(false);
                    Boolean isconnected= ConnectivityReceiver.isConnected();
                    if(isconnected){
                        builder1.setTitle("Synced Successfully");
                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // startActivity(new Intent(getContext(),BlockTabbedteacher.class));
                                        dialog.cancel();
                                        sync.setClickable(false);
                                        sync.setText("Done");
                                        sync.setBackgroundColor(44);


                                    }
                                });
                    }
                    else
                        {
                        builder1.setTitle("No Internet Connection");
                        builder1.setMessage("Do you want to update attendance via mobile network?");

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // startActivity(new Intent(getContext(),BlockTabbedteacher.class));
                                    dialog.cancel();
                                    sync.setClickable(false);
                                    sync.setText("Done");
                                    sync.setBackgroundColor(44);


                                }
                            });

       builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
}
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });*/
        }

        public void bindProducto(String departments, String symptoms){



            dept.setText(departments);
            sympt.setText(symptoms);

           /*navigate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                           double latitudes = Double.parseDouble(invoices);
                           double longitudes = Double.parseDouble(longs);
                           String label = "" + latitudes + "," + longitudes;
                           String uriBegin = "geo:" + latitudes + "," + longitudes;
                           String query = latitudes + "," + longitudes + "(" + label + ")";
                           String encodedQuery = Uri.encode(query);
                           String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                           Uri uri = Uri.parse(uriString);
                           Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                           context.startActivity(mapIntent);

               }
           });*/

            /*if(Integer.parseInt(i)==53){
                pb.setProgress(50);
                ts2.setText("Started");
                ts3.setText(" ");
            }
            else if(Integer.parseInt(i)==102){
                pb.setProgress(100);
                ts2.setText(" ");
                ts3.setText("Completed");
              //  ts2.setText(String.valueOf(100));
            }*/


        }

    }
}
