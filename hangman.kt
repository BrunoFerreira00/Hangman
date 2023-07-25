    import java.io.File

    data class Lifes(var life: Int = 6)

    fun languageChoice(): String {
        println("Do you wish a Portuguese or an English Word?")
        while (true) {
            val language = readLine()?.lowercase()
    
            if (language == "portuguese" || language == "english") {
                return language
            } else if (language == "exit") {
                quit()
            } else {
                println("Invalid choice. Please enter 'Portuguese' or 'English', or type 'exit' to leave the game.")
            }
        }
    }
    
    fun addWord(): List<String> {
        val words = mutableListOf<String>()
        val file = File(languageChoice()+"Words.txt")
        file.forEachLine {
            words.add(it)
        }
        return words
    }

    fun randomWord(): String = addWord().random()



    fun readLetter(): Char? {
        while (true) {
            println("Please enter a letter:")
            var input = readLine()?.lowercase()
    
            if (input == "exit") {
                quit() 
            }
    
            val letter = input?.firstOrNull()
            if (letter != null && letter in 'a'..'z') {
                return letter
            } else {
                println("Invalid input. Please enter a valid letter.")
            }
        }
    }
    
    

    fun hideWord(word: String): MutableList<Char> {
        return List(word.length) { '_' }.toMutableList()
    }



    fun guessLetter(word: String, hiddenWord: MutableList<Char>, lifes: Lifes) {
        while (true) {
            val letter = readLetter() ?: return 
            if (letter in word) {
                for (index in word.indices) {
                    if (word[index] == letter) {
                        hiddenWord[index] = letter
                    }
                }
                break
            } else {
                lifes.life -= 1
                println("You lost a life! You have ${lifes.life} lifes left!")
                break
            }
        }
    }
    

    fun wordDiscovered(word: String, lifes: Lifes) {
        var hiddenWord = hideWord(word)
        while (hiddenWord.joinToString("") != word && lifes.life > 0) {
            guessLetter(word, hiddenWord, lifes)
            println(hiddenWord.joinToString(" "))
        }
        if (lifes.life == 0) {
            return println("You lost! The word was: $word")
        }
        println("Congratulations! You discovered the word: $word")
    }

    fun quit() {
        println("Exiting the game...")
        System.exit(0)
    }

    fun main() {
        println("At any point you can type 'exit' to leave the game")
        val word = randomWord()
        val lifes = Lifes()
        wordDiscovered(word, lifes)
    }
