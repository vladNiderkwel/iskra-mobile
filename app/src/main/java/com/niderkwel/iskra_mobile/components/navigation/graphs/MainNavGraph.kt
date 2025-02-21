package com.niderkwel.iskra_mobile.components.navigation.graphs

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.niderkwel.iskra_mobile.components.Screen
import com.niderkwel.iskra_mobile.components.screens.event.EventsScreen
import com.niderkwel.iskra_mobile.components.screens.event.EventsViewModel
import com.niderkwel.iskra_mobile.components.screens.event.create.CreateEventScreen
import com.niderkwel.iskra_mobile.components.screens.event.create.CreateEventViewModel
import com.niderkwel.iskra_mobile.components.screens.event.create.CreateEventViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.event.details.EventDetailScreen
import com.niderkwel.iskra_mobile.components.screens.event.details.EventDetailsViewModel
import com.niderkwel.iskra_mobile.components.screens.event.details.EventDetailsViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.map.MapScreen
import com.niderkwel.iskra_mobile.components.screens.map.MapViewModel
import com.niderkwel.iskra_mobile.components.screens.post.PostsScreen
import com.niderkwel.iskra_mobile.components.screens.post.PostsViewModel
import com.niderkwel.iskra_mobile.components.screens.post.details.PostDetailsScreen
import com.niderkwel.iskra_mobile.components.screens.post.details.PostDetailsViewModel
import com.niderkwel.iskra_mobile.components.screens.post.details.PostDetailsViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.profile.ProfileScreen
import com.niderkwel.iskra_mobile.components.screens.profile.ProfileViewModel
import com.niderkwel.iskra_mobile.components.screens.profile.ProfileViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.profile.edit.ProfileEditScreen
import com.niderkwel.iskra_mobile.components.screens.profile.edit.ProfileEditViewModel
import com.niderkwel.iskra_mobile.components.screens.profile.edit.ProfileEditViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.questions.QuestionsScreen
import com.niderkwel.iskra_mobile.components.screens.questions.QuestionsViewModel
import com.niderkwel.iskra_mobile.components.screens.questions.QuestionsViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.questions.create.CreateQuestionScreen
import com.niderkwel.iskra_mobile.components.screens.questions.create.CreateQuestionViewModel
import com.niderkwel.iskra_mobile.components.screens.questions.create.CreateQuestionViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.questions.details.QuestionDetailScreen
import com.niderkwel.iskra_mobile.components.screens.questions.details.QuestionDetailsViewModel
import com.niderkwel.iskra_mobile.components.screens.questions.details.QuestionDetailsViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.ratings.RatingScreen
import com.niderkwel.iskra_mobile.components.screens.ratings.RatingsViewModel
import com.niderkwel.iskra_mobile.components.screens.task.TasksScreen
import com.niderkwel.iskra_mobile.components.screens.task.TasksViewModel
import com.niderkwel.iskra_mobile.components.screens.task.TasksViewModelFactory
import com.niderkwel.iskra_mobile.components.screens.task.details.TaskDetailsScreen
import com.niderkwel.iskra_mobile.components.screens.task.details.TaskDetailsViewModel
import com.niderkwel.iskra_mobile.components.screens.task.details.TaskDetailsViewModelFactory

@Composable
fun MainNavGraph(
    navController: NavHostController,
    sharedPreferences: SharedPreferences,
    modifier: Modifier = Modifier,
    contentResolver: ContentResolver
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Profile.path,
        route = Screen.Main.path,
        modifier = modifier
    ) {
        addProfile(navController, sharedPreferences)
        addProfileEdit(navController, sharedPreferences, contentResolver)
        addRating(navController)
        addEvent(navController)
        addEventDetail(navController, sharedPreferences)
        addCreateEvent(navController, sharedPreferences)
        addTasks(navController, sharedPreferences)
        addTaskDetails(navController, sharedPreferences)
        addMap(navController)
        addPosts(navController)
        addPostDetails(navController)
        addQuestions(navController, sharedPreferences)
        addCreateQuestion(navController, sharedPreferences)
        addQuestionDetails(navController)
    }
}

private fun NavGraphBuilder.addProfile(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.Profile.path) {
        val viewModel = viewModel<ProfileViewModel>(
            factory = ProfileViewModelFactory(sharedPreferences)
        )
        ProfileScreen(
            state = viewModel.state.collectAsState(),
            onEditProfile = {
                navController.popBackStack()
                navController.navigate(Screen.ProfileEdit.path)
            },
            onQuestions = {
                navController.navigate(Screen.Questions.path)
            }
        )
    }
}

private fun NavGraphBuilder.addProfileEdit(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    contentResolver: ContentResolver
) {
    composable(route = Screen.ProfileEdit.path) {
        val viewModel = viewModel<ProfileEditViewModel>(
            factory = ProfileEditViewModelFactory(sharedPreferences)
        )
        ProfileEditScreen(
            state = viewModel.state.collectAsState(),
            onSave = { viewModel.onSave(navController) },
            onEmailUpdate = { value -> viewModel.onEmailUpdate(value) },
            onNameUpdate = { value -> viewModel.onNameUpdate(value) },
            onImageUpdate = { file, value -> viewModel.onImageUpdate(file, value) },
            contentResolver = contentResolver
        )
    }
}

