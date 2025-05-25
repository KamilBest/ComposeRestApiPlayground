package com.best.composeRestApiPlayground.ui.screen.search

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import com.best.composeRestApiPlayground.usecase.search.data.PostModel

data class PostUiModel(
    val title: AnnotatedString,
    val body: AnnotatedString? = null
)

fun PostModel.toUiModel(query: String): PostUiModel {
    return PostUiModel(
        title = highlightQuery(this.title, query),
        body = this.body?.let { highlightQuery(it, query) }
    )
}

private fun highlightQuery(text: String, query: String): AnnotatedString {
    val builder = AnnotatedString.Builder()
    val lowerText = text.lowercase()
    val lowerQuery = query.lowercase()
    var start = 0

    while (start < text.length) {
        val index = lowerText.indexOf(lowerQuery, start)
        if (index == -1) {
            builder.append(text.substring(start))
            break
        }
        builder.append(text.substring(start, index))
        builder.withStyle(style = SpanStyle(color = Color.Red)) {
            builder.append(text.substring(index, index + query.length))
        }
        start = index + query.length
    }
    return builder.toAnnotatedString()
}