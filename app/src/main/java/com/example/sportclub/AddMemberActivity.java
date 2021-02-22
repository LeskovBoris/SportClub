package com.example.sportclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sportclub.data.SportClubContract.MemberEntry;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EDIT_MEMBER_LOADER = 333;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportEditText;
    private Spinner genderSpinner;
    private int gender = 0;
    private ArrayAdapter spinnerAdapter;
    Uri currentMemberUri;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_member:
                saveMember();
                return  true;
            case R.id.delete_member:
                showDeleteMemberDialog();
            return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportEditText.getText().toString().trim();

        //проверка на корректный ввод данных
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Input the first name", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Input the last name", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(sport)) {
            Toast.makeText(this, "Input sport", Toast.LENGTH_LONG).show();
            return;
        } else if (gender == MemberEntry.GENDER_UNKNOWN) {
            Toast.makeText(this, "Choose the gender", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.KEY_FIRSTNAME, firstName);
        contentValues.put(MemberEntry.KEY_LASTNAME, lastName);
        contentValues.put(MemberEntry.KEY_SPORT, sport);
        contentValues.put(MemberEntry.KEY_GENDER, gender);

        if(currentMemberUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);

            if (uri == null) {

                Toast.makeText(this, "Insertion of data of the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanged = getContentResolver().update(currentMemberUri, contentValues, null, null);

            if (rowsChanged == 0) {
                Toast.makeText(this, "Saving of data of the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Member's data updated", Toast.LENGTH_LONG).show();
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        //Запуск intent
        Intent intent = getIntent();

        currentMemberUri = intent.getData();

        if(currentMemberUri == null) {
            setTitle("Add a Member");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the Member");
            getSupportLoaderManager().initLoader(EDIT_MEMBER_LOADER, null, this);
        }

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        sportEditText = findViewById(R.id.sportEditText);
        genderSpinner = findViewById(R.id.genderSpinner);



        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Male")) {
                        gender = MemberEntry.GENDER_MALE;
                    } else if (selectedGender.equals("Female")) {
                        gender = MemberEntry.GENDER_FEMALE;
                    } else {
                        gender = MemberEntry.GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = 0;

            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if(currentMemberUri == null) {
           MenuItem menuItem = menu.findItem(R.id.delete_member);
           menuItem.setVisible(false);

        }
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MemberEntry._ID,
                MemberEntry.KEY_FIRSTNAME,
                MemberEntry.KEY_LASTNAME,
                MemberEntry.KEY_GENDER,
                MemberEntry.KEY_SPORT
        };


        return new CursorLoader(this,
                currentMemberUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if(cursor.moveToFirst()) {
            int firstNameColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_FIRSTNAME);
            int lastNameColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_LASTNAME);
            int genderColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_GENDER);
            int sportColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_SPORT);

            String firstName = cursor.getString(firstNameColumnIndex);
            String lastName = cursor.getString(lastNameColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            String sport = cursor.getString(sportColumnIndex);

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            sportEditText.setText(sport);

            switch (gender) {
                case MemberEntry.GENDER_MALE:
                    genderSpinner.setSelection(1);
                    break;
                case MemberEntry.GENDER_FEMALE:
                    genderSpinner.setSelection(2);
                    break;
                case MemberEntry.GENDER_UNKNOWN:
                    genderSpinner.setSelection(0);
            }



        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void showDeleteMemberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want delete the member?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMember();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteMember() {
        if (currentMemberUri != null) {
            int rowsDeleted = getContentResolver().delete(currentMemberUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, "Deleting of member failed", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Member is deleted", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}