package com.example.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.components.models.AnimatedActionModel

data class ButtonModel(
    val text: String,
    val visible: Boolean = true
)

@Composable
fun AnimatedActions(
    modifier: Modifier = Modifier,
    items: List<AnimatedActionModel>,
    onClick: (AnimatedActionModel) -> Unit
) {
    val buttons = remember {
        items
            .map { ButtonModel(it.name) }
            .toMutableStateList()
    }

    LaunchedEffect(items.toList()) {
        when {
            buttons.size < items.size -> {
                for (i in buttons.size until items.size) {
                    buttons.add(ButtonModel(items[i].name))
                }
            }
            buttons.size > items.size -> {
                for (i in 0  until buttons.size) {
                    buttons[i] = if (i < items.size) {
                        ButtonModel(items[i].name, true)
                    } else {
                        buttons[i].copy(visible = false)
                    }
                }
            }
            else -> {
                for (i in items.indices) {
                    buttons[i] = ButtonModel(items[i].name, true)
                }
            }
        }
    }

    Column(
        modifier = modifier
            .background(Color.LightGray)
            .padding(12.dp)
    ) {
        buttons.forEachIndexed { index, it ->

            LaunchedEffect(it.visible) {
                if (!it.visible && items.getOrNull(index) != null) {
                    buttons[index] = it.copy(visible = true)
                }
            }
            AnimatedVisibility(it.visible) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        onClick(items[index])
                    }
                ) {
                    Crossfade(it.text) {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}