package com.lova.codelabweek2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.lova.codelabweek2_1.ui.theme.CodelabWeek2_1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodelabWeek2_1Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //LayoutsCodelab()
                    ScrollingList()
                }
            }
        }
    }
}
// Create your custom layout
fun Modifier.firstBaselineToTop(firstBaselineToTop:Dp) =
    this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            check (placeable[FirstBaseline]!= AlignmentLine.Unspecified)
            val firstBaseline = placeable[FirstBaseline]

            val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
            val height = placeable.height + placeableY
            layout(placeable.width, height){
                placeable.placeRelative(0,placeableY)
            }
        }
    )
@Preview
@Composable
fun TextWithPaddingToBaselinePreview(){
    CodelabWeek2_1Theme() {
        Text("Hi There ! ",Modifier.firstBaselineToTop(32.dp))
    }
}
@Preview
@Composable
fun TextWithNormalPaddingPreview(){
    CodelabWeek2_1Theme() {
        Text("Hi there !",Modifier.padding(top=32.dp))
    }
}


// Working with Lists
@Composable
fun ImageListItem(index: Int){
    Row(verticalAlignment =  Alignment.CenterVertically){
        Image(painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png")
        ,contentDescription = "Android Logo",
        modifier = Modifier.size(50.dp))
        Spacer(Modifier.width(10.dp))
        Text("Item #$index",style = MaterialTheme.typography.subtitle1)
    }
}
@Composable
fun ScrollingList(){
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column{
        Row{
            Button(onClick = {coroutineScope.launch{
                scrollState.animateScrollToItem(0)
            }}){
                Text("Scroll to the Top")
            }
            Button(onClick = {coroutineScope.launch{
                scrollState.animateScrollToItem(100)
            }}){
                Text("Scroll to the End")
            }
        }
        LazyColumn(state = scrollState){
            items(listSize){
                ImageListItem(it)
            }
        }
    }
}

@Composable
fun LazyList(){
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState){
        items(100){
            Text("Lazy Item #$it")
        }
    }
}

@Composable
fun SimpleList(){
    val scrollState = rememberScrollState()
    Column{Modifier.verticalScroll(scrollState)
        repeat(100){
            Text("Item #$it")
        }
    }
}
@Composable
@Preview
fun SimpleListPreview(){
    CodelabWeek2_1Theme{
        //SimpleList()
    }
}

@Composable
fun PhotographerCard(){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alfred Sisly", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}


// Codelab about layouts
@Composable
fun LayoutsCodelab(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions={
                    IconButton(onClick = {}){
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) {innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp))
    }
}
@Composable
fun BodyContent(modifier:Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi There !")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}
@Preview
@Composable
fun LayoutsCodelabPreview(){
    CodelabWeek2_1Theme{
        LayoutsCodelab()
    }
}



// Codelab about modifiers
@Preview
@Composable
fun PhotographerCardPreview(){
    CodelabWeek2_1Theme{
        PhotographerCard()
    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodelabWeek2_1Theme {
        Greeting("Android")
    }
}