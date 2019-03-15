package com.example.b6015413.usbtourteam6.Helper_Classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.b6015413.usbtourteam6.R;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

/**
 * Class to take a room, activity and context and then use the show image class to display it
 */
public class ShowRoom {

    private Activity activity;
    private Context context;
    private Room room;
    public static final int RADIUS = 300;

    public ShowRoom(Activity activity, Context context, Room room) {
        this.activity = activity;
        this.context = context;
        this.room = room;
        openFloorPlan(room);
    }

    // the drawing over an image was thanks to https://stackoverflow.com/questions/18520287/draw-a-circle-on-an-existing-image
    private void openFloorPlan(Room room) {
        Dialog builder = new Dialog(context);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));


        // region get id of level floor plan
        ImageView imageView = new ImageView(context);
        int id = 0;
        switch (room.getLevel()) {
            case 0:
                Toast.makeText(context, "No floor plan exists for ground floor", Toast.LENGTH_LONG).show();
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
        int py = activity.getWindow().getDecorView().getHeight() / 4;

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), id, myOptions);
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
        imageView.setOnTouchListener(new ShowImage(scale, scale, 0, py, imageView));

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

}
