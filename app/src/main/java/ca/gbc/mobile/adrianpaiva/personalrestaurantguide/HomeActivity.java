package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;


public class HomeActivity extends Activity {

    ListView listView;
    EditText search;
    Restaurants rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.restaurantListView);
        search = (EditText) findViewById(R.id.searchView);
        Button aboutButton = (Button) findViewById(R.id.aboutButton);
        rest = new Restaurants(this);


        ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, rest.getAllRestaurants());

        listView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                //MainActivity.this.adapt.getFilter().filter(s);
                String searchString = search.getText().toString();

                ArrayAdapter<Restaurant> searchAdapter = new ArrayAdapter<Restaurant>(HomeActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, rest.search(searchString));

                listView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(search.getText().toString().equals("Search"))
                {
                    search.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapter,View v, int position, long id){

                Restaurant r = (Restaurant)listView.getItemAtPosition(position);

                Intent intent = new Intent(HomeActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", r);
                startActivity(intent);


            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
    public void goToAddRestaurant(View view)
    {
         Intent  intent = new Intent(HomeActivity.this, AddRestaurantActivity.class);
         startActivity(intent);
    }
    public void goToAbout(View view)
    {
        Intent  intent = new Intent(HomeActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
