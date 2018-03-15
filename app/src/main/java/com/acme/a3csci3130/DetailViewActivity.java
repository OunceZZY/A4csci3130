package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Student Name: 	Zhiyuan Zhang (Owen)
 * Student ID: 		B00716809
 * CS ID: 			zhiyuanz
 * Date: 			Mar. 15th
 * Assignment 3
 *
 * This class is for the page of DetailView, display a certain contact and allow user to
 * update the personal info
 *
 * And print out error message the info violates the rule
 *
 */
public class DetailViewActivity extends Activity {

    private EditText nameField, bnum_field, prim_bus_field, addre_field, pro_terr_field; // input fields

    Contact receivedPersonInfo;

    TextView testView;

    /**
     * read a contact and put infomation of a contact to the corresponding field
     *
     * @param savedInstanceState no particular use
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");

        // set up feedback text view: for error message
        testView = (TextView) findViewById(R.id.testView2);
        testView.setText("");

        nameField = (EditText) findViewById(R.id.name);
        bnum_field = (EditText) findViewById(R.id.business_number_input);
        prim_bus_field = (EditText) findViewById(R.id.primary_business_input);
        addre_field = (EditText) findViewById(R.id.address_input);
        pro_terr_field = (EditText) findViewById(R.id.province_territory_input);

        if (receivedPersonInfo != null) {
            nameField.setText(receivedPersonInfo.name);
            bnum_field.setText(receivedPersonInfo.business_number);
            prim_bus_field.setText(receivedPersonInfo.primary_business);
            addre_field.setText(receivedPersonInfo.address);
            pro_terr_field.setText(receivedPersonInfo.province_territory);
        }
    }

    /**
     * read the input field and validate the info and update to firebase
     *
     * and print out error message the info violates the rule
     *
     * @param v no particular use
     * @return void
     */
    public void updateContact(View v) {
        // renew the test
        testView.setText("");

        // read the input
        receivedPersonInfo.name = nameField.getText().toString();
        receivedPersonInfo.address = addre_field.getText().toString();
        receivedPersonInfo.business_number = bnum_field.getText().toString();
        receivedPersonInfo.primary_business = prim_bus_field.getText().toString();
        receivedPersonInfo.province_territory = pro_terr_field.getText().toString();

        // test validation of the input value
        int[] inputTest = Contact.inputValidation(receivedPersonInfo.name, receivedPersonInfo.business_number, receivedPersonInfo.primary_business, receivedPersonInfo.address, receivedPersonInfo.province_territory);
        String[] error = {"Name", "Business number", "Primary Business", "Address", "Province/Territory"};
        boolean errorExists = false; // to check if there is invalid input
        for (int i = 0; i < inputTest.length; i++) {
            if (inputTest[i] < 0) {
                errorExists = true;
                testView.append(error[i] + " information is invalid\n");
            }
        }
        if (errorExists) return; // if error then no update

        // update to firebase
        DatabaseReference updateInfo = FirebaseDatabase.getInstance().getReference().child("contacts");

        updateInfo.child(receivedPersonInfo.personalID).updateChildren(receivedPersonInfo.toMap());

        finish();

    }

    /**
     * erase the choosen contact
     *
     * @param v no particular use
     * @return void
     */
    public void eraseContact(View v) {
        //TODO: Erase contact functionality

        FirebaseDatabase.getInstance().getReference().child("contacts").child(receivedPersonInfo.personalID).removeValue();

        finish();
    }
}
