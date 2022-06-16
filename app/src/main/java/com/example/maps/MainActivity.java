package com.example.maps;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import java.io.ObjectStreamField;

public class MainActivity extends AppCompatActivity{
    MapView mapview;
    UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("6140d8d1-12ca-426b-b76e-65913c54854c");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapview = (MapView) findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0), null);
        mapview.getMap().getMapObjects().addPlacemark(new Point(55.751574, 37.573856),
                ImageProvider.fromResource(MainActivity.this, R.drawable.icon_map));
        onLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    public void onLocation() {
        UserLocationLayer userLocationLayer = mapview.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener((UserLocationObjectListener) this);
}

    //@Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.5)),
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.83)));
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(this, R.drawable.icon_user));
        userLocationView.getPin().setIcon(ImageProvider.fromResource(this, R.drawable.icon_user));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
    }

    //@Override
    public void onObjectRemove(@NonNull UserLocationView userLocationView) {
    }

    //@Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull
            ObjectEvent objectEvent) {
    }
}