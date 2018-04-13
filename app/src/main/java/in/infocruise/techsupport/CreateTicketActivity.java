package in.infocruise.techsupport;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.infocruise.techsupport.Model.Dealers;
import in.infocruise.techsupport.Model.Manufacturer;
import in.infocruise.techsupport.Model.Status_Tag;
import in.infocruise.techsupport.Model.Tickets;
import in.infocruise.techsupport.helper.MySingleton;

public class CreateTicketActivity extends AppCompatActivity {
    // Validation for picking up Contact with Intent
    private static final int RESULT_PICK_CONTACT = 1;
    // getting the user id from first screen that is login screen from intent extras
    private String user_id;
    private LinearLayout mNameFromContact;
    private LinearLayout mNumberFromContact;
    // getting selected dealership id in spinner
    private int getDealershipId;
    // getting selected stats tag id in spinner of StatusTag
    private int getStatusTagId;
    // For showing logcat infromation
    private static final String TAG = CreateTicketActivity.class.getSimpleName();

    //Declaration for picking up Manufacturer from spinner widget
    Spinner mSpinnerManufacturer;
    // Declaration for picking up Dealership from spinner widget
    Spinner mSpinnerDealerships;

    // Declaration for picking up Status of Tag from spinner widget
    Spinner mSpinnerStatusTag;
    // Delaratioon of User Input like Name , Number and Ticket Discription from the user
    EditText mTicketDesc, mTicketClientName, mTicketClientNumber;
    // Declaration of swith widget to enable or disable picking up contact from contact of device or not.
    Switch mContactPick;
    // Declaration of Name and Number TextView where Contact Name, Number picked up from Contact of device is shown
    TextView mContactNumber, mContactName;

    // Declaration of Button for Saving Ticket and Picking Contact Info from Contact of the Device
    Button mSaveTicket, mPickContact;
    //String urlDealerships = "http://192.168.1.240:84/service.asmx/dealershipMaster?name=";
    // Default String of Api for getting and sending data to our own server
    String urlManufacturer = "http://192.168.1.240:84/service.asmx/";


    ArrayList<Manufacturer> ManufacturerMaster;
    ArrayList<Dealers> DealershipMaster;
    ArrayList<Status_Tag> TagMaster;
    ArrayList<Tickets> TicketMaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        user_id = extras.getString("userId");

        ManufacturerMaster = new ArrayList<>();
        DealershipMaster = new ArrayList<>();

        TagMaster = new ArrayList<>();
        TicketMaster = new ArrayList<>();
        mSpinnerManufacturer = findViewById(R.id.manufacturers_select);
        mSpinnerDealerships = findViewById(R.id.dealerships_select);
        mSpinnerStatusTag = findViewById(R.id.status_tag);

        mNameFromContact = findViewById(R.id.nameFromContact);
        mNumberFromContact = findViewById(R.id.numberFromContact);

        mContactNumber = findViewById(R.id.contact_number);
        mTicketDesc = findViewById(R.id.TicketDesc);
        mSaveTicket = findViewById(R.id.saveTicket);
        mPickContact = findViewById(R.id.pick_contact_bt);
        mContactPick = findViewById(R.id.switch_contact_pick);
        mContactName = findViewById(R.id.contact_name);
        mTicketClientName = findViewById(R.id.edit_name_input);
        mTicketClientNumber = findViewById(R.id.edit_number_input);
        mTicketClientName.setVisibility(View.GONE);
        mTicketClientNumber.setVisibility(View.GONE);

        loadSpinnerDataForManufacturer(urlManufacturer);

