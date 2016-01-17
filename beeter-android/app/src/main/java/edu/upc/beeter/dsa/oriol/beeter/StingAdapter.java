package edu.upc.beeter.dsa.oriol.beeter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.beeter.dsa.oriol.beeter.api.Sting;

/**
 * Created by Oriol on 09/05/2015.
 */
public class StingAdapter extends BaseAdapter {

    private ArrayList<Sting> data;
    private LayoutInflater inflater;



    @Override
    public int getCount() {
    //nº de files que hi haurà a la llista
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
        //El model de dades per a una posició
    }

    @Override
    public long getItemId(int position) {
        return ((Sting) getItem(position)).getStingid();
        //Retorna el valor únic
    }

    ///////////////////////////
    public StingAdapter(Context context, ArrayList<Sting> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    /////////////////////////// Is used to hold the data in the View objects
    private static class ViewHolder {
        TextView tvSubject;
        TextView tvUsername;
        TextView tvDate;
    }
    ////////////////////////////A ListView shows as many Views fit in the screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) { //Inflate the layout, set data (ViewHolder) calling setTag
            convertView = inflater.inflate(R.layout.list_row_sting, null);
            viewHolder = new ViewHolder();
            viewHolder.tvSubject = (TextView) convertView
                    .findViewById(R.id.tvSubject);
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tvUsername);
            viewHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        }
        else { //Android is reciclyng the view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Set data in ViewHolder
        String subject = data.get(position).getSubject();
        String username = data.get(position).getUsername();
        String date = SimpleDateFormat.getInstance().format(
                data.get(position).getLastModified());
        viewHolder.tvSubject.setText(subject);
        viewHolder.tvUsername.setText(username);
        viewHolder.tvDate.setText(date);
        //Return convertView
        return convertView;
    }


}
