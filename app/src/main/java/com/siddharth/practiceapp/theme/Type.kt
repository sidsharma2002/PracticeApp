package com.siddharth.practiceapp.theme

import androidx.compose.material.Typography
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.siddharth.practiceapp.R

// Set of Material typography styles to start with
val Typography = Typography(

    h1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.robotoregular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppinsregular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)