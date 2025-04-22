package com.bagew.expensetracker2.views.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bagew.expensetracker2.R;
import com.bagew.expensetracker2.databinding.RowTransactionBinding;
import com.bagew.expensetracker2.views.activities.MainActivity;
import com.bagew.expensetracker2.views.models.Transaction;
import com.bagew.expensetracker2.views.utils.Constants;
import com.bagew.expensetracker2.views.utils.Helper;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionsAdpater extends RecyclerView.Adapter<TransactionsAdpater.TransactionViewHolder>{

    Context context;
    RealmResults<Transaction> transactions;
    public  TransactionsAdpater(Context context, RealmResults<Transaction> transactions){
        this.context=context;
        this.transactions=transactions;

    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountlbl.setText(transaction.getCategory());
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getAccount());

        if(transaction.getType().equals(Constants.Income)){ //I or i
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.green));
        }else if(transaction.getType().equals(Constants.Expense)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.red));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog Note = new AlertDialog.Builder(context).create();
                Note.setTitle("Note");
                Note.setMessage(transaction.getNote());
                Note.setButton(DialogInterface.BUTTON_NEGATIVE,"Close",((dialogInterface, i) -> {
                    Note.dismiss();
                }));
                Note.show();
                //return false;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Confirm to Delete Transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE,"yes",((dialogInterface, i) -> {
                    ((MainActivity)context).viewModel.deleteTransaction(transaction);
                }));
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No",((dialogInterface, i) -> {
                    deleteDialog.dismiss();
                }));
                deleteDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        RowTransactionBinding binding;
        public TransactionViewHolder(@NonNull View itemView){
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
