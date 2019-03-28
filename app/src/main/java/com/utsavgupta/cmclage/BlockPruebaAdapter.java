package com.utsavgupta.cmclage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


public class BlockPruebaAdapter extends RecyclerView.Adapter<BlockPruebaAdapter.PruebaViewHolder> {
    //private Block_submit_tables_teachers bstt;
    private List<String> appointment_times = new ArrayList<>();
    private List<String> appointment_id = new ArrayList<>();
    private List<String> appointment_dates = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> clinics = new ArrayList<>();
    private List<String> locations = new ArrayList<>();
    private List<String> loc_ids = new ArrayList<>();
    private List<String> hospital_noa = new ArrayList<>();
    private List<String> invoice_nos = new ArrayList<>();
    private List<String> latitudes = new ArrayList<>();
    private List<String> status = new ArrayList<>();
    private List<String> longitudes = new ArrayList<>();String patient_id;
    AlertDialog.Builder builder1;
    String[] escrito;
    ProgressDialog dialog;
    private Context context;

    public BlockPruebaAdapter(List<String> appointment_id,List<String> appointment_date, List<String> appointment_time, List<String> names, List<String> clinics,
                              List<String> locations, List<String> latitude, List<String> hospitals, List<String> invoice,
                              List<String> longitude,List<String> status) {
    this.appointment_id=appointment_id;
    this.appointment_dates=appointment_date;
    this.appointment_times=appointment_time;
    this.clinics=clinics;
    this.locations=locations;
    this.latitudes=latitude;
    this.longitudes=longitude;
    this.hospital_noa=hospitals;
    this.invoice_nos=invoice;
    this.status=status;
    //this.datex=dates;
      //escrito = new String[lista.size()];
    }

    @Override
    public PruebaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pateint_record,parent,false);
        //this.bstt=new Block_submit_tables_teachers(v.getContext());
        context=v.getContext();
        return new PruebaViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PruebaViewHolder holder, int position) {
       // if(!(status.get(position).equals("0"))) {
            String date = appointment_dates.get(position);
            String time = appointment_times.get(position);
            String clinic = clinics.get(position);
            String hospital = hospital_noa.get(position);
            String lat = latitudes.get(position);
            String longs = longitudes.get(position);
            String invoice = invoice_nos.get(position);
            String loc = locations.get(position);
            String app_id = appointment_id.get(position);
            String stat = status.get(position);
            // String date=datex.get(position);

            holder.bindProducto(app_id, date, time, clinic, hospital, lat, longs, invoice, loc, stat);
        //}
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public String[] getEscrito() {
        return escrito;
    }

    public class PruebaViewHolder extends RecyclerView.ViewHolder{


        TextView invoice,hospital,date,time,clinic,location,appoint_id,statts;
        Button sync,navigate; ProgressBar pb;ImageView done;
        int accuracyt,slot_idt,taken_by_idt,school_idt,marked_by_id,marked_type=1;
        String remarkt,datett,marked_ont,datet;String message,status;
        double lattitudet,longitudet;
        String user_idt="",attendencestatust="";
        public PruebaViewHolder( View itemView) {
            super(itemView);
            invoice = (TextView) itemView.findViewById(R.id.invoice);
            //invoice.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            hospital = (TextView) itemView.findViewById(R.id.hospital);
            //hospital.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            appoint_id=itemView.findViewById(R.id.app_id);
            //appoint_id.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            date = (TextView) itemView.findViewById(R.id.date);
            //date.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            statts = (TextView) itemView.findViewById(R.id.stat);
            //statts.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            time = (TextView) itemView.findViewById(R.id.time);
            //time.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            clinic = (TextView) itemView.findViewById(R.id.clinic);
           // clinic.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            pb=itemView.findViewById(R.id.progressBar);
            location = (TextView) itemView.findViewById(R.id.location);
           // location.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            navigate=itemView.findViewById(R.id.navigate);
            done=itemView.findViewById(R.id.done);
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

        public void bindProducto(String app_id, String dates, String times, String clinics,
                                 String hospitals, final String lats, final String longs,
                                 final String invoices, final String loc, String stat)

        {
           invoice.setText(lats);
           hospital.setText(hospitals);
           clinic.setText(clinics);
           time.setText(times);
           date.setText(dates);
           location.setText(loc);
           appoint_id.setText(app_id);
           if(stat.equals("0"))
               {
               statts.setVisibility(View.GONE);
               done.setVisibility(View.VISIBLE);
               }
          else{
               done.setVisibility(View.GONE);
               statts.setText(stat );
           }
           navigate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {


                   final Dialog nagDialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                   nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   nagDialog.setCancelable(false);
                   nagDialog.setContentView(R.layout.popup);
                   Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);

                   ImageButton ivPreview = (ImageButton) nagDialog.findViewById(R.id.iv_preview_image);
                   TextView desc=(TextView) nagDialog.findViewById(R.id.place);
                   desc.setText(loc);

                   /* if(thisSpacecraft.getImageURL() != null && thisSpacecraft.getImageURL().length()>0)
                    {
                        Picasso.get().load(thisSpacecraft.getImageURL()).placeholder(R.drawable.ic_launcher_background).into(ivPreview);
                    }*/
                   //ivPreview.setBackgroundDrawable(dd);
                   btnClose.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View arg0) {

                           nagDialog.dismiss();
                       }
                   });
                   ivPreview.setOnClickListener(new View.OnClickListener() {
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
                           Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                           mapIntent.setPackage("com.google.android.apps.maps");
                           context.startActivity(mapIntent);
                           nagDialog.dismiss();
                       }
                   });
                   nagDialog.show();



               }
           });

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
