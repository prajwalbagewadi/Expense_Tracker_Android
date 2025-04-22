package com.bagew.expensetracker2.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bagew.expensetracker2.views.models.Transaction;
import com.bagew.expensetracker2.views.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    Realm realm;
    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<RealmResults<Transaction>> categoriesTransactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    Calendar calendar;
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabase();
    }

    public void getTransactions(Calendar calendar, String type){
        this.calendar=calendar;
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        //realm query returns Realm results
        //select * from transactions
//        RealmResults<Transaction> newtransactions = realm.where(Transaction.class)
//                .equalTo("date",calendar.getTime())
//                .findAll();


        RealmResults<Transaction> newtransactions=null;
        if(Constants.SELECTED_TAB_STATS==Constants.DAILY){
            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",calendar.getTime())
                    .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                    .equalTo("type",type)
                    .findAll();

        }else if(Constants.SELECTED_TAB_STATS==Constants.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type",type)
                    .findAll();
        }
        categoriesTransactions.setValue(newtransactions);
    }
    public void getTransactions(Calendar calendar){
        this.calendar=calendar;

        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        //realm query returns Realm results
        //select * from transactions
//        RealmResults<Transaction> newtransactions = realm.where(Transaction.class)
//                .equalTo("date",calendar.getTime())
//                .findAll();

        double income=0;
        double expense=0;
        double total=0;
        RealmResults<Transaction> newtransactions=null;
        if(Constants.SELECTED_TAB==Constants.DAILY){
            newtransactions = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                .findAll();

            income = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000))).equalTo("type", Constants.Income)
                        .sum("amount")
                .doubleValue();

            expense = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000))).equalTo("type", Constants.Expense)
                .sum("amount")
                .doubleValue();

            total = realm.where(Transaction.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                .sum("amount")
                .doubleValue();


        }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH,0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
            newtransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .findAll();
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type", Constants.Income)
                    .sum("amount")
                    .doubleValue();

            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .equalTo("type", Constants.Expense)
                    .sum("amount")
                    .doubleValue();

            total = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date",startTime)
                    .lessThan("date",endTime)
                    .sum("amount")
                    .doubleValue();
        }
        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);
        transactions.setValue(newtransactions);

    }

    public void addTransactions(Transaction transaction){
        realm.beginTransaction();
        //db code
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();
    }

//    public void addTransactions(){
//        realm.beginTransaction();
//        //db code
//        realm.copyToRealmOrUpdate(new Transaction("Income","online","cash","test note",new Date(),500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction("Expense","Investment","cash","test note2",new Date(),-900,new Date().getTime()));
//        realm.commitTransaction();
//    }
    public void deleteTransaction(Transaction transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }
    public 
    void setupDatabase(){
        realm = Realm.getDefaultInstance();
    }


}
