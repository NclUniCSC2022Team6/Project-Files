package com.example.b6015413.usbtourteam6.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class Settings extends Fragment {

    // todo these should probs be private
    public static float fontSize = 15f;
    public static boolean updateDB = true;

    TextView settings, lookAndFeel, darkModeTxt, fontSizeTxt, about, appInfo, developerInfo;
    View line1, line2;
    ToggleButton darkModeBtn;

    public static void setFontSize(float fontSize) {
        Settings.fontSize = fontSize;
    }

    public static void setUpdateDB(boolean updateDB) {
        Settings.updateDB = updateDB;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inputFragmentView = inflater.inflate(R.layout.activity_settings, container, false);

        super.onCreate(savedInstanceState);

        //region Add fonts
        AssetManager am = getContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion

        View view = getView();

        //region findViewById's
        settings = view.findViewById(R.id.settingsTxt);
        lookAndFeel = view.findViewById(R.id.lookAndFeel);
        darkModeTxt = view.findViewById(R.id.darkMode);
        darkModeBtn = view.findViewById(R.id.darkModeBtn);
        fontSizeTxt = view.findViewById(R.id.fontSize);
        line1 = view.findViewById(R.id.line1);
        about = view.findViewById(R.id.about);
        appInfo = view.findViewById(R.id.appInfo);
        line2 = view.findViewById(R.id.line2);
        developerInfo = view.findViewById(R.id.developerInfo);
        //endregion

        //region setting Typefaces
        settings.setTypeface(robotoLight);
        lookAndFeel.setTypeface(robotoBlack);
        darkModeTxt.setTypeface(robotoLight);
        darkModeBtn.setTypeface(robotoLight);
        fontSizeTxt.setTypeface(robotoLight);
        about.setTypeface(robotoBlack);
        appInfo.setTypeface(robotoLight);
        developerInfo.setTypeface(robotoLight);

        updateTextSize();
        //endregion

        darkModeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //this is where we will toggle dark mode on
                    darkModeBtn.setTextOn("On");
                } else {
                    //this is where we will toggle dark mode off
                    darkModeBtn.setTextOff("Off");
                }
            }
        });

        SeekBar textSizeSeek = view.findViewById(R.id.fontSizeSeekBar);
        textSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Settings.setFontSize(seekBar.getProgress());
                updateTextSize();
            }
        });

        textSizeSeek.setProgress(Math.round(Settings.fontSize));

        return inputFragmentView;

    }

    private void updateTextSize() {
        settings.setTextSize(Settings.fontSize);
        lookAndFeel.setTextSize(Settings.fontSize + 5f);
        darkModeTxt.setTextSize(Settings.fontSize + 5f);
        darkModeBtn.setTextSize(Settings.fontSize + 5f);
        fontSizeTxt.setTextSize(Settings.fontSize + 5f);
        about.setTextSize(Settings.fontSize + 5f);
        appInfo.setTextSize(Settings.fontSize + 5f);
        developerInfo.setTextSize(Settings.fontSize + 5f);
    }
}
