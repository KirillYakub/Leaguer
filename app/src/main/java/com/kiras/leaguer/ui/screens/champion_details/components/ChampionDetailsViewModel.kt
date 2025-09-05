package com.kiras.leaguer.ui.screens.champion_details.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kiras.leaguer.domain.repository.champions.ChampionsApiResponse
import com.kiras.leaguer.domain.repository.champions.InsertChampionRepos
import com.kiras.leaguer.util.ChampionsDetails
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChampionDetailsViewModel @Inject constructor(
    private val championsApiResponse: ChampionsApiResponse,
    private val insertChampionRepos: InsertChampionRepos,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf<ChampionDetailsState?>(null)
        private set

    init {
        getChampionByName()
    }

    fun getChampionByName() {
        val args = savedStateHandle.toRoute<ChampionsDetails>()
        viewModelScope.launch(Dispatchers.IO) {
            state = ChampionDetailsState(
                isLoading = true,
                error = null
            )
            delay(600)
            championsApiResponse.getChampionByName(args.name)
                .suspendOnSuccess {
                    state = ChampionDetailsState(
                        isLoading = false,
                        champion = data.champion.values.firstOrNull()
                    )
                    state?.champion?.name?.let {
                        insertChampionRepos.insertChampion(it)
                    }
                }
                .onFailure {
                    state = ChampionDetailsState(
                        isLoading = false,
                        error = "Something went wrong."
                    )
                }
        }
    }
}