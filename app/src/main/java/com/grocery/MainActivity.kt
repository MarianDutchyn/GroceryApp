package com.grocery

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.grocery.ui.theme.GroceryTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("main_screen") {
            MainScreen()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true ) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                delayMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1500L)
        navController.navigate("main_screen") {
            popUpTo("splash_screen") {
                inclusive = true
            }
        }
    }
    val systemUIController = rememberSystemUiController()
    systemUIController.setSystemBarsColor(colorResource(id = R.color.orange))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.orange)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
        Text(
            text = "Fresh Fruits",
            style = TextStyle(
                colorResource(id = R.color.white),
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(0.dp, 38.dp, 0.dp, 0.dp)
                .scale(scale.value)
        )
    }
}

@Composable
fun MainScreen() {
    val systemUIController = rememberSystemUiController()
    systemUIController.setSystemBarsColor(colorResource(id = R.color.white))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen")
    }
}