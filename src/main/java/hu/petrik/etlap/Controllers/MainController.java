package hu.petrik.etlap.Controllers;

import hu.petrik.etlap.Controller;
import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.EtlapDB;
import hu.petrik.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class MainController extends Controller {
    @FXML
    private Button AddBtn;
    @FXML
    private Button DeleteBtn;

    @FXML
    private Button SzazalekEmeloBtn;
    @FXML
    private Spinner<Integer> szazalekEmeloSpinner;
    @FXML
    private Button FtEmeloBtn;
    @FXML
    private Spinner<Integer> FtEmeloSpinner;

    @FXML
    private TableView<Etlap> EtlapTable;
    @FXML
    private TableColumn<Etlap, String> colNev;
    @FXML
    private TableColumn<Etlap, Integer> colAr;
    @FXML
    private TableColumn<Etlap, String> colKategoria;

    @FXML
    public TableView<Kategoria> KategoriaTable;
    @FXML
    public TableColumn<Kategoria, String> colKategoriaNev;

    @FXML
    private Label leirasLbl;

    private EtlapDB db;
    private List<Etlap> etlapList;
    private HashSet<Kategoria> kategoriaHashSet;

    public void initialize() {
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));

        colKategoriaNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        try {
            db = new EtlapDB();
            etlapListaFeltolt();
            kategoriaListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void etlapListaFeltolt() {
        try {
            etlapList = db.getEtlap();
            EtlapTable.getItems().clear();
            for (Etlap etlap : etlapList) {
                EtlapTable.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void kategoriaListaFeltolt() {
        try {
            kategoriaHashSet = db.getKategoria();
            KategoriaTable.getItems().clear();
            for (Kategoria kategoria: kategoriaHashSet) {
                KategoriaTable.getItems().add(kategoria);
            }

        }
        catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void onRowClick(MouseEvent mouseEvent) {
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("Válassz ki egy sort");
        } else {
            leirasLbl.setText("Leírás: " + etlapList.get(selectedIndex).getLeiras());
        }
    }

    public void onSzazClicked(ActionEvent actionEvent) throws SQLException {
        int szazalek = 0;
        try {
            szazalek = szazalekEmeloSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Nem lehet üres értékkel árat növelni");
            return;
        } catch (Exception e) {
            alert("0 vagy annál kisebb értékkel nem tudod növelni az árat");
            return;
        }
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex() + 1;
        db.etelNovelSzazalek(szazalek, selectedIndex);
        etlapListaFeltolt();
    }

    public void onFtClicked(ActionEvent actionEvent) throws SQLException {
        int ft = 0;
        try {
            ft = FtEmeloSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Nem lehet üres értékkel árat növelni");
            return;
        } catch (Exception e) {
            alert("0 vagy annál kisebb értékkel nem tudod növelni az árat");
            return;
        }
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex() + 1;
        db.etelNovelForint(ft, selectedIndex);
        etlapListaFeltolt();
    }

    public void onDeleteClicked(ActionEvent actionEvent) {
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("Nem tudod a semmit kitörölni");
            return;
        }

        Etlap torlendoEtel = EtlapTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan törölni szeretnéd ezt az ételt: " + torlendoEtel.getNev() + "?")) {
            return;
        } else {
            try {
                db.etelTorlese(torlendoEtel.getId());
                alert("Sikeres törlés");
                etlapListaFeltolt();
            } catch (SQLException e) {
                alert("Sikertelen törlés");
            }
        }
    }

    public void onInsertEtelClicked(ActionEvent actionEvent) {
        try {
            Controller insertAblak = ujAblak("create_etlap.fxml", "Étel hozzáadása", 300, 200);
            insertAblak.getStage().setOnCloseRequest(windowEvent -> etlapListaFeltolt());
            insertAblak.getStage().show();
        }
        catch (Exception e) {
            hibaKiir(e);
        }
    }
}