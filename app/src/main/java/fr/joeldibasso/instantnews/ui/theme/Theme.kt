package fr.joeldibasso.instantnews.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AfricanViolet,
    secondary = CoolGray,
    onSecondary = CoolGray,
    tertiary = MountbattenPink,
    surfaceVariant = SpaceCadet,
    background = RichBlack
)
private val LightColorScheme = lightColorScheme(
    onSurface = SpaceCadet,
    onSecondary = SlateGray,
    primary = RebeccaPurple,
    secondary = CoolGray,
    tertiary = MountbattenPink,
    surfaceVariant = Magnolia,
    background = Lavender3
)

@Composable
fun InstantNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}