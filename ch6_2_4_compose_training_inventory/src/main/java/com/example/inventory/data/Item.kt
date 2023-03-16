/*
 * Copyright (C) 2022 The Android Open Source Project.
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

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entitiy (개체, 독립체)
 * 데이터베이스에 저장될 데이터의 형태를 가지는 개체 (Data class 형태)
 *
 * @Entity 어노테이션을 통해 Item 데이터 클래스가 room의 Entitiy임을 표시
 * tableName 값으로 테이블의 이름을 설정 (기본값은 data class의 이름 소문자화, Item -> item)
 *
 * @PrimaryKey는 테이블의 모든 항목을 식별할 수 있는 고유키로 반드시 필요
 * autoGenerate = true로 설정하면 각 항목에 자동으로 고유키가 부여됨
 */
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
