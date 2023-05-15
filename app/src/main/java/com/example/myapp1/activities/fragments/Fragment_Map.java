package com.example.myapp1.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp1.R;
import com.example.myapp1.MSPV;
import com.example.myapp1.logic.Player;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PLAYER1 = "player1";
    private static final String ARG_PLAYER2 = "player2";

    private String mPlayer1;
    private String mPlayer2;
    private GoogleMap googleMap;
    private ArrayList<Player> playerList;

    public  Fragment_Map(ArrayList<Player> topTen){
        this.playerList = topTen;
    }
    public  Fragment_Map(){}

    public static Fragment_Map newInstance(String player1, String player2) {
        Fragment_Map fragment = new Fragment_Map(MSPV.getMe().readTopTenPlayers().getPlayers());
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER1, player1);
        args.putString(ARG_PLAYER2, player2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayer1 = getArguments().getString(ARG_PLAYER1);
            mPlayer2 = getArguments().getString(ARG_PLAYER2);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng centerMapLocation = new LatLng(0,0);
        setCameraPosition(0,centerMapLocation);
        for (Player player: playerList){
            String name = player.getPlayerName();
            LatLng myLocation = new LatLng(player.getLat(),player.getLng());
            addMarker(myLocation, name);
        }
    }
    public void showOnMap(Player player) {
        googleMap.clear();
        String name = player.getPlayerName();
        LatLng myLocation = new LatLng(player.getLat(),player.getLng());
        addMarker(myLocation, name);
        setCameraPosition(10,myLocation);
    }
    private void addMarker(LatLng myLocation, String name) {
        googleMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .title(name));
    }
    private void setCameraPosition(int zoom, LatLng myLocation) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myLocation)
                .zoom(zoom)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}