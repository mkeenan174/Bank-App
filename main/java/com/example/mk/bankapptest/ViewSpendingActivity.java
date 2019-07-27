package com.example.mk.bankapptest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;

import static com.example.mk.bankapptest.BankThread.bank;
import static java.lang.Double.parseDouble;

public class ViewSpendingActivity extends AppCompatActivity implements LabelFormatter {
    static BankThread bankThread;
    String[] tranDates;
    String[] tranAmounts;
    int position = 0;
    int yPoint;
    int xPoint;
    int labelNum =0;
    String detailDate;
    Context c = this;
    static TextView errorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_spending);
        bankThread = MainActivity.getBankThread();

       errorTextView = (TextView) findViewById(R.id.view_transactions_error_text);
        getGraphInfoTask Task = new getGraphInfoTask();
        Task.execute();

    }

    @Override
    public String formatLabel(double value, boolean isValueX) {
        return null;
    }

    @Override
    public void setViewport(Viewport viewport) {

    }


    private class getGraphInfoTask extends AsyncTask<String[],Integer, LinkedList> {
        LinkedList<String[]> graphResults = new LinkedList();
        String[] resultDates;
        String[] resultAmounts;
        String test;

        @Override
        protected LinkedList doInBackground(String[]... strings) {
            resultAmounts = bank.getAccTranAmount();
            resultDates = bank.getAccTranDate();
            graphResults.add(resultAmounts);
            graphResults.add(resultDates);
            return graphResults ;
        }

        @Override
        protected void onPostExecute(LinkedList linkedList) {
            super.onPostExecute(linkedList);
            tranAmounts = graphResults.get(0);
            tranDates = graphResults.get(1);
            graphMaker();

        }

    }
public void graphMaker(){
    if(tranAmounts != null) {
        DataPoint[] graphPoints = new DataPoint[tranAmounts.length];
        for (String a : tranAmounts) {
            Double temp = parseDouble(tranAmounts[position]);
            yPoint = (int) Math.round(temp);
            xPoint = position;
            graphPoints[position] = new DataPoint(xPoint, yPoint);
            position++;
        }
        GraphView accountGraph = (GraphView) findViewById(R.id.account_transactions_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphPoints);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(10);
        accountGraph.getViewport().setYAxisBoundsManual(true);
        accountGraph.getViewport().setMaxY(500);
        accountGraph.getViewport().setMinY(-500);
        accountGraph.getViewport().setXAxisBoundsManual(true);
        accountGraph.getViewport().setMaxX(position);
        accountGraph.getViewport().setMinX(0);
        accountGraph.getViewport().setScrollable(false);
        accountGraph.getGridLabelRenderer().setNumHorizontalLabels(position + 1);
        accountGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values

                    String labelString = String.valueOf(labelNum);
                    labelNum++;
                    return labelString;
                } else {
                    // show currency for y values
                    return "€" + super.formatLabel(value, isValueX);
                }
            }
        });
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                int i = 0;
                for(String S : tranAmounts){
                    Double t = Double.parseDouble(S);
                    if(t == dataPoint.getY()){
                        detailDate = tranDates[i];
                    }else{
                        i++;
                    }
                }
                Toast.makeText(c,"€" + dataPoint.getY() + " on  " + detailDate,Toast.LENGTH_SHORT).show();                }
        });
        accountGraph.addSeries(series);

    }else{
        errorTextView.setText("No transactions to display.");
    }
}

}
