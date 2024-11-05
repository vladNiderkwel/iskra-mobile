package com.niderkwel.iskra_mobile.components.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.niderkwel.iskra_mobile.components.navigation.graphs.AuthNavGraph

//@Composable
//fun AuthScreen(
//    navController: NavController,
//    sharedPreferences: SharedPreferences
//) {
//    val navControllerBottomBar = rememberNavController()
//
//    Scaffold {
//        Box(
//            modifier = Modifier
//                .padding(
//                    bottom = it.calculateBottomPadding(),
//                    top = it.calculateTopPadding(),
//                )
//        ) {
//            AuthNavGraph(
//                navControllerBottomBar,
//                sharedPreferences
//            )
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//private fun screen() {
//    AuthScreen(
//        navController = rememberNavController()
//    )
//}