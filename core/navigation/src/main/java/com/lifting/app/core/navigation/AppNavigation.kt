package com.lifting.app.core.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lifting.app.core.navigation.bottom_nav.BottomNavigationBar
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.NavBarScreen


internal const val animDuration = 250


@Composable
fun AppNavigationWithBottomBar(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            AppNavigation(navController = navController)
        }
    }
}


@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavBarScreen.Dashboard,
        enterTransition = {
            fadeIn(animationSpec = tween(animDuration)) + scaleIn(
                animationSpec = tween(animDuration),
                initialScale = 0.75f
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animDuration))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animDuration / 1)) + scaleIn(
                animationSpec = tween(animDuration / 1),
                initialScale = 0.9f
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(animDuration)) + scaleOut(
                animationSpec = tween(animDuration),
                targetScale = 0.75f
            )
        }
    ) {
        dashboardRoot(navController)
        historyRoot(navController)
        workoutRoot(navController)
        exercisesRoot(navController)
        settingsRoot(navController)

        addExercisesBottomSheet(navController)
        newExercisesBottomSheet(navController)
    }
}

private fun NavGraphBuilder.dashboardRoot(navController: NavController) {
    navigation<NavBarScreen.Dashboard>(
        startDestination = LiftingScreen.Dashboard
    ) {
        dashboard(navController)
    }
}

private fun NavGraphBuilder.historyRoot(navController: NavController) {
    navigation<NavBarScreen.History>(
        startDestination = LiftingScreen.History
    ) {
        addHistory(navController)
        addCalendar(navController)
        addSession(navController)
        addWorkoutEdit(navController, NavBarScreen.History)
    }
}

private fun NavGraphBuilder.workoutRoot(navController: NavController) {
    navigation<NavBarScreen.Workout>(
        startDestination = LiftingScreen.Workout
    ) {
        workout(navController)
        workoutTemplatePreview(navController)
    }
}

private fun NavGraphBuilder.exercisesRoot(navController: NavController) {
    navigation<NavBarScreen.Exercises>(
        startDestination = LiftingScreen.Exercises
    ) {
        exercises(navController)
        exerciseDetail(navController)
    }
}

private fun NavGraphBuilder.settingsRoot(navController: NavController) {
    navigation<NavBarScreen.Settings>(
        startDestination = LiftingScreen.Settings
    ) {
        settings(navController)
        measure(navController)
    }
}

private fun NavGraphBuilder.dashboard(navController: NavController) {
    composable<LiftingScreen.Dashboard> {
        DashboardScreen(
            showSheet = {
                navController.navigate(LiftingScreen.ExercisesBottomSheet.toString())

            },
            showFeed = { navController.navigate(NavBarScreen.Dashboard) },
            showHistory = { navController.navigate(LiftingScreen.History) }
        )
    }

}

@Composable
private fun DashboardScreen(showSheet: () -> Unit, showFeed: () -> Unit, showHistory: () -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Body")
        Button(onClick = showSheet) {
            Text("Show sheet!")
        }
        Button(onClick = showFeed) {
            Text("Navigate to Feed")
        }
        Button(onClick = showHistory) {
            Text("Navigate to Feed")
        }
    }
}

private fun NavGraphBuilder.addHistory(navController: NavController) {
    composable<LiftingScreen.History> {
        SampleScreen("addHistory") {
            navController.navigate(LiftingScreen.Calendar("deneme"))
        }
    }
}

private fun NavGraphBuilder.addCalendar(navController: NavController) {
    composable<LiftingScreen.Calendar> {
        SampleScreen("addCalendar") {
            navController.navigate(LiftingScreen.Session("deneme"))
        }
    }
}

private fun NavGraphBuilder.addSession(navController: NavController) {
    composable<LiftingScreen.Session> {
        SampleScreen("addSession") {
            navController.navigate(LiftingScreen.WorkoutEdit("deneme", false))
        }
    }
}

private fun NavGraphBuilder.addWorkoutEdit(navController: NavController, root: NavBarScreen) {
    composable<LiftingScreen.WorkoutEdit> {
        SampleScreen("addWorkout") {
            navController.navigate(NavBarScreen.Dashboard)
        }
    }
}

private fun NavGraphBuilder.workout(navController: NavController) {
    composable<LiftingScreen.Workout> {
        SampleScreen("workout") {
            navController.navigate(LiftingScreen.WorkoutTemplatePreview)
        }
    }
}

private fun NavGraphBuilder.workoutTemplatePreview(navController: NavController) {
    composable<LiftingScreen.WorkoutTemplatePreview> {
        SampleScreen("workoutTemplatePreview") {
            navController.popBackStack()

        }
    }
}

private fun NavGraphBuilder.exercises(navController: NavController) {
    composable<LiftingScreen.Exercises> {
        SampleScreen("exercises") {
            navController.navigate(LiftingScreen.ExerciseDetail)
        }
    }
}

private fun NavGraphBuilder.exerciseDetail(navController: NavController) {
    composable<LiftingScreen.ExerciseDetail> {
        SampleScreen("exerciseDetail") {
            navController.popBackStack()

        }
    }
}

private fun NavGraphBuilder.settings(navController: NavController) {
    composable<LiftingScreen.Settings> {
        SampleScreen("settings") {
            navController.navigate(LiftingScreen.Measure)
        }
    }
}

private fun NavGraphBuilder.measure(navController: NavController) {
    composable<LiftingScreen.Measure> {
        SampleScreen("measure") {
            navController.popBackStack()
        }
    }
}

private fun NavGraphBuilder.addExercisesBottomSheet(navController: NavController) {
    bottomSheet(LiftingScreen.ExercisesBottomSheet.toString()) {
        AddExercise(navController = navController, text = "addExerciseBottomSheet")
    }
}


private fun NavGraphBuilder.newExercisesBottomSheet(navController: NavController) {
    bottomSheet(LiftingScreen.NewExercisesBottomSheet.toString()) {
        NewExercise(navController = navController, text = "NewExerciseBottomSheet")
    }
}


@Composable
private fun SampleScreen(text: String, onClick: () -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, modifier = Modifier.clickable { onClick() })
    }
}

@Composable
fun AddExercise(
    navController: NavController,
    text: String
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val muscleSelectorResult = navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>("bubenimkey", null)
            ?.collectAsState()
        LaunchedEffect(muscleSelectorResult) {
            muscleSelectorResult?.let {
                Log.d("AddExercise", "${it.value}")
            }
        }
        Text(
            text,
            modifier = Modifier.clickable { navController.navigate(LiftingScreen.NewExercisesBottomSheet.toString()) })
    }
}

@Composable
fun NewExercise(
    navController: NavController,
    text: String
) {

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, modifier = Modifier.clickable {
            navController.previousBackStackEntry?.savedStateHandle?.set("bubenimkey", "biceps")
            navController.popBackStack()
        }
        )
    }
}

inline fun <reified T : LiftingScreen> NavGraphBuilder.composableScreen(
    noinline content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(content = content)
}


/*fun NavGraphBuilder.bottomSheetScreen(
    screen: LiftingScreen,
    content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
)  {
    bottomSheet(
        route = LiftingScreen.ExercisesBottomSheet::class.toString(),
        content = content
    )
}*/

/*inline fun <reified T: Any> NavGraphBuilder.bottomSheetScreen(
    noinline content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
) {
    addDestination(
        BottomSheetNavigator.Destination(
            provider[BottomSheetNavigator::class],
            content
        ).apply {
            this.route = T::class
        }
    )
}*/


