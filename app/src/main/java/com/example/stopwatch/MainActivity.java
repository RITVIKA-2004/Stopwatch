package com.example.stopwatch;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView elapsedTimeTextView;
    private Button startButton, stopButton, holdButton;
    private Handler handler;
    private long startTime = 0L, elapsedTime = 0L;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);

        // Initialize Handler
        handler = new Handler();

        // Set click listeners for buttons
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdStopwatch();
            }
        });
    }

    private void startStopwatch() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(updateTimeRunnable, 0);
            isRunning = true;
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            handler.removeCallbacks(updateTimeRunnable);
            isRunning = false;
        }
    }

    private void holdStopwatch() {
        if (isRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimeRunnable);
            isRunning = false;
        }
    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;

            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;

            seconds %= 60;
            minutes %= 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            elapsedTimeTextView.setText(timeString);

            handler.postDelayed(this, 1000);
        }
    };
}