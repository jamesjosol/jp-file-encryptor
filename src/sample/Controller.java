package sample;

/**
 * @author James Paul Josol BSIT - 4
 * IAS Final Project
 */

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    Button openWindowOne;
    @FXML
    Button openWindowTwo;
    @FXML
    Button fbBtn;

    public void openFileCrytoWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("fileCryptoWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("File Encryptor/Decryptor");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    private static Stage editorStage; // **Declare static Stage**

    static public Stage getEditorStage() {
        return Controller.editorStage;
    }

    public void openTextEditorWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("textEditorWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            this.editorStage = stage;
            stage.setTitle("Encrypted Text Editor");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void onOpenFb() {
        try {
            Desktop.getDesktop().browse(new URL("https://www.facebook.com/james.josol.31").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Text fileIcon = GlyphsDude.createIcon(FontAwesomeIcons.FILE_CODE_ALT, "20px");
        Text editIcon = GlyphsDude.createIcon(FontAwesomeIcons.EDIT, "20px");
        Text fbIcon = GlyphsDude.createIcon(FontAwesomeIcons.FACEBOOK, "15px");
        editIcon.setId("icon");
        fileIcon.setId("icon");
        fbIcon.setId("icon");
        openWindowOne.setGraphic(fileIcon);
        openWindowTwo.setGraphic(editIcon);
        fbBtn.setGraphic(fbIcon);
    }
}
