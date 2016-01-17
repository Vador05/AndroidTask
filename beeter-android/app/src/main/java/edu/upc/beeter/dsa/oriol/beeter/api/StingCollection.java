package edu.upc.beeter.dsa.oriol.beeter.api;

/**
 * Created by Oriol on 07/05/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StingCollection {
    private List<Sting> stings;
    private long newestTimestamp;
    private long oldestTimestamp;
    private Map<String, Link> links = new HashMap<String, Link>();

    public StingCollection() {
        super();
        stings = new ArrayList<Sting>();
    }

    public List<Sting> getStings() {
        return stings;
    }

    public void setStings(List<Sting> stings) {
        this.stings = stings;
    }

    public void addSting(Sting sting) {
        stings.add(sting);
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public Map<String, Link> getLinks() {
        return links;
    }
}