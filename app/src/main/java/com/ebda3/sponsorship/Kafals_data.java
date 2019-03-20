package com.ebda3.sponsorship;

/**
 * Created by Omda on 7/2/2018.
 */

public class Kafals_data {


    String kafal_name;
    String link;
    String organization;

    public Kafals_data(String organization, String kafal_name, String link) {
        this.organization = organization;
        this.link = link;
        this.kafal_name = kafal_name;
    }

    public String getname() {
        return kafal_name;
    }

    public String getlink() {
        return link;
    }

    public String getorganization() {
        return organization;
    }

}
