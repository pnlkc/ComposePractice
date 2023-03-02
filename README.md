# ComposePractice
안드로이드 Jetpack Compose를 연습하기 위한 프로젝트 입니다  
구글의 강의(https://developer.android.com/courses/android-basics-compose/course?hl=ko)에 따라 공부하였습니다

### 공부한 내용
***23.02.20***  
> - 기본적인 Compose 사용방법 및 Material Design을 사용한 앱 제작 연습
> - Modifier의 기본적인 역할 공부
> - LazyColumn, LazyVerticalGrid를 통한 RecyclerView와 비슷한 기능 구현
  
> - 문제해결  
> 문제 : Compose 1.2.0 부터 Scaffold 사용시 padding 값을 지정하지 않으면 "UnusedMaterialScaffoldPaddingParameter" 에러 발생  
> 해결방법 : Scaffold 안의 Compsable 메소드에 Modifier.padding(it)을 추가하여 해결 (it은 PaddingValues를 의미)
  
***23.02.21***
> - 지금까지 배운 내용을 가지고 앱 만들어보기 (코드랩 3-3-7)
> - 색상의 의미 앱을 만들었음
> - 사용한 기술 : LazyColumn, Scaffold - TopBar, animateContentSize, 앱 색상(테마)변경 등

***23.02.22***
> - Activity의 생명주기 [onCreate(), onStart(), onRestart(), onResume(), onPause(), onStop(), onDestroy()]
> - onDestroy() 후 재생성 되는 경우에도 값을 유지하려면 remember가 아닌 rememberSaveable을 사용해야됨  
> - 구글 dessertclicker 앱으로 연습

***23.02.23***
> - 앱 아키텍쳐 - ViewModel에서 앱의 UI 레이어의 상태를 관리하는 방법
> - StateFlow라는 옵저버블 데이터 홀더 flow 학습  
  
> - 궁금증 : StateFlow와 LiveData의 차이점은 무엇인가  
> 1) 둘 다 옵저버블한 스테이트 홀더 flow 이다  
> 2) StateFlow는 초기 상태가 반드시 필요하지만 LiveData는 없어도 됨  
> 3) StateFlow는 Activity의 LifeCycle을 알지 못함
> 4) 클린 아키텍쳐 원칙에 따라 LiveData는 안드로이드 플랫폼에 종속되고 UI 계층에서 관리 되기 때문에 도메인 레이어에서 사용할 수 없지만 StateFlow는 사용 가능하다  
> 5) StateFlow는 비동기처리가 가능하다  
> 6) StateFlow는 Flow API를 사용가능 하여 다양한 기능을 사용할 수 있다
  
> - Activity 클래스와 컴포저블 클래스, GameViewModel 클래스를 별도로 분리하여 앱 제작하는 방법 학습
> - AlertDialog, OutlinedTextField 구현 형태 

***23.02.24***
> - Unscramble 앱에 단위 테스트 작성 방법 학습
> 1) Jumit을 활용한 assert(assertNotEquals, assertEquals, assertTrue, assertFalse 등)
> 2) 테스트 함수의 이름은 `thingUnderTest_TriggerOfTest_ResultOfTest` 규칙으로 작성하는 것이 좋음
> 3) 테스트 클래스의 테스트 메소드는 개별적으로 실행되어 각 테스트마다 테스트 클래스의 새로운 인스턴스를 생성 함
> 4) 테스트 코드의 적용 범위(Coverage) 개선 필요 이유 및 방법 학습

***23.02.25***
> - dessertclicker 앱을 viewmode을 사용하는 앱으로 변경  

> - 변경 도중 생긴 문제  
> 1) DessertClickerUiState() 데이터 클래스의 `currentDessertImageId`의 초기값을 0으로 설정해서 앱 실행시 다운 됨  
> 해결방법 : `currentDessertImageId`의 초기값을 dessertList[0].imageId로 변경
> 2) DessertClickerUiState() 데이터 클래스의 `currentDessertPrice`의 초기값을 0으로 설정해서 앱 실행후 첫번째 클릭시 Total Revenue 값이 변하지 않음
> 해결방법 : `currentDessertPrice`의 초기값을 dessertList[0].price로 변경

