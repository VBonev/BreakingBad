package com.today.breakingbad.details

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
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

        toolbar.setupWithNavController(
            findNavController(),
            AppBarConfiguration(findNavController().graph)
        )
        arguments?.let {
            val selectedCharacter = CharacterDetailsFragmentArgs.fromBundle(it).character

            toolbar.title = selectedCharacter.name

            details_character_birthday.setLabelAndValue(
                getString(R.string.birthday_label),
                selectedCharacter.birthday
            )
            details_character_occupation.setLabelAndValue(
                getString(R.string.occupation_label),
                selectedCharacter.occupation?.joinToString()
            )

            details_character_nickname.setLabelAndValue(
                getString(R.string.nickname_label),
                selectedCharacter.nickname
            )

            details_character_status.setLabelAndValue(
                getString(R.string.status_label),
                selectedCharacter.status
            )
            details_character_season_appearance.setLabelAndValue(
                getString(R.string.seasons_label),
                selectedCharacter.appearance?.joinToString(separator = ", ")
            )

            Picasso.get().load(selectedCharacter.img).into(details_character_image)
        }
    }


    private fun TextView.setLabelAndValue(label: String, value: String?) {
        this.text = prettifyText(
            String.format(
                label,
                value
            ), label.length
        )
    }

    private fun prettifyText(text: String, length: Int): CharSequence? {

        val spannable = SpannableString(text)
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0, length - PLACEHOLDER_SIZE,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val foregroundColorSpan =
            if (text.contains("Unknown", ignoreCase = true)) {
                ForegroundColorSpan(Color.RED)
            } else {
                ForegroundColorSpan(Color.BLUE)
            }
        spannable.setSpan(
            foregroundColorSpan,
            length - PLACEHOLDER_SIZE, text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    companion object {
        private const val PLACEHOLDER_SIZE = 2
    }
}