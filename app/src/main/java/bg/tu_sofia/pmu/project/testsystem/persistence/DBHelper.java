package bg.tu_sofia.pmu.project.testsystem.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bg.tu_sofia.pmu.project.testsystem.persistence.datasources.UsersDataSource;
import bg.tu_sofia.pmu.project.testsystem.persistence.model.User;

/**
 * Created by Stefan Chuklev on 12.6.2016 г..
 */
public class DBHelper extends SQLiteOpenHelper implements DBConstants {

    private static DBHelper instance = null;

    private SQLiteDatabase readableDB = null;
    private SQLiteDatabase writableDB = null;

    private Context ctx;

    public synchronized static DBHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DBHelper(ctx);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
        readableDB = getReadableDatabase();
        writableDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESULTS_TABLE);
        onCreate(db);
    }

    public SQLiteDatabase getPooledReadableDB() {return readableDB; }

    public SQLiteDatabase getPooledWritableDB() { return writableDB; }


    // test purpose
    static boolean isPopulated = false;
    public void populateDB () {
        if (!isPopulated) {
            UsersDataSource uds = new UsersDataSource(ctx);
            User user = User.createAdminUser("atasheva@tu-sofia.bg", "123123");
            uds.insertUser(user);
            user = User.createAdminUser("s.chuklev@gmail.com", "1q2w3e4r");
            uds.insertUser(user);
            user = User.createStudentUser("student@test.com", "123123");
            uds.insertUser(user);
            user = User.createStudentUser("test@student.com", "1q2w3e4r");
            uds.insertUser(user);
            isPopulated = true;
        }




    }


}
