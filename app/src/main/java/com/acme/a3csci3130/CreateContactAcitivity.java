package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Student Name: 	Zhiyuan Zhang (Owen)
 * Student ID: 		B00716809
 * CS ID: 			zhiyuanz
 * Date: 			Mar. 15th
 * Assignment 3
 *
 * This class responds to user if they want to create contact
 * read the data from input field and send to firebase
 *
 * And print out error message the info violates the rule
 *
 */
public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, business_number_field, primary_business_field, address_field, prov_terr_field;

    private MyApplicationData appState;

    private DatabaseReference mDatabase;

    private TextView testView; // error message display

    /**
     * simply get all fields ready for read
     *
     * @param savedInstanceState no particular use
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        // The only button
        submitButton = (Button) findViewById(R.id.submitButtonCreate); // no use
        // Fields: names, b_num, prim_bus, addr., prov/terr
        nameField = (EditText) findViewById(R.id.name4);
        business_number_field = (EditText) findViewById(R.id.business_number_input4);
        primary_business_field = (EditText) findViewById(R.id.primary_business_input4);
        address_field = (EditText) findViewById(R.id.address_input4);
        prov_terr_field = (EditText) findViewById(R.id.province_territory_input4);

        testView = (TextView) findViewById(R.id.testView);
    }

    /**
     * update firebase or print error message
     *
     * @param v no particular use
     * @return void
     */
    public void submitInfoButton(View v) {
        testView.setText("");
        // each entry needs a unique ID
        String personID = appState.firebaseReference.push().getKey();

        String name = nameField.getText().toString();
        String business_number = business_number_field.getText().toString();
        String primary_business = primary_business_field.getText().toString();
        String address = address_field.getText().toString();
        String prov_terr = prov_terr_field.getText().toString();

        // error checking
        int[] inputTest = Contact.inputValidation(name, business_number, primary_business, address, prov_terr);
        String [] error = {"Name","Business number","Primary Business","Address","Province/Territory"};
        boolean errorExists = false; // to check if there is invalid input
        for (int i = 0; i < inputTest.length; i++) {
            if (inputTest[i] < 0) {
                errorExists = true;
                testView.append(error[i]+" information is invalid\n");
            }
        }
        if (errorExists) return;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("contacts");

        // update all fields
        mDatabase.child(personID).child("personalID").setValue(personID);
        mDatabase.child(personID).child("name").setValue(name);
        mDatabase.child(personID).child("business_number").setValue(business_number);
        mDatabase.child(personID).child("primary_business").setValue(primary_business);
        mDatabase.child(personID).child("address").setValue(address);
        mDatabase.child(personID).child("province_territory").setValue(prov_terr);

        finish();
    }
}
