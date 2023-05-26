package sample.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Segment;


import java.util.ArrayList;

import java.util.List;

public class MemoryManger {

    private String algorithmType;
    private long memorySize;
    private ObservableList<Segment> observableList;

    public MemoryManger(String algorithmType, long memorySize) {
        this.algorithmType = algorithmType;
        this.memorySize = memorySize;
        List<Segment> list = new ArrayList<>();
        list.add(new Segment("segment1","",0,memorySize));
        observableList = FXCollections.observableList(list);
    }

    public void addHole(Segment hole){
        List<Segment> list = new ArrayList<>(observableList);
        List<Segment > newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Segment s = list.get(i);

            if (hole.getSegmentId().equals("Hole") &&
                    !s.getSegmentId().equals("Hole")&&
                    hole.getBaseAdress() >= s.getBaseAdress() &&
                    hole.getBaseAdress() <= s.getBaseAdress() + s.getLimitSize()) {

                List<Segment> newSegments = insertHole(s, hole);

                if(list.size() > 1 && i != 0)
                    newList.addAll(list.subList(0,i));

                newList.addAll(newSegments);

                if(list.size() > 1 && i != list.size())
                    newList.addAll(list.subList(i+1,list.size()));
                break;
            }
        }
        observableList.clear();
        observableList.addAll(combineHoles(newList));
    }

    public boolean addSegment(Segment segment){
        List<Segment> list = new ArrayList<>(observableList);
        List<Segment > newList = new ArrayList<>();
        List<Segment> newSegments;
        boolean found = false;
        System.out.println(algorithmType);
        if(algorithmType.equals("First Fit")){
            for (int i = 0; i < list.size(); i++) {
                Segment s = list.get(i);
                if(s.getSegmentId().equals("Hole") && s.getLimitSize() >= segment.getLimitSize()){
                    segment.setBaseAdress(s.getBaseAdress());
                    found = true;
                     newSegments = insertSegment(s,segment);
                     newList = buildNewList(list,newSegments,i);
                    break;
                }

            }
        }
        else {
            long min = 999999999;
            Segment fitSegment = null;
            int index =0;
            for (int i = 0; i < list.size(); i++) {
                Segment s = list.get(i);
                if (s.getSegmentId().equals("Hole") &&
                        s.getLimitSize() >= segment.getLimitSize() &&
                        s.getLimitSize() - segment.getLimitSize() < min) {
                    min = s.getLimitSize() - segment.getLimitSize();
                    fitSegment = s;
                    index = i;
                }
            }
            if(fitSegment != null){
                found = true;
                segment.setBaseAdress(fitSegment.getBaseAdress());
                newSegments = insertSegment(fitSegment,segment);
                newList = buildNewList(list,newSegments,index);
            }
        }

        if(found){
        observableList.clear();
        observableList.addAll(newList);
        }
        return found;
    }

    private List<Segment> buildNewList(List<Segment> list, List<Segment> newSegments,int index) {
        List<Segment> newList = new ArrayList<>();
        if(list.size() > 1 && index != 0)
            newList.addAll(list.subList(0,index));

        newList.addAll(newSegments);

        if(list.size() > 1 && index != list.size())
            newList.addAll(list.subList(index+1,list.size()));
        return newList;
    }

    private List<Segment> insertSegment(Segment s, Segment segment) {
        List<Segment> newSegments = new ArrayList<>();
        newSegments.add(segment);
        if(s.getLimitSize() != segment.getLimitSize()){
            long newBaseAddress = segment.getBaseAdress() + segment.getLimitSize();
            long newLimitSize = s.getLimitSize() - segment.getLimitSize();
            newSegments.add(new Segment("Hole","",newBaseAddress,newLimitSize));
        }
        return newSegments;
    }

    private List<Segment> insertHole(Segment container, Segment hole) {
        List<Segment> newSegments = new ArrayList<>();

        long holeEndAddress = hole.getBaseAdress() + hole.getLimitSize();
        long containerEndAddress = container.getBaseAdress() + container.getLimitSize();

        if(hole.getLimitSize() == container.getLimitSize()){
            newSegments.add(hole);
        }
        else if(hole.getBaseAdress() == container.getBaseAdress()){
            newSegments.add(hole);
            long newLimitSize = container.getLimitSize() - hole.getLimitSize();
            newSegments.add(new Segment(container.getSegmentId(),"", holeEndAddress,newLimitSize));
        }
        else if(hole.getBaseAdress() > container.getBaseAdress() &&
                holeEndAddress < containerEndAddress){
            long segmentOneNewLimitSize = hole.getBaseAdress() - container.getBaseAdress();
            newSegments.add(new Segment(container.getSegmentId(),"",container.getBaseAdress(),segmentOneNewLimitSize));

            newSegments.add(hole);

            String oldName = container.getSegmentId();
            String newName = oldName.substring(0,oldName.length()-1) +
                    (Integer.parseInt(oldName.charAt(oldName.length()-1)+"") + 1);
            long segmentTwoNewLimitSize = containerEndAddress - holeEndAddress;
            newSegments.add(new Segment(newName,"",holeEndAddress,segmentTwoNewLimitSize));

        }
        else{
            long newLimitSize = hole.getBaseAdress() - container.getBaseAdress();
            newSegments.add(new Segment(container.getSegmentId(),"",container.getBaseAdress(),newLimitSize));
            newSegments.add(hole);
        }

        return newSegments;
    }

    public ObservableList<Segment> getObservableList() {
        return observableList;
    }

    public void setAlgorithmType(String algorithm) {
        algorithmType = algorithm;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
        if(observableList.size() == 1){
            observableList.get(0).setLimitSize(memorySize);
        } if(observableList.size() == 0){
            observableList.add(new Segment("segment1","",0,memorySize));
        }
    }

    public void deleteProcess(String processId){
        List<Segment> deletedList= new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            Segment segment = observableList.get(i);
            if (segment.getProcess().equals(processId))
                observableList.set(i,new Segment("Hole","",segment.getBaseAdress(),segment.getLimitSize()));
        }
        List<Segment> temp = new ArrayList<>(observableList);
        observableList.clear();
        observableList.addAll(combineHoles(temp));

    }
    public void deleteSegment(String segmentId){
        List<Segment> deletedList= new ArrayList<>();
        for (int i = 0; i < observableList.size(); i++) {
            Segment segment = observableList.get(i);
            if (segment.getSegmentId().equals(segmentId))
                observableList.set(i,new Segment("Hole","",segment.getBaseAdress(),segment.getLimitSize()));
        }
        List<Segment> temp = new ArrayList<>(observableList);
        observableList.clear();
        observableList.addAll(combineHoles(temp));

    }
    private List<Segment> combineHoles(List<Segment> list){
        for (int i = 0; i < list.size() - 1; i++) {

            while(list.get(i).getSegmentId().equals("Hole") && list.get(i + 1).getSegmentId().equals("Hole")){
                Segment temp = combineHelper(list.get(i), list.get(i + 1));
                list.remove(i + 1);
                list.set(i,temp);
                if(list.size() == i + 1)
                    break;
            }
        }
        return list;
    }
    private Segment combineHelper(Segment s1, Segment s2){
        return new Segment(s1.getSegmentId(), "",s1.getBaseAdress(),s1.getLimitSize()+s2.getLimitSize());
    }

    public void newOperation() {
        observableList.clear();
    }
}
