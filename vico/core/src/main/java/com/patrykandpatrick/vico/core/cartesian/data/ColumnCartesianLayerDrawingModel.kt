/*
 * Copyright 2024 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.core.cartesian.data

import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.data.CartesianLayerDrawingModel
import com.patrykandpatrick.vico.core.common.lerp
import com.patrykandpatrick.vico.core.common.orZero

/** Houses drawing information for a [ColumnCartesianLayer]. [opacity] is the columns’ opacity. */
public class ColumnCartesianLayerDrawingModel(
  private val entries: List<Map<Double, Entry>>,
  public val opacity: Float = 1f,
) : CartesianLayerDrawingModel<ColumnCartesianLayerDrawingModel.Entry>(entries) {
  override fun transform(
    entries: List<Map<Double, Entry>>,
    from: CartesianLayerDrawingModel<Entry>?,
    fraction: Float,
  ): CartesianLayerDrawingModel<Entry> {
    val oldOpacity = (from as ColumnCartesianLayerDrawingModel?)?.opacity.orZero
    return ColumnCartesianLayerDrawingModel(entries, oldOpacity.lerp(opacity, fraction))
  }

  override fun equals(other: Any?): Boolean =
    this === other ||
      other is ColumnCartesianLayerDrawingModel &&
        entries == other.entries &&
        opacity == other.opacity

  override fun hashCode(): Int = 31 * entries.hashCode() + opacity.hashCode()

  /**
   * Houses positional information for a [ColumnCartesianLayer]’s column. [height] expresses the
   * column’s height as a fraction of the [ColumnCartesianLayer]’s height.
   */
  public class Entry(public val height: Float) : CartesianLayerDrawingModel.Entry {
    override fun transform(
      from: CartesianLayerDrawingModel.Entry?,
      fraction: Float,
    ): CartesianLayerDrawingModel.Entry {
      val oldHeight = (from as? Entry)?.height.orZero
      return Entry(oldHeight.lerp(height, fraction))
    }

    override fun equals(other: Any?): Boolean =
      this === other || other is Entry && height == other.height

    override fun hashCode(): Int = height.hashCode()
  }
}
