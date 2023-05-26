package sample;

public class Segment {
    private String segmentId;
    private String process;
    private long baseAdress;
    private long limitSize;

    public Segment(String segmentId, String process, long baseAdress, long limitSize) {
        this.segmentId = segmentId;
        this.process = process;
        this.baseAdress = baseAdress;
        this.limitSize = limitSize;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public long getBaseAdress() {
        return baseAdress;
    }

    public void setBaseAdress(long baseAdress) {
        this.baseAdress = baseAdress;
    }

    public long getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(long limitSize) {
        this.limitSize = limitSize;
    }
}
