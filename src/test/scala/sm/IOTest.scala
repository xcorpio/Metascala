package sm

import org.scalatest.FreeSpec

import sm.Gen._
import util.{Failure, Try}

class IOTest extends FreeSpec with Util{


  implicit val intAll10 = 10 ** Gen.intAll

  "primitives" - {
    val buffer = new BufferLog(4000)
    val tester = new Tester("sm.io.Primitives", buffer)
    "retInt" in tester.run("retInt")
    "retDouble" in tester.run("retDouble")
    "argInt" in tester.run("argInt", 10)
    "argDouble" in tester.run("argDouble", 10.01)
    "multiArgD" in tester.run("multiArgD", 27, 3.14)
    "multiArgI" in tester.run("multiArgI", 27, 3.14)

    "strings" in {try{
      tester.run("strings", "mooo")

    }catch { case e =>
      buffer.lines.foreach(println)
      throw e
    }}
  }
  "exceptions" -{
    val tester = new Tester("sm.io.Exceptions")
    "runtime" in {
      val x = Try(tester.svm.invoke("sm.io.Exceptions", "runtime", Nil))

      val Failure(UncaughtVmException(_, _, _, _)) = x
    }
  }

}

