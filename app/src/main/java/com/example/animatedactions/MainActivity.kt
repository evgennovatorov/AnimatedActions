package com.example.animatedactions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.animatedactions.ui.theme.AnimatedActionsTheme
import com.example.animatedactions.ui.theme.Orange
import com.example.components.AnimatedActions
import com.example.components.models.AnimatedActionModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimatedActionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val items = remember { createList(3).toMutableStateList() }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Change the actions using the buttons below")
        AppButton("Refresh list with a random size") {
            val list = createList((1..MAX_ITEMS).random())
            items.clear()
            items.addAll(list)
        }
        AppButton("Refresh list keeping the same size") {
            val list = createList(items.size)
            items.clear()
            items.addAll(list)
        }
        AppButton("Add item") {
            if (items.size < MAX_ITEMS) {
                items.add(AnimatedActionModel(items.size, getMessage()))
            }
        }
        AppButton("Remove last item") {
            if (items.size > 1) {
                items.removeAt(items.size - 1)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedActions(
            items = items,
            onClick = {}
        )
    }
}

@Composable
private fun AppButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange
        ),
        onClick = onClick,
        content = {
            Text(text)
        }
    )
}

private fun createList(count: Int) = List(count) { index ->
    AnimatedActionModel(
        index,
        getMessage()
    )
}

private fun getMessage() = messages[messages.indices.random()]

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimatedActionsTheme {
        Greeting()
    }
}