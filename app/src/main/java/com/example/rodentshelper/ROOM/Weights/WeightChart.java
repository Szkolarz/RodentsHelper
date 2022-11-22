package com.example.rodentshelper.ROOM.Weights;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.room.Room;

import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM._MTM.RodentWeight.RodentWithWeights;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class WeightChart {

    private ArrayList<Entry> lineChartDataSet() {
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        for (int i = 0; i < quarters.size(); i++) {
            quarters1.add(quarters.get(i).weightModel.getDate().toString());
            dataSet.add(new Entry(i, quarters.get(i).weightModel.getWeight()));
        }

        return dataSet;
    }

    List<RodentWithWeights> quarters = new ArrayList<>();
    List<String> quarters1 = new ArrayList<>();

    ValueFormatter formatter = new ValueFormatter() {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return quarters1.get((int) value);
        }
    };

    private void chartViewSettings(LineDataSet lineDataSet) {

        lineDataSet.setColor(Color.parseColor("#5397DF"));
        lineDataSet.setCircleColor(Color.parseColor("#0375a8"));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setLineWidth(5);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleHoleRadius(10);
        lineDataSet.setValueTextSize(16);
        lineDataSet.setValueTextColor(Color.parseColor("#0375a8"));


        lineDataSet.setGradientColor(Color.GREEN, Color.BLUE);

        lineDataSet.setDrawValues(true);

    }

    public List getListWeightASC(Context context){
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        SharedPreferences prefsGetRodentId = context.getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        List<RodentWithWeights> weightModel = daoWeight.getRodentWithWeightsASC(prefsGetRodentId.getInt("rodentId", 0));
        return weightModel;
    }

    public void runChart(LineChart lineChart_weight, Context context) {
        quarters = getListWeightASC(context);

        lineChart_weight.getDescription().setEnabled(false);
        lineChart_weight.setPinchZoom(true);
        lineChart_weight.setDrawBorders(false); //?
        lineChart_weight.setMaxVisibleValueCount(6);
        lineChart_weight.setAutoScaleMinMaxEnabled(true);


        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(), "Waga twojego pupila na przestrzeni ostatnich pomiarów");
        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSet);


        LineData lineData = new LineData(iLineDataSet);
        lineChart_weight.setData(lineData);

        XAxis xAxis = lineChart_weight.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

        xAxis.setTextSize(12);
        xAxis.setAxisMaximum(10);

        xAxis.setValueFormatter(formatter);

        lineChart_weight.invalidate();


        chartViewSettings(lineDataSet);

    }


}
