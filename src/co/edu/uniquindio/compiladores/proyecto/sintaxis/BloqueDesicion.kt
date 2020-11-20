package co.edu.uniquindio.compiladores.proyecto.sintaxis

class BloqueDesicion (var expresionLogica: ExpresionLogica, var bloqueSentencia: ArrayList<Sentencia>, var bloqueSentencia2: ArrayList<Sentencia>?): Sentencia(null) {
    override fun toString(): String {
        return "BloqueDesicion(expresionLogica=$expresionLogica, bloqueSentencia=$bloqueSentencia, bloqueSentencia2=$bloqueSentencia2)"
    }
}