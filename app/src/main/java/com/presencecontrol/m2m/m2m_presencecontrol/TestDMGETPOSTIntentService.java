package com.presencecontrol.m2m.m2m_presencecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by root on 14/01/15.
 */
public class TestDMGETPOSTIntentService extends Activity {
    //atributes
    //Intent mServiceIntent;

    DMGETPOSTIntentService mServiceGETPOST;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        TextView textOutput=(TextView)findViewById(R.id.test_output_TextView);
             /*Create a new Intent to start the DMGETPOSTIntentService
        * pase a URI in the Intents "data" field*/
//        mServiceIntent = new Intent(this, DMGETPOSTIntentService.class);
//        mServiceIntent.putExtra(EXTRA_GETPOSTINTENSERVICE_METHOD,"GET");
//        mServiceIntent.putExtra(EXTRA_GETPOSTINTENSERVICE_URL,"url");
//               //mServiceIntent.setData(Uri.parse(url));
//        this.startService(mServiceIntent);

           // String url="http://192.168.1.103:8080/new";
           // mServiceGETPOST.startActionGET(this,url);
          // String url="http://192.168.1.103:8080/post/tight-custody/?paiload=1234";
           //mServiceGETPOST.startActionPOSTHEADER(this,url);
           String url="http://192.168.1.103:8080/get/tight-custody";
           mServiceGETPOST.startActionGET(this,url);

           String text="getstatus no implementado";
           textOutput.setText( text);
    }
}
