package co.megaminds.sqlitebasiccrudoperation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.megaminds.sqlitebasiccrudoperation.Database.DbHelper;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private long ePID;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objectInitialization();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ePID = id;

                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + ePID, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    private void objectInitialization() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        dbHelper = new DbHelper(this);

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arrayList
        );

        listView.setAdapter(arrayAdapter);
        loadData();
    }

    private void showInputDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Input Dialog Box");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameEditText = new EditText(this);
        nameEditText.setHint("Name");
        layout.addView(nameEditText);

        final EditText phoneEditText = new EditText(this);
        phoneEditText.setHint("Phone Number");
        layout.addView(phoneEditText);

        alert.setView(layout);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String nameString = nameEditText.getText().toString();
                String phoneString = phoneEditText.getText().toString();

                saveInformationToDatabase(nameString, phoneString);

                Toast.makeText(getApplicationContext(), nameString + "\n" + phoneString, Toast.LENGTH_LONG).show();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void saveInformationToDatabase(String name, String phone) {
        dbHelper.insertRecord(name, phone);
        loadData(); //reload the ListView
    }

    private void loadData(){
        arrayList.clear();
        arrayList.addAll(dbHelper.getAllInformation());
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
