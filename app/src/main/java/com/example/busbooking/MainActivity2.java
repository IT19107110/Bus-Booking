package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper myDb;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myDb = new DatabaseHelper( this );
        button = (Button) findViewById(R.id.button);
        ViewDetails();

}

    public void ViewDetails() {
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDb.getAllData();
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("View is Empty !!!", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Room Type :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Phone No :" + res.getString(2) + "\n");
                            buffer.append("Email :" + res.getString(3) + "\n");
                            buffer.append("Check In :" + res.getString(4) + "\n");
                            buffer.append("Check Out :" + res.getString(5) + "\n");
                            buffer.append("No of Rooms :" + res.getString(6) + "\n");
                            buffer.append("Total Cost :" + res.getString(7) + "\n\n");

                        }
                        showMessage("Customer Booking Details", buffer.toString());

                    }
                }
        );
    }

    private void showMessage(String rooms_details, String toString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(rooms_details);
        builder.setMessage(toString);
        builder.show();
    }




}
