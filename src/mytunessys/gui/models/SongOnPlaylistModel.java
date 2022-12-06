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

    private ILogicFacade songOnPlaylistManager;
    private ObservableList<Song> songs;
    List<Object> songsAndPlaylistsIn1List;

    //region testing data
    //playlists
    private Playlist playlist1 = new Playlist(1,"testing playlist 1");
    private Playlist playlist2 = new Playlist(2,"testing playlist 2");
    //songs
    private Song song1 = new Song(1,"baby","3300","JB","user/asd",new Genre(1,"pop"));
    private Song song2 = new Song(2,"baby2","3300","JB","user/asd2",new Genre(1,"pop"));
    private Song song3 = new Song(3,"baby3","3300","JB","user/asd3",new Genre(2,"pop2"));
    //songs on playlist
    private List<SongOnPlaylist> songsOnPlaylist1 = new ArrayList<>();
    private List<SongOnPlaylist> songsOnPlaylist2 = new ArrayList<>();

    private SongOnPlaylist songOnPlaylist1 = new SongOnPlaylist(song1,playlist1);
    private SongOnPlaylist songOnPlaylist2 = new SongOnPlaylist(song2,playlist1);

    private SongOnPlaylist songOnPlaylist3 = new SongOnPlaylist(song1,playlist2);
    private SongOnPlaylist songOnPlaylist4 = new SongOnPlaylist(song2,playlist2);
    private SongOnPlaylist songOnPlaylist5 = new SongOnPlaylist(song3,playlist2);


    private SongOnPlaylist songOnPlaylist;


    //endregion

    public SongOnPlaylistModel(){
        songOnPlaylistManager = new SongOnPlaylistManager();
        //region fill testing data
        songsOnPlaylist1.add(songOnPlaylist1);
        songsOnPlaylist1.add(songOnPlaylist2);

        songsOnPlaylist2.add(songOnPlaylist3);
        songsOnPlaylist2.add(songOnPlaylist4);
        songsOnPlaylist2.add(songOnPlaylist5);
        //endregion
    }

    public ObservableList<Song> getAllSongsForPlaylist(Playlist playlist) throws ApplicationException {

        List<Song> temp = new ArrayList<>();
        for (SongOnPlaylist sOp :
            songsOnPlaylist2) {
            if (sOp.getPlaylist().getId() == playlist.getId())
                temp.add(sOp.getSong());
        }
        
        return songs = FXCollections.observableArrayList(temp);
    }

}
