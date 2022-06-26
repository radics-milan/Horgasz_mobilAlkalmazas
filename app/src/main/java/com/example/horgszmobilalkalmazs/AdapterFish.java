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

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.Normalizer;
import java.util.ArrayList;

public class AdapterFish extends RecyclerView.Adapter<AdapterFish.ViewHolder> implements Filterable {
    Context context;
    ArrayList<ClassFish> originalFishArray;
    ArrayList<ClassFish> filteredFishArray;
    int gridNumber;
    DisplayImageOptions options;
    int defaultImage;

    public AdapterFish(Context context, ArrayList<ClassFish> fishArray, int gridNumber) {
        this.context = context;
        this.originalFishArray = fishArray;
        this.filteredFishArray = fishArray;
        this.gridNumber = gridNumber;
    }

    @NonNull
    @Override
    public AdapterFish.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setupImageLoader();
        defaultImage = context.getResources().getIdentifier("@drawable/amur", null, context.getPackageName());
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fish_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFish.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        holder.fishImageImageView.startAnimation(animation);

        ClassFish currentFish = filteredFishArray.get(position);
        holder.bindTo(currentFish);
    }

    @Override
    public int getItemCount() {
        return filteredFishArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

        public void bindTo(@NonNull ClassFish currentFish) {
            nameTextView.setText(currentFish.getNev());
            latinNameTextView.setText(currentFish.getLatinNev());

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage("drawable://" + currentFish.getImageResourceId(), fishImageImageView, options);
            fishDetailsButton.setOnClickListener(o -> onFishDetailsButtonClick(currentFish.getNev()));

            switch (gridNumber) {
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

        public void onFishDetailsButtonClick(String name) {
            Intent intent = new Intent(context, FishDetailsActivity.class);
            intent.putExtra("fishName", name);
            context.startActivity(intent);
        }
    }

    @Override
    public Filter getFilter() {
        return fishFilter;
    }

    private final Filter fishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ClassFish> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            if (charSequence == null || charSequence.length() == 0) {
                results.count = originalFishArray.size();
                results.values = originalFishArray;
            } else {
                String filterPattern = Normalizer.normalize(charSequence, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                for (ClassFish hal : originalFishArray) {
                    String halNev = Normalizer.normalize(hal.getNev(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                    String halLatinNev = Normalizer.normalize(hal.getLatinNev(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("\\s", "");
                    if (halNev.contains(filterPattern) || halLatinNev.contains(filterPattern)) {
                        filteredList.add(hal);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filteredFishArray = (ArrayList<ClassFish>) results.values;
            notifyDataSetChanged();
        }
    };

    private void setupImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize( 100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }
}