        mContactPick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPickContact.setEnabled(false);
                    mContactName.setVisibility(View.GONE);
                    mContactNumber.setVisibility(View.GONE);
                    mNameFromContact.setVisibility(View.GONE);
                    mNumberFromContact.setVisibility(View.GONE);
                    mTicketClientName.setVisibility(View.VISIBLE);
                    mTicketClientNumber.setVisibility(View.VISIBLE);
                } else{
                    mPickContact.setEnabled(true);
                    mContactName.setVisibility(View.VISIBLE);
                    mContactNumber.setVisibility(View.VISIBLE);
                    mTicketClientName.setVisibility(View.GONE);
                    mTicketClientNumber.setVisibility(View.GONE);
                    mNameFromContact.setVisibility(View.VISIBLE);
                    mNumberFromContact.setVisibility(View.VISIBLE);

                }

            }
        });

        mPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
            }
        });

        mSpinnerManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Manufacturer manufacturer = (Manufacturer) parent.getSelectedItem();

                //int item = mSpinnerManufacturer.getSelectedItemPosition();
                //String itemSelected = ManufacturerMaster.get(item);

                Log.d(TAG, "Manufacturer Id: " + manufacturer.getId() + ", Manufacturer Name: " + manufacturer.getManufacturer_name());

                // loadSpinnerDataForDealerships(item);


                getItemSelected(manufacturer.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mSpinnerDealerships.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // int item = mSpinnerDealerships.getSelectedItemPosition();
                Dealers dealerships = (Dealers) parent.getSelectedItem();
                getDealershipId = dealerships.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerStatusTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status_Tag statusTag = (Status_Tag) parent.getSelectedItem();
                getStatusTagId = statusTag.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadDataSpinnerForStatuTag();

        mContactNumber.callOnClick();

        mSaveTicket.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                saveTicket();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("userId", user_id);
                startActivity(intent);
            }
        });


    }

    private void getItemSelected(int itemSelected) {
        loadSpinnerDataForDealerships(String.valueOf(itemSelected));

    }

    private void loadSpinnerDataForManufacturer(String url) {


        url += "manufacturerMaster?";
        Log.d(TAG, "url now is: " + url);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response.get(i);
                                String manufacturerId = person.getString("id");
                                String manufacturerName = person.getString("name");

                                //ManufacturerMaster.add(manufacturerName);
                                ManufacturerMaster.add(new Manufacturer(Integer.valueOf(manufacturerId), manufacturerName));

                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CreateTicketActivity.this, android.R.layout.simple_spinner_dropdown_item, ManufacturerMaster);
                            mSpinnerManufacturer.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Get a RequestQueue
        //RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
        // getRequestQueue();

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void loadSpinnerDataForDealerships(String item) {
        Log.d(TAG, "item now is : " + item);
        DealershipMaster.clear();
        String url = "http://202.83.19.113:84/service.asmx/dealershipMaster?id=" + item;

        Log.d(TAG, "starting to get json data: " + url);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject dealer = (JSONObject) response.get(i);
                                String dealersipId = dealer.getString("id");
                                String dealershipCode = dealer.getString("code");
                                String dealershipName = dealer.getString("name");
                                String manufacturerId = dealer.getString("manufacturer_id");

                                DealershipMaster.add(new Dealers(Integer.valueOf(dealersipId) , dealershipCode, dealershipName,Integer.valueOf(manufacturerId) ));

                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CreateTicketActivity.this, android.R.layout.simple_spinner_dropdown_item, DealershipMaster);

                            mSpinnerDealerships.setAdapter(arrayAdapter);

                            // mSpinnerDealerships.setAdapter(new ArrayAdapter<>(CreateTicketActivity.this, android.R.layout.simple_spinner_dropdown_item, DealershipMaster));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Get a RequestQueue
        //RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
        // getRequestQueue();

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void loadDataSpinnerForStatuTag() {
        String url = "http://202.83.19.113:84/service.asmx/tagMaster?";
        Log.d(TAG, "URL: " + url);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject statusTag = (JSONObject) response.get(i);
                                String statusId = statusTag.getString("id");
                                String statusName = statusTag.getString("tag_name");

                                TagMaster.add(new Status_Tag(Integer.valueOf(statusId) , statusName));
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CreateTicketActivity.this, android.R.layout.simple_spinner_dropdown_item, TagMaster);

                            mSpinnerStatusTag.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Get a RequestQueue
        // RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
        //  getRequestQueue();


        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.d(TAG, "ZZZ number : " + number + " , name : " + name);
                String correctNumber = number.replaceAll("[()\\s-]+", "");
                mContactName.setText(name);
                mContactNumber.setText(correctNumber);

            }
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor;
        try {
            String phoneNo;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            assert uri != null;
            cursor = getContentResolver().query(uri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            phoneNo = cursor.getString(phoneIndex);
            // Set the value to the textviews

            mContactNumber.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTicket() {
        // Store values at the time of the login attempt.
        String name_input = mTicketClientName.getText().toString();
        String number_input = mTicketClientNumber.getText().toString();
        String contact_number;
        String ticket_input = mTicketDesc.getText().toString().replaceAll("[\\s]", "/s");


        if (mContactPick.isChecked()) {
            contact_number = number_input.replaceAll("[-()\\s]+", "").trim();
        } else {
            contact_number = mContactNumber.getText().toString();
        }
        Log.d(TAG, "contact number : " + contact_number);
        String url = "http://202.83.19.113:84/service.asmx/SaveTickets?user_id="
                + user_id + "&contact=" + contact_number + "&dealership_id="
                + getDealershipId + "&ticket_note=" + ticket_input + "&tag_id=" + getStatusTagId;

        Log.d(TAG, "url now is: " + url);

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject ticketAll = (JSONObject) response.get(i);
                                String id = ticketAll.getString("id");
                                String ticketNote = ticketAll.getString("ticket_note");
                                String contact = ticketAll.getString("contact");
                                String dealershipId = ticketAll.getString("dealership_id");
                                String userId = ticketAll.getString("user_id");
                                String createdTime = ticketAll.getString("created_time");
                                String tagId = ticketAll.getString("tag_id");
                                String contact_name = ticketAll.getString("contact_name");

                                TicketMaster.add(new Tickets(Integer.valueOf(id) , ticketNote,Integer.valueOf(contact) ,Integer.valueOf(dealershipId) ,Integer.valueOf(userId) , createdTime,Integer.valueOf(tagId), contact_name));
                            }


                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CreateTicketActivity.this, android.R.layout.simple_list_item_1, DealershipMaster);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Get a RequestQueue
        // RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
        //  getRequestQueue();


        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);


    }
}
