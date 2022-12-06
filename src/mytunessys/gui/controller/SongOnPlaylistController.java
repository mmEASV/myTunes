package mytunessys.gui.controller;

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

/**
 * @author Bálint
 */
public class SongOnPlaylistController {

    SongOnPlaylistModel songOnPlaylistModel;
    AnchorPane contentWindow;

    PlaylistModel playlist;

    ObservableList<Song> currentSongsInPlaylist;


    public SongOnPlaylistController(AnchorPane contentWindow,SongOnPlaylistModel model,PlaylistModel playlist) {
        this.contentWindow = contentWindow;
        this.songOnPlaylistModel = model;
        this.playlist = playlist;
       // this.currentSongsInPlaylist = playlist;
    }

    public void Show(AnchorPane centerContent) throws ApplicationException {

        TableView<Song> table = new TableView<>();
        table.setFocusTraversable(false);

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
        DurationColumn.prefWidthProperty().set(47);
        DurationColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("duration"));

        TableColumn<Song, String> OptionsColumn = new TableColumn<>();
        OptionsColumn.prefWidthProperty().set(47);

        Callback<TableColumn<Song, String>, TableCell<Song, String>> cellFactory
            = //
            new Callback<TableColumn<Song, String>, TableCell<Song, String>>() {
                @Override
                public TableCell call(final TableColumn<Song, String> param) {
                    final TableCell<Song, String> cell = new TableCell<Song, String>() {

                        final Button btn = new Button("...");
                        final ContextMenu menu = new ContextMenu(new MenuItem("edit song"));
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    menu.show(btn, Side.BOTTOM,0,0);
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

        OptionsColumn.setCellFactory(cellFactory);
        table.editableProperty().set(false);
        table.getColumns().addAll(TitleColumn,GenreColumn,DurationColumn,OptionsColumn);
        table.setFocusTraversable(false);

        centerContent.getChildren().add(table);
        table.setItems(currentSongsInPlaylist);

    }
}
