package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*

class InicioController : Initializable {

    @FXML lateinit var codigo:TextArea

    @FXML lateinit var  tablaTokens:TableView<Token>
    @FXML lateinit var columnaLexema:TableColumn<Token,String>
    @FXML lateinit var columnaCategoria:TableColumn<Token,String>
    @FXML lateinit var columnaFila:TableColumn<Token,Int>
    @FXML lateinit var columnaColumna:TableColumn<Token,Int>

    @FXML lateinit var arbolVisual:TreeView<String>

    @FXML
    fun analizarCodigo( e: ActionEvent){
        tablaTokens.items = null
        if(codigo.text.length>0) {
            val lexico = AnalizadorLexico(codigo.text)
            lexico.analizar()
            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            if (lexico.listaErrores.isEmpty()){

                val sintaxis= AnalizadorSintactico(lexico.listaTokens)
                val uc= sintaxis.esUnidadDeCompilacion()
                        if(uc!=null){
                            arbolVisual.root= uc.getArbolVisual()
                        }

                    }else{
                        var alerta = Alert(Alert.AlertType.WARNING)
                            alerta.headerText="Mensaje"
                            alerta.contentText= "Existen errores lexicos"
                        }



            //print(lexico.listaTokens)

        }
    }

    override fun initialize (location: URL?, resources: ResourceBundle?){
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCategoria.cellValueFactory= PropertyValueFactory("categoria")
        columnaFila.cellValueFactory= PropertyValueFactory("fila")
        columnaColumna.cellValueFactory= PropertyValueFactory("columna")


    }
}