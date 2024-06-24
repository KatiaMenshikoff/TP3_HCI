package com.hci.TP3_HCI.ui.sprinkler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R

@Composable
fun SprinklerScreen(
    deviceId: String,
//    viewModel: SprinklerViewModel = viewModel(factory = getViewModelFactory()),
) {
//    viewModel.setCurrentDevice(deviceId)
//    val uiState by viewModel.uiState.collectAsState()
    var quantity by remember { mutableStateOf(0) }
    var unit by remember { mutableStateOf("ml") }
    var showInHome by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.background))
                .padding(16.dp)
        ) {
            // Device Name and Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = stringResource(id = R.string.sprinkler_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Icon(painter = painterResource(id = R.drawable.icon_sprinkler), contentDescription = "Sprinkler Icon", tint = Color.Gray)

            }


            Text(
                text = stringResource(id = R.string.current_status, "closed"),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Pump Water Controls
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = quantity.toString(),
                    onValueChange = { quantity = it.toIntOrNull() ?: 0 },
                    label = { Text(text = stringResource(id = R.string.quantity)) },
                    modifier = Modifier.weight(1f)
                )

            }
            Spacer(modifier = Modifier.width(15.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { unit = "ml" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (unit == "ml") colorResource(id = R.color.pinkButton) else Color.LightGray
                    )
                ) {
                    Text(text = "ml", fontSize = 20.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { unit = "l" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (unit == "l") colorResource(id = R.color.pinkButton) else Color.LightGray
                    )
                ) {
                    Text(text = "l", fontSize = 20.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { unit = "cl" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (unit == "cl") colorResource(id = R.color.pinkButton) else Color.LightGray
                    )
                ) {
                    Text(text = "cl", fontSize = 20.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { unit = "dcl" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (unit == "dcl") colorResource(id = R.color.pinkButton) else Color.LightGray
                    )
                ) {
                    Text(text = "dcl", fontSize = 20.sp, color = Color.Black)
                }

        }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* handle dispense water */ },
                modifier = Modifier.fillMaxWidth() ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button)
                ),
            ) {
                Text(text = stringResource(id = R.string.dispense_water), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            // Show in Home
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showInHome,
                    onCheckedChange = { showInHome = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.show_in_home), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            // Delete Button
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { /* handle delete */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(painter = painterResource(id = R.drawable.icon_delete), contentDescription = stringResource(id = R.string.delete), tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.delete), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SprinklerScreenPreview() {
    SprinklerScreen(
        deviceId = "1",
    )
}
