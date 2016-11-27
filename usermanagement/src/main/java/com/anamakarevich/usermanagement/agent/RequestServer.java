package com.anamakarevich.usermanagement.agent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.hsqldb.lib.Iterator;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.DatabaseException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestServer extends CyclicBehaviour {

    private static final long serialVersionUID = 1L;

    @Override
    public void action() {
        // get the message
        ACLMessage message = myAgent.receive();
        // check that the message was received
        if (message != null) {
            // if it's a request
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            }
            // if it's a response (i.e. type - INFORM)
            else {
                Collection<?> users = parseMessage(message);
                ((SearchAgent)myAgent).showUsers(users);
            }
        }
        else {
            // block if no message was received
            block();
        }

    }

    private Collection<?> parseMessage(ACLMessage message) {
        
        Collection<User> users = new LinkedList<User>();
        
        // extract content
        String content = message.getContent();
        
        // check if the content is null
        if (content != null && !content.isEmpty()) {
            
            // get separate users
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                String currentUser = tokenizer.nextToken();
                
                // get user fields 
                StringTokenizer userTokenizer = new StringTokenizer(currentUser, ",");
                if (userTokenizer.countTokens() == User.class.getDeclaredFields().length) {
                    
                    // extract field values and create user
                    String id = userTokenizer.nextToken();
                    String firstName = userTokenizer.nextToken();
                    String lastName = userTokenizer.nextToken();
                    String dateStr = userTokenizer.nextToken();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dateOfBirthd;
                    try {
                        dateOfBirthd = LocalDate.parse(dateStr,formatter);
                    }
                    catch (DateTimeParseException e) {
                        dateOfBirthd = null;
                    }
                    users.add(new User(new Long(id), firstName, lastName, dateOfBirthd));
                }
            }
                
            }
        return users;
    }

    /**
     * Tries to extract search parameters, and if successfull,
     * performs search and returns collection of users as a formatted string,
     * where users are separated by ";" and the fields are separated by ","
     * @param message - request message
     * @return - reply message with users collection as content
     */
    private ACLMessage createReply(ACLMessage message) {
        // get proper reply format
        ACLMessage reply = message.createReply();
        
        // extract message contents
        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content,",");
        
        // if we've got exactly two strings - first name and last name
        // then perform search, serialize, pack into message and send as reply
        if (tokenizer.countTokens() ==2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = (Collection<User>) DaoFactory.getInstance().getUserDao().find(firstName,lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList<User>(0);
            }
            
            // pack users collection into string
            StringBuffer buffer= new StringBuffer();
            for (User user: users) {
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getDateOfBirthd().toString()).append(",");
                buffer.append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }

}
