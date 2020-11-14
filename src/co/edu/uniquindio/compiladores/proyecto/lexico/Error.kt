package co.edu.uniquindio.compiladores.proyecto.lexico

class Error (var error:String, var fila:Int, var columna:Int) {

    override fun toString(): String {
        return "Error(error='$error', fila=$fila, columna=$columna)"
    }
}