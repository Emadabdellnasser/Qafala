package com.ebda3.sponsorship;

/**
 * Created by Omda on 7/2/2018.
 */

public class Worker_data {
    String worker_name;
    String link_pw;
    String career;

    public Worker_data(String career, String worker_name, String link_pw) {
        this.career = career;
        this.link_pw = link_pw;
        this.worker_name = worker_name;
    }

    public String getname() {
        return worker_name;
    }

    public String getlink() {
        return link_pw;
    }

    public String getcareer() {
        return career;
    }

}

