/*
 * Copyright 2025 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.sample.showcase.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.databinding.BasicLineChartBinding
import com.patrykandpatrick.vico.sample.showcase.UIFramework

@Composable
private fun ComposeBasicLineChart(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
  CartesianChartHost(
    chart =
      rememberCartesianChart(
        rememberLineCartesianLayer(),
        startAxis = VerticalAxis.rememberStart(),
        bottomAxis = HorizontalAxis.rememberBottom(),
      ),
    modelProducer = modelProducer,
    modifier = modifier,
  )
}

@Composable
private fun ViewBasicLineChart(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
  AndroidViewBinding(
    { inflater, parent, attachToParent ->
      BasicLineChartBinding.inflate(inflater, parent, attachToParent).apply {
        chartView.modelProducer = modelProducer
      }
    },
    modifier,
  )
}

@Composable
internal fun BasicLineChart(uiFramework: UIFramework, modifier: Modifier) {
  val modelProducer = remember { CartesianChartModelProducer() }
  LaunchedEffect(Unit) {
    modelProducer.runTransaction {
      /* Learn more: https://patrykandpatrick.com/vmml6t. */
      lineSeries { series(13, 8, 7, 12, 0, 1, 15, 14, 0, 11, 6, 12, 0, 11, 12, 11) }
    }
  }
  when (uiFramework) {
    UIFramework.Compose -> ComposeBasicLineChart(modelProducer, modifier)
    UIFramework.Views -> ViewBasicLineChart(modelProducer, modifier)
  }
}