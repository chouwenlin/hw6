package com.example.wenlin.volley;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wenlin.volley.beans.Data;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private RequestQueue requestQueue;
    private TextView mText;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.testresult);
        this.execute();
    }
    private void execute(){
        this.requestQueue = Volley.newRequestQueue(this);
        this.doGet();
    }

    private void doGet(){
        showDialog();
        String url= "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=55ec6d6e-dc5c-4268-a725-d04cc262172b&limit=10";
        final StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                hideDialog();
                Gson gson=new Gson();
                Data data=gson.fromJson(response, Data.class);
                String no="";
                for(Data.ResultItem resultItem:data.getResult().getResults()){
                    no += resultItem.get_id()+"\n"+"列車進入月台車站站名: "+resultItem.getStation()+"\n"+"目的地: "+resultItem.getDestination()+"\n\n";
                }
                mText.setText(no);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                if(error.networkResponse != null) {
                    Toast.makeText(MainActivity.this, "  error", Toast.LENGTH_SHORT).show();
                }else{
                    nonetwork();
                }
            }
        });
        requestQueue.add(req);

    }

    //no network dialog
    private void nonetwork(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(" error");
        alertDialog.setMessage("check your network and reload app.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void showDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("wait a minute");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    private void hideDialog() {
        if (pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
