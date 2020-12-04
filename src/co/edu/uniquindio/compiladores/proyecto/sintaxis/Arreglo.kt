package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
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

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String, fila:Int, columna:Int) {
        if(listaDatos.isNotEmpty()){
            var tipoDato = listaDatos.get(0).obtenerTipo(tablaSimbolos, ambito)
            for(f in listaDatos){
                if(f.obtenerTipo(tablaSimbolos, ambito) != tipoDato){
                    erroresSemanticos.add(Error("Un arreglo solo puede contener un tipo de datos",fila, columna ))
                    break;
                }
            }
        }else{
            erroresSemanticos.add(Error("No se puede declarar un arreglo vacío",fila, columna ))
        }
    }
}
