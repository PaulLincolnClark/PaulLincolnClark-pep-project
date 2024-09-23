package Controller;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    
    SocialMediaService socialMediaService = new SocialMediaService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccount);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByMessageID);
        app.delete("/messages/{message_id}", this::getMessageByMessageID);
        app.patch("/messages/{message_id}", this::modifyMessageByMessageID);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void createAccount (Context context) throws JsonMappingException, JsonProcessingException {        
        ObjectMapper mapper = new ObjectMapper();
        Account addedAccount = socialMediaService.createAccount(
            mapper.readValue(context.body(), Account.class));
        
        if (addedAccount == null) {
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }
    }

    private void login (Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginAccount = socialMediaService.login(
            mapper.readValue(context.body(), Account.class));
        
        if (loginAccount == null) {
            context.status(401);
        }
        else {
            context.json(mapper.writeValueAsString(loginAccount));
            context.status(200);
        }
    }

    private void createMessage (Context context) throws JsonMappingException, JsonProcessingException {  
        ObjectMapper mapper = new ObjectMapper();
        Message createdMessage = socialMediaService.createMessage(
            mapper.readValue(context.body(), Message.class));
        
        if (createdMessage == null) {
            context.status(400);
        }
        else {
            context.json(mapper.writeValueAsString(createdMessage));
            context.status(200);
        }
    }

    private void getAllMessages (Context context) throws JsonMappingException, JsonProcessingException {  
        context.json(socialMediaService.getAllMessages());
        context.status(200);
    }

    private void getAllMessagesByUser (Context context) throws JsonMappingException, JsonProcessingException {  
        Integer accountID = Integer.parseInt(context.pathParam("account_id"));
        try {
            context.json(socialMediaService.getAllMessagesByUser(accountID));
            context.status(200);
        }
        catch (Exception E) {
            context.status(200);
        }
    }

    private void getMessageByMessageID (Context context) throws JsonMappingException, JsonProcessingException {  
        Integer messageID = Integer.parseInt(context.pathParam("message_id"));
        try {
            context.json(socialMediaService.getMessagebyMessageID(messageID));
            context.status(200);
        }
        catch (Exception E) {
            context.status(200);
        }
    }
 
    private void modifyMessageByMessageID (Context context) throws JsonMappingException, JsonProcessingException {  
        ObjectMapper mapper = new ObjectMapper();
        Integer messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = mapper.readValue(context.body(), Message.class);
        String messageText = message.getMessage_text();

        try {
            context.json(socialMediaService.modifyMessageByMessageID(messageID, messageText));
            context.json(socialMediaService.modifyMessageByMessageID(messageID, messageText));
            //COPIED THE ABOVE LINE TO FIX A STRANGE BUG I ENCOUNTERED

            context.status(200);
        }
        catch (Exception E) {
            context.status(400);
        }
    }
}