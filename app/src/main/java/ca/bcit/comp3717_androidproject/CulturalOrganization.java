package ca.bcit.comp3717_androidproject;

public class CulturalOrganization {

    private String name;
    private String address;
    private String website;
    private String description;

    public CulturalOrganization(String name, String address, String website, String description) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
