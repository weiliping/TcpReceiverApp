package com.example.tcpreceiverapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class MainActivity extends AppCompatActivity {
    private static final int cap = 8192;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textShowId);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
    public void oKOnClick(View view) {
        ZContext context = new ZContext();
        ZMQ.Socket socket = context.createSocket(SocketType.REP);
        EditText tcp = findViewById(R.id.editNameId);
        socket.bind(tcp.getText().toString());
        StringBuilder result = null;
        while (!Thread.currentThread().isInterrupted()) {
            byte[] reply = new byte[cap];
            socket.recv(reply, 0, cap, 0);
            result = new StringBuilder();
            for (byte aByte : reply) {
                result.append(String.format("%02x", aByte));
            }
        }

        TextView textView = findViewById(R.id.textShowId);
        textView.setText(result.toString());
    }
}