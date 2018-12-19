package ratik.edu.fullerton.cpsc411.application2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import ratik.edu.fullerton.cpsc411.application2.todoAcivity.*;

public class todoAcivity extends AppCompatActivity {

    DbHelper myDB;
    Button btnAdd;
    EditText etData;
    ListView lv;
    boolean update;
    String ID;

    ArrayList<String> dataList;
    ArrayList<String> idList;

    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_acivity);

        myDB=new DbHelper(this);

        etData= (EditText) findViewById(R.id.etNewItem);
        btnAdd= (Button) findViewById(R.id.btnAddItem);
        lv=(ListView)findViewById(R.id.lvItems);


        populateList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText= etData.getText().toString();
                if(newText.length()>0){

                        AddRecord(newText);


                    etData.setText("");
                }
                else {
                    Toast.makeText(todoAcivity.this,"Enter Data",Toast.LENGTH_LONG).show();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object obj= lv.getItemAtPosition(position);
                ID=idList.get(position);
                String str=dataList.get(position);

                //Toast.makeText(todoAcivity.this,str,Toast.LENGTH_LONG).show();

                //etData.setText(str);
                //btnAdd.setText("Update");
                //update=true;
                Intent new_intent=new Intent(todoAcivity.this,singleRecord.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("data", str);
                mBundle.putString("id",ID);
                new_intent.putExtras(mBundle);
                startActivity(new_intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    public void populateList(){
        dataList=new ArrayList<>();
        idList=new ArrayList<>();

        Cursor data=myDB.getData();


        if(data.getCount()==0){
            Toast.makeText(todoAcivity.this,"No record found",Toast.LENGTH_LONG).show();
        }
        else{
            while(data.moveToNext()){
                dataList.add(data.getString(1));
                idList.add(data.getString(0));

            }
            Collections.reverse(dataList);
            Collections.reverse(idList);
            listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dataList);
            lv.setAdapter(listAdapter);
        }
    }

    public void AddRecord(String data){
        boolean result= myDB.addData(data);
        if(result){
            Toast.makeText(todoAcivity.this,"Task Added",Toast.LENGTH_LONG).show();
            populateList();
        }
        else{
            Toast.makeText(todoAcivity.this,"Error Occured",Toast.LENGTH_LONG).show();
        }
    }


}
