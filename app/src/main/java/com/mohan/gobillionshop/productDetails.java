package com.mohan.gobillionshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mohan.gobillionshop.R;
import com.mohan.gobillionshop.ui.home.productClass;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class productDetails extends AppCompatActivity {

    SliderView sliderView;
    private SliderAdapter adapter;
    private ImageButton share;
    private TextView desc,name,cat,price;
    private DatabaseHelper db;
    private FrameLayout addTowish;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productDetails.this.setTitle("Product Details");
        ActionBar ab = getSupportActionBar();
        desc = findViewById(R.id.productDescription);
        name=  findViewById(R.id.productName);
        cat = findViewById(R.id.productCat);
        price = findViewById(R.id.productPrice);
        db = new DatabaseHelper(this);
        share = findViewById(R.id.shareOnWhatsapp);
        btn = findViewById(R.id.add_to_wishlist_button);
        addTowish = findViewById(R.id.addToWishList);
        Glide.with(this)
                .load(R.drawable.whatsapp)
                .fitCenter()
                .centerCrop()
                .into(share);

        final String id = getIntent().getStringExtra("id");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWhatsApp(id);
            }
        });


        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            SingleBackgroundWorker singleBackgroundWorker = new SingleBackgroundWorker(this);
            singleBackgroundWorker.execute("POST",id);
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
//        sliderView.setIndicatorSelectedColor(Color.WHITE);
//        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
        renewItems();

        addTowish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                db.insertData(name.getText().toString(),desc.getText().toString(),price.getText().toString(),"fake","4.2",4);
                Toast.makeText(productDetails.this, "Added To WIshList SccessFully", Toast.LENGTH_SHORT).show();
//                updateData();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insertData(name.getText().toString(),desc.getText().toString(),price.getText().toString(),"fake","4.2",4);
                Toast.makeText(productDetails.this, "Added To WIshList SccessFully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://images.unsplash.com/photo-1571513721963-d855fd8df4c2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
            } else {
                sliderItem.setImageUrl("https://images.unsplash.com/photo-1499939667766-4afceb292d05?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void onClickWhatsApp(String id) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "http://www.gobillionproduct.com/"+id;

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public class SingleBackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        SingleBackgroundWorker (Context ctx) {
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "https://gobillion-tgyrb.run-ap-south1.goorm.io/gobillion/single.php";
            if(type.equals("POST")) {
                try {
                    String id = params[1];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
//                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    String data = URLEncoder.encode("id", "UTF-8")
                            + "=" + URLEncoder.encode(id, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line="";
                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Responce");
        }

        @Override
        protected void onPostExecute(String result) {
//        alertDialog.setMessage(result);
//        alertDialog.show();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
                String[] stocks = new String[jsonArray.length()];
                JSONObject obj = jsonArray.getJSONObject(0);
                name.setText(obj.getString("name"));
                desc.setText(obj.getString("description"));
                price.setText("$"+obj.getString("price"));
                cat.setText(obj.getString("category"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



//    private void updateData(){
//        Cursor res = db.getAllData();
//        if(res.getCount()==0){
//            Toast.makeText(this, "Empty database", Toast.LENGTH_SHORT).show();
//        }else{
//            itemList.clear();
//            while(res.moveToNext()){
//                itemList.add(new userClass(res.getString(1),res.getString(2),res.getString(3)));
//            }
//            adapter.notifyDataSetChanged();
//        }
//    }
}


