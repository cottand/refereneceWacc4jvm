@file:Suppress("EXPERIMENTAL_API_USAGE")

package reference

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.InlineDataPart
import com.github.kittinunf.fuel.gson.responseObject
import com.google.gson.Gson
import java.io.File

object ReferenceWACC {

  private val gson by lazy { Gson() }

  private const val delimiters = "==========================================================="

  /**
   * Compiles and runs [prog]  passing it [stdin].
   *
   * IMPORTANT: assumes the program is well formed, ie, there can be no compile errors. This tool should be used to
   * see the reference compiler's assembly code and to check their output, not to check semantic/syntactic checking.
   */
  @JvmStatic
  fun compile(prog: File, stdin: String): RefAnswer? {
    val emulatorUrl = "https://teaching.doc.ic.ac.uk/wacc_compiler/run.cgi"
    val out =
      queryReference<CompilerReply>(prog, emulatorUrl, stdin, "-x", "-a")?.compiler_out?.lines()
        ?.filter { it.isNotEmpty() }
        ?.toList()
    val assembly = out
      ?.asSequence()
      ?.dropWhile { delimiters !in it }
      ?.drop(1)
      ?.takeWhile { delimiters !in it }
      ?.map { str -> str.dropWhile { chr -> chr.isDigit() }.trim() }
      ?.filter { it.isNotBlank() }?.toList()?.joinLines()
    val runtimeOut = out
      ?.dropWhile { "-- Executing..." !in it }
      ?.drop(2)
      ?.takeWhile { "The exit code is" !in it }
      ?.map { if (delimiters in it) it.replace(delimiters, "") else it }
      ?.joinLines()
    val code = out?.last { "The exit code is" in it }?.filter { str -> str.isDigit() }?.toInt()
    return if (out == null) null else RefAnswer(assembly!!, runtimeOut!!, code!!)
  }

  /**
   * Emulates the assembly file [armProg] with standard input [stdin]
   *
   * Returns null if the query failed, or the serialised JSON of the reference compiler otherwise.
   */
  @JvmStatic
  fun emulate(armProg: File, stdin: String): EmulatorReply? {
    val emulatorUrl = "https://teaching.doc.ic.ac.uk/wacc_compiler/emulate.cgi"
    return queryReference(armProg, emulatorUrl, stdin = stdin)
  }

  private inline fun <reified T : Any> queryReference(f: File, url: String, stdin: String = "", vararg ops: String) =
    ops.asSequence()
      .map { InlineDataPart(it, "options[]") }
      .toList().toTypedArray()
      .let { options ->
        Fuel.upload(url)
          .add(FileDataPart(f, "testfile", f.name, "application/octet-stream"))
          .add(*options)
          .apply { if (stdin.isNotBlank()) add(InlineDataPart(stdin, "stdin")) }
          .responseObject<T>(gson).third
          .component1()
      }

  private fun Iterable<String>.joinLines() = joinToString(separator = "\n")
}

/**
 * Represents an answer from the reference compiler. [out] represents the compiled assembly,
 * or the received errors.
 */
data class RefAnswer(val assembly: String, val out: String, val code: Int)

/**
 * Serialised JSON produced by the reference emulator
 *
 * [assemble_out] is the output of the reference assembler
 * [emulator_out] is the output of the emulated code
 * [emulator_exit] is the exit code of the emulated code
 */
data class EmulatorReply(
  val test: String,
  val upload: String,
  val assemble_out: String,
  val emulator_out: String,
  val emulator_exit: String
)

/**
 * Serialised JSON produced by the reference compiler
 */
data class CompilerReply(val test: String, val upload: String, val compiler_out: String)

