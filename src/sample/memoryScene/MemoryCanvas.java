package sample.memoryScene;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.Segment;
import sample.logic.MemoryRepository;
import sample.logic.Process;

import java.util.List;

public class MemoryCanvas {
    private List<Process> listOfProcesses;
    @FXML
    VBox container;
    final static int height = 70;

    @FXML
    void initialize(){
        listOfProcesses = MemoryRepository.getListOfProcesses();
        render();
    }

    void render(){
        for (Process p :
                listOfProcesses) {
            HBox processContainer = new HBox();
            Text processName = new Text(p.processName);
            Rectangle frame = new Rectangle(50,height,new Color(0,1,1,0.7));
            frame.setStroke(new Color(0,0,0,1));
            processContainer.getChildren().add(new StackPane(frame,processName));
            for (Segment s :
                    p.list) {
                Text segmentId = new Text("Id: " + (MemoryRepository.segmentCounter.get(s.getProcess()).indexOf(s.getSegmentId()) + 1));
                Text segmentName = new Text("Name: "+ s.getSegmentId());
                Text baseAddress = new Text("Base Address: "+String.valueOf(s.getBaseAdress()));
                Text limitSize = new Text("Limit Size: "+String.valueOf(s.getLimitSize()));
                VBox textContainer = new VBox();
                textContainer.setAlignment(Pos.CENTER);
                textContainer.getChildren().addAll(segmentId, segmentName,baseAddress,limitSize);
                Rectangle segmentFrame = new Rectangle(120,height,new Color(1,1,1,1));
                segmentFrame.setStroke(new Color(0,0,0,1));
                processContainer.getChildren().add(new StackPane(segmentFrame,textContainer));
            }
            ScrollPane scrollPane = new ScrollPane(new AnchorPane(processContainer));
            scrollPane.setPrefWidth(600);
            scrollPane.setPrefHeight(90);
            container.getChildren().add(scrollPane);
        }
    }
}
