package in.infocruise.techsupport.Model;

public class Manufacturer {
    int id;
    String manufacturer_name;

    // constructors
    public Manufacturer() {

    }

    public Manufacturer(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public Manufacturer(int id, String manufacturer_name){
        this.id = id;
        this.manufacturer_name= manufacturer_name;
    }

    // setters
    public void setId(int id){
        this.id=id;
    }
    public void setManufacturer_name(String manufacturer_name){
        this.manufacturer_name = manufacturer_name;
    }

    // getters
    public int getId(){
        return this.id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    //to display object as a string in spinner
    @Override
    public String toString(){
        return manufacturer_name;
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof Manufacturer){
            Manufacturer m = (Manufacturer )object;
            if (m.getManufacturer_name().equals(manufacturer_name) && m.getId()==id ) return true;
        }
        return false;
    }
}
