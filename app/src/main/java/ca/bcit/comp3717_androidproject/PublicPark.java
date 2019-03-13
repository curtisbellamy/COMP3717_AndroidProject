package ca.bcit.comp3717_androidproject;

public class PublicPark {

    private String name;
    private String neighborhood;
    private String StrNum;
    private String StrName;

    public PublicPark(String name, String neighborhood, String strNum, String strName) {
        this.name = name;
        this.neighborhood = neighborhood;
        StrNum = strNum;
        StrName = strName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStrNum() {
        return StrNum;
    }

    public void setStrNum(String strNum) {
        StrNum = strNum;
    }

    public String getStrName() {
        return StrName;
    }

    public void setStrName(String strName) {
        StrName = strName;
    }
}
