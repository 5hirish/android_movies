package com.alleviate.movies.models;

/**
 * Created by Shirish Kadam on 21/6/17.
 * Logged in as felix.
 * www.shirishkadam.com
 */

public class CreditsCrewTMdb {

    public String crew_department, crew_job, crew_name, crew_profile;

    public CreditsCrewTMdb(String crew_department, String crew_job, String crew_name, String crew_profile){

        this.crew_department = crew_department;
        this.crew_job = crew_job;
        this.crew_name = crew_name;
        this.crew_profile = crew_profile;
    }
}
