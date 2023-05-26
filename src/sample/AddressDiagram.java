package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


import java.util.List;

public class AddressDiagram {

    private List<Segment> segmentsList;
    private long totalSize;

    private static final int expectedCells = 25;
    private  float cellSize = 18; //Modified 70
    private static final int baseHeight = 48; //modified 40

    public AddressDiagram(List<Segment> segmentsList, long totalSize,float cellSize) {
        this.segmentsList = segmentsList;
        this.totalSize = totalSize;
        this.cellSize = cellSize;
    }

    public VBox build(){
        VBox container = new VBox();

        for(int i = 0; i < segmentsList.size(); i++) {
            long baseAddress = segmentsList.get(i).getBaseAdress();
            container.getChildren().add(new Text(String.valueOf(baseAddress)));
            if(i != 0)
                container.getChildren().add(box(segmentsList.get(i)));
            else {
                VBox offset = new VBox();
                offset.setPrefWidth(20);
                float height = baseHeight + segmentsList.get(i).getLimitSize() * ((expectedCells * cellSize / totalSize)) - 26;
                offset.setPrefHeight(height);
                container.getChildren().add(offset);
            }
        }
        long lastAddress = 0;
        if(segmentsList.size() != 0)
            lastAddress = segmentsList.get(segmentsList.size()-1).getLimitSize() + segmentsList.get(segmentsList.size()-1).getBaseAdress();
        container.getChildren().add(new Text(String.valueOf(lastAddress)));
        container.setPadding(new Insets(0,4,0,4));
        return container;
    }

    private VBox box(Segment segment){
        VBox offset = new VBox();
        offset.setPrefWidth(20);
        float height = baseHeight + segment.getLimitSize() * ((expectedCells * cellSize / totalSize)) - 16;
        offset.setPrefHeight(height);
        return offset;
    }

    }

