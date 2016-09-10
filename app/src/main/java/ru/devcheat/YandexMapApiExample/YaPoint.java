package ru.devcheat.YandexMapApiExample;

import ru.yandex.yandexmapkit.utils.GeoPoint;


public class YaPoint {
    private String _adress;
    private GeoPoint _point ;

    public YaPoint  (String adress , GeoPoint point){

        set_adress(adress);
        set_point(point);
    }


    public void set_adress(String _adress) {
        this._adress = _adress;
    }

    public void set_point(GeoPoint _point) {
        this._point = _point;
    }

    public String get_adress() {
        return _adress;
    }

    public GeoPoint get_point() {
        return _point;
    }
}
