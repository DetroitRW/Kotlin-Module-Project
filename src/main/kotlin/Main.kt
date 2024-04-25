import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val archivesStorage = ArchivesStorage()

    var isComplete = false
    while (!isComplete) {
        val options = makeArchivesListOptions(
            archives = archivesStorage.getAllArchives(),
            showCreateArchive = { displayCreateArchive(scanner, archivesStorage) },
            exit = { isComplete = true },
            showArchive = {
                var isComplete = false
                while (!isComplete) {
                    displayArchive(
                        it,
                        scanner,
                        exit = { isComplete = true },
                        createNote = {
                            displayCreateNote(scanner, it, archivesStorage)
                        },
                    )
                }
            }
        )

        displayOptionsScreen("Список архивов:", options, scanner)
    }
}

private fun displayArchive(
    archive: Archive,
    scanner: Scanner,
    createNote: () -> Unit,
    exit: () -> Unit,
) {
    val options = makeArchiveDetailsOptions(createNote,
        {
            println("Заметка ${it.title}")
            println(it.text)
        },
        exit, archive)
    displayOptionsScreen("Архив ${archive.title}:", options, scanner)
}

private fun makeArchiveDetailsOptions(
    createNote: () -> Unit,
    showNote: (Note) -> Unit,
    exit: () -> Unit,
    archive: Archive
): List<Option> {
    val options = mutableListOf(
        Option("Создать заметку", createNote)
    )

    for (i in archive.notes.indices) {
        val option = Option(title = archive.notes[i].title) {
            showNote(archive.notes[i])
        }
        options.add(option)
    }

    options.add(Option("Выход", exit))
    return options
}

private fun displayCreateArchive(scanner: Scanner, storage: ArchivesStorage) {
    val title = "Введите название нового архива:"
    val validate: (String) -> String? = {
        if (it.isEmpty()) {
            "Нельзя создать архив с пустым именем:"
        } else {
            null
        }
    }

    val handleInput: (String) -> Unit = {
        storage.addArchive(it)
    }

    displayInput(title, scanner, validate, handleInput)
}

private fun makeArchivesListOptions(
    archives: List<Archive>,
    showCreateArchive: () -> Unit,
    showArchive: (Archive) -> Unit,
    exit: () -> Unit,
): List<Option> {
    val list = mutableListOf(
        Option("Создать архив", showCreateArchive)
    )

    for (archive in archives) {
        list.add(
            Option(archive.title) { showArchive(archive) }
        )
    }

    list.add(
        Option("Выход", exit)
    )
    return list
}