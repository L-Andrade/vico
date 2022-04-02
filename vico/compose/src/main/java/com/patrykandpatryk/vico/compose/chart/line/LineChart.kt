/*
 * Copyright 2022 Patryk Goworowski and Patryk Michalik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.patrykandpatryk.vico.compose.chart.line

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import com.patrykandpatryk.vico.compose.style.currentChartStyle
import com.patrykandpatryk.vico.core.DefaultDimens
import com.patrykandpatryk.vico.core.chart.column.ColumnChart
import com.patrykandpatryk.vico.core.chart.decoration.Decoration
import com.patrykandpatryk.vico.core.component.Component
import com.patrykandpatryk.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatryk.vico.core.chart.line.LineChart
import com.patrykandpatryk.vico.core.entry.ChartEntryModel

/**
 * Creates a [LineChart].
 *
 * @param point an optional [Component] that can be drawn at a given point on the line.
 * @param pointSize the size of the [point].
 * @param spacing the spacing between each [point].
 * @param lineThickness the thickness of the line.
 * @param lineColor the color of the line.
 * @param minX the minimum value shown on the x-axis. If not null, it overrides [ChartEntryModel.minX].
 * @param maxX the maximum value shown on the x-axis. If not null, it overrides [ChartEntryModel.maxX].
 * @param minY the minimum value shown on the y-axis. If not null, it overrides [ChartEntryModel.minY].
 * @param maxY the maximum value shown on the y-axis. If not null, it overrides [ChartEntryModel.maxY].
 * @param decorations the list of [Decoration]s that will be added to the [LineChart].
 *
 * @see com.patrykandpatryk.vico.compose.chart.Chart
 * @see ColumnChart
 */
@Composable
public fun lineChart(
    point: Component? = currentChartStyle.lineChart.point,
    pointSize: Dp = currentChartStyle.lineChart.pointSize,
    spacing: Dp = currentChartStyle.lineChart.spacing,
    lineThickness: Dp = currentChartStyle.lineChart.lineThickness,
    lineColor: Color = currentChartStyle.lineChart.lineColor,
    lineBackgroundShader: DynamicShader? = currentChartStyle.lineChart.lineBackgroundShader,
    lineStrokeCap: StrokeCap = StrokeCap.Round,
    cubicStrength: Float = DefaultDimens.CUBIC_STRENGTH,
    minX: Float? = null,
    maxX: Float? = null,
    minY: Float? = null,
    maxY: Float? = null,
    decorations: List<Decoration> = emptyList(),
): LineChart = remember {
    LineChart().apply {
        addDecorations(decorations)
    }
}.apply {
    this.point = point
    this.pointSizeDp = pointSize.value
    this.spacingDp = spacing.value
    this.lineThicknessDp = lineThickness.value
    this.lineColor = lineColor.toArgb()
    this.lineBackgroundShader = lineBackgroundShader
    this.lineStrokeCap = lineStrokeCap.paintCap
    this.cubicStrength = cubicStrength
    this.minX = minX
    this.maxX = maxX
    this.minY = minY
    this.maxY = maxY
}

private val StrokeCap.paintCap: Paint.Cap
    get() = when (this) {
        StrokeCap.Butt -> Paint.Cap.BUTT
        StrokeCap.Round -> Paint.Cap.ROUND
        StrokeCap.Square -> Paint.Cap.SQUARE
        else -> throw IllegalArgumentException("Not `StrokeCap.Butt`, `StrokeCap.Round`, or `StrokeCap.Square`.")
    }