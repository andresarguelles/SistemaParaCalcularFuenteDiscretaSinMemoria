package com.fdsm.fuentediscretasinmemoria;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    @FXML
    private Label alertaArchivo;

    @FXML
    private TextArea cuadroTexto;

    @FXML
    void obtenerDireccion(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt")
        );

        File initialDirectory = new File("src/main/java/com/fdsm/fuentediscretasinmemoria/doc");
        if(initialDirectory.exists()){
            fileChooser.setInitialDirectory(initialDirectory);
        }

        Stage stage = (Stage) tablaDeSimbolos.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile!=null){
            MainData.direccion = selectedFile.getAbsolutePath();
        }
        System.out.println(MainData.direccion);
    }



    @FXML
    void calcularDatos(ActionEvent event) {
        initData();
    }


    private void initData(){
        if(MainData.direccion == null){
            alertaArchivo.setText("Por favor, seleccione un archivo");
            alertaArchivo.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(javafx.util.Duration.seconds(2));
            visiblePause.setOnFinished(e -> alertaArchivo.setVisible(false));
            visiblePause.play();
            return;
        }

        ObservableList<Simbolo> listaDeSimbolos = FXCollections.observableArrayList(
                Objects.requireNonNull(MainData.obtenerDatos())
        );
        simbolo.setCellValueFactory(new PropertyValueFactory<Simbolo, Character>("simbolo"));
        frecuencia.setCellValueFactory(new PropertyValueFactory<Simbolo, Integer>("frecuencia"));
        probabilidad.setCellValueFactory(new PropertyValueFactory<Simbolo, Double>("probabilidad"));
        tablaDeSimbolos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cantidadSimbolos.setText(String.valueOf(MainData.totalSimbolos));
        cantidadEntropia.setText(String.format("%.3f", MainData.entropia) + " bits/símbolo");
        cantidadInformacion.setText(String.format("%.3f", MainData.informacionTotal) + " bits");
        cantidadRedundancia.setText(String.format("%.3f", MainData.redundancia) + "%");
        cantidadEficiencia.setText(String.format("%.3f", MainData.eficiencia) + "%");
        tablaDeSimbolos.setItems(listaDeSimbolos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initData();
    }
}