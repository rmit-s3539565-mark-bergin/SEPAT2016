package gui;

import data.Station;

/* Gui classes all send messages to this interface.
 * They are handled by your Main app class */
public interface GuiEventInterface 
{
	abstract void onOpen72TempPlot(Station station);
	abstract void onOpenHisTempPlot(Station station);
	abstract void onAddFav(Station station);
	abstract void onOpenYearlyTable (Station station);
	abstract void onOpen72HrTable (Station station);
}
