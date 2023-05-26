package sample.logic;

import sample.Segment;

import java.util.List;

public class Process {
    public List<Segment> list;
    public String processName;

    public Process(List<Segment> list, String processName) {
        this.list = list;
        this.processName = processName;
    }
}
