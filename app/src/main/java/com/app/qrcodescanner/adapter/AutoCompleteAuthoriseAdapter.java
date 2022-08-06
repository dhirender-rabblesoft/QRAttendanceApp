package com.app.qrcodescanner.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.qrcodescanner.R;
import com.app.qrcodescanner.listner.AutocompletListner;
import com.app.qrcodescanner.model.AuthoriseList;
import com.app.qrcodescanner.model.CareListJson;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAuthoriseAdapter extends ArrayAdapter<AuthoriseList.Data>
{
    private List<AuthoriseList.Data> allPlacesList;
    private List<AuthoriseList.Data> filteredPlacesList;
    private AutocompletListner autocompletListner;

    public AutoCompleteAuthoriseAdapter(@NonNull Context context, @NonNull List<AuthoriseList.Data> placesList, AutocompletListner autocompletListner)
    {
        super(context, 0, placesList);
        allPlacesList = new ArrayList<>(placesList);
        this.autocompletListner=autocompletListner;
    }

    public void setData(List<AuthoriseList.Data> list) {
        allPlacesList.clear();
        allPlacesList.addAll(list);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_item_place, parent, false
            );
        }

        AppCompatButton carebutton = convertView.findViewById(R.id.carebutton);
        AppCompatTextView placeLabel = convertView.findViewById(R.id.autocomplete_item_place_label);

        AuthoriseList.Data place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getName());
            if (place.getId()==0)
            {
                carebutton.setVisibility(View.VISIBLE);
                carebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autocompletListner.autoclick("3",null,null);
                    }
                });

            }
            else
            {
//                placeLabel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        autocompletListner.autoclick("2",place,null);
//                    }
//                });
                carebutton.setVisibility(View.GONE);
            }
         //   Glide.with(convertView).load(place.getImageUrl()).into(placeImage);
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            filteredPlacesList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredPlacesList.addAll(allPlacesList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AuthoriseList.Data place: allPlacesList) {
                    if (place.getName().toLowerCase().contains(filterPattern)) {
                        filteredPlacesList.add(place);
                    }
                }
            }

            results.values = filteredPlacesList;
            results.count = filteredPlacesList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((AuthoriseList.Data) resultValue).getName();
        }
    };
}