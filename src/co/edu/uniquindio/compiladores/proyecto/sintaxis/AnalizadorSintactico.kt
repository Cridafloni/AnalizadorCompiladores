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
        val listaVariableGlobal: ArrayList<VariableGlobal> = esListaVariableGlobal()
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        if(listaFunciones.size>0 ){
            if(tokenActual.categoria== Categoria.CIERRE_BLOQUE_SENTENCIA)
            {
                return  UnidadDeCompilacion(listaFunciones, listaVariableGlobal)
            }else{
                reportarError("Sintaxis invalida función esperada")
            }

        }
        return null

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
                var posicionInicial = posicionActual
                var tipoRetorno = esTipoDato()

                if(tipoRetorno== null) {
                    hacerBT(posicionInicial)
                }
                    if(tokenActual.categoria==Categoria.AGRUPADOR)
                    {
                        obtenerSiguienteToken()
                        var listaParametros= esListaParametros()
                        if(tokenActual.categoria==Categoria.AGRUPADOR)
                        {
                            obtenerSiguienteToken()

                            var bloqueDeSentencia = esBloqueSentencias()

                            if(bloqueDeSentencia!=null){
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
            }else {
                reportarError("No es un identificador valido")
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
                if(tokenActual.categoria!=Categoria.AGRUPADOR)
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
                var sentencia = esBloqueDecision()
                if(sentencia!=null){
                    return Sentencia(sentencia)
                }else{
                    reportarError("Sentencia inválida")
                }
            }else if (tokenActual.lexema=="WHEN"||tokenActual.lexema=="DO"){
                var sentencia = esBloqueCiclos()
                if(sentencia != null){
                    return Sentencia(sentencia)
                }
            }else if (tokenActual.lexema=="PRT"){
                var impresion = esImpresion()
                if(impresion != null){
                    return Sentencia(impresion)
                }
            }else if (tokenActual.lexema=="RD") {
                var lectura = esLectura()
                if(lectura != null){
                    if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                        obtenerSiguienteToken()
                        return Sentencia(lectura)
                    }
                }
            }else if (tokenActual.lexema=="RT") {
                var retorno = esRetorno()
                if(retorno != null){
                    if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                        obtenerSiguienteToken()
                        return Sentencia(retorno)
                    }
                }
            }else {
                var sentencia = esDeclaracionVariable()
                if(sentencia!=null){
                    return Sentencia(sentencia)
                }else{
                    reportarError("La sentencia es inválida")
                }
            }
        }else if(tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var posicionInicial = posicionActual
            var sentencia:Sentencia? = esAsignacion()
            if (sentencia != null) {
                return Sentencia(sentencia)
            }
            hacerBT(posicionInicial)
            sentencia = esInvocacionFuncion()
            if(sentencia != null){
                if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                    obtenerSiguienteToken()
                    return Sentencia(sentencia)
                }else{
                    reportarError("Falta el operador final en la invocación del método.")
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
        if(tokenActual.categoria == Categoria.RESERVADA && tokenActual.lexema == "VI"){
            obtenerSiguienteToken()
            if (tokenActual.categoria==Categoria.AGRUPADOR){
                obtenerSiguienteToken()
                var posicionInicial = posicionActual
                var esValorLogico:Boolean = true
                var expresionLogica = esExpresionLogica()
                var datoLogico:Dato? = null
                if(expresionLogica== null){
                    esValorLogico = false
                    hacerBT(posicionInicial)
                    datoLogico = esDato(false, false)
                    if(datoLogico != null){
                        esValorLogico = true
                    }
                }
                if(esValorLogico){
                    if(tokenActual.categoria == Categoria.AGRUPADOR){
                        obtenerSiguienteToken()
                        var bloqueSentencia = esBloqueSentencias()
                        if(bloqueSentencia != null){
                            var bloqueSentencia1:ArrayList<Sentencia>?=null
                            if(tokenActual.categoria == Categoria.APERTURA_BLOQUE_SENTENCIA){
                                bloqueSentencia1 = esBloqueSentencias()
                            }
                            return BloqueDesicion(expresionLogica, bloqueSentencia, bloqueSentencia1, datoLogico)
                        }else{
                            reportarError("El bloque de sentencias está vacío")
                        }
                    }else {
                        reportarError("Falta cierre de agrupador")
                    }
                }else{
                    reportarError("Expresión lógica inválida")
                }
            }else {
                reportarError("Falta apertura de agrupador")
            }
        }

        return null

    }
    fun esBloqueCiclos(): BloqueCiclos?{
        if(tokenActual.categoria == Categoria.RESERVADA){
            if (tokenActual.lexema=="WHEN"){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.AGRUPADOR){
                    obtenerSiguienteToken()
                    var posicionInicial = posicionActual
                    var esValorLogico:Boolean = true
                    var expresionLogica = esExpresionLogica()
                    var datoLogico:Dato? = null
                    if(expresionLogica== null){
                        esValorLogico = false
                        hacerBT(posicionInicial)
                        datoLogico = esDato(false, false)
                        if(datoLogico != null){
                            esValorLogico = true
                        }
                    }
                    if(esValorLogico){
                        if(tokenActual.categoria == Categoria.AGRUPADOR){
                            obtenerSiguienteToken()
                            var bloqueSentencias = esBloqueSentencias()
                            if(bloqueSentencias != null){
                                return BloqueCiclos(expresionLogica, bloqueSentencias, datoLogico)
                            }
                        }else {
                            reportarError("Falta cierre de agrupador")
                        }
                    }else{
                        reportarError("Valor lógico inválido")
                    }
                }else {
                    reportarError("Falta apertura de agrupador")
                }

            }else if(tokenActual.lexema=="DO"){
                obtenerSiguienteToken()
                var bloqueSentencias = esBloqueSentencias()
                if(bloqueSentencias != null){
                    if (tokenActual.lexema=="WHEN") {
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.AGRUPADOR) {
                            obtenerSiguienteToken()
                            var posicionInicial = posicionActual
                            var esValorLogico:Boolean = true
                            var expresionLogica = esExpresionLogica()
                            var datoLogico:Dato? = null
                            if(expresionLogica== null){
                                esValorLogico = false
                                hacerBT(posicionInicial)
                                datoLogico = esDato(false, false)
                                if(datoLogico != null){
                                    esValorLogico = true
                                }
                            }
                            if(esValorLogico){
                                if (tokenActual.categoria == Categoria.AGRUPADOR) {
                                    obtenerSiguienteToken()
                                    return BloqueCiclos(expresionLogica, bloqueSentencias, datoLogico)
                                }else {
                                    reportarError("Falta cierre de agrupador")
                                }
                            }else{
                                reportarError("Expresión lógica inválida")
                            }
                        }else {
                            reportarError("Falta apertura de agrupador")
                        }
                    }
                }
            }
        }
        return null
    }
    /**
     * <ExpresiónLógica>::= [negacion]<ExpresióRelacional> [Operadorlogicos <ExpresiónLógica>]
     */
    fun esExpresionLogica(): ExpresionLogica?{
        var operadorNegacion:Token? = null
        if(tokenActual.lexema == "~"){
            operadorNegacion = tokenActual
            obtenerSiguienteToken()
        }
        var relacional = esExpresionRelacional()
        if (relacional!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                var operadorLogico= tokenActual
                obtenerSiguienteToken()
                var expresion = esExpresionLogica()
                return ExpresionLogica(operadorNegacion,relacional,expresion,operadorLogico)
            }else{
                return ExpresionLogica(operadorNegacion,relacional,null,null)
            }
        }else{

        }
        return null
    }

    /**
     * <ExpresiónRelacional>::= <Dato> operadorRelacional <Dato>
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        var componente1= esDato(true, false)
        if(componente1!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_RELACIONAL){
                var operadorRelacional = tokenActual
                obtenerSiguienteToken()
                var componente2= esDato(true, false)
                if(componente2!=null){
                    return ExpresionRelacional(componente1, operadorRelacional, componente2)
                }else{
                    reportarError("Componente 2 no valido")
                }
            }
        }
        return null
    }

    /**
     * <ExpresiónAritmética>::= <Dato> operadorAritmético <Dato> [operadorAritmético  <Dato>]
     */
    fun esExpresionAritmetica(): ExpresionAritmetica?{
        var dato1 = esDato(false, false)
        if(dato1!=null){
            if(tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                var operadorAritmetico = tokenActual
                obtenerSiguienteToken()
                var dato2 = esDato(true, false)
                if(dato2 != null){
                    return ExpresionAritmetica(dato1,operadorAritmetico ,dato2)
                }else{
                    reportarError("El segundo parámetro de una expresión aritmética no puede ser null.")
                }
            }
        }
        return null
    }

    /**
     * <InvocacionFuncion>::= identificador comillasAbriendo [<ListaDatos>]
     *                          comillasCerrando operadorFinal
     */
    fun esInvocacionFuncion(): FuncionInvocada ?{
        if(tokenActual.categoria==Categoria.IDENTIFICADOR){
            var nombre= tokenActual;
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.AGRUPADOR){
                obtenerSiguienteToken()
                var listaDatos = esListaDatos()
                if(listaDatos != null){
                    if(tokenActual.categoria==Categoria.AGRUPADOR){
                        obtenerSiguienteToken()
                        return FuncionInvocada(nombre,listaDatos)
                    }else{
                        reportarError("Falta cierre de agrupación")
                    }
                }
            }
        }
        return null
    }
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
            }else{
                reportarError("El parámetro debe llevar un identificador")
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
            }else {
                reportarError("El tipo de dato no es valido")
            }
        }
        return null
    }

    fun esRetorno():Retorno?{
        if (tokenActual.lexema=="RT" && tokenActual.categoria==Categoria.RESERVADA){
            obtenerSiguienteToken()

            var dato = esDato(true, true)
            if (dato!= null){
                return Retorno(dato)
            }
            else{
                reportarError("El obligariorio el tipo de retorno")
            }
        }
        return null
    }

    /**
     * <Dato> ::=  numeroEntero | numeroReal | <ListaCadena>|  logicos
     * | <Arreglo> | <ExpresiónAritmética> | <Matriz> | <InvocacionFuncion>| variable
     * |<ExpresionLogica>
     */
    fun esDato(expresionAritmetica: Boolean, expresionLogica: Boolean):Dato?{
        var posicionInicial = posicionActual
        var arreglo = esArreglo()
        if(arreglo !=null){
            return  Dato(arreglo)
        }
        hacerBT(posicionInicial)
        if (tokenActual.categoria==Categoria.CADENA_CARACTER) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.CONCATENACION) {
                hacerBT(posicionInicial)
                var listaCadena = esListaCadena()
                if (listaCadena != null) {
                    return Dato(listaCadena)
                }
            }
        }
        hacerBT(posicionInicial)
        var lectura = esLectura();
        if(lectura != null){
            return Dato(lectura)
        }
        hacerBT(posicionInicial)
        var invocacion = esInvocacionFuncion();
        if(invocacion!=null){
            return Dato(invocacion)
        }
        if(expresionAritmetica){
            hacerBT(posicionInicial)
            var expresion = esExpresionAritmetica()
            if(expresion!=null){
                return Dato(expresion)
            }
        }
        if(expresionLogica){
            hacerBT(posicionInicial)
            var expresionLogica = esExpresionLogica();
            if(expresionLogica!=null){
                return Dato(expresionLogica)
            }
        }
        hacerBT(posicionInicial)
        if(tokenActual.categoria==Categoria.ENTERO||tokenActual.categoria==Categoria.CADENA_CARACTER
                ||tokenActual.categoria==Categoria.DECIMAL||tokenActual.categoria==Categoria.LOGICO
                ||tokenActual.categoria == Categoria.IDENTIFICADOR){
            var dato = tokenActual
            obtenerSiguienteToken()
            return Dato(dato)
        }
        return null
    }

    /**
     *<DeclaracionVariables>::= [constante] <TipoDato> variable operadorFinal
     *  | <TipoDato>  variable operadorAsignacion <Dato> operadorFinal
     *  | <DeclararArreglo> operadorFinal | <DeclararMatriz> operadorFinal
     */
    fun esDeclaracionVariable(): DeclararVariable?{
        if(tokenActual.categoria == Categoria.RESERVADA && tokenActual.lexema != "|M|") {
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
                        return DeclararVariable(constante, tipoDato, variable, null, null, null)
                    }else if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                        hacerBT(posicionInicial)
                        var tipoDato = esTipoDato()
                        if (tipoDato != null) {
                            var asignacion = esAsignacion()
                            if(asignacion!=null){
                                return DeclararVariable(constante, tipoDato, null, asignacion, null, null)
                            }
                        }
                    }else{
                        reportarError("Falta operador de finalización")
                    }
                }else if(tokenActual.categoria == Categoria.ABRIR_ARREGLO){
                    hacerBT(posicionInicial)
                    var arreglo = esDeclaracionArreglo()
                    if(arreglo != null){
                        return DeclararVariable(constante, null, null, null, arreglo, null)
                    }
                    hacerBT(posicionInicial)
                    var matriz = esDeclararMatriz()
                    if(matriz !=null){
                       return DeclararVariable(constante, null, null, null, null, matriz)
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
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            if(tokenActual.categoria == Categoria.ABRIR_ARREGLO){
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.ENTERO){
                    var tamanio = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.CERRAR_ARREGLO){
                        obtenerSiguienteToken()
                        var posicionInicial = posicionActual
                        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                            var variable = tokenActual
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                                obtenerSiguienteToken()
                                return DeclararArreglo(tipoDato, tamanio, variable, null)
                            }else if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                                hacerBT(posicionInicial)
                                var asignacion = esAsignacion()
                                if(asignacion !=null){
                                    return DeclararArreglo(tipoDato, tamanio, null, asignacion)
                                }else{
                                    reportarError("La asignación del arreglo es incorrecta")
                                }
                            }else{
                                reportarError("Falta operacdor final.")
                            }
                        }
                    }else{
                        reportarError("Se debe cerrar el operador de arreglo.")
                    }
                }else{
                    reportarError("La longitud del arreglo debe ser Entero.")
                }
            }
        }
        return null
    }

    /**
     * <DeclararMatriz>::= <TipoDato> abrirArreglo numeroEntero operadorSeparador numeroEntero cerrarArreglo  variable
     * | <TipoDato> abrirArreglo  numeroEntero operadorSeparador numeroEntero cerrarArreglo <Asignacion>
     */
    fun esDeclararMatriz(): DeclararMatriz?{
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            if(tokenActual.categoria == Categoria.ABRIR_ARREGLO) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.ENTERO) {
                    var filas = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.CERRAR_ARREGLO) {
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.ABRIR_ARREGLO) {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.ENTERO) {
                                var columnas = tokenActual
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.CERRAR_ARREGLO) {
                                    obtenerSiguienteToken()
                                    if(tokenActual.categoria == Categoria.IDENTIFICADOR){
                                        var variable = tokenActual
                                        var posicionInicial = posicionActual
                                        obtenerSiguienteToken()
                                        if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                                            obtenerSiguienteToken()
                                            return DeclararMatriz(tipoDato, filas, columnas, variable, null)
                                        }else if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                                            hacerBT(posicionInicial)
                                            var asignacion = esAsignacion()
                                            if(asignacion !=null){
                                                return DeclararMatriz(tipoDato, filas, columnas, null, asignacion)
                                            }else{
                                                reportarError("La asignación de la matriz es incorrecta")
                                            }
                                        }else{
                                            reportarError("Falta operacdor final.")
                                        }
                                    }else{
                                        reportarError("Falta el identificador de la matriz.")
                                    }
                                }
                            }else{
                                reportarError("La cantidad de columnas de la matriz debe ser Entero.")
                            }
                        }
                    }
                }else{
                    reportarError("La cantidad de filas de la matriz debe ser Entero.")
                }
            }
        }
        return null
    }

    /**
     * <Asignación>::= variable  operadorAsignacion <Dato> operadorFinal
     */
    fun esAsignacion():Asignacion?{
        if(tokenActual.categoria == Categoria.IDENTIFICADOR){
            var identificador = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.OPERADOR_ASIGNACION){
                obtenerSiguienteToken()
                var dato = esDato(true, true)
                if(dato!=null){
                    if(tokenActual.categoria == Categoria.OPERADOR_FINAL){
                        obtenerSiguienteToken()
                        return Asignacion(identificador, dato)
                    }else{
                        reportarError("Falta el operador final.")
                    }
                }else{
                    reportarError("La asignación debe tener un dato")
                }
            }
        }
    return null
    }

    /**
     * <listaVariableGlobales>::= <DeclararVariable> [<listaVariableGlobales>]
     */
    fun esListaVariableGlobal(): ArrayList<VariableGlobal>{
        var listaVariableGlobal = ArrayList<VariableGlobal>()
        var variableGlobal = esDeclaracionVariable()
        while(variableGlobal != null){
            listaVariableGlobal.add(VariableGlobal(variableGlobal))
            variableGlobal = esDeclaracionVariable()
        }
        return listaVariableGlobal
    }
    /**
     * <Impresión>::= imprimir comillasAbriendo <ListaCadena> comillasCerrando operadorFinal
     */
    fun esImpresion(): Impresion?{
        if(tokenActual.categoria == Categoria.RESERVADA)
        {
            if(tokenActual.lexema == "PRT") {
                var token = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria==Categoria.AGRUPADOR){
                    obtenerSiguienteToken()
                    var aux= esListaCadena()
                    if(aux!= null)
                    {
                        if (tokenActual.categoria==Categoria.AGRUPADOR )
                        {
                            obtenerSiguienteToken()
                            if(tokenActual.categoria == Categoria.OPERADOR_FINAL)
                            {
                                obtenerSiguienteToken()
                                return Impresion(aux)
                            }
                        }else{
                            reportarError("Falta cerrar el bloque de agrupación del PRT")
                        }
                    }else{
                        reportarError("Se necesita una cadena para imprimir")
                    }

                }else {
                    reportarError("Falta abrir el bloque de agrupación del PRT")
                }
            }

        }
        return null
    }

    /**
     * <ListaCadena>::=  cadenaDeCaracteres  [operadorConcatenacion variable operadorConcatenacion <ListaCadena>]
     */
    fun esListaCadena(): ArrayList<Token>? {
        var listaCadena = ArrayList<Token>()

        if(tokenActual.categoria==Categoria.CADENA_CARACTER)
        {
            listaCadena.add(tokenActual)
            obtenerSiguienteToken()
            while(tokenActual.lexema == "+" && tokenActual.categoria== Categoria.CONCATENACION)
            {
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR)
                {
                    listaCadena.add(tokenActual)
                    obtenerSiguienteToken()
                    if(tokenActual.lexema == "+" && tokenActual.categoria== Categoria.CONCATENACION)
                    {
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.CADENA_CARACTER)
                        {
                            listaCadena.add(tokenActual)
                            obtenerSiguienteToken()
                        }else{
                            reportarError("Falta una cadena por concatenar")
                        }
                    }else{
                        reportarError("Falta una cadena por concatenar")
                    }
                }else{
                    reportarError("Identificador no valido")
                }
            }

            return listaCadena
        }else{
            return null
        }




    }
    /**
     *<Lectura>::= leer  comillasAbriendo comillasCerrando operadorFinal
     */
    fun esLectura(): FuncionInvocada?{
        if(tokenActual.categoria == Categoria.RESERVADA){
            if(tokenActual.lexema == "RD"){
                var aux= tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.AGRUPADOR){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.AGRUPADOR) {
                        obtenerSiguienteToken()
                        var vacia = ArrayList<Dato>()
                        return FuncionInvocada(aux,vacia)
                    }else{
                        reportarError("Falta cierre de agrupacion")
                    }
                }else{
                    reportarError("Falta apertura de agrupacion")
                }
            }
        }
        return null
    }




    /**
     * <Arreglo>::= comillasAbriendo <ListaDatos> comillasCerrando
     */
    fun esArreglo(): Arreglo?{
        if(tokenActual.categoria==Categoria.AGRUPADOR){
            obtenerSiguienteToken()
            var listaDatos = esListaDatos()
            if(listaDatos!= null){
                if(tokenActual.categoria!=Categoria.AGRUPADOR && listaDatos.isNotEmpty())
                {
                    reportarError("Falta un punto y coma en la lista de datos")

                }
                obtenerSiguienteToken()
                return Arreglo(listaDatos)
            }
        }
        return null
    }

    /**
     * <Matriz>::= comillasAbriendo<Arreglo> operadorSeparador <Arreglo> comillasCerrando operadorFinal
     */
    fun esMatriz(): Matriz?{
        if(tokenActual.categoria==Categoria.AGRUPADOR) {
            obtenerSiguienteToken()
            var arreglo1 = esArreglo()
            if(arreglo1 != null){
                if(tokenActual.categoria == Categoria.SEPARADOR){
                    obtenerSiguienteToken()
                    var arreglo2 = esArreglo()
                    if(arreglo2 != null){
                        if(tokenActual.categoria == Categoria.AGRUPADOR)
                        {
                            obtenerSiguienteToken()
                            return Matriz(arreglo1, arreglo2)
                        }else{
                            reportarError("Falta las comillas de Cierre del bloque de agrupación")
                        }

                    }
                }
            }
        }
        return null
    }
    /**
     * <ListaDatos>::= <Dato> [operadorSeparador<ListaDatos>]
     */
    private fun esListaDatos(): ArrayList<Dato>? {
        var listaDatos= ArrayList<Dato>()
        var dato = esDato(true, true)
        while (dato!=null){
            listaDatos.add(dato)
            if(tokenActual.lexema==";"&& tokenActual.categoria==Categoria.SEPARADOR){
                obtenerSiguienteToken()
                dato=esDato(true, true)
            }else{
                if(tokenActual.categoria!=Categoria.AGRUPADOR)
                {
                    reportarError("Falta un punto y coma en la lista de datos")

                }
                break
            }
        }
        if(tokenActual.categoria != Categoria.AGRUPADOR){
            return null

        }

        return listaDatos


    }
}

