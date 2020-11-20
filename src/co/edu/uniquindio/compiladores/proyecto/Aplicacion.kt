package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.proyecto.sintaxis.AnalizadorSintactico

// Min 32 vid 2
    fun main(){

    val lexico= AnalizadorLexico("" +
            "|M| funcion ENT \"ENT par\" < " +
                "VI\"@12 >:~ @6 &+ @5 % @1 <:~ @6 \" <" +
                    "CONS ENT variable_ " +
                    "PAL variablo_" +
                    "CONS ENT ¿@5? array :~  \"@12;@23;SIMON\"_" +
                    "REL ¿@9? arraos_" +
                "> <" +
                    "CONS REL variable_ " +
                    "variable :~ @3 &+ @23 &- $3~5 &/ @5_" +
                    "cadenita :~ ^esto es una cadenita^_" +
                    "val :~ variab_" +
                    "arreglo :~ \"@12;$32~4;^cadena^\"_" +
                    "PAL palabra :~ ^hola mundo^_" +
                "> " +
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