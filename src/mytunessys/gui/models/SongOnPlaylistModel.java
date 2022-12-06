package mytunessys.gui.models;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.SongOnPlaylistManager;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.bll.interfaces.ILogicFacade;

/**
 * @author Bálint
 */
public class SongOnPlaylistModel {

    private ILogicFacade<SongOnPlaylist> songOnPlaylistManager;
    private ObservableList<Song> songs;

    private SongOnPlaylist songOnPlaylist;

    public SongOnPlaylistModel(){
        songOnPlaylistManager = new SongOnPlaylistManager();

    }

//    public ObservableList<Song> getAllSongsForPlaylist(Playlist playlist) throws ApplicationException {
//
//        List<Song> temp = new ArrayList<>();
//        for (SongOnPlaylist sOp :
//            songsOnPlaylist2) {
//            if (sOp.getPlaylist().getId() == playlist.getId())
//                temp.add(sOp.getSong());
//        }
//
//        return songs = FXCollections.observableArrayList(temp);
//    }

    public ObservableList<Song> getAllSongOnPlaylist() throws ApplicationException{
        List<SongOnPlaylist> temp =  songOnPlaylistManager.getAllObject();
        List<Song> songForPlaylist = new ArrayList<>();
        for (SongOnPlaylist s : temp
             ) {
            if(s.getPlaylist().getId() == 4){
                songForPlaylist.add(s.getSong());
            }
        }
        // return songs = FXCollections.observableArrayList(temp);
        return songs = FXCollections.observableArrayList(songForPlaylist);

    }

}
