package user;

import java.io.IOException;
import java.io.Serializable;

import data.Station;
import guiPlots.PlotLast72hrTemp;
import guiPlots.PlotBase;
import guiPlots.PlotHistoricalTemp;
import guiPlots.PlotType;
import guiPlots.PlotWindow;

public class PlotWindowSaved implements Serializable
{
	WindowProps props;
	Station station;
	PlotType type;
	
	public PlotWindowSaved(PlotWindow window)
	{
		PlotBase plot = window.getPlot();
		this.props = new WindowProps(window.getX(), window.getY(),
				window.getWidth(), window.getHeight());
		this.station = plot.getStation();
		if(plot instanceof PlotLast72hrTemp)
		{
			type = PlotType.Last72Hr;
		}
		else if(plot instanceof PlotHistoricalTemp)
		{
			type = PlotType.Historical;
		}
	}
	
	public PlotWindow restorePlotWindow()
	{
		PlotBase plot = null;
		switch (type)
		{
		case Historical:
			plot = new PlotHistoricalTemp(station);
			break;
		case Last72Hr:
			plot = new PlotLast72hrTemp(station);
			break;
		default:
			break;
		}
		PlotWindow window = new PlotWindow(plot);
		window.setX(props.xPos);
		window.setY(props.yPos);
		window.setWidth(props.xSize);
		window.setHeight(props.ySize);
		return window;
	}
}
