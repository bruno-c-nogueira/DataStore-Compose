package com.example.datastore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastore.MainViewModel
import com.example.datastore.components.AlertDialogExample
import com.example.datastore.components.InputField
import com.example.datastore.data.UserDTO


@Composable
fun LoginScreen(mainViewModel: MainViewModel = viewModel()) {
    var password by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    val collector: UserDTO? by mainViewModel.getUserFromPreferencesStore(LocalContext.current)
        .collectAsState(initial = null)
    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "${collector?.userName}, be welcome =]",
                dialogText = "${collector?.password}, do not share it okay !",
                icon = Icons.Default.Info
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            InputField("User") {
                user = it
            }
        }
        item {
            InputField("Password") {
                password = it
            }
        }
        item {
            Column(modifier = Modifier.padding(start = 250.dp)) {
                Checkbox(checked = isChecked, onCheckedChange = {
                    isChecked = !isChecked
                })
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val context = LocalContext.current
                Button(onClick = {
                    if (isChecked) {
                        mainViewModel.incrementCounter(context = context, UserDTO(user, password))
                        openAlertDialog.value = true
                    }
                })
                { Text("Login") }
            }
        }
    }

}