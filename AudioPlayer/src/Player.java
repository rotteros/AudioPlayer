import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class Player extends PlaybackListener {
    private Thread playingThread;
    private int pauseOnFrame = 0;
    private AdvancedPlayer player;
    private String path;

    public Player(String path) {
        this.path = path;
    }

    public void play() {
        try {
            player = new AdvancedPlayer(new FileInputStream(Paths.get(path).toFile()),  new JavaSoundAudioDevice());
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        player.setPlayBackListener(this);

        if (playingThread != null && !playingThread.isInterrupted()) {
            playingThread.interrupt();
        }

        playingThread = new Thread(() -> {
            try { player.play(pauseOnFrame, Integer.MAX_VALUE); }
            catch (Exception exception) { System.out.println(exception); }
        });

        playingThread.start();
    }

    public void pause() {
        player.stop();
    }

    @Override
    public void playbackFinished(PlaybackEvent playbackEvent) {
        pauseOnFrame = playbackEvent.getFrame();

        super.playbackFinished(playbackEvent);
    }
}
