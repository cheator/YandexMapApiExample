package ru.devcheat.YandexMapApiExample;

import android.util.Log;

import java.util.ArrayList;

import ru.yandex.yandexmapkit.utils.GeoPoint;

/*
List of Yandex GeoPoints with adress

 */
public  class SingleList {

    private static ArrayList<YaPoint> points  ;
    private static int _index = 0;
    private SingleList (){

    }

    public static ArrayList<YaPoint> getPoints() {
       return points;
    }

    public static synchronized void addPoint (String adress , GeoPoint point ){

        if (points == null ){
            points = new ArrayList<>();
        }
        points.add(new YaPoint( ++_index , adress , point ));

        Log.d("ADD TO LIST", points.size()+"");
    }

}
