package ru.devcheat.YandexMapApiExample;

import android.widget.Toast;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.map.GeoCode;
import ru.yandex.yandexmapkit.map.GeoCodeListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

/**
 * Created by cheat on 08.09.2016.
 */
public class GeoCodeOverlay extends Overlay implements GeoCodeListener {

    public GeoCodeOverlay(MapController mapController) {
        super(mapController);
    }

    @Override
    public boolean onFinishGeoCode(final GeoCode geoCode) {
        if (geoCode != null) {
            getMapController().getMapView().post(new Runnable() {
                @Override
                public void run() {
                    // show display name of the point
                    Toast.makeText(getMapController().getContext(),
                            geoCode.getDisplayName(), Toast.LENGTH_LONG).show();

                }
            });
        }
        return true;
    }

    @Override
    public boolean onSingleTapUp(float x, float y) {
        getMapController().getDownloader()
                .getGeoCode(this, getMapController().getGeoPoint(new ScreenPoint(x, y)));
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}