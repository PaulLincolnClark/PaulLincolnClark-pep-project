package Service;

import Model.Account;
import Model.Message;
import DAO.SocialMediaDAO;
import java.util.List;

public class SocialMediaService {
    SocialMediaDAO socialMediaDAO;

    //CONSTRUCTORS AND WHATNOT

    public SocialMediaService() {
        socialMediaDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO socialMediaDAO) {
        this.socialMediaDAO = socialMediaDAO;
    }

    public SocialMediaDAO getDAO() {
        return this.socialMediaDAO;
    }

    //ACCOUNT METHODS

    public Account createAccount(Account account) { //Works
        //Checks username and password requirements.
        try {
            if (account.getUsername().isBlank() || account.getPassword().isBlank() 
                || account.getPassword().length() < 4) {
                return null;
            }

            Account acct = socialMediaDAO.createAccount(account);
            return acct;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account account) { //Works
        return socialMediaDAO.login(account);
    }

    public List<Account> getAllAccounts() { //Works
        return socialMediaDAO.getAllAccounts();
    }

    //MESSAGE METHODS

    public Message createMessage(Message message) { //Works
        //Checks message requirements.
        try {
            if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
                return null;
            }

            Message msg = socialMediaDAO.createMessage(message);
            return msg;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() { //Works
        return socialMediaDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUser(int userID) { //Works
        return socialMediaDAO.getAllMessagesByUser(userID);
    }

    public Message getMessagebyMessageID(int messageID) { //Works
        return socialMediaDAO.getMessageByMessageID(messageID);
    }

    public Message deleteMessagebyMessageID(int messageID) { //Works
        Message message = getMessagebyMessageID(messageID);
        if (message == null) {
            return null;
        }
        else {
            socialMediaDAO.deleteMessageByMessageID(messageID);
            return message;
        }
    }

    public Message modifyMessageByMessageID(int messageID, String messageText) {
        Message message = getMessagebyMessageID(messageID);
        if (messageText.isBlank() || messageText.length() > 255) {
            return null;
        }
        else {
            socialMediaDAO.modifyMessageByMessageID(messageID, messageText);
            return message;
        }
    }
}

