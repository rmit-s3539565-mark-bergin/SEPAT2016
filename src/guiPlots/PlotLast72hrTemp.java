package guiPlots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.YearMonth;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.javafx.charts.Legend;

import data.Bom;
import data.Station;
import data.WthrSampleDaily;
import data.WthrSampleFine;
import data.WthrSamplesDaily;
import data.WthrSamplesFine;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class PlotLast72hrTemp extends PlotBase
{
	private String cssPath;
	static String cssFileName = "CurrTempPlot.css";
	final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    XYChart.Series<String, Number> seriesAirTemp = new XYChart.Series<String, Number>();
    LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
    
	public PlotLast72hrTemp(Station station) 
	{
		super(station);
		URL url = this.getClass().getResource(cssFileName);
        cssPath = url.toExternalForm();
		
		xAxis.setLabel("Date/Time");
        yAxis.setLabel("Temperature in Degrees");
        lineChart.setTitle(station.getName());
        seriesAirTemp.setName("Air Temperature");
        
        // Remove markers from line
        lineChart.setCreateSymbols(false);
        
        plot(station);

        // Allow children to resize vertically
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().add(columnConstraints);
        
        // Allow children to resize horizontally
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        this.getRowConstraints().add(rowConstraints);
        
        // add the lineChart to the gridPane
        assembleFrom(lineChart);
	}
	
	@Override 
	public String getCssPath()
	{
		return cssPath;
	}
	
	private WthrSamplesFine getData(Station station) {
        WthrSamplesFine wthrSamplesFine = new WthrSamplesFine();
        try {
			wthrSamplesFine = Bom.getWthrLast72hr(station);
			return wthrSamplesFine;
		} catch (JsonIOException | JsonSyntaxException | IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Cannot access BoM JSON server");
			alert.setContentText("Please check your internet connection and try again");

			alert.showAndWait();
			return null;
		}
	}
	
	private void addToSeries(WthrSamplesFine samples, XYChart.Series<String, Number> series) {
		int samplesSize = samples.size();
        WthrSampleFine sample = null;
        for(int i = samplesSize - 1; i > -1; i--) {
        	sample = samples.get(i);
        	String date = sample.getLocalDateTime();
        	String airTemp = sample.getAirTemp();
        	
        	// Check if the string is null or blank
        	if (airTemp.length() > 0)
        		seriesAirTemp.getData().add(new Data<String, Number>(date,Float.parseFloat(airTemp)));
        }
	}
	
	private void plot(Station station) {
		WthrSamplesFine wthrSamplesFine = getData(station);
        addToSeries(wthrSamplesFine, seriesAirTemp);
        lineChart.getData().add(seriesAirTemp);
	}
	
	@Override
	protected void onRefresh() {
		WthrSamplesFine wthrSamplesFine = getData(station);
		seriesAirTemp.getData().clear();
		addToSeries(wthrSamplesFine, seriesAirTemp);
	}
}
