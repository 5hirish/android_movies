
package com.alleviate.movies.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditMovies {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<CreditCast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<CreditCrew> crew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CreditCast> getCast() {
        return cast;
    }

    public void setCast(List<CreditCast> cast) {
        this.cast = cast;
    }

    public List<CreditCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<CreditCrew> crew) {
        this.crew = crew;
    }

}
