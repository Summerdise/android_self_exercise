package com.example.helloworld;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LookingActivity extends AppCompatActivity {

    Button lookingButton;
    private final int REQUEST_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking);
        lookingButton = findViewById(R.id.looking_btn);
        lookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK) {
            Cursor phoneCursor = null;
            Uri contactUri = data.getData();
            if (contactUri != null) {
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                phoneCursor = getContentResolver().query(contactUri, projection, null, null, null);
                if (phoneCursor != null) {
                    if (phoneCursor.moveToFirst()) {
                        String contactsName = phoneCursor.getString(phoneCursor.getColumnIndex(projection[0]));
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(projection[1]));
                        TextView contactsDetail = findViewById(R.id.phone_detail);
                        contactsDetail.setText(contactsName.concat(phoneNumber));
                    }
                }
            }
            phoneCursor.close();
        }
    }
}