package mytunessys.gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

        var FormHolder = new AnchorPane();
        FormHolder.setLayoutX(36);
        FormHolder.setLayoutY(100);
        FormHolder.setMinWidth(300);
        FormHolder.setMinHeight(250);
        FormHolder.getStyleClass().add("form");

        var BackButton = new Button();
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.visibleProperty().set(false);
            }
        });
        BackButton.setText("<-");
        FormHolder.getChildren().add(BackButton);

        anchorPane.getChildren().add(FormHolder);

        pageContent.getChildren().add(anchorPane);
    }
}
