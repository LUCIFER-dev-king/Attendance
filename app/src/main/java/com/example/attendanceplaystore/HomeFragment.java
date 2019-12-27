package com.example.attendanceplaystore;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeFragment extends Fragment {

    private TextView txt;
    private EditText es1,es2,es3,es4,es5,es6,es7,es8;
    private TextView e1,e2,e3,e4,e5,e6,e7,e8;
    private FirebaseAuth mAuth;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference databaseReference;
    private String days;

    private ArrayList<String> subjectlist= new ArrayList<>();
    private ArrayList<String> periodlist = new ArrayList<>();

    private ListView totperiods,totsubjects;
    private AdView adView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        MobileAds.initialize(getActivity(),"ca-app-pub-4618591388539179~9038242806");

        totperiods = (ListView) view.findViewById(R.id.totperiods);
        totsubjects = (ListView) view.findViewById(R.id.totsubjects);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adView = (AdView) view.findViewById(R.id.adView);
        progressBar= (ProgressBar) view.findViewById(R.id.progressbar);




        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        final ArrayAdapter<String> periodarrays = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, periodlist){

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
        totperiods.setAdapter(periodarrays);

        final ArrayAdapter<String> subjectarrays = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, subjectlist){
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
        totsubjects.setAdapter(subjectarrays);

        txt = view.findViewById(R.id.txt);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        txt.setText(name);



        final String [] values = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> list, View view, int position, long id) {

                progressBar.setVisibility(View.VISIBLE);

                days = list.getItemIdAtPosition(position)+"";
                if(days.equals("0")){
                    subjectlist.clear();
                    periodlist.clear();
                    days="Monday";
                }
                else if(days.equals("1")){
                    subjectlist.clear();
                    periodlist.clear();

                    days="Tuesday";
                }
                else if(days.equals("2")){
                    subjectlist.clear();
                    periodlist.clear();

                    days="Wednesday";
                }
                else if(days.equals("3")){
                    subjectlist.clear();
                    periodlist.clear();

                    days="Thursday";
                }
                else if(days.equals("4")){
                    subjectlist.clear();
                    periodlist.clear();

                    days="Friday";
                }
                else if(days.equals("5")){
                    subjectlist.clear();
                    periodlist.clear();
                    days="Saturday";
                }

                databaseReference.child("my_users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String check = dataSnapshot.getKey();
                        if(dataSnapshot.hasChild(days)){

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),"No subjects are found, Please add some subject for these days",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference.child("my_users").child(user.getUid()).child(days).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();
                        progressBar.setVisibility(View.GONE);
                        subjectlist.add(value);
                        subjectarrays.notifyDataSetChanged();

                        periodlist.add(key);
                        periodarrays.notifyDataSetChanged();

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


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        return view;

    }


}
