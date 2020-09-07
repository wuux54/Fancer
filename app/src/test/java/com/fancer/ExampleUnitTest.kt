package com.fancer

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test() {
        runBlocking {
            launch {
                delay(200L)
                println("Task from runBlocking")
            }

            coroutineScope {
                // 创建一个协程作用域
                launch {
                    delay(500L)
                    println("Task from nested launch")
                }

                delay(100L)
                println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
            }

            println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
        }
    }

    @Test
    fun test2() {
//        runBlocking {
//            println("launch scope is start")
//            try {
//                launch {
//                    delay(2000)
//                    println("launch scope is next")
//
//                    throw RuntimeException("ext")
//                }
//            } catch (e: RuntimeException) {
//                println("ext: ${e.message}")
//            }
//
//            println("launch scope is over") // 这一行在内嵌 launch 执行完毕后才输出
//        }

        var string:String = "launch"
        string.replace("(?<=[\\S]{1})\\S(?=[\\S]{0})".toRegex(), "*")
        println(string)
    }


}
