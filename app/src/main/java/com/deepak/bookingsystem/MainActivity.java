package com.deepak.bookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{


    private Spinner spinRoomType;
    private TextView etCheckDate, etCheckOutDate;
    private EditText etAdultNo, etChildNo, etRoomNo;
    private Button btnCalculate;
    private TextView tvResult, tvPrice;

    int year1, year2;
    int month1, month2;
    int day1, day2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCheckDate = findViewById(R.id.etCheckDate);
        etCheckOutDate = findViewById(R.id.etCheckOutDate);
        etAdultNo = findViewById(R.id.etAdultNo);
        etChildNo = findViewById(R.id.etChildNo);
        etRoomNo = findViewById(R.id.etRoomNo);
        spinRoomType = findViewById(R.id.spinRoomType);
        tvResult = findViewById(R.id.tvResult);
        tvPrice = findViewById(R.id.tvPrice);
        btnCalculate = findViewById(R.id.btnCalculate);

        //Room Type
        final String roomType[] = {"Select room type", "Deluxe - Rs.2000", "Presidential - Rs.5000", "Premium - Rs.4000"};

        final ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                roomType
        );
        spinRoomType.setAdapter(adapter);

        //Check Date

        etCheckDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate();
            }
        });

        //Check Out Date
        etCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutDate();
            }
        });

    }
        private void checkDate() {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            final int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = (month+1) + "/" + dayOfMonth + "/" + year;
                    year1 = year;
                    month1 = month;
                    day1 = dayOfMonth;
                    etCheckDate.setText(date);
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }

        private void checkOutDate() {

            final Calendar c = Calendar.getInstance();
            final int year = c.get(Calendar.YEAR);
            final int month = c.get(Calendar.MONTH);
            final int day = c.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = (month+1) + "/" + dayOfMonth + "/" + year;
                    year2 = year;
                    month2 = month;
                    day2 = dayOfMonth;
                    etCheckOutDate.setText(date);
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();



        //Calculation
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rooms;
                double total, price, vat, grossTotal;


                if (TextUtils.isEmpty(etCheckDate.getText())){
                    etCheckDate.setError("Please select check in date.");
                    return;
                }
                else if (TextUtils.isEmpty(etCheckOutDate.getText())){
                    etCheckOutDate.setError("Please select the checkout date");
                    return;
                }
                else if (TextUtils.isEmpty(etAdultNo.getText())){
                    etAdultNo.setError("Please enter no of adult");
                    return;
                }
                else if (TextUtils.isEmpty(etChildNo.getText())){
                    etChildNo.setError("Please enter no of children");
                    return;
                }
                else if(TextUtils.isEmpty(etRoomNo.getText())){
                    etRoomNo.setError("Enter no of rooms.");
                    return;
                }



                rooms = Integer.parseInt(etRoomNo.getText().toString());

                if (spinRoomType.getSelectedItem() == "Deluxe - Rs.2000"){
                    tvPrice.setText("2000");
                }
                if (spinRoomType.getSelectedItem() == "Presidential - Rs.5000"){
                    tvPrice.setText("5000");
                }
                if (spinRoomType.getSelectedItem() == "Premium - Rs.4000"){
                    tvPrice.setText("4000");
                }


//                //Count Days
//                Calendar cCal = Calendar.getInstance();
//                int cYear = cCal.get(Calendar.YEAR);
//                int cMonth = cCal.get(Calendar.MONTH);
//                int cDay = cCal.get(Calendar.DAY_OF_MONTH);
//
//                Calendar eCal = Calendar.getInstance();
//                int eYear = eCal.get(Calendar.YEAR);
//                int eMonth = eCal.get(Calendar.MONTH);
//                int eDay = eCal.get(Calendar.DAY_OF_MONTH);


                Calendar date1 = Calendar.getInstance();
                Calendar date2 = Calendar.getInstance();


                date1.clear();
                date1.set(year1,month1,day1);
                date2.clear();
                date2.set(year2,month2,day2);

                long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
                float dayCount = (float) diff / (24 * 60 * 60 *1000);

                price = Integer.parseInt(tvPrice.getText().toString());
                total = price * rooms * dayCount;
                vat = 0.13 * total;
                grossTotal = total + vat;

                tvResult.append("No of Days:"+ dayCount +" \n Total: Rs. " + total + "\n VAT (13%): Rs. "+ vat + "\n Grand Total: Rs. " + grossTotal + "\n \b");

            }
        });
    }
}
