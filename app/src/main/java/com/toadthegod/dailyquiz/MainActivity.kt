package com.toadthegod.dailyquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.toadthegod.dailyquiz.data.dbModule
import com.toadthegod.dailyquiz.data.networkModule
import com.toadthegod.dailyquiz.data.repositoryModule
import com.toadthegod.dailyquiz.ui.theme.DailyQuizTheme
import com.toadthegod.dailyquiz.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinApplication(
                application = {
                    androidLogger(Level.INFO)
                    androidContext(this@MainActivity)
                    modules(networkModule, dbModule, repositoryModule, uiModule)
                }
            ) {
                val navController = rememberNavController()
                DailyQuizTheme {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                    )
                }
            }
        }
    }
}