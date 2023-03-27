# ComposePractice
안드로이드 Jetpack Compose를 연습하기 위한 프로젝트 입니다  
구글의 강의(https://developer.android.com/courses/android-basics-compose/course?hl=ko)에 따라 공부하였습니다

## 공부한 내용

### ***23.02.20***  
- 기본적인 Compose 사용방법 및 Material Design을 사용한 앱 제작 연습
- Modifier의 기본적인 역할 공부
- LazyColumn, LazyVerticalGrid를 통한 RecyclerView와 비슷한 기능 구현
  
- 문제해결  
문제 : Compose 1.2.0 부터 Scaffold 사용시 padding 값을 지정하지 않으면 "UnusedMaterialScaffoldPaddingParameter" 에러 발생  
해결방법 : Scaffold 안의 Compsable 메소드에 Modifier.padding(it)을 추가하여 해결 (it은 PaddingValues를 의미)
<br>

### ***23.02.21***
- 지금까지 배운 내용을 가지고 앱 만들어보기 (코드랩 3-3-7)
- 색상의 의미 앱을 만들었음
- 사용한 기술 : LazyColumn, Scaffold - TopBar, animateContentSize, 앱 색상(테마)변경 등
<br>

### ***23.02.22***
- Activity의 생명주기 [onCreate(), onStart(), onRestart(), onResume(), onPause(), onStop(), onDestroy()]
- onDestroy() 후 재생성 되는 경우에도 값을 유지하려면 remember가 아닌 rememberSaveable을 사용해야됨  
- 구글 dessertclicker 앱으로 연습
<br>

### ***23.02.23***
- 앱 아키텍쳐 - ViewModel에서 앱의 UI 레이어의 상태를 관리하는 방법 (코드랩 4-1-5)
- StateFlow라는 옵저버블 데이터 홀더 flow 학습  
  
- 궁금증 : StateFlow와 LiveData의 차이점은 무엇인가  
1) 둘 다 옵저버블한 스테이트 홀더 flow 이다  
2) StateFlow는 초기 상태가 반드시 필요하지만 LiveData는 없어도 됨  
3) StateFlow는 Activity의 LifeCycle을 알지 못함
4) 클린 아키텍쳐 원칙에 따라 LiveData는 안드로이드 플랫폼에 종속되고 UI 계층에서 관리 되기 때문에 도메인 레이어에서 사용할 수 없지만 StateFlow는 사용 가능하다  
5) StateFlow는 비동기처리가 가능하다  
6) StateFlow는 Flow API를 사용가능 하여 다양한 기능을 사용할 수 있다
  
- Activity 클래스와 컴포저블 클래스, GameViewModel 클래스를 별도로 분리하여 앱 제작하는 방법 학습
- AlertDialog, OutlinedTextField 구현 형태 
<br>

### ***23.02.24***
- Unscramble 앱에 단위 테스트 작성 방법 학습 (코드랩 4-1-5)
1) Jumit을 활용한 assert(assertNotEquals, assertEquals, assertTrue, assertFalse 등)
2) 테스트 함수의 이름은 `thingUnderTest_TriggerOfTest_ResultOfTest` 규칙으로 작성하는 것이 좋음
3) 테스트 클래스의 테스트 메소드는 개별적으로 실행되어 각 테스트마다 테스트 클래스의 새로운 인스턴스를 생성 함
4) 테스트 코드의 적용 범위(Coverage) 개선 필요 이유 및 방법 학습
<br>

### ***23.02.25***
- dessertclicker 앱을 viewmode을 사용하는 앱으로 변경 (코드랩 4-1-2)

- 변경 도중 생긴 문제  
1) DessertClickerUiState() 데이터 클래스의 `currentDessertImageId`의 초기값을 0으로 설정해서 앱 실행시 다운 됨  
해결방법 : `currentDessertImageId`의 초기값을 dessertList[0].imageId로 변경
2) DessertClickerUiState() 데이터 클래스의 `currentDessertPrice`의 초기값을 0으로 설정해서 앱 실행후 첫번째 클릭시 Total Revenue 값이 변하지 않음
해결방법 : `currentDessertPrice`의 초기값을 dessertList[0].price로 변경
<br>

### ***23.02.26***
- Compose에서 Navigation 사용하는 방법 학습  (코드랩 4-2-1)

- Navigation의 구성요소 3가지
1. NavController: 대상(즉, 앱의 화면) 간 이동을 담당합니다
2. NavGraph: 이동할 컴포저블 대상을 매핑합니다
3. NavHost: NavGraph의 현재 대상을 표시하는 컨테이너 역할을 하는 컴포저블입니다

