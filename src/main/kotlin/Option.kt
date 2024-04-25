import java.util.*

class Option(val title: String, val select: () -> Unit)

fun displayOptionsScreen(
    title: String,
    options: List<Option>,
    scanner: Scanner
) {
    println(title)
    displayOptions(options)

    val selectedOption = scanner.nextLine().toIntOrNull()
    if (selectedOption == null || !options.indices.contains(selectedOption)) {
        println("Неверный выбор. Пожалуйста, введите правильное число.")
        return
    }

    options[selectedOption].select()
}

private fun displayOptions(options: List<Option>) {
    for (i in options.indices) {
        println("$i: ${options[i].title}")
    }
}

fun displayCreateNote(scanner: Scanner, archive: Archive, archivesStorage: ArchivesStorage) {
    displayCreateNoteTitle(scanner) { title ->
        displayCreateNoteText(scanner) { text ->
            archivesStorage.addNote(title, text, archive)
        }
    }
}

private fun displayCreateNoteTitle(scanner: Scanner, handleInput: (String) -> Unit) {
    val title = "Введите название новой заметки:"
    val validate: (String) -> String? = {
        if (it.isEmpty()) {
            "Нельзя создать заметку с пустым именем:"
        } else {
            null
        }
    }

    displayInput(title, scanner, validate, handleInput)
}

private fun displayCreateNoteText(scanner: Scanner, handleInput: (String) -> Unit) {
    val title = "Введите текст новой заметки:"
    val validate: (String) -> String? = {
        if (it.isEmpty()) {
            "Нельзя создать заметку с пустым текстом:"
        } else {
            null
        }
    }

    displayInput(title, scanner, validate, handleInput)
}

