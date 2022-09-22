package sample;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author James Paul Josol BSIT - 4
 * IAS Final Project
 */

public class FileCryptoWindowController implements Initializable {

    @FXML
    Label filePathLabel;
    @FXML
    Label fileNameLabel;
    @FXML
    Label fileTypeLabel;
    @FXML
    Label fileSizeLabel;

    @FXML
    Button openFileBtn;
    @FXML
    Button removeBtn;
    @FXML
    Button encryptBtn;
    @FXML
    Button decryptBtn;
    @FXML
    Button changeKeyBtn;

    private byte[] fileData;
    private File file;
    private static String key = "defaultkey123456";
    final FileChooser fileChooser = new FileChooser();

    public void openFile(ActionEvent ev) {
        Stage stage = Main.getPrimaryStage();
        File selectedfile = fileChooser.showOpenDialog(stage);
        if (selectedfile != null) {
            this.file = selectedfile;
            filePathLabel.setText(file.getAbsolutePath());
            fileNameLabel.setText("Name : " + file.getName());
            fileTypeLabel.setText("Type : " + file.getName().substring(file.getName().lastIndexOf(".") +1));
            fileSizeLabel.setText("Size : " + (file.length() <  (1024 * 1024) ? (file.length() / 1024)  + " KB" : (file.length() / (1024 * 1024))  + " MB"));
            System.out.println(file.getName());

            try {
                FileInputStream fis = new FileInputStream(file);
                this.fileData = fis.readAllBytes();
                fis.close();

            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeFile(ActionEvent ev) {
        this.file = null;
        filePathLabel.setText("");
        fileNameLabel.setText("Name : ");
        fileTypeLabel.setText("Type : ");
        fileSizeLabel.setText("Size : ");
    }


    public void onEncryptFile(ActionEvent ev) {
        if(file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setHeaderText(null);
            alert.setContentText("No file selected! \nSelect a file first.");
            alert.showAndWait();
            return;
        }

        try {
            doCrypto(Cipher.ENCRYPT_MODE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success.");
            alert.setHeaderText(null);
            alert.setContentText("Encryption finished \nFile Encrypted.");
            alert.showAndWait();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onDecryptFile(ActionEvent ev) {
        if(file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setHeaderText(null);
            alert.setContentText("No file selected! \nSelect a file first.");
            alert.showAndWait();
            return;
        }

        try {
            doCrypto(Cipher.DECRYPT_MODE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success.");
            alert.setHeaderText(null);
            alert.setContentText("Decryption finished \nFile Decrypted.");
            alert.showAndWait();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doCrypto(int cipherMode) {
        try {
            Key key = new SecretKeySpec(this.key.getBytes(), "AES");
            Cipher c = Cipher.getInstance("AES");

            c.init(cipherMode, key);

            byte[] outputBytes = c.doFinal(this.fileData); // encoded/decoded data

            this.fileData = outputBytes;

            FileOutputStream fos = new FileOutputStream(this.file);

            fos.write(outputBytes);
            fos.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Text openFileIcon = GlyphsDude.createIcon(FontAwesomeIcons.FOLDER_OPEN, "15px");
        Text deleteIcon = GlyphsDude.createIcon(FontAwesomeIcons.REMOVE, "15px");
        Text encryptIcon = GlyphsDude.createIcon(FontAwesomeIcons.LOCK, "20px");
        Text decryptIcon = GlyphsDude.createIcon(FontAwesomeIcons.UNLOCK, "20px");
        Text changeIcon = GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "15px");
        openFileIcon.setId("icon");
        deleteIcon.setId("icon");
        encryptIcon.setId("icon");
        decryptIcon.setId("icon");
        changeIcon.setId("icon");
        openFileBtn.setGraphic(openFileIcon);
        removeBtn.setGraphic(deleteIcon);
        encryptBtn.setGraphic(encryptIcon);
        decryptBtn.setGraphic(decryptIcon);
        changeKeyBtn.setGraphic(changeIcon);
    }

    public static void setKey(String newKey) {
        key = newKey;
    }

    public static String getKey() {
        return key;
    }

    private static Stage changeKeyStage; // **Declare static Stage**

    static public Stage getChangeKeyStage() {
        return FileCryptoWindowController.changeKeyStage;
    }

    public void onChangeKey(ActionEvent ev) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("changeKey.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            this.changeKeyStage = stage;
            stage.setTitle("Change Key");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initOwner(
                    ((Node)ev.getSource()).getScene().getWindow() );
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}