private fun NavGraphBuilder.addEvent(navController: NavController) {
    composable(route = Screen.Events.path) {
        val viewModel = viewModel<EventsViewModel>()
        EventsScreen(
            state = viewModel.state.collectAsState(),
            seeDetails = { id -> navController.navigate("event/$id") },
            onRefresh = { viewModel.fetchEvents() },
            onCreateEvent = { viewModel.onCreateEvent(navController) }
        )
    }
}

private fun NavGraphBuilder.addCreateEvent(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.CreateEvent.path) {
        val viewModel = viewModel<CreateEventViewModel>(
            factory = CreateEventViewModelFactory(sharedPreferences)
        )
        CreateEventScreen(
            state = viewModel.state.collectAsState(),
            onSave = { viewModel.onSave(navController) },
            onTitleChanged = { viewModel.onTitleChanged(it) },
            onDescriptionChanged = { viewModel.onDescriptionChanged(it) },
            onStartChanged = { viewModel.onStartChanged(it) },
            onEndChanged = { viewModel.onEndChanged(it) }
        )
    }
}

private fun NavGraphBuilder.addRating(navController: NavController) {
    composable(route = Screen.Ratings.path) {
        val viewModel = viewModel<RatingsViewModel>()
        RatingScreen(
            state = viewModel.state.collectAsState(),
        )
    }
}

private fun NavGraphBuilder.addEventDetail(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(
        route = Screen.Event.path,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
        val viewModel = viewModel<EventDetailsViewModel>(
            factory = EventDetailsViewModelFactory(
                it.arguments?.getInt("id")!!,
                sharedPreferences
            )
        )
        EventDetailScreen(
            state = viewModel.state.collectAsState(),
            onRefresh = {},
            onToggleAttendance = { viewModel.onToggle() },
        )
    }
}

private fun NavGraphBuilder.addTasks(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.Tasks.path) {
        val viewModel = viewModel<TasksViewModel>(
            factory = TasksViewModelFactory(
                sharedPreferences = sharedPreferences
            )
        )
        TasksScreen(
            state = viewModel.state.collectAsState(),
            onRefresh = { viewModel.fetchTasks() },
            onDetails = { id -> navController.navigate("task/$id") }
        )
    }
}

private fun NavGraphBuilder.addTaskDetails(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(
        route = Screen.Task.path,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
        val viewModel = viewModel<TaskDetailsViewModel>(
            factory = TaskDetailsViewModelFactory(
                it.arguments?.getInt("id")!!,
                sharedPreferences = sharedPreferences
            )
        )
        TaskDetailsScreen(
            state = viewModel.state.collectAsState(),
            onWrittenAnswerChange = { id, value -> viewModel.onWrittenSubtaskUpdate(id, value) },
            onRadiobuttonAnswerChange = { id, value ->
                viewModel.onRadiobuttonSubtaskUpdate(
                    id,
                    value
                )
            },
            onCheckboxAnswerChange = { id, value, bool ->
                viewModel.onCheckboxSubtaskUpdate(
                    id,
                    value,
                    bool
                )
            },
            onSubmit = { viewModel.onSave(navController) }
        )
    }
}

private fun NavGraphBuilder.addMap(navController: NavController) {
    composable(route = Screen.Map.path) {
        val viewModel = viewModel<MapViewModel>()
        MapScreen(
            state = viewModel.state.collectAsState(),
            onRefresh = { viewModel.fetchMapMarks() },
        )
    }
}

private fun NavGraphBuilder.addPosts(navController: NavController) {
    composable(route = Screen.Posts.path) {
        val viewModel = viewModel<PostsViewModel>()
        PostsScreen(
            state = viewModel.state.collectAsState(),
            onRefresh = { viewModel.fetchPosts() },
            onDetails = { id -> navController.navigate("post/$id") }
        )
    }
}

private fun NavGraphBuilder.addPostDetails(navController: NavController) {
    composable(
        route = Screen.Post.path,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
        val viewModel = viewModel<PostDetailsViewModel>(
            factory = PostDetailsViewModelFactory(
                it.arguments?.getInt("id")!!
            )
        )
        PostDetailsScreen(
            state = viewModel.state.collectAsState(),
            onRefresh = { viewModel.fetchPost() },
        )
    }
}

private fun NavGraphBuilder.addQuestions(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.Questions.path) {
        val viewModel = viewModel<QuestionsViewModel>(
            factory = QuestionsViewModelFactory(
                sharedPreferences = sharedPreferences
            )
        )
        QuestionsScreen(
            state = viewModel.state.collectAsState(),
            onClick = {
                navController.navigate("question/$it")
            }
        )
    }
}

private fun NavGraphBuilder.addCreateQuestion(
    navController: NavController,
    sharedPreferences: SharedPreferences
) {
    composable(route = Screen.CreateQuestion.path) {
        val viewModel = viewModel<CreateQuestionViewModel>(
            factory = CreateQuestionViewModelFactory(
                sharedPreferences = sharedPreferences
            )
        )
        CreateQuestionScreen(
            state = viewModel.state.collectAsState(),
            onQuestionChanged = { viewModel.onQuestionChanged(it) },
            onSave = {
                viewModel.onSave(navController)
            }
        )
    }
}

private fun NavGraphBuilder.addQuestionDetails(navController: NavController) {
    composable(
        route = Screen.Question.path,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
        val viewModel = viewModel<QuestionDetailsViewModel>(
            factory = QuestionDetailsViewModelFactory(
                it.arguments?.getInt("id")!!,
            )
        )
        QuestionDetailScreen(
            state = viewModel.state.collectAsState(),
            //onRefresh = { viewModel.fetchDetail() },
        )
    }
}