package com.ernesto.candidatosapp;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ernesto.candidatosapp.adapters.ConsumptionPreferenceAdapter;
import com.ernesto.candidatosapp.pojos.ConsumptionPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumptionFragment extends Fragment {

    private ListView listView;
    private ConsumptionPreferenceAdapter preferenceAdapter;
    private RequestQueue mQueue;
    private Spinner dropwdown;
    private int currentCandidateId;
    private HashMap<Integer, ArrayList<ConsumptionPreference>> consumptionPreferencesMap;


    public ConsumptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumption, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.preferencesList);
        preferenceAdapter = new ConsumptionPreferenceAdapter(view.getContext(), R.layout.consumption_element_layout, new ArrayList<ConsumptionPreference>());
        listView.setAdapter(preferenceAdapter);
        mQueue = VolleySingleton.getInstance(view.getContext()).getRequestQueue();

        consumptionPreferencesMap = new HashMap<Integer, ArrayList<ConsumptionPreference>>();
        consumptionPreferencesMap.put(1, new ArrayList<ConsumptionPreference>());
        consumptionPreferencesMap.put(2, new ArrayList<ConsumptionPreference>());
        consumptionPreferencesMap.put(3, new ArrayList<ConsumptionPreference>());

        dropwdown = (Spinner) view.findViewById(R.id.dropdown);
        String[] candidates = new String[]{"Andres Manuel Lopez Obrador", "Ricardo Anaya", "Jose Antonio Meade"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, candidates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropwdown.setAdapter(adapter);
        dropwdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCandidateId = i + 1;
                populateList(preferenceAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getConsumptionPreferences();

    }

    public void populateList(ConsumptionPreferenceAdapter adapter){
        adapter.clear();
        ArrayList<ConsumptionPreference> preferences = consumptionPreferencesMap.get(currentCandidateId);
        for(ConsumptionPreference preference: preferences) {
            adapter.add(preference);
        }
    }

    public void getConsumptionPreferences() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DataService.serverUrl + "candidates", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String token = response.getString("token");
                    JSONObject consumptionPreferences = response.getJSONObject("consumptionPreferences");

                    JSONArray consumptionPreferencesAmlo = consumptionPreferences.getJSONArray("1");
                    for(int i = 0; i<consumptionPreferencesAmlo.length(); i++) {
                        JSONObject preference = consumptionPreferencesAmlo.getJSONObject(i);

                        ConsumptionPreference consumptionPreference = new ConsumptionPreference();
                        consumptionPreference.description = preference.getString("name");
                        consumptionPreference.yes = preference.getInt("score") == 1;

                        consumptionPreferencesMap.get(1).add(consumptionPreference);
                    }

                    JSONArray consumptionPreferencesAnaya = consumptionPreferences.getJSONArray("2");
                    for(int i = 0; i<consumptionPreferencesAnaya.length(); i++) {
                        JSONObject preference = consumptionPreferencesAnaya.getJSONObject(i);

                        ConsumptionPreference consumptionPreference = new ConsumptionPreference();
                        consumptionPreference.description = preference.getString("name");
                        consumptionPreference.yes = preference.getInt("score") == 1;

                        consumptionPreferencesMap.get(2).add(consumptionPreference);
                    }

                    JSONArray consumptionPreferencesMeade = consumptionPreferences.getJSONArray("3");
                    for(int i = 0; i<consumptionPreferencesMeade.length(); i++) {
                        JSONObject preference = consumptionPreferencesMeade.getJSONObject(i);

                        ConsumptionPreference consumptionPreference = new ConsumptionPreference();
                        consumptionPreference.description = preference.getString("name");
                        consumptionPreference.yes = preference.getInt("score") == 1;

                        consumptionPreferencesMap.get(3).add(consumptionPreference);
                    }

                    populateList(preferenceAdapter);


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
        mQueue.add(request);

    }
}
