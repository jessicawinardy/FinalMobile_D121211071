package com.example.tugasfinal_d121211071.ui.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tugasfinal_d121211071.data.models.Disney
import com.example.tugasfinal_d121211071.ui.activities.CharacterDetails.DetailActivity
import com.example.tugasfinal_d121211071.ui.theme.TugasFinal_D121211071Theme

class MainActivity : ComponentActivity() {

    var searchQuery by mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasFinal_D121211071Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        // Header Judul
                        TopAppBar(
                            title = { Text("Disney Film List") },
                            modifier = Modifier.background(Color.Blue),
                        )

                        // List Disney Movies
                        val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
                        ListMoviesScreen(mainViewModel.mainUiState)
                    }
                }
            }
        }
    }


    @Composable
    private fun ListMoviesScreen(mainUiState: MainUiState, modifier: Modifier = Modifier) {
        var searchQuery by remember { mutableStateOf("") }

        when (mainUiState) {
            is MainUiState.Loading -> CenterText(text = "Loading...")
            is MainUiState.Error -> CenterText(text = "Something Error")
            is MainUiState.Success -> {
                // SearchBar
                SearchBar(
                    searchQuery = searchQuery,
                    onValueChange = { newQuery ->
                        searchQuery = newQuery
                    }
                )

                // DisneyList hasil searchQuery
                DisneyList(mainUiState.disney, searchQuery = searchQuery)
            }
        }
    }

    @Composable
    fun CenterText(text: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
            )
        }
    }

    @Composable
    fun DisneyList(disney: List<Disney>, modifier: Modifier = Modifier, searchQuery: String = "") {
        val filteredDisney = if (searchQuery.isEmpty()) {
            //Sort dari yang terbaru
            disney.sortedByDescending { it.year }
        } else {
            //sort hasil filter dari yang terbaru
            disney.filter {
                it.title?.contains(searchQuery, ignoreCase = true) ?: false
            }.sortedByDescending { it.year }
        }

        LazyColumn(modifier = modifier) {
            items(filteredDisney) { disney ->
                DisneyItem(disney = disney)
            }
        }
    }

    @Composable
    fun DisneyItem(disney: Disney) {
        if (disney.title == "Fantasia" || disney.title == "The Reluctant Dragon" || disney.title == "Saludos Amigos") {
            // Skip beberapa data karena image yang rusak
            return
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp),)
                .clickable {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("DISNEY", disney)
                    startActivity(intent)
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Movie Poster
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(disney.image)
                        .crossfade(true)
                        .build(), contentDescription = disney.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                // Movie Details
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = disney.title.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = disney.year.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = disney.genre.toString(), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(searchQuery: String, onValueChange: (String) -> Unit) {
        TextField(
            value = searchQuery,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Search") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            colors = TextFieldDefaults.textFieldColors(),
        )
    }
}