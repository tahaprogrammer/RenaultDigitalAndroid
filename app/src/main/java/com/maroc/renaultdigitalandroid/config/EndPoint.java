package com.maroc.renaultdigitalandroid.config;

public class EndPoint {

    private static final String SERVER_URL_PRODUCTION = "https://mrmoroccan.online/";

    private static final String LISTING_CAR_MODELS = SERVER_URL_PRODUCTION + "renaultJobCapgemini/carModels.json";

    public static String getServerUrlProduction() {
        return SERVER_URL_PRODUCTION;
    }

    public static String getListingCarModels() {
        return LISTING_CAR_MODELS;
    }
}
