package com.presencecontrol.m2m.m2m_presencecontrol;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

//java import


public class dmInterfaceActivityV1 extends ActionBarActivity {
    //atributtes
    String mlatitude="0";
    String mlongitude="0";
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm_interface);
        //Obtener latitud y longitud de googlePlayServicesUtils
        //primero comprovar si podemos conectar a googlePlayServices
        Log.d("----------------------layoutOk------------------------------------------", "------------------------------------");
        // create class object
        gps = new GPSTracker(this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d("---------------------LocationOk------------------------------------------", "------------------------------------");
            mlatitude=String.valueOf(latitude);
            mlongitude=String.valueOf(longitude);
        }
        //mlatitude="2";
        // mlongitude="3";
        Log.d("----------------------latitud y longitud y altitude------------------------------------------", mlatitude+" y "+mlongitude+"------------------------------------");
        //  String url="https://dweet.io:443/dweet/for/M2M_DM";
      // String url="https://dweet.io:443/dweet/for/M2M_DM?latitude="+mlatitude+"&longitude="+mlongitude;
        EditText editUrl=(EditText) findViewById(R.id.default_ipport_editText);
        String urlaux= editUrl.getText().toString();
        EditText editDevice=(EditText) findViewById(R.id.default_device_editText);
        String deviceaux= editDevice.getText().toString();
        EditText editPaiload=(EditText) findViewById(R.id.datoTOsend_editText);
        String pailoadaux= editPaiload.getText().toString();
        Log.d("------urlaux-------------", urlaux+"------------------------------------");
        //new
        //String url="http://"+urlaux+"/new";
        //String charset="UTF-8";
        //new getAsyncTask(url, charset).execute();
       // add paiload
        //String url="http://"+urlaux+"/post/?latitude="+mlatitude+"&longitude="+mlongitude;
//        try {
String url="http://"+urlaux+"/post/"+deviceaux+"/?prueba=1";
//         String charset="UTF-8";
//         String query = String.format("time=%s",
//                        URLEncoder.encode("1234", charset));
//         new postAsyncTask(url,charset,query).execute();
//         Log.d("------url-------------", url+"------------------------------------");
//        }catch(IOException e) {
//            Log.d("---Error en post ---", e.getMessage());
//        };

        new postAsyncTask(url).execute();
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            Log.d("---Error in readStream---", e.getMessage());
            return "";
        }
    }

    private String inputStreamToString(InputStream is) {
        String line;
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        try {
            while ((line = rd.readLine()) != null)
                total.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total.toString();
    }

//----------------------------------------------------------------------GET-----------------------------------------------------------//
    private class getAsyncTask extends AsyncTask<String, Void, String> {
        URL url;
        String charset;
        public getAsyncTask(String stringUrl, String mcharset) {
            try {
                url = new URL(stringUrl);
                charset=mcharset;
                Log.d("----------------------UrlOk------------------------------------------", "------------------------------------");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String result="Server not response";
            try{
                //url = new URL("https://dweet.io/dweet/for/M2M12345567789a?hello=world");
                URLConnection urlConnection = url.openConnection();
                urlConnection.setRequestProperty("Accept-Charset", charset);
                Log.d("----------------------UrlConnectionOk------------------------------------------", "------------------------------------");
                InputStream in0 = urlConnection.getInputStream();
                Log.d("----------------------getInputStreamOk------------------------------------------", "------------------------------------");
                InputStreamReader in = new InputStreamReader(in0);
                Log.d("----------------------getInputStreanReaderOk------------------------------------------", "------------------------------------");
                BufferedReader rd = new BufferedReader(in);
                Log.d("----------------------BufferedOk------------------------------------------", "------------------------------------");
                String line;
                StringBuilder total = new StringBuilder();
                try {
                    while ((line = rd.readLine()) != null)
                        total.append(line);            } catch (IOException e) {
                    e.printStackTrace();
                }
                result = total.toString();
                Log.d("-------------------------StringOk---------------------------------------", "------------------------------------");
                Log.d("respuesta de https://dweet.io:443/dweet/for/M2M_DM/", "------" + result + "-----");

            } catch (MalformedURLException e) {
                Log.d("---Error make URL---https://dweet.io:443/dweet/for/M2M_DM/", e.getMessage());
            } catch (IOException e) {
                Log.d("---Error IOException", e.getMessage());
            }
            return result;
        }

    @Override
    protected void onPostExecute(String result) {
        TextView t = (TextView) findViewById(R.id.respuestaSEND_textView);
        if(result != null) {
               t.setText(result);
        }else if(result == ""){
            t.setText("RESPONSE OK");
        }else{
            t.setText("TEST OK");
        } }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
     //---------------------------------------------------------POST------------------------------------------------------------------------------------//
     private class postAsyncTask extends AsyncTask<String, Void, String> {
        URL url;
//         String charset;
//         String query;
//         public postAsyncTask(String stringUrl,String mcharset,String mquery) {
//             try {
//                 url = new URL(stringUrl);
//                 charset=mcharset;
//                 query=mquery;
//                 Log.d("----------------------UrlOk------------------------------------------", "------------------------------------");
//             } catch (MalformedURLException e) {
//                 e.printStackTrace();
//             }
//         }
public postAsyncTask(String stringUrl) {
             try {
                 url = new URL(stringUrl);
                 Log.d("----------------------UrlOk------------------------------------------", "------------------------------------");
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             }
         }
         @TargetApi(Build.VERSION_CODES.KITKAT)
         @Override
         protected String doInBackground(String... params) {
             String result="Server not response";
             try{
                 //url = new URL("https://dweet.io/dweet/for/M2M12345567789a?hello=world");
                 URLConnection urlConnection = url.openConnection();
                  Log.d("----------------------UrlConnectionOk------------------------------------------", "------------------------------------");
                 urlConnection.setDoOutput(true);
                 Log.d("------set-----POST----","true");
                 InputStream in0 = urlConnection.getInputStream();
                 Log.d("----------------------getInputStreamOk------------------------------------------", "------------------------------------");
                 InputStreamReader in = new InputStreamReader(in0);
                 Log.d("----------------------getInputStreanReaderOk------------------------------------------", "------------------------------------");
                 BufferedReader rd = new BufferedReader(in);
                 Log.d("----------------------BufferedOk------------------------------------------", "------------------------------------");
                 String line;
                 StringBuilder total = new StringBuilder();
                 try {
                     while ((line = rd.readLine()) != null)
                         total.append(line);            } catch (IOException e) {
                     e.printStackTrace();
                 }
                 result = total.toString();
                 Log.d("-------------------------StringOk---------------------------------------", "------------------------------------");
                 Log.d("respuesta de https://dweet.io:443/dweet/for/M2M_DM/", "------" + result + "-----");

             } catch (MalformedURLException e) {
                 Log.d("---Error make URL---https://dweet.io:443/dweet/for/M2M_DM/", e.getMessage());
             } catch (IOException e) {
                 Log.d("---Error IOException", e.getMessage());
             }
             return result;
         }
         @Override
         protected void onPostExecute(String result) {
             TextView t = (TextView) findViewById(R.id.respuestaSEND_textView);
             if(result != null) {
                   t.setText(result);
             }else if(result == ""){
                 t.setText("RESPONSE OK");
             }else{
                 t.setText("TEST OK");
             } }
         @Override
         protected void onPreExecute() {
         }
         @Override
         protected void onProgressUpdate(Void... values) {
         }
     }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dm_interface, menu);
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
