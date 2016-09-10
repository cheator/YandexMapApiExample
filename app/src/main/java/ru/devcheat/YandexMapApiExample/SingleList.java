package ru.devcheat.YandexMapApiExample;

import android.util.Log;

import java.util.ArrayList;

/*
List of Yandex GeoPoints with adress

 */
public  class SingleList {

    private  static ArrayList<YaPoint> points  ;

    private SingleList (){

    }

    public static ArrayList<YaPoint> getPoints() {
       return points;
    }

    public static synchronized void addPoint (YaPoint point ){
        if (points == null ){
            points = new ArrayList<>();
        }
        points.add(point);
        Log.d("ADD TO LIST", points.size()+"");
    }

}
