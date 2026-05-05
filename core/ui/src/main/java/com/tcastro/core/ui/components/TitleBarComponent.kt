package com.tcastro.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tcastro.core.ui.theme.Dimen


@Composable
fun TitleBarComponent(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(Dimen.Spacing.defaultPlus)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f)
        )

    }
}