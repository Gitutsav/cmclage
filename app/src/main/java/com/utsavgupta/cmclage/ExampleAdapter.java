package com.utsavgupta.cmclage;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<xample_iteam> mExampleList;
    private Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView,nav;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            //mTextView2 = itemView.findViewById(R.id.textView2);
            nav=itemView.findViewById(R.id.nav);


        }
    }

    public ExampleAdapter(ArrayList<xample_iteam> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.xampleitem,
                parent, false);
        context=v.getContext();
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final xample_iteam currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getNam());
        holder.nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitudes = Double.parseDouble(currentItem.getText1());
                double longitudes = Double.parseDouble(currentItem.getText2());
                String label = "" + latitudes + "," + longitudes;
                String uriBegin = "geo:" + latitudes + "," + longitudes;
                String query = latitudes + "," + longitudes + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        //holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<xample_iteam> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}