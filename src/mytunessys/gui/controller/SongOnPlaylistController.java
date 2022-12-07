package mytunessys.gui.controller;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableValue; //remove later with other data
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.be.SongOnPlaylist;
import mytunessys.bll.exceptions.ApplicationException;
import mytunessys.gui.models.PlaylistModel;
import mytunessys.gui.models.SongModel;
import mytunessys.gui.models.SongOnPlaylistModel;

import java.util.List;
import java.util.Random;
import java.util.function.Function; //remove later with other data

/**
 * @author Bálint
 */
public class SongOnPlaylistController {

    private SongOnPlaylistModel songOnPlaylistModel;
    private AnchorPane contentWindow;

    private PlaylistModel playlistModel;

    private ObservableList<Song> currentSongsInPlaylist;

    private TableView<Song> table;



    public SongOnPlaylistController(AnchorPane contentWindow,SongOnPlaylistModel model,PlaylistModel playlist) {
        this.contentWindow = contentWindow;
        this.songOnPlaylistModel = model;
        this.playlistModel = playlist;
       // this.currentSongsInPlaylist = playlist;
    }

    public void Show(AnchorPane centerContent,Playlist playlist) throws ApplicationException {

        table = new TableView<>();
        table.setFocusTraversable(false);

        ReadOnlyIntegerProperty selectedIndex = table.getSelectionModel().selectedIndexProperty();

        TableColumn<Song, String> TitleColumn = new TableColumn<>();
        TitleColumn.setText("Title");
        TitleColumn.prefWidthProperty().set(203);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));

        TableColumn<Song, String> GenreColumn = new TableColumn<>();
        GenreColumn.setText("Genre");
        GenreColumn.prefWidthProperty().set(47);
        GenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));


        TableColumn<Song, String> DurationColumn = new TableColumn<>();
        DurationColumn.setText("Duration");
        DurationColumn.prefWidthProperty().set(94);
        DurationColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("duration"));

        TableColumn<Song, Void> indexCol = createColumn("Index", Song -> null, 0);
        indexCol.setCellFactory(col -> new TableCell<Song, Void>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index));
                }
            }
        });


        table.editableProperty().set(false);
        table.getColumns().addAll(TitleColumn,GenreColumn,DurationColumn);
        table.setFocusTraversable(false);

        centerContent.getChildren().add(table);
        table.setItems(playlistModel.getPlaylistById(playlist));
    }

    private <S, T> TableColumn<S, T> createColumn(String title,
        Function<S, ObservableValue<T>> property, double width) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setPrefWidth(width);
        return col;
    }

    public void moveUp(){
        if(table.getSelectionModel().getSelectedIndex() > 0){
            int index = table.getSelectionModel().getSelectedIndex();
            table.getItems().add(index-1, table.getItems().remove(index));
            table.getSelectionModel().clearAndSelect(index-1);
        }


    }

    public void moveDown(){
        if(table.getSelectionModel().getSelectedIndex() < table.getItems().size() - 1 &&
            table.getSelectionModel().getSelectedIndex() != -1){
            int index = table.getSelectionModel().getSelectedIndex();
            table.getItems().add(index+1, table.getItems().remove(index));
            table.getSelectionModel().clearAndSelect(index+1);
        }
    }
    //TODO need to save the positions in the database (idea: maybe with a save song order button?)

    public void shuffleSongs(){         //need to test
        Random rnd = new Random();
        //moveUp the 2nd half of the songs by a random value

        int middle;
        int moveSongAmount;

        if(table.getItems().size() % 2 == 0)
            middle = table.getItems().size() / 2;
        else
            middle = (table.getItems().size() / 2) + 1;

        for (int i = middle; i < table.getItems().size(); i++) {    //for every row after the middle point
            moveSongAmount = rnd.nextInt(table.getSelectionModel().getSelectedIndex());
            //move up by the random amount
            table.getItems().add(i-moveSongAmount, table.getItems().remove(i));
            table.getSelectionModel().clearAndSelect(i-moveSongAmount);
        }


    }

}
