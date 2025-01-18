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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.databinding.BasicComboChartBinding
import com.patrykandpatrick.vico.sample.showcase.UIFramework

@Composable
private fun ComposeBasicComboChart(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
  CartesianChartHost(
    rememberCartesianChart(
      rememberColumnCartesianLayer(
        ColumnCartesianLayer.ColumnProvider.series(
          rememberLineComponent(fill = fill(Color(0xffffc002)), thickness = 16.dp)
        )
      ),
      rememberLineCartesianLayer(
        LineCartesianLayer.LineProvider.series(
          LineCartesianLayer.Line(LineCartesianLayer.LineFill.single(fill(Color(0xffee2b2b))))
        )
      ),
      startAxis = VerticalAxis.rememberStart(),
      bottomAxis = HorizontalAxis.rememberBottom(),
    ),
    modelProducer,
    modifier,
  )
}

@Composable
private fun ViewBasicComboChart(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
  AndroidViewBinding(
    { inflater, parent, attachToParent ->
      BasicComboChartBinding.inflate(inflater, parent, attachToParent).apply {
        chartView.modelProducer = modelProducer
      }
    },
    modifier,
  )
}

@Composable
internal fun BasicComboChart(uiFramework: UIFramework, modifier: Modifier) {
  val modelProducer = remember { CartesianChartModelProducer() }
  LaunchedEffect(Unit) {
    modelProducer.runTransaction {
      /* Learn more: https://patrykandpatrick.com/eji9zq. */
      columnSeries { series(4, 15, 5, 8, 10, 15, 9, 10, 7, 9, 10, 12, 2, 9, 5, 14) }
      /* Learn more: https://patrykandpatrick.com/vmml6t. */
      lineSeries { series(1, 5, 4, 7, 3, 14, 5, 9, 9, 14, 7, 13, 14, 4, 10, 12) }
    }
  }
  when (uiFramework) {
    UIFramework.Compose -> ComposeBasicComboChart(modelProducer, modifier)
    UIFramework.Views -> ViewBasicComboChart(modelProducer, modifier)
  }
}
