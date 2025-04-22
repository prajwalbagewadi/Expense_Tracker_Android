package com.bagew.expensetracker2.views.fragments;

import static com.bagew.expensetracker2.views.utils.Constants.categories;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.bagew.expensetracker2.R;
import com.bagew.expensetracker2.databinding.FragmentAddTransactionBinding;
import com.bagew.expensetracker2.databinding.ListDialogBinding;
import com.bagew.expensetracker2.views.activities.MainActivity;
import com.bagew.expensetracker2.views.adapter.AccountsAdapter;
import com.bagew.expensetracker2.views.adapter.CategoryAdapter;
import com.bagew.expensetracker2.views.models.Account;
import com.bagew.expensetracker2.views.models.Category;
import com.bagew.expensetracker2.views.models.Transaction;
import com.bagew.expensetracker2.views.utils.Constants;
import com.bagew.expensetracker2.views.utils.Helper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;


public class AddTransactionFragment extends BottomSheetDialogFragment {

    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentAddTransactionBinding binding;
    Transaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater);
        transaction = new Transaction();
        //return inflater.inflate(R.layout.fragment_add_transaction, container, false);
        binding.income.setOnClickListener(v->{
            binding.income.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expense.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expense.setTextColor(getContext().getColor(R.color.black));
            binding.income.setTextColor(getContext().getColor(R.color.white));
            transaction.setType(Constants.Income);
        });
        binding.expense.setOnClickListener(v->{
            binding.income.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expense.setTextColor(getContext().getColor(R.color.white));
            binding.income.setTextColor(getContext().getColor(R.color.black));
            transaction.setType(Constants.Expense);
        });
        binding.date.setOnClickListener(v->{
            DatePickerDialog date = new DatePickerDialog(getContext());
            date.setOnDateSetListener(((datePicker, i, i1, i2) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH,datePicker.getMonth());
                calendar.set(Calendar.YEAR,datePicker.getYear());
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MMMM,yyyy");
                String dateToShow = Helper.formatDate(calendar.getTime());
                binding.date.setText(dateToShow);
                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime()); //double getTime give in long format
            }));
            date.show();
        });
//        String[] categories = {"Food", "Transport", "Shopping", "Utilities", "Entertainment", "Other"};
//
//        binding.category.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Select Category");
//            builder.setItems(categories, (dialog, which) -> {
//                binding.category.setText(categories[which]);
//            });
//            builder.show();
//        });
        binding.category.setOnClickListener(v->{
            ListDialogBinding dialogBinding =  ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());



            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCatgoryName());
                    transaction.setCategory(category.getCatgoryName());
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);
            categoryDialog.show();
        });

        binding.acc.setOnClickListener(v->{
            ListDialogBinding dialogBinding =  ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Paytm"));
            accounts.add(new Account(0,"Googlepay"));
            accounts.add(new Account(0,"Card"));
            accounts.add(new Account(0,"Others"));

            AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.acc.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountsDialog.dismiss();
                }
            });
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);

            accountsDialog.show();
        });
        binding.savetransaction.setOnClickListener(v->{
            double amount = Double.parseDouble(binding.amt.getText().toString());
            String note = binding.note.getText().toString();
            if(transaction.getType().equals(Constants.Expense)){
                transaction.setAmount(amount*-1);
            }else {
                transaction.setAmount(amount);
            }
            transaction.setNote(note);
            ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
            ((MainActivity)getActivity()).getTransactions();
            dismiss();
        });
        return binding.getRoot();
    }
}