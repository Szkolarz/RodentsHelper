package com.gryzoniopedia.rodentshelper.ROOM.Weights;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOWeight;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class WeightChart {

    private ArrayList<Entry> lineChartDataSet() {
        ArrayList<Entry> dataSet = new ArrayList<>();

        for (int i = 0; i < quarters.size(); i++) {
            quarters1.add(quarters.get(i).weightModel.getDate().toString());
            dataSet.add(new Entry(i, quarters.get(i).weightModel.getWeight()));
        }

        return dataSet;
    }

    private List<RodentWithWeights> quarters = new ArrayList<>();
    private final List<String> quarters1 = new ArrayList<>();

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
        lineDataSet.setValueTextSize(14);
        lineDataSet.setValueTextColor(Color.parseColor("#0375a8"));
        lineDataSet.setHighLightColor(Color.parseColor("#5397DF"));
        lineDataSet.setHighlightLineWidth(2f);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.parseColor("#5397DF"));

        lineDataSet.setValueFormatter(new DefaultAxisValueFormatter(0));


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


    @SuppressLint("SuspiciousIndentation")
    public void runChart(LineChart lineChart_weight, Context context) {
        quarters = getListWeightASC(context);

        ArrayList<Entry> lineChartDataSetArray = lineChartDataSet();

        lineChart_weight.getDescription().setEnabled(false);
        lineChart_weight.setPinchZoom(true);

        //lineChart_weight.setAutoScaleMinMaxEnabled(true);
        lineChart_weight.setNoDataText("Potrzeba minimum dwóch pomiarów wagi, aby wyświetlić wykres.");
        lineChart_weight.setExtraTopOffset(3);

        lineChart_weight.getXAxis().setSpaceMin(0.15f);
        lineChart_weight.getXAxis().setSpaceMax(0.15f);

        lineChart_weight.getAxisRight().setEnabled(false);


        Paint p = lineChart_weight.getPaint(Chart.PAINT_INFO);
        p.setTextSize(30);
        p.setColor(Color.parseColor("#014D70"));



        LineDataSet lineDataSet = new LineDataSet(lineChartDataSetArray, "Waga twojego pupila na przestrzeni ostatnich pomiarów");
        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(lineDataSet);

        XAxis xAxis = lineChart_weight.getXAxis();

        int lineChartDataSize = lineChartDataSetArray.size();

        LineData lineData = new LineData(iLineDataSet);
        if (lineChartDataSize >= 2)
            lineChart_weight.setData(lineData);

        if (lineChartDataSize >= 4) {
            lineChart_weight.getXAxis().setAxisMaximum(lineChartDataSize - 1);
            lineChart_weight.setVisibleXRangeMaximum(4);
            if (lineChartDataSize == 4)
                xAxis.setAxisMaximum(lineData.getXMax() + 0.2f);
            else
                xAxis.setAxisMaximum(lineData.getXMax() + 0.3f);
        } else if (lineChartDataSize == 3) {
            lineChart_weight.getXAxis().setAxisMaximum(lineChartDataSize - 1);
            lineChart_weight.setVisibleXRangeMaximum(3);
            xAxis.setAxisMaximum(lineData.getXMax() + 0.1f);
        } else if (lineChartDataSize == 2) {
            lineChart_weight.getXAxis().setAxisMaximum(lineChartDataSize - 1);
            lineChart_weight.setVisibleXRangeMaximum(2);
            xAxis.setAxisMaximum(lineData.getXMax() + 0.1f);
        }



        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

        xAxis.setTextSize(12);

        //xAxis.setAxisMaximum(10);



        if (lineChartDataSize >= 2)
            xAxis.setValueFormatter(formatter);


        if (lineChartDataSetArray.isEmpty())
            lineChart_weight.clear();

        lineChart_weight.invalidate();


        chartViewSettings(lineDataSet);

    }


}
