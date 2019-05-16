package com.example.mvvmexample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @NonNull
    private ListView categoriesLst;
    @NonNull
    private ListView couponsLst;

    @NonNull
    private CompositeDisposable compositeDisposable;
    @NonNull
    private CouponViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesLst = findViewById(R.id.category_list);
        couponsLst = findViewById(R.id.coupon_list);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CouponViewModel.class);
        setItemClickListener();

    }


    private void setItemClickListener(){
        categoriesLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String cat = (String)adapterView.getItemAtPosition(position);
                //pass selected category to viewmodel
                viewModel.setSelectedCat(cat);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        unBind();
        super.onPause();
    }

    private void bind() {
        compositeDisposable = new CompositeDisposable();
        //subscribe to categories observable
        //add the observable to disposable
        compositeDisposable.add(viewModel.getCategories()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCategories));

        //subscribe to coupons observable
        //add the observable to disposable
        compositeDisposable.add(viewModel.getCouponsByCat()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCoupons));
    }

    private void unBind() {
        compositeDisposable.clear();
    }

    private void setCategories(ArrayList<String> cats){
        //display received data from viewmodel
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, cats);
        categoriesLst.setAdapter(itemsAdapter);
    }


    private void setCoupons(List<Coupon> coupons){
        //display received data from viewmodel
        ArrayAdapter<Coupon> itemsAdapter =
                new ArrayAdapter<Coupon>(this,
                        android.R.layout.simple_list_item_1, coupons);
        couponsLst.setAdapter(itemsAdapter);
    }
    
}
