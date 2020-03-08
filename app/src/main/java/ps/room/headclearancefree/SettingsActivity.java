package ps.room.headclearancefree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {
    public static int VIBRATION_TOGGLE;
    private MyDatabaseHelper mMyDatabaseHelper;

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this, FastenerTypesActivity.class);
        backIntent.putExtra("VIBRATION_TOGGLE", VIBRATION_TOGGLE);
        setResult(RESULT_OK, backIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        VIBRATION_TOGGLE = extras.getInt("VIBRATION_TOGGLE");

        getVibrationEffect();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void getVibrationEffect(){
        mMyDatabaseHelper = MyDatabaseHelper.getInstance(this);
        try {

            mMyDatabaseHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase database = mMyDatabaseHelper.getReadableDatabase();
        Cursor vibrationCursor = database.query("VibrationEffect", null, null, null, null, null, null);
        int isSetPos = vibrationCursor.getColumnIndex("IS_SET");
        while(vibrationCursor.moveToNext()){
            VIBRATION_TOGGLE = vibrationCursor.getInt(isSetPos);
        }
        vibrationCursor.close();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private FragmentActivity mContext;
        private FragmentActivity mActivity;
        private MyDatabaseHelper mMyDatabaseHelper;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            mContext = this.getActivity();
            mActivity = this.getActivity();

            final SwitchPreferenceCompat onOffRandomColor = findPreference(mContext.getString(R.string.vibration_toggle));
            if(VIBRATION_TOGGLE == 0) {
                assert onOffRandomColor != null;
                onOffRandomColor.setChecked(false);
            }
            else {
                assert onOffRandomColor != null;
                onOffRandomColor.setChecked(true);
            }

            onOffRandomColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(onOffRandomColor.isChecked()){
                        updateVibrationSetting(0);
                        VIBRATION_TOGGLE = 0;

                        // Checked the switch programmatically
                        onOffRandomColor.setChecked(false);
                    }else {
                        updateVibrationSetting(1);
                        VIBRATION_TOGGLE = 1;

                        // Unchecked the switch programmatically
                        onOffRandomColor.setChecked(true);
                    }
                    return false;
                }
            });
        }

        private void updateVibrationSetting(int value){
            mMyDatabaseHelper = MyDatabaseHelper.getInstance(mContext);
            try {

                mMyDatabaseHelper.createDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ContentValues values = new ContentValues();
            values.put("IS_SET", value);
            SQLiteDatabase database = mMyDatabaseHelper.getReadableDatabase();
            int nr_of_updated_rows = database.update("VibrationEffect", values, null, null);
        }

    }
}