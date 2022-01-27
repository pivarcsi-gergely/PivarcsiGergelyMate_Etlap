package hu.petrik.etlap.Controllers;

import hu.petrik.etlap.Controller;
import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.EtlapDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private Button AddBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private TableColumn<Etlap, String> colNev;
    @FXML
    private Button SzazalekEmeloBtrn;
    @FXML
    private Button FtEmeloBtn;
    @FXML
    private TableColumn<Etlap, Integer> colAr;
    @FXML
    private TableView<Etlap> EtlapTable;
    @FXML
    private Spinner FtEmeloSpinner;
    @FXML
    private TableColumn<Etlap, String> colKategoria;
    @FXML
    private Spinner szazalekEmeloSpinner;
    @FXML
    private Label leirasLbl;

    private EtlapDB db;

    public void initialize() {
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtlapDB();
            etlapListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    private void etlapListaFeltolt() {
        try {
            List<Etlap> etlapList = db.getEtlap();
            etlapList.clear();
            for (Etlap etlap :
                    etlapList) {
                etlapList.add(etlap);
            }
        }
        catch (SQLException e){
            hibaKiir(e);
        }
    }
}