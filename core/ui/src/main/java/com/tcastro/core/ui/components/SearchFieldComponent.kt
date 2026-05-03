package com.tcastro.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tcastro.core.ui.theme.Dimen
import com.tcastro.core.ui.theme.SwordCatChallengeTheme


@Composable
fun SearchFieldComponent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String = "Search..."
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(Dimen.Dimensions.medium),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldComponentPreview(){
    SwordCatChallengeTheme() {
        SearchFieldComponent(
            query = "",
            onQueryChange = { },
            modifier = Modifier,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SearchFieldComponentFilledPreview(){
    SwordCatChallengeTheme() {
        SearchFieldComponent(
            query = "test",
            onQueryChange = { },
            modifier = Modifier,
        )
    }
}