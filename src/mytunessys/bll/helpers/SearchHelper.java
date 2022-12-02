package mytunessys.bll.helpers;

import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper {

    public List<Object> searchSongs(List<Object> listToSearch, String query){
        List<Song> castedList = (List<Song>) (Object) listToSearch;
        List<Object> filtered = new ArrayList<>();
        for (Song s:
                castedList) {
            if(s.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    s.getArtist().toLowerCase().contains(query.toLowerCase()) ||
                    s.getGenre().getName().toLowerCase().contains(query.toLowerCase())){
                filtered.add(s);
            }
        }
        return filtered;
    }

    public List<Object> searchPlaylists(List<Object> listToSearch, String query){
        List<Playlist> castedList = (List<Playlist>) (Object) listToSearch;
        List<Object> filtered = new ArrayList<>();
        for (Playlist p:
                castedList) {
            if(p.getPlaylistName().toLowerCase().contains(query.toLowerCase())){
                filtered.add(p);
            }
        }
        return filtered;
    }

}
