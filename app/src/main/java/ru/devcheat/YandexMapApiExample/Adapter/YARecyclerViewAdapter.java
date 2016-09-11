package ru.devcheat.YandexMapApiExample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.devcheat.YandexMapApiExample.R;
import ru.devcheat.YandexMapApiExample.SingleList;
import ru.devcheat.YandexMapApiExample.YaPoint;


public class YARecyclerViewAdapter extends RecyclerView.Adapter<YARecyclerViewAdapter.ViewHolder> {

    private List<YaPoint> records;

    public YARecyclerViewAdapter() {
        this.records = SingleList.getPoints();
    }
    public void add (){
        this.records = SingleList.getPoints();

        if (records.size()>0 ){
            notifyItemInserted(records.size()-1);
        }

    }
    public void remove (YaPoint point){
       // this.records = SingleList.getPoints();

        int position = records.indexOf(point);
        records.remove(position);
        notifyItemRemoved(position);

    }
    public void remove (int position){
        // this.records = SingleList.getPoints();

        records.remove(position);
        notifyItemRemoved(position);

    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.point_item, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        YaPoint record = records.get(i);
        viewHolder.index.setText(record.get_index()+"");
        viewHolder.adress.setText(record.get_adress());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView index;
        private TextView adress;

        public ViewHolder(View itemView) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.tvInd);
            adress = (TextView) itemView.findViewById(R.id.tvAdress);

        }
    }
}