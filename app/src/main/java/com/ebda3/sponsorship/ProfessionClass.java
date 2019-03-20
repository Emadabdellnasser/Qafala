package com.ebda3.sponsorship;

/**
 * Created by Aml on 7/17/2018.
 */

public class ProfessionClass {
    public String pro_name;
    public String pro_id;

    public ProfessionClass() {
    }

    public ProfessionClass(String pro_name, String pro_id) {
        this.pro_name = pro_name;
        this.pro_id = pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }
}
