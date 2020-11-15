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
        //print(listaFunciones)
        val listaVariableGlobal: ArrayList<VariableGlobal> = esListaVariableGlobal()
        return if(listaFunciones.size>=0){
            UnidadDeCompilacion(listaFunciones, listaVariableGlobal)
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
     * <Funcion> ::= metodo identificador <TipoDato>  comillasAbriendo [<ListaParametros>]
     *              comillasCerrando <BloqueSentencias>
     */
    fun esFuncion():Funcion?{
        if (tokenActual.categoria == Categoria.RESERVADA && tokenActual.lexema=="|M|"){
            obtenerSiguienteToken()

            if(tokenActual.categoria== Categoria.IDENTIFICADOR){
                var nombreFuncion= tokenActual
                obtenerSiguienteToken()

                var tipoRetorno = esTipoRetorno()

                if(tipoRetorno!= null){
                    obtenerSiguienteToken()

                    if(tokenActual.categoria==Categoria.APERTURA_BLOQUE_AGRUPACION)
                    {
                        obtenerSiguienteToken()
                        var listaParametros= esListaParametros()
                        if(tokenActual.categoria==Categoria.APERTURA_BLOQUE_AGRUPACION)
                        {
                            obtenerSiguienteToken()

                            var bloqueDeSentencia = esBloqueSentencias()

                            if(bloqueDeSentencia!=null){
                                obtenerSiguienteToken()
                                //Funcion bien escrita
                                return Funcion(nombreFuncion,tipoRetorno, listaParametros,bloqueDeSentencia )
                            }else{
                                reportarError("El bloque de sentencias esta vacio")
                            }
                        }
                        else{
                            reportarError("Falta cerrar la lista de Parametros")
                        }
                    }else{
                        reportarError("Falta abrir la lista de Parametros")
                    }
                }else{
                    reportarError("Falta el tipo de retorno de la funcion")
                }
            }else {
                reportarError("No es un identificador valido")
            }
        }else{
            if(tokenActual.categoria !=  Categoria.CIERRE_BLOQUE_SENTENCIA){
                reportarError("No  posee el parametro para el metodo")
            }

        }
        return null
    }

    /**
     * <TipoRetorno> ::= int | decimal | etc... (FALTA void )
     */
    fun esTipoRetorno(): Token?{
        if (tokenActual.categoria == Categoria.RESERVADA){
            if (tokenActual.lexema=="REL"||tokenActual.lexema=="ENT"||tokenActual.lexema=="PAL"||
                    tokenActual.lexema=="LOGI"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros>::=  <parámetro> [ operadorSeparador <ListaParametros>  ]
     */
    fun esListaParametros():ArrayList<Parametro>{
        var listaParametros= ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro!=null){
            listaParametros.add(parametro)

            if(tokenActual.lexema==";"&& tokenActual.categoria==Categoria.SEPARADOR){
                obtenerSiguienteToken()
                parametro=esParametro()
            }else{
                if(tokenActual.categoria!=Categoria.APERTURA_BLOQUE_AGRUPACION)
                {
                    reportarError("Falta un punto y coma en la lista de parametros")

                }
                break
            }
        }
        return listaParametros
    }

    /**
     *<BloqueSentencias> ::=abrirBloque [<ListaSentencia>] cerrarBloque
     */
    fun esBloqueSentencias():ArrayList<Sentencia>?{
        if(tokenActual.categoria==Categoria.APERTURA_BLOQUE_SENTENCIA){
            obtenerSiguienteToken()

            var listaSentencias= esListaSentencias()
            if (tokenActual.categoria==Categoria.CIERRE_BLOQUE_SENTENCIA){
                obtenerSiguienteToken()
                return  listaSentencias
            }else{
                reportarError("Falta cerrar el bloque de sentencia >")
            }

        }else{
            reportarError("Falta abrir el bloque de sentencia <")
        }
        return null
    }

    /**
     *
     */
    fun esListaSentencias():ArrayList<Sentencia>{
        var listaSentencia= ArrayList<Sentencia>()
        var sentencia=

        return ArrayList()
    }

    /**
     * <parámetro>::= <TipoDato> identificador
     */
    fun esParametro(): Parametro?{
        val tipoDato= esTipoDato()
        if(tipoDato!=null){
            obtenerSiguienteToken()
            if (tokenActual.categoria==Categoria.IDENTIFICADOR){
                val nombre= tokenActual
                obtenerSiguienteToken()
                return Parametro(nombre,tipoDato)
            }
        }
        return null
    }

    /**
     * <TipoDato> ::= int | decimal | etc...
     */
    fun esTipoDato(): Token?{
        if (tokenActual.categoria == Categoria.RESERVADA){
            if (tokenActual.lexema=="REL"||tokenActual.lexema=="ENT"||tokenActual.lexema=="PAL"||
                    tokenActual.lexema=="LOGI"){
                return tokenActual
            }
        }
        return null
    }


    /**
     * <variableGlobal>::= VariableGlobal <DeclararVariable>
     */
    fun esListaVariableGlobal(): ArrayList<VariableGlobal>{
        return ArrayList()
    }

}