package com.ernesto.candidatosapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ernesto.candidatosapp.R;
import com.ernesto.candidatosapp.pojos.ConsumptionPreference;

import java.util.List;

/**
 * Created by ernesto on 4/26/18.
 */

public class ConsumptionPreferenceAdapter extends ArrayAdapter<ConsumptionPreference>{
    private Context context;

    public ConsumptionPreferenceAdapter(Context context, int resource, List<ConsumptionPreference> objects) {
        super(context, resource, objects);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConsumptionPreference consumptionPreference = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.consumption_element_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.description);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.yesno);

        textView.setText(consumptionPreference.description);
        if(consumptionPreference.yes) {
            imageView.setImageResource(R.drawable.yes);
        } else {
            imageView.setImageResource(R.drawable.no);
        }

        return convertView;
    }
}
