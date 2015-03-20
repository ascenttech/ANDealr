package com.ascentsmartwaves.andealr.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.async.RegisterMerchant;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.regex.Pattern;

/**
 * Created by ADMIN on 05-12-2014.
 */
public class SignUpActivity extends Activity {
    RelativeLayout header;
    EditText email,mobileno,password;
    Button signup;
    String registeration[],field[], merchantid[]=null;
    String possibleEmail;
    TelephonyManager tm;
    String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences.Editor editor;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        header=(RelativeLayout)findViewById(R.id.header_register_activity);
        signup=(Button)findViewById(R.id.signup_btn_register_activity);
        email=(EditText)findViewById(R.id.email_register_activity);
        mobileno=(EditText)findViewById(R.id.mobile_register_activity);
        password=(EditText)findViewById(R.id.password_register_activity);
        autoemail();
        automobile();

        editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                String email_String= String.valueOf(email.getText());
                if(email.length()>0 && mobileno.length()>0 && password.length()>0)
                {
                    if ((email_String.matches(emailPatterns)))
                    {
                        if (mobileno.length() == 10)
                        {
                            if (password.length() >= 6)
                            {
                                new RegisterMerchant(getApplicationContext(),new RegisterMerchant.RegisterMerchantCallback() {
                                    @Override
                                    public void onStart(boolean a) {
                                        dialog = new ProgressDialog(SignUpActivity.this);
                                        dialog.setTitle("Registering");
                                        dialog.setMessage("Loading, Please wait");
                                        dialog.show();
                                        dialog.setCancelable(false);
                                    }

                                    @Override
                                    public void onResult(boolean b) {
                                        dialog.dismiss();
                                        if(b)
                                        {
                                            if(Constants.merchantId.equals(null))
                                            {

                                            }
                                            else
                                            {
                                                editor.putString("Merchant Id",Constants.merchantId );
                                                editor.commit();
                                            }
                                            Intent i = new Intent(SignUpActivity.this,ProfileActivity.class);
                                            startActivity(i);
                                        }
                                        else{
                                            AlertDialog builder = new AlertDialog.Builder(SignUpActivity.this).create();
                                            builder.setTitle("Registration Status");
                                            builder.setMessage("This user has been registered already");
                                            builder.show();
                                        }

                                    }
                                }).execute(Constants.registerMerchant + email.getText() + "&contactNo=" + mobileno.getText() + "&pwd=" + password.getText());
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Password should be more then 6 digits", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Mobile number should not be less then 10 digits", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please complete all the details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }





    private void automobile()
    {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        if (number != null) {
            mobileno.setText(number);
        } else {
        }
    }

    private void autoemail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts)
        {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail  = account.name;
                email.setText(possibleEmail);
            }
        }
    }




//    @Override
//    public void onBackPressed() {
//        // nothing is written so that it doesnt get go back to the prev page
//    }

    public void cancelbtn(View v)
    {
        Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(i);
    }








}
