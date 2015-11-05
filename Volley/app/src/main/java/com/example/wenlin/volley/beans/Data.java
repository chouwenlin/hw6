package com.example.wenlin.volley.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wenlin on 2015/11/5.
 */
public class Data {
    private Result result;

    public Result getResult(){
        return  result;
    }

    public static class Result {
        private String offset;

        @SerializedName("limit")
        private String limit;
        private String count;
        private String sort;
        private List<ResultItem> results;

        public List<ResultItem> getResults() {
            return results;
        }

    }

    public static class ResultItem{
        private String _id;
        private String Station;
        private String Destination;

        public String get_id(){
            return _id;
        }

        public String getStation(){
            return Station;
        }

        public String getDestination(){
            return  Destination;
        }

    }

}


