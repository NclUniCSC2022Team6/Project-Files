package com.example.b6015413.usbtourteam6.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.Adapter.FindARoomAdapter;
import com.example.b6015413.usbtourteam6.Database.DatabaseHelper;
import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import java.util.List;
import java.util.Locale;

public class FindARoom extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Room> items;
    public static final int RADIUS = 300;

    TextView findARoomTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aroom);

        AssetManager am = this.getApplicationContext().getAssets();
        Typeface robotoLight = Typeface.createFromAsset(am, String.format(Locale.UK, "fonts/%s", "Roboto-Light.ttf"));

        findARoomTxt = findViewById(R.id.findARoomTitle);

        findARoomTxt.setTypeface(robotoLight);

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.findARoomRV);
        //set the layout of the recycler view as the
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //populate the recycler view with this class as context and items as data
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        items = dbHelper.getAllRooms();
        recyclerView.setAdapter(new FindARoomAdapter(this, items));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //TODO use the query to search your data somehow
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_btn:
                startActivity(new Intent(this, SearchTutor.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // the drawing over an image was thanks to https://stackoverflow.com/questions/18520287/draw-a-circle-on-an-existing-image
    public void openFloorPlan(Room room) {
        Dialog builder = new Dialog(this);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // region get id of level floor plan
        ImageView imageView = new ImageView(this);
        int id = 0;
        switch (room.getLevel()) {
            case 0:
                Toast.makeText(this, "No floor plan exists for ground floor", Toast.LENGTH_LONG).show();
                return;
            case 1:
                id = R.drawable.floor_1_room_numbers;
                break;
            case 2:
                id = R.drawable.floor_2_room_numbers;
                break;
            case 3:
                id = R.drawable.floor_3_room_numbers;
                break;
            case 4:
                id = R.drawable.floor_4_room_numbers;
                break;
            case 5:
                id = R.drawable.floor_5_room_numbers;
                break;
            case 6:
                id = R.drawable.floor_6_room_numbers;
                break;
        }
        // endregion

        // variables to zoom out and move floor plan to centre
        float scale = 0.09f;
        int py = this.getWindow().getDecorView().getHeight() / 4;

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);

        int[] coords = room.getCoords();

        canvas.drawCircle(coords[0], coords[1], RADIUS, paint);

        imageView.setAdjustViewBounds(false);
        imageView.setImageBitmap(mutableBitmap);

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setOnTouchListener(new ShowImage(scale, scale, 0, py));

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale, 0, py);
        imageView.setImageMatrix(matrix);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }


}