- navHostController는 NavHost 컴포저블과 함께 사용할 추가 기능을 제공하는 NavController 클래스의 서브클래스입니다
- Intent를 통해 다른 앱으로 이동하는 법 학습

- 학습 도중 생긴 문제
1) CodeLab의 7번 과정과 실제 코드가 일치하지 않음  
해결방법 : Github의 완성 코드를 참고하여 수정 (완전히 동일하지는 않게  커스터마이징 함)
<br>

### ***23.02.27***
- Compose와 Navigation을 사용한 앱 테스트하는 법 학습 (코드랩 4-2-1)
1. Compose UI를 테스트 할 때는 테스트 규칙을 생성해서 테스트 한다  
ex) @get: Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()
2. 테스트 코드에서는 일반적인 `NavHostController`를 사용하는 대신 `TestNavHostController`를 사용해야한다
3. `@Before` 주석은 `@Test` 메소드보다 우선해서 실행 된다
4. 반복되는 코드의 경우 확장함수를 사용하여 간결하게 바꿀 수 있다

- Compose와 Navigation을 이용한 앱 만들기 연습 (코드랩 4-2-4)
구글 솔루션 코드와 차이점들
1. `cancel 버튼` 기능을 함수 메소드로 따로 분리
2. AppBar의 타이틀 인자
3. onSelectionChanged 람다의 it을 이름 지정후 
<br>

### ***23.02.28***
- 화면 크기에 따라 달리지는 앱 구현 연습 (코드랩 4-3-1)
1. Compose에서는 `calculateWindowSizeClass()`를 통해서 화면 크기를 알 수 있음  
gradle - dependencies - implementation "androidx.compose.material3:material3-window-size-class:$material_version" 추가 필요
2. 화면 크기는 `Compact`(width : 0-599, height : 0-479), `Medium`(width : 600-839, height : 480-899), `Expanded`(width : 840+, height : 900+)로 나누어짐

- `NavHostController` 없이 `If - Else`문으로 간단한 화면 전환 효과구현
- `AnimatedVisibility()`를 통해 Composable 애니메이션을 포함해서 Visibility 구현 가능
<br>

### ***23.03.01***
- 화면 크기에 따라 달리지는 앱 구현 연습2 (코드랩 4-3-1)
1. Compose 미리보기 화면에서 `@Preview(widthDp = 700)`를 통해 미리보기 화면 크기 조절 가능
2. Composable에 `modifier.testTag(태그)`를 통해 태그를 부여해서 `composeTestRule.onNodeWithTagForStringId(태그).assertExists()`로 테스트 가능
3. Composable의 구성 변경 테스트는 `StateRestorationTester()` 클래스를 사용해서 가능하다 (`stateRestorationTester.emulateSavedInstanceStateRestore()`)

- 학습 도중 생긴 문제 : Expanded 화면의 미리보기가 제대로 나오지 않고 Drawer 화면이 가득차게 나오는 현상 발생   
- 해결방법 : `PermanentNavigationDrawer()`의 `drawerContent`의 `NavigationDrawerContent()`를 `PermanentDrawerSheet()`으로 감싼 뒤 `modifier.width(240.dp)` 값을 
<br>

### ***23.03.02***
- 화면 크기에 따라 달리지는 앱 구현 연습3 (코드랩 4-3-1)
- Sports 앱의 Expanded 화면을 직접 만들어보면서 연습 (구글 Compose 코드랩 4-3-5 과정)

- 학습 도중 생긴 문제 : Composable 내에서 현재 Activity를 가져오기 위해 `val activity = LocalContext.current as Activity` 를 사용했는데 이 부분이 Composable의 Preview를 빌드할 때 오류가 발생
- 해결방법 : 코드 문제가 아닌 안드로이드 스튜디오가 꼬인 것으로 File - Invalidate Caches 해주니 해결됨
<br>

### ***23.03.03***
- 화면 크기에 따라 달리지는 앱 구현 연습4
- 지금까지 배운 기술들을 활용해서 처음부터 앱 구현(구글 Compose 코드랩 4-3-6 과정)
1. LazyColumn을 사용하여 리스트를 포함한 화면 구성
2. Card를 사용하여 LazyColumn의 아이템을 구현
3. Navigation을 사용하여 화면 이동 구현

- 학습도중 생긴 문제
1. 컴포저블에서 뷰모델 변수를 선언할 때 viewModel() 기능이 오류가 남
해결방법 : gradle에 implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version" 추가
2. 첫번째 화면에서 앱바 좌측에 화살표 아이콘이 표시되지 않는데 타이틀 글자가 밀려서 생기는 문제 발생
해결방법 TopAppBar() 컴포저블의 navigationIcon 값을 if-else문을 사용해 아이콘이 없을 때 null로 처리함
<br>

