package com.mohan.gobillionshop.ui.home;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mohan.gobillionshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FloatingActionButton filterButton;
    private RecyclerView recyclerView;
    private productAdapter adapter;
    private ArrayList<productClass> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        filterButton = root.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilter();
            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        recyclerView = root.findViewById(R.id.homeRecycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        downloadJSON("http://gobillion-tgyrb.run-ap-south1.goorm.io/gobillion/getProduct.php");
        itemList = new ArrayList<>();
//        itemList.add(new productClass(1,"shoes","this is a fake shoes","Home Care",
//                "http://google.com",65.25,Float.parseFloat("4.5"),5));
//        itemList.add(new productClass(1,"make up","this is a fake  maekup","Cosmetics",
//                "http://google.com",65.25,Float.parseFloat("3.2"),5));
//        itemList.add(new productClass(1,"garland","this is a fake garland","Apparel",
//                "http://google.com",56.25,Float.parseFloat("4.2"),5));

        adapter = new productAdapter(itemList,getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
    private void openFilter(){
        final String[] items = {"home care", "cosmetics", "apparel"};
        final Boolean[] cheked = {false,false,false};

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle("Filter by Category")
                .setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                                cheked[item] = isChecked;
                            }
                        });

        builder.setPositiveButton("apply",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        ArrayList<String> list = new ArrayList<>();
                        for(int i=0;i<3;i++){
                            if(cheked[i]){
                                list.add(items[i]);
                            }
                        }
                        filterList(list);
//                        Toast.makeText(getContext(),"You clicked yes button",Toast.LENGTH_LONG).show();
                        arg0.dismiss();
                    }
                });

        builder.show();
    }

    private void filterList(ArrayList<String> list){
        ArrayList<productClass> newList = new ArrayList<>();
        int n = itemList.size();
        int m = list.size();
        if(n==0 || m==0){
            return;
        }
        for(int i=0;i<n;i++){
            String s1 = itemList.get(i).getCategory();
            for(int j=0;j<m;j++){
                String s2 = list.get(j);
                if(s1.equals(s2)){
                    newList.add(itemList.get(i));
                    break;
                }
            }
        }
        adapter.updateList(newList);
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                try {
                    Toast.makeText(getActivity(), "successfull", Toast.LENGTH_SHORT).show();
                    loadIntoListView(s);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
//                    Toast.makeText(MainActivity.this, sb.toString().trim(), Toast.LENGTH_SHORT).show();
                    return sb.toString().trim();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] stocks = new String[jsonArray.length()];
        itemList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            productClass pn = new productClass(obj.getInt("id"),obj.getString("name"),obj.getString("description"),obj.getString("category"),
                    obj.getString("image"),obj.getDouble("price"), Float.parseFloat(obj.getString("rating")),obj.getInt("nRAting"));
//            stocks[i] = obj.getString("ID") + " " + obj.getString("Country");
            itemList.add(pn);
        }
        adapter.notifyDataSetChanged();
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
//        listView.setAdapter(arrayAdapter);
    }

}
