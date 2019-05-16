package com.example.mvvmexample;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CouponViewModel extends ViewModel {

    private CouponModel couponModel;
    private final BehaviorSubject<String> selectedCategory = BehaviorSubject.create();

    public CouponViewModel(){
        couponModel = new CouponModel();
    }

    //categories observable
    public Observable<ArrayList<String>> getCategories(){
        return couponModel.getCategories();
    }


    //coupons observable emits coupons when category is selected
    public Observable<List<Coupon>> getCouponsByCat(){
        return selectedCategory
                .observeOn(Schedulers.computation())
                .flatMap(couponModel::getCouponsByCat);
    }

    //pass selected category to model
    public void setSelectedCat(String cat){
        selectedCategory.onNext(cat);
    }

}
