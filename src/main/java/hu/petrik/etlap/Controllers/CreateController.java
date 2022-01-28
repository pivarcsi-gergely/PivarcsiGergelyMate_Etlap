package hu.petrik.etlap.Controllers;

import hu.petrik.etlap.Controller;
import hu.petrik.etlap.EtlapApp;
import hu.petrik.etlap.EtlapDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class CreateController extends Controller {
    @FXML
    public TextField TFNev;
    @FXML
    public TextField TFLeiras;
    @FXML
    public Spinner<Integer> SpinnerAr;
    @FXML
    public ChoiceBox<String> CBKategoria;
    @FXML
    public Button ConfirmBtn;

    @FXML
    public void onConfirmClicked(ActionEvent actionEvent) {
        String nev = TFNev.getText();
        String leiras = TFLeiras.getText();
        int ar = 0;
        int kategoriaIndex = CBKategoria.getSelectionModel().getSelectedIndex();
        if (nev.isEmpty()) {
            alert("Név megadása kötelező.");
            return;
        }
        if (leiras.isEmpty()) {
            alert("Leírás megadása kötelező.");
        }
        try {
            ar = SpinnerAr.getValue();
        } catch (NullPointerException e) {
            alert("Ár kiválasztása kötelező.");
            return;
        } catch (Exception e) {
            alert("Az ár csak 1 és 99999 Ft között lehet.");
            return;
        }

        if (kategoriaIndex == -1) {
            alert("Kategória kiválasztása kötelező.");
            return;
        }

        String kategoria = CBKategoria.getValue();

        try {
            EtlapDB etlapDB = new EtlapDB();
            int siker = etlapDB.etelHozzaadasa(nev, leiras, ar, kategoria);
            if (siker == 1) {
                alert("Étel hozzáadása sikeres!");
            } else {
                alert("Étel hozzáadása sikertelen!");
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    }
