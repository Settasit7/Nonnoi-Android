package com.settasit.nonnoi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.PermissionToken;
import java.lang.Math;
import java.util.Arrays;

public class MainActivity<ChecklistOptionActivity> extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    static boolean isPermissionGranted;
    static double radius_ = 0;
    static double x1 = 0;
    static double y1 = 0;
    static int i = 0;
    static int j = 0;
    static int k = 0;
    static Intent serviceIntent;
    double x0 = 0;
    double y0 = 0;
    int counter0 = 0;
    int counter1 = 0;
    int red = 0;
    EditText radiusInput;
    GoogleMap map;
    LatLng hold;
    LocationManager locationManager;
    String radius;
    TextView set, tick, tick_, song, circle, circle_, vibration, headphone_, num1_, num2_, num3_, num4_, num5_, num6_, num7_, num8_, num9_, num10_1, num10_0, tvNormal, tvSatellite, earth, earth_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        checkPermission();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "***************************************");
        }
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                map.clear();
                final LatLng latLng = place.getLatLng();
                hold = latLng;
                map.addMarker(new MarkerOptions().position(latLng).draggable(true));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                x1 = latLng.latitude;
                y1 = latLng.longitude;
                i = i + 1;
            }
            @Override
            public void onError(@NonNull Status status) {}
        });
        radiusInput = findViewById(R.id.radius);
        set = findViewById(R.id.set);
        tick = findViewById(R.id.tick);
        tick_ = findViewById(R.id.tick_);
        tick.setVisibility(View.VISIBLE);
        tick_.setVisibility(View.GONE);
        radiusInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                j = j + 1;
                if (x1 != 0 || y1 != 0) {
                    try {
                        map.clear();
                        map.addMarker(new MarkerOptions().position(hold).draggable(true));
                    } catch (Exception ignored) {}
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (red == 0 && counter0 != 0) {
                        if (counter1 == 0) {
                            counter1 = 1;
                        } else {
                            tick.setVisibility(View.GONE);
                            tick_.setVisibility(View.VISIBLE);
                        }
                        radiusInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        radiusInput.clearFocus();
                        radius = radiusInput.getText().toString();
                        radius_ = Double.parseDouble(radius);
                        i = 0;
                        j = 0;
                        map.clear();
                        map.addMarker(new MarkerOptions().position(hold).draggable(true));
                        if (radius_ != 0 && (x1 != 0 || y1 != 0) && (x0 != 0 || y0 != 0)) {
                            map.addCircle(new CircleOptions()
                                    .center(new LatLng(x1, y1))
                                    .radius(radius_ * 1000)
                                    .strokeWidth(0)
                                    .fillColor(Color.argb(128, 255, 255, 255)));
                        }
                    } else {
                        map.clear();
                        map.addMarker(new MarkerOptions().position(hold).draggable(true));
                        i = -1;
                        j = -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    radius_ = 0;
                }
            }
        });
        set.performClick();
        k = 0;
        song = findViewById(R.id.song);
        circle = findViewById(R.id.circle);
        circle_ = findViewById(R.id.circle_);
        vibration = findViewById(R.id.vibration);
        headphone_ = findViewById(R.id.headphone_);
        num1_ = findViewById(R.id.num1_);
        num2_ = findViewById(R.id.num2_);
        num3_ = findViewById(R.id.num3_);
        num4_ = findViewById(R.id.num4_);
        num5_ = findViewById(R.id.num5_);
        num6_ = findViewById(R.id.num6_);
        num7_ = findViewById(R.id.num7_);
        num8_ = findViewById(R.id.num8_);
        num9_ = findViewById(R.id.num9_);
        num10_1 = findViewById(R.id.num10_1);
        num10_0 = findViewById(R.id.num10_0);
        circle.setVisibility(View.VISIBLE);
        circle_.setVisibility(View.GONE);
        vibration.setVisibility(View.VISIBLE);
        headphone_.setVisibility(View.GONE);
        num1_.setVisibility(View.GONE);
        num2_.setVisibility(View.GONE);
        num3_.setVisibility(View.GONE);
        num4_.setVisibility(View.GONE);
        num5_.setVisibility(View.GONE);
        num6_.setVisibility(View.GONE);
        num7_.setVisibility(View.GONE);
        num8_.setVisibility(View.GONE);
        num9_.setVisibility(View.GONE);
        num10_1.setVisibility(View.GONE);
        num10_0.setVisibility(View.GONE);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k = k + 1;
                k = k % 11;
                if (k == 0) {
                    num10_1.setVisibility(View.GONE);
                    num10_0.setVisibility(View.GONE);
                    circle.setVisibility(View.VISIBLE);
                    circle_.setVisibility(View.GONE);
                    vibration.setVisibility(View.VISIBLE);
                    headphone_.setVisibility(View.GONE);
                } else if (k == 1) {
                    circle.setVisibility(View.GONE);
                    circle_.setVisibility(View.VISIBLE);
                    vibration.setVisibility(View.GONE);
                    headphone_.setVisibility(View.VISIBLE);
                    num1_.setVisibility(View.VISIBLE);
                } else if (k == 2) {
                    num1_.setVisibility(View.GONE);
                    num2_.setVisibility(View.VISIBLE);
                } else if (k == 3) {
                    num2_.setVisibility(View.GONE);
                    num3_.setVisibility(View.VISIBLE);
                } else if (k == 4) {
                    num3_.setVisibility(View.GONE);
                    num4_.setVisibility(View.VISIBLE);
                } else if (k == 5) {
                    num4_.setVisibility(View.GONE);
                    num5_.setVisibility(View.VISIBLE);
                } else if (k == 6) {
                    num5_.setVisibility(View.GONE);
                    num6_.setVisibility(View.VISIBLE);
                } else if (k == 7) {
                    num6_.setVisibility(View.GONE);
                    num7_.setVisibility(View.VISIBLE);
                } else if (k == 8) {
                    num7_.setVisibility(View.GONE);
                    num8_.setVisibility(View.VISIBLE);
                } else if (k == 9) {
                    num8_.setVisibility(View.GONE);
                    num9_.setVisibility(View.VISIBLE);
                } else if (k == 10) {
                    num9_.setVisibility(View.GONE);
                    num10_1.setVisibility(View.VISIBLE);
                    num10_0.setVisibility(View.VISIBLE);
                }
            }
        });
        song.performClick();
        tvNormal = findViewById(R.id.tv_normal);
        tvSatellite = findViewById(R.id.tv_satellite);
        earth = findViewById(R.id.earth);
        earth_ = findViewById(R.id.earth_);
        earth.setVisibility(View.GONE);
        earth_.setVisibility(View.VISIBLE);
        tvNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                tvNormal.setBackgroundResource(R.drawable.bg_button);
                tvNormal.setVisibility(View.GONE);
                tvSatellite.setVisibility(View.VISIBLE);
                earth.setVisibility(View.GONE);
                earth_.setVisibility(View.VISIBLE);
            }
        });
        tvSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                tvSatellite.setBackgroundResource(R.drawable.bg_button);
                tvNormal.setVisibility(View.VISIBLE);
                tvSatellite.setVisibility(View.GONE);
                earth.setVisibility(View.VISIBLE);
                earth_.setVisibility(View.GONE);
            }
        });
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                LocationUpdate();
                serviceIntent = new Intent(MainActivity.this, ForegroundService.class);
                startService(serviceIntent);
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {}
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {}
        }).check();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));
        map = googleMap;

        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(13.7649, 100.5383));
        map.moveCamera(point);

        map.setOnMarkerDragListener(this);
    }
    @SuppressLint("MissingPermission")
    private void LocationUpdate() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }
    @SuppressLint("MissingPermission")
    public void onLocationChanged(@NonNull Location location) {
        if (i == 0 && j == 0 && radius_ != 0 && (x1 != 0 || y1 != 0)) {
            tick.setVisibility(View.GONE);
            tick_.setVisibility(View.VISIBLE);
            red = 1;
        } else {
            tick.setVisibility(View.VISIBLE);
            tick_.setVisibility(View.GONE);
            red = 0;
        }
        if (counter0 == 0) {
            LatLng latLng_ = new LatLng(location.getLatitude(), location.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_, 16));
            counter0 = 1;
        }
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        x0 = location.getLatitude();
        y0 = location.getLongitude();
        if (i >= 0 && j >= 0 && radius_ != 0 && (x1 != 0 || y1 != 0) && 6371.01 * Math.acos((Math.sin((x1 * 3.14159) / 180) * Math.sin((x0 * 3.14159) / 180)) + (Math.cos((x1 * 3.14159) / 180) * Math.cos((x0 * 3.14159) / 180) * Math.cos(((y1 * 3.14159) / 180) - ((y0 * 3.14159) / 180)))) <= radius_) {
            i = i + 1;
            j = j + 1;
        }
    }
    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {}
    @Override
    public void onMarkerDrag(@NonNull Marker marker) {}
    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        LatLng latLngDrag = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngDrag, 16));
        x1 = marker.getPosition().latitude;
        y1 = marker.getPosition().longitude;
        i = i + 1;
        map.clear();
        hold = latLngDrag;
        map.addMarker(new MarkerOptions().position(hold).draggable(true));
    }
}