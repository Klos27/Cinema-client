package com.kloslabs.cinemaclient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private UDPReciver udpServer;

    private String change;

    @Override
    public void updateStatus(String mystr)
    {
//        Status;1;1;1;movie;1
        if(mystr.charAt(7) == '1')
            setStatusOn(mLedStatus);
        else
            setStatusOff(mLedStatus);
        if(mystr.charAt(9) == '1')
            setStatusOn(mLight1Status);
        else
            setStatusOff(mLight1Status);
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

//        mNextBtn.setOnClickListener(new NextBtnClass());

//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Hajd batons <Klasa Anonim>", Snackbar.LENGTH_SHORT).show();
//                mSeekBar1.setVisibility(View.INVISIBLE);
//
//            }
//        });

        mLedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ConnectionUDP.sendUDPMsg("LED " + Integer.valueOf(i).toString());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        try {
            udpServer = new UDPReciver(this);
            udpServer.start();
        } catch (IOException e) {
            exit(1);    //TODO change this to show popup with failed to create server, while port 50000 is taken
        }

    }
    public void turnOffAllBtnClickHandler(View view) {
//        Snackbar.make(view, "Szoł batons <OnClickHandler>", Snackbar.LENGTH_SHORT).show();
//        mSeekBar1.setVisibility(View.VISIBLE);
        ConnectionUDP.sendUDPMsg("PST 1");
    }
}
