module com.fdsm.fuentediscretasinmemoria {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.fdsm.fuentediscretasinmemoria to javafx.fxml;
    exports com.fdsm.fuentediscretasinmemoria;
}