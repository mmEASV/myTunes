package mytunessys.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.interfaces.ILogicFacade;

public class PlaylistModel {

    private ILogicFacade logicManager = new LogicManager();
    ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public PlaylistModel(){
        playlists.add(new Playlist(1,"Hello"));
    }

    public ObservableList<Playlist> getAllPlaylists(){ //waiting for backend to develop
        //return logicManager.getAllPlaylists();
        return playlists;
    }


}
