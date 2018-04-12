package in.infocruise.techsupport.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.infocruise.techsupport.Model.Call_Log;
import in.infocruise.techsupport.Model.Dealers;
import in.infocruise.techsupport.Model.Manufacturer;
import in.infocruise.techsupport.Model.Status_Tag;
import in.infocruise.techsupport.Model.Tickets;
import in.infocruise.techsupport.Model.User;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tab";

    /**
     * Table Names
     */
    // Login table name
    private static final String TABLE_USER = "user";
    // Manufacture table name
    private static final String TABLE_MANUFACTURER = "manufacturer";
    // Dealers table name
    private static final String TABLE_DEALERSHIP = "dealers";
    // Status tag tabel name
    private static final String TABLE_STATUS_TAG = "tags";
    // Ticket table name
    private static final String TABLE_TICKETS = "tickets";
    // Call Log Table name
    private static final String TABLE_LOGS = "logs";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT ="created_at";



    // Login Table - Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    // Manufacture Table - Column names
    private static final String KEY_MANUFACTURE_NAME = "name";

    // Dealers Table - Column names
    private static final String KEY_CODE = "code";
    private static final String KEY_DEALER_NAME = "name";
    private static final String KEY_MANUFACTURE_ID = "manufacturer_id";


    // Ticket Table - Column names
    private static final String KEY_NOTE = "note";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_DEALER_ID = "dealer_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TAG_ID  = "tag_id";
    private static final String KEY_CONTACT_NAME = "contact_name";

    // Log Table - Column names
    private static final String KEY_CALL_NAME = "call_name";
    private static final String KEY_PHONE_NO = "phone_no";
    private static final String KEY_CALL_TYPE = "call_type";
    private static final String KEY_DATE_TIME = "date_time";

    // Status Tag Table - Column names
    private static final String KEY_TAG_NAME = "tag_name";


    /**
     * TABLE CREATE STATEMENTS
     */
    // USER Table create statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_PASSWORD + " TEXT UNIQUE," + KEY_CREATED_AT + " DATETIME" + ")";

    // TABLE_MANUFACTURER CREATE STATEMENTS
    private static final String CREATE_TABLE_MANUFACTURER = "CREATE TABLE "
            + TABLE_MANUFACTURER + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MANUFACTURE_NAME
            + " TEXT UNIQUE" + ")";

    // Table Dealers create statments
    private static final String CREATE_TABLE_DEALERSHIP= "CREATE TABLE "
            + TABLE_DEALERSHIP + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CODE
            + " TEXT UNIQUE," + KEY_DEALER_NAME + " TEXT," + KEY_MANUFACTURE_ID + " INTEGER" + ")";

    // Ticket Table Create Statements
    private static final String CREATE_TABLE_TICKETS="CREATE TABLE "
            + TABLE_TICKETS + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOTE
            + " TEXT," + KEY_CONTACT + " INTEGER," + KEY_DEALER_ID + " INTEGER," + KEY_USER_ID + " INTEGER,"
            + KEY_TAG_ID + " INTEGER," + KEY_CONTACT_NAME + " TEXT," + KEY_CREATED_AT + " DATETIME"  + ")";

    // LOG Table Create Statements
    private static final String CREATE_TABLE_LOGS = "CREATE TABLE "
            + TABLE_LOGS + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CALL_NAME
            + " TEXT," + KEY_PHONE_NO + " INTEGER," + KEY_CALL_TYPE + " TEXT," + KEY_DATE_TIME + " DATETIME" + ")";

    // Status Tag Table Create Statements
    private static final String CREATE_TABLE_STATUS_TAG = "CREATE TABLE "
            + TABLE_STATUS_TAG + " (" + KEY_ID  + " INTEGER PRIMARY KEY," + KEY_TAG_NAME
            + "TEXT UNIQUE" + ")";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       // CREATING REQUIRED TABLES
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MANUFACTURER);
        db.execSQL(CREATE_TABLE_DEALERSHIP);
        db.execSQL(CREATE_TABLE_STATUS_TAG);
        db.execSQL(CREATE_TABLE_LOGS);
        db.execSQL(CREATE_TABLE_TICKETS);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // ON UPDATE DROP OLDER TABLES
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS_TAG);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_MANUFACTURER);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_DEALERSHIP);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_TICKETS);


        // Create tables again
        onCreate(db);
    }




    /**
     * Creating user table
     */
    public void createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,user.getName());
        values.put(KEY_PASSWORD,user.getPassword());
        values.put(KEY_CREATED_AT,getDateTime());

        // insert row
        db.insert(TABLE_USER, null,values);

    }

    /**
     * getting user
     */
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

            Log.e(TAG, selectQuery);

        Cursor c;
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            c = db.rawQuery(selectQuery, null);
        }

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                User u = new User();
                u.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                u.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                u.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));

                // adding to user list
                users.add(u);

            } while (c.moveToNext());
        }

        return users;
    }
    /**
     * Updating a user
     */
    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASSWORD,user.getPassword());

        // updating row
        return db.update(TABLE_USER,values,KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }


    /**
     * Creating Manufacturer table
     */
    public long create_manufacturers(Manufacturer manufacturer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MANUFACTURE_NAME, manufacturer.getManufacturer_name());

        // insert row
        long manufacturer_id = db.insert(TABLE_MANUFACTURER, null,values);

        return manufacturer_id;

    }


    /**
     * getting manufacturers
     */
    public List<Manufacturer> getAllManufacturers(){
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
        String selectQuery = "SELECT * FROM " + TABLE_MANUFACTURER;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                Manufacturer m= new Manufacturer();
                m.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                m.setManufacturer_name(c.getString(c.getColumnIndex(KEY_MANUFACTURE_NAME)));


                // adding to user list
                // adding to manufacturer list
                manufacturers.add(m);

            } while (c.moveToNext());
        }
        return manufacturers;
    }


    /**
     * Creating Dealers table
     */
    public long create_dealerships(Dealers dealers){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CODE, dealers.getDealer_code());
        values.put(KEY_DEALER_NAME,dealers.getDealer_name());
        values.put(KEY_MANUFACTURE_ID,dealers.getManufacturer_id());

        // insert row
        long dealership_id = db.insert(TABLE_DEALERSHIP, null, values);
        return dealership_id;

    }

    /**
     * getting dealers
     */
    public List<Dealers> getAllDealers(){
        List<Dealers> dealers = new ArrayList<Dealers>();
        String selectQuery = "SELECT * FROM " + TABLE_DEALERSHIP;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                Dealers d= new Dealers();
                d.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                d.setDealer_code(c.getString(c.getColumnIndex(KEY_CODE)));
                d.setDealer_name(c.getString(c.getColumnIndex(KEY_DEALER_NAME)));
                d.setManufacturer_id(c.getInt(c.getColumnIndex(KEY_MANUFACTURE_ID)));


                // adding to user list
                // adding to manufacturer list
                dealers.add(d);

            } while (c.moveToNext());
        }
        return dealers;
    }

    /**
     * Creating Status Tag Table
     */
    public long create_status_tag(Status_Tag status_tag){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, status_tag.getTag_name());

        // insert row
        long status_tag_id = db.insert(TABLE_STATUS_TAG, null, values);
        return status_tag_id;

    }
    /**
     * getting status tag
     */
    public List<Status_Tag> getAllStatusTag(){
        List<Status_Tag> status_tags = new ArrayList<Status_Tag>();
        String selectQuery = "SELECT * FROM " + TABLE_STATUS_TAG;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                Status_Tag s= new Status_Tag();
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                s.setTag_name(c.getString(c.getColumnIndex(KEY_TAG_NAME)));


                // adding to user list
                // adding to manufacturer list
                status_tags.add(s);

            } while (c.moveToNext());
        }
        return status_tags;
    }
    /**
     * Creating ticket table
     */
    public long create_tickets(Tickets tickets){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE, tickets.getNote());
        values.put(KEY_CONTACT,tickets.getContact());
        values.put(KEY_DEALER_ID,tickets.getDealer_id());
        values.put(KEY_USER_ID,tickets.getUser_id());
        values.put(KEY_TAG_ID,tickets.getTag_id());
        values.put(KEY_CONTACT_NAME,tickets.getContact_name());
        values.put(KEY_CREATED_AT,tickets.getCreated_at());

        // insert row
        long tickets_id = db.insert(TABLE_TICKETS, null, values);
        return tickets_id;

    }
    /**
     * getting tickets
     */
    public List<Tickets> getAllTickets(){
        List<Tickets> tickets = new ArrayList<Tickets>();
        String selectQuery = "SELECT * FROM " + TABLE_TICKETS;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                Tickets t= new Tickets();
                t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                t.setContact(c.getInt(c.getColumnIndex(KEY_CONTACT)));
                t.setContact_name(c.getString(c.getColumnIndex(KEY_CONTACT_NAME)));
                t.setDealer_id(c.getInt(c.getColumnIndex(KEY_DEALER_ID)));
                t.setNote(c.getString(c.getColumnIndex(KEY_NOTE)));
                t.setTag_id(c.getInt(c.getColumnIndex(KEY_TAG_ID)));
                t.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));


                // adding to user list
                // adding to manufacturer list
                tickets.add(t);

            } while (c.moveToNext());
        }
        return tickets;
    }

    /**
     * creating call log tables
     */
    public long create_call_log(Call_Log call_log){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_NO,call_log.getPhone_no());
        values.put(KEY_CALL_NAME,call_log.getCall_name());
        values.put(KEY_CALL_TYPE,call_log.getCall_type());
        values.put(KEY_DATE_TIME,call_log.getDate_time());


        // insert row
        long call_log_id = db.insert(TABLE_LOGS, null, values);
        Log.d(TAG, "New Call log inserted into sqlite: " + call_log_id);
        return call_log_id;

    }
    /**
     * getting call logs
     */
    public List<Call_Log> getAllLogs(){
        List<Call_Log> call_logs = new ArrayList<Call_Log>();
        String selectQuery = "SELECT * FROM " + TABLE_LOGS;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                Call_Log cl= new Call_Log();
                cl.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                cl.setCall_name(c.getString(c.getColumnIndex(KEY_CALL_NAME)));
                cl.setCall_type(c.getString(c.getColumnIndex(KEY_CALL_TYPE)));
                cl.setPhone_no(c.getString(c.getColumnIndex(KEY_PHONE_NO)));
                cl.setDate_time(c.getString(c.getColumnIndex(KEY_DATE_TIME)));

                // adding to user list
                // adding to manufacturer list
                call_logs.add(cl);

            } while (c.moveToNext());
        }
        return call_logs;
    }



    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
