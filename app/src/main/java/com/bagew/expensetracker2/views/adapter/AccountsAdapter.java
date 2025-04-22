package com.bagew.expensetracker2.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bagew.expensetracker2.R;
import com.bagew.expensetracker2.databinding.RowAccountBinding;
import com.bagew.expensetracker2.views.models.Account;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountsClickListener{
        void onAccountSelected(Account account);
    }

    AccountsClickListener accountsClickListener;
    public AccountsAdapter(Context context, ArrayList<Account> accountArrayList,AccountsClickListener accountsClickListener){
        this.context=context;
        this.accountArrayList=accountArrayList;
        this.accountsClickListener=accountsClickListener;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(v->{
            accountsClickListener.onAccountSelected(account);
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder{

        RowAccountBinding binding;
        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);
        }
    }
}
