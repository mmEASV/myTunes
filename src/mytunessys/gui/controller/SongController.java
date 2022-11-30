package mytunessys.gui.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Bálint & Matej
 */
public class SongController{

    public void Show(AnchorPane centerContent){
        var Table = new TableView<>();
        Table.setLayoutX(14);
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



}
