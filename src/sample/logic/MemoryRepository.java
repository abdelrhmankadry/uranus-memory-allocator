package sample.logic;

import javafx.collections.ObservableList;
import sample.Segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemoryRepository {
    static public ObservableList<Segment> list;
    static public long memorySize;
    static public Map<String,List<String>> segmentCounter = new HashMap<>();
    public static ObservableList<Segment> getList() {
        return list;
    }

    public static void setList(ObservableList<Segment> list) {
        MemoryRepository.list = list;
    }


    public static List<Process> getListOfProcesses(){
        List<String>   listOfNames = new ArrayList<>();
        List<Process> listOfProcesses = new ArrayList<>();
        for (Segment s :
                list) {
            if(!listOfNames.contains(s.getProcess()) && !s.getProcess().equals("") ){
                listOfNames.add(s.getProcess());
            }
        }
        for (String s :
                listOfNames) {
            listOfProcesses.add(new Process(list.stream()
                    .filter(segment -> segment.getProcess().equals(s))
                    .collect(Collectors.toList()), s));
        }
        return listOfProcesses;
    }

    static public void addSegment(String process, String segment){
        if(! segmentCounter.containsKey(process)){
            segmentCounter.put(process,new ArrayList<>());
        }
        List<String> segments = segmentCounter.get(process);
        segments.add(segment);
        segmentCounter.put(process,segments);

    }
}
