package sample;


import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.logic.MemoryManger;
import sample.logic.MemoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    HBox memoryDiagram;
    @FXML
    TextField MemorySizeTextEditor;
    @FXML
    TextField HoleBaseAddressTextEditor;
    @FXML
    TextField holeLimitSizeTextEditor;
    @FXML
    ChoiceBox<String> algorithmChoiseBox;
    @FXML
    TextField processIdTextEditor;
    @FXML
    TextField segmentIdTextEditor;

    @FXML
    TextField segmentLimitSizeTextEditor;
    @FXML
    AnchorPane scrollPane;
    @FXML
    Label erroText;
    private ObservableList<Segment> list;
    private MemoryManger memoryManger;
    private String algorithmType = "First Fit";
    private long memorySize = 0;

    @FXML
    void initialize() {
         memoryManger = new MemoryManger(algorithmType,memorySize);
        list = memoryManger.getObservableList();
        //render(new ArrayList<>(list),10000);
        list.addListener((ListChangeListener<Segment>) c -> {
            if(list.size() > 0){
                render(new ArrayList<>(list),memorySize);
            }
        });
        initializeChoiceBox();
        initializeMemorySizeTextField();
    }

    private void render(List<Segment> list, long memorySize){

        memoryDiagram.getChildren().clear();
        AddressDiagram addressDiagram = new AddressDiagram(list,memorySize,40);
        memoryDiagram.getChildren().add(addressDiagram.build());
        VBox memoryD = new VBox();

        for (Segment segment : list) {
            SegmentCellFactory segmentCellFactory = new SegmentCellFactory(segment, memorySize,40);
            StackPane rectangle = segmentCellFactory.build();
            String ProcessName = extractProcessName(rectangle);
            rectangle.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        if(!ProcessName.equals(""))
                            memoryManger.deleteProcess(ProcessName);
                        else
                            memoryManger.deleteSegment(extractSegmentName(rectangle));
                    }

                }
            });
            memoryD.getChildren().add(rectangle);
        }
        memoryDiagram.getChildren().add(memoryD);
    }


    public void onAddHoleClicked(ActionEvent actionEvent) {
        long holeBaseAddress = Long.parseLong(HoleBaseAddressTextEditor.getText());
        long holeLimitSize = Long.parseLong(holeLimitSizeTextEditor.getText());
        String id = "Hole";
        memoryManger.addHole(new Segment(id,"",holeBaseAddress,holeLimitSize));
    }

    void initializeChoiceBox(){

        algorithmChoiseBox.getItems().addAll("First Fit", "Best Fit");
        algorithmChoiseBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    switch (newValue.intValue()){
                        case 0 : memoryManger.setAlgorithmType("First Fit"); break;
                        case 1 : memoryManger.setAlgorithmType("Best Fit"); break;
                    }
                });
    }

    void initializeMemorySizeTextField(){
        MemorySizeTextEditor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                memorySize = Long.parseLong(MemorySizeTextEditor.getText());
                memoryManger.setMemorySize(memorySize);
                render(list,memorySize);
            }
        });
    }

    public void onAddSegmentClicked(ActionEvent actionEvent) {
        String processId = processIdTextEditor.getText();
        String segmentId = segmentIdTextEditor.getText();

        long limitSize = Long.parseLong(segmentLimitSizeTextEditor.getText());

        boolean flag = memoryManger.addSegment(new Segment(segmentId,processId,0,limitSize));
        if(flag){
            MemoryRepository.addSegment(processId,segmentId);
            erroText.setText("");
        } else{
            erroText.setText("*There is no room do add this Process to the Memory,\n deallocate any process first");
        }

    }
    private String extractProcessName(StackPane rectangle){
        VBox container = (VBox) rectangle.getChildren().get(1);
        Text processId = (Text) container.getChildren().get(0);
        return processId.getText();
    }
    private String extractSegmentName(StackPane rectangle){
        VBox container = (VBox) rectangle.getChildren().get(1);
        Text segmentId = (Text) container.getChildren().get(1);
        return segmentId.getText();
    }

    public void onNewOperationClicked(ActionEvent actionEvent) {
        memoryManger.newOperation();
        render(list,0);
        MemorySizeTextEditor.setText("");
        HoleBaseAddressTextEditor.setText("");
        holeLimitSizeTextEditor.setText("");
        algorithmChoiseBox.setValue("First Fit");
        processIdTextEditor.setText("");
        segmentIdTextEditor.setText("");
        segmentLimitSizeTextEditor.setText("");
    }

    public void onShowProcessesClicked(ActionEvent actionEvent) throws IOException {
        MemoryRepository.setList(list);
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("memoryScene/memoryCanvas.fxml"));
        primaryStage.setTitle("Processes");
        Scene scene = new Scene(root, 600,600 );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onShowMemoryClicked(ActionEvent actionEvent) throws IOException {
        MemoryRepository.setList(list);
        MemoryRepository.memorySize = memorySize;
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("maximizedMemory/maximizedMemoryCanvas.fxml"));
        primaryStage.setTitle("Processes");
        Scene scene = new Scene(root, 200,700 );
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
