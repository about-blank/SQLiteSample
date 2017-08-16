package com.vishal.sqlitesample;

import android.database.Cursor;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vishal.sqlitesample.model.Employee;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText idText, nameText, ageText;
    Button clearBtn, saveBtn, findBtn, updateBtn, deleteBtn, listAllBtn;
    RadioButton traditionalRadioBtn, sqliteopenhelperRadioBtn, cupboardRadioBtn;

    RadioGroup optionsRadioGroup;

    int optionResourceID;

    EmployeeDBHelper dbHelper;
    EmployeeCupboardDBHelper cupboardDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        idText = (EditText) findViewById(R.id.editTextID);
        nameText = (EditText) findViewById(R.id.editTextName);
        ageText = (EditText) findViewById(R.id.editTextAge);

        clearBtn = (Button) findViewById(R.id.button_clear);
        saveBtn = (Button) findViewById(R.id.button_save);
        findBtn = (Button) findViewById(R.id.button_find);
        updateBtn = (Button) findViewById(R.id.button_update);
        deleteBtn = (Button) findViewById(R.id.button_delete);
        listAllBtn = (Button) findViewById(R.id.button_list_all);

        traditionalRadioBtn = (RadioButton) findViewById(R.id.radioButton_traditional);
        sqliteopenhelperRadioBtn = (RadioButton) findViewById(R.id.radioButton_sqliteopenhelper);
        cupboardRadioBtn = (RadioButton) findViewById(R.id.radioButton_cupboard);

        if(traditionalRadioBtn.isChecked())
            optionResourceID = traditionalRadioBtn.getId();

        if(sqliteopenhelperRadioBtn.isChecked())
            optionResourceID = sqliteopenhelperRadioBtn.getId();

        if(cupboardRadioBtn.isChecked())
            optionResourceID = cupboardRadioBtn.getId();

        optionsRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_options);
        optionsRadioGroup.setOnCheckedChangeListener(this);

        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        findBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        listAllBtn.setOnClickListener(this);

        dbHelper = new EmployeeDBHelper(this, "Employee.db", null, 1);

        cupboardDBHelper = new EmployeeCupboardDBHelper(this, "Employee.db", null, 1);

    }

    private void clearAllFields(boolean skipIDField) {

        nameText.setText("");
        if(!skipIDField)
            idText.setText("");
        ageText.setText("");
    }


    private int saveEmployee(int option, String id, String name, String age) {

        int result = -1;



        return  result;
    }

    @Override
    public void onClick(View view) {


        String name = nameText.getText().toString().trim();
        String age = ageText.getText().toString().trim();
        String id = idText.getText().toString().trim();


        switch (view.getId()) {

            case R.id.button_clear:
                clearAllFields(false);
                break;
            case R.id.button_save:
                if(optionResourceID == R.id.radioButton_sqliteopenhelper) {
                    long l = dbHelper.saveEmployee(name, age);
                    if (l > 0) {

                        Toast.makeText(this, "Employee saved !!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Employee could not be saved !!", Toast.LENGTH_LONG).show();
                    }
                }
                else if(optionResourceID == R.id.radioButton_cupboard) {

                    Employee employee = new Employee(null, name, Long.valueOf(age));
                    long l = cupboardDBHelper.saveEmployee(employee);
                    if (l > 0) {

                        Toast.makeText(this, "Employee saved !!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Employee could not be saved !!", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.button_find:
                if(optionResourceID == R.id.radioButton_sqliteopenhelper) {
                    Cursor c = dbHelper.findEmployee(id);

                    if(c != null && c.moveToFirst()) {
                        nameText.setText(c.getString(0));
                        ageText.setText(c.getString(1));
                    }
                    else {
                        clearAllFields(true);
                        Toast.makeText(this, "No Employee found !!", Toast.LENGTH_LONG).show();
                    }
                }
                else if(optionResourceID == R.id.radioButton_cupboard) {

                    Employee employee = new Employee();
                    employee.set_id(Long.valueOf(id));
                    Employee fetchedEmployee = cupboardDBHelper.findEmployee(employee);
                    if(fetchedEmployee != null) {
                        nameText.setText(fetchedEmployee.getEname());
                        ageText.setText(String.valueOf(fetchedEmployee.getAge()));
                    }
                    else {
                        clearAllFields(true);
                        Toast.makeText(this, "No Employee found !!", Toast.LENGTH_LONG).show();
                    }
                }



                break;
            case R.id.button_update:
                if(optionResourceID == R.id.radioButton_sqliteopenhelper) {
                    int i1 = dbHelper.updateEmployee(id, name, age);

                    if(i1 > 0) {

                        Toast.makeText(this, "Employee data updated !!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Employee could not be updated !!", Toast.LENGTH_LONG).show();
                    }
                }
                else if(optionResourceID == R.id.radioButton_cupboard) {

                    Employee employee = new Employee(Long.valueOf(id), name, Long.valueOf(age));
                    long l = cupboardDBHelper.updateEmployee(employee);
                    if(l > 0) {

                        Toast.makeText(this, "Employee data updated !!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Employee could not be updated !!", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.button_delete:
                if(optionResourceID == R.id.radioButton_sqliteopenhelper) {
                    int i2 = dbHelper.deleteEmployee(id);
                    if(i2 > 0) {

                        Toast.makeText(this, "Employee deleted !!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        clearAllFields(true);
                        Toast.makeText(this, "Employee could not be deleted !!", Toast.LENGTH_LONG).show();
                    }
                }
                else if(optionResourceID == R.id.radioButton_cupboard) {

                    Employee employee = new Employee();
                    employee.set_id(Long.valueOf(id));
                    Employee fetchedEmployee = cupboardDBHelper.findEmployee(employee);
                    if(fetchedEmployee == null) {
                        clearAllFields(true);
                        Toast.makeText(this, "No Employee found !!", Toast.LENGTH_LONG).show();
                    }
                    else {

                        if(cupboardDBHelper.deleteEmployee(fetchedEmployee)) {
                            Toast.makeText(this, "Employee deleted !!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            clearAllFields(true);
                            Toast.makeText(this, "Employee could not be deleted !!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;

            case R.id.button_list_all:
                if(optionResourceID == R.id.radioButton_sqliteopenhelper) {
                }
                else if(optionResourceID == R.id.radioButton_cupboard) {

                }
                break;

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        Log.v(getString(R.string.app_name), "Tradition button id : " + R.id.radioButton_traditional);
        Log.v(getString(R.string.app_name), "sqliteopenhelper button id : " + R.id.radioButton_sqliteopenhelper);
        Log.v(getString(R.string.app_name), "cupboard button id : " + R.id.radioButton_cupboard);

        Log.v(getString(R.string.app_name), "Select Resource-id : " + i);

        optionResourceID = i;
    }
}