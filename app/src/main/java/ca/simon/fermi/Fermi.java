package ca.simon.fermi;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 *  Object Oriented Programming : Project II
 *  Fermi Guessing Game
 *  @author Sean Morrow
 */

public class Fermi implements Parcelable {
    /** The first random digit to be guessed in the game of Fermi. */
    private int first;
    /** The second random digit to be guessed in the game of Fermi. */
    private int second;
    /** The third random digit to be guessed in the game of Fermi. */
    private int third;
    /** the number of guesses made for the current game */
    private int guesses;
    private boolean win;

    /**
     * Creates a new instance of the game with three distinct random numbers
     * to guess.
     */
    public Fermi() {
        reset();
    }

    // --------------------------------------------------------------- get/set methods
    public int getGuesses() {
        return guesses;
    }

    public boolean win(){
        return win;
    }
    // --------------------------------------------------------------- public methods
    /**
     * resets fermi object and thus generates three distinct random numbers in the range of 0-9
     */
    public void reset() {
        // initialization
        guesses = 0;
        win = false;
        // randomly determine target numbers
        first = (int) (Math.random() * 10);
        do {
            second = (int) (Math.random() * 10);
        } while (second == first);

        do {
            third = (int) (Math.random() * 10);
        } while (third == first || third == second);

        // dump out correct answer for debugging
        Log.d("answer", first + " " + second + " " + third);
    }

    /**
     * Evaluates a guess and returns three hints. The hints are returned in
     * alphabetic order. The hints can be Fermi, Pico, or Nano. If a digit guessed
     * for a given position is correct, then the reply is Fermi. If a digit
     * guessed for a given position is in a different position, the reply is Pico.
     * If the digit guessed for a given position does not match any of the
     * three digits, then the reply is Nano.
     *
     * @param fst the element guessed in the first position
     * @param sec the element guessed in the second position
     * @param trd the element guessed in the third position
     *
     * @return a string containing the three hints, sorted in alphabetic order
     */
    public String guess(int fst, int sec, int trd){
        String out = "";



        int nanoCounter = 3;
        int firstTarget = first;
        int secondTarget = second;
        int thirdTarget = third;

        // first iteration to find fermis
        if (fst == firstTarget) {
            out += "Fermi ";
            firstTarget = -1;
            nanoCounter--;
        }
        if (sec == secondTarget) {
            out += "Fermi ";
            secondTarget = -1;
            nanoCounter--;
        }
        if (trd == thirdTarget) {
            out += "Fermi ";
            thirdTarget = -1;
            nanoCounter--;
        }

        // second iteration to find picos
        if (fst == secondTarget || fst == thirdTarget) {
            out += "Pico ";
            nanoCounter--;
        }
        if (sec == firstTarget || sec == thirdTarget) {
            out += "Pico ";

            nanoCounter--;
        }
        if (trd == firstTarget || trd == secondTarget) {
            out += "Pico ";
            nanoCounter--;
        }

        // third iteration for nanos
        for (int n = 0; n < nanoCounter; n++) {
            out += "Nano ";
        }

        // increment guess counter
        guesses++;
        if (out.contains("Fermi Fermi Fermi")) {
            win = true;
        }
        // return feedback string (remove space character at end of string)
        return out.substring(0, out.length() - 1);
    }


    //if the guess is in the array list do not allow it to run.
    public boolean alreadyGuessed(String submitedGuess, ArrayList<String> guessList){
        for(String guess : guessList){
            if(guess.equals(submitedGuess)){
                return true;
            }
        }
        return false;
    }

    protected Fermi(Parcel in) {
        first = in.readInt();
        second = in.readInt();
        third = in.readInt();
        guesses = in.readInt();
        win = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(first);
        dest.writeInt(second);
        dest.writeInt(third);
        dest.writeInt(guesses);
        dest.writeByte((byte) (win ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Fermi> CREATOR = new Parcelable.Creator<Fermi>() {
        @Override
        public Fermi createFromParcel(Parcel in) {
            return new Fermi(in);
        }

        @Override
        public Fermi[] newArray(int size) {
            return new Fermi[size];
        }
    };
}