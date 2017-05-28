package libSource;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by dayan on 27.05.2017.
 */
public class HelpForm extends JFrame{

    JFrame frame;
    private JEditorPane editorPane1;
    private JPanel panel1;
    private JScrollPane editorPaneScrollPane;
    JFXPanel fxPanel;
    WebView wv;

    public HelpForm() {

        fxPanel = new JFXPanel ();

        File f = new File("Docs/Help/Help.html");
        // create JavaFX scene
        com.sun.javafx.application.PlatformImpl.runLater ( new Runnable () {
            @Override
            public void run () {
                wv = new WebView ();
                try {
                    wv.getEngine().load ( f.toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                fxPanel.setScene ( new Scene ( wv, 700, 600 ) );
                frame = new JFrame ( "Справка" );
                frame.add (new JScrollPane (fxPanel));
                frame.setDefaultCloseOperation ( JFrame.HIDE_ON_CLOSE );
                frame.setVisible ( true );
                frame.pack ();
            }
        } );


    }
}
