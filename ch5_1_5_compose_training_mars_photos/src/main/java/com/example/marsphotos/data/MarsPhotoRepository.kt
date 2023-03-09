package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto

// 앱의 다른 레이어에서 데이터 소스에 직접 접근하지 않고 데이터 저장소(Repository)를 통해 접근해야 함
/*
아래의 코드처럼 Repository Interface를 만들고 그것을 구현한 DefaultRepository 클래스를 만드는 이유는?

인터페이스를 사용하여 Repository를 구현하면 코드의 유연성과 테스트의 유연성을 높이기 때문이다.
인터페이스를 사용하면 데이터 소스가 다른 여러 구현 클래스를 작성할 때 더욱 유연하게 코드를 작성할 수 있으며,
테스트 코드를 작성할 때 모의 객체를 사용하여 데이터 소스의 동작을 대신할 수 있기 때문에 데이터 소스의 응답을
대기하지 않고도 테스트를 할 수 있다.
 */
interface MarsPhotoRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// MarsPhotoRepository 인터페이스를 실제로 구현한 클래스
class DefaultMarsPhotosRepository(
    private val marsApiService: MarsApiService
    ) : MarsPhotoRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return marsApiService.getPhotos()
    }
}