package hu.petrik.etlap.Controllers;

import hu.petrik.etlap.Controller;
import hu.petrik.etlap.EtlapDB;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CreateKatController extends Controller {
    @FXML
    private Button ConfirmBtn;
    @FXML
    private TextField TFNev;

    @FXML
    public void onConfirmClicked(ActionEvent actionEvent) {
        String nev = TFNev.getText();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező.");
            return;
        }

        try {
            EtlapDB etlapDB = new EtlapDB();
            int siker = etlapDB.katHozzaadasa(nev);
            if (siker == 1) {
                Stage stage = (Stage) ConfirmBtn.getScene().getWindow();
                alertWait("Kategória hozzáadása sikeres!");
                stage.close();

            } else {
                alert("Kategória hozzáadása sikertelen!");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
}
