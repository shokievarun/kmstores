package com.shokievarun.kmstores.Model;

public class Products {

    private  String pname, price, image, description, Category, pid, date, time,productStatus,quantity;

    public Products() {
    }

    public  Products(String pname) {
        this.pname = pname;
    }

    public Products(String price, String quantity) {
        this.price = price;
        this.quantity = quantity;
    }



    public Products(String pname, String price, String image, String description, String category, String pid, String date, String time, String productStatus) {
        this.pname = pname;
        this.price = price;
        this.image = image;
        this.description = description;
        Category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.productStatus=productStatus;
    }


    public Products(String pname, String price, String image, String description) {
        this.pname = pname;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    public Products(String pname, String price, String image, String description, String pid) {
        this.pname = pname;
        this.price = price;
        this.image = image;
        this.description = description;
        this.pid = pid;
    }

    public Products(String pname, String price, String description) {
        this.pname = pname;
        this.price = price;
        this.description = description;
    }




    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }




    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
