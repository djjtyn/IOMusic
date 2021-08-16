//This class is used to allow the candidate instructor to select multiple Artists in forms

package formHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ioMusicPublic.models.Artist;

public class ArtistList implements Iterable<Artist>{
	
	//List to hold the selected artists
	List<Artist> artistList = new ArrayList<>();
	
	//Iterator to iterate through the artists in the list
	@Override
	public Iterator<Artist> iterator() {
        Iterator<Artist> iterator = artistList.iterator();
        return iterator; 
	}
	
	//Getters and Setters
	public List<Artist> getArtistList() {
		return artistList;
	}

	public void setArtistList(List<Artist> artistList) {
		this.artistList = artistList;
	}
}
