package com.kiras.leaguer.ui.screens.champions_list.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiras.leaguer.domain.repository.champions.ChampionsApiResponse
import com.kiras.leaguer.util.toChampionsList
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsListViewModel @Inject constructor(
    private val championsApiResponse: ChampionsApiResponse
) : ViewModel() {

    private val _state = MutableStateFlow(ChampionsListState())
    val state = _state.asStateFlow()

    init {
        getAllChampions()
    }

    fun getAllChampions() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { listState ->
                listState.copy(
                    isLoading = true,
                    error = null
                )
            }
            delay(600)
            championsApiResponse.getAllChampions()
                .onSuccess {
                    _state.update { listState ->
                        listState.copy(
                            isLoading = false,
                            champions = data.champion.toChampionsList()
                        )
                    }
                }
                .onFailure {
                    _state.update { listState ->
                        listState.copy(
                            isLoading = false,
                            error = "Something went wrong."
                        )
                    }
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _state.update { listState ->
            listState.copy(
                searchText = text,
                filteredChampions = listState.champions.filter { champion ->
                    champion.name?.contains(text, ignoreCase = true) == true
                }
            )
        }
    }
}