package mytunessys.bll.helpers;

import mytunessys.be.Playlist;
import mytunessys.be.Song;

import java.util.List;

/**
 * @author Julian,Tomas
 */
public interface ISearchHelper {
    /**
     * method that searches list of songs by its title or artist
     * @param listToSearch list that will be iterated
     * @param query that is searched string needed to be found
     * @return list of songs by its title or artist
     */
    List<Song> searchSongs(List<Song> listToSearch, String query);

    /**
     * method that searches list of songs by its title or artist
     * @param listToSearch list that will be iterated
     * @param query that is searched string needed to be found
     * @return list of songs by its title or artist
     */
    List<Playlist> searchPlaylists(List<mytunessys.be.Playlist> listToSearch, String query);
}