### ***23.03.04***
- 화면 크기에 따라 달리지는 앱 구현 연습5
- 지금까지 배운 기술들을 활용해서 처음부터 앱 구현(구글 Compose 코드랩 4-3-6 과정)
1. calculateWindowSizeClass() - widthSizeClass()를 사용해서 기기의 화면 크기에 따라 달라지는 기능 구현
2. Expanded 화면에서는 3가지 화면(Shape, Color, Detail)이 모두 보여지고 앱바의 뒤로 버튼이 보이지 않게 구현
<br>

### ***23.03.06***
- Compose에서 비동기 작업 연습 with Coroutine (코드랩 5-1-3)
1. Composable에서 Coroutine을 실행하려면 LaunchedEffect()라는 Composable 안에서 실행해야 됨
2. LaunchedEffect()의 인자인 key값이 변경되면 기본 코루틴이 취소되고 다시 실행 됨
3. coroutineScope 블록은 블록 내부의 모든 코드가 실행이 완료되어야 반환되고 계속 진행됨
4. `public inline fun require(value: Boolean, lazyMessage: () -> Any): Unit`를 사용해서 value가 false면 IllegalArgumentException을 Throw할 수 있음
5. Coroutine의 Test는 runTest 블럭 안에서 실행할 수 있음 (testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4' 추가 필요)
6. Coroutine을 테스트 할 때 advanceTimeBy()을 사용해 특정 시간 만큼 진행시키고 runCurrent()를 통해 advanceTimeBy()에 의해 진행된 시간의 작업을 실행할 수 있음
7. Test할 때 Exception Thrown을 테스트 하고 싶으면 어노테이션을 수정해서 할 수 있음 (예시 `@Test (expected = IllegalArgumentException::class)`)  
<br>

### ***23.03.07***
- Retrofit2를 사용하여 네트워트와 통신하는 방법 공부 (코드랩 5-1-5)
0. gradle 추가
1. Retrofit.Builder()를 통해 Retrofit 객체를 생성
2. Retrofit 객체에 addConverterFactory()을 통해 JSON Convertor 추가(scalars converter, kotlinx serialization converter 등)  
3. baseUrl()에 기본 URL을 추가
4. Retrofit이 HTTP 요청을 사용하여 웹 서버와 통신하는 방법을 정의하는 인터페이스 생성
5. 인터페이스를 사용하여 Retrofit 객체를 초기화한 싱글톤 객체 생성
6. viewModel에서 viewModelScope 안에서 통신 기능 실행 <- 이 과정은 추후에 Repository로 이동할 예정
<br>

### ***23.03.09***
- Data Repository를 통해 앱 아키텍쳐 가이드 공부하기 (코드랩 5-1-5)
1. 앱의 다른 레이어가 데이터 소스에 접근할 때는 데이터 Repository를 통해야 함
2. 의존성 주입의 사용 이유와 사용 방법 공부  
의존성 주입을 할 때는 앱에 필요한 의존성 항목들이 포함된 객체인 컨테이너를 만들고  
컨테이너를 사용한 애플리케이션 클래스를 만들고 매니페스트에 등록해야 함  
3. viewModel에서 생성자를 호출하려면 viewModelProvider.Factory를 사용해야 함
4. 단위 테스트에서는 UI 스레드를 사용할 수 없기 때문에 단위 테스트용 Dispatcher를 사용하는 TestRule을 따로 만들어야 함  

- 학습 도중 생긴 궁금증1  
`Repository Interface`를 만들고 그것을 구현한 `DefaultRepository 클래스`를 만드는 이유는?  
인터페이스를 사용하여 Repository를 구현하면 코드의 유연성과 테스트의 유연성을 높이기 때문이다.  
인터페이스를 사용하면 데이터 소스가 다른 여러 구현 클래스를 작성할 때 더욱 유연하게 코드를 작성할 수 있으며,  
테스트 코드를 작성할 때 모의 객체를 사용하여 데이터 소스의 동작을 대신할 수 있기 때문에 데이터 소스의 응답을 대기하지 않고도 테스트를 할 수 있다.  

- 학습 도중 생긴 궁금증2
MarsViewModelTest 클래스의 marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess 메소드는 runTest 블록으로 지정하지 않아도 에러가 뜨지 않는데 runTest 블록을 사용한 이유  
runTest를 사용해야 하위 코루틴들을 runTest라는 최상위 테스트 코루틴으로 계층화 할 수 있기 때문  
runTest라는 최상위 테스트 코루틴으로 계층화할 때의 장점
1) runTest 블록으로 감싸면 코루틴 실행 중 발생할 수 있는 예외를 캐치하여 처리할 수 있습니다.  
     만약 runTest 블록으로 감싸지 않으면 예외가 발생했을 때 테스트가 중단되고 테스트 결과도 실패로 처리됩니다.  
     하지만 runTest 블록으로 감싸면 예외를 캐치하고 테스트 결과를 실패로 처리하면서 테스트를 계속 진행할 수 있습니다.  
