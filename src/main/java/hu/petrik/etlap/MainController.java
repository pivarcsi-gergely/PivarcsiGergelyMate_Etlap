package hu.petrik.etlap;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class MainController {

    @FXML
    private Button AddBtn;
    @FXML
    private Button DeleteBtn;
    @FXML
    private TableColumn colNev;
    @FXML
    private Button SzazalekEmeloBtrn;
    @FXML
    private Button FtEmeloBtn;
    @FXML
    private TableColumn colAr;
    @FXML
    private TableView EtlapTable;
    @FXML
    private Spinner FtEmeloSpinner;
    @FXML
    private TableColumn colKategoria;
    @FXML
    private Spinner szazalekEmeloSpinner;
    @FXML
    private Label leirasLbl;

    public void initialize() {
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try{
            db = new EtlapDB();
            etlapListaFeltolt();
        } catch (SQLException e)
            hibaKiir(e);
    }

    private void etlapListaFeltolt() {

    }
}