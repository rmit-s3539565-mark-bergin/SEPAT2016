package gui;

import java.text.DecimalFormat;

import data.Station;
import data.StationList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/* All the options for one station.
 * Goes in the OptionsArea which is/has
 * a TapPane. */
public class OptionsPaneBase extends VBox
{
	Station station;
	GuiEventInterface eventHandler;	
	String button72HrText = "Open 72 Hour Temperature Plot";
	Button open72HrPlotButton = new Button(button72HrText);
	String buttonHisTempTxt = "Open Historical Temperature Plot";
	Button OpenHisTempPlotButton = new Button(buttonHisTempTxt);
	String buttonTable72hrTxt = "Open 72hr Table";
	Button OpenHisTable72hrButton = new Button(buttonTable72hrTxt);
	String buttonTableYearlyTxt = "Open Yearly Table";
	Button OpenHisTableYearlyButton = new Button(buttonTableYearlyTxt);
	public OptionsPaneBase(Station station, GuiEventInterface eventHandler)
	{
		super();
		this.station = station;
		this.eventHandler = eventHandler;
		float[] averageTemps = new float[3];
		DecimalFormat roundtotwo = new DecimalFormat("##.00");
		averageTemps = station.findAverageTemps(station);
		Label labelHead = new Label(" Average Temperatures over the last three days:");
		Label labelEarlyMornings = new Label(" From 5am-10am: " + roundtotwo.format(averageTemps[0]) + " degress celcius");
		Label labelMidDays = new Label(" From 11am-4pm: " + roundtotwo.format(averageTemps[1]) + " degress celcius");
		Label labelNights = new Label(" From 5pm-10pm: " + roundtotwo.format(averageTemps[2]) + " degress celcius");
		Label labelLateNights = new Label(" From 11pm-4am: " + roundtotwo.format(averageTemps[3]) + " degress celcius");
		addOption(labelHead);
		addOption(labelEarlyMornings);
		addOption(labelMidDays);
		addOption(labelNights);
		addOption(labelLateNights);
		
		open72HrPlotButton.setOnMouseClicked(e -> eventHandler.onOpen72TempPlot(station));
		OpenHisTempPlotButton.setOnMouseClicked(e -> eventHandler.onOpenHisTempPlot(station));
		OpenHisTable72hrButton.setOnMouseClicked(e -> eventHandler.onOpen72HrTable(station));
		OpenHisTableYearlyButton.setOnMouseClicked(e -> eventHandler.onOpenYearlyTable(station));
		addOption(OpenHisTempPlotButton);
		addOption(open72HrPlotButton);
		addOption(OpenHisTable72hrButton);
		addOption(OpenHisTableYearlyButton);
	}
	
	void removeOption(Parent node)
	{
		getChildren().remove(node);
	}
	
	void removeOptionTop()
	{
		getChildren().remove(0);
	}
	
	void addOptionTop(Parent node)
	{
		getChildren().add(0, node);
	}
	
	void addOption(Parent node)
	{
		getChildren().add(node);
	}

	public Station getStation() 
	{
		return station;
	}
}
