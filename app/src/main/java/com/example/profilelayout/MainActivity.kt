package com.example.profilelayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.profilelayout.ui.theme.ProfileCardLayoutTheme
import com.example.profilelayout.ui.theme.ProfileLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                UsersApplication()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(userProfiles: List<UserProfile> = userProfileList, navController: NavController) {
    Scaffold(topBar = {AppBar()}) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray
        ) {
            LazyColumn(){
                items(userProfiles){ userProfile->
                    ProfileCard(userProfile = userProfile){
                        navController.navigate("user_details/${userProfile.id}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            Icon(imageVector = Icons.Default.Home, contentDescription = "",
                Modifier.padding(horizontal =12.dp ))},
        title = { Text(text = "Messaging Application Users")})
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit){
     Card(
         colors = CardDefaults.cardColors(
             containerColor = Color.White
         ),
         modifier = Modifier
             .padding(16.dp)
             .fillMaxWidth()
             .wrapContentHeight(align = Alignment.Top)
             .clickable(onClick = {clickAction.invoke()})
     ) {
         Row(
             modifier=Modifier.wrapContentSize(),
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.Start
         ) {
             ProfilePicture(userProfile.pictureUrl,userProfile.status,72.dp)
             ProfileContent(userProfile.name,userProfile.status,Alignment.Start)
         }
     }
}

@Composable
fun  ProfilePicture(pictureUrl: String, onlineStatus: Boolean,imageSize: Dp){
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color= if (onlineStatus)
                MaterialTheme.colorScheme.primary else Color.Green),
        modifier = Modifier.padding(16.dp),
        ) {
        Image(
            painter = rememberAsyncImagePainter(pictureUrl),
            contentDescription = "",
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun  ProfileContent(userName: String, onlineStatus: Boolean,alignment: Alignment.Horizontal){
    Column(
        modifier=Modifier.padding(8.dp),
        horizontalAlignment = alignment) {
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(text = if (onlineStatus) "Active now" else "offline",
            color=Color.Magenta,
            style = MaterialTheme.typography.bodySmall)
    }

}


// screen 2
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserProfileDetailsScreen(userId: Int) {
    val userProfile = userProfileList.first{userProfile -> userId == userProfile.id }
    Scaffold(topBar = {AppBar()}) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier=Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(userProfile.pictureUrl,userProfile.status,240.dp)
                ProfileContent(userProfile.name,userProfile.status,Alignment.CenterHorizontally)
            }
        }
    }
}

@Composable
fun UsersApplication(userProfiles: List<UserProfile> = userProfileList){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list" ){
        composable("users_list"){
            UsersScreen(userProfiles, navController)
        }

        composable("user_details/{userId}",

            arguments = listOf(navArgument("userId"){
                type = NavType.IntType
            })

        ){navBackStackEntry ->
            UserProfileDetailsScreen(navBackStackEntry.arguments!!.getInt("userId"))
        }
    }
}

@Preview
@Composable
fun ProfilePreview(){
    UserProfileDetailsScreen(userId = 0)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileLayoutTheme {
        UsersApplication()
    }
}


