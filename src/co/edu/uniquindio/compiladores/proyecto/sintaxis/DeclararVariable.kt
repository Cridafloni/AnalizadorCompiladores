package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class DeclararVariable (var constante: Token?, var tipoDato: Token?, var variable: Token?, var asignacion: Asignacion?, var declararArreglo: DeclararArreglo?,
                        var declararMatriz: DeclararMatriz?) : Sentencia(null){
    override fun toString(): String {
        var cons = "constante=$constante, "
        if(constante==null){
            cons = ""
        }
        if(declararArreglo!=null){
            return "DeclararVariable("+cons+"declararArreglo=$declararArreglo)"
        }
        if(declararMatriz!=null){
            return "DeclararVariable("+cons+"declararMatriz=$declararMatriz)"
        }
        if(asignacion == null){
            return "DeclararVariable("+cons+"tipoDato=$tipoDato, variable=$variable)"
        }
        return "DeclararVariable("+cons+"tipoDato=$tipoDato, asignacion=$asignacion)"

    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Dato")

        if (constante != null) {
            raiz.children.add(TreeItem("Constante: ${constante!!.lexema}"))
        }
        if (tipoDato != null) {
            raiz.children.add(TreeItem("Tipo Dato: ${tipoDato!!.lexema}"))
        }
        if (variable != null) {
            raiz.children.add(TreeItem("Tipo Dato: ${variable!!.lexema}"))
        }
        if (asignacion != null) {
            raiz.children.add(asignacion!!.getArbolVisual())
        }
        if (declararArreglo != null) {
            raiz.children.add(declararArreglo!!.getArbolVisual())
        }
        if (declararMatriz != null) {
            raiz.children.add(declararMatriz!!.getArbolVisual())
        }
        return raiz
    }

}