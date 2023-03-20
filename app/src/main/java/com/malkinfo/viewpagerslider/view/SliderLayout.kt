package com.malkinfo.viewpagerslider.view

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.*
import com.malkinfo.viewpagerslider.R
import com.malkinfo.viewpagerslider.model.FotosData
import com.malkinfo.viewpagerslider.model.fotoslist
import com.malkinfo.viewpagerslider.ui.theme.Purple500
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Composable
fun ViewPagerSlider(){

    val pagerState  = rememberPagerState(
        pageCount = fotoslist.size,
        initialPage =  0
    )

    LaunchedEffect(Unit){
        while (true){
            yield()
           delay(5000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {


        //Spacer(modifier = Modifier.height(5.dp))
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(5.dp, 50.dp, 5.dp, 50.dp)

        ) { page ->
            Card(modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale

                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                }
                .fillMaxWidth()
                .padding(25.dp,0.dp, 25.dp, 0.dp),
                shape = RoundedCornerShape(15.dp)
            ) {

                val newFotos = fotoslist[page]
                 Box(modifier = Modifier
                     .fillMaxSize()
                     .background(Color.LightGray)
                     .align(Alignment.Center)

                 ) {
                     Image(painter = painterResource(
                         id = newFotos.imgUri

                     ),
                         contentDescription = "Image",
                         contentScale = ContentScale.Crop,
                         modifier = Modifier.fillMaxSize()
                     )

                     Column(modifier = Modifier
                         .align(Alignment.BottomStart)
                         .padding(15.dp)
                     ) {

                     }

                 }


            }

        }

        //Horizontal dot indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

    }

}