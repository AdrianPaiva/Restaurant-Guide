package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class EditRestaurantActivity extends Activity {

    EditText name;
    EditText address;
    EditText phoneNumber;
    EditText description;
    Spinner tag1;
    Spinner tag2;
    Spinner tag3;
    Spinner rating;

    String nameValue;
    String addressValue;
    String phoneNumberValue;
    String descriptionValue;
    String tag1Value;
    String tag2Value;
    String tag3Value;
    String ratingValue;

    Button editRestBtn;

    Restaurants rest;

    Restaurant r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        rest = new Restaurants(this);
        Intent i = getIntent();
        r = (Restaurant)i.getSerializableExtra("restaurant");


        name = (EditText)findViewById(R.id.editTextName);
        address = (EditText)findViewById(R.id.editTextAddress);
        phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
        description = (EditText)findViewById(R.id.editTextDescription);

        tag1 = (Spinner) findViewById(R.id.spinnerTag1);
        tag2 = (Spinner) findViewById(R.id.spinnerTag2);
        tag3 = (Spinner) findViewById(R.id.spinnerTag3);

        rating = (Spinner) findViewById(R.id.spinnerRating);

        editRestBtn = (Button) findViewById(R.id.buttonEditRestaurant);



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, rest.getTag1Type());
        tag1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, rest.getTag2Price());
        tag2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, rest.getTag3Style());
        tag3.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, rest.getRating());
        rating.setAdapter(adapter4);


        // set current values
        name.setText(r.getName());
        address.setText(r.getAddress());
        phoneNumber.setText(r.getPhoneNumber());
        description.setText(r.getDescription());

        tag1.setSelection(((ArrayAdapter<String>)tag1.getAdapter()).getPosition(r.getTag1()));
        tag2.setSelection(((ArrayAdapter<String>)tag2.getAdapter()).getPosition(r.getTag2()));
        tag3.setSelection(((ArrayAdapter<String>)tag3.getAdapter()).getPosition(r.getTag3()));
        rating.setSelection(((ArrayAdapter<String>)rating.getAdapter()).getPosition(r.getRating()));

        editRestBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                editRestaurant();

            }
        });




    }

    public void editRestaurant()
    {
        int id = r.getId();
        nameValue = name.getText().toString();
        addressValue = address.getText().toString();
        phoneNumberValue = phoneNumber.getText().toString();
        descriptionValue = description.getText().toString();
        tag1Value = tag1.getSelectedItem().toString();
        tag2Value = tag2.getSelectedItem().toString();
        tag3Value = tag3.getSelectedItem().toString();
        ratingValue = rating.getSelectedItem().toString();

        if (nameValue != "" && addressValue != "" && phoneNumberValue != "" && descriptionValue != "")
        {
            rest.editRestaurant(id, nameValue, addressValue, phoneNumberValue, descriptionValue, tag1Value, tag2Value, tag3Value, ratingValue);

            Restaurant editedRest = new Restaurant(id, nameValue, addressValue, phoneNumberValue, descriptionValue, tag1Value, tag2Value, tag3Value, ratingValue);
            Intent intent = new Intent(EditRestaurantActivity.this, HomeActivity.class);
            intent.putExtra("restaurant", editedRest);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(), " Please enter all fields",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
