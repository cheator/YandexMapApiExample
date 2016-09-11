package ru.devcheat.YandexMapApiExample;

import android.util.Log;

import java.util.ArrayList;

import ru.yandex.yandexmapkit.utils.GeoPoint;

/*
List of Yandex GeoPoints with adress

 */
public  class SingleList {

    private static ArrayList<YaPoint> points  = new ArrayList<>();
    private static int _index = 0;
    private SingleList (){

    }

    public synchronized static ArrayList<YaPoint> getPoints() {
        return points;
    }

    public static synchronized void addPoint (String adress , GeoPoint point ){
        points.add(new YaPoint( ++_index , adress , point ));

        Log.d("ADD TO LIST", "index: " +_index+ " " + points.size()+"");
    }
    public static void remove (YaPoint point){
        // this.records = SingleList.getPoints();
        points.remove(point);

    }
    public static String getText(int position){

        return points.get(position).get_adress();
    }
    public static void setText (int position , String adress){
        points.get(position).set_adress(adress);
    }




}
