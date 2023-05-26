package sample.maximizedMemory;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.AddressDiagram;
import sample.Segment;
import sample.SegmentCellFactory;
import sample.logic.MemoryRepository;

public class MaximizedMemoryCanvas {
    @FXML
    HBox container;
    ObservableList<Segment> list;
    @FXML
    void initialize(){
        list = MemoryRepository.getList();
        render();
        list.addListener((ListChangeListener<Segment>) c -> {
            render();
        });
    }

    void render(){
        container.getChildren().clear();
        AddressDiagram addressDiagram = new AddressDiagram(list,MemoryRepository.memorySize,18);
        container.getChildren().add(addressDiagram.build());
        VBox memoryD = new VBox();

        for (Segment segment : list) {
            SegmentCellFactory segmentCellFactory = new SegmentCellFactory(segment, MemoryRepository.memorySize,18);
            StackPane rectangle = segmentCellFactory.build();
            memoryD.getChildren().add(rectangle);
        }
        container.getChildren().add(memoryD);
    }
}
