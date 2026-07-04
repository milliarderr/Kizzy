package com.my.kizzy.feature_custom_rpc.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.kizzy.domain.model.rpc.RpcConfig
import com.my.kizzy.feature_profile.ui.component.ActivityRow
import com.my.kizzy.resources.R

private val DISCORD_BG = Color(0xFF313338)
private val DISCORD_CARD = Color(0xFF2B2D31)
private val DISCORD_TEXT = Color(0xFFDBDBDB)
private val DISCORD_SUBTEXT = Color(0xFFB5BAC1)

@Composable
fun RpcLivePreview(
    rpcConfig: RpcConfig,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(true) }

    val showTs = !rpcConfig.timestampsStart.isNullOrEmpty() ||
            !rpcConfig.timestampsStop.isNullOrEmpty()

    val typeLabel = when (rpcConfig.type?.toIntOrNull()) {
        1 -> stringResource(R.string.activity_streaming_title, rpcConfig.name.orEmpty())
        2 -> stringResource(R.string.activity_listening_title, rpcConfig.name.orEmpty().ifEmpty { "music" })
        3 -> stringResource(R.string.activity_watching_title, rpcConfig.name.orEmpty().ifEmpty { "video" })
        5 -> stringResource(R.string.activity_competiting_title, rpcConfig.name.orEmpty())
        else -> stringResource(R.string.activity_playing_title)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(DISCORD_BG)
    ) {
        // Header row — toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PREVIEW",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = DISCORD_SUBTEXT
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = DISCORD_SUBTEXT
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DISCORD_CARD)
                    .padding(bottom = 8.dp)
            ) {
                // "PLAYING A GAME" section header
                Text(
                    text = typeLabel.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = DISCORD_TEXT,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp)
                )

                ActivityRow(
                    rpcConfig = rpcConfig,
                    showTs = showTs,
                    special = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}
