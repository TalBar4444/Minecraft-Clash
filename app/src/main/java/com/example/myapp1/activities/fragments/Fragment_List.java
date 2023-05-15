package com.example.myapp1.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp1.R;
import com.example.myapp1.interfaces.CallBack_List;
import com.example.myapp1.interfaces.OnItemClickListener;
import com.example.myapp1.logic.Player;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_List extends Fragment implements OnItemClickListener {

    private static final String ARG_PLAYER1 = "player1";
    private static final String ARG_PLAYER2 = "player2";

    private String mPlayer1;
    private String mPlayer2;
    private ArrayList<Player> players;
    private RecyclerView recycleView;
    private CallBack_List callBackList;
    public void setCallBackList(CallBack_List callBackList){this.callBackList = callBackList;}
    public Fragment_List(ArrayList<Player> players) {
        this.players = players;
    }
    public Fragment_List() {
        // Required empty public constructor
    }
    public static Fragment_List newInstance(String param1, String param2) {
        Fragment_List fragment = new Fragment_List();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER1, param1);
        args.putString(ARG_PLAYER2, param2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.recyclerview_list_players);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(),players,this);
        recycleView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(Player player) {
        callBackList.playerClicked(player);
    }
}