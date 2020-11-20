package co.edu.uniquindio.compiladores.proyecto.sintaxis

import javafx.scene.control.TreeItem

class BloqueDesicion (var expresionLogica: ExpresionLogica, var bloqueSentencia: ArrayList<Sentencia>, var bloqueSentencia2: ArrayList<Sentencia>?): Sentencia(null) {

    override fun toString(): String {
        return "BloqueDesicion(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia, bloqueSentencia2=$bloqueSentencia2)"
    }
    override fun getArbolVisual(): TreeItem<String>{
        var raiz= TreeItem("Decision")

        var condicion =  TreeItem("Condicion")
        condicion.children.add(expresionLogica.getArbolVisual())

        raiz.children.add(condicion)

        var raiz1= TreeItem("Sentencias")
        for(f in bloqueSentencia){
            raiz1.children.add(f.getArbolVisual())
        }

        raiz.children.add(raiz1)

        if (bloqueSentencia2!=null){
            var raiz2= TreeItem("Sentencias Else")
            for(f1 in bloqueSentencia2!!){
                raiz2.children.add(f1.getArbolVisual())
            }
            raiz.children.add(raiz1)
        }
        return raiz

    }

}