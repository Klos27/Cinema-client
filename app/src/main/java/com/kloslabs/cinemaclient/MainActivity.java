package com.kloslabs.cinemaclient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.kloslabs.cinemaclient.connections.*;
import java.io.IOException;
import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity implements StatusCallback{
    private Button mLedOnBtn;
    private Button mLedOffBtn;
    private SeekBar mLedSeekBar;
    private TextView mLedLabel;
    private TextView mLedStatus;
    private Button mMovieStartBtn;
    private TextView mMovieLabel;
    private TextView mMovieStatus;
    private Spinner mMovieSpinner;
    private Button mPreset1Btn;
    private Button mPreset2Btn;
    private Button mPreset3Btn;
    private Button mPreset4Btn;
    private Button mTurnOnAllBtn;
    private Button mTurnOffAllBtn;

    private Button mLight1OnBtn;
    private Button mLight1OffBtn;
    private TextView mLight1Label;
    private TextView mLight1Status;
    private Button mLight2OnBtn;
    private Button mLight2OffBtn;
    private TextView mLight2Label;
    private TextView mLight2Status;

    private TextView mLight3Status;
    private TextView mLight4Status;
    private TextView mLight5Status;
    private TextView mLight6Status;
    private TextView mLight7Status;
    private TextView mLight8Status;
    private TextView mLight9Status;

    private UDPReciver udpServer;
    private StatusTrigger statusTrigger;

    private boolean seekBarFlag;

    @Override
    public void updateStatus(String mystr)
    {
//        setStatusOn(mLedStatus);
        try {
////        Status;1;128;1;2;3;4;5;6;7;8;9;movie;1
            if(mystr.length() >= 37 && mystr.substring(0,6).equalsIgnoreCase("status")) {
                if (mystr.charAt(7) == '1')
                    setStatusOn(mLedStatus);
                else
                    setStatusOff(mLedStatus);
                setLedValue(Integer.parseInt(mystr.substring(9,12)));

                //-- MOV
                if(mystr.substring(31,36).equals("movie"))
                    setMovieStatus(Integer.parseInt(Character.toString(mystr.charAt(37))));

                //-- LIGHTS
                if (mystr.charAt(13) == '1')
                    setStatusOn(mLight1Status);
                else
                    setStatusOff(mLight1Status);
                if (mystr.charAt(15) == '1')
                    setStatusOn(mLight2Status);
                else
                    setStatusOff(mLight2Status);
                if (mystr.charAt(17) == '1')
                    setStatusOn(mLight3Status);
                else
                    setStatusOff(mLight3Status);
                if (mystr.charAt(19) == '1')
                    setStatusOn(mLight4Status);
                else
                    setStatusOff(mLight4Status);
                if (mystr.charAt(21) == '1')
                    setStatusOn(mLight5Status);
                else
                    setStatusOff(mLight5Status);
                if (mystr.charAt(23) == '1')
                    setStatusOn(mLight6Status);
                else
                    setStatusOff(mLight6Status);
                if (mystr.charAt(25) == '1')
                    setStatusOn(mLight7Status);
                else
                    setStatusOff(mLight7Status);
                if (mystr.charAt(27) == '1')
                    setStatusOn(mLight8Status);
                else
                    setStatusOff(mLight8Status);
                if (mystr.charAt(29) == '1')
                    setStatusOn(mLight9Status);
                else
                    setStatusOff(mLight9Status);
            }
        }
        catch(Exception e){
            Log.d("Exception", e.getMessage());
        }
    }

    private void setMovieStatus(int value){
        if(value == 0){ // OFF
            mMovieStatus.setText("Off");
            mMovieStatus.setBackgroundColor(Color.parseColor("#ac5d5e"));
        }
        else{   // ON
            mMovieStatus.setText("MOV " + String.valueOf(value));
            mMovieStatus.setBackgroundColor(Color.parseColor("#5dac71"));
        }
    }
    private void setLedValue(int value){
        mLedLabel.setText("LED " + value + "%");
        if(!seekBarFlag)
            mLedSeekBar.setProgress(value);
    }
    private void setStatusOn(TextView txtV){
        txtV.setText("On");
        txtV.setBackgroundColor(Color.parseColor("#5dac71"));
    }
    private void setStatusOff(TextView txtV){
        txtV.setText("Off");
        txtV.setBackgroundColor(Color.parseColor("#ac5d5e"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setTheme(R.style.Theme);
        setContentView(R.layout.activity_main);

        mLedOnBtn = findViewById(R.id.ledOnBtn);
        mLedOffBtn = findViewById(R.id.ledOffBtn);
        mLedSeekBar = findViewById(R.id.ledSeekBar);
        mLedLabel = findViewById(R.id.ledLabel);
        mLedStatus = findViewById(R.id.ledStatus);
        mMovieStartBtn = findViewById(R.id.movieStartBtn);
        mMovieLabel = findViewById(R.id.movieLabel);
        mMovieStatus = findViewById(R.id.movieStatus);
        mMovieSpinner = findViewById(R.id.movieSpinner);
        mPreset1Btn = findViewById(R.id.preset1Btn);
        mPreset2Btn = findViewById(R.id.preset2Btn);
        mPreset3Btn = findViewById(R.id.preset3Btn);
        mPreset4Btn = findViewById(R.id.preset4Btn);
        mTurnOnAllBtn = findViewById(R.id.turnOnAllBtn);
        mTurnOffAllBtn = findViewById(R.id.turnOffAllBtn);
        mLight1OnBtn = findViewById(R.id.light1OnBtn);
        mLight1OffBtn = findViewById(R.id.light1OffBtn);
        mLight1Label = findViewById(R.id.light1Label);
        mLight1Status = findViewById(R.id.light1Status);
        mLight2OnBtn = findViewById(R.id.light2OnBtn);
        mLight2OffBtn = findViewById(R.id.light2OffBtn);
        mLight2Label = findViewById(R.id.light2Label);
        mLight2Status = findViewById(R.id.light2Status);
        mLight3Status = findViewById(R.id.light3Status);
        mLight4Status = findViewById(R.id.light4Status);
        mLight5Status = findViewById(R.id.light5Status);
        mLight6Status = findViewById(R.id.light6Status);
        mLight7Status = findViewById(R.id.light7Status);
        mLight8Status = findViewById(R.id.light8Status);
        mLight9Status = findViewById(R.id.light9Status);

        seekBarFlag = false;

        mLedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ConnectionUDP.sendUDPMsg("LED " + Integer.valueOf(i).toString());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarFlag = true;
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarFlag = false;
            }
        });

        try{
            statusTrigger = new StatusTrigger();
            statusTrigger.start();
        } catch (Exception e) {
            exit(1);    //TODO change this to show popup with failed to start app info
        }
        try {
            udpServer = new UDPReciver(this);
            udpServer.start();
        } catch (IOException e) {
            exit(1);    //TODO change this to show popup with failed to create server info, while port 50000 is taken
        }
    }

    public void movieStartBtnClickHandler(View view) {
        String val = mMovieSpinner.getSelectedItem().toString();

        if(val == "Off")
            ConnectionUDP.sendUDPMsg("MOV 0");
        else{
            ConnectionUDP.sendUDPMsg(val.toUpperCase());
        }
    }
    public void ledOnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL00 1");
    }
    public void ledOffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL00 0");
    }
    public void turnOffAllBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST11 1");
    }
    public void turnOnAllBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST10 1");
    }
    public void preset1BtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST01 1");
    }
    public void preset2BtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST02 1");
    }
    public void preset3BtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST03 1");
    }
    public void preset4BtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("PST04 1");
    }
    public void light1OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL01 1");
    }
    public void light1OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL01 0");
    }
    public void light2OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL02 1");
    }
    public void light2OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL02 0");
    }
    public void light3OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL03 1");
    }
    public void light3OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL03 0");
    }
    public void light4OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL04 1");
    }
    public void light4OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL04 0");
    }
    public void light5OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL05 1");
    }
    public void light5OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL05 0");
    }
    public void light6OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL06 1");
    }
    public void light6OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL06 0");
    }
    public void light7OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL07 1");
    }
    public void light7OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL07 0");
    }
    public void light8OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL08 1");
    }
    public void light8OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL08 0");
    }
    public void light9OnBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL09 1");
    }
    public void light9OffBtnClickHandler(View view) {
        ConnectionUDP.sendUDPMsg("REL09 0");
    }
}
