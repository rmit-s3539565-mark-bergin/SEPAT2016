package gui;

import guiCallbacks.FavClicked;
import javafx.scene.input.MouseEvent;
import user.Favourite;
import user.FavouritesList;

public class ExplorerPaneFavourites extends ExplorerPane
{
	FavClicked clickHandler;
	// used for when favourites are selected
	public void createFavButtons(FavouritesList favs, 
    		FavClicked clickHandler)
    {
    	this.clickHandler = clickHandler;
    	for (Favourite fav : favs)
    	{
    		addFavourite(fav);
    	}
    }
	
	void onFavClicked(MouseEvent e)
	{
		FavButton button = (FavButton)e.getSource();
		clickHandler.favClicked(button.getFav());
	}
	
	public void addFavourite(Favourite fav)
	{
		FavButton node = new FavButton(fav);
		node.setOnMouseClicked(e -> onFavClicked(e));
		getVBox().getChildren().add(node);
        node.toFront();
	}
}