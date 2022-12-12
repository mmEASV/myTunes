package mytunessys.gui.models;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.PlaylistManager;
import mytunessys.bll.exceptions.FactoryException;
import mytunessys.bll.interfaces.ILogicFacade;
import mytunessys.bll.exceptions.ApplicationException;

/**
 * @author Bálint, Matej & Tomas,Julian
 */

public class PlaylistModel {

    private final ILogicFacade<Playlist> playlistManager;
    private ObservableList<Playlist> playlists;

    public PlaylistModel() throws ApplicationException {
        try {
            playlistManager = new PlaylistManager();
        } catch (FactoryException e) {
            throw new ApplicationException(e.getMessage(),e.getCause());
        }
    }

    public ObservableList<Playlist> getAllPlaylists() throws Exception {
        List<Playlist> temp =  playlistManager.getAllObject();
        return playlists = FXCollections.observableArrayList(temp);
    }

    public ObservableList<Song> getPlaylistById(Playlist playlist) throws Exception {
        List<Song> songs = new ArrayList<>(playlistManager.getObjectById(playlist).orElseThrow().getSongList().values());
        return FXCollections.observableArrayList(songs);
    }

    public boolean addSongToPlaylist(Song song,Playlist playlist) throws Exception {
        return this.playlistManager.addToObject(song,playlist);
    }

    public void createPlaylist(Playlist playlist) throws Exception {
        this.playlistManager.createObject(playlist);
    }
    public void updatePlaylist(Playlist playlist) throws Exception {
        this.playlistManager.updateObject(playlist);
    }
    public boolean deletePlaylist(Playlist playlist) throws Exception {
        return this.playlistManager.deleteObject(playlist);
    }
    public void searchPlaylist(String query) throws Exception {
        List<Playlist> searched = playlistManager.searchObjects(playlistManager.getAllObject(), query);
        playlists.clear();
        playlists.addAll(searched);
    }
    public boolean removeSongFromPlaylist(Playlist playlist) throws Exception{
        return this.playlistManager.removeObjectFrom(playlist);
    }
}
