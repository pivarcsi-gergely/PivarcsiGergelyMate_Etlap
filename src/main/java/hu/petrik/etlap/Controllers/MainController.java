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

import static hu.petrik.etlap.Kategoria.DEFAULT_KATEGORIA;

public class MainController extends Controller {
    @FXML
    private Button AddBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private ChoiceBox<Kategoria> CBSzuro;

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
    private TableColumn<Etlap, Kategoria> colKategoria;

    @FXML
    private TableView<Kategoria> KategoriaTable;
    @FXML
    private TableColumn<Kategoria, String> colKategoriaNev;
    @FXML
    private Button KatHozzaadBtn;
    @FXML
    private Button KatTorlesBtn;

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
            Kategoria.initialize(db);
            kategoriaHashSet = Kategoria.getKategoriaHashSet();
            CBSzuro.getItems().addAll(kategoriaHashSet);
            kategoriaListaFeltolt(db);
            etlapListaFeltolt(db);
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void etlapListaFeltolt(EtlapDB etlapDB) {
        try {
            etlapList = etlapDB.getEtlap();
            EtlapTable.getItems().clear();
            for (Etlap etlap : etlapList) {
                EtlapTable.getItems().add(etlap);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void kategoriaListaFeltolt(EtlapDB etlapDB) {
        KategoriaTable.getItems().clear();
        for (Kategoria kategoria : kategoriaHashSet) {
            KategoriaTable.getItems().add(kategoria);
        }
    }

    public void onRowClick(MouseEvent mouseEvent) {
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("V??lassz ki egy sort");
        } else {
            leirasLbl.setText("Le??r??s: " + etlapList.get(selectedIndex).getLeiras());
        }
    }

    public void onSzazClicked(ActionEvent actionEvent) throws SQLException {
        int szazalek;
        try {
            szazalek = szazalekEmeloSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Nem lehet ??res ??rt??kkel ??rat n??velni");
            return;
        } catch (Exception e) {
            alert("0 vagy ann??l kisebb ??rt??kkel nem tudod n??velni az ??rat");
            return;
        }
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex() + 1;
        db.etelNovelSzazalek(szazalek, selectedIndex);
        etlapListaFeltolt(db);
    }

    public void onFtClicked(ActionEvent actionEvent) throws SQLException {
        int ft;
        try {
            ft = FtEmeloSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Nem lehet ??res ??rt??kkel ??rat n??velni");
            return;
        } catch (Exception e) {
            alert("0 vagy ann??l kisebb ??rt??kkel nem tudod n??velni az ??rat");
            return;
        }
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex();
        db.etelNovelForint(ft, selectedIndex);
        etlapListaFeltolt(db);
    }

    public void onDeleteClicked(ActionEvent actionEvent) {
        int selectedIndex = EtlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("Nem tudod a semmit kit??r??lni");
            return;
        }

        Etlap torlendoEtel = EtlapTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan t??r??lni szeretn??d ezt az ??telt: " + torlendoEtel.getNev() + "?")) {
            return;
        } else {
            try {
                db.etelTorlese(torlendoEtel.getId());
                alert("Sikeres t??rl??s!");
                etlapListaFeltolt(db);
            } catch (SQLException e) {
                alert("Sikertelen t??rl??s!");
            }
        }
    }

    public void onInsertEtelClicked(ActionEvent actionEvent) {
        try {
            Controller insertAblak = ujAblak("create_etlap.fxml", "??tel hozz??ad??sa", 300, 200);
            insertAblak.getStage().setOnCloseRequest(windowEvent -> etlapListaFeltolt(db));
            insertAblak.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    public void onKatHozzaadClick(ActionEvent actionEvent) {
        try {
            Controller insertAblak = ujAblak("create_kategoria.fxml", "Kateg??ria hozz??ad??sa", 300, 200);
            insertAblak.getStage().setOnCloseRequest(windowEvent -> kategoriaListaFeltolt(db));
            insertAblak.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    public void onKatTorlesClick(ActionEvent actionEvent) {
        int selectedIndex = KategoriaTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("Nem tudod a semmit kit??r??lni.");
            return;
        }

        Kategoria torlendoKat = KategoriaTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztosan t??r??lni szeretn??d ezt a kateg??ri??t: " + torlendoKat.getNev() + "?")) {
            return;
        } else {
            try {
                db.katTorlese(torlendoKat.getId());
                alert("Sikeres t??rl??s!");
                kategoriaListaFeltolt(db);
            } catch (SQLException e) {
                alert("Sikertelen t??rl??s!");
            }
        }
    }

    public void onSzures(ActionEvent actionEvent) {
        Kategoria kivalaszottKat = Kategoria.fromNev(CBSzuro.getSelectionModel().getSelectedItem().toString());
        if (kivalaszottKat.getId() == 0) {
            etlapListaFeltolt(db);
        } else {
            try {
                etlapList = db.szures(kivalaszottKat.getId());
                EtlapTable.getItems().clear();
                for (Etlap etlap : etlapList) {
                    EtlapTable.getItems().add(etlap);
                }
            } catch (SQLException e) {
                hibaKiir(e);
            }
        }
    }
}