package com.example.b6015413.usbtourteam6.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Settings extends Fragment {

    private static float fontSize = 15f;
    private static boolean updateDB = true;
    private static boolean firstLoad = true;

    private TextView settings, lookAndFeel, fontSizeTxt, about, appInfo, developerInfo;
    View line1, line2;
    private Spinner fontSizeSpinner;
    private Button appInfoBtn, developerInfoBtn;

    public static float getFontSize(Context context) {
        if (firstLoad) {
            try {
                String mLine = readFromFile(context);
                if (mLine != null) {
                    fontSize = Float.valueOf(mLine);
                }
            } catch (NumberFormatException e) {
                fontSize = 15f; // error so set to standard value
            }
            firstLoad = false;
        }
        return fontSize;
    }

    public static void setFontSize(Context context, float fontSize) {
        writeToFile("" + fontSize, context);
        Settings.fontSize = fontSize;
    }

    public static boolean getUpdateDB() {
        if (updateDB) { // only update on first open
            updateDB = false;
            return true;
        }
        return false;
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
        final Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));
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
        fontSizeSpinner = view.findViewById(R.id.fontSizeSlider);
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

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, new String[]{"Normal", "Large", "Extra Large"}) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(robotoLight);//Typeface for normal view

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(robotoLight);//Typeface for dropdown view
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(adapter);


        fontSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                float size = 15;
                switch (position) {
                    case 0:
                        size = 15;
                        fontSizeSpinner.getLayoutParams().height = 70;
                        fontSizeSpinner.requestLayout();
                        break;
                    case 1:
                        size = 22;
                        fontSizeSpinner.getLayoutParams().height = 90;
                        fontSizeSpinner.requestLayout();
                        break;
                    case 2:
                        size = 30;
                        fontSizeSpinner.getLayoutParams().height = 120;
                        fontSizeSpinner.requestLayout();
                        break;
                }
                Settings.setFontSize(getContext(), size);
                updateTextSize();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int progress = 0;
        switch (Math.round(Settings.getFontSize(getContext()))) {
            case 15:
                progress = 0;
                break;
            case 22:
                progress = 1;
                break;
            case 30: // can only be 30 but default to stop compiler complaining
                progress = 2;
                break;
        }
        fontSizeSpinner.setSelection(progress);

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
        lookAndFeel.setTextSize(Settings.fontSize + 5f);
        fontSizeTxt.setTextSize(Settings.fontSize + 5f);
        about.setTextSize(Settings.fontSize + 5f);
        appInfo.setTextSize(Settings.fontSize + 5f);
        developerInfo.setTextSize(Settings.fontSize + 5f);
        if (fontSizeSpinner.getSelectedView() != null)
            ((TextView) fontSizeSpinner.getSelectedView()).setTextSize(Settings.fontSize + 5f);

    }

    private static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Toast.makeText(context, "Error saving to file. Please check permissions", Toast.LENGTH_LONG);
        }
    }

    private static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error reading file. Please check permissions", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(context, "Error reading file. Please check permissions", Toast.LENGTH_LONG);
        }

        return ret;
    }


    // Sends a broadcast to the FrameworkMain Activity
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("drawer.listener");
        getActivity().sendBroadcast(intent);
    }
}