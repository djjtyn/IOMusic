//This class is used to allow the candidate instructor to select multiple Instruments in forms

package formHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ioMusicPublic.models.Instrument;

public class InstrumentList implements Iterable<Instrument>{
	
	//List to hold the selected instruments
	List<Instrument> instrumentList = new ArrayList<>();
	
	//Iterator to iterate through the artists in the list
	@Override
	public Iterator<Instrument> iterator() {
		Iterator<Instrument> iterator = instrumentList.iterator();
		return iterator;
	}

	//Getters and Setters
	public List<Instrument> getInstrumentList() {
		return instrumentList;
	}

	public void setInstrumentList(List<Instrument> instrumentList) {
		this.instrumentList = instrumentList;
	}
}
