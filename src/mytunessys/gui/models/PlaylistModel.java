package mytunessys.gui.models;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Playlist;
import mytunessys.bll.PlaylistManager;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.exceptions.ApplicationException;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistModel {

    private final ILogicFacade<Playlist> playlistManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel(){
        playlistManager = new PlaylistManager();
    }

    public ObservableList<Playlist> getAllPlaylists() throws ApplicationException {
        List<Playlist> temp =  playlistManager.getAllObject();
        return playlists = FXCollections.observableArrayList(temp);
    }
    public void createPlaylist(Playlist playlist) throws ApplicationException {
        playlistManager.createObject(playlist);
    }
    public void updatePlaylist(Playlist playlist) throws ApplicationException {
        playlistManager.updateObject(playlist);
    }
    public boolean deletePlaylist(Playlist playlist) throws ApplicationException {
        return playlistManager.deleteObject(playlist);
    }
    public void searchPlaylist(String query) throws ApplicationException {
        List<Playlist> searched = playlistManager.searchObjects(playlistManager.getAllObject(), query);
        playlists.clear();
        playlists.addAll(searched);
    }
}
