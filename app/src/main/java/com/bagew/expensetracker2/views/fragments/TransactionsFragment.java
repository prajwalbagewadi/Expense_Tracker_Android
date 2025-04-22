package com.bagew.expensetracker2.views.fragments;

import static com.bagew.expensetracker2.views.utils.Constants.SELECTED_STATS_TYPE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bagew.expensetracker2.R;
import com.bagew.expensetracker2.databinding.FragmentTransactionsBinding;
import com.bagew.expensetracker2.viewmodels.MainViewModel;
import com.bagew.expensetracker2.views.activities.MainActivity;
import com.bagew.expensetracker2.views.adapter.TransactionsAdpater;
import com.bagew.expensetracker2.views.models.Transaction;
import com.bagew.expensetracker2.views.utils.Constants;
import com.bagew.expensetracker2.views.utils.Helper;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionsFragment extends Fragment {



    public TransactionsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTransactionsBinding binding;
    Calendar calendar;
    /*
     *  0 daily
     *  1 monthly
     *  2 calander
     *  3 summary
     *  4 notes
     * */

    //Realm realm;
    public MainViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        calendar = Calendar.getInstance();
        updateDate();

        binding.nextDate.setOnClickListener(v->{
            if(Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE,1);
            }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH,1);
            }
            updateDate();
        });

        binding.previousDate.setOnClickListener(v->{
            if(Constants.SELECTED_TAB==Constants.DAILY){
                calendar.add(Calendar.DATE,-1);
            }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
                calendar.add(Calendar.MONTH,-1);
            }
            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(v->{
            new AddTransactionFragment().show(getParentFragmentManager(),null);

        });

//        ArrayList<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction("Income","online","cash","test note",new Date(),500,1));
//        transactions.add(new Transaction("Expense","Investment","cash","test note2",new Date(),-900,2));

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdpater transactionsAdpater = new TransactionsAdpater(getActivity(), transactions);
                binding.transactionsList.setAdapter(transactionsAdpater);
                if(transactions.size() > 0) {
                    binding.emptyState.setVisibility(View.GONE);
                }else{
                    binding.emptyState.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomelbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenselbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totallbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.getTransactions(calendar);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    updateDate();
                }else if(tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB = 0;
                    updateDate();
                } else if(tab.getText().equals("Notes")) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return binding.getRoot();
    }
    void updateDate(){
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MMMM,yyyy");
        if(Constants.SELECTED_TAB==Constants.DAILY){
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getTransactions(calendar);
    }
}