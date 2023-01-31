package com.example.bookcollectionapp.book_feature.presentation.book_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcollectionapp.common.TestTags
import com.example.bookcollectionapp.ui.theme.Red

@Composable
fun DetailsListDateItem(
    date: String
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(
                vertical = 7.dp
            )
    ) {
        Text(
            text = "Date Added",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                tint = Red
            )

            Text(
                text = date,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .testTag(TestTags.BOOK_DETAILS_DATE)
            )
        }
    }
}