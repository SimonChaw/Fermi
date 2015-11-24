package ca.simon.fermi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    //UI Controls
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Button btnOk;
    Button btnReset;
    TextView txtGuesses;
    ArrayAdapter arrayAdapter;
    Boolean firstGuess;
    Fermi fermi;
    private ArrayList<String> guessList = new ArrayList<>();
    private Toast popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guessList.add("init");
        popup = Toast.makeText(this,"",Toast.LENGTH_SHORT);
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
        txtGuesses.setMovementMethod(new ScrollingMovementMethod());
        Integer[] num = {0,1,2,3,4,5,6,7,8,9};
        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, num);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);
        spinner3.setAdapter(arrayAdapter);
        if(savedInstanceState != null){
            fermi = savedInstanceState.getParcelable("fermi");
            txtGuesses.setText(savedInstanceState.getString("txtGuesses"));
            guessList = savedInstanceState.getStringArrayList("guesses");
            firstGuess = savedInstanceState.getBoolean("firstGuess");
            spinner1.setSelection(savedInstanceState.getInt("spinner1"));
            spinner2.setSelection(savedInstanceState.getInt("spinner2"));
            spinner3.setSelection(savedInstanceState.getInt("spinner3"));
        }else{
            fermi = new Fermi();
            firstGuess = true;
        }
        setUpButtons();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGuess();
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

    public void onSaveInstanceState(Bundle outState){
        outState.putString("txtGuesses", txtGuesses.getText().toString());
        outState.putStringArrayList("guesses", guessList);
        outState.putParcelable("fermi", fermi);
        outState.putBoolean("firstGuess", firstGuess);
        outState.putInt("spinner1", spinner1.getSelectedItemPosition());
        outState.putInt("spinner2",spinner2.getSelectedItemPosition());
        outState.putInt("spinner3",spinner3.getSelectedItemPosition());
    }

    public void makeGuess(){
        //first guess clear ??? from text view
        if(firstGuess) {
            txtGuesses.setText("");
            firstGuess = false;
        }
        //if the user already made this guess display a message
        if(alreadyGuessed(Integer.toString((Integer) spinner1.getSelectedItem())
                + Integer.toString((Integer) spinner2.getSelectedItem()) + Integer.toString((Integer) spinner3.getSelectedItem()))) {
                toastMe("Sorry! You've already made this guess!");
        }else {//else add it to the arraylist and make the guess
            guessList.add(Integer.toString((Integer) spinner1.getSelectedItem())
                    + Integer.toString((Integer) spinner2.getSelectedItem()) + Integer.toString((Integer) spinner3.getSelectedItem()));
            txtGuesses.setText(fermi.guess((Integer) spinner1.getSelectedItem(),
                    (Integer) spinner2.getSelectedItem(), (Integer) spinner3.getSelectedItem()) + "\n" + txtGuesses.getText());
            //check if the player has won
            if (fermi.win()) {
                onWin();
            }
        }
    }

    //once the reset button is pressed restore the game state to as it was when on create first ran
    public void Reset(){
        fermi.reset();
        txtGuesses.setText("???");
        firstGuess = true;
        btnOk.setEnabled(true);
        spinner1.setEnabled(true);
        spinner2.setEnabled(true);
        spinner3.setEnabled(true);
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
        guessList.clear();
        guessList.add("init");
    }

    public void setUpButtons(){
        btnOk.setEnabled(!fermi.win());
        spinner1.setEnabled(!fermi.win());
        spinner2.setEnabled(!fermi.win());
        spinner3.setEnabled(!fermi.win());
        btnReset.setEnabled(fermi.win());
    }

    //Once the player wins display the win message, disable all controls and enable the resetbutton
    public void onWin(){
        txtGuesses.setText("Congrats! Total Guesses: " + fermi.getGuesses() + "\n" + txtGuesses.getText());
        setUpButtons();
    }



    //if the guess is in the array list do not allow it to run.
    public boolean alreadyGuessed(String submitedGuess){
        for(String guess : guessList){
            if(guess.equals(submitedGuess)){
                return true;
            }
        }
        return false;
    }

    private void toastMe(String msg){
        popup.setText(msg);
        popup.show();
    }

}
