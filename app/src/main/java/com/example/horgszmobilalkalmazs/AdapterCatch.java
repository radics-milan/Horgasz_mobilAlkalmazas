package com.example.horgszmobilalkalmazs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCatch extends RecyclerView.Adapter<AdapterCatch.ViewHolder> {
    Context context;
    ArrayList<ClassCatch> catchArray;
    int gridNumber;

    public AdapterCatch(Context context, ArrayList<ClassCatch> catchArray, int gridNumber) {
        this.context = context;
        this.catchArray = catchArray;
        this.gridNumber = gridNumber;
    }

    @NonNull
    @Override
    public AdapterCatch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCatch.ViewHolder(LayoutInflater.from(context).inflate(R.layout.catch_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCatch.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        holder.catchImageView.startAnimation(animation);

        ClassCatch currentCatch = catchArray.get(position);
        holder.bindTo(currentCatch);
    }

    @Override
    public int getItemCount() {
        return catchArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catchImageView;
        TextView catchNameTextView;
        TextView catchDateTextView;
        TextView catchSizeTextView;
        TextView catchWeightTextView;
        TextView catchBaitTextView;
        TextView catchLocationTextView;
        Button catchDeleteButton;

        TextView dateTextView;
        TextView sizeTextView;
        TextView weightTextView;
        TextView baitTextView;
        TextView locationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catchImageView = itemView.findViewById(R.id.catchImageView);
            catchNameTextView = itemView.findViewById(R.id.catchNameTextView);
            catchDateTextView = itemView.findViewById(R.id.catchDateTextView);
            catchSizeTextView = itemView.findViewById(R.id.catchSizeTextView);
            catchWeightTextView = itemView.findViewById(R.id.catchWeightTextView);
            catchBaitTextView = itemView.findViewById(R.id.catchBaitTextView);
            catchLocationTextView = itemView.findViewById(R.id.catchLocationTextView);
            catchDeleteButton = itemView.findViewById(R.id.catchDeleteButton);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            sizeTextView = itemView.findViewById(R.id.sizeTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            baitTextView = itemView.findViewById(R.id.baitTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);

        }

        @SuppressLint("NotifyDataSetChanged")
        public void bindTo(@NonNull ClassCatch currentCatch) {
            switch (gridNumber) {
                case 1:
                    catchNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                    catchDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    catchSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    catchWeightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    catchBaitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    catchLocationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    catchDeleteButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    sizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    weightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    baitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    break;
                case 2:
                    catchNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    catchDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchWeightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchBaitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchLocationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchDeleteButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    sizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    weightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    baitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    break;
                case 3:
                    catchNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    catchDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    catchSizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    catchWeightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    catchBaitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    catchLocationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    catchDeleteButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);

                    dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    sizeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    weightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    baitTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    locationTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    break;
            }
            catchImageView.setImageURI(Uri.parse(currentCatch.getImage()));
            catchNameTextView.setText(currentCatch.getFishName());
            catchDateTextView.setText(currentCatch.getDateOfCatch());
            String catchSizeText;
            if (currentCatch.getSize() == 0){
                catchSizeText = "Ismeretlen";
            } else {
                catchSizeText = currentCatch.getSize() + " cm";
            }
            catchSizeTextView.setText(catchSizeText);

            String catchWeightText;
            if (currentCatch.getWeight() == 0){
                catchWeightText = "Ismeretlen";
            } else {
                catchWeightText = currentCatch.getWeight() + " kg";
            }
            catchWeightTextView.setText(catchWeightText);

            String catchBaitText;
            if (currentCatch.getBait() == null){
                catchBaitText= "Ismeretlen";
            } else {
                catchBaitText = currentCatch.getBait();
            }
            catchBaitTextView.setText(catchBaitText);

            String catchLocationText;
            if (currentCatch.getLocation() == null){
                catchLocationText= "Ismeretlen";
            } else {
                catchLocationText = currentCatch.getLocation();
            }
            catchLocationTextView.setText(catchLocationText);

            catchDeleteButton.setOnClickListener(o -> onDeleteCatch(currentCatch.getDateOfCatch()) );
        }

        private void onDeleteCatch(String date){
            new DatabaseCatch(context).delete(date);
            for (int i = 0; i < catchArray.size() ; i++) {
                if (catchArray.get(i).getDateOfCatch().equals(date)){
                    catchArray.remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            }
        }
    }
}
