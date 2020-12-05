package co.edu.uniquindio.compiladores.proyecto.semantica

import co.edu.uniquindio.compiladores.proyecto.lexico.Error
class TablaSimbolos (var listaErrores: ArrayList<Error>){


    var listaSimbolos: ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un simbolo que representa una variable, constante, parametro o un arreglo
     */
    fun guardarSimbloValor(nombre:String, tipoDato:String, modificable:Boolean, ambito:String, fila:Int, columna:Int){

            var s = buscarSimboloValor(nombre,ambito)
        if(s==null){
            listaSimbolos.add(Simbolo(nombre, tipoDato, modificable, ambito, fila, columna))
        }else{
                listaErrores.add(Error("El campo $nombre ya existe dentro del ambito $ambito", fila,columna))
        }


    }

    /**
     * Permite guardar un sumbolo que representa una funcion
     */
    fun guardarSimbloFuncion(nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito:String, fila:Int, columna:Int) {
            val s = buscarSimboloFuncion(nombre, tiposParametros)
        if(s==null){
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tiposParametros, ambito))
        }else{
            listaErrores.add(Error("La funcion $nombre ya existe dentro del ambito $ambito", fila,columna))
        }
    }

    /**
     * Permite buscar un valor dentro de la tabla de simbolos
     */
    fun buscarSimboloValor(nombre:String, ambito:String): Simbolo? {

        for (s in listaSimbolos) {
            if(s.tiposParametros==null){
                if ( s.nombre == nombre && (s.ambito == ambito || ambito.contains(s.ambito!!)) ) {
                return s
                }
            }

        }
        return null
    }

    /**
     *Permite buscar una funcion dentro de la tabla de simbolos
     */
    fun buscarSimboloFuncion(nombre:String, tiposParametros:ArrayList<String>): Simbolo? {
        for (s in listaSimbolos) {
            if(tiposParametros!=null) {
                if (s.nombre == nombre && s.tiposParametros == tiposParametros) {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}