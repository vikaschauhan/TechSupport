package in.infocruise.techsupport.Model;

public class SupportUser {
    private String id;
    private String name;
    private String password;
    private String created_at;

    public SupportUser(String id, String name, String password,String created_at){
        this.id=id;
        this.name= name;
        this.password = password;
        this.created_at=created_at;
    }

    public String getName() {
        return name;
    }
    public String getId(){
        return id;
    }
    public String getPassword() {
        return password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(String id){
        this.id=id;
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
}
