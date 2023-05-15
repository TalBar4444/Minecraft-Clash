package com.example.myapp1.logic;
import java.util.Comparator;

public class PlayersComparator implements Comparator<Player> {
    @Override
        public int compare(Player firstPlayer, Player secondPlayer) {
            if(firstPlayer.getScore() > secondPlayer.getScore())
                return -1;
            else if (firstPlayer.getScore() < secondPlayer.getScore())
                return 1;
            return 0;
        }

}
