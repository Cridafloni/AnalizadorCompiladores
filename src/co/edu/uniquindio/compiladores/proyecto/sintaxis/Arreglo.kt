package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo (var listaDatos: ArrayList<Dato>): Dato(null){
    override fun toString(): String {
        return "Arreglo(listaDatos=$listaDatos)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz= TreeItem<String>("Datos")


        for (f in listaDatos){
            raiz.children.add(f.getArbolVisual())

        }


        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: String): String {
        if(listaDatos.isNotEmpty()){
            return listaDatos.get(0).obtenerTipo(tablaSimbolos, ambito)
        }
        return "DESCONOCIDO"
    }

}
