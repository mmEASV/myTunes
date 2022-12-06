package mytunessys.gui.models;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;

/**
 * @author Bálint, Matej & Tomas
 */

public class SongModel {

    private final ILogicFacade<Song> songManager;
    private ObservableList<Song> songs;

    public SongModel() {
        songManager = new LogicManager();
    }

    public ObservableList<Song> getAllSongs() throws ApplicationException {
        List<Song> temp =  songManager.getAllObject();
        return songs = FXCollections.observableArrayList(temp);
    }

    public void createSong(Song song) throws ApplicationException {
        songManager.createObject(song);
    }

    public void updateSong(Song song) throws ApplicationException {
        songManager.updateObject(song);
    }

    public boolean deleteSong(Song song) throws ApplicationException {
        return songManager.deleteObject(song);
    }
    public void searchSongs(String query) throws ApplicationException {
        List<Song> searched = songManager.searchObjects(songManager.getAllObject(), query);
        songs.clear();
        songs.addAll(searched);
    }

}
