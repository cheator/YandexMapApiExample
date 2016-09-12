package ru.devcheat.YandexMapApiExample;

import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

/**
 * Обьект с точкой.
 */
public class YaPoint {
    private String _adress;
    private GeoPoint _point;
    public OverlayItem marker = null;

    private int _index = 0;

    public YaPoint(int index, String adress, GeoPoint point) {
        set_index(index);
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

    public void set_index(int index) {
        if (index < 0) {
            this._index = index * -1;
        } else {
            this._index = index;
        }

    }

    public int get_index() {
        return _index;

    }


    @Override
    public int hashCode() {
        return _point.hashCode() + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        YaPoint _comparePoint = ((YaPoint) obj);
        if (_point.getLat() != _comparePoint.get_point().getLat())
            return false;

        if (_point.getLon() == _comparePoint.get_point().getLon())
            return false;

        return true;
    }


}
