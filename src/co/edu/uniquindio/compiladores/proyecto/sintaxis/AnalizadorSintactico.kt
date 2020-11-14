package co.edu.uniquindio.compiladores.proyecto.sintaxis

import co.edu.uniquindio.compiladores.proyecto.lexico.Categoria
import co.edu.uniquindio.compiladores.proyecto.lexico.Error
import co.edu.uniquindio.compiladores.proyecto.lexico.Token

class AnalizadorSintactico (var listaTokens:ArrayList<Token>){

    var posicionActual=0
    var tokenActual= listaTokens[0]
    var listaErrores= ArrayList<Error>()

    fun obtenerSiguienteToken(){
        posicionActual++

        if (posicionActual<listaTokens.size){
            tokenActual=listaTokens[posicionActual]
        }

    }

    /**
     * <UnidadDeCompilacion>::= <ListaFunciones>
     */
    fun esUnidadDeCompilacion (): UnidadDeCompilacion?{
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if(listaFunciones.size>0){
            UnidadDeCompilacion(listaFunciones)
        }else null

    }


    fun reportarError(mensaje:String){
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }
    /**
     * <ListaFunciones> ::= <Funcion>[<ListaDeFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion>{

        var listaFunciones= ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion!=null){
            listaFunciones.add(funcion)
            funcion= esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> ::= def <TipoRetorno> identificador "(" [<ListaParametros>]")"<BloqueSentencias>
     */
    fun esFuncion():Funcion?{

        if (tokenActual.categoria == Categoria.RESERVADA && tokenActual.lexema=="def"){
            obtenerSiguienteToken()

            var tipoRetorno = esTipoRetorno()

            if(tipoRetorno!= null){

                obtenerSiguienteToken()

                if(tokenActual.categoria== Categoria.IDENTIFICADOR){
                    var nombreFuncion= tokenActual
                    obtenerSiguienteToken()

                    var listaParametros= esListaParametros()

                    var bloqueDeSentencia = esBloqueSentencias()

                    if(bloqueDeSentencia!=null){
                        //Funcion bien escrita
                        return Funcion(nombreFuncion,tipoRetorno, listaParametros,bloqueDeSentencia )
                    }else{
                        reportarError("El bloque de sentencias esta vacio")
                    }
                }else{
                    reportarError("Falta nombre función")
                }
            }else{
                    reportarError("Falta el tipo de retorno de la funcion")
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= int | decimal | etc...
     */
    fun esTipoRetorno(): Token?{
        if (tokenActual.categoria == Categoria.RESERVADA){
            if (tokenActual.lexema=="REL"||tokenActual.lexema=="ENT"||tokenActual.lexema=="PAL"){
                return tokenActual
            }
        }
        return null
    }

    fun esListaParametros():ArrayList<Parametro>{
        var listaParametros= ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro!=null){
            listaParametros.add(parametro)

            if(tokenActual.lexema==","){
                obtenerSiguienteToken()
                parametro=esParametro()
            }else{
                reportarError("Falta una coma en la lista de parametros")
                break
            }

        }
        return listaParametros
    }

    /**
     *
     */
    fun esBloqueSentencias():ArrayList<Sentencia>{

        var listaSentencias= esListaSentencias()
        return listaSentencias
    }

    fun esListaSentencias():ArrayList<Sentencia>{
        return ArrayList()
    }

    fun esParametro(): Parametro?{

        if(tokenActual.categoria==Categoria.IDENTIFICADOR){
            val nombre= tokenActual
            obtenerSiguienteToken()

            if (tokenActual.lexema==":"){
                obtenerSiguienteToken()

                val tipoDato= esTipoDato()

                if(tipoDato!=null){
                    obtenerSiguienteToken()

                    return Parametro(nombre,tipoDato)
                }
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= int | decimal | etc...
     */
    fun esTipoDato(): Token?{
        if (tokenActual.categoria == Categoria.RESERVADA){
            if (tokenActual.lexema=="REL"||tokenActual.lexema=="ENT"||tokenActual.lexema=="PAL"){
                return tokenActual
            }
        }
        return null
    }
}