import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Message;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        ObjectMapper om = new ObjectMapper();
        HttpClient webClient = HttpClient.newHttpClient();
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/1"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString("{"+
                        "\"message_text\": \"SNEED 72\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Message actualResult = om.readValue(response.body().toString(), Message.class);
        System.out.println(actualResult.getMessage_text());
    }
}
