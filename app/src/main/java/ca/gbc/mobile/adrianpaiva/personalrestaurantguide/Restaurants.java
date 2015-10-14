package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;


import android.content.Context;

import java.util.ArrayList;
import java.io.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by adrian on 12/4/2014.
 */
public class Restaurants {

    private static ArrayList<Restaurant> restaurantArrayList;
    private  String[] tag1Type = {"Fast food","Fast Casual","Casual Dining","Family Style", "Fine Dining"};
    private  String[] tag2Price = {"Cheap","Moderate","Expensive"};
    private String[] tag3Style = {"Asian","Indian","American","Italian","Mexican","Canadian","South American","Portuguese","African"};
    private  String[] rating = {"1","2","3","4","5"};

    Context context;
    public Restaurants(Context context)
    {
        this.context = context;

        restaurantArrayList = getAllRestaurants();
        if(restaurantArrayList.isEmpty())
        {
            restaurantArrayList.add(new Restaurant(1,"Pizza Pizza","51 Dockside Dr","416-656-2222","Pizza description goes here","Fast food","cheap", "Canadian","1"));
            restaurantArrayList.add(new Restaurant(2,"Burger King","415 Spadina Rd","416-656-1144","Burger description goes here","Fast food","Cheap", "American","1"));
            restaurantArrayList.add(new Restaurant(3,"California Sandwiches","568 St Clair Ave W","416-656-1111","California sandwiches description goes here","Fast food","Expenisive", "Italian","5"));
            restaurantArrayList.add(new Restaurant(4,"Tapas Bar","396 St Clair Ave W","416-656-2234","Tapas description","Fast Casual","Moderate", "South American","4"));
            writeRestaurants(restaurantArrayList);
        }
    }
    
    public int getNewId()
    {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Restaurant restaurant : getAllRestaurants()) {
            ids.add(restaurant.getId());
        }
        int id = Collections.max(ids) + 1;

        return id;
    }


    public ArrayList<Restaurant> getAllRestaurants() {

            ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

            try
            {
                File file = context.getFileStreamPath("restaurants");

                if(file.exists())
                {

                    FileInputStream fis = context.openFileInput("restaurants");
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    restaurantList = (ArrayList<Restaurant>)ois.readObject();

                    fis.close();
                    ois.close();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return restaurantList;
        }

    public void writeRestaurants(ArrayList<Restaurant> restaurantArrayList) {

        try
        {
            File file = context.getFileStreamPath("restaurants");


            FileOutputStream fos = context.openFileOutput("restaurants",Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(restaurantArrayList);
            fos.close();
            oos.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public  void addRestaurant(Restaurant rest)
    {
        ArrayList<Restaurant> restaurantArrayList = getAllRestaurants();

        restaurantArrayList.add(rest);
        writeRestaurants(restaurantArrayList);
    }
    public  void deleteRestaurant(int id)
    {
        ArrayList<Restaurant> restaurantArrayList = getAllRestaurants();

        for (Restaurant restaurant : restaurantArrayList) {
            if(restaurant.getId() == id)
            {
                restaurantArrayList.remove(restaurant);
                writeRestaurants(restaurantArrayList);
            }
        }
    }
    public  void editRestaurant(int id, String name,String address,String phoneNumber,String description,String tag1,String tag2,String tag3,String rating)
    {
        ArrayList<Restaurant> restaurantArrayList = getAllRestaurants();

        for (Restaurant r : restaurantArrayList) {
            if(r.getId() == id)
            {
                r.setName(name);
                r.setAddress(address);
                r.setPhoneNumber(phoneNumber);
                r.setDescription(description);
                r.setTag1(tag1);
                r.setTag2(tag2);
                r.setTag3(tag3);
                r.setRating(rating);

                writeRestaurants(restaurantArrayList);
            }
        }
    }
    public ArrayList<Restaurant> search(String search)
    {
        ArrayList<Restaurant> temp = new ArrayList<Restaurant>();

        for (Restaurant r : getAllRestaurants()) {
            if (r.getName().contains(search) || r.getTag1().contains(search) || r.getTag2().contains(search) || r.getTag3().contains(search))
            {
                temp.add(r);
            }
        }
        return temp;
    }
    public  String[] getRating() {
        return rating;
    }

    public String[] getTag1Type() {
        return tag1Type;
    }

    public  String[] getTag2Price() {
        return tag2Price;
    }

    public  String[] getTag3Style() {
        return tag3Style;
    }
}
