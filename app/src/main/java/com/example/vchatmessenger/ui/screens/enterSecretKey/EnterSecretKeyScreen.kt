package com.example.vchatmessenger.ui.screens.enterSecretKey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vchatmessenger.R
import com.example.vchatmessenger.domain.navigation.ScreensRouts
import com.example.vchatmessenger.ui.components.VchatAlertDialog
import com.example.vchatmessenger.ui.components.VchatBackIconButton
import com.example.vchatmessenger.ui.components.VchatInfoText
import com.example.vchatmessenger.ui.components.VchatLoadingScreen
import com.example.vchatmessenger.ui.components.VchatNextFloatingActionButton
import com.example.vchatmessenger.ui.components.VchatSecretKeyInputBox
import com.example.vchatmessenger.ui.theme.getMainAppColor
import com.example.vchatmessenger.ui.theme.getSecondAppColor

@Composable
fun EnterSecretKeyScreen(
    vm: EnterSecretKeyViewModel,
    navController: NavHostController
) {
    val state = vm.state
    if (!state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(getMainAppColor())
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.85f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    VchatBackIconButton("enter_password", navController)
                    Spacer(modifier = Modifier.height(50.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.password_recovery),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = getSecondAppColor(),
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center,
                            lineHeight = 40.sp
                        )
                        Spacer(modifier = Modifier.height(70.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(items = state.secretKeyWordsNumbers) { secretKeyWordNumber ->
                                val secretKeyWordIndex =
                                    state.secretKeyWordsNumbers.indexOf(secretKeyWordNumber)
                                VchatSecretKeyInputBox(
                                    number = secretKeyWordNumber + 1,
                                    secretKey = state.secretKey[secretKeyWordIndex],
                                    onSecretKeyChange = { secretKeyWord ->
                                        vm.updateSecretKey(secretKeyWordIndex, secretKeyWord)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.15f),
                contentAlignment = Alignment.BottomEnd
            ) {
                VchatNextFloatingActionButton {
                    vm.buttonNextPressed(navController)
                }
            }
            VchatInfoText(
                "Вспомнили пароль? ",
                "Войти",
                ScreensRouts.EnterPassword.route,
                navController
            )

            when {
                state.showErrorDialog -> {
                    VchatAlertDialog(
                        onDismissRequest = { vm.setShowErrorDialog(false) },
                        onConfirmation = { vm.setShowErrorDialog(false) },
                        dialogTitle = "Произошла ошибка",
                        dialogText = state.errorSecretKey
                    )
                }
            }
        }
    } else {
        VchatLoadingScreen()
    }
}
