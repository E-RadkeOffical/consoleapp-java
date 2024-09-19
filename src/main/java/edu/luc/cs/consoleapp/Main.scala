package edu.luc.cs.consoleapp

import java.util.Scanner
import org.apache.commons.collections4.queue.CircularFifoQueue

object Main {

  val LAST_N_WORDS = 10

  def main(args: Array[String]): Unit = {

    // TODO consider using a command-line option library

    // perform argument validity checking
    if (args.length > 1) {
      System.err.println("usage: ./target/universal/stage/bin/consoleapp [ last_n_words ]")
      System.exit(2)
    }

    var lastNWords = LAST_N_WORDS
    try {
      if (args.length == 1) {
        lastNWords = args(0).toInt
        if (lastNWords < 1) {
          throw new NumberFormatException()
        }
      }
    } catch {
      case ex: NumberFormatException =>
        System.err.println("argument should be a natural number")
        System.exit(4)
    }

    val input = new Scanner(System.in).useDelimiter("(?U)[^\\p{Alpha}0-9']+")
    val queue = new CircularFifoQueue[String](lastNWords)

    while (input.hasNext) {
      val word = input.next()
      queue.add(word) // the oldest item automatically gets evicted
      println(queue)
      // terminate on I/O error such as SIGPIPE
      if (System.out.checkError()) {
        System.exit(1)
      }
    }
  }
}
