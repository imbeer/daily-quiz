package com.toadthegod.dailyquiz.ui


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.DestinationStyle
import androidx.compose.animation.AnimatedContentTransitionScope as AnimScope

object ScreenTransitions : DestinationStyle.Animated() {
    private const val DURATION = 300

    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        fadeIn(animationSpec = tween(DURATION))
    }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        fadeOut(animationSpec = tween(DURATION))
    }

    override val popEnterTransition = enterTransition
    override val popExitTransition = exitTransition
}