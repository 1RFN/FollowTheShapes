
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static Clip backgroundMusic;
    private static Map<String, Clip> soundEffects = new HashMap<>();

    public static void loadSounds() {
        try {
            // Load background music
            backgroundMusic = loadClip("assets/sounds/bg-game.mp3");

            // Load sound effects
            soundEffects.put("1", loadClip("assets/sounds/1.mp3"));
            soundEffects.put("2", loadClip("assets/sounds/2.mp3"));
            soundEffects.put("3", loadClip("assets/sounds/3.mp3"));
            soundEffects.put("4", loadClip("assets/sounds/4.mp3"));
            soundEffects.put("game-over", loadClip("assets/sounds/game-over.mp3"));

        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Clip loadClip(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File audioFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        
        // Let's decode the stream
        AudioFormat baseFormat = audioStream.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
        );
        
        AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
        
        Clip clip = AudioSystem.getClip();
        clip.open(decodedStream);
        return clip;
    }

    public static void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    public static void playSound(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Stop the clip if it's already running
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }
}
