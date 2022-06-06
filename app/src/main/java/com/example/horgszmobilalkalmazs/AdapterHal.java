package com.example.horgszmobilalkalmazs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;

public class AdapterHal extends RecyclerView.Adapter<AdapterHal.ViewHolder> implements Filterable {
    Context context;
    ArrayList<ClassHal> originalFishArray;
    ArrayList<ClassHal> filteredFishArray;
    int gridNumber;

    public AdapterHal(Context context, ArrayList<ClassHal> fishArray, int gridNumber) {
        this.context = context;
        this.originalFishArray = fishArray;
        this.filteredFishArray = fishArray;
        this.gridNumber = gridNumber;
    }

    @NonNull
    @Override
    public AdapterHal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fish_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHal.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        holder.fishImageImageView.startAnimation(animation);

        ClassHal currentFish = filteredFishArray.get(position);
        holder.bindTo(currentFish);
    }

    @Override
    public int getItemCount() {
        return filteredFishArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView latinNameTextView;
        ImageView fishImageImageView;
        Button fishDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.fishName);
            latinNameTextView = itemView.findViewById(R.id.fishLatinName);
            fishImageImageView = itemView.findViewById(R.id.fishImage);
            fishDetailsButton = itemView.findViewById(R.id.fishDetails);
        }

        public void bindTo(@NonNull ClassHal currentFish) {
            nameTextView.setText(currentFish.getNev());
            latinNameTextView.setText(currentFish.getLatinNev());
            fishImageImageView.setImageResource(currentFish.getImageResourceId());
            fishDetailsButton.setOnClickListener(o -> onFishDetailsButtonClick(currentFish.getNev()));

            switch (gridNumber){
                case 1:
                    nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                    latinNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    fishDetailsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    break;
                case 2:
                    nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    latinNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    fishDetailsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    break;
                case 3:
                    nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    latinNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    fishDetailsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 6.6);
                    break;
            }
        }

        public void onFishDetailsButtonClick(String name){
            Intent intent = new Intent(context, FishDetailsActivity.class);
            intent.putExtra("fishName", name);
            context.startActivity(intent);
        }
    }

    @Override
    public Filter getFilter(){return fishFilter;}

    private final Filter fishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ClassHal> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            if(charSequence == null || charSequence.length() == 0){
                results.count = originalFishArray.size();
                results.values = originalFishArray;
            } else{
                String filterPattern =  Normalizer.normalize(charSequence, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                for(ClassHal hal : originalFishArray){
                    String halNev = Normalizer.normalize(hal.getNev(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                    String halLatinNev = Normalizer.normalize(hal.getLatinNev(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                    if(halNev.contains(filterPattern) || halLatinNev.contains(filterPattern)){
                        filteredList.add(hal);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filteredFishArray = (ArrayList<ClassHal>) results.values;
            notifyDataSetChanged();
        }
    };
}