2) runTest 블록으로 감싸면 코루틴이 완료될 때까지 대기할 수 있습니다.  
     코루틴은 비동기적으로 동작하기 때문에, 코루틴이 완료되기 전에 테스트가 종료될 수 있습니다.  
     하지만 runTest 블록으로 감싸면 코루틴이 완료될 때까지 대기할 수 있으므로 테스트를 완료할 수 있습니다.  
때문에 코루틴을 테스트 할 때는 runTest 블록으로 감싸지 않아도 정상적으로 작동할 수 있지만 감싸서 테스트하는 것이 안전함  
<br>

### ***23.03.10***
- Coil 라이브러리와 AsyncImage 컴포저블을 이용해서 인터넷에서 이미지 로딩하기 (코드랩 5-1-5)
0. gradle 추가 - implementation "io.coil-kt:coil-compose:2.2.2"  
1. Compose에서 Coil 라이브러리를 사용해 이미지를 로딩할 때는 AsyncImage 컴포저블을 이용하면 비동기적으로 이미지를 로딩할 수 있음  
2. AsyncImage의 model에 Coil - ImageRequest 객체를 넣어주면 됨
3. AsyncImage의 error에는 에러시 표시할 이미지를 placeholder에는 이미지 로딩중에 표시할 이미지를 입력

- LazyVerticalGrid, LazyHorizontalGrid 구현
1. Recycler뷰의 그리드화면과 같은 화면을 LazyVerticalGrid, LazyHorizontalGrid 컴포저블로 구현 가능
2. columns나 rows에 GridCells.Adaptive(최소dp) 혹은 GridCells.Fixed(표시할 갯수)를 입력해서 모양 지정 가능
3. items의 key에 값을 지정해서 각 아이템이 고유한 키를 가지게 할 수 있음 (스크롤 위치 기억 등에 사용 가능)

- 학습 도중 생긴 문제  
Retrofit 객체를 생성할 때 addConverterFactory(Json.asConverterFactory(MediaType.get("application/json"))) 이렇게 kotlinx serialization를 사용했는데  
MediaType.get()이 deprecated 되어서 에러가 발생함  
해결방법 : addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) mediaType.toMediaType()를 사용하여 해결  
<br>

### ***23.03.13***
- 지금까지 배운 인터넷에서 이미지 로드 및 표시 기능 혼자서 구현하기1 (코드랩 5-2-4)
1. Retrofit을 사용해서 인터넷에서 데이터 가져오기
2. kotlinx serialization을 사용해서 Json 파일 직렬화 하기
3. Coil과 AsyncImage 컴포저블을 사용해서 이미지 로드하기
4. DI(의존성 주입)
<br>

### ***23.03.14***
- 지금까지 배운 인터넷에서 이미지 로드 및 표시 기능 혼자서 구현하기2 (코드랩 5-2-5)
1. Naver Search Api를 활용하여 네이버 이미지 검색 기능 구현
2. 레이아웃을 LazyVerticalStaggeredGrid를 사용하여 구성
3. kotlinx serialization을 사용해서 Json 파일 직렬화 하기
4. Coil과 AsyncImage 컴포저블을 사용해서 이미지 로드하기

- 학습도중 생긴 문제 
검색어 쿼리를 입력하고 검색을 하려고 하면 `No valid NAT64 prefix` 에러가 발생하면서 앱이 팅김
원인 : kotlinx serialization은 Gson과 달리 Data Class에 Json 응답의 필드를 모두 만들어야 함 (예를 들어 Json에 name, email, address 필드가 있으면 data class에 name, email, address 변수가 있어야 함)
<br>

### ***23.03.15***
- sql 기본적인 사용법 (코드랩 6-1-2)
1. SELECT ~ FROM ~ 문 (COUNT(), SUM(), AVG(), MIN(), MAX())
2. WHERE 절 (AND, OR, LIKE)
3. GROUP BY, ORDER BY, LIMIT
4. INSERT INTO ~ VALUES ~ / UPDATE ~ SET ~ / DELETE FROM ~

