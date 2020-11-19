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

    fun hacerBT(movido:Int){
        posicionActual=movido
        tokenActual=listaTokens[posicionActual]
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
     *<BloqueSentencias> ::= abrirBloque [<ListaSentencia>] cerrarBloque
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
     * <ListaSentencia>::= <sentencia> [<ListaSentencia>]
     */
    fun esListaSentencias():ArrayList<Sentencia>?{
        var listaSentencia= ArrayList<Sentencia>()
        var sentencia= esSentencia()

        while(sentencia!=null){
            listaSentencia.add(sentencia)
            sentencia= esSentencia()
        }
        if(listaSentencia.isEmpty()){
            return null
        }
        return listaSentencia
    }

    /**
     * <sentencia>::= <BloqueDecision> | <DeclaracionVariables> | <Asignacion> | <Impresion> |
                        <BloqueCiclos> | <Lectura> | <InvocacionFuncion>| [<Retorno>]
     */
    fun esSentencia(): Sentencia?{
        if(tokenActual.categoria==Categoria.RESERVADA){
            if(tokenActual.lexema=="VI"){
                obtenerSiguienteToken()
                var sentencia = esBloqueDecision()
                if(sentencia!=null){
                    obtenerSiguienteToken()
                    return Sentencia(sentencia)
                }else{
                    reportarError("Sentencia inválida")
                }
            }else if (tokenActual.lexema=="WHEN"||tokenActual.lexema=="DO"){

            }else if (tokenActual.lexema=="PRT"){

            }else if (tokenActual.lexema=="RD"){

            }else {
                var sentencia = esDeclaracionVariable()
                if(sentencia!=null){
                    return Sentencia(sentencia)
                }else{
                    reportarError("La sentencia es inválida")
                }

            }
        }
        return null
    }

    /**
     * <BloqueDesicion>::= condicional comillasAbriendo <ExpresiónLógica>
     *     comillasCerrando <BloqueSentencia>[<BloqueSentencia>]
     */
    fun esBloqueDecision(): BloqueDesicion? {
        if (tokenActual.categoria==Categoria.APERTURA_BLOQUE_AGRUPACION){
           obtenerSiguienteToken()

            var expresionLogica= esExpresionLogica()
            if(expresionLogica!=null){
                if (tokenActual.categoria == Categoria.APERTURA_BLOQUE_SENTENCIA){
                    var bloqueSentencia = esBloqueSentencias()
                    if(bloqueSentencia != null){
                        var bloqueSentencia1:ArrayList<Sentencia>?=null
                        if(tokenActual.categoria == Categoria.APERTURA_BLOQUE_SENTENCIA){
                            bloqueSentencia1 = esBloqueSentencias()
                        }
                        return BloqueDesicion(expresionLogica, bloqueSentencia, bloqueSentencia1)
                    }else{
                        reportarError("El bloque de sentencias está vacío")
                    }
                }
            }else{
                reportarError("Expresión lógica inválida")
            }
        }
        return null

    }

    /**
     * <ExpresiónLógica>::= <ExpresióRelacional> [Operadorlogicos <ExpresiónLógica>]
     */
    fun esExpresionLogica(): ExpresionLogica?{
        var relacional = esExpresionRelacional()
        if (relacional!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                var operadorLogico= tokenActual
                obtenerSiguienteToken()
                var expresion = esExpresionLogica()
                return ExpresionLogica(relacional,expresion,operadorLogico)
            }else{
                obtenerSiguienteToken()
                return ExpresionLogica(relacional,null,null)
            }
        }else{
            reportarError("Expresion relacional no valida")
        }
        return null
    }

    /**
     * <ExpresiónRelacional>::= <componente> operadorRelacional <componente>
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        var componente1= esComponente()
        if(componente1!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_RELACIONAL){
                var operadorRelacional = tokenActual
                obtenerSiguienteToken()
                var componente2= esComponente()
                if(componente2!=null){
                    return ExpresionRelacional(componente1, operadorRelacional, componente2)
                }else{
                    reportarError("Componente 2 no valido")
                }
            }else{
                reportarError("Se necesita operador relacional válido")
            }
        }else{
            reportarError("Componente 1 no valido")
        }
        return null
    }

    fun esComponente():Any?{
        var esdato= esDato()
        var invocacion= esInvocacionFuncion()
        var variable = tokenActual
        if(tokenActual.categoria==Categoria.IDENTIFICADOR){
            if(invocacion!=null){
                obtenerSiguienteToken()
                return invocacion
            }else{
                obtenerSiguienteToken()
                return variable
            }
        }else if(esdato!=null){
            obtenerSiguienteToken()
            return esdato
        }
        return null
    }

    /**
     * <InvocacionFuncion>::= identificador comillasAbriendo [<ListaParametros>]
     *                          comillasCerrando operadorFinal
     */
    fun esInvocacionFuncion(): FuncionInvocada ?{
        if(tokenActual.categoria==Categoria.IDENTIFICADOR){
            var nombre= tokenActual;
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.APERTURA_BLOQUE_AGRUPACION){
                obtenerSiguienteToken()
                var listaParametros = esListaParametros()
                if(tokenActual.categoria==Categoria.APERTURA_BLOQUE_AGRUPACION){
                    obtenerSiguienteToken()
                    if (tokenActual.categoria==Categoria.OPERADOR_FINAL){
                        return FuncionInvocada(nombre,listaParametros)
                    }else{
                        reportarError("Falta operador final")
                    }
                }else{
                    reportarError("Falta cerrar el bloque")
                }
            }else{
                reportarError("Falta cerrar el bloque")
            }
        }
        return null
    }
    /**fun verificarDeclaracionVariables(tokenActual: Token): Boolean {
        if(tokenActual.lexema=="CONS"){
            obtenerSiguienteToken()
            if (){

            }
        }

        return false
    }
*/
    /**
     * <parámetro>::= <TipoDato> identificador
     */

    fun esParametro(): Parametro?{
        val tipoDato= esTipoDato()
        if(tipoDato!=null){
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
            var tipo = tokenActual
            if (tipo.lexema=="REL"||tipo.lexema=="ENT"||tipo.lexema=="PAL"||
                    tipo.lexema=="LOGI"){
                obtenerSiguienteToken()
                return tipo
            }
        }
        return null
    }

    /**
     *
    <Dato> ::=  numeroEntero | numeroReal | <ListaCadena>|  logicos | <Arreglo> |
                <ExpresiónAritmética>|<Matriz>
     */
    fun esDato():Token?{
        if(tokenActual.categoria==Categoria.ENTERO||tokenActual.categoria==Categoria.CADENA_CARACTER
                ||tokenActual.categoria==Categoria.DECIMAL||tokenActual.categoria==Categoria.LOGICO){
            return tokenActual
        }
        return null
    }

    /**
     *<DeclaracionVariables>::= [constante] <TipoDato> variable operadorFinal
     *  | <TipoDato>  variable operadorAsignacion <Dato> operadorFinal
     *  | <DeclararArreglo> operadorFinal | <DeclararMatriz> operadorFinal
     */
    fun esDeclaracionVariable(): Any?{
        if(tokenActual.categoria==Categoria.RESERVADA) {
            var constante:Token? = null
            if (tokenActual.lexema == "CONS") {
                constante = tokenActual
                obtenerSiguienteToken()
            }
            var posicionInicial = posicionActual
            var tipoDato = esTipoDato()
            if (tipoDato != null) {
                if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                    var variable =  tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                        obtenerSiguienteToken()
                        return DeclararVariable(constante, tipoDato, variable, null, null)
                    }
                }else if(tokenActual.categoria == Categoria.ABRIR_ARREGLO){
                    hacerBT(posicionInicial)
                    var arreglo = esDeclaracionArreglo()
                    if(arreglo != null){
                        return arreglo
                    }
                }
            }else{
                reportarError("La variable debe tener un tipo de dato especificado.")
            }
        }
    return null
    }

    /**
     * <DeclararArreglo>::=  <TipoDato> abrirArreglo numeroEntero cerrarArreglo  variable operadorFinal
     * | <TipoDato> abrirArreglo cerrarArreglo variable operadorAsignacion <Arreglo>
     */
    fun esDeclaracionArreglo(): DeclararArreglo?{
        var posicionInicial = posicionActual
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            if(tokenActual.categoria == Categoria.ABRIR_ARREGLO){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.ENTERO){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.CERRAR_ARREGLO){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                            var variable = tokenActual
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                                obtenerSiguienteToken()
                                return DeclararArreglo(tipoDato, variable, null)
                            }else if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){

                            }
                        }
                    }
                }
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