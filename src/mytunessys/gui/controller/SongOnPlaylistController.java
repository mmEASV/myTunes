package mytunessys.gui.controller;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Side;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;

import javafx.util.Callback;
import mytunessys.be.Playlist;
import mytunessys.be.Song;
import mytunessys.bll.exceptions.ApplicationException;

import mytunessys.bll.utilities.MusicPlayer;

import mytunessys.bll.utilities.AlertNotification;

import mytunessys.gui.models.PlaylistModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * @author Bálint
 */
public class SongOnPlaylistController {

    private final PlaylistModel playlistModel;

    private TableView<Song> table;
    private final BaseController baseController;
    private Playlist currentPlaylist;
    private boolean playlistChanged = false;

    public SongOnPlaylistController(PlaylistModel playlist, BaseController baseController) {
        this.playlistModel = playlist;
        this.baseController = baseController;
    }

    public TableView<Song> getTable() {
        return table;
    }

    public void fillTable() throws Exception {
        table.setItems(playlistModel.getPlaylistById(currentPlaylist));
    }

    public boolean getPlaylistChanged() {
        return playlistChanged;
    }

    public void setPlaylistChanged(boolean value) {
        playlistChanged = value;
    }

    public void Show(AnchorPane centerContent, Playlist playlist) throws Exception {

        currentPlaylist = playlist;
        table = new TableView<>();
        table.setFocusTraversable(false);
        table.getStyleClass().add("playlist-table");

        TableColumn<Song, String> TitleColumn = new TableColumn<>();
        TitleColumn.setText("Title");
        TitleColumn.prefWidthProperty().set(180);
        TitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));

        TableColumn<Song, String> GenreColumn = new TableColumn<>();
        GenreColumn.setText("Genre");
        GenreColumn.prefWidthProperty().set(47);
        GenreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));

        TableColumn<Song, String> DurationColumn = new TableColumn<>();
        DurationColumn.setText("Duration");
        DurationColumn.prefWidthProperty().set(70);
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

        table.setRowFactory(new Callback<TableView<Song>, TableRow<Song>>() {
            @Override
            public TableRow<Song> call(TableView<Song> param) {
                TableRow<Song> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            baseController.updatePlayerUI(param);
                        }
                    }
                });
                return row;
            }
        });

        TableColumn<Song, String> OptionsColumn = new TableColumn<>();
        OptionsColumn.prefWidthProperty().set(47);
        OptionsColumn.setResizable(false);

        Callback<TableColumn<Song, String>, TableCell<Song, String>> cellFactory
            = //
            new Callback<TableColumn<Song, String>, TableCell<Song, String>>() {
                @Override
                public TableCell call(final TableColumn<Song, String> param) {
                    final TableCell<Song, String> cell = new TableCell<Song, String>() {

                        final Button btn = new Button("...");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                MenuItem deleteSong = new MenuItem("Remove song");
                                // adding all items to context menu
                                var menu = new ContextMenu(deleteSong);

                                deleteSong.setOnAction(event -> {
                                    removeSongFromPlaylist(getTableRow().getItem());
                                    event.consume();
                                });
                                btn.setOnAction(event -> {
                                    menu.show(btn, Side.BOTTOM, 0, 0);
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
        table.getColumns().addAll(TitleColumn, GenreColumn, DurationColumn, OptionsColumn);
        table.setFocusTraversable(false);
        centerContent.getChildren().add(table);
        this.currentPlaylist = playlist;
        table.setItems(playlistModel.getPlaylistById(playlist));
        table.getSelectionModel().clearAndSelect(0);
    }

    /**
     * Removes a Song from the playlist and notifies the user if it was successful or not.
     *
     * @param item Needs to be a Song.
     */
    private void removeSongFromPlaylist(Song item) {
        boolean finalResult;
        try {
            HashMap<Integer, Song> listToBest = new HashMap<>();
            listToBest.put(item.getId(), item);
            currentPlaylist.setSongList(listToBest);
            finalResult = playlistModel.removeSongFromPlaylist(currentPlaylist);
            fillTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (finalResult) {
            AlertNotification.showAlertWindow("Successfully removed song with id " + item.getId(), Alert.AlertType.INFORMATION);
        } else {
            AlertNotification.showAlertWindow("Could not remove song " + item.getId(), Alert.AlertType.WARNING);
        }
    }

    /**
     * Removes song without a notification to the user.
     *
     * @param item Needs to be a Song.
     */
    private void removeSongWithoutPopup(Song item) {
        try {
            HashMap<Integer, Song> listToBest = new HashMap<>();
            listToBest.put(item.getId(), item);
            currentPlaylist.setSongList(listToBest);
            playlistModel.removeSongFromPlaylist(currentPlaylist);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <S, T> TableColumn<S, T> createColumn(String title,
        Function<S, ObservableValue<T>> property, double width) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setPrefWidth(width);
        return col;
    }

    /**
     * Moves songs inside the playlist up by 1.
     */
    public void moveUp() {
        if (table.getSelectionModel().getSelectedIndex() > 0) {
            int index = table.getSelectionModel().getSelectedIndex();

            table.getItems().add(index - 1, table.getItems().remove(index));
            table.getSelectionModel().clearAndSelect(index - 1);
            playlistChanged = true;
        }
    }

    /**
     * Moves songs inside the playlist down by 1.
     */
    public void moveDown() {

        if (table.getSelectionModel().getSelectedIndex() < table.getItems().size() - 1 &&
            table.getSelectionModel().getSelectedIndex() != -1) {

            int index = table.getSelectionModel().getSelectedIndex();

            table.getItems().add(index + 1, table.getItems().remove(index));
            table.getSelectionModel().clearAndSelect(index + 1);

            playlistChanged = true;
        }

    }

    /**
     * Shuffles the 2nd half of the songs up by a random amount inside the playlist.
     */
    public void shuffleSongs() {
        Random rnd = new Random();

        int middle = table.getItems().size() % 2 == 0 ? table.getItems().size() / 2 : table.getItems().size() / 2 + 1;
        int moveSongAmount;

        for (int i = 0; i <= middle; i++) {
            moveSongAmount = rnd.nextInt(0, middle + i);
            table.getSelectionModel().select(middle + i);

            for (int j = 0; j < moveSongAmount; j++) {
                moveUp();
            }

        }

    }

    /**
     * Saves the state of the current TableView inside the playlist into the DB.
     */
    public void savePlaylistState() {

        for (Song s : table.getItems()) {
            removeSongWithoutPopup(s);
        }

        for (Song s : table.getItems()) {
            try {
                playlistModel.addSongToPlaylist(s, currentPlaylist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
