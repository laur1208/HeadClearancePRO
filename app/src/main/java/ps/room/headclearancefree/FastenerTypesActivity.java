package ps.room.headclearancefree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class FastenerTypesActivity extends AppCompatActivity {
    private static final String TAG = "==MAIN_ACTIVITY LOG==";
    private MyDatabaseHelper mMyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastener_types);

        Log.d(TAG,"Before data fetching.");
        fetchFastenerTypes();
        Log.d(TAG,"AFTER data fetching.");

        Log.d(TAG,"Before data display.");
        displayFastenerTypes();
        Log.d(TAG,"After data display.");
    }

    /*-------place fastener types into the recycler view-----------*/
    private void displayFastenerTypes() {
        final RecyclerView recyclerFastenerTypes = findViewById(R.id.fastener_types_recycler);
        final LinearLayoutManager fastenerTypesLayoutManager = new LinearLayoutManager(this);
        recyclerFastenerTypes.setLayoutManager(fastenerTypesLayoutManager);
        recyclerFastenerTypes.setHasFixedSize(true);
        recyclerFastenerTypes.setOverScrollMode(View.OVER_SCROLL_NEVER);

        List<FastenerType> fastenerTypeList = DataManager.getInstance().getFastenerTypes();
        FastenerTypesRecyclerAdapter fastenerTypesRecyclerAdapter = new FastenerTypesRecyclerAdapter(this, fastenerTypeList);
        recyclerFastenerTypes.setAdapter(fastenerTypesRecyclerAdapter);
    }

    /*-------get fastener types from database---------*/
    public void fetchFastenerTypes() {
        mMyDatabaseHelper = MyDatabaseHelper.getInstance(this);
        try {

            mMyDatabaseHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase database = mMyDatabaseHelper.getReadableDatabase();
        Cursor cursor = database.query("vw_fastener_types", null, null, null, null, null, null);

        DataManager.loadFastenerTypes(cursor);
        mMyDatabaseHelper.close();
        database.close();
    }
}
