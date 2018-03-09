/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.example.justinmeskan.justjava.R;
import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public final String QUANTITY = "quantity";
    public final String CHOCOLATE = "chocolate";
    public final String WHIP = "whip";

    int quantity = 0;

    boolean whip = false;
    boolean chocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** saves app data */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUANTITY, quantity);
        outState.putBoolean(CHOCOLATE, chocolate);
        outState.putBoolean(WHIP, whip);
    }

    /** Restores app data on new state */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt(QUANTITY);
        chocolate = savedInstanceState.getBoolean(CHOCOLATE);
        whip = savedInstanceState.getBoolean(WHIP);
        displayQuantity(quantity);
        checkChoco(chocolate);
        checkWhip(whip);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        displayQuantity(quantity);
        sendEmai(getName(),createOrderSummary(calculatePrice()));
    }

    public void increment(View view) {
        quantity += 1;
        if(quantity > 100){
            quantity = 100;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity -= 1;
        if(quantity < 1){
            quantity = 1;
        }
        this.displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void sendEmai(String email, String content){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/html");
        intent.setData(Uri.parse("mailto:justinmeskan@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your JustJava Order");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    public void checkWhip(boolean w){
        CheckBox whipBox = findViewById(R.id.whipped);
        whipBox.setChecked(w);
    }

    public void checkChoco(boolean c){
        CheckBox chocBox = findViewById(R.id.chocolated);
        chocBox.setChecked(c);
    }

    public void whipCream(View view){
        whip = ((CheckBox) view).isChecked();
    }

    public void chocolateSauce(View view){
        chocolate = ((CheckBox) view).isChecked();
    }

    public String getName(){
        EditText tv = (EditText) findViewById(R.id.name);
        return tv.getText().toString();
    }

    private int calculatePrice() {
       int total = quantity * 5;
       if(whip){
           total += 1 * quantity;
       }
       if(chocolate){
           total += 2 * quantity;
       }
       return total;
    }

    private String createOrderSummary(int price) {
        String mess = getString(R.string.namestr, getName());
        mess += getString(R.string.add_whipped_cream, whip);
        mess += getString(R.string.add_chocolate_sauce, chocolate);
        mess += getString(R.string.quantitystr, quantity);
        mess += getString(R.string.total,NumberFormat.getCurrencyInstance().format(price));
        mess += getString(R.string.thank_you);
        return  mess;
    }
}
