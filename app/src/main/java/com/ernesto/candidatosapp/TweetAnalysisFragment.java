package com.ernesto.candidatosapp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class TweetAnalysisFragment extends Fragment {


    private PieChart sentimentChart;
    private Spinner dropwdown;
    private int currentCandidateId;
    private HashMap<Integer, HashMap<String, Integer>> candidateStatsMap;
    private TextView promedio;

    public TweetAnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_analysis, container, false);
    }

    public void initMaps() {
        candidateStatsMap = new HashMap<Integer, HashMap<String, Integer>>();
        candidateStatsMap.put(1, new HashMap<String, Integer>());
        candidateStatsMap.put(2, new HashMap<String, Integer>());
        candidateStatsMap.put(3, new HashMap<String, Integer>());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sentimentChart = (PieChart) view.findViewById(R.id.sentimentChart);
        dropwdown = (Spinner) view.findViewById(R.id.dropdown);
        promedio = (TextView) view.findViewById(R.id.promedio);


        String[] candidates = new String[]{"Andres Manuel Lopez Obrador", "Ricardo Anaya", "Jose Antonio Meade"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, candidates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropwdown.setAdapter(adapter);
        dropwdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCandidateId = i + 1;
                formatPieChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initMaps();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DataService.serverUrl + "candidates", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String token = response.getString("token");
                    JSONArray candidatesArray = response.getJSONArray("candidates");
                    for(int i =0; i<candidatesArray.length();i++) {
                        JSONObject candidate = candidatesArray.getJSONObject(i);
                        int frontendId = candidate.getInt("frontendId");
                        int negativeTweets = candidate.getInt("negativeTweets");
                        int positiveTweets = candidate.getInt("positiveTweets");
                        int favAverage = candidate.getInt("favAverage");

                        HashMap<String, Integer> map = new HashMap<String, Integer>();
                        map.put("Negative Tweets", negativeTweets);
                        map.put("Positive Tweets", positiveTweets);
                        map.put("Favorite Average", favAverage);

                        candidateStatsMap.put(frontendId,map);
                    }

                    formatPieChart();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        VolleySingleton.getInstance(view.getContext()).getRequestQueue().add(request);


    }

    private void formatPieChart() {
        sentimentChart.setUsePercentValues(true);
        sentimentChart.getDescription().setEnabled(false);
        sentimentChart.setExtraOffsets(5, 10, 5, 5);

        sentimentChart.setDragDecelerationFrictionCoef(0.95f);

        sentimentChart.setCenterText(generateCenterSpannableText());

        sentimentChart.setDrawHoleEnabled(true);
        sentimentChart.setHoleColor(Color.WHITE);

        sentimentChart.setTransparentCircleColor(Color.WHITE);
        sentimentChart.setTransparentCircleAlpha(110);

        sentimentChart.setHoleRadius(58f);
        sentimentChart.setTransparentCircleRadius(61f);

        sentimentChart.setDrawCenterText(true);

        sentimentChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        sentimentChart.setRotationEnabled(true);
        sentimentChart.setHighlightPerTapEnabled(true);

        sentimentChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });


        setData();
    }

    private void setData() {

        HashMap<String, Integer> map = candidateStatsMap.get(currentCandidateId);
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        if(map.isEmpty()) return;

        entries.add(new PieEntry((float) map.get("Positive Tweets")));
        entries.add(new PieEntry((float) map.get("Negative Tweets")));
        promedio.setText("Promedio de favoritos: " +map.get("Favorite Average"));

        PieDataSet dataSet = new PieDataSet(entries, "P vs. N");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(106, 176, 76));
        colors.add(Color.rgb(235, 77, 75));


        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        sentimentChart.setData(data);

        // undo all highlights
        sentimentChart.highlightValues(null);

        sentimentChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Tweets \n Positivos vs Negativos");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, s.length(), 0);

        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        //s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

}
