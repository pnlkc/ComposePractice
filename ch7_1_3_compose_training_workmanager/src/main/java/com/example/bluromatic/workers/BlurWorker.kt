package com.example.bluromatic.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Worker 클래스
 * Worker 클래스는 Worker(동기) / CoroutineWorker(비동기) 클래스를 상속 받습니다.
 * 그리고 doWork() 메소드를 override하여 실행하려는 작업을 코드를 입력합니다.
 * Worker의 doWork()는 일반 fun이고, CoroutineWorker의 doWork()는 suspend fun입니다.
 *
 * Worker / CoroutineWorker 클래스 모두 ListenableWorker 클래스를 상속 받기 때문에
 * ListenableWorker 클래스의 파라미터인 "appContext: Context"와 "params: WorkerParameters"
 * 모두 인자로 전달해주어야 합니다.
 *
 * makeStatusNotification() 메소드는 WorkerUtils에서 제공되는 메소드로
 * 화면 상단에 배너를 쉽게 표시할 수 있게 해줍니다.
 *
 * androidx.work.ListenableWorker의 Result.success(), Result.failure()를 사용하여
 * 실행 중인 작업 요청의 최종 상태를 나타낼 수 있습니다.
 */
private const val TAG = "BlurWorker"

class BlurWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        val blurLevel = inputData.getInt(KEY_BLUR_LEVEL, 1)

        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )

        return withContext(Dispatchers.IO) {
            return@withContext try {
                require(!resourceUri.isNullOrBlank()) {
                    val errorMessage = applicationContext.resources.getString(R.string.invalid_input_uri)
                    Log.e(TAG, errorMessage)
                    errorMessage
                }

                val resolver = applicationContext.contentResolver

                delay(DELAY_TIME_MILLIS)

                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
                )

                val output = blurBitmap(picture, blurLevel)
                val outputUri = writeBitmapToFile(applicationContext, output)

                // 출력 데이터 객체가 이제 URI를 사용하므로 알림을 표시하는 코드가 더 이상 필요하지 않아 이 코드를 삭제합니다.
//                makeStatusNotification(
//                    "Output is $outputUri",
//                    applicationContext
//                )

                val outPutData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                Result.success(outPutData)
            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )

                Result.failure()
            }
        }
    }
}