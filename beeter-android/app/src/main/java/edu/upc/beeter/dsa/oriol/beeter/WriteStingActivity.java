package edu.upc.beeter.dsa.oriol.beeter;

/**
 * Created by Oriol on 10/05/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import edu.upc.beeter.dsa.oriol.beeter.api.AppException;
import edu.upc.beeter.dsa.oriol.beeter.api.BeeterAPI;
import edu.upc.beeter.dsa.oriol.beeter.api.Sting;

public class WriteStingActivity extends Activity {
    private final static String TAG = WriteStingActivity.class.getName();

    private class PostStingTask extends AsyncTask<String, Void, Sting> {
        private ProgressDialog pd;

        @Override
        protected Sting doInBackground(String... params) {
            Sting sting = null;
            try {
                sting = BeeterAPI.getInstance(WriteStingActivity.this)
                        .createSting(params[0], params[1]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return sting;
        }

        @Override
        protected void onPostExecute(Sting result) {
            showStings(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteStingActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_sting_layout);

    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void postSting(View v) {
        EditText etSubject = (EditText) findViewById(R.id.etSubject);
        EditText etContent = (EditText) findViewById(R.id.etContent);

        String subject = etSubject.getText().toString();
        String content = etContent.getText().toString();

        (new PostStingTask()).execute(subject, content);
    }

    private void showStings(Sting result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-sting", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }

}