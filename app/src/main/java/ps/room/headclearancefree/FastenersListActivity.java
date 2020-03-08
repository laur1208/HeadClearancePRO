package ps.room.headclearancefree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import ps.room.headclearancefree.DatabaseTablesContract.FastenerDescriptionEntry;

public class FastenersListActivity extends AppCompatActivity implements FastenersListRecyclerAdapter.ViewHolder.OnFastenerListener {
    private static final String TAG = "==FASTENER LIST LOG==";
    private MyDatabaseHelper mMyDatabaseHelper;
    private FastenersListRecyclerAdapter mFastenerTypesRecyclerAdapter;
    private List<FastenerDescription> mFastenerDescriptionList;
    private int mFastenerTypeId;
    private List<String> previousSizes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasteners_list);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            mFastenerTypeId = -1;
        }else{
            mFastenerTypeId = extras.getInt("FASTENER_TYPE_ID");
            if(extras.getStringArrayList("sizes") != null){
                previousSizes = extras.getStringArrayList("sizes");
            }
        }

        fetchFastenerDescriptionList(mFastenerTypeId);

        displayFastenersList();

    }

    public void fetchFastenerDescriptionList(int fastenerTypeId) {
        mMyDatabaseHelper = MyDatabaseHelper.getInstance(this);
            try {

            mMyDatabaseHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String selection = FastenerDescriptionEntry.FASTENER_TYPE_ID + " = ?";
        String[] selectionArgs = {Integer.toString(fastenerTypeId)};

        SQLiteDatabase database = mMyDatabaseHelper.getReadableDatabase();
        Cursor cursor = database.query("vw_fasteners_description", null, selection, selectionArgs, null, null, null);

        DataManager.loadFastenerDescriptionList(cursor);
        mMyDatabaseHelper.close();
        database.close();
    }

    private void displayFastenersList() {
        RecyclerView recyclerFastenersList = findViewById(R.id.fasteners_list_recycler);
        final LinearLayoutManager fastenerTypesLayoutManager = new LinearLayoutManager(this);
        recyclerFastenersList.setLayoutManager(fastenerTypesLayoutManager);
        recyclerFastenersList.setHasFixedSize(true);

        mFastenerDescriptionList = DataManager.getInstance().getFastenersList();
        mFastenerTypesRecyclerAdapter = new FastenersListRecyclerAdapter(this, mFastenerDescriptionList, this);
        recyclerFastenersList.setAdapter(mFastenerTypesRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMyDatabaseHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                previousSizes = data.getExtras().getStringArrayList("sizes");
            }
        }
    }

    @Override
    public void onFastenerClick(int position, View clickedView, int fastener_id, int fastener_type_id, int value_to_set_on_favorite) {
        if (clickedView.getId() == R.id.is_favorite) {
            updateFastenerFavoriteValue(position, fastener_id, fastener_type_id, value_to_set_on_favorite);
        } else {
            Intent intent = new Intent(this, FastenerDetailActivity.class);
            ConstraintLayout constraintLayout = findViewById(R.id.list_constraint_layout);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("fastenerTypeId", fastener_type_id);
            intent.putExtra("fastenerId", fastener_id);
            if(previousSizes != null){
                intent.putExtra("sizes", (Serializable) previousSizes);
            }
            //this.finish();
//            startActivity(intent);
            startActivityForResult(intent, 1);
        }
    }

    void updateFastenerFavoriteValue(int position, int fastener_id, int fastener_type_id, int value_to_set_on_favorite){
        mMyDatabaseHelper = MyDatabaseHelper.getInstance(this);
        try {

            mMyDatabaseHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(FastenerDescriptionEntry.IS_FAVORITE, value_to_set_on_favorite);
        String whereClause = FastenerDescriptionEntry.ID + " = ?";
        String[] whereArgs = {fastener_id +" "};

        SQLiteDatabase database = mMyDatabaseHelper.getReadableDatabase();

        try{
            //load all data
            //logDataFromDb(database, "FastenerDescription", "[Update] ", "[Before update] ",null,null,null);

            logDataFromDb(database, "FastenerDescription", "[Update] ", "[Before update] ",null,"id = ?",whereArgs, position);

            int nr_of_updated_rows = database.update("FastenerDescription", values, whereClause, whereArgs);

            //update all
            //int nrOfUpdatedRows = database.update("FastenerDescription", values, null, null);
            logDataFromDb(database, "FastenerDescription", "[Update] ", "[After update] ",null,"id = ?",whereArgs, position);
            //logDataFromDb(database, "FastenerDescription", "[Update] ", "[Before update] ",null,null,null);

            if(nr_of_updated_rows > 0){
                mMyDatabaseHelper.close();
                database.close();

                if(value_to_set_on_favorite == 1)
                    Toast.makeText(this,"Favorite fastener moved up",Toast.LENGTH_SHORT).show();

                fetchFastenerDescriptionList(fastener_type_id);
                mFastenerDescriptionList = DataManager.getInstance().getFastenersList();
                mFastenerTypesRecyclerAdapter.notifyDataSetChanged();
            }
        }catch (SQLException ex)
        {
            Log.i(TAG,"[Update fastener error]: " + ex);
        }
    }

    void logDataFromDb(SQLiteDatabase db, String tableName, String logTag, String whenHappened, String[] columnNames, String selection, String[] selectionArgs, int positionInList){
        Cursor cursor = db.query(tableName,columnNames,selection,selectionArgs,null,null,null);
        int favoritePos = cursor.getColumnIndex(FastenerDescriptionEntry.IS_FAVORITE);
        int fastenerNamePos = cursor.getColumnIndex(FastenerDescriptionEntry.FASTENER_NAME);
        int fastenerIdPos = cursor.getColumnIndex(FastenerDescriptionEntry.ID);
        while(cursor.moveToNext()){
            int favorite = cursor.getInt(favoritePos);
            String fastenerName = cursor.getString(fastenerNamePos);
            int id = cursor.getInt(fastenerIdPos);
            Log.i(logTag, whenHappened + " [fastener id]: " + id + " [is favorite]: " + favorite + " [fastener name]:" + fastenerName + " [at position ] " + positionInList);
        }
        cursor.close();
    }
}
