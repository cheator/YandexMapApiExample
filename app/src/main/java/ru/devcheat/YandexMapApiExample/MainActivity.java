package ru.devcheat.YandexMapApiExample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.map.GeoCode;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class MainActivity extends AppCompatActivity implements OnBalloonListener {

    private MapView map ;
    OverlayManager mOverlayManager;
     MapController mMapController;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapwork();
        setBtn();
    }

    private void setBtn() {
        btnSearch = (Button) findViewById(R.id.btnSort);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoPoint geo  = new GeoPoint(55.734029 , 37.588499);
                GeoCode geoCod = new GeoCode();
                geoCod.a(geo);
                String name = geoCod.getDisplayName();
             //   Toast.makeText(MainActivity.this,name , Toast.LENGTH_SHORT).show();

                map.getMapController().getOverlayManager()
                        .getMyLocation().setEnabled(true);
                map.getMapController().getOverlayManager()
                        .addOverlay(new GeoCodeOverlay(map.getMapController()));
            }
        });


    }

    private void mapwork() {
        // Load required resources
        Resources res = getResources();
        map = (MapView) findViewById(R.id.map);

        mMapController = map.getMapController();


        mOverlayManager = mMapController.getOverlayManager();
        // Disable determining the user's location
        mOverlayManager.getMyLocation().setEnabled(false);

        Overlay overlay = new Overlay(mMapController);
        // Create an object for the layer
        OverlayItem yandex = new OverlayItem(new GeoPoint(55.734029 , 37.588499), res.getDrawable(R.drawable.nav_sleep));
        // Create the balloon model for the object
        BalloonItem balloonYandex = new BalloonItem(this, yandex.getGeoPoint());

        balloonYandex.setText("ANDROID");
        balloonYandex.setOnBalloonListener(this);
        // Add the balloon model to the object
        yandex.setBalloonItem(balloonYandex);
        // Add the object to the layer
        overlay.addOverlayItem(yandex);

        // Add the layer to the map
        mOverlayManager.addOverlay(overlay);


    }

    @Override
    public void onBalloonViewClick(BalloonItem balloonItem, View view) {
        OverlayItem item = balloonItem.getOverlayItem();


    }

    @Override
    public void onBalloonShow(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonHide(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationStart(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationEnd(BalloonItem balloonItem) {

    }
}
