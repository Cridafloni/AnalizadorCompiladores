package co.edu.uniquindio.compiladores.proyecto.lexico

class AnalizadorLexico(var codigoFuente:String) {

    var posicionActual=0

    var caracterActual  = codigoFuente[0]
    var listaTokens= ArrayList<Token>()
    var finCodigo= 0.toChar()
    var filaActual=0
    var columnaActual=0

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int) = listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial:Int,filaInicial:Int,columnaInicial:Int){
        posicionActual= posicionInicial
        filaActual=filaInicial
        columnaActual=columnaInicial

        caracterActual= codigoFuente[posicionActual]
    }

    fun analizar(){

        while (caracterActual != finCodigo){

            if (caracterActual == ' ' || caracterActual=='\t' || caracterActual=='\n'){
                obtenerSiguienteCaracter()
                continue
            }
            //Palabras reservadas
            if(esImprimir())continue
            if(esLeer())continue
            if(esTipoCadena())continue
            if(esTipoEntero())continue
            if(esTipoLogico())continue
            if(esTipoReal())continue
            if(esCiclo()) continue
            if(esCondicional()) continue

            //Tipos de datos
            if(esConstante())continue
            if(esEntero()) continue
            if(esDecimal())continue
            if(esCadena()) continue //=================
            if(esLogico()) continue
            //Bloques de sentencia
                //if(esBloqueSentencia()) continue
                //if(esAgrupacion()) continue
            if(esAgrupacionAbierta()) continue
            if(esAgrupacionCierre()) continue
            if(esSentenciaAbierta())continue
            if(esSentenciaCierre())continue
            if (esIdentificador())continue
            //Operadores lógicos
            if(esConectorY()) continue
            if(esConectorO()) continue
            if(esNegacion()) continue

            //Comentarios
            if (esComentarioBloque()) continue
            if (esComentarioLinea()) continue
            //Operadores matemáticos y asignación
            if(esMetodo()) continue
            if (esOperadorMatematico()) continue
            if (esOperadorFinal()) continue
            if(esConcatenación()) continue
            if(esSeparador())continue
            //Operadores relacionales
            if (esMayorIgual()) continue
            if (esMenorIgual()) continue
            if (esIgualIgual()) continue
            if (esDiferenteIgual()) continue

            ///Adicionales
            if(esAbrirArreglo())continue
            if(esCerrarArreglo())continue



            if(caracterActual!=finCodigo){
               almacenarToken(""+caracterActual, Categoria.DESCONOCIDO,filaActual,columnaActual)
               obtenerSiguienteCaracter()
           }
        }
    }

    fun esCondicional():Boolean{
        if (caracterActual == 'V'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != 'I'){
                return false
            }
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)

            return true
        }
        return false
    }

    fun esIgualIgual():Boolean{
        if (caracterActual == ':'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != '~'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != ':'){
                almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicial,columnaInicial)
                return false
            }
            posicionInicial = posicionActual
            filaInicial = filaActual
            columnaInicial = columnaActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='~'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            lexema = lexema.dropLast(1)
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            almacenarToken(lexema,Categoria.OPERADOR_ASIGNACION,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esMenorIgual():Boolean{
        if (caracterActual == '<'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != ':'){
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='~'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            return false
        }
        return false
    }

    fun esMayorIgual():Boolean{
        if (caracterActual == '>'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != ':'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='~'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            return false
        }
        return false
    }

    /**
     *

    fun esBloqueSentencia():Boolean{
        if (caracterActual == '<'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == ':'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            while (caracterActual != '>' && caracterActual != finCodigo){
                obtenerSiguienteCaracter()
            }
            if (caracterActual=='>'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.BLOQUE_SENTENCIA,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
     */

    fun esSentenciaAbierta():Boolean{
        if (caracterActual == '<'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual==':'){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema,Categoria.APERTURA_BLOQUE_SENTENCIA,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esSentenciaCierre():Boolean{
        if (caracterActual == '>'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual==':'){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema,Categoria.CIERRE_BLOQUE_SENTENCIA,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esComentarioBloque():Boolean{
        if (caracterActual == '*'){
            var caracterAnterior = ' '
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != '-'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != finCodigo){
                caracterAnterior = caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '*' && caracterAnterior == '-'){
                    lexema += caracterAnterior
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema,Categoria.COMENTARIO_BLOQUE,filaInicial,columnaInicial)

                    return true
                }
            }
            return false
        }
        return false
    }

    fun esComentarioLinea():Boolean{
        if (caracterActual == '*'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '-'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            while (caracterActual != '\n' && caracterActual != finCodigo){
                obtenerSiguienteCaracter()
            }
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.COMENTARIO_LINEA,filaInicial,columnaInicial)
            return true
        }
        return false
    }

   /* fun esAgrupacion():Boolean{
        if (caracterActual == '"'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '"' && caracterActual != finCodigo){
                obtenerSiguienteCaracter()
            }
            if (caracterActual=='"'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.BLOQUE_AGRUPACION,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }*/
    fun esAgrupacionAbierta():Boolean{
        if(caracterActual == '"'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.APERTURA_BLOQUE_AGRUPACION,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esAgrupacionCierre():Boolean{
        if(caracterActual == '"'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.CIERRE_BLOQUE_AGRUPACION,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esCaracterEscape():String{
        if (caracterActual == '°'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            when(caracterActual){
                's' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE,filaInicial,columnaInicial)

                    return "\n"
                }
                '°' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE,filaInicial,columnaInicial)

                    return "°"
                }
                '^' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE,filaInicial,columnaInicial)
                    return "^"
                }
                't' -> {
                    lexema += caracterActual
                    almacenarToken(lexema,Categoria.CARACTER_ESCAPE,filaInicial,columnaInicial)

                    return "\t"
                }
                else -> {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)

                    return ""
                }
            }
        }
        return ""
    }

    fun esCadena():Boolean{
        if (caracterActual=='^'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '^' && caracterActual != finCodigo){
                var caracterEscape = esCaracterEscape()
                if (caracterEscape.isNotEmpty()){
                   // lexema += caracterEscape
                }
                else {
                     lexema += caracterActual
                }
                    obtenerSiguienteCaracter()
            }
            if (caracterActual=='^'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.CADENA_CARACTER,filaInicial,columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esLogico():Boolean{
        val verdadero = "SIMON"
        val falso = "NELSON"
        var recorrido = ""
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        if (caracterActual =='S')
            recorrido = verdadero
        else if(caracterActual == 'N')
            recorrido = falso

        for (i in recorrido){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        if(recorrido.equals(lexema) && lexema.isNotEmpty()){
            almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)

            return true
            }
        return false
    }

    fun esEntero():Boolean{
        if(caracterActual == '@') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            //var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }

    fun esDecimal():Boolean{
        if (caracterActual== '$'){

            var lexema=""
            var filaInicial= filaActual
            var columnaInicial= columnaActual

            lexema+= caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual=='~'||caracterActual.isDigit()){


            if(caracterActual=='~'){
                lexema+= caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual.isDigit()){
                    lexema+= caracterActual
                    obtenerSiguienteCaracter()
                }
            }else{
                lexema+= caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual.isDigit()){
                    lexema+= caracterActual
                    obtenerSiguienteCaracter()
                }
                if(caracterActual=='~') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

            }
            while(caracterActual.isDigit()){
                lexema+= caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL,filaInicial,columnaInicial)
            return true
        }
}
        return false
    }

    fun esTipoCadena():Boolean{
        val cadena = "PAL"
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        for(i in cadena){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(cadena.equals(lexema)){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }

        return false
    }

    fun esTipoEntero():Boolean{
        val entero = "ENT"
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        for(i in entero){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(entero.equals(lexema)){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }

        return false
    }

    fun esTipoReal():Boolean{
        val real = "REL"
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        for(i in real){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(real.equals(lexema)){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }
        return false
    }

    fun esTipoLogico():Boolean{
        val logico = "LOGI"
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        for(i in logico){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(logico.equals(lexema)){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }
        return false
    }

    fun esConstante():Boolean{
        val constante = "CONS"
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        for(i in constante){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(constante.equals(lexema)){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }

        return false
    }

    fun esConectorY() :Boolean{
        if(caracterActual == '%')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
    


            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esConectorO() :Boolean{
        if(caracterActual == '!')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esNegacion() :Boolean{
        if(caracterActual == '~')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_LOGICO,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esOperadorMatematico () :Boolean{
        if(caracterActual == '&')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' ||caracterActual == '%'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_ARITMETICO, filaInicial,columnaInicial)
                return true
            }else{
            return false}
        }
        return false
    }

    fun esOperadorFinal () :Boolean{
        if(caracterActual == '_'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema,Categoria.OPERADOR_FINAL,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esCiclo():Boolean{
        val ciclo = "WHEN"
        val cicloDo= "DO"
        var recorrido=""
        var lexema = ""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        if (caracterActual =='W')
            recorrido = ciclo
        else if(caracterActual == 'D')
            recorrido = cicloDo

        for(i in recorrido){
            if(i == caracterActual){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }

        if(recorrido.equals(lexema)&& lexema.isNotEmpty()){
            almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)

            return true
        }

        return false
    }

    fun esIdentificador():Boolean{
        var cont = 9
        if(caracterActual.isLowerCase() && caracterActual.isLetter()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLowerCase()  && caracterActual.isLetter() && cont>0 ){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                cont--
            }
            almacenarToken(lexema,Categoria.IDENTIFICADOR,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun obtenerSiguienteCaracter(){
        if(posicionActual==codigoFuente.length-1){
            caracterActual= finCodigo
        }else{
            if(caracterActual=='\n'){
                filaActual++
                columnaActual=0
            }else{
                columnaActual++
            }

            posicionActual++
            caracterActual=codigoFuente[posicionActual]
        }
    }

    fun esMetodo():Boolean{
       if (caracterActual=='|'){
           var lexema = ""
           var filaInicial = filaActual
           var columnaInicial = columnaActual
           var posicionInicial = posicionActual

           lexema += caracterActual
           obtenerSiguienteCaracter()

               if(caracterActual=='M'){
                   lexema += caracterActual
                   obtenerSiguienteCaracter()
               }
               else
               {
                   return false
               }
               if (caracterActual == '|') {
                   lexema += caracterActual
                   obtenerSiguienteCaracter()
                   almacenarToken(lexema, Categoria.RESERVADA, filaInicial, columnaInicial)
                   return true
               }

       }
       return false

    }

    fun esDiferenteIgual():Boolean{
        if (caracterActual == '¬'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != ':'){
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='~'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.OPERADOR_RELACIONAL,filaInicial,columnaInicial)
                return true
            }
            return false
        }
        return false
    }

    fun esImprimir():Boolean{
        if (caracterActual == 'P'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual != 'R'){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='T'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }
            return false
        }
        return false
    }

    fun esLeer():Boolean{
        if (caracterActual == 'R'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual=='D'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.RESERVADA,filaInicial,columnaInicial)
                return true
            }
            else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        return false
    }

    fun esSeparador():Boolean{

        if (caracterActual == ';'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

                almacenarToken(lexema,Categoria.SEPARADOR,filaInicial,columnaInicial)
                return true
            }
        return false
        }

    fun esConcatenación():Boolean{

        if (caracterActual == '+'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema,Categoria.SEPARADOR,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esAbrirArreglo():Boolean{
        if (caracterActual == '¿'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema,Categoria.ABRIR_ARREGLO,filaInicial,columnaInicial)
            return true
        }
        return false

    }

    fun esCerrarArreglo():Boolean{
        if (caracterActual == '?'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema,Categoria.CERRAR_ARREGLO,filaInicial,columnaInicial)
            return true
        }
        return false

    }


}