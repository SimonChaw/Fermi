package ca.simon.fermi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //UI Controls
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Button btnOk;
    Button btnReset;
    TextView txtGuesses;
    ArrayAdapter arrayAdapter;
    Fermi fermi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET CONTROLS
            //spinners
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
            //buttons
        btnOk = (Button) findViewById(R.id.btnOk);
        btnReset = (Button) findViewById(R.id.btnReset);
            //TextViews
        txtGuesses = (TextView) findViewById(R.id.txtGuesses);
        fermi = new Fermi();
        Integer[] num = {0,1,2,3,4,5,6,7,8,9};
        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, num);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);
        spinner3.setAdapter(arrayAdapter);

        
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fermi.reset();
            }
        });
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
