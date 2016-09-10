package ru.devcheat.YandexMapApiExample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;

public class MainActivity extends AppCompatActivity implements OnBalloonListener {

    private MapView map ;
    OverlayManager mOverlayManager;
     MapController mMapController;
    Overlay overlay;
    Button btnSearch;
    GeocodeCallBack _cb ;
    ListViewCompat lv;
    Map<YaPoint , BalloonItem>  balloons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListViewCompat) findViewById(R.id.listV);
        mapwork();
        setBtn();
    }

    private void setBtn() {
        btnSearch = (Button) findViewById(R.id.btnSort);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadMapObjects ();

            }
        });


    }






    private void mapwork() {
        // Load required resources
        Resources res = getResources();
        byte MainPriority  = 1;
        map = (MapView) findViewById(R.id.map);
        mMapController = map.getMapController();
        mOverlayManager = mMapController.getOverlayManager();
        // Disable determining the user's location
        mOverlayManager.getMyLocation().setEnabled(true);
         overlay = new Overlay(mMapController);
        overlay.setPriority(MainPriority);

        // Create an object for the layer

        mOverlayManager.addOverlay(overlay);
        _cb = new GeocodeCallBack() {
            @Override
            public boolean onFinish() {

                getList();
                return true;
            }
        };


        GeoCodeOverlay geo =  new GeoCodeOverlay(map.getMapController(),_cb);
        byte GeoPriority  = 2;
        geo.setPriority(GeoPriority);
        mOverlayManager.addOverlay(geo);

    }
    private void getList( ){
        ArrayList<YaPoint> points =  SingleList.getPoints();
        String[] adreses = new String[points.size()];


        for (YaPoint point  : points){
            adreses[point.get_index()-1] = point.get_index()+" "+ point.get_adress();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,
            android.R.layout.simple_list_item_1 , adreses );
            lv.setAdapter(adapter);

        reloadMapObjects ();

    }

    private void reloadMapObjects (  ){
        ArrayList<YaPoint> points =   SingleList.getPoints();
        balloons = new HashMap<>();

        Resources res = getResources();
        BalloonItem balloonForPointL = null;
        overlay.clearOverlayItems();
       // Overlay overlay = new Overlay(mMapController);
        for (YaPoint point  : points){

            // Create an object for the layer
            OverlayItem marker = new OverlayItem(point.get_point() , res.getDrawable(R.drawable.nav_sleep));
            // Create the balloon model for the object
            BalloonItem balloonForPoint = new BalloonItem(this, point.get_point() );
            balloonForPoint.setVisible(true);
            balloonForPoint.setText(point.get_index()+" "+point.get_adress());
            balloonForPoint.setOnBalloonListener(this);

            // Add the balloon model to the object
            marker.setBalloonItem(balloonForPoint);
            // Add the object to the layer
            overlay.addOverlayItem(marker);


        }

        mOverlayManager.addOverlay(overlay);

        mMapController.notifyRepaint();

    }


    @Override
    public void onBalloonViewClick(BalloonItem balloonItem, View view) {
       // OverlayItem item = balloonItem.getOverlayItem();

        //Toast.makeText(MainActivity.this, balloonItem.getText(), Toast.LENGTH_SHORT).show();
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
