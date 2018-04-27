package com.ernesto.candidatosapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

// https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android

public class PersonalityInsightsFragment extends Fragment {

    private RadarChart personalityInsightsChart, needsInsightsChart, valuesInsightsChart;
    private Spinner dropwdown;
    private HashMap<Integer, ArrayList<String>> personalityInsightsMap, needsInsightsMap, valuesInsightsMap;
    private int currentCandidateId;

    public PersonalityInsightsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amlo, container, false);
    }

    public void initMaps() {

        personalityInsightsMap = new HashMap<Integer, ArrayList<String>>();
        personalityInsightsMap.put(1, new ArrayList<String>());
        personalityInsightsMap.put(2, new ArrayList<String>());
        personalityInsightsMap.put(3, new ArrayList<String>());

        needsInsightsMap = new HashMap<Integer, ArrayList<String>>();
        needsInsightsMap.put(1, new ArrayList<String>());
        needsInsightsMap.put(2, new ArrayList<String>());
        needsInsightsMap.put(3, new ArrayList<String>());

        valuesInsightsMap = new HashMap<Integer, ArrayList<String>>();
        valuesInsightsMap.put(1, new ArrayList<String>());
        valuesInsightsMap.put(2, new ArrayList<String>());
        valuesInsightsMap.put(3, new ArrayList<String>());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initMaps();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DataService.serverUrl + "candidates", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String token = response.getString("token");
                    JSONArray personalityInsightsArray = response.getJSONArray("personalityInsights");
                    for(int i = 0; i <= personalityInsightsArray.length()-1; i++) {
                        String personalidadAmlo = personalityInsightsArray.getJSONObject(i).getString("1");
                        String personalidadAnaya = personalityInsightsArray.getJSONObject(i).getString("2");
                        String personalidadMeade = personalityInsightsArray.getJSONObject(i).getString("3");

                        ArrayList<String> resultadosAmlo = personalityInsightsMap.get(1);
                        ArrayList<String> resultadosAnaya = personalityInsightsMap.get(2);
                        ArrayList<String> resultadosMeade = personalityInsightsMap.get(3);

                        resultadosAmlo.add(personalidadAmlo);
                        resultadosAnaya.add(personalidadAnaya);
                        resultadosMeade.add(personalidadMeade);

                        personalityInsightsMap.put(1, resultadosAmlo);
                        personalityInsightsMap.put(2, resultadosAnaya);
                        personalityInsightsMap.put(3, resultadosMeade);
                    }

                    JSONArray needsInsightsArray = response.getJSONArray("needsInsights");
                    for(int i = 0; i <= needsInsightsArray.length()-1; i++) {
                        String personalidadAmlo = needsInsightsArray.getJSONObject(i).getString("1");
                        String personalidadAnaya = needsInsightsArray.getJSONObject(i).getString("2");
                        String personalidadMeade = needsInsightsArray.getJSONObject(i).getString("3");

                        ArrayList<String> resultadosAmlo = needsInsightsMap.get(1);
                        ArrayList<String> resultadosAnaya = needsInsightsMap.get(2);
                        ArrayList<String> resultadosMeade = needsInsightsMap.get(3);

                        resultadosAmlo.add(personalidadAmlo);
                        resultadosAnaya.add(personalidadAnaya);
                        resultadosMeade.add(personalidadMeade);

                        needsInsightsMap.put(1, resultadosAmlo);
                        needsInsightsMap.put(2, resultadosAnaya);
                        needsInsightsMap.put(3, resultadosMeade);
                    }

                    JSONArray valuesInsightsArray = response.getJSONArray("valuesInsights");
                    for(int i = 0; i <= valuesInsightsArray.length()-1; i++) {
                        String personalidadAmlo = valuesInsightsArray.getJSONObject(i).getString("1");
                        String personalidadAnaya = valuesInsightsArray.getJSONObject(i).getString("2");
                        String personalidadMeade = valuesInsightsArray.getJSONObject(i).getString("3");

                        ArrayList<String> resultadosAmlo = valuesInsightsMap.get(1);
                        ArrayList<String> resultadosAnaya = valuesInsightsMap.get(2);
                        ArrayList<String> resultadosMeade = valuesInsightsMap.get(3);

                        resultadosAmlo.add(personalidadAmlo);
                        resultadosAnaya.add(personalidadAnaya);
                        resultadosMeade.add(personalidadMeade);

                        valuesInsightsMap.put(1, resultadosAmlo);
                        valuesInsightsMap.put(2, resultadosAnaya);
                        valuesInsightsMap.put(3, resultadosMeade);
                    }


                    formatPersonalityChart(personalityInsightsChart);
                    formatNeedsChart(needsInsightsChart);
                    formatValuesChart(valuesInsightsChart);

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


        personalityInsightsChart = (RadarChart) view.findViewById(R.id.personalidadChart);
        needsInsightsChart = (RadarChart) view.findViewById(R.id.necesidadesChart);
        valuesInsightsChart = (RadarChart) view.findViewById(R.id.valoresChart);

        dropwdown = (Spinner) view.findViewById(R.id.dropdown);

        String[] candidates = new String[]{"Andres Manuel Lopez Obrador", "Ricardo Anaya", "Jose Antonio Meade"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, candidates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropwdown.setAdapter(adapter);
        dropwdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCandidateId = i + 1;
                formatPersonalityChart(personalityInsightsChart);
                formatNeedsChart(needsInsightsChart);
                formatValuesChart(valuesInsightsChart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void formatPersonalityChart(RadarChart chart){
        chart.setBackgroundColor(Color.rgb(75, 101, 132));
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        setPersonalityData();

        chart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"Apertura a Experiencias", "Responsabilidad", "Extroversion", "Amabilidad", "Rango Emocional"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);


        chart.getDescription().setEnabled(false);
    }

    public void setPersonalityData() {
        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();

        ArrayList<String> entries = personalityInsightsMap.get(currentCandidateId);
        for(String entry: entries) {
            entries1.add(new RadarEntry(Float.parseFloat(entry)));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Evaluacion");
        set1.setColor(Color.rgb(9, 132, 227));
        set1.setFillColor(Color.rgb(9, 132, 227));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(15f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        personalityInsightsChart.setData(data);
        personalityInsightsChart.invalidate();

    }

    public void formatNeedsChart(RadarChart chart){
        chart.setBackgroundColor(Color.rgb(75, 101, 132));
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        setNeedsData();

        chart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"Desafío", "Familiaridad", "Curiosidad", "Entusiasmo", "Armonía", "Ideal", "Libertad", "Amor", "Practicidad", "Autoexpresión", "Estabilidad", "Estructura"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);


        chart.getDescription().setEnabled(false);

    }

    public void setNeedsData() {
        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();

        ArrayList<String> entries = needsInsightsMap.get(currentCandidateId);
        for(String entry: entries) {
            entries1.add(new RadarEntry(Float.parseFloat(entry)));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Evaluacion");
        set1.setColor(Color.rgb(253, 203, 110));
        set1.setFillColor(Color.rgb(253, 203, 110));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(15f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        needsInsightsChart.setData(data);
        needsInsightsChart.invalidate();
    }

    public void formatValuesChart(RadarChart chart){
        chart.setBackgroundColor(Color.rgb(75, 101, 132));
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        setValuesData();

        chart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"Conservación", "Apertura al cambio", "Hedonismo", "Superación personal", "Autotranscendencia"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);


        chart.getDescription().setEnabled(false);

    }

    public void setValuesData() {
        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();

        ArrayList<String> entries = valuesInsightsMap.get(currentCandidateId);
        for(String entry: entries) {
            entries1.add(new RadarEntry(Float.parseFloat(entry)));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Evaluacion");
        set1.setColor(Color.rgb(46, 204, 113));
        set1.setFillColor(Color.rgb(46, 204, 113));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(15f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        valuesInsightsChart.setData(data);
        valuesInsightsChart.invalidate();
    }

}
