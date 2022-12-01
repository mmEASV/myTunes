package mytunessys.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunessys.be.Genre;
import mytunessys.be.Song;
import mytunessys.bll.LogicManager;
import mytunessys.gui.models.SongModel;

/**
 * @author Bálint & Matej
 */
public class SongController{

    SongModel songModel = new SongModel();

    public void Show(AnchorPane centerContent){
        TableView<Song> Table = new TableView<>();
        Table.setFocusTraversable(false);

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

        TableColumn<Song, Object> OptionsColumn = new TableColumn<>();
        OptionsColumn.setText("Options");
        OptionsColumn.prefWidthProperty().set(47);
        OptionsColumn.setCellValueFactory(new PropertyValueFactory<Song, Object>("button"));//change this later

        Table.editableProperty().set(false);
        Table.getColumns().add(TitleColumn);
        Table.getColumns().add(GenreColumn);
        Table.getColumns().add(DurationColumn);
        Table.getColumns().add(OptionsColumn);
        Table.setFocusTraversable(false);
        centerContent.getChildren().add(Table);

        Table.setItems(songModel.getAllSongs());

    }
    public void NewSong(AnchorPane pageContent){
        var anchorPane = new AnchorPane();
        anchorPane.setMinWidth(400);
        anchorPane.setMinHeight(470);
        anchorPane.getStyleClass().add("new");
        pageContent.getChildren().add(anchorPane);

        var FormHolder = new AnchorPane();
        FormHolder.setLayoutX(36);
        FormHolder.setLayoutY(100);
        FormHolder.setMinWidth(300);
        FormHolder.setMinHeight(250);
        FormHolder.getStyleClass().add("form");
        anchorPane.getChildren().add(FormHolder);

        var vBoxHolder = new VBox();
        FormHolder.getChildren().add(vBoxHolder);

        var TopRow = new HBox();
        vBoxHolder.getChildren().add(TopRow);

        var songLabel = new Label("Add new Song");
        var BackButton = new Button("<-");
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.visibleProperty().set(false);

            }
        });
        TopRow.getChildren().addAll(songLabel,BackButton);

        var GetFileButton = new Button("File");
        var chooseFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.mp3)", "*.mp3");
        chooseFile.setSelectedExtensionFilter(extFilter);
        GetFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseFile.showOpenDialog(new Stage());
            }
        });
        vBoxHolder.getChildren().add(GetFileButton);

        var songRow = new HBox();
        var SongNameLabel = new Label("Song");
        var SongName = new TextField();
        songRow.getChildren().addAll(SongNameLabel,SongName);
        vBoxHolder.getChildren().addAll(songRow);

        var ArtistRow = new HBox();
        var ArtistNameLabel = new Label("Artist");
        var ArtistName = new TextField();
        ArtistRow.getChildren().addAll(ArtistNameLabel,ArtistName);
        vBoxHolder.getChildren().addAll(ArtistRow);
    }

}
