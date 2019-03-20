package com.utsavgupta.cmclage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity
        extends Activity
        implements View.OnClickListener
{
    private static final String Locale_KeyValue = "Saved Locale";
    private static final String Locale_Preference = "Locale Preference";
    private static TextView chooseText;
    private static SharedPreferences.Editor editor;
    private static Button english;
    private static Button french;
    private static Button german;
    private static Button hindi;
    private static Locale myLocale;
    private static Button russian;
    private static SharedPreferences sharedPreferences;

    private void initViews()
    {
        sharedPreferences = getSharedPreferences("Locale Preference", 0);
        editor = sharedPreferences.edit();

    }

    private void setListeners()
    {
        english.setOnClickListener(this);
        hindi.setOnClickListener(this);
        french.setOnClickListener(this);
        german.setOnClickListener(this);
        russian.setOnClickListener(this);
    }

    private void updateTexts()
    {

    }

    public void changeLocale(String paramString)
    {
        if (paramString.equalsIgnoreCase("")) {
            return;
        }
        myLocale = new Locale(paramString);
        saveLocale(paramString);
        Locale.setDefault(myLocale);
        Configuration localConfiguration = new Configuration();
        localConfiguration.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(localConfiguration, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void loadLocale()
    {
        changeLocale(sharedPreferences.getString("Saved Locale", ""));
    }

    public void onClick(View paramView)
    {
        String str = "en";
        switch (paramView.getId())
        {
            default:
                break;
            case 2131296594:
                str = "ru";
                break;
            case 2131296431:
                str = "hi";
                break;
            case 2131296421:
                str = "de";
                break;
            case 2131296419:
                str = "fr";
                break;
            case 2131296400:
                str = "en";
        }
        changeLocale(str);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        Locale localLocale = new Locale("fr_FR");
        Locale.setDefault(localLocale);
        Configuration localConfiguration = new Configuration();
        localConfiguration.locale = localLocale;
        getApplicationContext().getResources().updateConfiguration(localConfiguration, getApplicationContext().getResources().getDisplayMetrics());
        initViews();
        saveLocale("ja");
        setListeners();
        loadLocale();
    }

    public void saveLocale(String paramString)
    {
        editor.putString("Saved Locale", paramString);
        editor.commit();
    }

    public void setLocale(String paramString)
    {
        Locale localLocale = new Locale(paramString);
        Resources localResources = getResources();
        DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
        Configuration localConfiguration = localResources.getConfiguration();
        localConfiguration.locale = localLocale;
        localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

/* Location:           C:\Users\Utsav Gupta\Downloads\debug\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes3_dex2jar.jar * Qualified Name:     com.utsavgupta.cmclage.MainActivity * JD-Core Version:    0.7.0.1 */