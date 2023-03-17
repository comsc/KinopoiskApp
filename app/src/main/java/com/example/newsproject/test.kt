package com.example.newsproject

object A {
    @JvmStatic
    fun main(args: Array<String>) {
        if (one() and two() or one()) {
            print("three")
        }
    }

    private fun two(): Boolean {
        print("two")
        return false
    }

    private fun one(): Boolean {
        print("one")
        return true
    }
}