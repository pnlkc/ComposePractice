/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.*
import com.example.bluromatic.*
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import java.time.Duration

/**
 * WorkManager 및 WorkRequest는 관심사 분리 디자인 원칙에 따라 Repository에 위치합니다.
 *
 * WorkManager 클래스
 * 이 클래스는 실제로 WorkRequest를 예약하고 실행합니다.
 * 지정된 제약 조건을 준수하면서 시스템 리소스에 부하를 분산하는 방식으로 WorkRequest를 예약합니다.
 *
 * WorkManager 작업 제약 조건
 * Constraints.Builder()를 통해 WorkRequest를 실행하기 전에 충족해야 하는 제약 조건을 만들 수 있습니다.
 * 이후 WorkRequest.setConstraints()로 제약 조건을 추가할 수 있습니다.
 *
 * WorkRequest 클래스
 * WorkRequest 클래스는 작업 실행 요청을 나타냅니다.
 * WorkRequest는 OneTimeWorkRequest, PeriodicWorkRequest 두가지 유형이 있습니다.
 * OneTimeWorkRequest는 한번만 실행되는 WorkRequest 입니다.
 * PeriodicWorkRequest는 일정 주기로 반복 실행되는 WorkRequest 입니다.
 * WorkRequest에 작업 실행 전에 특정 조건 충족을 요구하는 제약 조건을 배치할 수도 있습니다.
 * 예를 들어, 기기가 충전 상태일 때에만 작업을 실행하도록 하는 것입니다.
 *
 * 작업 체인
 * WorkManager를 통해 순서대로 실행 되거나 동시에 실행되는 별도의 WorkerRequest를 만들 수 있습니다.
 * beginWith() : 중복 실행을 허용하는 작업 체인입니다.
 * beginUniqueWork() : 태그가 동일한 작업 체인은 중복 실행을 제한하는 작업 체인입니다.
 * beginUniqueWork()은 ExistingWorkPolicy 값인 REPLACE, KEEP, APPEND, APPEND_OR_REPLACE를 통해
 * 중복시 할 행동을 설정할 수 있습니다.
 *
 * blurBuilder.setInputData()를 통해 WorkRequest의 입력 데이터 객체를 설정할 수 있습니다.
 *
 * OneTimeWorkRequest 객체는 두 가지 방식으로 생성할 수 있습니다.
 * OneTimeWorkRequest.from(CleanupWorker::class.java)는
 * OneTimeWorkRequestBuilder<BlurWorker>().build()와 동일
 *
 * enqueue() 를 통해 작업을 시작할 수 있습니다.
 *
 * workManager = WorkManager.getInstance(context)를 통해 WorkManager 클래스의 인스턴스를 생성합니다.
 *
 * blurBuilder = OneTimeWorkRequestBuilder<Worker 클래스>()를 통해 WorkRequest Builder를 생성합니다.
 */
class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    private var imageUri: Uri = context.getImageUri()
    private val workManager = WorkManager.getInstance(context)

    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow()
            .mapNotNull { if (it.isNotEmpty()) it.first() else null }

    /**
     * Create the WorkRequests to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    override fun applyBlur(blurLevel: Int) {
        // 작업 체인 생성
//        var continuation = workManager.beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))
//        var continuation = workManager.beginWith(OneTimeWorkRequestBuilder<CleanupWorker>().build())

        // 중복 실행을 방지하는 작업 체인 (서버와 동기화 등의 작업)
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.Companion.from(CleanupWorker::class.java)
            )

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
        blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))
        blurBuilder.setConstraints(constraints)
        // 단일 작업 실행행//        workManager.enqueue(blurBuilder.build())

        continuation = continuation.then(blurBuilder.build())

        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        // 작업 시작
        continuation.enqueue()
    }

    /**
     * Cancel any ongoing WorkRequests
     * */
    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    /**
     * Creates the input data bundle which includes the blur level to
     * update the amount of blur to be applied and the Uri to operate on
     * @return Data which contains the Image Uri as a String and blur level as an Integer
     */
    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
