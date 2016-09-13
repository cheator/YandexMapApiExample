package ru.devcheat.YandexMapApiExample;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.yandex.yandexmapkit.utils.GeoPoint;

/*
List of Yandex GeoPoints with adress

 */
public class SingleList {

    private static ArrayList<YaPoint> points = new ArrayList<>();
    private static int _index = 0;
    private static Comparator itemComparator = null;

    private SingleList() {

    }

    public synchronized static ArrayList<YaPoint> getPoints() {
        return points;
    }

    public static synchronized void addPoint(String adress, GeoPoint point) {

        points.add(new YaPoint(++_index, adress, point));

        Log.d("ADD TO LIST", "index: " + _index + " " + points.size() + "");
    }

    public static void remove(YaPoint point) {
        // this.records = SingleList.getPoints();
        points.remove(point);

    }

    public static String getText(int position) {

        return points.get(position).get_adress();
    }

    public static YaPoint getPointByIndex(int i) {
        return points.get(i);
    }


    public static void setText(int position, String adress) {
        points.get(position).set_adress(adress);
    }


    public static void sortIndex() {
        itemComparator = new Comparator<YaPoint>() {
            @Override
            public int compare(YaPoint a, YaPoint b) {
                return a.get_index() - b.get_index();
            }
        };
        Collections.sort(points, itemComparator);
    }

    public static void sortDistance() {
        itemComparator = new Comparator<YaPoint>() {
            @Override
            public int compare(YaPoint a, YaPoint b) {
                return (new Double(a.getDistanse())).compareTo(new Double(b.getDistanse()));
            }
        };
        Collections.sort(points, itemComparator);
    }

    public static void sortAdress() {
        itemComparator = new Comparator<YaPoint>() {
            @Override
            public int compare(YaPoint a, YaPoint b) {
                return a.get_adress().compareTo(b.get_adress());
            }
        };
        Collections.sort(points, itemComparator);

    }

    public static void addDistance(GeoPoint from) {
        for (YaPoint point : points) {
            point.setDistanse(from);
        }
    }


}
