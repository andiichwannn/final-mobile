package com.D121211025.final_mobile.ui.activities.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.D121211025.final_mobile.data.models.Result
import com.D121211025.final_mobile.ui.theme.FinalmobileTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailActivity : ComponentActivity() {

    private var selectedMovie: Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedMovie = intent.getParcelableExtra("MOVIE")
        setContent {
            val darkCustomColor = Color(20, 20, 20)
            FinalmobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = darkCustomColor
                ) {
                    DetailScreen()
                }
            }
        }
    }

    @Composable
    fun DetailScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .size(width = 480.dp, height = 240.dp)
            ){
                Box(
                    modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/original" + selectedMovie?.backdrop_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = selectedMovie?.title,
                        modifier = Modifier
                            .width(480.dp)
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .offset(y = 160.dp)
                        .offset(x = 16.dp)
                        .width(120.dp)
                        .height(160.dp)
                        .clip(MaterialTheme.shapes.medium)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/original" + selectedMovie?.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = selectedMovie?.title,
                        modifier = Modifier
                            .width(120.dp)
                            .height(160.dp)
                            .align(Alignment.BottomStart)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 80.dp)
            .verticalScroll(rememberScrollState())
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ){
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = selectedMovie?.title.toString(),
                    style = TextStyle(color = Color.White, fontSize = 24.sp, FontWeight.Bold),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = selectedMovie?.release_date?.let {
                            try {
                                val inputFormatter =
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
                                val outputFormatter =
                                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
                                LocalDate.parse(it, inputFormatter).format(outputFormatter)
                            } catch (e: Exception) {
                                "Unknown"
                            }
                        } ?: "Unknown",
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp, FontWeight.Medium),
                        modifier = Modifier.padding(end = 15.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .height(20.dp)
                            .width(3.dp)
                            .background(Color.LightGray)
                    )

                    Text(
                        text = selectedMovie?.vote_average.toString().take(3),
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp, FontWeight.Medium),
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Overview",
                    style = TextStyle(color = Color.White, fontSize = 20.sp, FontWeight.Bold),
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedMovie?.overview.toString(),
                    style = TextStyle(color = Color.LightGray, fontSize = 16.sp, FontWeight.Medium),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )
                }
            }
        }
    }
}