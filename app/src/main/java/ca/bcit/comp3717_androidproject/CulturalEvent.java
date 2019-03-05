package ca.bcit.comp3717_androidproject;

import java.io.Serializable;

public class CulturalEvent implements Serializable {
    private String email;
    private String address2;
    private String Address;
    private String city;
    private String prov;
    private String pcode;
    private String fax;
    private String phone;
    private String Name;
    private String Descriptn;
    private String id;
    private String category;
    private String company;
    private String website;
    private String social_networks;
    private String summary;
    private String catname;
    private String X;
    private String Y;
    private String date;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    private String Picture;

    public void setAddress(String address){
        this.Address = address;
    }

    public String getAddress(){
        return Address;
    }

    public void setName(String name){
        this.Name = name;
    }

    public String getName(){
        return Name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setAddress2(String address2){
        this.address2 = address2;
    }

    public String getAddress2(){
        return address2;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setProv(String prov){
        this.prov = prov;
    }

    public String getProv(){
        return prov;
    }

    public void setPCode(String pcode){
        this.pcode = pcode;
    }

    public String getPCode(){
        return pcode;
    }
    public void setFax(String fax){
        this.fax = fax;
    }

    public String getFax(){
        return fax;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setDescriptn(String desc){
        this.Descriptn = desc;
    }

    public String getDescriptn(){
        return Descriptn;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getID(){
        return id;
    }

    public void setCat(String category){
        this.category = category;
    }

    public String getCat(){
        return category;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getCompany(){
        return company;
    }
    public void setWebsite(String website){
        this.website = website;
    }

    public String getWebsite(){
        return website;
    }
    public void setSocial(String social){
        this.social_networks = social;
    }

    public String getSocial(){
        return social_networks;
    }

    public void setSummary(String summ){
        this.summary = summ;
    }

    public String getSummary(){
        return summary;
    }

    public void setCatNamee(String catName){
        this.catname = catName;
    }

    public String getCatname(){
        return catname;
    }

    public void setX(String x){
        this.X = x;
    }

    public String getX(){
        return X;
    }

    public void setY(String y){
        this.Y = y;
    }

    public String getY(){
        return Y;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }


    public void setPicture(String Picture){
        this.Picture = Picture;
    }
    public String getPicture(){
        return this.Picture;
    }

}
