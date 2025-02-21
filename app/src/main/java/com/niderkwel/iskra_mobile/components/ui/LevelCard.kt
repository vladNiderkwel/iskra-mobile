package com.niderkwel.iskra_mobile.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.niderkwel.domain.API_URL
import com.niderkwel.domain.models.Level
import com.niderkwel.iskra_mobile.R

@Composable
fun LevelCard(
    level: Level,
    modifier: Modifier = Modifier,
    brush: Brush? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        level.user.let {
            if (it.photoUrl.length < 64)
                Image(
                    painter = painterResource(
                        id = R.drawable.photo_placeholder
                    ),
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            else
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${API_URL}/images/photos/${it.photoUrl}.jpg")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.photo_placeholder),
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            
            Text(
                text = it.name,
                maxLines = 3,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "${level.current} ур.",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall.copy(
                    brush = brush,
                )
            )
        }
    }
}