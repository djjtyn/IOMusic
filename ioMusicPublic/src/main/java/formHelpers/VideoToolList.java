//This class is used to allow the candidate instructor to select multiple Video tools in forms

package formHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import ioMusicPublic.models.VideoTool;

public class VideoToolList implements Iterable<VideoTool>{
	
	
	//List to hold the selected video tools
	List<VideoTool> videoToolList = new ArrayList<>();
	
	//Iterator to iterate through the video tools in the list
	@Override
	public Iterator<VideoTool> iterator() {
        Iterator<VideoTool> iterator = videoToolList.iterator();
        return iterator;
	}
	
	//Getters and Setters
	public List<VideoTool> getVideoToolList() {
		return videoToolList;
	}

	public void setVideoToolList(List<VideoTool> videoToolList) {
		this.videoToolList = videoToolList;
	}
}
