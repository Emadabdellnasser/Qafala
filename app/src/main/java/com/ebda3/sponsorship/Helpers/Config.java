package com.ebda3.sponsorship.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ebda3.sponsorship.ProfessionClass;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

/**
 * Created by EBDA3 on 8/26/2017.
 */

public class Config {
    public static String comeActivity = "activity";
    public static String Lang = "ar";
    public static final String imageupload = "http://qeta3km.a5aa.com/uploads/";
    public static final String CatImageUpload = "http://adc-company.net/kafala/category/";
    public static final String LoginUrl = "http://adc-company.net/kafala/include/pages/login_cust.php?json=true";
    public static final String SignUpCustomer  = "http://adc-company.net/kafala/users-edit-1.html?json=true&ajax_page=true";
    public static final String SignUpTrader  = "http://adc-company.net/kafala/trader-edit-1.html?json=true&ajax_page=true";
    public static final String SignUpWorkshop  = "http://adc-company.net/kafala/workshop-edit-1.html?json=true&ajax_page=true";
    public static final String SignUpImporter  = "http://adc-company.net/kafala/importer-edit-1.html?json=true&ajax_page=true";
    public static final String PartRequestURL  = "http://adc-company.net/kafala/partRequest-edit-1.html?json=true&ajax_page=true";
    public static final String RequestOfferstURL  = "http://adc-company.net/kafala/RequestOffers-edit-1.html?json=true&ajax_page=true";
    public static final String WebService  = "http://adc-company.net/kafala/include/webService.php?json=true";

    public static ArrayList<String> Cars =  new ArrayList<String>();
    public static ArrayList<String> CarsIDS =  new ArrayList<String>();
    public static ArrayList<String> CarsPhoto =  new ArrayList<String>();
    public static ArrayList<ArrayList<String>> Models =  new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> ModelsIDS =  new ArrayList<ArrayList<String>>();

    public static ArrayList<String> Motorcycle =  new ArrayList<String>();
    public static ArrayList<String> MotorcyclePhoto =  new ArrayList<String>();
    public static ArrayList<String> MotorcycleIDS =  new ArrayList<String>();
    public static ArrayList<ArrayList<String>> MotorcycleModels =  new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> MotorcycleModelsIDS =  new ArrayList<ArrayList<String>>();

    public static ArrayList<String> Country =  new ArrayList<String>();
    public static ArrayList<String> CountryIDS =  new ArrayList<String>();
    public static ArrayList<String> CountryW =  new ArrayList<String>();
    public static ArrayList<String> CountryWIDS =  new ArrayList<String>();
    public static ArrayList<String> Proffesions =  new ArrayList<String>();
    public static ArrayList<String> Advantages =  new ArrayList<String>();
    public static ArrayList<String> Nationalitiescheck =  new ArrayList<String>();
    public static ArrayList<String> Proffesions2 =  new ArrayList<String>();
    public static ArrayList<String> Proffesions3 =  new ArrayList<String>();
    public static ArrayList<String> ProffesionsIDS =  new ArrayList<String>();
    public static ArrayList<String> ProffesionsIDS2 =  new ArrayList<String>();
    public static ArrayList<String> ProffesionsIDS3 =  new ArrayList<String>();
    public static ArrayList<String> Proffesionscheckable =  new ArrayList<String>();
    public static ArrayList<String> Proffesionscheckable2 =  new ArrayList<String>();
    public static ArrayList<String> Proffesionscheckable3 =  new ArrayList<String>();
public static ArrayList<String> Nationalities =  new ArrayList<String>();
    public static ArrayList<String> NationalitiesIDS =  new ArrayList<String>();
    public static ArrayList<String> NationalitiesWorker =  new ArrayList<String>();
    public static ArrayList<String> NationalitiesWorkerIDS =  new ArrayList<String>();
    public static ArrayList<String> Nationalitiescheckable =  new ArrayList<String>();

    public static ArrayList<String> Features =  new ArrayList<String>();
    public static ArrayList<String> FeaturesIDS =  new ArrayList<String>();


    public static ArrayList<ProfessionClass> ProfessionData =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> ProfessionData2 =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> ProfessionNationality =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> NationalityData =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> SpecificationData =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> cityData =  new ArrayList<ProfessionClass>();

    public static ArrayList<ProfessionClass> NationalitiesData =  new ArrayList<ProfessionClass>();
    public static ArrayList<ProfessionClass> FeaturesData =  new ArrayList<ProfessionClass>();




    public static String getFirebaseInstanceId(){
        String InstanceId = FirebaseInstanceId.getInstance().getToken();
        return  InstanceId;
    }





    public static String getJsonEmail(Context context){
        SharedPreferences sp1 = context.getSharedPreferences("Login", 0);
        String email = sp1.getString("json_email", " ");
        return  email;
    }





    public static String getJsonPassword(Context context){
        SharedPreferences sp1 = context.getSharedPreferences("Login", 0);
        String email = sp1.getString("json_password", " ");
        return  email;
    }

    public static String getUserType(Context context){
        SharedPreferences sp1 = context.getSharedPreferences("Login", 0);
        String type = sp1.getString("type", " ");
        return  type;
    }


    public static void EditProfile(Context context){
        String UserType = Config.getUserType(context);


    }

    public static String getUserID(Context context){
        SharedPreferences sp1 = context.getSharedPreferences("Login", 0);
        String ID = sp1.getString("ID", "0");
        return  ID;
    }

    public static String getUserName(Context context){
        SharedPreferences sp1 = context.getSharedPreferences("Login", 0);
        String name = sp1.getString("name", "");
        return  name;
    }

    public static void SetUserLocation(Context context , String Location ){
        SharedPreferences sp = context.getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("UserLocation", Location );
        Ed.commit();
    }


}
