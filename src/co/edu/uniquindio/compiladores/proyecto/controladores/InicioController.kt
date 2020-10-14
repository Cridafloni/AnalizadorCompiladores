package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TableColumn
import javafx.scene.control.TextArea
import javafx.scene.control.TextField

class InicioController {

    @FXML lateinit var codigo:TextArea

    @FXML lateinit var  tabla:ObservableList<Token>
    @FXML lateinit var columnaLexema:TableColumn<String,String>
    @FXML lateinit var columnaCategoria:TableColumn<Categoria,String>
    @FXML lateinit var columnaFila:TableColumn<Int,String>
    @FXML lateinit var columnaColumna:TableColumn<Int,String>

    @FXML
    fun analizarCodigo( e: ActionEvent){

        if(codigo.text.length>0){
            val lexico = AnalizadorLexico(codigo.text)
            lexico.analizar()

            print(lexico.listaTokens)

        }
    }
}