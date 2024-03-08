package jp.aqua.passwordmanager.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.FragmentActivity
import jp.aqua.passwordmanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1


@Composable
fun AuthDialog(
    masterPassword: String?,
    coroutineScope: CoroutineScope,
    setMasterPassword: KSuspendFunction1<String, Unit>
) {
    var isAuthed by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current as FragmentActivity
    if (!isAuthed) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {}
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (masterPassword == null) {
                    var newMasterPassword by rememberSaveable { mutableStateOf("") }

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Enter master password",
                        textAlign = TextAlign.Center
                    )
                    TextField(
                        modifier = Modifier.padding(16.dp),
                        value = newMasterPassword,
                        onValueChange = { newMasterPassword = it }
                    )
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            coroutineScope.launch {
                                setMasterPassword(newMasterPassword)
                            }
                        }
                    ) {
                        Text("Enter")
                    }

                } else {
                    var currentMasterPassword by rememberSaveable { mutableStateOf("") }
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Enter password",
                        textAlign = TextAlign.Center
                    )

                    TextField(
                        modifier = Modifier.padding(16.dp),
                        value = currentMasterPassword,
                        onValueChange = { currentMasterPassword = it }
                    )

                    IconButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            authenticateWithBiometric(
                                context,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Successfully authed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isAuthed = true
                                }
                            )
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_fingerprint_24),
                            stringResource(R.string.use_fingerprint),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            if(currentMasterPassword == masterPassword) {
                                Toast.makeText(
                                    context,
                                    "Successfully authed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isAuthed = true
                            } else {
                                Toast.makeText(
                                    context,
                                    "Incorrect password",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Done,
                            stringResource(R.string.use_master_password)
                        )
                    }
                }
            }

        }
    }
}