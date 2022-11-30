package mytunessys.gui.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import mytunessys.be.Genre;
import mytunessys.be.Playlist;
import mytunessys.be.Song;

/**
 * @author Bálint & Matej
 */
public class SongController{

    public void Show(AnchorPane centerContent){
        var Table = new TableView<>();
        Table.setLayoutX(14);
        Table.setFocusTraversable(false);

        var TitleCollumn = new TableColumn<>();
        TitleCollumn.setText("Title");
        TitleCollumn.prefWidthProperty().set(203);

        var GenreCollumn = new TableColumn<>();
        GenreCollumn.setText("Genre");
        GenreCollumn.prefWidthProperty().set(47);

        var DurationCollumn = new TableColumn<>();
        DurationCollumn.setText("Duration");
        DurationCollumn.prefWidthProperty().set(47);

        var OptionsCollumn = new TableColumn<>();
        OptionsCollumn.setText("Options");
        OptionsCollumn.prefWidthProperty().set(47);

        Table.editableProperty().set(false);
        Table.getColumns().add(TitleCollumn);
        Table.getColumns().add(GenreCollumn);
        Table.getColumns().add(DurationCollumn);
        Table.getColumns().add(OptionsCollumn);
        Table.setFocusTraversable(false);
        centerContent.getChildren().add(Table);
    }



}
