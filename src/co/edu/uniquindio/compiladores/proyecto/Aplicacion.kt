package co.edu.uniquindio.compiladores.proyecto

import co.edu.uniquindio.compiladores.proyecto.lexico.AnalizadorLexico

// Min 32 vid 2
    fun main(){

    val lexico= AnalizadorLexico("@123 a $12~^asdasd12^ nelson simon @31")
        lexico.analizar()
        print(lexico.listaTokens)
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