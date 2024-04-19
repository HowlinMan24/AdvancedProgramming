package mk.kolokvium.updatedEX.ex24;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Risk {

    public List<Player> playerList;

    public Risk() {
        this.playerList = new ArrayList<>();
    }

    int processAttacksData(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(line -> {
            String[] parts = line.split(";");
            String attack = parts[0].replace("\\s+", "");
            String defend = parts[1].replace("\\s+", "");
            playerList.add(new Player(attack, defend));
        });
        br.close();
        int counter = 0;
        for (Player player : playerList) {
            if (player.war() == 1)
                counter++;
        }
        return counter;
    }


}

class Player {
    public List<Integer> attacks;
    public List<Integer> defences;

    public Player(String attacksS, String defencesS) {
        this.attacks = new ArrayList<>();
        this.defences = new ArrayList<>();

        String[] numbers1 = attacksS.split("\\s+");
        for (String s : numbers1) {
            attacks.add(Integer.parseInt(s));
        }

        String[] numbers2 = defencesS.split("\\s+");
        for (String s : numbers2) {
            defences.add(Integer.parseInt(s));
        }
        attacks.sort(Comparator.reverseOrder());
        defences.sort(Comparator.reverseOrder());
//        System.out.println(attacks);
//        System.out.println(defences);
    }

    public int war() {
        for (int i = 0; i < attacks.size(); i++) {
            if (attacks.get(i) <= defences.get(i))
                return 0;
        }
        return 1;
    }
}

public class RiskTester {
    public static void main(String[] args) throws IOException {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}
