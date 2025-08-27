package sammc.client.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import sammc.client.gui.components.DataLabel;

/**
 * Class that handles the http requests for interacting with the server program.
 * Clean interface for working with permament storage of data with various types
 * 
 * @author sammc
 */
public class DataManager {

    private static Logger log = LoggerFactory.getLogger(DataManager.class);
    private String uri;

    // Must be instantiated with a valid url
    public DataManager(String uri) {
        this.uri = uri;
    }
    
    /**
     * Create a new datapoint with automatically generated id and datetime
     * @param data
     * @return boolean requestSuccessful
     */
    public int create(LooselyTypedData data) {
        log.info("Create Data Triggered on uri: " + uri);
        try {
            // set date time
            LocalDateTime currentDateTime = LocalDateTime.now();
            data.setDatetime(currentDateTime.getYear() + "/" + currentDateTime.getMonthValue() + "/" + currentDateTime.getDayOfMonth() + " " + currentDateTime.getHour() + ":" + currentDateTime.getMinute() + ":" + currentDateTime.getSecond());

            // Build the post request
            Request request = Request.post(uri);
            request.setHeader("Content-Type", "application/json");
            request.bodyString(new ObjectMapper().writeValueAsString(data), ContentType.APPLICATION_JSON);

            // Get Response
            HttpResponse response = request.execute().returnResponse();
            if (response.getCode() != 200) throw new Exception("Connection Error: " + response.getCode());
            
            return -1;
        } catch (Exception e) {
            log.error("Error On Create", e);
            return -1;
        }
    }

    /**
     * Reads and returns the datapoint with the given id
     * @param id
     * @return LooselyTypedData data
     */
    public LooselyTypedData read(int id) {
        log.info("Read Data Triggered on uri: " + uri + " with id #" + id);
        try {
            String content = Request.get(uri + "/" + id).execute().returnContent().asString();
            
            LooselyTypedData result = new ObjectMapper().readValue(content, LooselyTypedData.class);
            return result;
        } catch (Exception e) {
            log.error("Error On Read", e);
            return null;
        }
    }


    /**
     * Update the datapoint with the provided ID
     * @param id
     * @param data
     * @return boolean requestSuccessful
     */
    public boolean update(int id, LooselyTypedData data) {
        log.info("Update Data Triggered on uri: " + uri + " with id #" + id);
        try {
            Request req = Request.put(uri + "/" + id);
            
            req.setHeader("Content-Type", "application/json");
            req.bodyString(new ObjectMapper().writeValueAsString(data), ContentType.APPLICATION_JSON);


            HttpResponse response = req.execute().returnResponse();
            if (response.getCode() != 200) throw new Exception("Connection Error: " + response.getCode());
            return true;
        } catch (Exception e) {
            log.error("Error On Update", e);
            return false;
        }
    }

    /**
     * Deletes the datapoint with the provided ID
     * @param id
     * @return boolean requestSuccessful
     */
    public boolean delete(int id) {
        log.info("Delete Data Triggered on uri: " + uri + " with id #" + id);
        try {
            HttpResponse response = Request.delete(uri + "/" + id).execute().returnResponse();
            if (response.getCode() != 200) throw new Exception("Connection Error: " + response.getCode());
            return true;
        } catch (Exception e) {
            log.error("Error On Delete", e);
            return false;
        }
    }

    /**
     * List all datapoints
     * @return LooselyTypedData[] list
     */
    public LooselyTypedData[] list() {
        log.info("List Data Triggered on uri: " + uri);
        try {
            String jsonArray = Request.get(uri + "/all").execute().returnContent().asString();
            return new ObjectMapper().readValue(jsonArray, LooselyTypedData[].class);
        } catch (Exception e) {
            log.error("Error On List", e);
            return null;
        }
    }

    /**
     * List all datapoints that share the provided type
     * @param type
     * @return LooselyTypedData[] listOfTypeX
     */
    public LooselyTypedData[] listByType(String type) {
        List<LooselyTypedData> result = new ArrayList<>();
        for (LooselyTypedData dat : list()) {
            // if the type is not null and equals the type being searched for, add it to the results
            if (dat.getType() != null && dat.getType().equals(type)) {
                result.add(dat);
            }
        }

        // repackage into an array
        LooselyTypedData[] final_result = new LooselyTypedData[result.size()];
        for (int i = 0; i < result.size(); i++) {
            final_result[i] = result.get(i);
        }
        return final_result;
    }

    /**
     * List only the labels for each datapoint in a list of strings. 
     * Each label should look like: '{point_name} {point_id}'
     * @return String[] labels
     */
    public DataLabel[] listLabels() {
        log.info("List Data Labels triggered on uri: " + uri);
        try {
            LooselyTypedData[] data = list();
            DataLabel[] data_labels = new DataLabel[data.length];

            int i = 0;
            for (LooselyTypedData ltd : data) {
                data_labels[i++] = new DataLabel(ltd.getName(), ltd.getId());
            }

            return data_labels;
        } catch (Exception e) {
            log.error("Error On List Labels", e);
            return null;
        }
    }

    public DataLabel[] listLabelsByType(String type) {
        log.info("List Data Labels By Type triggered on uri: " + uri);
        try {
            LooselyTypedData[] data = listByType(type);
            DataLabel[] data_labels = new DataLabel[data.length];

            int i = 0;
            for (LooselyTypedData ltd : data) {
                data_labels[i++] = new DataLabel(ltd.getName(), ltd.getId());
            }

            return data_labels;
        } catch (Exception e) {
            log.error("Error On List Labels", e);
            return null;
        }
    }
}
