package mytunessys.gui.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import mytunessys.be.Playlist;
import mytunessys.bll.LogicManager;

/**
 * @author Bálint & Matej
 */

public class PlaylistController {

    private List<Playlist> playlists;

    public PlaylistController(){
        //playlists = getAllPlaylists();
    }

    private List<Playlist> getAllPlaylists(){ //waiting on backend to develop
        //return logicManager.getAllPlaylists();
        return null;
    }

    private void displayPlaylistsOnListView(){ //waiting on backend to develop
        ObservableList<String> playlistNames = FXCollections.observableArrayList();
        for (Playlist p : playlists) {
            //playlistNames.add(p.getName());
        }


    }

}
