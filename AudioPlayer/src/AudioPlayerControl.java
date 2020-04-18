import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.Paths;

public class AudioPlayerControl extends JPanel {
    private final JButton playBtn = new JButton("Play");
    private final JButton pauseBtn = new JButton("Pause");
    private final JLabel authorLbl = new JLabel("---");
    private String audioFilePath;
    private JPanel panel;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Player player;

    public String getPlayBtnText() {
        return playBtn.getText();
    }

    public void setPlayBtnText(String value) {
        String oldValue = playBtn.getText();

        playBtn.setText(value);

        propertyChangeSupport.firePropertyChange("playBtnText", oldValue, value);
    }

    public String getPauseBtnText() {
        return pauseBtn.getText();
    }

    public void setPauseBtnText(String value) {
        String oldValue = pauseBtn.getText();

        pauseBtn.setText(value);

        propertyChangeSupport.firePropertyChange("pauseBtnText", oldValue, value);
    }

    public Color getPlayBtnColor() {
        return playBtn.getBackground();
    }

    public void setPlayBtnColor(Color value) {
        Color oldValue = playBtn.getBackground();

        playBtn.setBackground(value);

        propertyChangeSupport.firePropertyChange("playBtnColor", oldValue, value);
    }

    public Color getPauseBtnColor() {
        return pauseBtn.getBackground();
    }

    public void setPauseBtnColor(Color value) {
        Color oldValue = pauseBtn.getBackground();

        pauseBtn.setBackground(value);

        propertyChangeSupport.firePropertyChange("pauseBtnColor", oldValue, value);
    }

    public Color getAuthorLabelColor() {
        return authorLbl.getForeground();
    }

    public void setAuthorLabelColor(Color value) {
        Color oldValue = authorLbl.getForeground();

        authorLbl.setForeground(value);

        propertyChangeSupport.firePropertyChange("authorLabelColor", oldValue, value);
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String value) {
        String oldValue = audioFilePath;

        audioFilePath = value;

        player = new Player(audioFilePath);

        Mp3File mp3file = null;

        try {
            mp3file = new Mp3File(Paths.get(audioFilePath).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

        if (mp3file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();

            authorLbl.setText(String.format("%s - %s", id3v1Tag.getArtist(), id3v1Tag.getTitle()));
        }

        propertyChangeSupport.firePropertyChange("audioFilePath", oldValue, value);
    }

    public AudioPlayerControl() {
        setPreferredSize(new Dimension(300, 200));
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        add(playBtn, gridBagConstraints);

        gridBagConstraints.gridx++;

        add(pauseBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy++;
        gridBagConstraints.gridwidth = 2;

        add(authorLbl, gridBagConstraints);

        setSize(200, 200);

        playBtn.addActionListener(e -> player.play());
        pauseBtn.addActionListener(e -> player.pause());
    }
//
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeSupport.addPropertyChangeListener(listener);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeSupport.removePropertyChangeListener(listener);
//    }
}