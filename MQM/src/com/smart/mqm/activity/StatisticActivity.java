package com.smart.mqm.activity;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.smart.mqm.R;
import com.smart.mqm.util.Utils;

public class StatisticActivity extends Activity {
	private GraphicalView mChartView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);
		// set some properties on the main renderer 
		drawChart();
		drawBar();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    drawChart();
	}
	
	private XYMultipleSeriesRenderer getDemoRenderer() {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(Utils.spSize(this, 16));
	    renderer.setChartTitleTextSize(Utils.spSize(this, 20));
	    renderer.setLabelsTextSize(Utils.spSize(this, 14));
	    renderer.setPointSize(Utils.spSize(this, 5));
	    renderer.setMargins(new int[] {50, 50, 20, 15});
	    
	    //line 1
	    XYSeriesRenderer r = new XYSeriesRenderer();
	    r.setColor(Color.BLUE);
	    r.setPointStyle(PointStyle.CIRCLE);
	    r.setLineWidth(Utils.dipSize(this, 3));
	    r.setChartValuesSpacing(Utils.dipSize(this, 10));
	    r.setFillPoints(true);
	    r.setDisplayBoundingPoints(true);
	    renderer.addSeriesRenderer(r);
	    
	    //line 2
	    r = new XYSeriesRenderer();
	    r.setColor(Color.GREEN);
	    r.setPointStyle(PointStyle.CIRCLE);
	    r.setLineWidth(Utils.dipSize(this, 3));
	    r.setChartValuesSpacing(Utils.dipSize(this, 10));
	    r.setFillPoints(true);
	    r.setDisplayBoundingPoints(true);
	    renderer.addSeriesRenderer(r);
	    
	    renderer.setShowAxes(true); // show both axes
	    renderer.setShowLegend(false);
	    renderer.setShowGridX(true); // X grid helps identify values
	    renderer.setShowLabels(true); 
	    renderer.setAxesColor(Color.LTGRAY);
	    renderer.setMarginsColor(Color.WHITE);
	    renderer.setLabelsColor(Color.BLACK);
	    String[] date={"week 1","week 2","week 3","week 4"}; 
	    for (int i = 0; i < date.length; i++) 
	    { 
	    	renderer.addXTextLabel(i+1, date[i]);
	    }
	    
	    renderer.setXAxisMin(0.0);
	    renderer.setXAxisMax(5.0);
	    renderer.setYAxisMin(-0.0);
	    renderer.setYAxisMax(10.0);
	    
	    renderer.setXLabelsAlign(Align.CENTER);
	    renderer.setXLabelsColor(Color.BLACK);
	    renderer.setYLabelsColor(0, Color.BLACK);
	    renderer.setXLabels(0);
	    renderer.setYAxisAlign(Align.LEFT, 0);   
	    renderer.setYLabelsAlign(Align.RIGHT, 0); 
	    renderer.setBackgroundColor(Color.WHITE);
	    renderer.setPanEnabled(false, false);
	    renderer.setZoomEnabled(false, false);
	    renderer.setPanLimits(new double[]{0.0,Double.MAX_VALUE,0.0,Double.MAX_VALUE});

	    return renderer;
	}
	
	private XYMultipleSeriesDataset getDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 5;
		String[] titlePoint = {"Target","Actual"}; 
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries(titlePoint[i]);
			for (int k = 1; k < nr; k++) {
				int y = r.nextInt(100);
				series.add(k, y);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}
	
	private void drawChart(){
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer());
			mChartView.setBackgroundColor(Color.WHITE);
			layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			mChartView.repaint();
		} else {
		  mChartView.repaint();
		}
	}
	
	private void drawBar(){
	}
}