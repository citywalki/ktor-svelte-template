package memos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemosApplication

fun main(args: Array<String>) {
    runApplication<MemosApplication>(*args)
}
