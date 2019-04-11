package com.wildcardenter.myfab.schoolbuslocation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wildcardenter.myfab.schoolbuslocation.R;
import com.wildcardenter.myfab.schoolbuslocation.activities.MapActivity;
import com.wildcardenter.myfab.schoolbuslocation.models.UserDetail;

import java.util.List;

public class SharedLocationAdapter extends RecyclerView.Adapter<SharedLocationAdapter.SharedLocationViewHolder> {

    private Context context;
    private List<UserDetail> userDetails;

    public SharedLocationAdapter(Context context, List<UserDetail> userDetails) {
        this.context = context;
        this.userDetails = userDetails;
    }

    @NonNull
    @Override
    public SharedLocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new SharedLocationViewHolder(LayoutInflater.from(context).inflate(R.layout.driver_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SharedLocationViewHolder sharedLocationViewHolder, int i) {
        UserDetail currentItem = userDetails.get(i);
        Picasso.with(context).load(currentItem.getImgUrl()).into(sharedLocationViewHolder.driver_image);
        sharedLocationViewHolder.driver_name.setText(currentItem.getName());
        sharedLocationViewHolder.bus_no.setText(currentItem.getBusNo());
        sharedLocationViewHolder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("uid", currentItem.getId());
            intent.putExtra("busNo",currentItem.getBusNo());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    public class SharedLocationViewHolder extends RecyclerView.ViewHolder {
        private ImageView driver_image;
        private TextView driver_name;
        private TextView bus_no;
        private ConstraintLayout container;

        public SharedLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            driver_image = itemView.findViewById(R.id.Item_Driver_Image);
            driver_name = itemView.findViewById(R.id.Item_Driver_Name);
            bus_no = itemView.findViewById(R.id.Item_Bus_Number);
            container = itemView.findViewById(R.id.Item_container);
        }
    }
}
