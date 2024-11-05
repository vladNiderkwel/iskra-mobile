package com.niderkwel.iskra_mobile.components.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.IskraBrand
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.IskraTextField

@Composable
fun LoginScreen(
    state: State<LoginState>,
    onEmailChanged: (email: String) -> Unit = {},
    onPasswordChanged: (password: String) -> Unit = {},
    navigateToRegistration: () -> Unit = {},
    onLogin: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        IskraBrand()

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Вход",
            style = MaterialTheme.typography.titleLarge,
        )

        IskraTextField(
            value = state.value.email,
            onValueChange = { onEmailChanged(it) },
            label = "Почта",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
        )

        IskraTextField(
            value = state.value.password,
            onValueChange = { onPasswordChanged(it) },
            label = "Пароль",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
        )

        Button(
            onClick = { onLogin() },
            enabled = state.value.uiStatus != UiStatus.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (state.value.uiStatus == UiStatus.Loading) {
                IskraCircularProgressBar(
                    size = 20.dp,
                    strokeWidth = 3.dp
                )
            } else Text("Войти")
        }

        Text(
            text = "или",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceTint,
        )

        OutlinedButton(
            onClick = { navigateToRegistration() },
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Создать аккаунт")
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun screen() {
    val viewModel = viewModel<LoginViewModel>()
    LoginScreen(
        state = viewModel.state.collectAsState(),
    )
}