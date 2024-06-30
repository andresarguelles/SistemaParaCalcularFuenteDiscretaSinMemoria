package com.fdsm.fuentediscretasinmemoria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class HelloController implements Initializable {

    @FXML
    private TableView<Simbolo> tablaDeSimbolos;

    @FXML
    private TableColumn<Simbolo, Character> simbolo;
    @FXML
    private TableColumn<Simbolo, Integer> frecuencia;
    @FXML
    private TableColumn<Simbolo, Double> probabilidad;

    @FXML
    private Label cantidadSimbolos;

    @FXML
    private Label cantidadEntropia;

    @FXML
    private Label cantidadInformacion;

    @FXML
    private Label cantidadRedundancia;

    @FXML
    private Label cantidadEficiencia;



    ObservableList<Simbolo> listaDeSimbolos = FXCollections.observableArrayList(
         Objects.requireNonNull(MainData.obtenerDatos())
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simbolo.setCellValueFactory(new PropertyValueFactory<Simbolo, Character>("simbolo"));
        frecuencia.setCellValueFactory(new PropertyValueFactory<Simbolo, Integer>("frecuencia"));
        probabilidad.setCellValueFactory(new PropertyValueFactory<Simbolo, Double>("probabilidad"));
        tablaDeSimbolos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cantidadSimbolos.setText(String.valueOf(MainData.totalSimbolos));
        cantidadEntropia.setText(String.format("%.3f", MainData.entropia) + " bits/símbolo");
        cantidadInformacion.setText(String.format("%.3f", MainData.informacionTotal) + " bits");
        cantidadRedundancia.setText(String.format("%.3f", MainData.redundancia));
        cantidadEficiencia.setText(String.format("%.3f", MainData.eficiencia));
        tablaDeSimbolos.setItems(listaDeSimbolos);

    }
}