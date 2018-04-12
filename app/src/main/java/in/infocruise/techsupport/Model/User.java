package in.infocruise.techsupport.Model;

public class User {
    int id;
    String name;
    String password;
    String created_at;

    // constructors
    public User(){

    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password){
        this.id = id;
        this.name= name;
        this.password = password;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setCreated_at(String created_at){
        this.created_at=created_at;
    }

    // getters
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword() {
        return password;
    }
}
