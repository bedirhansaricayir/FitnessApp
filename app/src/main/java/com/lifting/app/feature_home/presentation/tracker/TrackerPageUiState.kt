package com.lifting.app.feature_home.presentation.tracker

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.lifting.app.feature_home.domain.model.AnalysisSortBy
import com.lifting.app.feature_home.domain.model.AnalysisTimeRange
import com.lifting.app.feature_home.domain.model.ChartState
import com.lifting.app.feature_home.presentation.tracker.components.SortBy
import com.lifting.app.feature_home.presentation.tracker.components.TimeRange

data class TrackerPageUiState(
    val timeRange: AnalysisTimeRange = AnalysisTimeRange.TIMERANGE_30DAYS,
    val chartState: List<ChartState> = emptyList(),
    val sortBy: AnalysisSortBy = AnalysisSortBy.DATE
){
    fun getTimeRange() = when (timeRange) {
        AnalysisTimeRange.TIMERANGE_7DAYS -> TimeRange.SEVEN_DAYS
        AnalysisTimeRange.TIMERANGE_30DAYS -> TimeRange.THIRTY_DAYS
        AnalysisTimeRange.TIMERANGE_60DAYS -> TimeRange.SIXTY_DAYS
        AnalysisTimeRange.TIMERANGE_90DAYS -> TimeRange.NINETY_DAYS
        AnalysisTimeRange.TIMERANGE_1YEAR -> TimeRange.ONE_YEAR
    }
    fun getSortBy() = when(sortBy) {
        AnalysisSortBy.DATE -> SortBy.DATE
        AnalysisSortBy.RECORD -> SortBy.RECORD

    }
}
