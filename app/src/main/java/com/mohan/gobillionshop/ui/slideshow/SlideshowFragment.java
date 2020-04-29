package com.mohan.gobillionshop.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mohan.gobillionshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private EditText name,desc,price,image;
    private Button submit,appreal,home,cosmetics;
    private Button[] buttons = new Button[3];
    int selected = 1;
    String[] strings = {"apparel","home care","cosmetics"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        name = root.findViewById(R.id.nameField);
        desc = root.findViewById(R.id.descField);
        price = root.findViewById(R.id.priceField);
        image = root.findViewById(R.id.imageField);
        submit = root.findViewById(R.id.addBtn);
        appreal = root.findViewById(R.id.btn_appeal);
        home = root.findViewById(R.id.btn_home);
        cosmetics = root.findViewById(R.id.btn_consmetics);
        buttons[0] = appreal;
        buttons[1] = home;
        buttons[2] = cosmetics;
        for(int i=0;i<3;i++){
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected = finalI;
                    changeColor();
                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        return root;
    }
    private void changeColor(){
        for(int i=0;i<3;i++){
            if(i==selected){
                buttons[i].setBackgroundResource(R.drawable.btn_corner_selected);
            }else{
                buttons[i].setBackgroundResource(R.drawable.button_corner);
            }
        }
    }
    private void upload() {
        String sname = name.getText().toString();
        String sdesc = desc.getText().toString();
        String sprice = price.getText().toString();
        String simage = image.getText().toString();
        String scat = strings[selected];
        if (TextUtils.isEmpty(sname)) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(price.getText().toString())) {
            price.setError("Price is required");
            price.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sdesc)) {
            sdesc = "This product have no description";
        }
        if (TextUtils.isEmpty(simage)) {
            image.setError("Image link is required");
            image.requestFocus();
            return;
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity());
        backgroundWorker.execute("POST", sname, sdesc, sprice, scat, simage);
    }
}
