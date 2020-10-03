package com.example.bus_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        next=findViewById( R.id.next );

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent( MainActivity2.this,MainActivity3.class );
                startActivity( next );

            }
        } );
    }

}