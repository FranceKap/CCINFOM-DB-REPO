public class User {
    private int ID;
    private String lastName;
    private String firstName;
    private long contactNbr;

    

    public User(int ID, String lastName, String firstName, long contactNbr){
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactNbr = contactNbr;
    }

    public int getID() {
        return ID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getContactNbr() {
        return contactNbr;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setContactNbr(long contactNbr) {
        this.contactNbr = contactNbr;
    }

    //TODO attributes to store for now as these are the common attributes between Citizen and Staff
}
