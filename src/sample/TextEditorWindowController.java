package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
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

/**
 * @author James Paul Josol BSIT - 4
 * IAS Final Project
 */

public class TextEditorWindowController implements Initializable {

    @FXML
    TextArea textEditor;
    final FileChooser fileChooser = new FileChooser();
    private File file;
    private String key = "1234567890123456";

    public void onNew() {
        this.file = null;
        textEditor.setText(null);
        Controller.getEditorStage().setTitle("Encrypted Text Editor");
    }

    public void onOpenFile() {
        Stage stage = Main.getPrimaryStage();
        File selectedfile = fileChooser.showOpenDialog(stage);

        try {

            if(file == null) {

                if(selectedfile != null) {
                    file = selectedfile;

                    Controller.getEditorStage().setTitle(Controller.getEditorStage().getTitle() + " - " + file.getName());

                    byte[] inputBytes = new byte[(int) file.length()];
                    try(FileInputStream inputStream = new FileInputStream(file)) {
                        inputStream.read(inputBytes);
                        textEditor.setText(decrypt(inputBytes, key));
                    }
                }

            }else {
                file = new File(selectedfile.getPath());

                Controller.getEditorStage().setTitle("Encrypted Text Editor - " + file.getName());

                byte[] inputBytes = new byte[(int) file.length()];
                try(FileInputStream inputStream = new FileInputStream(file)) {
                    inputStream.read(inputBytes);
                    textEditor.setText(decrypt(inputBytes, key));
                }
            }

        }catch(IOException ex) {
            ex.printStackTrace();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onSaveFile() {
        try {
            if(file==null) {
                Stage stage = Main.getPrimaryStage();
                File selectedfile = fileChooser.showOpenDialog(stage);

                if(selectedfile != null) {
                    file = selectedfile;
                    File fileOut = new File(file.getPath());
                    FileOutputStream outputStream = new FileOutputStream(fileOut);
                    outputStream.write(encrypt(textEditor.getText(),key));
                }

            }else {
                File fileOut = new File(file.getPath());
                FileOutputStream outputStream = new FileOutputStream(fileOut);
                outputStream.write(encrypt(textEditor.getText(),key));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Saved.");
                alert.setHeaderText(null);
                alert.setContentText("Saved File " + file.getName() + ".");
                alert.showAndWait();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onClose() {
        Stage stage = Controller.getEditorStage();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textEditor.setWrapText(true);
    }


    public static byte[] encrypt(String str, String keyStr) throws Exception {

        Key key = new SecretKeySpec(keyStr.getBytes(), "AES");

        Cipher c = Cipher.getInstance("AES");

        c.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = c.doFinal(str.getBytes("UTF8"));

        return encrypted;
    }


    public static String decrypt(byte[] data, String keyStr) throws Exception {

        Key key = new SecretKeySpec(keyStr.getBytes(), "AES");

        Cipher c = Cipher.getInstance("AES");

        c.init(Cipher.DECRYPT_MODE, key);

        byte[] decrypted = c.doFinal(data);

        return new String(decrypted);
    }
}
