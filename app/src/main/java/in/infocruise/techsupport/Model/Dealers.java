package in.infocruise.techsupport.Model;

public class Dealers {
    int id;
    String dealer_code;
    String dealer_name;
    int manufacturer_id;

    // constructors
    public Dealers(){

    }
    public Dealers(String dealer_code, String dealer_name, int  manufacturer_id){
        this.dealer_code=dealer_code;
        this.dealer_name = dealer_name;
        this.manufacturer_id = manufacturer_id;
    }

    public Dealers(int id, String dealer_code){
        this.id=id;
        this.dealer_code=dealer_code;
    }
    public Dealers(int id, String dealer_code, String dealer_name, int manufacturer_id){
        this.id=id;
        this.dealer_code=dealer_code;
        this.dealer_name=dealer_name;
        this.manufacturer_id=manufacturer_id;
    }

    // setters
    public void setId(int id){
        this.id=id;
    }
    public void setDealer_code(String dealer_code){
        this.dealer_code=dealer_code;
    }
    public void setDealer_name(String dealer_name){
        this.dealer_name=dealer_name;
    }
    public void setManufacturer_id(int manufacturer_id){
        this.manufacturer_id=manufacturer_id;
    }

    // getters
    public int getId(){
        return this.id;
    }

    public String getDealer_code() {
        return dealer_code;
    }
    public String getDealer_name(){
        return dealer_name;
    }
    public int getManufacturer_id(){
        return manufacturer_id;
    }


    //to display object as a string in spinner


    @Override
    public boolean equals(Object obj){
        if (obj instanceof Dealers){
            Dealers d = (Dealers ) obj;
            if (d.getDealer_code().equals(dealer_code) && d.getId()==id ) return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return dealer_code;
    }
}
