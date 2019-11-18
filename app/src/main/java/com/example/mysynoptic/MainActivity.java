package com.example.mysynoptic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 0;

    private LocationManager locationManager;

    String TAG = "WEATHER";
    TextView tvTemp;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestGeoPermission();

        Intent intent = new Intent(this, WeatherService.class);
        intent.putExtra(WeatherService.EXTRA_COUNT_TO, 5);

        startService(intent);
        //api = SynRetro.createApi();
    }

    @Override
    protected void onDestroy() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }

        super.onDestroy();
    }

    /**
     * Проверяем, есть ли разрешение и запрашиваем его, если нет
     */

    private void checkAndRequestGeoPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            setupLocation();
        }
    }

    /**
     * Подписываемся на обновления гео
     */
    private void setupLocation() {
        // Получаем LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Получаем лучший провайдер
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        Log.v(TAG, "Best provider: " + bestProvider);

        if (bestProvider != null) {
            // Получаем последнюю доступную позицию
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);

            Log.v(TAG, "Last location: " + lastKnownLocation);

            // Подписываемся на обновления
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(
                    bestProvider, // провайдер
                    0, // мин. время
                    0, // мин. расстояние
                    locationListener
            );
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocation();
            } else {
                // Нет гео
                // Попробуем показать ещё раз
                checkAndRequestGeoPermission();
            }
        }
    }

    private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.v(TAG, "Location changed: " + location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.v(TAG, "Status changed: " + provider + ", status: " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.v(TAG, "Provider enabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.v(TAG, "Provider disabled: " + provider);
        }

    };

    /*public void getWeather(View v) throws IOException {
        Double lat = 49.22;
        Double lng = 28.409;
        String units = "metric";
        String key = Consts.API_KEY;

        //Log.d(TAG, "OK");

        // get weather for today
        Call<WeatherPJ> callToday = api.getCurrentWeather(lat, lng, key, units);
        /*callToday.enqueue(new Callback<WeatherPJ>() {
            @Override
            public void onResponse(Call<WeatherPJ> call, Response<WeatherPJ> response) {
                Log.e(TAG, "onResponse");
                WeatherPJ data = response.body();
                Log.d(TAG,response.toString());

                if (response.isSuccessful()) {
                    tvTemp.setText(data.getCityName() + " " + data.getMain());
                    //Glide.with(MainActivity.this).load(data.getIconUrl()).into(tvImage);
                }
            }

           /* @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }

        Response<WeatherPJ> response = callToday.execute();
        WeatherPJ data = response.body();
        if (response.isSuccessful()) {
            tvTemp.setText(data.getCityName() + " " + data.getMain());
            //Glide.with(MainActivity.this).load(data.getIconUrl()).into(tvImage);
        }
        }*/



}
