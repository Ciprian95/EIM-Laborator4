package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.save_btn:
                    saveMethod();
                    break;
                case R.id.cancel_btn:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
                case R.id.show_btn:
                    if (extraLayout.getVisibility() == View.GONE ||
                            extraLayout.getVisibility() == View.INVISIBLE ) {
                        extraLayout.setVisibility(View.VISIBLE);
                        showHideBtn.setText("Hide Additional Details");
                    }
                    else {
                        extraLayout.setVisibility(View.INVISIBLE);
                        showHideBtn.setText("Show Additional Details");
                    }
                    break;
                default: break;
            }
        }
    }
    LinearLayout extraLayout;
    Button showHideBtn, saveBtn, cancelBtn;
    ButtonOnClickListener btnListener = new ButtonOnClickListener();

    private void saveMethod() {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        EditText edit_name = findViewById(R.id.edit_name);
        String name = edit_name.getText().toString();
        EditText edit_phone = findViewById(R.id.edit_phone);
        String phone = edit_phone.getText().toString();
        EditText edit_email = findViewById(R.id.edit_email);
        String email = edit_email.getText().toString();
        EditText edit_address = findViewById(R.id.edit_address);
        String address = edit_address.getText().toString();
        EditText edit_job = findViewById(R.id.edit_job);
        String jobTitle = edit_job.getText().toString();
        EditText edit_company = findViewById(R.id.edit_company);
        String company = edit_company.getText().toString();
        EditText edit_website = findViewById(R.id.edit_website);
        String website = edit_website.getText().toString();
        EditText edit_im = findViewById(R.id.edit_id);
        String im = edit_im.getText().toString();

        if (name != null) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        }
        if (phone != null) {
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        }
        if (email != null) {
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        }
        if (address != null) {
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
        }
        if (jobTitle != null) {
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
        }
        if (company != null) {
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
        }
        ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
        if (website != null) {
            ContentValues websiteRow = new ContentValues();
            websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
            websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
            contactData.add(websiteRow);
        }
        if (im != null) {
            ContentValues imRow = new ContentValues();
            imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
            contactData.add(imRow);
        }
        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
        //startActivity(intent);
        startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        extraLayout = (LinearLayout)findViewById(R.id.extra_layout);
        showHideBtn = (Button)findViewById(R.id.show_btn);
        showHideBtn.setOnClickListener(btnListener);
        saveBtn = (Button)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(btnListener);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(btnListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                EditText phoneEditText = findViewById(R.id.edit_phone);
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }
}
