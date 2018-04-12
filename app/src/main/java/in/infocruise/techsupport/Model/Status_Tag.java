package in.infocruise.techsupport.Model;

public class Status_Tag {
    int id;
    String tag_name;

    // constructors
    public Status_Tag(){

    }
    public Status_Tag(String tag_name){
        this.tag_name=tag_name;
    }
    public Status_Tag(int id, String  tag_name){
        this.id=id;
        this.tag_name= tag_name;
    }

    // setters
    public void setId(int  id){
        this.id=id;
    }
    public void setTag_name(String tag_name){
        this.tag_name=tag_name;
    }

    // getters
    public int getId(){
        return this.id;
    }

    public String getTag_name() {
        return tag_name;
    }

    //to display object as a string in spinner
    @Override
    public String toString(){
        return tag_name;
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof Status_Tag){
            Status_Tag s = (Status_Tag ) object;
            if (s.getTag_name().equals(tag_name) && s.getId()==id) return true;

        }
        return false;
    }
}
