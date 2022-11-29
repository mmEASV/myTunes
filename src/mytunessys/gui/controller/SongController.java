package mytunessys.gui.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Playlist;
import mytunessys.be.Song;

/**
 * @author Bálint & Matej
 */
public class SongController extends BaseController{

    private List<Song> songs;


    private List<Song> getAllSongs() {
        return null;
    }

    private void displaySongsOnListView(){
        ObservableList<String> songTitles = FXCollections.observableArrayList();
        for (Song s : songs) {
            songTitles.add(s.getTitle());
        }

        contentList.setItems(songTitles);
    }

}
