package com.D121211025.final_mobile.ui.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.D121211025.final_mobile.data.models.Result
import com.D121211025.final_mobile.ui.activities.detail.DetailActivity
import com.D121211025.final_mobile.ui.theme.FinalmobileTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkCustomColor = Color(20, 20, 20)
            FinalmobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = darkCustomColor
                ) {
                    Column {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.background(darkCustomColor),
                            title = {
                                Text(
                                    text = "FLAXFLIX",
                                    fontWeight = FontWeight.ExtraBold,
                                    style = TextStyle(
                                        color = Color.Red,
                                        fontSize = 24.sp
                                    )
                                )
                            }
                        )
                        val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
                        ListMoviesScreen(mainViewModel.mainUiState)
                    }

                }
            }
        }
    }

    @Composable
    private fun ListMoviesScreen(mainUiState: MainUiState, modifier: Modifier = Modifier) {
        when (mainUiState) {
            is MainUiState.Loading -> CenterText(text = "Loading...")
            is MainUiState.Error -> CenterText(text = "Something Error")
            is MainUiState.Success -> MovieList(mainUiState.movies)
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
    fun MovieList(movies: List<Result>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }

    @Composable
    fun MovieItem(movie: Result) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("MOVIE", movie)
                    startActivity(intent)
                },
            shape = RoundedCornerShape(12.dp),
        ){
            Box(
                modifier = Modifier
                    .height(200.dp)
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/original" + movie.backdrop_path)
                            .crossfade(true)
                            .build(),
                      contentDescription = movie.title,
                      contentScale = ContentScale.FillWidth
                    )
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 400f
                        )
                    )
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 24.dp),
                    contentAlignment = Alignment.BottomStart
                ){
                    Text(text = movie.title.take(32) + if (movie.title.length > 32) "..." else "",
                        style = TextStyle(color = Color.White, fontSize = 24.sp, FontWeight.Bold,),
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                    contentAlignment = Alignment.BottomStart
                ){
                    Text(text = movie.release_date.take(4),
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp))
                }
                Box(modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 0.dp))
                    .align(Alignment.TopEnd)
                ){
                    Text(text = movie.vote_average.toString().take(3),
                        style = TextStyle(color = Color.White, fontSize = 16.sp, FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}