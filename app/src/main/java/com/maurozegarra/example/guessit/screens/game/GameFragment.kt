package com.maurozegarra.example.guessit.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.maurozegarra.example.guessit.R
import com.maurozegarra.example.guessit.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    //@formatter:off
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
    //@formatter:on
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Set the ViewModel for DataBinding - this allows the bound layout access to all of the
        // data in the VieWModel
        binding.gameViewModel = viewModel

        /** Setting up LiveData observation relationship **/
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })

        // Sets up event listening to navigate the player when the game is finished
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                val currentScore = viewModel.score.value ?: 0
                val action = GameFragmentDirections.actionGameToScore(currentScore)
                findNavController(this).navigate(action)
                viewModel.onGameFinishComplete()
            }
        })

        return binding.root
    }
}
