package edu.upc.beeter.dsa.oriol.beeter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.beeter.dsa.oriol.beeter.api.AppException;
import edu.upc.beeter.dsa.oriol.beeter.api.BeeterAPI;
import edu.upc.beeter.dsa.oriol.beeter.api.Sting;
import edu.upc.beeter.dsa.oriol.beeter.api.StingCollection;

public class BeeterMainActivity extends ListActivity {

    private final static String TAG = BeeterMainActivity.class.toString();
    //private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
    //        "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
    //        "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
    //        "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
    //        "augue", "purus" };
    //private ArrayAdapter<String> adapter;

    private ArrayList<Sting> stingsList;
    private StingAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beeter_main);

        stingsList = new ArrayList<Sting>();
        adapter = new StingAdapter(this, stingsList);
        setListAdapter(adapter);

        //Final amb SharedPreferences
        SharedPreferences prefs = getSharedPreferences("beeter-profile",
                Context.MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        final String password = prefs.getString("password", null);

        //////////////////////////////////
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alicia", "alicia"
                        .toCharArray());
            }
        });
        //adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, items);
        //setListAdapter(adapter);
        (new FetchStingsTask()).execute();
    }
/////////////////////////////////////////////////////////////////////////////

    ///FetchStingsTask
    private class FetchStingsTask extends
            AsyncTask<Void, Void, StingCollection> {
        private ProgressDialog pd;

        @Override///////////////////Segon
        protected StingCollection doInBackground(Void... params) {
            StingCollection stings = null;
            try {
                stings = BeeterAPI.getInstance(BeeterMainActivity.this)
                        .getStings();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return stings;
        }

        @Override////////////////////Tercer
        protected void onPostExecute(StingCollection result) {
            //ArrayList<Sting> stings = new ArrayList<Sting>(result.getStings());
            //for (Sting s : stings) {
            //    Log.d(TAG, s.getStingid() + "-" + s.getSubject());
            //}
            //if (pd != null) {
            //    pd.dismiss();
            //}
            addStings(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override//////////////////Primer
        protected void onPreExecute() {
            pd = new ProgressDialog(BeeterMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }
    //////////////////////////////////////////////////////////////////////////////
    //Add a StingCollection to the ListMovies
    //Notify the UI that data has changed
    private void addStings(StingCollection stings){
        stingsList.addAll(stings.getStings());
        adapter.notifyDataSetChanged();
    }

    ///////////////////////////////////////////////////////////////////////////////
    //Obtencion de la URI del Sting seleccionado
    //@Override
    //protected void onListItemClick(ListView l, View v, int position, long id) {
    //   Sting sting = stingsList.get(position);
    //    Log.d(TAG, sting.getLinks().get("self").getTarget());
    //}

    //BeeterMainActivity -> StingDetailActivity
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Sting sting = stingsList.get(position);
        Log.d(TAG, sting.getLinks().get("self").getTarget());

        //An Intent encapsulates a request, made to Android, for some activity to do something
        Intent intent = new Intent(this, StingDetailActivity.class);
        //Pass data through Intent extras
        intent.putExtra("url", sting.getLinks().get("self").getTarget());
        //Start activity calling startActivity
        startActivity(intent);
    }

    ////////////////////////////////////////////////////////////
    //Adding a Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miWrite:
                Intent intent = new Intent(this, WriteStingActivity.class);
                startActivityForResult(intent, WRITE_ACTIVITY);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final static int WRITE_ACTIVITY = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case WRITE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String jsonSting = res.getString("json-sting");
                    Sting sting = new Gson().fromJson(jsonSting, Sting.class);
                    stingsList.add(0, sting);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //Inflate the menu: this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beeter_main, menu);
        return true;
    }


}
