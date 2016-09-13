package ru.devcheat.YandexMapApiExample.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.devcheat.YandexMapApiExample.R;
import ru.devcheat.YandexMapApiExample.SingleList;
import ru.devcheat.YandexMapApiExample.YaPoint;


public class YAListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<YaPoint> objects;

    public YAListAdapter(Context context) {
        ctx = context;
        objects = SingleList.getPoints();
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add() {
        this.objects = SingleList.getPoints();

        if (objects.size() > 0) {
            notifyDataSetChanged();
        }

    }

    public void edit(int position, String adress) {
        SingleList.setText(position, adress);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        // this.records = SingleList.getPoints();

        SingleList.remove(objects.get(position));
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.point_item, parent, false);
        }

        YaPoint p = getPoint(position);
        ((TextView) view.findViewById(R.id.tvInd)).setText(p.get_index() + "");
        ((TextView) view.findViewById(R.id.tvAdress)).setText(p.get_adress());

        ImageView nav = (ImageView) view.findViewById(R.id.ivImg);
        nav.setTag(position);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
                intent.setPackage("ru.yandex.yandexnavi");
                YaPoint p = getPoint((Integer) view.getTag());
                PackageManager pm = ctx.getPackageManager();
                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

                // Проверяем, установлен ли Яндекс.Навигатор
                if (infos == null || infos.size() == 0) {
                    // Если нет - будем открывать страничку Навигатора в Google Play
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=ru.yandex.yandexnavi"));
                } else {
                    intent.putExtra("lat_to", p.get_point().getLat());
                    intent.putExtra("lon_to", p.get_point().getLon());
                }
                ctx.startActivity(intent);
            }
        });

        return view;
    }

    YaPoint getPoint(int position) {
        return ((YaPoint) getItem(position));
    }


}
