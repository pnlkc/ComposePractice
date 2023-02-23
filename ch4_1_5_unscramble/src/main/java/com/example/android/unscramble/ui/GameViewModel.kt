package com.example.android.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    // StateFlow는 새로운 상태 업데이트를 내보내는(emit) Observable(관찰 가능한) 데이터 홀더 Flow이다.
    // 변경 가능한 상태를 업데이트하고 Flow에 전송하려면 MutableStateFlow를 사용하면 된다.
    private val _uiState = MutableStateFlow(GameUiState())

    // 읽기 전용으로 변경 ViewModel 외부에서 직접적인 값(_uiState)을 수정하지 못하도록 하는 것이 안전함
    // LiveData 사용방법과 유사함
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // 이미 사용된 단어용 리스트
    private var usedWords: MutableSet<String> = mutableSetOf()

    // 글자의 순서가 섞인 현재 단어용 변수
    private lateinit var currentWord: String

    // 사용자의 입력 단어를 저장할 변수
    var userGuess by mutableStateOf("")
        private set

    // GameViewModel 초기화
    init {
        resetGame()
    }

    // 전체 단어 목록에서 랜덤한 단어를 선택하는 기능
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }

    // 선택된 단어를 섞는 기능
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        do {
            tempWord.shuffle()
        }
        while (String(tempWord) == word)
        return String(tempWord)
    }

    // 게임 초기화 기능
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    // 사용자의 입력 단어를 가져오는 기능
    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    // 사용자 입력 단어 정답 확인 기능
    fun checkUserGuess() {
        if(userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }

    // 정답이면 단어 수와 스코어 올리는 기능
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS) {
            _uiState.update {currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentWordCount = currentState.currentWordCount.inc(),
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore
                )
            }
        }
    }

    // 문제를 스킵하는 기능
    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }
}