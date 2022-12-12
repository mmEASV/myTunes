package mytunessys.bll.helpers;

import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper implements ISearchHelper{

    public List<Song> searchSongs(List<Song> listToSearch, String query){
        List<Song> filtered = new ArrayList<>();
        for (Song s:
                listToSearch) {
            if(s.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    s.getArtist().toLowerCase().contains(query.toLowerCase()) ||
                    s.getGenre().getName().toLowerCase().contains(query.toLowerCase())){
                filtered.add(s);
            }
        }
        return filtered;
    }

    public List<Playlist> searchPlaylists(List<Playlist> listToSearch, String query){
        List<Playlist> filtered = new ArrayList<>();
        for (Playlist p:
                listToSearch) {
            if(p.getPlaylistName().toLowerCase().contains(query.toLowerCase())){
                filtered.add(p);
            }
        }
        return filtered;
    }

}