- Flow, StateFlow에 대한 개념
<br>

### ***23.03.16***
- Room Database의 사용법 (코드랩 6-2-4)
1. Entity의 역할 및 사용법
2. Dao의 역할 및 사용법
3. Room Database 인스턴스 생성법
4. Room Database에서 Repository를 생성하고 사용하는 방법
5. rememberCoroutineScope()를 통해 컴포저블의 생명주기에 따르는 컴포저블 
<br>

### ***23.03.20***
- Room을 사용하여 데이터 읽기 및 업데이트 (코드랩 6-2-5, inventory)
1. ViewModel에서 Flow를 사용할 때는 StateFlow를 사용하면 UI 수명 주기와 관계없이 값을 관리할 수 있음
2. Flow를 StateFlow로 바꿀 때는 stateIn()을 사용하면 가능
3. stateIn()은 scope, started, initialValue 파라미터를 가짐
4. scope: viewModelScope가 StateFlow의 수명 주기를 정의합니다. viewModelScope가 취소되면 StateFlow도 취소됨
5. started: SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)는 마지막 구독자의 사라짐과 공유 코루틴 중지 사이의 지연(밀리초)을 설정
6. initialValue: StateFlow의 초깃값 
7. 컴포저블에서 StateFlow 값을 collectAsState()를 통해 사용

- 데이터베이스 테스트 방법
1. 데이터베이스 테스트는 androidTest 소스 디렉토리에서 실행
2. androidTest에서 JUnit을 사용한 테스트는 Activity를 따로 생성하지 않아 UI 테스트보다 빠르게 가능
3. 테스트용 Room 데이터베이스 인스턴스를 만들 때 inMemoryDatabaseBuilder(), allowMainThreadQueries() 사용하여 빠르게 테스트 가능

- 학습도중 생긴 궁금증1  
왜 데이터베이스 테스트 중 코루틴 테스트를 할 때 runTest가 아닌 runBlocking을 사용하는 이유는?  
이유 : runTest는 androidTest 소스 디렉토리의 테스트에서는 사용할 수 없음

- 학습도중 생긴 궁금증2  
ItemDetailsViewModel에서 reduceQuantityByOne()는 viewModelScope.launch로 deleteItem() suspend fun를 사용하는지 궁금  
이유 : deleteItem()은 아이템 제거 후 화면을 이동하는데 컴포저블에서 코루틴 블럭을 생성하고 suspend fun으로 구현된 deleteItem()을  후 화면 이동을 하는 것이 더 자연스러움
<br>

### ***23.03.21***
- Room을 사용한 앱 만들기 (코드랩 6-2-6)
1. Entitiy, DAO, Database 구현
2. DI를 위한 Container, Application 구현
3. createFromAsset() 메소드를 사용해 앱 내부의 Assets 폴더에서 SQLite 데이터베이스 파일을 불러와서 Room 데이터베이스를 생성
4. @ColumnInfo(name = "") 어노테이션을 사용해 필드의 이름을 직접 지정
<br>

### ***23.03.23***
- Preferences DataStore 사용법 (코드랩 6-3-3)
1. gradle에 implementation "androidx.datastore:datastore-preferences:1.0.0" 추가
2. PreferencesRepository 클래스 생성 (DataStore<Preferences> 타입의 프로퍼티를 생성자로 선언)
3. PreferencesKey를 싱글턴 객체(companion object)로 생성, ex) booleanPreferencesKey()
4. DataStore.data를 통해 Flow<Preferences> 객체 생성 -> Flow<필요한 타입>으로 변환 (catch를 통한 IOException 처리가 권장됨)
5. suspend fun을 통해 dataStore.edit()을 실행하고 키-값 쌍을 설정
6. DataStore 초기화 및 PreferencesRepository에 인스턴스 전달

- 학습도중 생긴 궁금증1  
코드랩에서 DI를 구현 할 때 Application 클래스와 AppContainer 클래스를 따로 분리하지 않았는지?
이유 : DI를 구현 할 때 Application 클래스와 AppContainer 클래스를 분리하는 것이 필수는 아님, 하지만 앱이 복잡해질수록 분리하는 것이 좋을 것으로 추측
<br>

### ***23.03.27***
- WorkManager 사용법 (코드랩 7-1-3)
1. gradle에 implementation "androidx.work:work-runtime-ktx:2.8.1" 추가
2. Worker 클래스 생성
3. Repository에서 WorkManager 인스턴스 생성
4. Repository에서 WorkRequest 생성
5. 생성된 WorkManager 인스턴스로 WorkRequest 예약 및 실행 (필요한 경우 작업 체인 생성 후 실행)
<br>
