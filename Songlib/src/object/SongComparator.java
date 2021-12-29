package object;
//Krishna Patel kgp58
//William Yubeaton - wpy2
import java.util.*;
public class SongComparator implements Comparator<Song>{

		public int compare(Song s1, Song s2) {
	        if(s1.getName().compareToIgnoreCase(s2.getName()) == 0) {          
	            return s1.getArtist().compareToIgnoreCase(s2.getArtist());
	        } else {
	            return s1.getName().compareToIgnoreCase(s2.getName());
	        }
	    }
}
