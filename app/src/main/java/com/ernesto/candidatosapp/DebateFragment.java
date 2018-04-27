package com.ernesto.candidatosapp;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DebateFragment extends Fragment {

    private BarChart resultadosChart;
    private Spinner dropdown;
    private int currentCandidateId, tweetsPositivos, tweetsNegativos;
    private TextView NMF,LDA;

    public DebateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dropdown = (Spinner) view.findViewById(R.id.dropdown);
        NMF = (TextView) view.findViewById(R.id.NMF);
        LDA = (TextView) view.findViewById(R.id.LDA);

        String[] candidates = new String[]{"Andres Manuel Lopez Obrador", "Ricardo Anaya", "Jose Antonio Meade"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, candidates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCandidateId = i + 1;

                if(currentCandidateId == 1) {
                    // AMLO
                    tweetsPositivos = 969;
                    tweetsNegativos = 1479;

                    NMF.setText("Topico 0: debate méxico si méxicoconamlo corrupción amlo vamos mexicoconamlo presidente pueblo\n\n" +
                            "Topico 1: amnistía impunidad significa causas atender pobreza combatir violencia originaron delincuentes\n\n" +
                            "Topico 2: violencia crecimiento económico desató años 30 país hace paz bienestar\n\n" +
                            "Topico 3: gobierno jefe cdmx violencia bajó homicidios robo inseguridad incidencia reduje\n\n" +
                            "Topico 4: andrés manuel obrador lópez lengua lagarto peje traen aristeguinoticias poder\n\n");

                    LDA.setText("Topico 0: amnistía impunidad significa violencia pobreza combatir dice delincuentes manera undefined\n\n" +
                            "Topico 1: debate si candidatos años propuestas amlo dice mexicoconamlo va solo\n\n" +
                            "Topico 2: corrupción méxico vamos presidencial si avión dice país arriba gobierno\n\n" +
                            "Topico 3: gobierno violencia jefe puede crecimiento si cdmx seguridad económico estrategia\n\n" +
                            "Topico 4: méxico presidente pueblo andrés transformación manuel país obrador mexicoconamlo mexicanos\n\n");

                } else if(currentCandidateId == 2) {
                    tweetsPositivos = 1620;
                    tweetsNegativos = 1553;

                    NMF.setText("Topico 0:  joseameadek lopezobrador_ candidatos debate propuestas primer inemexico presidencia\n\n" +
                            "Topico 1: juntosconanaya méxico propuestas vamos debate candidato mejor conanayaporméxico futuro porméxicoalfrente\n\n" +
                            "Topico 2: mujeresconanaya mujeresalfrente pormexicoalfrente progreso lograremos libertad bienestar conanayaporméxico juntosconanaya noesnormal\n\n" +
                            "Topico 3: amlo dice si anaya amnistía estrategia seguridad gobierno va propone\n\n" +
                            "Topico 4: meade pregunta honestidad gobernado jefe nieto peña contesta enrique frente\n\n");

                    LDA.setText("Topico 0: joseameadek lopezobrador_ mzavalagc jaimerdznl debate candidatos propuestas si ganó quién\n\n" +
                            "Topico 1: méxico hoy futuro propuestas duarte visión país noche cambio siempre\n\n" +
                            "Topico 2: lopezobrador_ amlo joseameadek anaya dice meade si país amnistía pregunta\n\n" +
                            "Topico 3: juntosconanaya conanayaporméxico candidato vamos méxico propuestas debate mejor ganar duda\n\n" +
                            "Topico 4: corrupción propone estrategia mandato gobierno dice revocación va favor conanayaporméxico\n\n");

                } else if(currentCandidateId == 3) {
                    tweetsPositivos = 1682;
                    tweetsNegativos = 1112;

                    NMF.setText("Topico 0: presidente méxicoconmeade meadepresidente queremos cdeprihidalgo pri_nacional sola honesto \n\n" +
                            "Topico 1: propuestas ganómeade méxico debate duda hoy candidato mejores preparado mejor\n\n" +
                            "Topico 2: verdaderas corrupción combatir acciones demuestra hidalgoconmeade propuestas meadepresidente redhgoconmeade retweeted\n\n" +
                            "Topico 3: meade antonio josé lopezobrador_ retweeted seguridad gobierno criminales si meadepresidente\n\n" +
                            "Topico 4: nuevaalianza decidadanxaciudadanx mensaje final contundente ganar dabateine vamos mexico ganomeade\n\n");

                    LDA.setText("Topico 0: debate ganar vamos nuevaalianzaconmeade nuevaalianza candidato deciudadanxaciudadanx meadepresidente dabateine creo\n\n" +
                            "Topico 1: lopezobrador_ dice pri partido amlo si corrupción violencia seguridad años\n\n" +
                            "Topico 2: gobierno si seguridad meadepresidente días 100 voy cárcel único primeros\n\n" +
                            "Topico 3: ganómeade meade propuestas debate antonio hoy josé duda meadepresidente méxico\n\n" +
                            "Topico 4: meadepresidente méxico presidente propuestas méxicoconmeade hidalgoconmeade honesto ganómeade pri_nacional preparado\n\n");
                }

                setupChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        resultadosChart = view.findViewById(R.id.resultadosChart);
        setupChart();
    }

    private void setupChart() {

        resultadosChart.setDrawBarShadow(false);
        resultadosChart.setDrawValueAboveBar(true);

        resultadosChart.getDescription().setEnabled(false);

        resultadosChart.setMaxVisibleValueCount(2);

        resultadosChart.setPinchZoom(false);

        resultadosChart.setDrawGridBackground(false);

        XAxis xAxis = resultadosChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = resultadosChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = resultadosChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);

        Legend l = resultadosChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setData();

    }

    private void setData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(0f, (float) tweetsPositivos));
        yVals1.add(new BarEntry(1f,(float) tweetsNegativos));


        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(106, 176, 76));
        colors.add(Color.rgb(235, 77, 75));

        BarDataSet set1 = new BarDataSet(yVals1, "Menciones Positivas vs Negativas durante el debate");
        set1.setDrawIcons(false);
        set1.setColors(colors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        resultadosChart.setData(data);
        resultadosChart.invalidate();

    }
}
