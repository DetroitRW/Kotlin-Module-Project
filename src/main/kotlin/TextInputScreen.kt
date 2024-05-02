import java.util.*

fun displayInput(
    title: String,
    scanner: Scanner,
    validate: (String) -> String?,
    handleInput: (String) -> Unit
) {
    println(title)
    var input: String
    do {
        input = scanner.nextLine()
        val error = validate(input)
        if (error != null) {
            println(error)
            continue
        }

        break
    } while (true)

    handleInput(input)
}
