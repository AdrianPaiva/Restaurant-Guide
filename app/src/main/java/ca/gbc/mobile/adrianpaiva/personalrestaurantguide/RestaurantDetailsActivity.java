package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;


public class RestaurantDetailsActivity extends Activity {

    Restaurant r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent i = getIntent();
        r = (Restaurant)i.getSerializableExtra("restaurant");

        TextView name = (TextView) findViewById(R.id.restaurantName);
        TextView address = (TextView) findViewById(R.id.restaurantAddress);
        TextView phoneNumber = (TextView) findViewById(R.id.restaurantPhoneNumber);
        TextView description = (TextView) findViewById(R.id.restaurantDescription);
        TextView tag1 = (TextView) findViewById(R.id.restaurantTag1);
        TextView tag2 = (TextView) findViewById(R.id.restaurantTag2);
        TextView tag3 = (TextView) findViewById(R.id.restaurantTag3);
        TextView rating = (TextView) findViewById(R.id.restaurantRating);

        name.setText(r.getName());
        address.setText(r.getAddress());
        phoneNumber.setText(r.getPhoneNumber());
        description.setText(r.getDescription());
        tag1.setText(r.getTag1());
        tag2.setText(r.getTag2());
        tag3.setText(r.getTag3());
        rating.setText(r.getRating());

        Button delete = (Button) findViewById(R.id.buttonDelete);
        Button edit = (Button) findViewById(R.id.goToEditButton);
        Button share = (Button) findViewById(R.id.shareButton);
        final Button viewMap = (Button) findViewById(R.id.viewmapButton);

        edit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                goToEditActivity(view);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                goToDeleteActivity(view);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                shareRestaurant(view);
            }
        });

        viewMap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                viewMap(view);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant_details, menu);
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
    public void goToDeleteActivity(View view)
    {

        Intent  intent = new Intent(RestaurantDetailsActivity.this, DeleteRestaurantActivity.class);
        intent.putExtra("id",r.getId());
        startActivity(intent);

    }
    public void goToEditActivity(View view)
    {

        Intent  intent = new Intent(RestaurantDetailsActivity.this, EditRestaurantActivity.class);
        intent.putExtra("restaurant",r);
        startActivity(intent);

    }
    public void shareRestaurant(View view)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, r.getName());
        email.putExtra(Intent.EXTRA_TEXT, r.getName() + "\n" + r.getAddress() + "\n"+ r.getDescription() + "\n" + r.getPhoneNumber());

        // need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client"));
    }
    public void viewMap(View view)
    {
        Intent  intent = new Intent(RestaurantDetailsActivity.this, MapActivity.class);
        intent.putExtra("restaurant",r);
        startActivity(intent);
    }
}
