package com.niderkwel.iskra_mobile.components.screens.profile.edit

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.niderkwel.domain.API_URL
import com.niderkwel.iskra_mobile.R
import com.niderkwel.iskra_mobile.components.UiStatus
import com.niderkwel.iskra_mobile.components.ui.ErrorMessage
import com.niderkwel.iskra_mobile.components.ui.IskraCircularProgressBar
import com.niderkwel.iskra_mobile.components.ui.IskraTextField
import java.nio.file.Path
import kotlin.io.path.outputStream

@Composable
fun ProfileEditScreen(
    state: State<ProfileEditState>,
    contentResolver: ContentResolver,
    onSave: () -> Unit,
    onEmailUpdate: (value: String) -> Unit,
    onNameUpdate: (value: String) -> Unit,
    onImageUpdate: (file: Path, value: Uri) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val file = kotlin.io.path.createTempFile()
            contentResolver.openInputStream(uri).use { input ->
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }
            }
            onImageUpdate(file, uri)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (state.value.uiStatus == UiStatus.Loading)
            IskraCircularProgressBar(
                modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                )
            )
        else state.value.user?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp, Alignment.CenterHorizontally
                ),
                modifier = Modifier.fillMaxWidth()
            ) {

                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(
                            if (state.value.image == null)
                                "${API_URL}/images/photos/${it.photoUrl}.jpg"
                            else
                                state.value.image
                        )
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .build(),
                    placeholder = painterResource(R.drawable.photo_placeholder),
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )

                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_pick_image),
                        contentDescription = "Выбрать изображение",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            IskraTextField(
                value = it.name,
                onValueChange = { value -> onNameUpdate(value) },
                modifier = Modifier.fillMaxWidth()
            )

            IskraTextField(
                value = it.email,
                onValueChange = { value -> onEmailUpdate(value) },
                modifier = Modifier.fillMaxWidth()
            )

            Button({ onSave() }) {
                Text("Сохранить")
            }
        }
    }

    if (state.value.uiStatus is UiStatus.Error) ErrorMessage()
}