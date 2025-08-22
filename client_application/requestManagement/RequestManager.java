package sammc.lifeSupport.desktopClient.requestManagement;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;

import sammc.lifeSupport.flexibleDatabaseServer.looselyTypedData.LooselyTypedData;

public class RequestManager {

    private String url;

    public RequestManager(String url) {
        this.url = url;
    }
    
    public static void createData(String url, LooselyTypedData data) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(BodyPublishers.ofString("your_json_body"))
        .header("Content-Type", "application/json")
        .build();
    }

    public static void readData(String url, int id) {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url + "/" + id))
        .GET() 
        .header("Content-Type", "application/json")
        .build();

    }

    public static void updateData(String url, int id) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url + "/" + id))
            .POST(BodyPublishers.ofString("your_json_body"))
            .header("Content-Type", "application/json")
            .build();
    }

    public static void deleteData(String url, int id) {

    }

    public static List<LooselyTypedData> listData(String url, int id) {
        return null;
    }

    


}
