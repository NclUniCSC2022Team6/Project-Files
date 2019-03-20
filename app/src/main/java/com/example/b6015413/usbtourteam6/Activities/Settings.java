package com.example.b6015413.usbtourteam6.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.b6015413.usbtourteam6.R;

import java.util.Locale;

public class Settings extends Fragment {

    // todo these should probs be private
    public static float fontSize = 15f;
    public static boolean updateDB = true;

    TextView settings, lookAndFeel, fontSizeTxt, about, appInfo, developerInfo;
    View line1, line2;
    Button appInfoBtn, developerInfoBtn;

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
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        // Toolbar title
        ((FrameworkMain) getActivity())
                .setActionBarTitle("Settings");

        super.onCreate(savedInstanceState);

        //region Add fonts
        AssetManager am = getContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
        Typeface robotoBlack = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Black.ttf"));
        //endregion


        //region findViewById's
        // settings = view.findViewById(R.id.settingsTxt);
        lookAndFeel = view.findViewById(R.id.lookAndFeel);
        fontSizeTxt = view.findViewById(R.id.fontSize);
        line1 = view.findViewById(R.id.line1);
        about = view.findViewById(R.id.about);
        appInfo = view.findViewById(R.id.appInfo);
        line2 = view.findViewById(R.id.line2);
        developerInfo = view.findViewById(R.id.developerInfo);
        appInfoBtn = view.findViewById(R.id.appInfoBtn);
        developerInfoBtn = view.findViewById(R.id.developerInfoBtn);
        //endregion

        //region setting Typefaces
        //settings.setTypeface(robotoLight);
        lookAndFeel.setTypeface(robotoBlack);
        fontSizeTxt.setTypeface(robotoLight);
        about.setTypeface(robotoBlack);
        appInfo.setTypeface(robotoLight);
        developerInfo.setTypeface(robotoLight);

        updateTextSize();
        //endregion

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
                float size;
                switch (seekBar.getProgress()) {
                    case 1:
                        size = 15;
                        break;
                    case 2:
                        size = 22;
                        break;
                    default: // can only be 3 but default to stop compiler complaining
                        size = 30;
                        break;
                }
                Settings.setFontSize(size);
                updateTextSize();
            }
        });

        int progress;
        switch (Math.round(Settings.fontSize)) {
            case 15:
                progress = 1;
                break;
            case 22:
                progress = 2;
                break;
            default: // can only be 30 but default to stop compiler complaining
                progress = 3;
                break;
        }
        textSizeSeek.setProgress(progress);

        final Context context = getContext();

        //region Button clicks
        appInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Application Information:")
                        .setMessage(R.string.app_info)
                        .show();
            }
        });

        developerInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Developer Information:")
                        .setMessage(R.string.developer_info)
                        .show();
            }
        });
        //endregion

        return view;

    }

    private void updateTextSize() {
        // settings.setTextSize(Settings.fontSize);
        lookAndFeel.setTextSize(Settings.fontSize + 5f);
        fontSizeTxt.setTextSize(Settings.fontSize + 5f);
        about.setTextSize(Settings.fontSize + 5f);
        appInfo.setTextSize(Settings.fontSize + 5f);
        developerInfo.setTextSize(Settings.fontSize + 5f);
    }
}