package com.hci.TP3_HCI.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.theme.*

@Composable
fun HomeSecurityCard(
    title: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(colorResource(R.color.device), RoundedCornerShape(12.dp))
            .padding(16.dp)
            .size(width = 180.dp, height = 280.dp)  // Adjust the size as per your requirement
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    color = colorResource(R.color.white),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_device),  // Make sure to use your icon resource
                    contentDescription = null,
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = status,
                color = colorResource(R.color.white),
                fontSize = 48.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeSecurityCardPreview() {
    HomeSecurityCard(
        title = "Home Security",
        status = "On",
        modifier = Modifier.padding(16.dp)
    )
}
