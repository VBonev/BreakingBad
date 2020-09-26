package com.today.breakingbad.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.squareup.picasso.Picasso
import com.today.breakingbad.R
import kotlinx.android.synthetic.main.fragment_character_details.*

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)

        toolbar.setupWithNavController(
            findNavController(),
            appBarConfiguration
        )
        arguments?.let {

            val selectedCharacter = CharacterDetailsFragmentArgs.fromBundle(it).character
            details_character_name.text = String.format(
                getString(R.string.character_name_label),
                selectedCharacter.name
            )
            details_character_nickname.text = String.format(
                getString(R.string.character_nickname_label),
                selectedCharacter.nickname
            )
            details_character_status.text = String.format(
                getString(R.string.character_status_label),
                selectedCharacter.status
            )
            details_character_occupation.text = String.format(
                getString(R.string.character_occupation_label),
                selectedCharacter.occupation?.joinToString()
            )
            details_character_season_appearance.text = String.format(
                getString(R.string.character_season_appearance_label),
                selectedCharacter.appearance?.joinToString(separator = " . ")
            )
            Picasso.get().load(selectedCharacter.img).into(details_character_image)
        }
    }
}