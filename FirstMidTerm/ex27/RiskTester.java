package mk.kolokvium.updatedEX.ex27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Risk {

    public List<Player> playerList;

    public Risk() {
        this.playerList = new ArrayList<>();
    }

    void processAttacksData(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            String[] attacks = parts[0].split("\\s+");
            String[] defences = parts[1].split("\\s+");
            List<Integer> intAttacks = new ArrayList<>();
            List<Integer> intDefences = new ArrayList<>();
            intAttacks = Arrays.stream(attacks).map(Integer::parseInt).collect(Collectors.toList());
            intDefences = Arrays.stream(defences).map(Integer::parseInt).collect(Collectors.toList());
            playerList.add(new Player(intAttacks, intDefences));
        });

        playerList.forEach(Player::war);

        br.close();
    }
}

class Player {
    public List<Integer> attacks;
    public List<Integer> defences;

    public Player(List<Integer> attacks, List<Integer> defences) {
        this.attacks = new ArrayList<>();
        this.defences = new ArrayList<>();
        this.attacks = attacks;
        this.defences = defences;
        attacks.sort(Comparator.naturalOrder());
        defences.sort(Comparator.naturalOrder());
    }

    public void war() {
        int attacksWon = 0;
        int defencesWon = 0;
        for (int i = 0; i < attacks.size(); i++) {
            if (attacks.get(i) > defences.get(i))
                attacksWon++;
            else
                defencesWon++;
        }
        System.out.println(attacksWon + " " + defencesWon);
    }

}

public class RiskTester {
    public static void main(String[] args) throws IOException {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}
