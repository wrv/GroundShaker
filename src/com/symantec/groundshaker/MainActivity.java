package com.symantec.groundshaker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener, CompoundButton.OnCheckedChangeListener {
	
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	SeekBar xval,yval,zval;
	TextView txval, tyval, tzval;
	Boolean collect = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get the sensors service and the accelerometer value
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		//get Seekbars
		xval = (SeekBar) findViewById(R.id.seekBar1);
		yval = (SeekBar) findViewById(R.id.seekBar2);
		zval = (SeekBar) findViewById(R.id.seekBar3);
		txval = (TextView) findViewById(R.id.textView2);
		tyval = (TextView) findViewById(R.id.textView1);
		tzval = (TextView) findViewById(R.id.textView3);
		
		Switch s = (Switch) findViewById(R.id.switch1);
		if(s != null){
			s.setOnCheckedChangeListener(this);
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float equis = event.values[0];
		float igriega = event.values[1];
		float zeta = event.values[2];
		if(collect){
			xval.setProgress(10+Math.round(equis));
			txval.setText("X: " + (equis));
			yval.setProgress(10+Math.round(igriega));
			tyval.setText("Y: " + igriega);
			zval.setProgress(10+Math.round(zeta));
			tzval.setText("Z: " + (zeta));
		}else{
			xval.setProgress(10);
			txval.setText("X: 0");
			yval.setProgress(10);
			tyval.setText("Y: 0");
			zval.setProgress(10);
			tzval.setText("Z: 0");
		}
	}

	
	@Override
	protected void onResume()
	{
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			collect = true;
		} else {
			collect = false;
		}
	}
}
