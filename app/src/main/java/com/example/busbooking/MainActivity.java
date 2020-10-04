package com.example.busbooking;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    DatePickerDialog picker;
    EditText eText, tname, name, email, phone, departure, arrival, noSeats,tcost;
    private  Button cButton, view, update, delete, calender,cost,next;
    private int year;
    private int month;
    private int day;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner5;

    static final int DATE_DIALOG_ID1 = 1;
    int cur = 0;

    AwesomeValidation awesomeValidation;
    String[] Travels = { "Royal Travels", "NCG Travels", "Surena Travels"};
    String[] Departure = { "Batticaloa", "Colombo", "Jaffna"};
    String[] Arrival = { "Colombo", "Jaffna", "Batticaloa"};
    String[] NoOfSeats = { "01", "02", "03","04","05","06","07","08","09","10"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        tname=(EditText)findViewById(R.id.tname);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        departure=(EditText)findViewById(R.id.departure);
        arrival=(EditText)findViewById(R.id.arrival);
        noSeats=(EditText)findViewById(R.id.noOfSeats);
        tcost=(EditText)findViewById(R.id.tcost);
        cButton=(Button)findViewById(R.id.cButton);
        view=(Button)findViewById(R.id.view);
        update=(Button)findViewById(R.id.update);
        cost=(Button)findViewById(R.id.cost);
        next=(Button)findViewById(R.id.next);
        delete=(Button)findViewById(R.id.delete);
        calender=(Button)findViewById(R.id.calender);
        eText=(EditText) findViewById(R.id.date);
        eText.setInputType(InputType.TYPE_NULL);
        tname.getText().toString();
        departure.getText().toString();
        arrival.getText().toString();
        noSeats.getText().toString();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });


        AddData();
        ViewDetail();
        DeleteDetail();
        UpdateDetail();

        setCurrentDateOnView();
        addListenerOnButton();


        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noSeats.getText().toString().length() == 0) {
                    noSeats.setText("0");
                }
                tcost.setText(String.valueOf(calculate()));


            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(onItemSelectedListener1);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Travels);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(onItemSelectedListener2);

        ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Departure);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner2.setAdapter(a);

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(onItemSelectedListener3);

        ArrayAdapter ada = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Arrival);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner3.setAdapter(ada);

        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner5.setOnItemSelectedListener(onItemSelectedListener4);

        ArrayAdapter ada1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,NoOfSeats);
        ada1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner5.setAdapter(ada1);


    //initialize validation
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.phone, ".{10}",R.string.invalid_phone);



    }

    AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(Travels[position]);
                    tname.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };


    AdapterView.OnItemSelectedListener onItemSelectedListener2 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String d1 = String.valueOf(Departure[position]);
                    departure.setText(d1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };

    AdapterView.OnItemSelectedListener onItemSelectedListener3 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String a1 = String.valueOf(Arrival[position]);
                    arrival.setText(a1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };

    AdapterView.OnItemSelectedListener onItemSelectedListener4 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String n = String.valueOf(NoOfSeats[position]);
                    noSeats.setText(n);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };



    public void DeleteDetail(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDb.deleteDetail(email.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Booking cancelled", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Booking not cancelled", Toast.LENGTH_LONG).show();
                    }

                }
        );
    }
    public void UpdateDetail(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate= myDb.updateDetail(tname.getText().toString(),name.getText().toString(),email.getText().toString(),phone.getText().toString(),departure.getText().toString(),arrival.getText().toString(),eText.getText().toString(),noSeats.getText().toString(),tcost.getText().toString());
                        if(isUpdate==true)
                            Toast.makeText(MainActivity.this,"Booking details updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Booking details not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData() {
        cButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(tname.getText().toString(),name.getText().toString(), email.getText().toString(), phone.getText().toString(), departure.getText().toString(), arrival.getText().toString(), eText.getText().toString(), Integer.parseInt(noSeats.getText().toString()),Integer.parseInt(tcost.getText().toString()));
                        if (isInserted == true && awesomeValidation.validate())
                            Toast.makeText(MainActivity.this, "Booking Confirmed", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Booking not Confirmed", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void ViewDetail(){
        view.setOnClickListener(
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
                            buffer.append("Travels Name :- " + res.getString(0) + "\n");
                            buffer.append("Name :- " + res.getString(1) + "\n");
                            buffer.append("Email Address :- " + res.getString(2) + "\n");
                            buffer.append("Phone Number :- " + res.getString(3) + "\n");
                            buffer.append("Departure :- " + res.getString(4) + "\n");
                            buffer.append("Arrival :- " + res.getString(5) + "\n");
                            buffer.append("Date :- " + res.getString(6) + "\n");
                            buffer.append("No of Seats :- " + res.getString(7) + "\n");
                            buffer.append("Total Cost :- " + res.getString(8) + "\n\n");

                        }
                        showMessage("View Booking Details", buffer.toString());

                    }
                }
        );
    }

    public void showMessage(String view_booking_details,String toString){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(view_booking_details);
        builder.setMessage(toString);
        builder.show();
    }

    // display current date
    public void setCurrentDateOnView() {

        eText = (EditText) findViewById(R.id.date);


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        eText.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

    }

    public void addListenerOnButton() {

        calender = (Button) findViewById(R.id.calender);

        calender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID1);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID1:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID1;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

                // set selected date into textview
                eText.setText("" + new StringBuilder().append(day).append("-").append(month + 1)
                        .append("-").append(year)
                        .append(" "));

        }
    };

    public int calculate() {
        int tcost1 = 1100;
        int tcost2 =  1300;
        int tcost3 =  1200;

        int tnum = Integer.parseInt(noSeats.getText().toString());


        if(tname.getText().toString().equalsIgnoreCase("Royal Travels")){

            int cal = tcost1 * tnum;
            return cal;


        }
        else if(tname.getText().toString().equalsIgnoreCase("NCG Travels")){

            int cal = tcost2 * tnum;
            return cal;


        }
        else{
            int cal = tcost3 * tnum;
            return cal;
        }
    }

}


