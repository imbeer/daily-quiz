package com.toadthegod.DailyQuiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.toadthegod.DailyQuiz.data.networkModule
import com.toadthegod.DailyQuiz.data.repositoryModule
import com.toadthegod.DailyQuiz.ui.component.AnswerState
import com.toadthegod.DailyQuiz.ui.theme.DailyQuizTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import com.toadthegod.DailyQuiz.ui.component.SelectableAnswerRow
import com.toadthegod.DailyQuiz.ui.welcome.QuizStartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinApplication(
                application = {
                    androidLogger(Level.DEBUG)
                    androidContext(this@MainActivity)
                    modules(networkModule, repositoryModule)
                }
            ) {
                DailyQuizTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        QuizStartScreen(Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyQuizTheme {
        Greeting("Android")
    }
}