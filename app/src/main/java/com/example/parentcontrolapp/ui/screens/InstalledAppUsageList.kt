package com.example.parentcontrolapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parentcontrolapp.model.InstalledApp

@Composable
fun InstalledAppsUsageList(apps: ArrayList<InstalledApp>) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 0.dp)
    ) {
        items(apps.size) { idx ->
            AppList(metadata = apps[idx])
        }
    }
}