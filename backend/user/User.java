package user;

import java.util.Date;

public class User {

    private final String userId;
    private final String userName;

    public User(String userName, Date dateOfBirth) {

        this.userId = this.generateUserId(dateOfBirth);
        this.userName = userName;
    
    }

    public User(String userId) {
        
        this.userId = userId;
        this.userName = this.loadRecord(userId);

    }

    public final String generateUserId(Date dateOfBirth) {
        return "";
    }

    // TODO: Implement this method to load the user record from the database.
    private String loadRecord(String userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }


}