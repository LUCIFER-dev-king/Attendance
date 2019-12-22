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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TextView txt;
    private EditText es1,es2,es3,es4,es5,es6,es7,es8;
    private TextView e1,e2,e3,e4,e5,e6,e7,e8;;
    FirebaseAuth mAuth;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference databaseReference;
    private String days;

    private ArrayList<String> subject= new ArrayList<>();
    private ArrayList<String> period = new ArrayList<>();

    private ListView periodnum,subjectnum;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

//        e1=view.findViewById(R.id.e1);
//        e2=view.findViewById(R.id.e2);
//        e3=view.findViewById(R.id.e3);
//        e4=view.findViewById(R.id.e4);
//        e5=view.findViewById(R.id.e5);
//        e6=view.findViewById(R.id.e6);
//        e7=view.findViewById(R.id.e7);
//        e8=view.findViewById(R.id.e8);
//        es1=view.findViewById(R.id.es1);
//        es2=view.findViewById(R.id.es2);
//        es3=view.findViewById(R.id.es3);
//        es4=view.findViewById(R.id.es4);
//        es5=view.findViewById(R.id.es5);
//        es6=view.findViewById(R.id.es6);
//        es7=view.findViewById(R.id.es7);
//        es8=view.findViewById(R.id.es8);

        periodnum = view.findViewById(R.id.periodnum);
        subjectnum = view.findViewById(R.id.subjectnum);

        final ArrayAdapter<String> periodarray = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, period){

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
        periodnum.setAdapter(periodarray);

        final ArrayAdapter<String> subjectarray = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, subject){
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
        subjectnum.setAdapter(subjectarray);

        txt = view.findViewById(R.id.txt);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        String name = user.getDisplayName();

        txt.setText(name);



        String [] values = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> list, View view, int position, long id) {


                //Toast.makeText(getActivity(),list.getItemIdAtPosition(position)+"",Toast.LENGTH_LONG).show();
                days = list.getItemIdAtPosition(position)+"";
                if(days.equals("0")){
                    subject.clear();
                    period.clear();
                    days="Monday";
                }
                else if(days.equals("1")){
                    subject.clear();
                    period.clear();

                    days="Tuesday";
                }
                else if(days.equals("2")){
                    subject.clear();
                    period.clear();

                    days="Wednesday";
                }
                else if(days.equals("3")){
                    subject.clear();
                    period.clear();

                    days="Thursday";
                }
                else if(days.equals("4")){
                    subject.clear();
                    period.clear();

                    days="Friday";
                }
                else if(days.equals("5")){
                    subject.clear();
                    period.clear();

                    days="Saturday";
                }
                databaseReference = FirebaseDatabase.getInstance().getReference().child("my_users").child(user.getUid()).child(days);
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String value = dataSnapshot.getValue(String.class);
                        String key = dataSnapshot.getKey();
                        period.add(key);
                        subject.add(value);
                        periodarray.notifyDataSetChanged();
                        subjectarray.notifyDataSetChanged();

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