***23.02.26***
> - Compose에서 Navigation 사용하는 방법 학습  

> - Navigation의 구성요소 3가지
> 1. NavController: 대상(즉, 앱의 화면) 간 이동을 담당합니다
> 2. NavGraph: 이동할 컴포저블 대상을 매핑합니다
> 3. NavHost: NavGraph의 현재 대상을 표시하는 컨테이너 역할을 하는 컴포저블입니다

> - navHostController는 NavHost 컴포저블과 함께 사용할 추가 기능을 제공하는 NavController 클래스의 서브클래스입니다
> - Intent를 통해 다른 앱으로 이동하는 법 학습

> - 학습 도중 생긴 문제
> 1) CodeLab의 7번 과정과 실제 코드가 일치하지 않음  
> 해결방법 : Github의 완성 코드를 참고하여 수정 (완전히 동일하지는 않게  커스터마이징 함)

***23.02.27***
> - Compose와 Navigation을 사용한 앱 테스트하는 법 학습
> 1. Compose UI를 테스트 할 때는 테스트 규칙을 생성해서 테스트 한다  
> ex) @get: Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()
> 2. 테스트 코드에서는 일반적인 `NavHostController`를 사용하는 대신 `TestNavHostController`를 사용해야한다
> 3. `@Before` 주석은 `@Test` 메소드보다 우선해서 실행 된다
> 4. 반복되는 코드의 경우 확장함수를 사용하여 간결하게 바꿀 수 있다

> - Compose와 Navigation을 이용한 앱 만들기 연습 (Lunch Tray)
> 구글 솔루션 코드와 차이점들
> 1. `cancel 버튼` 기능을 함수 메소드로 따로 분리
> 2. AppBar의 타이틀 인자
> 3. onSelectionChanged 람다의 it을 이름 지정후 

***23.02.28***
> - 화면 크기에 따라 달리지는 앱 구현 연습
> 1. Compose에서는 `calculateWindowSizeClass()`를 통해서 화면 크기를 알 수 있음  
> gradle - dependencies - implementation "androidx.compose.material3:material3-window-size-class:$material_version" 추가 필요
> 2. 화면 크기는 `Compact`(width : 0-599, height : 0-479), `Medium`(width : 600-839, height : 480-899), `Expanded`(width : 840+, height : 900+)로 나누어짐

> - `NavHostController` 없이 `If - Else`문으로 간단한 화면 전환 효과구현
> - `AnimatedVisibility()`를 통해 Composable 애니메이션을 포함해서 Visibility 구현 가능

***23.03.01***
> - 화면 크기에 따라 달리지는 앱 구현 연습2
> 1. Compose 미리보기 화면에서 `@Preview(widthDp = 700)`를 통해 미리보기 화면 크기 조절 가능
> 2. Composable에 `modifier.testTag(태그)`를 통해 태그를 부여해서 `composeTestRule.onNodeWithTagForStringId(태그).assertExists()`로 테스트 가능
> 3. Composable의 구성 변경 테스트는 `StateRestorationTester()` 클래스를 사용해서 가능하다 (`stateRestorationTester.emulateSavedInstanceStateRestore()`)

> - 학습 도중 생긴 문제 : Expanded 화면의 미리보기가 제대로 나오지 않고 Drawer 화면이 가득차게 나오는 현상 발생   
> - 해결방법 : `PermanentNavigationDrawer()`의 `drawerContent`의 `NavigationDrawerContent()`를 `PermanentDrawerSheet()`으로 감싼 뒤 `modifier.width(240.dp)` 값을 

***23.03.02***
> - 화면 크기에 따라 달리지는 앱 구현 연습3
> - Sports 앱의 Expanded 화면을 직접 만들어보면서 연습 (구글 Compose 코드랩 4-3-5 과정)

> - 학습 도중 생긴 문제 : Composable 내에서 현재 Activity를 가져오기 위해 `val activity = LocalContext.current as Activity` 를 사용했는데 이 부분이 Composable의 Preview를 빌드할 때 오류가 발생
> - 해결방법 : 코드 문제가 아닌 안드로이드 스튜디오가 꼬인 것으로 File - Invalidate Caches 해주니 해결됨
