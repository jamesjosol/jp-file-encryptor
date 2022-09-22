package sample;

/**
 * @author James Paul Josol BSIT - 4
 * IAS Final Project
 */

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeKeyController implements Initializable {

    @FXML
    Label changekeylabel;

    @FXML
    PasswordField keyField;
    @FXML
    CheckBox showKey;
    @FXML
    Button doneBtn;

    private Tooltip toolTip;


    public void onShowKey(ActionEvent ev) {
        if(showKey.isSelected()) {
            showPassword();
        }else {
            hidePassword();
        }
    }

    private void showPassword(){
        Stage stage = FileCryptoWindowController.getChangeKeyStage();
        Point2D p = keyField.localToScene(keyField.getBoundsInLocal().getMaxX(), keyField.getBoundsInLocal().getMaxY());
        toolTip.setText(keyField.getText());
        toolTip.show(keyField,
                p.getX() + stage.getScene().getX() + stage.getX(),
                p.getY() + stage.getScene().getY() + stage.getY());
    }

    private void hidePassword(){
        toolTip.setText("");
        toolTip.hide();
    }

    public void onKeyTyped(KeyEvent ke) {
        if(showKey.isSelected()) {
            showPassword();
        }else {
            hidePassword();
        }
    }

    public void onDone(ActionEvent ev) {
        FileCryptoWindowController.setKey(keyField.getText());
        Stage stage = (Stage) doneBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Text keyIcon = GlyphsDude.createIcon(FontAwesomeIcons.KEY, "20px");
        keyIcon.setId("icon");
        changekeylabel.setGraphic(keyIcon);

        keyField.setText(FileCryptoWindowController.getKey());

        toolTip = new Tooltip();
        toolTip.setShowDelay(Duration.ZERO);
        toolTip.setAutoHide(false);
        toolTip.setMinWidth(50);

        keyField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if(keyField.getText().length() > 16) {
                    String s = keyField.getText().substring(0, 16);
                    keyField.setText(s);
                }
                if(keyField.getText().length() != 16) {
                    doneBtn.setDisable(true);
                }else {
                    doneBtn.setDisable(false);
                }
            }
        });
    }
}
