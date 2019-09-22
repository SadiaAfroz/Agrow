package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.model.Product;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class addtocart extends AppCompatActivity implements LocationListener {
    Button buttonUp,buttonDown;
    EditText Amount;
    ImageView imgRes;
    String userid;
    private ProgressDialog progressDialog;
    TextView couriershow,totalshow;
    double lati2=0,longi2=0,lati,longi;
    private static int _counter=1 ;
    String _stringVal,stringVal1;
    Product product;
    Button order;
    AutoCompleteTextView actv;
    AutoCompleteTextView actv2;
    float amount;
    float unitprice;
    float totalprice;
    float couriercost;
    LocationManager locationManager;
    String  title,description,unitCost,totalAmount,division, district,upzila,union,availableDate,expiryDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        CheckPermission();
        product = (Product) getIntent().getSerializableExtra("productObject");
        buttonUp=findViewById(R.id.up);
        progressDialog = new ProgressDialog(this);
        buttonDown=findViewById(R.id.down);
        Amount=findViewById(R.id.amount);
        couriershow=findViewById(R.id.shiftcostshow);
        totalshow=findViewById(R.id.costshow);
        imgRes=findViewById(R.id.cartImgeview);
        order=findViewById(R.id.orderbtn);
        userid= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        String base= Api.BASE_IMAGEURL+"uploads/";
        String addidImage=product.getId();
        String url=base+addidImage+".jpg";

        amount=product.getAmount();
        lati=product.getLatitude();
        longi=product.getLongitude();
        totalprice=product.getUnitcost();
        unitprice=product.getUnitcost();




        if(lati2==0&&longi2==0){
            Toast.makeText(addtocart.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
        }
        else{

            couriershow.setText(String.valueOf(couriercost));
            totalshow.setText(String.valueOf(totalprice));

        }

        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgRes.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //imgRequest.setTag(CANCELABLE_REQUEST_TAG);
        RequestHandler.getInstance(addtocart.this).getRequestQueue().add(imgRequest);
        buttonUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("src", "Increasing value...");
                EditText text = (EditText)findViewById(R.id.amount);
                String value = text.getText().toString();
                _counter=Integer.parseInt(value);
                if(_counter<amount) {
                    _counter++;
                    totalprice=totalprice+unitprice;
                }
                _stringVal = String.valueOf(_counter);
                Amount.setText(_stringVal);
                totalshow.setText(String.valueOf(totalprice));
            }
        });


        buttonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("src", "Decreasing value...");
                EditText text = (EditText)findViewById(R.id.amount);
                String value = text.getText().toString();
                _counter=Integer.parseInt(value);
                if(_counter>1)
                {_counter--;totalprice-=unitprice;}
                _stringVal = String.valueOf(_counter);
                Amount.setText(_stringVal);
                totalshow.setText(String.valueOf(totalprice));
            }
        });


        // Amount=R.id.amount;
        String[] divisonsList={"Barisal","Chittagong","Dhaka","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet"};
        ArrayAdapter<String> divisionAdapter=new ArrayAdapter<>(this,android.R.layout.select_dialog_item,divisonsList);
        actv=findViewById(R.id.divison1);
        actv.setThreshold(1);
        actv.setAdapter(divisionAdapter);
        actv.setTextColor(Color.BLUE);


        //district autocomplete
        String[] districtList={"Barisal","Chittagong","Dhaka","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet","Barguna","Bhola","Jhalokati","Patuakhali","Pirojpur","Bandarban","Brahmanbaria","Chandpur","Comilla","Cox's Bazar","Feni","Khagrachhari","Lakshmipur","Noakhali","Rangamati",
                "Faridpur","Gazipur","Gopalganj","Kishoreganj", "Madaripur","Manikganj","Munshiganj","Narayanganj","Narshingdi","Rajbari","Shariatpur","Tangail","Bagerhat","Chuadanga","Jessore","Jhenaidhah","Khushtia","Magura","Meherpur","Narail","Satkhira","Jamalpur","Netrokona","Sherpur",
                "Bogra","Joypurhat","Naogaon","Natore","Chapainawabganj","Pabna","Sirajganj","Dinajpur","Gaibandha","Kurigram","Lalmonirhat","Nilphamari","Panchagarh","Thakugaon","Habiganj","Moulovibaar","Sunamganj"};
        ArrayAdapter<String> districtAdapter=new ArrayAdapter<>(this,android.R.layout.select_dialog_item,districtList);
        actv2=findViewById(R.id.district1);
        actv2.setThreshold(1);
        actv2.setAdapter(districtAdapter);
        actv2.setTextColor(Color.BLUE);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtocarttable();
            }
        });



    }

    public void addtocarttable(){
        progressDialog.setMessage("Placing order...");
        progressDialog.show();
        String urlString=Api.BASE_URL+"add_to_cart";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response+"k");
                        Toast.makeText(getApplicationContext(),"Order placed successfully", Toast.LENGTH_LONG).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("buyerid", userid);
                params.put("adid", product.getId());
                params.put("division", actv.getText().toString());
                params.put("district", actv2.getText().toString());
                params.put("amount", String.valueOf(_counter));
                params.put("cost", String.valueOf(totalprice));
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private float calculateCuriercost() {
        float cost;
        int distance;
        distance=calculateDistanceInKilometer(lati2,longi2,lati,longi);
        cost=(float)((distance/3.5)+40);
        cost=Math.round(cost);
        Toast.makeText(getApplicationContext(), "Distance: "+distance, Toast.LENGTH_LONG).show();
        return cost;
    }
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    @Override
    public void onLocationChanged(Location location) {

        longi2 =location.getLongitude();
        lati2 = location.getLatitude();
        Toast.makeText(getApplicationContext(), lati2+" "+longi2 , Toast.LENGTH_LONG).show();
        couriercost=calculateCuriercost();
        totalprice+=couriercost;
        couriershow.setText(String.valueOf(couriercost));
        totalshow.setText(String.valueOf(totalprice));

    }


    @Override
    public void onResume() {
        super.onResume();
        getLocation();
        couriershow.setText(String.valueOf(couriercost));
        totalshow.setText(String.valueOf(totalprice));
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(addtocart.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
