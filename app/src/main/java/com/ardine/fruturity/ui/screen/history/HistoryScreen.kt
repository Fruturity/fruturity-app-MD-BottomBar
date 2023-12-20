package com.ardine.fruturity.ui.screen.history

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ardine.fruturity.R
import com.ardine.fruturity.data.ResultState
import com.ardine.fruturity.di.Injection
import com.ardine.fruturity.data.response.FruitResponse
import com.ardine.fruturity.ui.ViewModelFactory
import com.ardine.fruturity.ui.components.MyItems

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (fruitId: String) -> Unit,
){
    val searchState by viewModel.searchState

    viewModel.resultState.collectAsState(initial = ResultState.Loading).value.let { resultState ->
        when(resultState){
            is ResultState.Loading -> {
                viewModel.getAllFruits()
            }
            is ResultState.Success -> {
                HistoryContent(
                    fruits = resultState.data,
                    navigateToDetail = navigateToDetail,
                    query = searchState.query,
//                    onQueryChange = viewModel::onQueryChange,
//                    updateBookmarkStatus = {
//                        viewModel.updateFruitMark(it)
//                    },
                    modifier = modifier,
                )
            }
            is ResultState.Error -> {
                Toast.makeText(LocalContext.current, R.string.empty_msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun HistoryContent (
    fruits: List<FruitResponse>,
    query: String,
    navigateToDetail: (String) -> Unit,
//    updateBookmarkStatus :(id: Long) -> Unit,
//    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column {
//        Box(
//            modifier = modifier
//                .padding(horizontal = 8.dp)
//        ) {
//            SearchBar(
//                query = query,
//                onQueryChange = onQueryChange,
//                modifier = modifier
//                    .clip(RoundedCornerShape(8.dp))
//            )
//        }
            if (fruits.isEmpty()) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.empty_msg)
                )
            } else {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(fruits) { items ->
                        MyItems(
                            fruitsId = items.id ,
                            ripeness = items.ripeness,
                            imageUrl = items.imageUrl,
                            category = items.category,
                            date = items.date,
                            onItemClick = {
                                navigateToDetail(items.id)
                            },
//                            bookmarkStatus = items.fruits.isBookmark,
//                            updateBookmarkStatus = updateBookmarkStatus,
                        )
                    }
                }
            }

    }
}