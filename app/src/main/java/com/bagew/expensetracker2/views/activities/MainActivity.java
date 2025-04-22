package com.bagew.expensetracker2.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bagew.expensetracker2.views.adapter.TransactionsAdpater;
import com.bagew.expensetracker2.views.fragments.AddTransactionFragment;
import com.bagew.expensetracker2.R;
import com.bagew.expensetracker2.databinding.ActivityMainBinding;
import com.bagew.expensetracker2.views.fragments.StatsFragment;
import com.bagew.expensetracker2.views.fragments.TransactionsFragment;
import com.bagew.expensetracker2.views.models.Transaction;
import com.bagew.expensetracker2.views.utils.Constants;
import com.bagew.expensetracker2.views.utils.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

import com.bagew.expensetracker2.viewmodels.*;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    /*
    *  0 daily
    *  1 monthly
    *  2 calander
    *  3 summary
    *  4 notes
    * */
    int seletedTab =0;
    //Realm realm;
    public MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.materialToolbar);
        binding.bottommenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.transactions){
                    getSupportActionBar().setTitle("Transaction");
                }else if(item.getItemId()==R.id.stats){
                    getSupportActionBar().setTitle("Statics");
                }
                return false;
            }
        });

        //setupDatabase();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Constants.setCategories();
        calendar = Calendar.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new TransactionsFragment());
        transaction.commit();

        binding.bottommenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId()==R.id.transactions){
                    getSupportFragmentManager().popBackStack();
                }else if(item.getItemId()==R.id.stats){
                    transaction.replace(R.id.content,new StatsFragment());
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                return true;
            }
        });

    }
    public void getTransactions(){
        viewModel.getTransactions(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}