package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcollectionapp.common.TestTags
import com.example.bookcollectionapp.ui.theme.Yellow

@Composable
fun DetailsListRatingItem(
    rating: Int
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(
                vertical = 7.dp
            )
    ) {
        Text(
            text = "Rating",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (star in 1..rating) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Yellow,
                    modifier = Modifier.testTag(TestTags.BOOK_DETAILS_RATING)
                )
            }
        }
    }
}