//

package com.example.attendanceplaystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AddAttendence extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> subject= new ArrayList<>();
    private ArrayList<String> period = new ArrayList<>();
    private ListView listperiod,listsubject;
    private String currentdate,day;
    private int num;
    private TextView datetxt;
    private RadioButton ones,onen,twos,twon,threes,threen,fours,fourn,fives,fiven,sixs,sixn,sevens,sevenn,eights,eightn;
    private RadioGroup r1,r2,r3,r4,r5,r6,r7,r8;
    private String[] id;
    private int temp;
    private Button addsavebtn;
    private final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
    private Button btn,addbackbtn;
    private CheckBox holiday;
    private AdView adView;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendence);


        listsubject=findViewById(R.id.listsubject);
        listperiod=findViewById(R.id.listperiod);
        datetxt = findViewById(R.id.datetxt);
        ones = findViewById(R.id.ones);
        onen=findViewById(R.id.onen);
        twos=findViewById(R.id.twos);
        twon=findViewById(R.id.twon);
        threes=findViewById(R.id.threes);
        threen=findViewById(R.id.threen);
        fours=findViewById(R.id.fours);
        fourn=findViewById(R.id.fourn);
        fives=findViewById(R.id.fives);
        fiven=findViewById(R.id.fiven);
        sixs=findViewById(R.id.sixs);
        sixn=findViewById(R.id.sixn);
        sevens=findViewById(R.id.sevens);
        sevenn=findViewById(R.id.sevenn);
        eights = findViewById(R.id.eights);
        eightn = findViewById(R.id.eightn);

        holiday=findViewById(R.id.checkBox);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);
        r4=findViewById(R.id.r4);
        r5=findViewById(R.id.r5);
        r6=findViewById(R.id.r6);
        r7=findViewById(R.id.r7);
        r8=findViewById(R.id.r8);
        progressBar = findViewById(R.id.progressbar);
        addbackbtn = findViewById(R.id.addbackbtn);

        btn = findViewById(R.id.btn);
        addsavebtn = findViewById(R.id.addsavebtn);
        adView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(AddAttendence.this,"ca-app-pub-4618591388539179~9038242806");



        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final RadioGroup[] radio = new RadioGroup[10];

        databaseReference = FirebaseDatabase.getInstance().getReference();



        pickedIntent();
        datetxt.setText(currentdate);
        calls();

        final ArrayAdapter<String> periodarray = new ArrayAdapter<String>(AddAttendence.this,android.R.layout.simple_list_item_1, period){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                return view;
            }

        };
        listperiod.setAdapter(periodarray);

        final ArrayAdapter<String> subjectarray = new ArrayAdapter<String>(AddAttendence.this,android.R.layout.simple_list_item_1, subject){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                return view;
            }
        };
        listsubject.setAdapter(subjectarray);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("my_users").child(user.getUid()).child(day).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String value = dataSnapshot.getValue(String.class);
                        subject.add(value);
                        subjectarray.notifyDataSetChanged();
                        String key = dataSnapshot.getKey();
                        period.add(key);
                        periodarray.notifyDataSetChanged();


                        String list = listperiod.getAdapter().getCount()+"";
                        num=Integer.parseInt(list+"");
                        id = new String[]{"r1","r2","r3","r4","r5","r6","r7","r8"};


                        for(int i=0; i<num; i++) {
                            temp = getResources().getIdentifier(id[i], "id", AddAttendence.this.getPackageName());
                            radio[i] = (RadioGroup) findViewById(temp);
                            radio[i].setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                aftercalls();
            }
        });




        addsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if(holiday.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("1").setValue("Holiday").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(AddAttendence.this, "Saved, Go back to save for other days!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(AddAttendence.this,"Something went wrong, Please report if it prolongs", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if(ones.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("1").setValue("Present").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAttendence.this, "Saved, Go back to save for other days!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(AddAttendence.this,"Something went wrong, Please report if it prolongs", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
                else if(onen.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("1").setValue("Absent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAttendence.this, "Saved, Go back to save for other days!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(AddAttendence.this,"Something went wrong, Please report if it prolongs", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                if(twos.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("2").setValue("Present");

                }
                else if(twon.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("2").setValue("Absent");

                }
                if(threes.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("3").setValue("Present");
                }
                else if(threen.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("3").setValue("Absent");

                }
                if(fours.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("4").setValue("Present");
                }
                else if(fourn.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("4").setValue("Absent");

                }
                if(fives.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("5").setValue("Present");
                }
                else if(fiven.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("5").setValue("Absent");

                }
                if(sixs.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("6").setValue("Present");
                }
                else if(sixn.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("6").setValue("Absent");

                }
                if(sevens.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("7").setValue("Present");
                }
                else if(sevenn.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("7").setValue("Absent");

                }
                if(eights.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("8").setValue("Present").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAttendence.this, "Saved, Go back to save for other days!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(AddAttendence.this,"Something went wrong, Please report if it prolongs", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else if(eightn.isChecked()){
                    databaseReference.child("my_users").child(user.getUid()).child("Dates").child(currentdate).child("8").setValue("Absent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAttendence.this, "Saved, Go back to save for other days!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(AddAttendence.this,"Something went wrong, Please report if it prolongs", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
                progressBar.setVisibility(View.GONE);

            }
        });


        addbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAttendence.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void pickedIntent(){
        Intent intent = getIntent();
        currentdate = intent.getStringExtra("currentDate");
        day = intent.getStringExtra("currentDay");
    }
    private void calls(){

        btn.setVisibility(View.VISIBLE);
        addsavebtn.setVisibility(View.GONE);
        holiday.setVisibility(View.GONE);

    }
    private void aftercalls(){
        btn.setVisibility(View.INVISIBLE);
        addsavebtn.setVisibility(View.VISIBLE);
        holiday.setVisibility(View.VISIBLE);

    }

    public void rootlayout(View view){

        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
