import java.io.IOException;
import java.util.Iterator;
import javax.sound.sampled.*;
import java.io.File;

public class FirstTo100 {
    public static void main(String[] args) {
        // creating a CircularLinkedList of Player and instantiating Players
        CircularLinkedList<Player> playerList = new CircularLinkedList<>(new Player("Maya"),
                new Player("Peter"), new Player("Rain"), new Player("Caeden"));

        // creating the playerList Iterator
        Iterator<Player> playerIterator = playerList.iterator();

        // loading sound effects
        // credit to Peter for inspiring me to use Among Us sounds
        SoundPlayer gameBegin = new SoundPlayer("sounds/among-us-role-reveal.aiff");
        SoundPlayer gameNewRound = new SoundPlayer("sounds/among-us-voted-out-sound-effect.aiff");
        SoundPlayer gameWin = new SoundPlayer("sounds/among-us-crewmate-victory-sound.aiff");

        // generating a random number that will be the number of times playerList.iterator() is called
        int randomizePlayer = (int) ((Math.random() * 100000) % (playerList.size() - 1));

        // move to the next Player a random number of times
        for (int i = 0; i < randomizePlayer; i++) {
            playerIterator.next();
        }

        // flag for ending the game
        boolean gameOver = false;

        // keeping track of the highest score in the game
        int highScore = 0;

        // used to start a new round in the game
        // new round starts when all players have taken a turn
        int turnsTaken = playerList.size();

        // message and sound to start the game
        System.out.println("ඞ LET THE GAME BEGIN! ඞ");
        gameBegin.play();

        // delay game by 5 seconds to let sound play
        sleep(5);

        // game loop
        // while the game isn't over
        while (!gameOver) {
            // start a new round when the number of turns equals the number of Players in the game
            if (turnsTaken == playerList.size()) {
                System.out.println();
                System.out.println("ඞ New Round Starting ඞ");

                // play a sound for the start of a new round
                gameNewRound.play();

                // reset turnsTaken after a new round begins
                turnsTaken = 0;
            }

            // using playerIterator to go through all the Players in playerList
            Player player = playerIterator.next();

            // rolling the dice 2 times and collecting their results
            int roll1 = roll();
            int roll2 = roll();

            // display text of a Player's rolls and score each round
            String gameText = player.getName() + " rolls a " + roll1 + " and a " + roll2 +
                    ", score now totalling " + player.addRoundTotal(roll1 + roll2);

            // if a Player's score is greater than the highest score
            if (player.getScore() > highScore) {
                // make highScore equal to the Player's score (this is the new highest score)
                highScore = player.getScore();

                // add a message that the Player has the new high score
                System.out.println(gameText + "...new high score!");
            } else {
                // print regular text
                System.out.println(gameText);
            }

            // delay game by 2 seconds to let new round sound play
            sleep(2);

            // increase counter for number of turns taken after a Player rolls
            turnsTaken++;

            // if a Player's score is greater than or equal to 100
            if (player.getScore() >= 100) {
                // play sound for winning the game
                gameWin.play();

                // delay game by 7 seconds to let winning game sound play
                sleep(7);

                // set flag for ending the game to true and display message for the winning Player
                gameOver = true;
                System.out.println("The winner is " + player.getName() + " with a score of " + player.getScore() +
                        "!");
            }
        }
    }

    public static int roll() {
        // generate a random number between 1 and 6 to represent a dice roll
        return (int) (((Math.random() * 100000) % 6) + 1);
    }

    public static void sleep(int seconds) {
        try {
            // delay the program by a specified number of seconds
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            // do nothing - we don't care
        }
    }

    private static class SoundPlayer {
        // instance variable
        // used to store a sound clip
        private Clip clip;

        // constructor
        public SoundPlayer(String filePath) {
            try {
                // opening an audio input stream
                File soundFile = new File(filePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

                // loading sound file into the sound clip
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (UnsupportedAudioFileException e) {
                // do nothing - game works without sound
            } catch (IOException e) {
                // do nothing - game works without sound
            } catch (LineUnavailableException e) {
                // do nothing - game works without sound
            }
        }

        // methods
        // plays sound
        public void play() {
            if (clip != null) {
                // rewind sound clip to the beginning
                clip.setFramePosition(0);

                // start playing
                clip.start();
            }
        }
    }
}
