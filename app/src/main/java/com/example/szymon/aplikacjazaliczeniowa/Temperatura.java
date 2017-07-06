package com.example.szymon.aplikacjazaliczeniowa;


        import android.content.Context;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.icu.text.SimpleDateFormat;
        import android.os.Build;
        import android.support.annotation.RequiresApi;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;


@RequiresApi(api = Build.VERSION_CODES.N)
public class Temperatura extends ActionBarActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometr;
    TextView title;
    ImageView iv;
    GregorianCalendar data= new GregorianCalendar();
    Integer a=data.get(Calendar.MONTH)+1;
    String dat=String.valueOf(data.get(Calendar.YEAR)+"."+ a+ "." + data.get(Calendar.DAY_OF_MONTH));

    GregorianCalendar czas= new GregorianCalendar();
    String cz=String.valueOf(czas.get(Calendar.HOUR_OF_DAY)+":"+ czas.get(Calendar.MINUTE)+ ":" + czas.get(Calendar.SECOND));
    EditText t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperatura);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        title = (TextView)findViewById(R.id.txt);
        iv = (ImageView)findViewById(R.id.imageView1);
        t=(EditText)findViewById(R.id.edit);
    }





    



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    public void onAccuracyChanged(Sensor arg0, int arg1){
        //nie wykorzystywane
    }

    public void onSensorChanged(SensorEvent event){

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(Math.abs(x) > Math.abs(y)){
            if(x < 0){
                iv.setImageResource(R.drawable.prawo);


                t.setText(cz);

            }
            if(x > 0){
                iv.setImageResource(R.drawable.lewo);
                t.setText(dat);
            }
        } else {
            if(y < 0){
                iv.setImageResource(R.drawable.gora);
            }
            if(y > 0){

            }
        }

        if(x > (-2) && x < (2) && y > (-2) && y < (2)){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometr,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}