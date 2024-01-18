package com.example.profilelayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.profilelayout.ui.theme.ProfileCardLayoutTheme
import com.example.profilelayout.ui.theme.ProfileLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(userProfiles: List<UserProfile> = userProfileList) {
    Scaffold(topBar = {AppBar()}) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray
        ) {
            LazyColumn(){
                items(userProfiles){ userProfile->
                    ProfileCard(userProfile = userProfile)
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
fun ProfileCard(userProfile: UserProfile){
     Card(
         colors = CardDefaults.cardColors(
             containerColor = Color.White
         ),
         modifier = Modifier
             .padding(16.dp)
             .fillMaxWidth()
             .wrapContentHeight(align = Alignment.Top),


//         elevation = 8.dp,
     ) {
         Row(
             modifier=Modifier.wrapContentSize(),
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.Start
         ) {
             ProfilePicture(userProfile.pictureUrl,userProfile.status)
             ProfileContent(userProfile.name,userProfile.status)
         }
     }
}

@Composable
fun  ProfilePicture(pictureUrl: String, onlineStatus: Boolean){
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color= if (onlineStatus)
                MaterialTheme.colorScheme.primary else Color.Green),
        modifier = Modifier.padding(16.dp),
        ) {


//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(pictureUrl)
//                .crossfade(true)
//                .build(),
//            contentDescription = "",
//            modifier = Modifier.size(72.dp),
//            contentScale = ContentScale.Crop
//        )
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pictureUrl)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun  ProfileContent(userName: String, onlineStatus: Boolean){
    Column(
        modifier=Modifier.padding(8.dp)) {
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(text = if (onlineStatus) "Active now" else "offline",
            color=Color.LightGray,
            style = MaterialTheme.typography.bodySmall)
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileLayoutTheme {
        MainScreen()
    }
}