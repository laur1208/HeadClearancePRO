package ps.room.headclearancefree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class FastenerTypesActivity extends AppCompatActivity implements FastenerTypesRecyclerAdapter.ViewHolder.OnFastenerTypeClickListener {
    private static final String TAG = "==MAIN_ACTIVITY LOG==";
    private MyDatabaseHelper mMyDatabaseHelper;
    public static int VIBRATION_TOGGLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastener_types);

        fetchFastenerTypes();

        displayFastenerTypes();

    }

    private void vibrate(int VIBRATION_TOGGLE){
        if(VIBRATION_TOGGLE == 1){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert v != null;
                v.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                assert v != null;
                v.vibrate(80);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("VIBRATION_TOGGLE", VIBRATION_TOGGLE);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*-------place fastener types into the recycler view-----------*/
    private void displayFastenerTypes() {
        final RecyclerView recyclerFastenerTypes = findViewById(R.id.fastener_types_recycler);
        final LinearLayoutManager fastenerTypesLayoutManager = new LinearLayoutManager(this);
        recyclerFastenerTypes.setLayoutManager(fastenerTypesLayoutManager);
        recyclerFastenerTypes.setHasFixedSize(true);
        recyclerFastenerTypes.setOverScrollMode(View.OVER_SCROLL_NEVER);

        List<FastenerType> fastenerTypeList = DataManager.getInstance().getFastenerTypes();
        FastenerTypesRecyclerAdapter fastenerTypesRecyclerAdapter = new FastenerTypesRecyclerAdapter(this, fastenerTypeList, this);
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

        getVibrationEffect(database);

        DataManager.loadFastenerTypes(cursor);
        mMyDatabaseHelper.close();
        database.close();
    }

    public void getVibrationEffect(SQLiteDatabase database){
        Cursor vibrationCursor = database.query("VibrationEffect", null, null, null, null, null, null);
        int isSetPos = vibrationCursor.getColumnIndex("IS_SET");
        while(vibrationCursor.moveToNext()){
            VIBRATION_TOGGLE = vibrationCursor.getInt(isSetPos);
        }
        vibrationCursor.close();
    }

    @Override
    public void onFastenerTypeClick(int position, View clickedCell) {
        Intent intent = new Intent(this, FastenersListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("FASTENER_TYPE_ID", position);
        intent.putExtra("VIBRATION_TOGGLE", VIBRATION_TOGGLE);
        this.startActivity(intent);
        vibrate(VIBRATION_TOGGLE);
    }
}
