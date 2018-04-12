package in.infocruise.techsupport.Model;

public class Call_Log {
    int id;
    String call_name;
    String phone_no;
    String call_type;
    String date_time;

    // constructor
    public Call_Log(){

    }
    public Call_Log(String call_name, String phone_no, String call_type, String date_time){
        this.call_name=call_name;
        this.phone_no=phone_no;
        this.call_type=call_type;
        this.date_time=date_time;
    }
    public Call_Log(int id, String call_type,String phone_no, String date_time){
        this.id =id;
        this.call_type=call_type;
        this.phone_no=phone_no;
        this.date_time=date_time;
    }
    public Call_Log(int id, String call_name, String phone_no, String call_type, String date_time){
        this.id=id;
        this.call_name=call_name;
        this.phone_no=phone_no;
        this.call_type=call_type;
        this.date_time=date_time;
    }

    // setters
    public void setId(int id){
        this.id=id;
    }
    public void setCall_name(String call_name){
        this.call_name= call_name;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    // getters
    public int getId(){
        return this.id;
    }

    public String getCall_name() {
        return this.call_name;
    }
    public String getPhone_no(){
        return this.phone_no;
    }
    public String getCall_type(){
        return this.call_type;
    }

    public String getDate_time() {
        return this.date_time;
    }
}
