package com.example.agrow.controller;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addproduct extends AppCompatActivity implements LocationListener {
    Spinner spinner;
    double lati=0,longi=0;
    String userid;
    AutoCompleteTextView actv;
    AutoCompleteTextView actv2;
    private ProgressDialog progressDialog;
    String  title,description,unitCost,totalAmount,division, district,upzila,union,availableDate,expiryDate;
    Button choosebtn,expirybutton,availblebutton,addproductbutton;
    TextView availabletext,expirytext;
    //float unitCost,totalAmount;
    EditText titleEdit,descriptionEdit,unitcostEdit,amountEdit,unionEdit,upzilaEdit;
    ImageView imgview;
    private final int IMG_REQUEST=1;
    Bitmap bitmap;
    DatePickerDialog dpd;
    Calendar c;
    LocationManager locationManager;

    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> catidlist=new ArrayList<>();
    String selectedcatId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        CheckPermission();
        progressDialog = new ProgressDialog(this);
        // Edittext findby id
        titleEdit=findViewById(R.id.title);
        descriptionEdit=findViewById(R.id.description);
        unitcostEdit=findViewById(R.id.unitcost);
        amountEdit=findViewById(R.id.amount);
        unionEdit=findViewById(R.id.union);
        upzilaEdit=findViewById(R.id.upzila);
        userid= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        spinner = (Spinner) findViewById(R.id.categorylist);
        getCatogories();
        choosebtn=(Button)findViewById(R.id.chosebtn);
        imgview=(ImageView)findViewById(R.id.imageview);

        // date taking form Datedialog
        availabletext=(TextView) findViewById(R.id.availabletext);
        availblebutton=(Button) findViewById(R.id.availablebutton);
        expirytext=(TextView) findViewById(R.id.expirytext);
        expirybutton=(Button) findViewById(R.id.expirybutton);
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                view.setMinDate(System.currentTimeMillis() - 1000);
                availableDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                availabletext.setText(availableDate);
            }
        };
        availblebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDate = new DatePickerDialog(addproduct.this, date2, 2016, 2, 24);
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                view.setMinDate(System.currentTimeMillis() - 1000);
                expiryDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                expirytext.setText(expiryDate);
            }
        };
        expirybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog mDate = new DatePickerDialog(addproduct.this, date, 2016, 2, 24);
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });

        //division autocomplete
        String[] divisonsList={"Barisal","Chittagong","Dhaka","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet"};
        ArrayAdapter<String> divisionAdapter=new ArrayAdapter<>(addproduct.this,android.R.layout.select_dialog_item,divisonsList);
        actv=findViewById(R.id.divison);
        actv.setThreshold(1);
        actv.setAdapter(divisionAdapter);
        actv.setTextColor(Color.BLUE);


        //district autocomplete
        String[] districtList={"Barisal","Chittagong","Dhaka","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet","Barguna","Bhola","Jhalokati","Patuakhali","Pirojpur","Bandarban","Brahmanbaria","Chandpur","Comilla","Cox's Bazar","Feni","Khagrachhari","Lakshmipur","Noakhali","Rangamati",
                "Faridpur","Gazipur","Gopalganj","Kishoreganj", "Madaripur","Manikganj","Munshiganj","Narayanganj","Narshingdi","Rajbari","Shariatpur","Tangail","Bagerhat","Chuadanga","Jessore","Jhenaidhah","Khushtia","Magura","Meherpur","Narail","Satkhira","Jamalpur","Netrokona","Sherpur",
                "Bogra","Joypurhat","Naogaon","Natore","Chapainawabganj","Pabna","Sirajganj","Dinajpur","Gaibandha","Kurigram","Lalmonirhat","Nilphamari","Panchagarh","Thakugaon","Habiganj","Moulovibaar","Sunamganj"};
        ArrayAdapter<String> districtAdapter=new ArrayAdapter<>(addproduct.this,android.R.layout.select_dialog_item,districtList);
        actv2=findViewById(R.id.district);
        actv2.setThreshold(1);
        actv2.setAdapter(districtAdapter);
        actv2.setTextColor(Color.BLUE);

        //image picking
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();

            }
        });

        //add product button

        addproductbutton=findViewById(R.id.addproductbutton);
        addproductbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductDataCollect();
            }

        });


    }
    private void addProductDataCollect(){

        title=titleEdit.getText().toString();
        description=descriptionEdit.getText().toString();
        unitCost=unitcostEdit.getText().toString();
        totalAmount=amountEdit.getText().toString();
        union=unionEdit.getText().toString();
        upzila=upzilaEdit.getText().toString();

        division=actv.getText().toString();
        district=actv2.getText().toString();
        if(longi!=0 && lati!=0) {

            addProduct();
        }
        else Toast.makeText(getApplicationContext(), "Please enable GPS", Toast.LENGTH_LONG).show();
    }


    private void addProduct()
    {
        progressDialog.setMessage("Adding product...");
        progressDialog.show();
        String urlString= Api.BASE_URL+"add_insert";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response+"k");
                        Toast.makeText(getApplicationContext(),"Product added successfully", Toast.LENGTH_LONG).show();



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
                params.put("sellerid", userid);
                params.put("title", title);
                params.put("description", description);
                params.put("division", division);
                params.put("district", district);
                params.put("upazila", upzila);
                params.put("unionloc", union);
                params.put("available_date", availableDate);
                params.put("expiry_date", expiryDate);
                params.put("catid", selectedcatId);
                params.put("unit_cost", unitCost);
                params.put("amount", totalAmount);
                params.put("latitude",String.valueOf(lati));
                params.put("longitude",String.valueOf(longi));
                params.put("image", imageToString(bitmap));
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    private void selectimage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void getCatogories(){
        String urlString=Api.BASE_URL+"category_list";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                list.add(jsonObject.getString("catname"));
                                catidlist.add(jsonObject.getString("catid"));
                                System.out.println(list.get(i));
                            }

                            ArrayAdapter<String> adapter=new ArrayAdapter<>(addproduct.this, android.R.layout.simple_spinner_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    //Setting the values to textviews for a selected item

                                     selectedcatId=catidlist.get(position).toString();
                                    String selectedItem=parent.getItemAtPosition(position).toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onLocationChanged(Location location) {

        longi =location.getLongitude();
        lati = location.getLatitude();


    }


    @Override
    public void onResume() {
        super.onResume();
        getLocation();
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
        Toast.makeText(addproduct.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
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
