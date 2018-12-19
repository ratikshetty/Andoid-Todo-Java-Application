package ratik.edu.fullerton.cpsc411.application2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class singleRecord extends AppCompatActivity {

    DbHelper myDB;
    EditText etData;
    Button btnUpdate, btnDelete;
    String id,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_record);

        data = getIntent().getExtras().getString("data");
        id = getIntent().getExtras().getString("id");

        myDB=new DbHelper(this);

        etData=(EditText) findViewById(R.id.editTextData);
        btnDelete=(Button) findViewById(R.id.btn_done);
        btnUpdate=(Button) findViewById(R.id.btn_update);
        etData.setText(data);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText= etData.getText().toString();
                if(newText.length()>0){

                    dataUpdate(newText,id);

                }
                else {
                    Toast.makeText(singleRecord.this,"Enter Data",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result=myDB.deleteData(id);

                if(result){
                    Toast.makeText(singleRecord.this,"Task Deleted",Toast.LENGTH_LONG).show();
                    Intent new_intent=new Intent(singleRecord.this,todoAcivity.class);
                    startActivity(new_intent);
                }
                else {
                    Toast.makeText(singleRecord.this,"Couldn't Delete",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void dataUpdate(String data, String id){
        boolean result=myDB.updateData(data,id);
        if(result){
            Toast.makeText(singleRecord.this,"Task Updated",Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(singleRecord.this,"Couldn't Update",Toast.LENGTH_LONG).show();
        }
    }
}
