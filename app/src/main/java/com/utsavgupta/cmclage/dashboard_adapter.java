package com.utsavgupta.cmclage;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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


public class dashboard_adapter extends RecyclerView.Adapter<dashboard_adapter.PruebaViewHolder> {
    //private Block_submit_tables_teachers bstt;
    private List<String> appointment_times = new ArrayList<>();
    private List<String> appointment_id = new ArrayList<>();
    private List<String> appointment_dates = new ArrayList<>();
    private List<String> tokenx = new ArrayList<>();
    private List<String> exptime = new ArrayList<>();
    /*  private List<String> locations = new ArrayList<>();
      private List<String> loc_ids = new ArrayList<>();
      private List<String> hospital_noa = new ArrayList<>();
      private List<String> invoice_nos = new ArrayList<>();
      private List<String> latitudes = new ArrayList<>();
      private List<String> status = new ArrayList<>();
      private List<String> longitudes = new ArrayList<>();String patient_id;**/
    AlertDialog.Builder builder1;
    String[] escrito;
    ProgressDialog dialog;
    private Context context;private static final int NOTIFY_ME_ID=1337;

    public dashboard_adapter(List<String> appointment_id, List<String> appointment_date, List<String> appointment_time, List<String> tokenx
    ,List<String> exptime) {
    this.appointment_id=appointment_id;
    this.appointment_dates=appointment_date;
    this.appointment_times=appointment_time;
    this.tokenx=tokenx;
    this.exptime=exptime;
    //this.datex=dates;
      //escrito = new String[lista.size()];
    }

    @Override
    public PruebaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard,parent,false);
        //this.bstt=new Block_submit_tables_teachers(v.getContext());
        context=v.getContext();
        return new PruebaViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PruebaViewHolder holder, int position) {
         String date=appointment_dates.get(position);
         String time=appointment_times.get(position);

         String app_id=appointment_id.get(position);

         String token=tokenx.get(position);
         String exptimes=exptime.get(position);

         holder.bindProducto(date,app_id,time,token,exptimes);
    }

    @Override
    public int getItemCount() {
        return appointment_times.size();
    }

    public String[] getEscrito() {
        return escrito;
    }

    public class PruebaViewHolder extends RecyclerView.ViewHolder{


        TextView sno,pid,tim,token,extime;
        Button sync,navigate; ProgressBar pb;ImageView done;
        int accuracyt,slot_idt,taken_by_idt,school_idt,marked_by_id,marked_type=1;
        String remarkt,datett,marked_ont,datet;String message,status;
        double lattitudet,longitudet;
        String user_idt="",attendencestatust="";
        public PruebaViewHolder( View itemView) {
            super(itemView);
            sno = (TextView) itemView.findViewById(R.id.sn_no);
            pid = (TextView) itemView.findViewById(R.id.patient_id);
            tim = (TextView) itemView.findViewById(R.id.time);
            token = (TextView) itemView.findViewById(R.id.token);
            extime = (TextView) itemView.findViewById(R.id.exptime);
          //  pid.setTextSize(context.getResources().getDimension(R.dimen.textsize));
           // hospital = (TextView) itemView.findViewById(R.id.hospital);
           // hospital.setTextSize(context.getResources().getDimension(R.dimen.textsize));

           // navigate=itemView.findViewById(R.id.navigate);
            //done=itemView.findViewById(R.id.done);
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

        public void bindProducto(String date, String app_id, String times, String tokens, String exptimes) {
            //if (!times.equals(exptimes)){
               /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.cmc_image);
            mBuilder.setContentTitle("Notification Alert, Click Me!");
            mBuilder.setContentText("Hi, This is Android Notification Detail!");
            Intent resultIntent = new Intent(context, nav_d.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(nav_d.class);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
            mNotificationManager.notify(NOTIFY_ME_ID, mBuilder.build());

// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);*/
     //   }
            Animation anim = new AlphaAnimation(0.0f,1.0f );
            anim.setDuration(800); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);

            anim.setBackgroundColor(Color.RED);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            sno.setText(date);
            pid.setText(app_id);
            tim.setText(times);
            token.setText(tokens);
            extime.setText(exptimes);
            //token.startAnimation(anim);
            //manageBlinkEffect(token);
            if(token.getText().toString().equals("03")){
                manageBlinkEffect(extime);
                //token.startAnimation(anim);
            }
         /*  appoint_id.setText(app_id);
           if(stat.equals("0")){
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

                   if(thisSpacecraft.getImageURL() != null && thisSpacecraft.getImageURL().length()>0)
                    {
                        Picasso.get().load(thisSpacecraft.getImageURL()).placeholder(R.drawable.ic_launcher_background).into(ivPreview);
                    }
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
                           Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
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
    @SuppressLint("WrongConstant")
    private void manageBlinkEffect(TextView txt)
    {
        ObjectAnimator anim = ObjectAnimator.ofInt(txt,"backgroundColor", Color.WHITE,Color.RED,Color.WHITE);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
}
