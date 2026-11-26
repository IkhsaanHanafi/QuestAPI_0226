package com.example.perte11.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perte11.modeldata.DataSiswa
import com.example.perte11.uicontroller.route.DestinasiHome
import com.example.perte11.viewmodel.HomeViewModel
import com.example.perte11.viewmodel.PenyediaViewModel
import com.example.perte11.viewmodel.StatusUiSiswa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Muat ulang daftar setiap kali layar Home tampil (termasuk setelah kembali dari Entry)
    LaunchedEffect(Unit) {
        viewModel.loadSiswa()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Siswa")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            statusUiSiswa = viewModel.statusUiSiswa,
            retryAction = viewModel::loadSiswa,
            contentPadding = innerPadding
        )
    }
}

@Composable
fun HomeStatus(
    statusUiSiswa: StatusUiSiswa,
    retryAction: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    when (statusUiSiswa) {
        is StatusUiSiswa.Loading -> Box(
            modifier.fillMaxSize().padding(contentPadding),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }

        is StatusUiSiswa.Success -> DaftarSiswa(
            itemSiswa = statusUiSiswa.siswa,
            contentPadding = contentPadding,
            modifier = modifier
        )

        is StatusUiSiswa.Error -> Column(
            modifier.fillMaxSize().padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Gagal memuat data")
            Button(onClick = retryAction) { Text("Coba Lagi") }
        }
    }
}

@Composable
fun DaftarSiswa(
    itemSiswa: List<DataSiswa>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    if (itemSiswa.isEmpty()) {
        Box(
            modifier.fillMaxSize().padding(contentPadding),
            contentAlignment = Alignment.Center
        ) { Text("Belum ada data") }
        return
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = itemSiswa, key = { it.id }) { siswa ->
            ItemSiswa(siswa, Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun ItemSiswa(siswa: DataSiswa, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(siswa.nama, style = MaterialTheme.typography.titleLarge)
            Text(siswa.alamat, style = MaterialTheme.typography.bodyMedium)
            Text(siswa.telpon, style = MaterialTheme.typography.titleMedium)
        }
    }
}