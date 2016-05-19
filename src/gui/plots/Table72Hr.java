package gui.plots;

import data.*;
import data.samples.WthrSampleDaily;
import data.samples.WthrSampleFine;
import data.samples.WthrSamplesFine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Nikolaev on 21/04/2016.
 */

public class Table72Hr extends PlotBase
{
	private String cssPath;
	String cssFileName = "tables.css";
	ObservableList content;
	TableView dataTable;
	
    public Table72Hr(Station station) 
    {
		super(station);
		setName(station.getName() + " 72 Hours Weather");
		URL url = this.getClass().getResource(cssFileName);
        cssPath = url.toExternalForm();
		createTable();
		assembleFrom(dataTable);
	}

    public void createTable()
    {


        //  creating a table of monthly data

        dataTable = new TableView<WthrSampleDaily>();

        //-------------------------------------------------------------------------------------//
        // in order to create a table you have to specify the class / type of object it is representing
        // it then automatically detects the values within a particular instance of object
        // this is done when you specify the propertyValueFactory

        TableColumn<WthrSampleFine, String> date = new TableColumn<>("Local DateTime");
        TableColumn<WthrSampleFine, String> gustKmh = new TableColumn<>("gustKmh");
        TableColumn<WthrSampleFine, String> gustKt = new TableColumn<>("gustKt");
        TableColumn<WthrSampleFine, String> airTemp = new TableColumn<>("airTemp");
        TableColumn<WthrSampleFine, String> relHumidity = new TableColumn<>("relHumidity");

        date.setSortable(false);
        gustKmh.setSortable(false);
        gustKt.setSortable(false);
        relHumidity.setSortable(false);
        airTemp.setSortable(false);

        TableColumn<WthrSampleFine, String> dewPt = new TableColumn<>("dewPt");
        TableColumn<WthrSampleFine, String> windDir = new TableColumn<>("windDir");
        TableColumn<WthrSampleFine, String> windSpdKmh = new TableColumn<>("windSpdKmh");
        TableColumn<WthrSampleFine, String> windSpdKt = new TableColumn<>("windSpdKt");

        windSpdKmh.setSortable(false);
        dewPt.setSortable(false);
        windSpdKt.setSortable(false);
        windDir.setSortable(false);


        date.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("localDateTime"));
        gustKmh.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("gustKmh"));
        gustKt.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("gustKt"));
        airTemp.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("airTemp"));
        relHumidity.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("relHumidity"));

        dewPt.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("dewPt"));
        windDir.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("windDir"));
        windSpdKmh.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("windSpdKmh"));
        windSpdKt.setCellValueFactory(new PropertyValueFactory<WthrSampleFine, String>("windSpdKt"));


        dataTable.getColumns().setAll(date,windDir,windSpdKmh,windSpdKt,relHumidity,gustKmh,gustKt,airTemp,dewPt);
        dataTable.setMinSize(665,450);
        dataTable.setEditable(false);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(dataTable);
        container.setMinSize(665,450);
    }
    
    @Override
    public String getCssPath()
    {
		return cssPath;
    }
    
    public ObservableList getData(Station station) throws Exception 
    {

        // here we create a list of weather sample objects
        // each weather sample object contains data from charlton on a particular day
        // the values of each sample objects variables is automatically detected by the tableView

        List list = new ArrayList();

        WthrSamplesFine stationData;

        stationData = Bom.getWthrLast72hr(station);
        for (WthrSampleFine sample : stationData) {
            list.add(sample);
        }

        ObservableList data = FXCollections.observableList(list);

        return data;
    }
    
    @Override 
	public void fetchData()
	{
    	try {
            content = getData(station);
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}
    
    @Override
	public void plotData() 
    {
    	dataTable.setItems(content);
	}
    
    @Override
    public void onRefresh()
    {
    	fetchData();
    	plotData();
    }
}