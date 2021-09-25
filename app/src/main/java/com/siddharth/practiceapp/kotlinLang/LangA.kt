package com.siddharth.practiceapp.kotlinLang

import kotlinx.coroutines.coroutineScope


/**
 *  this function takes function as an argument
 *  also note that the argument function is coded in the body itself
 */
fun <T> higherOrderFunction(x : T, y:T , mapper : (T) ->T) : T {
    return mapper(x)
}

/**
 *  @param x , integer parameter
 *  @param argFunction , lambda function
 */
fun returningFunction(x : Int, argFunction : (Int)->Int) : (Int,Int) ->Int {
    // tempFunction is a lambda function
    val tempFunction  : (Int,Int) -> Int =  {a,b->
         argFunction((a+b) * x)
    }
    return   tempFunction
}

/**
 *  function object which is of type (Int) -> Int
 */
val lambdaFunction : (Int) -> Int = {
    it+100
}

fun functionA(a : Int) : Int = a+10
val functionB : (Int)->Int = {
    it+10
}

fun main(){
    val newFunction = returningFunction(1,functionB)
    val x = newFunction(1,1)
    println(x)
}

