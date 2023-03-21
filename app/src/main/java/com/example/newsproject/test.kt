package com.example.newsproject

import android.util.Log

fun main() {
    val url = "https://www.youtube.com/embed/H1qZL--IEzE"
    if (url?.contains("embed") == true){
      val url2 =  url.substringAfterLast("embed/")
        println(url2)}
}






//val human = readln().toList().filter { it.isDigit() }.asSequence().zipWithNext { a, b -> a <= b }
//    .zipWithNext { a, b -> a >= b  }.all { it }
//if (human) println("YES")
//else println("NO")

//Test2
//val human = readln().toList().filter { it.isDigit() }
//val result = human[0].digitToInt() * human[2].digitToInt()
//var temp = 0
//if (result%human[1].digitToInt() == 0){}
//else temp = 1
//val result2 = result / human[1].digitToInt() + temp
//println(result2)

//Test3
//val lengthString = readln().toInt()
//val string = readln()
//var result = -1
//for (i in 4 .. lengthString) {
//    for (index in 0 .. lengthString - i) {
//        val subStr = string.substring(index, index + i)
//        if (subStr.contains("a") && subStr.contains("b") &&
//            subStr.contains("c") && subStr.contains("d")
//        ) {
//            result = i
//            break
//        }
//    }
//    if (result != -1) break
//}
//println(result)

//test5
//val lengthArray = readln().toInt()
//val array = readln().split(" ").map { it.toInt() }
//var result:MutableList<List<Int>> = mutableListOf()
//for (i in 2..lengthArray)
//for(index in 0..lengthArray - i)
//{
//    val subStr = array.subList(index, index + i)
//    if (subStr !in result) {
//        if (subStr.sum() == 0) {
//            result.add(result.size, subStr)
//        }
//        else{
//            for(ind in 0 until result.size){
//                if(subStr.containsAll(result[ind])){
//                    if (subStr !in result){
//                        result.add(result.size, subStr)
//                    }
//
//                }
//
//            }
//
//        }
//    }
//}
//
//println(result.size)