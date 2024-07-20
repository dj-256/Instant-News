package fr.joeldibasso.instantnews.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import fr.joeldibasso.instantnews.R


val FontFamily.Companion.Roboto: FontFamily
    get() = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_medium, FontWeight.Medium)
    )

val FontFamily.Companion.RobotoCondensed: FontFamily
    get() = FontFamily(
        Font(R.font.roboto_condensed_semi_bold, FontWeight.SemiBold),
    )