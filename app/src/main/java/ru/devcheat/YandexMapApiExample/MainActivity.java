package ru.devcheat.YandexMapApiExample;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.devcheat.YandexMapApiExample.Adapter.YAListAdapter;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CM_DELETE_ID = -1;
    private static final int CM_EDIT_ID = -2;

    private MapView map;
    OverlayManager mOverlayManager;
    MapController mMapController;
    Overlay overlay;
    Button btnSearch_index, btnSearch_adress , btnSearch_distance;
    GeocodeCallBack _cb;
    ListView lv;
    YAListAdapter yaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        mapInit();
        init();
    }

    private void init() {
        btnSearch_index = (Button) findViewById(R.id.btnSort_index);
        btnSearch_adress = (Button) findViewById(R.id.btnSort_adress);
        btnSearch_distance = (Button) findViewById(R.id.btnSort_distance);
        btnSearch_adress.setOnClickListener(this);
        btnSearch_index.setOnClickListener(this);
        btnSearch_distance.setOnClickListener(this);


        yaAdapter = new YAListAdapter(MainActivity.this);
        lv.setAdapter(yaAdapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reloadMapObjects();
                mMapController.setPositionAnimationTo(SingleList.getPointByIndex(i).get_point());
                SingleList.getPointByIndex(i).marker.setDrawable(getResources().getDrawable(R.drawable.nav_act));
              if ((int) SingleList.getPointByIndex(i).getDistanse() > 0 ){
                    Toast.makeText(MainActivity.this , " До точки "+ (int) SingleList.getPointByIndex(i).getDistanse() +" метров" , Toast.LENGTH_SHORT).show();
                }
                mMapController.notifyRepaint();
            }
        });
    }


    private void mapInit() {
        // Load required resources
        Resources res = getResources();
        byte MainPriority = 1;
        map = (MapView) findViewById(R.id.map);
        mMapController = map.getMapController();
        mOverlayManager = mMapController.getOverlayManager();
        mOverlayManager.getMyLocation().setEnabled(true);

        overlay = new Overlay(mMapController);
        overlay.setPriority(MainPriority);

        mOverlayManager.addOverlay(overlay);
        _cb = new GeocodeCallBack() {
            @Override
            public void onFinish() {
                addPoints();
            }
        };


        GeoCodeOverlay geo = new GeoCodeOverlay(map.getMapController(), _cb);
        byte GeoPriority = 2;
        geo.setPriority(GeoPriority);
        mOverlayManager.addOverlay(geo);

    }

    private void addPoints() {

        yaAdapter.add();
        reloadMapObjects();

    }

    private void reloadMapObjects() {
        ArrayList<YaPoint> points = SingleList.getPoints();

        Resources res = getResources();
        overlay.clearOverlayItems();

        for (YaPoint point : points) {

            // Create an object for the layer
            OverlayItem marker = new OverlayItem(point.get_point(), res.getDrawable(R.drawable.nav_sleep));
            BalloonItem balloonForPoint = new BalloonItem(this, point.get_point());
            balloonForPoint.setVisible(true);
            balloonForPoint.setText(point.get_index() + " " + point.get_adress());
            marker.setBalloonItem(balloonForPoint);
            overlay.addOverlayItem(marker);
            overlay.setVisible(true);
            point.marker = marker;

        }

        mOverlayManager.addOverlay(overlay);
        mMapController.notifyRepaint();

        if ( mOverlayManager.getMyLocation().getMyLocationItem() != null){
            SingleList.addDistance(mOverlayManager.getMyLocation().getMyLocationItem().getGeoPoint());
            btnSearch_distance.setEnabled(true);
        }else{
            btnSearch_distance.setEnabled(false);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
        menu.add(1, CM_EDIT_ID, 0, "Редактировать адрес");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CM_DELETE_ID:
                yaAdapter.remove(acmi.position);
                reloadMapObjects();
                break;

            case CM_EDIT_ID:
                showInputDialog(acmi.position);
                reloadMapObjects();
                break;
        }
        return super.onContextItemSelected(item);
    }

    protected void showInputDialog(int pos) {

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.edit_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final int position = pos;
        final EditText editText = (EditText) promptView.findViewById(R.id.etAdress);
        editText.setText(SingleList.getText(pos));

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        yaAdapter.edit(position, editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSort_adress:
                SingleList.sortAdress();
                yaAdapter.notifyDataSetChanged();
                reloadMapObjects();
                break;
            case R.id.btnSort_index:
                SingleList.sortIndex();
                yaAdapter.notifyDataSetChanged();
                reloadMapObjects();
                break;
            case R.id.btnSort_distance:
                SingleList.sortDistance();
                yaAdapter.notifyDataSetChanged();
                reloadMapObjects();
                break;
        }
    }
}
