package mytunessys.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author Bálint & Matej
 */
public class SongController{

    public void Show(AnchorPane centerContent){
        var Table = new TableView<>();
        Table.setFocusTraversable(false);

        var TitleColumn = new TableColumn<>();
        TitleColumn.setText("Title");
        TitleColumn.prefWidthProperty().set(203);

        var GenreColumn = new TableColumn<>();
        GenreColumn.setText("Genre");
        GenreColumn.prefWidthProperty().set(47);

        var DurationColumn = new TableColumn<>();
        DurationColumn.setText("Duration");
        DurationColumn.prefWidthProperty().set(47);

        var OptionsColumn = new TableColumn<>();
        OptionsColumn.setText("Options");
        OptionsColumn.prefWidthProperty().set(47);

        Table.editableProperty().set(false);
        Table.getColumns().add(TitleColumn);
        Table.getColumns().add(GenreColumn);
        Table.getColumns().add(DurationColumn);
        Table.getColumns().add(OptionsColumn);
        Table.setFocusTraversable(false);
        centerContent.getChildren().add(Table);
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
