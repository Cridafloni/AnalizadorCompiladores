package co.edu.uniquindio.compiladores.proyecto.controladores

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField

class InicioController {

    @FXML lateinit var codigo:TextArea

    @FXML
    fun analizarCodigo( e: ActionEvent){

        if(codigo.text.length>0){
            val lexico = AnalizadorLexico(codigo.text)
            lexico.analizar()

            print(lexico.listaTokens)
        }
    }
}