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


public class AddRestaurantActivity extends Activity{

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

    Button addRestBtn;

    Restaurants rest = new Restaurants(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

         name = (EditText)findViewById(R.id.editTextName);
         address = (EditText)findViewById(R.id.editTextAddress);
         phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
         description = (EditText)findViewById(R.id.editTextDescription);

         tag1 = (Spinner) findViewById(R.id.spinnerTag1);
         tag2 = (Spinner) findViewById(R.id.spinnerTag2);
         tag3 = (Spinner) findViewById(R.id.spinnerTag3);

         rating = (Spinner) findViewById(R.id.spinnerRating);

        addRestBtn = (Button) findViewById(R.id.buttonAddRestaurant);


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

        addRestBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                addRestaurant();


            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (name.getText().toString().equals("Restaurant Name")) {
                    name.setText("");
                }

            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(address.getText().toString().equals("Address"))
                {
                    address.setText("");
                }

            }
        });

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(phoneNumber.getText().toString().equals("Phone Number"))
                {
                    phoneNumber.setText("");
                }

            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(description.getText().toString().equals("Description"))
                {
                    description.setText("");
                }

            }
        });

    }

    public void addRestaurant()
    {
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
            Restaurant r = new Restaurant(rest.getNewId(),nameValue,addressValue,phoneNumberValue,descriptionValue,tag1Value,tag2Value,tag3Value,ratingValue);
            rest.addRestaurant(r);

            Intent intent = new Intent(AddRestaurantActivity.this, HomeActivity.class);
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
        getMenuInflater().inflate(R.menu.add_restaurant, menu);
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
