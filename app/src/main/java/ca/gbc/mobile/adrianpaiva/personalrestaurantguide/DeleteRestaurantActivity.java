package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DeleteRestaurantActivity extends Activity {

    Restaurants r;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_restaurant);

         id= (int)getIntent().getExtras().getInt("id");
        r = new Restaurants(this);

        Button delete = (Button) findViewById(R.id.confirmDeleteButton);

        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                deleteRestaurant();

                Intent intent = new Intent(DeleteRestaurantActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });




    }

    public void deleteRestaurant()
    {
        r.deleteRestaurant(id);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_restaurant, menu);
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
