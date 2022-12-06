package mytunessys.gui.models;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.PlaylistManager;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.exceptions.ApplicationException;

/**
 * @author Bálint, Matej & Tomas
 */

public class PlaylistModel {
    public static void main(String[] args) throws ApplicationException{
        PlaylistModel model = new PlaylistModel();
        var test = model.getPlaylistById(new Playlist(4,"test"));
        System.out.println("win");
    }

    private final ILogicFacade<Playlist> playlistManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel(){
        playlistManager = new PlaylistManager();
    }

    public ObservableList<Playlist> getAllPlaylists() throws ApplicationException {
        List<Playlist> temp =  playlistManager.getAllObject();
        return playlists = FXCollections.observableArrayList(temp);
    }

    public ObservableList<Song> getPlaylistById(Playlist playlist) throws ApplicationException {
        List<Song> songs = new ArrayList<>(playlistManager.getObjectById(playlist).getSongList().values());
        return FXCollections.observableArrayList(songs);
    }
    public void createPlaylist(Playlist playlist) throws ApplicationException {
        this.playlistManager.createObject(playlist);
    }
    public void updatePlaylist(Playlist playlist) throws ApplicationException {
        this.playlistManager.updateObject(playlist);
    }
    public boolean deletePlaylist(Playlist playlist) throws ApplicationException {
        return this.playlistManager.deleteObject(playlist);
    }
    public void searchPlaylist(String query) throws ApplicationException {
        List<Playlist> searched = playlistManager.searchObjects(playlistManager.getAllObject(), query);
        playlists.clear();
        playlists.addAll(searched);
    }
}
