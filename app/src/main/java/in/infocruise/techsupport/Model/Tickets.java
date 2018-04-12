package in.infocruise.techsupport.Model;

public class Tickets {
    int id;
    String note;
    int contact;
    int dealer_id;
    int user_id;
    int tag_id;
    String contact_name;
    String created_at;

    // CONSTRUCTORS
    public Tickets(){

    }
    public Tickets(String note, String contact_name, int dealer_id, String created_at){
        this.note=note;
        this.contact_name=contact_name;
        this.dealer_id=dealer_id;
        this.created_at=created_at;
    }
    public Tickets(String note, String contact_name, String created_at){
        this.note=note;
        this.contact_name=contact_name;
        this.created_at=created_at;
    }
    public Tickets(int id, String note, int contact, int dealer_id, int user_id,String contact_name,  int tag_id, String created_at){
        this.id=id;
        this.note=note;
        this.contact_name=contact_name;
        this.contact=contact;
        this.user_id=user_id;
        this.dealer_id=dealer_id;
        this.tag_id=tag_id;
        this.created_at=created_at;
    }

    // setters
    public void setId(int id){
        this.id=id;
    }
    public void setNote(String note){
        this.note=note;
    }
    public void setContact(int contact){
        this.contact=contact;
    }
    public void setContact_name(String contact_name){
        this.contact_name=contact_name;
    }
    public void setDealer_id(int dealer_id){
        this.dealer_id=dealer_id;
    }
    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public void setTag_id(int tag_id){
        this.tag_id=tag_id;
    }
    public void setCreated_at(String created_at){
        this.created_at=created_at;
    }

    // getters
    public long getId(){
        return id;
    }

    public String getNote() {
        return note;
    }

    public int getContact() {
        return contact;
    }

    public int getDealer_id() {
        return dealer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getCreated_at() {
        return created_at;
    }
}
