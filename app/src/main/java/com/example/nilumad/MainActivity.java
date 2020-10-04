package com.example.nilumad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText n1,n2,n3,n4,n5,n6,n7;
    Button bb1,bb2,bb3,bb4,search;
    AwesomeValidation awesomeValidation;
    Spinner spinner1;
    Spinner spinner2;

    String[] Arrival = {"Jaffna", "Colombo","Batticaloa","Trincomalee","Kandy"};

    String[] Deaparture = {"Jaffna", "Colombo","Batticaloa","Trincomalee","Kandy"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        n1=(EditText)findViewById(R.id.s1);
        n2=(EditText)findViewById(R.id.s2);
        n3=(EditText)findViewById(R.id.s3);
        n4=(EditText)findViewById(R.id.s4);
        n5=(EditText)findViewById(R.id.s5);
        n6=(EditText)findViewById(R.id.s6);
        n7=(EditText)findViewById(R.id.s7);
        bb1=(Button)findViewById(R.id.b1);
        bb2=(Button)findViewById(R.id.bt2);
        bb3=(Button)findViewById(R.id.b3);
        bb4=(Button)findViewById(R.id.b4);
        search=(Button)findViewById(R.id.button);

        AddData();
        viewAll();
        UpdateData();
        deleteData();
        SearchData();

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.s1, RegexTemplate.NOT_EMPTY,R.string.invalid_travelsname);
        awesomeValidation.addValidation(this,R.id.s2,".{4}",R.string.ivalid_busno);
        awesomeValidation.addValidation(this,R.id.s3, RegexTemplate.NOT_EMPTY,R.string.invalid_drivername);
        awesomeValidation.addValidation(this,R.id.s4, RegexTemplate.NOT_EMPTY,R.string.invalid_departure);
        awesomeValidation.addValidation(this,R.id.s5, RegexTemplate.NOT_EMPTY,R.string.invalid_arrival);
        awesomeValidation.addValidation(this,R.id.s6,Range.closed(1,50),R.string.invalid_noofsets);
        awesomeValidation.addValidation(this,R.id.s7, Range.closed(1000,1400),R.string.ivalid_ticketprice);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrival);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner4 );
        spinner2.setOnItemSelectedListener(onItemSelectedListener2);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Deaparture);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner2.setAdapter(aa1);


    }

    AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(Arrival[position]);
                    n4.setText(s1);
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
                    String s2 = String.valueOf( Deaparture[position] );
                    n5.setText( s2 );
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }


            };
    public void deleteData(){
        bb4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows =myDb.deleteData(n2.getText().toString());
                        if(deletedRows >0)
                            Toast.makeText(MainActivity.this,"Data is delete",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not delete",Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
    public void UpdateData(){
        bb3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate =myDb.updateData(n1.getText().toString(),n2.getText().toString(),n3.getText().toString(),n4.getText().toString(),n5.getText().toString(),n6.getText().toString(),n7.getText().toString());
                        if(isUpdate == true && awesomeValidation.validate() )
                            Toast.makeText(MainActivity.this,"Bus Details Updateded",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Updation Fail",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void AddData(){
        bb1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       boolean isInserted= myDb.insertData(n1.getText().toString(),n2.getText().toString(),n3.getText().toString(),n4.getText().toString(),n5.getText().toString(),n6.getText().toString(),n7.getText().toString());
                                       if(isInserted==true && awesomeValidation.validate())
                                           Toast.makeText( MainActivity.this,"data inserted",Toast.LENGTH_LONG ).show();
                                       else
                                           Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                                   }
                               }
        );
    }
    public void viewAll(){
        bb2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("BUS DETAILS","NO DATA");
                            return;
                    }

                    StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("TravelsName :"+res.getString(0)+"\n");
                            buffer.append("VehicleNo :"+res.getString(1)+"\n");
                            buffer.append("Drivername :"+res.getString(2)+"\n");
                            buffer.append("Departure :"+res.getString(3)+"\n");
                            buffer.append("Arrival :"+res.getString(4)+"\n");
                            buffer.append("TotalSeats :"+res.getString(5)+"\n");
                            buffer.append("TicketPrice :"+res.getString(6)+"\n\n");
                        }
                        showMessage("BUS DETAILS",buffer.toString());


                    }

                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = myDb.searchData(n2.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    n1.setText( data.getString(0));
                    n2.setText( data.getString(1));
                    n3.setText( data.getString(2));
                    n4.setText( data.getString(3));
                    n5.setText( data.getString(4));
                    n6.setText( data.getString(5));
                    n7.setText( data.getString(6));
                }
            }
        });

    }
    private void clearControls(){
        n1.setText("");
        n2.setText("");
        n3.setText("");
        n4.setText("");
        n5.setText("");
        n6.setText("");
        n7.setText("");
    }

}