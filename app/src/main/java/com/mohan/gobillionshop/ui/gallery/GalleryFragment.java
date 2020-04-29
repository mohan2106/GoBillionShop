package com.mohan.gobillionshop.ui.gallery;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mohan.gobillionshop.MainActivity;
import com.mohan.gobillionshop.R;
import com.mohan.gobillionshop.ui.home.productAdapter;
import com.mohan.gobillionshop.ui.home.productClass;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryViewModel galleryViewModel;
    private wishlistAdapter adapter;
    private ArrayList<wishlistItem> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.wishlistRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        itemList.add(new wishlistItem(1,"shoes","this is a fake shoes","Home care",
                "http://google.com",65.25,Float.parseFloat("4.5"),5,3));
        itemList.add(new wishlistItem(1,"make up","this is a fake  maekup","Cosmetics",
                "http://google.com",65.25,Float.parseFloat("3.2"),5,2));
        itemList.add(new wishlistItem(1,"garland","this is a fake garland","Apparel",
                "http://google.com",56.25,Float.parseFloat("4.2"),5,1));

        adapter = new wishlistAdapter(itemList,getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}
