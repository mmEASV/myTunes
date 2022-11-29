package mytunessys.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.bll.interfaces.ILogicFacade;

public class SongModel {

    private ILogicFacade logicManager = new LogicManager();
    ObservableList<Song> songs = FXCollections.observableArrayList();

    public SongModel(){ //waiting for backend to develop
        //songs = logicManager.getAllSongs();

        songs.add(new Song(1, "Baby", "3300", "JB", "users/yay",new Genre(1,"POP")));
        songs.add(new Song(2, "Baby2", "3302", "JayB", "users/woo",new Genre(2,"POPPY")));
    }

    public ObservableList<Song> getAllSongs(){ //waiting for backend to develop
        //return logicManager.getAllSongs();
        return songs;
    }

}
