package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Token
import javafx.scene.control.TreeItem

class FuncionInvocada (var nombreFuncion: Token,var listaParametros:ArrayList<Dato>): Sentencia(null) {
    override fun toString(): String {
        return "FuncionInvocada(nombreFuncion=$nombreFuncion, listaParametros=$listaParametros)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Invocación")

        raiz.children.add(TreeItem("Nombre Función : ${nombreFuncion.lexema}"))

        var raiz1= TreeItem("Parámetros")
        for (f in listaParametros){
            raiz1.children.add(f.getArbolVisual())
        }
        raiz.children.add(raiz1)

        return raiz
    }
}