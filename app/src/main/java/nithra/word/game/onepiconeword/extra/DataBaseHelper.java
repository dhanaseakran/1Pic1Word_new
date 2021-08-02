package nithra.word.game.onepiconeword.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static String TAG = "DataBaseHelper"; // Tag just for the LogCat
	// window
	// destination path (location) of our database on device
	///private static String DB_PATH = "";
	private static String DB_NAME = "findtheword";// Database name
	private SQLiteDatabase mDataBase;
	private final Context mContext;
	Cursor c;
	SharedPreferences mPreferences;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);// 1? its Database Version

		this.mContext = context;
	}



	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub


		if(newVersion>oldVersion) {


			db.execSQL("CREATE TABLE IF NOT EXISTS dailytest (id integer NOT NULL PRIMARY KEY AUTOINCREMENT,isshow VARCHAR,words VARCHAR," +
					"mtype VARCHAR,packid VARCHAR" + ");");
		}
	}



	public Cursor getQry(String Qry) {
		SQLiteDatabase sq = this.getReadableDatabase();
		c = sq.rawQuery(Qry, null);
		return c;
	}


	public void executeSql(String Qry) {

		SQLiteDatabase sq = this.getReadableDatabase();
		sq.execSQL(Qry);

	}



	public void createDataBase() throws IOException {
		// If database not exists copy it from the assets

		boolean mDataBaseExist = checkDataBase();

		// mDataBaseExist=false;
		String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
		if (mDataBaseExist) {

			File dbFile = new File(DB_PATH + DB_NAME);
			dbFile.delete();
		}

		// if (!mDataBaseExist) {
		this.getReadableDatabase();
		this.close();
		try {
			// Copy the database from assests
			copyDataBase();
			Log.e(TAG, "createDatabase database created");
			// Toast.makeText(mContext,
			// "aptitudequestiondb database copied",
			// Toast.LENGTH_LONG).show();
		} catch (IOException mIOException) {
			throw new Error("ErrorCopyingDataBase");
		}
		// } else {
		// // Toast.makeText(mContext, "aptitudequestiondb Already exits",
		// // Toast.LENGTH_LONG).show();
		// }
	}

	public void createDataBaseIFexits() throws IOException {
		// If database not exists copy it from the assets

		boolean mDataBaseExist = checkDataBase();

		// mDataBaseExist=false;

		this.getReadableDatabase();
		this.close();
		try {
			// Copy the database from assests
			copyDataBase();
			Log.e(TAG, "createDatabase database created");
			// Toast.makeText(mContext,
			// "aptitudequestiondb database copied",
			// Toast.LENGTH_LONG).show();
		} catch (IOException mIOException) {
			throw new Error("ErrorCopyingDataBase");
		}

	}

	// Check that the database exists here: /data/data/your package/databases/Da
	// Name
	public  boolean checkDataBase() {
		String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
		File dbFile = new File(DB_PATH + DB_NAME);
		// Log.v("dbFile", dbFile + "   "+ dbFile.exists());
		return dbFile.exists();
	}

	// Copy the database from assets
	public void copyDataBase() throws IOException {
		String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
		InputStream mInput = mContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		//	int mLength;
		while ((mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	// Open the database, so we can query it
	public boolean openDataBase() throws SQLException {
		String DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/";
		String mPath = DB_PATH + DB_NAME;
		// Log.v("mPath", mPath);
		mDataBase = SQLiteDatabase.openDatabase(mPath, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		// mDataBase = SQLiteDatabase.openDatabase(mPath, null,
		// SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();
		super.close();
	}


}
