package services;

import server.SendMail;

public class HandleSubCase {
    public static void handleWrongRequest(String from){
        SendMail.serversendEmail(from, "Something went wrong!", "",
                HTMLGenerator.generateHTML("There might be an error in your request.", "",
                        "Please check it again.<br>" +
                                "You can request <b>\"help\"</b> to get a full list of acceptance requests"));
    }
}
