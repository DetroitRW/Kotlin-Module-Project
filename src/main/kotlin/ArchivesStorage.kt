class ArchivesStorage {
    private val archives = mutableListOf<Archive>()

    fun addArchive(title: String) {
        archives.add(Archive(title = title, notes = mutableListOf()))
    }

    fun getAllArchives(): List<Archive> = archives

    fun addNote(title: String, text: String, archive: Archive) {
        val note = Note(title, text)
        archive.notes.add(note)
    }
}