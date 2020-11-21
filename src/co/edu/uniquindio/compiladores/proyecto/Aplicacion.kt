package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico

    fun main(){

    val lexico= AnalizadorLexico("" +
            "REL global :~ $12~5_" +
            "ENT plobal :~ @5_" +
            "|M| funcion ENT \"ENT par\" < " +
                "VI\"~@12 >:~ @6 &+ @5 % @1 <:~ @6 \" <" +
                    "PAL palabra :~ gola\"\"_" +
                    "REL valor :~ RD\"\"_ " +
                    "WHEN\"@12>:~@5\"<" +
                        "RD\"\"_" +
                        "RT variable_" +
                    ">" +
                    "DO<" +
                        "PRT\"^Ground control to Major Tom^\"_" +
                    ">WHEN\"@2 &+ @2 >:~ @5\"" +
                    "CONS ENT ¿@5? array :~  \"@12;@23;SIMON\"_" +
                    "REL ¿@2?¿@2? matriz :~ \" \"@2;SIMON\" ; \"@3;NELSON\" \"_" +
                "> <" +
                    "CONS REL variable_ " +
                    "variable :~ @3 &+ @23 &- $3~5 &/ @5_" +
                    "arreglo :~ \"@12;$32~4;^cadena^\"_" +
                "> " +
            "> " +
            "|M| hola  \"ENT par\" < " +
             "arreglo :~ \"@12;$32~4;^cadena^\"_" +
            ">")
        lexico.analizar()
        //println(lexico.listaTokens)
    val sintaxis= AnalizadorSintactico(lexico.listaTokens)
    println(sintaxis.esUnidadDeCompilacion())
    print("\n")
    println(sintaxis.listaErrores)
    }
/*
//Variable
 var numero= 12

var arreglo= arrayOf(1,2,3,4)
var lista= listOf<Int>(1,2,3,4,5) //Inmutable

 var listaMutable= mutableListOf<Int>(1,2,3,45) //Mutable

 var listaArray= ArrayList<Int>() //Mutable de java
listaArray.add(14)
listaArray.add(31)

for (i in listaArray){
   println("El valor es: ${i}")
}

for ((i,v) in listaArray.withIndex()){
   println("La posición ${i} contiene el valor es: ${v}")
 }
//Constante
 val constante = 13
println(isPrimo(constante))
println(operar(21, 23 ,::restar))

  fun isPrimo (numero:Int) :Boolean{
       for(i in 2.. numero/2){
           if(numero%i==0)
           {
               return false
           }
       }
       return true
   }

   fun operar(a:Int, b:Int, fn:(Int, Int)-> Int):Int{
       return fn(a,b)
   }

   fun sumar(a:Int, b:Int) = a+b
   fun restar(a:Int, b:Int) = a-b

   */