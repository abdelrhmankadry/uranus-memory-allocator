package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;




public class SegmentCellFactory {

    private Segment segment;
    private long memorySize;

    private static final int expectedCells = 25;
    private  float cellSize = 18; //Modified 70
    private static final int width = 90;
    private static final int space = 0;
    private static final int baseHeight = 48;//Modified 40

    private Color holeColor = new Color(1,1,1,1);
    private Color processColor = new Color((67.0/255),(227.0/255),1,0.7);
    private Color strokeColor = new Color(0,0,0,1);

    public SegmentCellFactory(Segment segment, long memorySize, float cellSize) {
        this.segment = segment;
        this.memorySize = memorySize;
        this.cellSize = cellSize;
    }

    public StackPane build(){

        float height = baseHeight + segment.getLimitSize() * ((expectedCells * cellSize / memorySize));
        Rectangle rectangle;

        if(! segment.getSegmentId().equals("Hole") )
            rectangle = new Rectangle(width,height,processColor);
        else
            rectangle = new Rectangle(width,height,holeColor);
        rectangle.setStroke(strokeColor);

        Text processId = new Text(segment.getProcess());
        Text segmentId = new Text(segment.getSegmentId());
        Text size = new Text(segment.getLimitSize() + " kb");

        VBox container = new VBox(space);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(processId,segmentId,size);


        return new StackPane(rectangle,container);
    }
}
