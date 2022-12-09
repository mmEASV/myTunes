package mytunessys.gui.models;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Song;
import mytunessys.bll.SongManager;
import mytunessys.bll.exceptions.*;
import mytunessys.bll.interfaces.ILogicFacade;

/**
 * @author Bálint, Matej & Tomas,Julian
 */

public class SongModel {

    private final ILogicFacade<Song> songManager;
    private ObservableList<Song> songs;

    public SongModel() throws ApplicationException {
        try {
            songManager = new SongManager();
        } catch (FactoryException e) {
            throw new ApplicationException(e.getMessage(),e.getCause());
        }
    }

    public ObservableList<Song> getAllSongs() throws Exception {
        List<Song> temp =  songManager.getAllObject();
        return songs = FXCollections.observableArrayList(temp);
    }

    public void createSong(Song song) throws Exception {
        songManager.createObject(song);
    }

    public void updateSong(Song song) throws Exception {
        songManager.updateObject(song);
    }

    public boolean deleteSong(Song song) throws Exception {
        return songManager.deleteObject(song);
    }
    public void searchSongs(String query) throws Exception {
        List<Song> searched = songManager.searchObjects(songManager.getAllObject(), query);
        songs.clear();
        songs.addAll(searched);
    }

}
