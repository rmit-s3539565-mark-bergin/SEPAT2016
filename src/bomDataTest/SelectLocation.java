package bomDataTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import bomData.Location;
import bomData.LocationList;
import bomData.WthrSample72hr;
import user.Favourite;
import user.FavouriteList;

public class SelectLocation 
{
	public static void main(String[] args) throws IOException 
	{
		Location matched = findLocation();
		matched.getWthrLastMonth("201603");
		//prints most recent observation.
		System.out.println(matched.getName() + " selected.\nFirst weather sample:");
		//Queries all the data regarding location
		Vector<WthrSample72hr> samples = matched.getWthrLast72hr();
		System.out.print(samples.get(0));
	}
	
	public static Location findLocation() throws IOException
	{
		Location match = null;
		int locationNumber = 1;	
		boolean locationFound = false;
		//Grabbing BOM locations/stations
	    LocationList locations = LocationList.getAllFromServer();
	    //User inputs location, searches for match
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));					
		System.out.println("Enter a search word:");
		String search = br.readLine();				
		//Works out number of potential locations
		LocationList foundLocs = locations.fuzzySearch(search);
		for (Location locationSearch : foundLocs) 
		{
			System.out.println("Location " + locationNumber + " : " + locationSearch.getName());
			locationNumber++;
	    }
		//Determines if only one potential location
		if(foundLocs.size() != 1)
		{
			//User chooses based on list of locations	
			while(locationFound == false)
	    {
			try
			{
				System.out.println("Enter Correct Location");
				String matchedLoc = br.readLine();	
				match = foundLocs.get(Integer.parseInt(matchedLoc) - 1);
				locationFound = true;
			}catch (ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Selected index not listed, please try again \n");
			}
	    }
			return  match;
		}
		//Else match is automatically chosen
		match = foundLocs.get(0);
		return match;
	}
	
}
