package edu.upc.beeter.dsa.oriol.beeter.api;

/**
 * Created by Oriol on 07/05/2015.
 */
import java.util.HashMap;
import java.util.Map;

public class BeeterRootAPI {

    private Map<String, Link> links;

    public BeeterRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}