package com.fitness.app.presentation.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.app.R
import com.fitness.app.core.constants.Constants.Companion.BES_GUN
import com.fitness.app.core.constants.Constants.Companion.FORM_KORU
import com.fitness.app.core.constants.Constants.Companion.HIC
import com.fitness.app.core.constants.Constants.Companion.IKI_UC_GUN
import com.fitness.app.core.constants.Constants.Companion.KAS_KAZAN
import com.fitness.app.core.constants.Constants.Companion.YAG_YAK
import com.fitness.app.data.remote.model.DusukZorluk
import com.fitness.app.data.remote.model.OrtaZorluk
import com.fitness.app.data.remote.model.YuksekZorluk
import com.fitness.app.presentation.calculator.SelectableGroup
import com.fitness.app.presentation.home.dialog.CustomCreatedProgramDialog
import com.fitness.app.presentation.home.dialog.CustomDialog
import com.fitness.app.presentation.home.list.AdvancedProgramList
import com.fitness.app.presentation.home.list.BeginnerProgramList
import com.fitness.app.presentation.home.list.IntermediateProgramList
import com.fitness.app.presentation.home.list.card.PersonalizedProgramCard
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.black20
import com.fitness.app.ui.theme.grey50
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomePageUiState,
    onEvent: (HomePageEvent) -> Unit
) {
    val verticalScroll = rememberScrollState()
    var openBottomSheet by remember { mutableStateOf(false) }
    var personalizedOpenBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val personalizedBottomSheetShate = rememberModalBottomSheetState()
    val openDialog = remember { mutableStateOf(false) }
    var showProgramDialog by remember { mutableStateOf(false) }
    val howManyDays = listOf(HIC,IKI_UC_GUN,BES_GUN)
    val whatIsYourGoal = listOf(YAG_YAK,KAS_KAZAN,FORM_KORU)
    var selectedHowManyDays by remember { mutableStateOf<String?>(null) }
    var selectedWhatIsYourGoal by remember { mutableStateOf<String?>(null) }
    var showButton by remember { mutableStateOf(false) }
    var videoUrl = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val beginnerState = state.programData?.antrenmanlar?.dusukZorluk
    val intermediateState = state.programData?.antrenmanlar?.ortaZorluk
    val advancedState = state.programData?.antrenmanlar?.yuksekZorluk


    if (selectedHowManyDays?.isNotBlank() == true && selectedWhatIsYourGoal?.isNotBlank() == true) {
        showButton = true
        onEvent(HomePageEvent.OnPersonalizedProgramButtonClicked(selectedHowManyDays!!,selectedWhatIsYourGoal!!))
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = black20
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = state.selectedProgramName!!,
                    color = White40,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            BottomSheetContent(state.selectedProgram) {
                videoUrl.value = it
                openDialog.value = true
            }
        }
    }

    if (openDialog.value) {
        CustomDialog(
            onDissmiss = { openDialog.value = !openDialog.value },
            dialogState = openDialog.value,
            url = videoUrl.value
        )
    }

    if (personalizedOpenBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { personalizedOpenBottomSheet = false },
            sheetState = personalizedBottomSheetShate,
            containerColor = black20
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.PersonalizedProgramTitle),
                    color = White40,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.HowManyDays),
                    color = White40,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                SelectableGroup(
                    options = howManyDays,
                    selectedOption = selectedHowManyDays,
                    onOptionSelected = { option ->
                        selectedHowManyDays = option
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.WhatIsYourGoal),
                    color = White40,
                    style = MaterialTheme.typography.labelMedium
                )
                SelectableGroup(
                    options = whatIsYourGoal,
                    selectedOption = selectedWhatIsYourGoal,
                    onOptionSelected = { option ->
                        selectedWhatIsYourGoal = option
                    }
                )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.Center) {
                AnimatedVisibility(
                    visible = showButton,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            showProgramDialog = !showProgramDialog
                            scope.launch { personalizedBottomSheetShate.hide() }.invokeOnCompletion {
                                if (!personalizedBottomSheetShate.isVisible) {
                                    personalizedOpenBottomSheet = false
                                    selectedHowManyDays = null
                                    selectedWhatIsYourGoal = null
                                    showButton = !showButton
                                }
                            } },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.Olustur),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                        )
                    }
                }
            }
        }
    }

    if (showProgramDialog) {
        CustomCreatedProgramDialog(
            dialogState = showProgramDialog,
            program = state.onPersonalizedProgramCreated!!
        ) { showProgramDialog = !showProgramDialog }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = grey50)
            .verticalScroll(verticalScroll)

    ) {
        if (beginnerState != null) {
            PlanSection()
            PersonalizedProgramCardSection {
                personalizedOpenBottomSheet = !personalizedOpenBottomSheet
            }
            BeginnerProgramListSection(beginnerState) { program ->
                openBottomSheet = !openBottomSheet
                onEvent(
                    HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                        program.uygulanis,
                        program.programAdi!!
                    )
                )
            }
            IntermediateProgramListSection(intermediateState!!) { program ->
                openBottomSheet = !openBottomSheet
                onEvent(
                    HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                        program.uygulanis,
                        program.programAdi!!
                    )
                )
            }
            AdvancedProgramListSection(advancedState!!) { program ->
               openBottomSheet = !openBottomSheet
               onEvent(
                   HomePageEvent.OnWorkoutProgramPlayButtonClicked(
                       program.uygulanis,
                       program.programAdi!!
                   )
               )
           }
        }
    }
    LoadingSection(state.isLoading)

}

@Composable
fun PlanSection() {
    Text(
        text = stringResource(id = R.string.PlanSeç),
        style = MaterialTheme.typography.titleMedium,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
@Composable
fun PersonalizedProgramCardSection(onCardClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        PersonalizedProgramCard{
            onCardClicked.invoke()
        }
    }
}

@Composable
fun BeginnerProgramListSection(
    state: ArrayList<DusukZorluk>,
    onItemClick: (DusukZorluk) -> Unit
) {
    BeginnerProgramList(state = state, programLevel = R.string.BeginnerProgram){
        onItemClick(it)
    }
}
@Composable
fun IntermediateProgramListSection(
    state: ArrayList<OrtaZorluk>,
    onItemClick: (OrtaZorluk) -> Unit
) {
    IntermediateProgramList(programListData = state, programLevel = R.string.IntermediateProgram) {
        onItemClick(it)
    }
}
@Composable
fun AdvancedProgramListSection(
    state: ArrayList<YuksekZorluk>,
    onItemClick: (YuksekZorluk) -> Unit
) {
    AdvancedProgramList(programListData = state, programLevel = R.string.AdvancedProgram) {
        onItemClick(it)
    }
}
@Composable
fun LoadingSection(
    state: Boolean?
) {
    if (state == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(strokeWidth = 2.dp)
        }
    }
}
