//
//  THIS CLASS WAS MADE WITH CHATGPT
//

import java.io.*;
import java.util.*;

public class Leaderboard {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    // Create a leaderboard file if it doesn't exist
    public static void createLeaderboardFile() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Write a name and score to the leaderboard file
    public static void writeScore(String name, int score) {
        try (FileWriter writer = new FileWriter(LEADERBOARD_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(name + " " + score);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read and return the top 10 scores from highest to lowest
    public static List<String> readTopScores() {
        List<String> topScores = new ArrayList<>();
        PriorityQueue<ScoreEntry> scoreQueue = new PriorityQueue<>(10, Collections.reverseOrder());
        System.out.println("\tLEADERBOARD\n~~~~~~~~~~~~~~~~~~~~~~~~~~");

        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scoreQueue.add(new ScoreEntry(name, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10 && !scoreQueue.isEmpty(); i++) {
            ScoreEntry entry = scoreQueue.poll();
            topScores.add(entry.getName() + " " + entry.getScore());
        }

        return topScores;
    }


    static class ScoreEntry implements Comparable<ScoreEntry> {
        private String name;
        private int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(this.score, other.score);
        }
    }
    public static void printTopScores() {
        
            List<String> topScores = readTopScores();
            for (String score : topScores) {
                String[] parts = score.split(" ");
                String name = parts[0];
                int playerScore = Integer.parseInt(parts[1]);
                System.out.println(name + " " + playerScore);
            }
        
    }
}
