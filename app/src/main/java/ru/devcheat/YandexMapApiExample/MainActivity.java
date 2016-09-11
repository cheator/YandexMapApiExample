package ru.devcheat.YandexMapApiExample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import ru.devcheat.YandexMapApiExample.Adapter.YAListAdapter;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;

public class MainActivity extends AppCompatActivity implements OnBalloonListener {

    private static final int CM_DELETE_ID = 231;
    private MapView map ;
    OverlayManager mOverlayManager;
    MapController mMapController;
    Overlay overlay;
    Button btnSearch;
    GeocodeCallBack _cb ;
    ListView lv;
    YAListAdapter yaAdapter;
    Map<YaPoint , BalloonItem>  balloons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        mapwork();
        init();
    }

    private void init() {
        btnSearch = (Button) findViewById(R.id.btnSort);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadMapObjects ();

            }
        });

        yaAdapter = new YAListAdapter(MainActivity.this);
        lv.setAdapter(yaAdapter);
        registerForContextMenu(lv);
        /*
        lv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return false;
            }
        });
        */


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

        mOverlayManager.addOverlay(overlay);
        _cb = new GeocodeCallBack() {
            @Override
            public boolean onFinish() {

                addPoints();
                return true;
            }
        };


        GeoCodeOverlay geo =  new GeoCodeOverlay(map.getMapController(),_cb);
        byte GeoPriority  = 2;
        geo.setPriority(GeoPriority);
        mOverlayManager.addOverlay(geo);

    }
    private void addPoints( ){

        yaAdapter.add();
        reloadMapObjects ();

    }

    private void reloadMapObjects (  ){
        ArrayList<YaPoint> points =   SingleList.getPoints();


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
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            yaAdapter.remove(acmi.position);
            reloadMapObjects ();
           return true;
        }
        return super.onContextItemSelected(item);
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
